package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hantsy
 *
 */
public class PostDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    private SimpleUserDetails createdBy;

    private Date createdDate;

    private SimpleUserDetails lastModifiedBy;

    private Date lastModifiedDate;

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

    public SimpleUserDetails getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleUserDetails createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleUserDetails getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(SimpleUserDetails lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "PostDetails{" + "title=" + title + ", content=" + content + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
