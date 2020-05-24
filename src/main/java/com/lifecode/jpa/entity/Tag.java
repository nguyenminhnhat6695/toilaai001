package com.lifecode.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tags")
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	private Long id;
	
	@Size(max = 50)
	private String tag;
	
//	@ManyToOne
//	@JoinColumn(name = "category_id")
//	private Category category;

	public Tag(@Size(max = 50) String tag) {
		super();
		this.tag = tag;
	}
}
