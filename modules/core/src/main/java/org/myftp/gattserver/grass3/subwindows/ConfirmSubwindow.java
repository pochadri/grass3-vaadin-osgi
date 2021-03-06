package org.myftp.gattserver.grass3.subwindows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public abstract class ConfirmSubwindow extends GrassSubWindow {

	private static final long serialVersionUID = 4123506060675738841L;

	/**
	 * Nejobecnější dotaz na potvrzení operace
	 */
	public ConfirmSubwindow() {
		this("Opravdu si přejete provést tuto operaci ?");
	}

	/**
	 * Okno bude vytvořeno s popiskem, ve kterém bude předaný text
	 * 
	 * @param labelCaption
	 *            text popisku okna
	 */
	public ConfirmSubwindow(String labelCaption) {
		this(new Label(labelCaption));
	}

	/**
	 * Okno bude vytvořeno přímo s připraveným popiskem
	 * 
	 * @param label
	 *            popisek okna
	 */
	public ConfirmSubwindow(Label label) {
		super("Potvrzení operace");

		center();
		setWidth("220px");

		GridLayout subWindowlayout = new GridLayout(2, 2);
		setContent(subWindowlayout);
		subWindowlayout.setMargin(true);
		subWindowlayout.setSpacing(true);
		subWindowlayout.setSizeFull();

		subWindowlayout.addComponent(label, 0, 0, 1, 0);

		Button confirm = new Button("Ano", new Button.ClickListener() {

			private static final long serialVersionUID = 8490964871266821307L;

			public void buttonClick(ClickEvent event) {
				onConfirm(event);
				getParent().removeWindow(ConfirmSubwindow.this);
			}
		});

		subWindowlayout.addComponent(confirm, 0, 1);
		subWindowlayout.setComponentAlignment(confirm, Alignment.MIDDLE_CENTER);

		Button close = new Button("Ne", new Button.ClickListener() {

			private static final long serialVersionUID = 8490964871266821307L;

			public void buttonClick(ClickEvent event) {
				onClose(event);
				getParent().removeWindow(ConfirmSubwindow.this);
			}
		});

		subWindowlayout.addComponent(close, 1, 1);
		subWindowlayout.setComponentAlignment(close, Alignment.MIDDLE_CENTER);

		// Zaměř se na nové okno
		focus();

	}

	protected abstract void onConfirm(ClickEvent event);

	// default je prostě, že se nic nestane, takže ho nenutím jako abstraktní
	// metodu potomkovi k implementaci
	protected void onClose(ClickEvent event) {
	}

}
