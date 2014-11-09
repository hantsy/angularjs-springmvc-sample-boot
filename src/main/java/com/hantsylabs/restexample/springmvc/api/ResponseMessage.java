package com.hantsylabs.restexample.springmvc.api;

/**
 * Used to transport messages back to the client.
 * 
 * @author hantsy
 */
public class ResponseMessage {
	public enum Type {
		success, warning, danger, info;
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

	public static ResponseMessage success(String text) {
		return new ResponseMessage(Type.success, text);
	}

	public static ResponseMessage warning(String text) {
		return new ResponseMessage(Type.warning, text);
	}

	public static ResponseMessage danger(String text) {
		return new ResponseMessage(Type.danger, text);
	}

	public static ResponseMessage info(String text) {
		return new ResponseMessage(Type.info, text);
	}
}
