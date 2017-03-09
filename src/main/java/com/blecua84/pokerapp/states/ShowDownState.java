package com.blecua84.pokerapp.states;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.service.impl.HandEvaluatorImpl;
import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.game.data.PlayerState;
import com.blecua84.pokerapp.game.evaluators.Hand7Evaluator;
import com.blecua84.pokerapp.statemachine.states.State;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación del estado ShowDown.
 *
 * @author blecua84
 */
public class ShowDownState implements State<ModelContext> {

    private static final String NAME = "ShowDown";

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Calcula el valor de las manos de cada jugador apoyándose en la clase Hand7Evaluator.
     *
     * @param cardList Listado de cartas disponibles.
     * @param playerEntityList Listado de jugadores.
     * @return Listado de jugadores con el valor de sus manos establecido.
     */
    private List<PlayerEntity> calculateHandValue(List<Card> cardList, List<PlayerEntity> playerEntityList) {

        Hand7Evaluator hand7Evaluator = new Hand7Evaluator(new HandEvaluatorImpl());
        hand7Evaluator.setCommunityCards(cardList);

        playerEntityList.stream().forEach(
                p -> p.setHandValue(hand7Evaluator.eval(p.getCard(0), p.getCard(1)))
        );

        return playerEntityList;
    }

    @Override
    public boolean execute(ModelContext context) {
        // Obtenemos el listado de jugadores con sus manos calculadas
        List<PlayerEntity> players = calculateHandValue(context.getCommunityCards(), context.getPlayers());

        // Agrupamos a los jugadores en función de la cuantía de su apuesta
        Map<Long, List<PlayerEntity>> indexByBet = players.stream()
                .filter(p -> p.getBet() > 0)
                .collect(Collectors.groupingBy(p -> p.getBet()));

        // Filtramos la lista en orden inverso(primero los usuarios con apuestas más altas)
        List<Long> inverseSortBets = new ArrayList<>(indexByBet.keySet());
        Collections.sort(inverseSortBets, (l0, l1) -> Long.compare(l1, l0));

        Iterator<Long> it = inverseSortBets.iterator();
        List<PlayerEntity> lastPlayers = indexByBet.get(it.next());

        while(it.hasNext()) {
            List<PlayerEntity> currentPlayer = indexByBet.get(it.next());
            currentPlayer.addAll(lastPlayers);
            lastPlayers = currentPlayer;
        }

        Set<Long> bet4Analysis = players.stream()
                .filter(p -> p.getState() == PlayerState.ALL_IN)
                .map(p -> p.getBet()).collect(Collectors.toSet());
        bet4Analysis.add(inverseSortBets.get(0));

        long accumulateChips = 0L;
        long lastBet = 0L;

        while(!inverseSortBets.isEmpty()) {
            Long bet = inverseSortBets.remove(inverseSortBets.size() - 1);
            List<PlayerEntity> currentPlayers = indexByBet.get(bet);
            accumulateChips += (bet - lastBet) * currentPlayers.size();
            if (bet4Analysis.contains(bet)) {
                Collections.sort(currentPlayers,
                        (p0, p1) -> p1.getHandValue() - p0.getHandValue());
                List<PlayerEntity> winners = new ArrayList<>(currentPlayers.size());
                currentPlayers.stream()
                        .filter(p -> p.getState() != PlayerState.OUT)
                        .peek  (p -> p.setShowCards(true))
                        .filter(p -> winners.isEmpty() ||
                                p.getHandValue() == winners.get(0).getHandValue())
                        .forEach(winners::add);
                long chips4Player = accumulateChips / winners.size();
                winners.stream().forEach(p -> p.addChips(chips4Player));
                int remain = (int) accumulateChips % winners.size();
                if (remain > 0) {
                    Collections.shuffle(winners);
                    winners.stream().limit(remain).forEach(p -> p.addChips(1));
                }
                accumulateChips = 0L;
            }
            lastBet = bet;
        }

        return true;
    }
}
