package com.hantsylabs.restexample.springmvc.api;

/**
 * Used to transport messages back to the client.
 * 
 * @author hantsy
 */
public class ResponseMessage {
    public enum Type {
        success, warn, danger, info;
    }

    private final Type type;
    private final String text;

    public ResponseMessage(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }
}
