package com.hantsylabs.restexample.springmvc.api;

/**
 * Used to transport messages back to the client.
 * 
 * @author hantsy
 */
public class AlertMessage {
	public enum Type {
		success, warning, danger, info;
	}

	private final Type type;
	private final String text;

	public AlertMessage(Type type, String text) {
		this.type = type;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Type getType() {
		return type;
	}

	public static AlertMessage success(String text) {
		return new AlertMessage(Type.success, text);
	}

	public static AlertMessage warning(String text) {
		return new AlertMessage(Type.warning, text);
	}

	public static AlertMessage danger(String text) {
		return new AlertMessage(Type.danger, text);
	}

	public static AlertMessage info(String text) {
		return new AlertMessage(Type.info, text);
	}
}
