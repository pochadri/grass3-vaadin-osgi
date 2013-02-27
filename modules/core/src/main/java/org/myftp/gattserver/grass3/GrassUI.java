package org.myftp.gattserver.grass3;

import java.util.Map;

import org.myftp.gattserver.grass3.facades.SecurityFacade;
import org.myftp.gattserver.grass3.model.domain.User;
import org.myftp.gattserver.grass3.model.dto.UserInfoDTO;
import org.myftp.gattserver.grass3.util.GrassRequest;
import org.myftp.gattserver.grass3.util.Mapper;
import org.myftp.gattserver.grass3.util.URLPathAnalyzer;
import org.myftp.gattserver.grass3.windows.HomePage;
import org.myftp.gattserver.grass3.windows.template.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Title("Gattserver")
@Theme("grass")
public class GrassUI extends UI {

	private static final long serialVersionUID = -785347532002801786L;
	private static Logger logger = LoggerFactory.getLogger(GrassUI.class);

	/**
	 * Fasády
	 */
	private SecurityFacade securityFacade = SecurityFacade.INSTANCE;
	private Mapper mapper = Mapper.INSTANCE;

	/**
	 * Mapa stránek
	 */
	private Map<String, PageFactory> factoriesMap = PageFactory
			.getRegistredFactories();

	/**
	 * Auth
	 */
	private UserInfoDTO user = null;

	public GrassUI() {
		PageFactory.setHomepageFactory(HomePage.FACTORY);
	}

	/**
	 * Authentikační metoda pro aplikaci
	 * 
	 * @param username
	 *            jméno uživatele, který se přihlašuje
	 * @param password
	 *            heslo, které použil
	 * @return true pokud se přihlášení zdařilo, jinak false
	 */
	public boolean login(String username, String password) {

		User loggedUser = securityFacade.authenticate(username, password);
		if (loggedUser != null) {
			user = mapper.map(loggedUser);
			return true;
		}

		return false;
	}

	/**
	 * Odhlášení
	 */
	public void logout() {
		this.user = null;
	}

	/**
	 * Získá aktuálního přihlášeného uživatele jako {@link UserInfoDTO} objekt
	 */
	public UserInfoDTO getUser() {
		return user;
	}

	@Override
	public void init(VaadinRequest request) {

		String path = request.getPathInfo();
		logger.info("Path: [" + path + "]");

		GrassRequest grassRequest = new GrassRequest(request);
		URLPathAnalyzer analyzer = grassRequest.getAnalyzer();

		PageFactory factory = factoriesMap.get(analyzer.getPathToken(0));
		setContent(factory.createPage(grassRequest));

	}

}