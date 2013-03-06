package org.myftp.gattserver.grass3.pages.factories.template;

import javax.annotation.PostConstruct;

import org.myftp.gattserver.grass3.pages.template.GrassLayout;
import org.myftp.gattserver.grass3.util.GrassRequest;

import com.vaadin.ui.Component;

public abstract class SettingsTab extends GrassLayout {

	private static final long serialVersionUID = 1135599178354473520L;

	public SettingsTab(GrassRequest request) {
		super("settings", request);
	}

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		addComponent(createContent(), "content");
	}

	protected abstract Component createContent();

}