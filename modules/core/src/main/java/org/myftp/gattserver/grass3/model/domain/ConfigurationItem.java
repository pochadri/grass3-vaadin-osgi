package org.myftp.gattserver.grass3.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Položka konfigurace - moduly přes tuto entitu můžou ukládat svoje
 * konfigurační údaje do DB
 * 
 * @author Gattaka
 * 
 */
@Entity
public class ConfigurationItem {

	@Id
	private String name;
	
	@Column(columnDefinition = "CLOB")
	private String value;

	public ConfigurationItem() {
	}

	public ConfigurationItem(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigurationItem) {
			String objName = ((ConfigurationItem) obj).getName();
			if (objName == null) {
				if (name == null) {
					return true;
				} else {
					return false;
				}
			}
			return objName.equals(name);
		}
		return false;
	}
}
