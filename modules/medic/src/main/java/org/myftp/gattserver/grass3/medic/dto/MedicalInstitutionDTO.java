package org.myftp.gattserver.grass3.medic.dto;

import javax.validation.constraints.NotNull;

public class MedicalInstitutionDTO {

	private Long id;

	/**
	 * Jm�no institutu
	 */
	@NotNull
	private String name;

	/**
	 * Adresa
	 */
	@NotNull
	private String address;

	/**
	 * Otev�rac� hodiny
	 */
	@NotNull
	private String hours;

	/**
	 * Webov� str�nky
	 */
	private String web;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}
