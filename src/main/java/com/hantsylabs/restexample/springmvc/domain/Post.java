package com.hantsylabs.restexample.springmvc.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

/**
 *
 * @author hantsy
 *
 */
@Entity
@Table(name = "posts")
public class Post extends AbstractAuditable<User, Long> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public enum Status {

        DRAFT,
        PUBLISHED
    }

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    @Size(max = 2000)
    private String content;

    @Column(name = "status")
    @Enumerated
    private Status status = Status.DRAFT;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", content=" + content + ", status=" + status + ", comments=" + comments + '}';
    }
}
