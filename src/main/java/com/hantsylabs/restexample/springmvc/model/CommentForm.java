package com.hantsylabs.restexample.springmvc.model;


import java.io.Serializable;


/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 *
 */
public class CommentForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentForm{ content=" + content + '}';
    }
}
