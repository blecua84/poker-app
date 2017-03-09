package com.blecua84.pokerapp;

import com.blecua84.pokerapp.api.exceptions.GameException;
import com.blecua84.pokerapp.controllers.GameControllerImpl;
import com.blecua84.pokerapp.game.actions.GameController;
import com.blecua84.pokerapp.game.actions.Strategy;
import com.blecua84.pokerapp.game.config.Settings;
import com.blecua84.pokerapp.gui.TexasHoldEmView;
import com.blecua84.pokerapp.strategies.RandomStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase principal que inicia el juego.
 *
 * @author blecua84
 */
public class MainController {

    private static final int PLAYERS = 10;

    public static void main(String[] args)throws InterruptedException,GameException {
        Strategy strategyMain = new RandomStrategy("RandomStrategy-0");
        TexasHoldEmView texasHoldEmView = new TexasHoldEmView(strategyMain);
        texasHoldEmView.setVisible(true);
        strategyMain = texasHoldEmView.getStrategy();

        List<Strategy> strategies = new ArrayList<>();
        strategies.add(strategyMain);
        for (int i = 1; i < PLAYERS; i++) {
            strategies.add(new RandomStrategy("RandomStrategy-"+String.valueOf(i)));
        }

        Collections.shuffle(strategies);

        Settings settings = new Settings();
        settings.setMaxErrors(3);
        settings.setMaxPlayers(PLAYERS);
        settings.setMaxRounds(1000);
        settings.setTime(500);
        settings.setPlayerChip(5000L);
        settings.setRounds4IncrementBlind(20);
        settings.setSmallBind(settings.getPlayerChip() / 100);

        GameController controller = new GameControllerImpl();
        controller.setSettings(settings);
        for (Strategy strategy : strategies) {
            controller.addStrategy(strategy);
        }

        controller.start();
    }
}
