package com.example.rest.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="postlikes", uniqueConstraints= @UniqueConstraint(columnNames={"user_id", "post_id"}))
public class Favori implements Serializable {
	
	private static final long serialVersionUID = -4538179406393643986L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) // default = EAGER
	@JoinColumn(name = "user_id")
	private User utilisateur;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "post_id")
	private Publication post;
	
	private Timestamp date;
	
	public Favori() {}

	public Favori(Long id, User utilisateur, Publication post) {
		super();
		this.id = id;
		this.utilisateur = utilisateur;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(User utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Publication getPost() {
		return post;
	}

	public void setPost(Publication post) {
		this.post = post;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		var prime = 31;
		var result = 1;
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Favori other = (Favori) obj;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}	
}
