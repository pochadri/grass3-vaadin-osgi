package org.myftp.gattserver.grass3.pages.template;

import org.myftp.gattserver.grass3.GrassUI;
import org.myftp.gattserver.grass3.pages.factories.template.PageFactory;
import org.myftp.gattserver.grass3.security.CoreACL;
import org.myftp.gattserver.grass3.subwindows.ErrorSubwindow;
import org.myftp.gattserver.grass3.subwindows.InfoSubwindow;
import org.myftp.gattserver.grass3.subwindows.WarnSubwindow;
import org.myftp.gattserver.grass3.util.GrassRequest;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

public abstract class GrassLayout extends CustomLayout {

	private static final long serialVersionUID = 604170960797872356L;

	private GrassRequest request;

	public GrassLayout(String layoutName, GrassRequest request) {
		super(layoutName);
		this.request = request;
	}

	protected GrassRequest getRequest() {
		return request;
	}

	/**
	 * Získá URL stránky
	 */
	public String getPageURL(PageFactory pageFactory) {
		return request.getContextRoot() + "/" + pageFactory.getPageName();
	}

	/**
	 * Získá URL stránky + relativní URL
	 */
	public String getPageURL(PageFactory pageFactory, String... relativeURLs) {
		if (relativeURLs.length == 1) {
			return getPageURL(pageFactory) + "/" + relativeURLs[0];
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(getPageURL(pageFactory));
			for (String relativeURL : relativeURLs) {
				if (relativeURL != null) {
					buffer.append("/");
					buffer.append(relativeURL);
				}
			}
			return buffer.toString();
		}
	}

	/**
	 * Získá resource dle stránky
	 */
	public ExternalResource getPageResource(PageFactory pageFactory) {
		return new ExternalResource(getPageURL(pageFactory));
	}

	/**
	 * Získá resource dle stránky + relativní URL
	 */
	public ExternalResource getPageResource(PageFactory pageFactory,
			String... relativeURLs) {
		return new ExternalResource(getPageURL(pageFactory, relativeURLs));
	}

	/**
	 * Získá ACL
	 */
	public CoreACL getUserACL() {
		return CoreACL.get(getGrassUI().getUser());
	}

	/**
	 * Získá aktuální UI jako {@link GrassUI}
	 */
	public GrassUI getGrassUI() {
		return (GrassUI) UI.getCurrent();
	}

	/**
	 * Přejde na stránku
	 */
	public void redirect(String uri) {
		Page.getCurrent().setLocation(uri);
	}

	/**
	 * Notifikace pomocí {@link InfoNotification}
	 */
	public void showInfo(String caption) {
		InfoSubwindow infoSubwindow = new InfoSubwindow(caption);
		getGrassUI().addWindow(infoSubwindow);
	}

	/**
	 * Notifikace varování pomocí {@link WarningNotification}
	 */
	public void showWarning(String caption) {
		WarnSubwindow warnSubwindow = new WarnSubwindow(caption);
		getGrassUI().addWindow(warnSubwindow);
	}

	/**
	 * Notifikace chyby pomocí {@link ErrorNotification}
	 */
	public void showError(String caption) {
		ErrorSubwindow errorSubwindow = new ErrorSubwindow(caption);
		getGrassUI().addWindow(errorSubwindow);
	}

	/**
	 * Vyhodí chybu
	 */
	public void showError500() {
		// TODO
	}

	/**
	 * Vyhodí chybu
	 */
	public void showError404() {
		// TODO
	}

}
