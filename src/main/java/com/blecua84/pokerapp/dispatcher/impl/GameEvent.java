package com.blecua84.pokerapp.dispatcher.impl;

/**
 * Clase que define la información a transmitir entre objetos de los eventos producidos.
 *
 * @author blecua84
 */
public class GameEvent {

    private String type;

    private String source;

    private Object payload;

    public GameEvent() {}

    public GameEvent(String type, String source) {
        this.type = type;
        this.source = source;
    }

    public GameEvent(String type, String source, Object payload) {
        this.type = type;
        this.source = source;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
