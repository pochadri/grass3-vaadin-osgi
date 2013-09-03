package org.myftp.gattserver.grass3.pg.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.myftp.gattserver.grass3.model.domain.ContentNode;

@Entity
@Table(name = "PHOTOGALLERY")
public class Photogallery {

	/**
	 * Meta-informace o obsahu
	 */
	@OneToOne
	private ContentNode contentNode;

	/**
	 * Popisky fotek
	 */
	@OneToMany
	private Set<PhotoDescription> photoDescriptions;

	/**
	 * Relativní cesta (od kořene fotogalerie) k adresáři s fotografiemi
	 */
	private String photogalleryPath;

	/**
	 * DB identifikátor
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	public Long getId() {
		return id;
	}

	public Photogallery() {
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContentNode getContentNode() {
		return contentNode;
	}

	public void setContentNode(ContentNode contentNode) {
		this.contentNode = contentNode;
	}

	public Set<PhotoDescription> getPhotoDescriptions() {
		return photoDescriptions;
	}

	public void setPhotoDescriptions(Set<PhotoDescription> photoDescriptions) {
		this.photoDescriptions = photoDescriptions;
	}

	public String getPhotogalleryPath() {
		return photogalleryPath;
	}

	public void setPhotogalleryPath(String photogalleryPath) {
		this.photogalleryPath = photogalleryPath;
	}

}
