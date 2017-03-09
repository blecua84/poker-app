package com.blecua84.pokerapp.controllers;

import com.blecua84.pokerapp.api.data.Deck;
import com.blecua84.pokerapp.dispatcher.GameEventDispatcher;
import com.blecua84.pokerapp.dispatcher.impl.GameEvent;
import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.ModelUtil;
import com.blecua84.pokerapp.game.config.Settings;
import com.blecua84.pokerapp.game.data.BetCommand;
import com.blecua84.pokerapp.game.data.BetCommandType;
import com.blecua84.pokerapp.game.data.GameState;
import com.blecua84.pokerapp.statemachine.decorators.StateDecoratorBuilder;
import com.blecua84.pokerapp.statemachine.impl.StateMachine;
import com.blecua84.pokerapp.statemachine.impl.StateMachineInstance;
import com.blecua84.pokerapp.statemachine.states.State;
import com.blecua84.pokerapp.states.*;
import com.blecua84.pokerapp.timer.GameTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

import static com.blecua84.pokerapp.controllers.GameControllerImpl.SYSTEM_CONTROLLER;

/**
 * Clase para conectar con una instancia de la máquina de estados del juego del Póquer, los eventos que se produzcan ya
 * sea por el comportamiento de los jugadores o por el del temporizador se canalizaran a través de esta clase para
 * hacerlos llegar a la instancia de la máquina de estados.
 *
 * @author blecua84
 */
