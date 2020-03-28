package com.lifecode.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.lifecode.jpa.model.audit.DateAudit;

@Entity
@Table(name = "comments")
public class Comment extends DateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3110411846914329919L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	@Column(name = "comment_parent_id")
	@Size(max = 11)
	private Long parent_id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@Lob
	@Length(max = 65535)
	private String comment;
}
