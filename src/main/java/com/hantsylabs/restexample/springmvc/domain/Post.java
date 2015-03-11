package com.hantsylabs.restexample.springmvc.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractAuditable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *  
 * @author hantsy
 *
 */
@Entity
@Table(name="posts")
@JsonIgnoreProperties("comments")
public class Post extends AbstractAuditable<User, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;
		
	@OneToMany(mappedBy="post", cascade={CascadeType.ALL})
	private List<Comment> comments=new ArrayList<>();
	

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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
