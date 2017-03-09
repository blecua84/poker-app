package com.blecua84.pokerapp.controllers;

import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.game.config.Settings;
import com.blecua84.pokerapp.game.data.GameInfo;
import com.blecua84.pokerapp.game.data.PlayerInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Clase de utilidad para adaptar la información de la lógica interna que manejará la API del juego.
 *
 * @author blecua84
 */
public class PlayerAdapter {

    public PlayerAdapter() {}

    public static List<PlayerInfo> toPlayerInfo(Collection<PlayerEntity> players, String name) {
        List<PlayerInfo> result = Collections.emptyList();

        if (players != null) {
            result = new ArrayList<>(players.size());
            for (PlayerEntity pe : players) {
                result.add(copy(pe, pe.isShowCards() || pe.getName().equals(name)));
            }
        }

        return result;
    }

    public static GameInfo<PlayerInfo> toTableState(ModelContext model, String name) {
        GameInfo<PlayerInfo> result = new GameInfo<>();
        result.setCommunityCards(model.getCommunityCards());
        result.setDealer(model.getDealer());
        result.setGameState(model.getGameState());
        result.setPlayerTurn(model.getPlayerTurn());
        result.setRound(model.getRound());

        if (model.getSettings() != null) {
            result.setSettings(new Settings(model.getSettings()));
        }
        result.setPlayers(toPlayerInfo(model.getPlayers(), name));

        return result;
    }

    public static PlayerInfo copy(PlayerEntity p, boolean copyCards) {
        PlayerInfo result = new PlayerInfo(); result.setName(p.getName());
        result.setChips(p.getChips());
        result.setBet(p.getBet());

        if (copyCards) {
            result.setCards(p.getCard(0), p.getCard(1));
        }

        result.setState(p.getState());
        result.setErrors(p.getErrors());

        return result;
    }
}
