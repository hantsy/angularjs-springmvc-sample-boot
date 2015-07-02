package com.hantsylabs.restexample.springmvc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to transport messages back to the client.
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class ResponseMessage {

    public enum Type {
        success, warning, danger, info;
    }

    private Type type;
    private String text;
    private String code;

    public ResponseMessage(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public ResponseMessage(Type type, String code, String message) {
        this.type = type;
        this.code = code;
        this.text = message;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

    public String getCode() {
        return code;
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

    private List<Error> errors = new ArrayList<Error>();

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void addError(String field, String code, String message) {
        this.errors.add(new Error(field, code, message));
    }

    class Error {

        private String code;
        private String message;
        private String field;

        private Error(String field, String code, String message) {
            this.field = field;
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

    }

}
