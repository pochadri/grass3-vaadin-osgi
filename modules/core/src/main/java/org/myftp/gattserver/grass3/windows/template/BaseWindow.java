package org.myftp.gattserver.grass3.windows.template;

import java.util.List;

import org.myftp.gattserver.grass3.ServiceHolder;
import org.myftp.gattserver.grass3.facades.NodeFacade;
import org.myftp.gattserver.grass3.facades.QuotesFacade;
import org.myftp.gattserver.grass3.model.dto.NodeDTO;
import org.myftp.gattserver.grass3.model.dto.UserInfoDTO;
import org.myftp.gattserver.grass3.security.Role;
import org.myftp.gattserver.grass3.service.ISectionService;
import org.myftp.gattserver.grass3.subwindows.GrassSubWindow;
import org.myftp.gattserver.grass3.windows.CategoryWindow;
import org.myftp.gattserver.grass3.windows.HomeWindow;
import org.myftp.gattserver.grass3.windows.LoginWindow;
import org.myftp.gattserver.grass3.windows.QuotesWindow;
import org.myftp.gattserver.grass3.windows.RegistrationWindow;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public abstract class BaseWindow extends BackgroundWindow {

	private static final long serialVersionUID = 2474374292329895766L;

	private QuotesFacade quotesFacade = QuotesFacade.INSTANCE;
	private NodeFacade nodeFacade = NodeFacade.INSTANCE;

	private HorizontalLayout sectionsMenuLayout = new HorizontalLayout();
	private HorizontalLayout userMenuLayout = new HorizontalLayout();
	private Link quotes;

	@Override
	protected void onShow() {

		// update menu sekcí
		populateSectionsMenu();

		// update menu uživatele
		populateUserMenu();

		// update hlášek
		quotes.setCaption(chooseQuote());

		// jQuery
		StringBuilder loadScript = new StringBuilder();
		loadScript
				.append("var head= document.getElementsByTagName('head')[0];")
				.append("var script= document.createElement('script');")
				.append("script.type= 'text/javascript';")
				.append("script.src= '/VAADIN/themes/grass/js/jquery.js';")
				.append("head.appendChild(script);");
		executeJavaScript(loadScript.toString());

		// grassJS
		loadScript = new StringBuilder();
		loadScript
				.append("var head= document.getElementsByTagName('head')[0];")
				.append("var script= document.createElement('script');")
				.append("script.type= 'text/javascript';")
				.append("script.src= '/VAADIN/themes/grass/js/grass.js';")
				.append("head.appendChild(script);");
		executeJavaScript(loadScript.toString());
	}

	private void populateSectionsMenu() {
		sectionsMenuLayout.removeAllComponents();

		// link na domovskou stránku
		createHomeLink();

		// sekce článků je rozbalená rovnou jako její kořenové kategorie
		List<NodeDTO> nodes = nodeFacade.getRootNodes();
		for (NodeDTO node : nodes) {
			createCategoryLink(node.getName(),
					node.getId() + "-" + node.getName());
		}

		// externí sekce
		for (ISectionService section : ServiceHolder.getInstance()
				.getSectionServices()) {
			createSectionLink(section.getSectionCaption(),
					section.getSectionWindowClass());
		}
	}

	private void populateUserMenu() {
		userMenuLayout.removeAllComponents();

		final UserInfoDTO userInfoDTO = getApplication().getUser();
		if (userInfoDTO == null) {
			Link link = new Link("Přihlášení",
					getWindowResource(LoginWindow.class));
			link.setStyleName("menu_item");
			userMenuLayout.addComponent(link);

			// Registrovat
			link = new Link("Registrace",
					getWindowResource(RegistrationWindow.class));
			link.setStyleName("menu_item");
			userMenuLayout.addComponent(link);

		} else {

			Button userDetails = new Button(userInfoDTO.getName(),
					new Button.ClickListener() {

						private static final long serialVersionUID = 4570994816815405844L;

						public void buttonClick(ClickEvent event) {
							final Window subwindow = new GrassSubWindow(
									"Detail uživatele " + userInfoDTO.getName());
							subwindow.center();
							addWindow(subwindow);
							subwindow.setWidth("220px");
							GridLayout gridLayout = new GridLayout(2, 4);
							gridLayout.setMargin(true);
							gridLayout.setSpacing(true);
							gridLayout.setSizeFull();
							subwindow.setContent(gridLayout);

							// Jméno
							gridLayout.addComponent(new Label("<h2>"
									+ userInfoDTO.getName() + "</h2>",
									Label.CONTENT_XHTML), 0, 0, 1, 0);

							// Admin ?
							gridLayout.addComponent(new Label("Admin"), 0, 1);
							gridLayout.addComponent(new Label(userInfoDTO
									.getRoles().contains(Role.ADMIN) ? "Ano"
									: "Ne"), 1, 1);

							// Friend ?
							gridLayout.addComponent(new Label("Friend"), 0, 2);
							gridLayout.addComponent(new Label(userInfoDTO
									.getRoles().contains(Role.FRIEND) ? "Ano"
									: "Ne"), 1, 2);

							// Author ?
							gridLayout.addComponent(new Label("Author"), 0, 3);
							gridLayout.addComponent(new Label(userInfoDTO
									.getRoles().contains(Role.AUTHOR) ? "Ano"
									: "Ne"), 1, 3);

							subwindow.focus();
						}
					});

			userDetails.setStyleName("user_status");
			userDetails.addStyleName("menu_item");
			userDetails.addStyleName(BaseTheme.BUTTON_LINK);
			userMenuLayout.addComponent(userDetails);

			// separator
			Label separator = new Label("|");
			separator.setStyleName("menu_item");
			userMenuLayout.addComponent(separator);

			// nastavení
			Link link = new Link("Nastavení",
					getWindowResource(SettingsWindow.class));
			link.setStyleName("menu_item");
			userMenuLayout.addComponent(link);

			// odhlásit
			Button button = new Button("Odhlásit", new Button.ClickListener() {

				private static final long serialVersionUID = 4570994816815405844L;

				public void buttonClick(ClickEvent event) {
					getApplication().close();
				}
			});
			button.setStyleName(BaseTheme.BUTTON_LINK);
			button.addStyleName("menu_item");
			userMenuLayout.addComponent(button);

		}

	}

	private String chooseQuote() {
		String quote = quotesFacade.getRandomQuote();
		if (quote == null) {
			showError500();
		}
		return quote;
	}

	protected void buildLayout(VerticalLayout layout) {

		// vytvoří tělo, které je sesypané směrem nahoru
		createBody(layout);

		// vytvoření patičku, která je sesypána dolů
		createFooter(layout);

	}

	private void createBody(VerticalLayout layout) {

		AbsoluteLayout bodyLayout = new AbsoluteLayout();
		layout.addComponent(bodyLayout);
		layout.setComponentAlignment(bodyLayout, Alignment.TOP_CENTER);

		// DŮLEŽITÉ - bez tohohle bude footer užírat svým top padding skoro
		// půlku stránky, čímž "ukousne" i existující část body-části
		layout.setExpandRatio(bodyLayout, 1.0f);

		bodyLayout.setStyleName("body_layout");
		bodyLayout.setWidth("990px");
		bodyLayout.setHeight("100%");

		createTop(bodyLayout);
		createMenu(bodyLayout);

		// obsah stránky
		createWindowContent(bodyLayout);

	}

	private void createFooter(VerticalLayout layout) {
		HorizontalLayout footerLayout = new HorizontalLayout();
		layout.addComponent(footerLayout);
		// layout.setComponentAlignment(footerLayout, Alignment.BOTTOM_CENTER);

		footerLayout.setWidth("100%");
		footerLayout.setHeight("25px");
		footerLayout.setStyleName("footer_layout");

		Label footerNote = new Label("GRASS3 Copyright Hynek Uhlíř 2012");
		footerLayout.addComponent(footerNote);

		HorizontalLayout footerShadow = new HorizontalLayout();
		layout.addComponent(footerShadow);
		layout.setComponentAlignment(footerShadow, Alignment.BOTTOM_CENTER);
		footerShadow.setStyleName("footer_shadow");
		footerShadow.setWidth("100%");
		footerShadow.setHeight("11px");
	}

	private void createTop(AbsoluteLayout layout) {

		HorizontalLayout topLayout = new HorizontalLayout();
		layout.addComponent(topLayout, "top: 0px; left: 0px");
		topLayout.setWidth("990px");
		topLayout.setHeight("94px");

		// logo (image)
		Embedded logoImage = new Embedded("", new ThemeResource("img/logo.png"));
		topLayout.addComponent(logoImage);
		logoImage.setAlternateText("Gattserver");
		logoImage.setStyleName("logo_image");

		// Hlášky - generují se znova a znova
		quotes = new Link(chooseQuote(), getWindowResource(QuotesWindow.class));
		quotes.setStyleName("quotes");
		quotes.setWidth("740px");
		topLayout.addComponent(quotes);
	}

	private void createMenu(AbsoluteLayout layout) {

		// menu (centrovací element)
		HorizontalLayout menuHolderLayout = new HorizontalLayout();
		layout.addComponent(menuHolderLayout, "top: 81px; left: 0px");
		menuHolderLayout.setStyleName("menu_holder");
		menuHolderLayout.setWidth("990px");
		menuHolderLayout.setHeight("41px");
		menuHolderLayout.setMargin(false, true, false, true);

		// sekce menu
		createSectionsMenu(menuHolderLayout);

		// user menu
		createUserMenu(menuHolderLayout);
	}

	private void createHomeLink() {
		Link link = new Link("Domů", getWindowResource(HomeWindow.class));
		link.setStyleName("first_menu_item");
		sectionsMenuLayout.addComponent(link);
	}

	private void createSectionsMenu(HorizontalLayout layout) {
		layout.addComponent(sectionsMenuLayout);
		layout.setComponentAlignment(sectionsMenuLayout, Alignment.MIDDLE_LEFT);
		sectionsMenuLayout.setStyleName("sections_menu_layout");

		// Domů
		createHomeLink();
	}

	private void createSectionLink(String caption,
			Class<? extends GrassWindow> windowClass) {
		Link link = new Link(caption, getWindowResource(windowClass));
		link.setStyleName("menu_item");
		sectionsMenuLayout.addComponent(link);
	}

	private void createCategoryLink(String caption, String URL) {
		Link link = new Link(caption, new ExternalResource(getWindow(
				CategoryWindow.class).getURL()
				+ URL));
		link.setStyleName("menu_item");
		sectionsMenuLayout.addComponent(link);
	}

	private void createUserMenu(HorizontalLayout layout) {
		layout.addComponent(userMenuLayout);
		layout.setComponentAlignment(userMenuLayout, Alignment.MIDDLE_RIGHT);
		userMenuLayout.setStyleName("user_menu_layout");
	}

	/**
	 * Vytvoří obsah okna - tedy všechno to, co je mezi menu a footerem
	 * 
	 * @param layout
	 *            layout, do kterého se má vytvářet
	 */
	protected abstract void createWindowContent(AbsoluteLayout layout);

}
