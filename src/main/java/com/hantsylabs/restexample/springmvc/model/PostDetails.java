package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 *
 */
public class PostDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String title;

    private String content;
    
    private String status;

    private SimpleUserDetails createdBy;

    private LocalDateTime createdDate;

    private SimpleUserDetails lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public SimpleUserDetails getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(SimpleUserDetails lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    @Override
    public String toString() {
        return "PostDetails{" + "id=" + id + ", title=" + title + ", content=" + content + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
