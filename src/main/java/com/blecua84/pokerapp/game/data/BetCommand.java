package com.blecua84.pokerapp.game.data;

import com.blecua84.pokerapp.api.exceptions.ExceptionUtil;

/**
 * Clase que modela una apuesta.
 *
 * @author josejavier.blecua
 */
public class BetCommand {

    private final BetCommandType type;
    private long chips;

    public BetCommand(BetCommandType type, long chips) {
        ExceptionUtil.checkNullArgument(type, "type");
        ExceptionUtil.checkMinValueArgument(chips, 0L, "chips");
        this.type = type;
        this.chips = chips;
    }

    public BetCommand(BetCommandType type) {
        this(type, 0);
    }

    public BetCommandType getType() {
        return type;
    }

    public long getChips() {
        return chips;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }
}
