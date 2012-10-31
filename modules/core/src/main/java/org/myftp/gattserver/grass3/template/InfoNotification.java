package org.myftp.gattserver.grass3.template;

import com.vaadin.ui.Window.Notification;

public class InfoNotification extends GrassNotification {

	private static final long serialVersionUID = 7344091799441719094L;

	public InfoNotification(String caption) {
		super(caption, Notification.TYPE_HUMANIZED_MESSAGE);
	}

}