package com.hantsylabs.restexample.springmvc.model;


import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author hantsy
 *
 */
public class CommentForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentForm{" + "id=" + id + ", content=" + content + '}';
    }
}
