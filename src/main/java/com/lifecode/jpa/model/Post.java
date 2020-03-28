package com.lifecode.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.lifecode.jpa.model.audit.DateAudit;

@Entity
@Table(name = "posts")
public class Post extends DateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3110411846914329919L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(
			mappedBy = "post",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private Set<Comment> comments;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "posts_images", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	private Set<Image> images = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "posts_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "posts_authors", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	@Size(max = 2)
	private int level;
	
	@Lob
	@Length(max = 65535)
	private String title;
	
	@Lob
	@Length(max = 65535)
	private String sumary;

	@Lob
	@Length(max = 65535)
	private String content;
	
	@Size(max = 11)
	private int times_of_view;

	public Long getId() {
		return id;
	}
}
