package com.hantsylabs.restexample.springmvc.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public enum Status {

        DRAFT,
        PUBLISHED
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    @Size(max = 2000)
    private String content;

    @Column(name = "status")
    @Enumerated
    private Status status = Status.DRAFT;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @CreatedBy
    private User createdBy;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    @CreatedBy
    private User lastModifiedBy;

    @Column(name = "last_modified_date")
    @CreatedDate
    private LocalDateTime lastModifiedDate;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