public class StateMachineConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineConnector.class);
    private static final int END_HAND_SLEEP_TIME = 1000;
    public static final String NEXT_PLAYER_TURN = "nextPlayerTurn";

    private final StateMachine<ModelContext> texasStateMachine = buildStateMachine();
    private final Map<String, GameEventDispatcher> playersDispatcher;
    private final GameTimer timer;
    private ModelContext model;
    private GameEventDispatcher system;
    private StateMachineInstance<ModelContext> instance;
    private long timeoutId = 0;
    public StateMachineConnector(GameTimer t,Map<String, GameEventDispatcher> pd){
        this.playersDispatcher = pd;
        this.timer = t;
    }

    public void setSystem(GameEventDispatcher system) {
        this.system = system;
    }

    public void createGame(Settings settings) {
        if (model == null) {
            LOGGER.debug("createGame: {}", settings);
            model = new ModelContext(settings);
            model.setDealer(-1);
        }
    }

    public void addPlayer(String playerName) {
        if (model != null) {
            LOGGER.debug("addPlayer: \"{}\"", playerName);
            model.addPlayer(playerName);
        }
    }

    public void startGame() {
        LOGGER.debug("startGame");
        if (instance == null && model != null) {
            model.setDeck(new Deck());
            instance = texasStateMachine.startInstance(model);
            model.setDealer(0);
            execute();
        }
    }

    public void betCommand(String playerName, BetCommand command) {
        LOGGER.debug("betCommand: {} -> {}", playerName, command);
        if (instance != null && playerName.equals(model.getPlayerTurnName())) {
            BetCommand betCommand = command;
            if (betCommand == null) {
                betCommand = new BetCommand(BetCommandType.ERROR);
            }
            model.getPlayerByName(playerName).setBetCommand(betCommand);
            execute(); }
    }

    public void timeOutCommand(Long timeoutId) {
        LOGGER.debug("timeOutCommand: id: {}", timeoutId);
        if (instance != null && timeoutId == this.timeoutId) {
            LOGGER.debug("timeOutCommand: player: {}", model.getPlayerTurnName());
            model.getPlayerByName(model.getPlayerTurnName()).setBetCommand(new BetCommand(BetCommandType.TIMEOUT));
            execute();
        }
    }

    private void execute() {
        if (instance.execute().isFinish()) {
            model.setGameState(GameState.END);
            model.setCommunityCards(Collections.emptyList());
            notifyEndGame();
            instance = null;
        }
    }

    private void notifyInitHand() {
        notifyEvent(GameControllerImpl.INIT_HAND_EVENT_TYPE);
    }

    private void notifyBetCommand() {
        String playerTurn = model.getLastPlayerBet().getName();
        BetCommand lbc = model.getLastBetCommand();
        LOGGER.debug("notifyBetCommand -> {}: {}", playerTurn, lbc);
        for (String playerName : playersDispatcher.keySet()) {
            playersDispatcher.get(playerName).dispatch(
                    new GameEvent(GameControllerImpl.BET_COMMAND_EVENT_TYPE, playerTurn,
                            new BetCommand(lbc.getType(), lbc.getChips())));

        }
    }

    private void notifyCheck() {
        LOGGER.debug("notifyCheck: {}", GameControllerImpl.CHECK_PLAYER_EVENT_TYPE);
        for (String playerName : playersDispatcher.keySet()) {
            playersDispatcher.get(playerName).dispatch(
                    new GameEvent(GameControllerImpl.CHECK_PLAYER_EVENT_TYPE,
                            SYSTEM_CONTROLLER, model.getCommunityCards()));
        }
    }

    private void notifyPlayerTurn() {
        String playerTurn = model.getPlayerTurnName(); if (playerTurn != null) {
            LOGGER.debug("notifyPlayerTurn -> {}", playerTurn);
            playersDispatcher.get(playerTurn).dispatch(
                    new GameEvent(GameControllerImpl.GET_COMMAND_PLAYER_EVENT_TYPE,
                            SYSTEM_CONTROLLER,
                            PlayerAdapter.toTableState(model, playerTurn)));
        }
        timer.resetTimer(++timeoutId);
    }

    private void notifyEndHand() {
        notifyEvent(GameControllerImpl.END_HAND_PLAYER_EVENT_TYPE); try {
            Thread.sleep(END_HAND_SLEEP_TIME);
        } catch (InterruptedException ex) {
            LOGGER.error("Error en la espera despues de terminar una mano.", ex);
        }
    }

    private void notifyEndGame() {
        notifyEvent(GameControllerImpl.END_GAME_PLAYER_EVENT_TYPE);
        system.dispatch(
            new GameEvent(GameControllerImpl.EXIT_CONNECTOR_EVENT_TYPE,
                    SYSTEM_CONTROLLER));
        notifyEvent(GameControllerImpl.EXIT_CONNECTOR_EVENT_TYPE);
    }

    private void notifyEvent(String type) {
        LOGGER.debug("notifyEvent: {} -> {}", type, model);
        for (String playerName : playersDispatcher.keySet()) {
            playersDispatcher.get(playerName).dispatch(
                new GameEvent(type, SYSTEM_CONTROLLER,
                        PlayerAdapter.toTableState(model, playerName)));

        }
    }

    private StateMachine<ModelContext> buildStateMachine() {
        StateMachine<ModelContext> sm = new StateMachine<>();
        final State<ModelContext> initHandState =
                StateDecoratorBuilder.after(new InitHandState(), () -> notifyInitHand());
        final State<ModelContext> betRoundState = StateDecoratorBuilder
                .create(new BetRoundState())
                .before(() -> notifyPlayerTurn())
                .after(() -> notifyBetCommand())
                .build();
        final State<ModelContext> checkState =
                StateDecoratorBuilder.after(new CheckState(), () -> notifyCheck());
        final State<ModelContext> showDownState = new ShowDownState();
        final State<ModelContext> winnerState = new WinnerState();
        final State<ModelContext> endHandState =
                StateDecoratorBuilder.before(new EndHandState(), () -> notifyEndHand());

        sm.setInitState(initHandState);
        // initHandState transitions
        sm.setDefaultTransition(initHandState, betRoundState);
        // betRoundState transitions
        sm.addTransition(betRoundState, betRoundState,
                c -> c.getPlayerTurn() != ModelUtil.NO_PLAYER_TURN);
        sm.addTransition(betRoundState, winnerState,
                c -> c.getPlayersAllIn() + c.getActivePlayers() == 1);
        sm.setDefaultTransition(betRoundState, checkState);
        // checkState transitions
        sm.addTransition(checkState, showDownState,
                c -> c.getGameState() == GameState.SHOWDOWN);
        sm.addTransition(checkState, betRoundState,
                c -> c.getPlayerTurn() != ModelUtil.NO_PLAYER_TURN);
        sm.setDefaultTransition(checkState, checkState);
        // betWinnerState transitions
        sm.setDefaultTransition(winnerState, endHandState);
        // showDownState transitions
        sm.setDefaultTransition(showDownState, endHandState);

        // endHandState transitions
        sm.addTransition(endHandState, initHandState, c -> c.getNumPlayers() > 1
                && c.getRound() < c.getSettings().getMaxRounds());

        return sm;
    }

}
