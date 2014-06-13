package com.hantsylabs.restexample.springmvc.api;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hantsylabs.restexample.springmvc.Constants;
import com.hantsylabs.restexample.springmvc.repository.CommentRepository;

@RestController
@RequestMapping(value = Constants.URI_API)
public class CommentController {
	private static final Logger log = LoggerFactory
			.getLogger(CommentController.class);

	@Inject
	private CommentRepository commentRepository;

	@RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<AlertMessage> deleteComment(@PathVariable("id") Long id) {
		if (log.isDebugEnabled()) {
			log.debug("get comments of post id @"+id);
		}

		commentRepository.delete(id);
		

		return new ResponseEntity<>(AlertMessage.success("Comment is deleted successfully!"), HttpStatus.NO_CONTENT);
	}

}
