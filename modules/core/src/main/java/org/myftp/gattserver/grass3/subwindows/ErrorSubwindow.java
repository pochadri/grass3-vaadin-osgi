package org.myftp.gattserver.grass3.subwindows;

import com.vaadin.terminal.ThemeResource;

public class ErrorSubwindow extends MessageSubwindow {

	private static final long serialVersionUID = -4793025663820815400L;

	public ErrorSubwindow(String labelCaption) {
		super("Problém", labelCaption, new ThemeResource("img/tags/stop_16.png"));
	}

}
