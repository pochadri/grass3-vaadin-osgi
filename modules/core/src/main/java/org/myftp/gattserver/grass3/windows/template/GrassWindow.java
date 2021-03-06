package org.myftp.gattserver.grass3.windows.template;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.myftp.gattserver.grass3.GrassApplication;
import org.myftp.gattserver.grass3.security.CoreACL;
import org.myftp.gattserver.grass3.subwindows.ErrorSubwindow;
import org.myftp.gattserver.grass3.subwindows.InfoSubwindow;
import org.myftp.gattserver.grass3.subwindows.WarnSubwindow;
import org.myftp.gattserver.grass3.windows.err.Err404;
import org.myftp.gattserver.grass3.windows.err.Err500;

import com.vaadin.Application;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Window;

/**
 * Kořenový předek všech oken, která lze zobrazit v Grass systému
 * 
 * @author gatt
 * 
 */
public abstract class GrassWindow extends Window {

	private static final long serialVersionUID = 8889472078008074552L;

	@Override
	public void setApplication(Application application) {
		super.setApplication(application);
		buildLayout();
	}

	/**
	 * Teprve tato metoda staví obsah okna - je jí potřeba mít takhle oddělenou,
	 * protože během stavby využívá dat jako referenci na instanci aplikace
	 * apod. Tyto informace jsou oknu dodány později (settery apod.), takže
	 * kdyby tato logika byla přímo v konstruktoru, vznikne problém ve velkém
	 * množství null pointer chyb apod.
	 * 
	 * Zároveň je tak možné počkat až budou zaregistrována všechny povinná okna
	 * do aplikace a teprve poté na nich na všech zavolat build, při kterém
	 * nebude ani problém s nacházením těchto oken v registru oken aplikace
	 */
	protected abstract void buildLayout();

	@Override
	public DownloadStream handleURI(URL context, String relativeUri) {
		onShow();
		return super.handleURI(context, relativeUri);
	}

	/**
	 * Vytvoří {@link CustomLayout} z layout HTML
	 * 
	 * @param filename
	 *            jméno HTML souboru layoutu. Pro layout 'base.html' je jméno
	 *            'base'
	 * @return vytvoření {@link CustomLayout} nebo {@code null}, pokud nebyl
	 *         layout nalezen
	 */
	public CustomLayout createLayoutFromFile(String filename) {
		InputStream layoutFile = getClass().getResourceAsStream(
				"/VAADIN/themes/grass/layouts/" + filename + ".html");
		CustomLayout layout = null;
		try {
			layout = new CustomLayout(layoutFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// CustomLayout layout = new CustomLayout(filename);

		return layout;
	}

	/**
	 * Získá ACL
	 */
	protected CoreACL getUserACL() {
		return CoreACL.get(getApplication().getUser());
	}

	/**
	 * Speciální metoda, která je volána při každém zobrazení okna, je tak
	 * pomocí této metody provádět refresh různých objektů v okně.
	 * 
	 * Metoda je volána původně z handleURI, takže definitivní přepsání
	 * handleURI bez volání předka způsobí nefunkčnost této metody
	 */
	protected abstract void onShow();

	public GrassApplication getApplication() {
		return (GrassApplication) super.getApplication();
	}

	protected void showError500() {
		open(getWindowResource(Err500.class));
	}

	protected void showError404() {
		open(getWindowResource(Err404.class));
	}

	/**
	 * Notifikace pomocí {@link InfoNotification}
	 */
	public void showInfo(String caption) {
		InfoSubwindow infoSubwindow = new InfoSubwindow(caption);
		addWindow(infoSubwindow);
	}

	/**
	 * Notifikace varování pomocí {@link WarningNotification}
	 */
	public void showWarning(String caption) {
		WarnSubwindow warnSubwindow = new WarnSubwindow(caption);
		addWindow(warnSubwindow);
	}

	/**
	 * Notifikace chyby pomocí {@link ErrorNotification}
	 */
	public void showError(String caption) {
		ErrorSubwindow errorSubwindow = new ErrorSubwindow(caption);
		addWindow(errorSubwindow);
	}

	public URL getWindowURL(URL appURL, String name) {
		URL url = null;
		try {
			url = new URL(appURL, name + "/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * Získá instanci okna dle jména
	 */
	public Window getWindow(String name) {
		return getApplication().getWindow(name);
	}

	/**
	 * Získá instanci okna dle třídy
	 */
	public Window getWindow(Class<? extends Window> windowClass) {
		return getApplication().getWindow(windowClass);
	}

	/**
	 * Získá resource okna dle jeho instance
	 */
	public ExternalResource getWindowResource(Window window) {
		if (window == null) {
			showError500();
			return null;
		} else
			return new ExternalResource(getWindowURL(getApplication().getURL(),
					window.getName()));
	}

	/**
	 * Získá resource okna dle jeho třídy
	 */
	public ExternalResource getWindowResource(
			Class<? extends GrassWindow> windowClass) {
		return getWindowResource(getWindow(windowClass));
	}

	/**
	 * Získá resource okna dle jeho jména
	 */
	public ExternalResource getWindowResource(String name) {
		return getWindowResource(getWindow(name));
	}

	/**
	 * Otevře okno dle jeho třídy
	 */
	protected void openWindow(Class<? extends GrassWindow> windowClass) {
		open(getWindowResource(windowClass));
	}

	/**
	 * Otevře okno dle instance
	 */
	protected void openWindow(Window window) {
		open(getWindowResource(window));
	}

	/**
	 * Otevře okno dle jeho jména
	 */
	protected void openWindow(String name) {
		open(getWindowResource(name));
	}

}
