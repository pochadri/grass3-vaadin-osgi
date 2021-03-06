package org.myftp.gattserver.grass3.model.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTENTNODE")
public class ContentNode {

	/**
	 * ID služby, která daný obsah umí číst
	 */
	private String contentReaderId;

	/**
	 * ID samotného obsahu v rámci dané služby (typu obsahu)
	 */
	private Long contentId;

	/**
	 * Název obsahu
	 */
	private String name;

	/**
	 * nadřazený uzel (kategorie ve které obsah je)
	 */
	@ManyToOne
	private Node parent;

	/**
	 * Kdy byl obsah vytvořen
	 */
	private Date creationDate;

	/**
	 * Kdy byl naposledy upraven
	 */
	private Date lastModificationDate;

	/**
	 * Je obsah ve fázi příprav, nebo už má být publikován ?
	 */
	private Boolean publicated = true;

	/**
	 * Tagy
	 */
	@ManyToMany
	private Set<ContentTag> contentTags;

	/**
	 * Kdo ho vytvořil
	 */
	@ManyToOne
	private User author;

	/**
	 * DB identifikátor
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ContentNode))
			return false;
		return ((ContentNode) obj).getId() == getId();
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentReaderId() {
		return contentReaderId;
	}

	public void setContentReaderId(String contentReaderId) {
		this.contentReaderId = contentReaderId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public Boolean getPublicated() {
		return publicated;
	}

	public void setPublicated(Boolean publicated) {
		this.publicated = publicated;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ContentTag> getContentTags() {
		return contentTags;
	}

	public void setContentTags(Set<ContentTag> contentTags) {
		this.contentTags = contentTags;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

}
