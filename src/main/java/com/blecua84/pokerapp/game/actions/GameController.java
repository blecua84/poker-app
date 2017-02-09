package com.blecua84.pokerapp.game.actions;

import com.blecua84.pokerapp.game.config.Settings;

/**
 * Interface controlador del juego.
 *
 * @author josejavier.blecua
 */
public interface GameController {

    public void setSettings(Settings settings);

    public boolean addStrategy(Strategy strategy);

    public void start();

    public void waitFinish();
}
