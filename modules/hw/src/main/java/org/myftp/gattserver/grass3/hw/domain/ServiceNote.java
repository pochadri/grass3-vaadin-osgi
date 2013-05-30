package org.myftp.gattserver.grass3.hw.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.myftp.gattserver.grass3.hw.dto.HWItemState;

/**
 * Údaj o opravě, změně součástí apod.
 */
@Entity
@Table(name = "SERVICE_NOTE")
public class ServiceNote {

	/**
	 * Identifikátor změny
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * Datum události
	 */
	private Date date;

	/**
	 * Popis změny
	 */
	@Column(columnDefinition = "TEXT")
	private String description;

	/**
	 * Stav do kterého byl HW převeden v souvislosti s popisovanou událostí
	 */
	private HWItemState state;

	/**
	 * Součásti
	 */
	private String usage;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HWItemState getState() {
		return state;
	}

	public void setState(HWItemState state) {
		this.state = state;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

}
