package org.myftp.gattserver.grass3.security;

import javax.xml.bind.JAXBException;

import org.myftp.gattserver.grass3.config.ConfigurationUtils;
import org.myftp.gattserver.grass3.config.CoreConfiguration;
import org.myftp.gattserver.grass3.model.dto.ContentNodeDTO;
import org.myftp.gattserver.grass3.model.dto.UserInfoDTO;
import org.myftp.gattserver.grass3.service.ISectionService;

/**
 * Access control list, bere uživatele a operaci a vyhodnocuje, zda povolit nebo
 * zablokovat
 * 
 * @author gatt
 * 
 */
public final class CoreACL {

	private UserInfoDTO user;

	private CoreACL(UserInfoDTO user) {
		this.user = user;
	}

	/**
	 * ACL musí pracovat s nějakým uživatelem, proto je praktičtější ho předat
	 * rovnou v konstruktoru než aby se předával do každé metody jako parametr
	 * 
	 * @param user
	 *            uživatel nebo {@code null} pokud není v systému nikdo
	 *            přihlášen
	 */
	public static CoreACL get(UserInfoDTO user) {
		return new CoreACL(user);
	}

	/**
	 * =======================================================================
	 * Sekce
	 * =======================================================================
	 */

	/**
	 * Může uživatel zobrazit danou sekci ?
	 */
	public boolean canShowSection(ISectionService section) {
		
		// záleží na viditelnosti definované sekcí
		return section.isVisibleForRoles(user == null ? null : user.getRoles());
	}

	/**
	 * Může uživatel upravovat "hlášky"
	 */
	public boolean canModifyQuotes() {
		
		// pokud je uživatel přihlášen a je to administrátor
		return user != null && user.getRoles().contains(Role.ADMIN);
	}

	/**
	 * =======================================================================
	 * Obsahy
	 * =======================================================================
	 */

	/**
	 * Může uživatel zobrazit daný obsah ?
	 */
	public boolean canShowContent(ContentNodeDTO content) {

		if (user == null) {

			// pokud je obsah publikován, můžeš zobrazit
			if (content.getPublicated())
				return true;

		} else {

			// pokud je admin, může zobrazit kterýkoliv obsah
			if (user.getRoles().contains(Role.ADMIN))
				return true;

			// pokud jsi autor, můžeš zobrazit svůj obsah
			if (content.getAuthor().getId().equals(user.getId()))
				return true;
		}

		// jinak false
		return false;
	}

	/**
	 * Může uživatel vytvářet obsah ?
	 */
	public boolean canCreateContent() {

		if (user == null) {

			// host nemůže zakládat obsah
			return false;

		} else {

			// pokud má uživatel oprávnění AUTHOR, pak může
			if (user.getRoles().contains(Role.AUTHOR))
				return true;

		}

		return false;

	}

	/**
	 * Může uživatel upravit daný obsah ?
	 */
	public boolean canModifyContent(ContentNodeDTO content) {

		if (user == null) {

			// host nemůže upravovat obsah
			return false;

		} else {

			// pokud je admin, může upravit kterýkoliv obsah
			if (user.getRoles().contains(Role.ADMIN))
				return true;

			// pokud jsi autor, můžeš upravit svůj obsah
			if (content.getAuthor().getId().equals(user.getId()))
				return true;
		}

		// jinak false
		return false;
	}

	/**
	 * Může uživatel smazat daný obsah ?
	 */
	public boolean canDeleteContent(ContentNodeDTO content) {
		return canModifyContent(content);
	}

	/**
	 * =======================================================================
	 * Kategorie
	 * =======================================================================
	 */

	/**
	 * Může uživatel založit kategorii ?
	 */
	public boolean canCreateCategory() {

		if (user == null) {

			// host nemůže
			return false;

		} else {

			// pokud je admin, můžeš
			if (user.getRoles().contains(Role.ADMIN))
				return true;

		}

		// jinak false
		return false;
	}

	/**
	 * Může uživatel upravit kategorii ?
	 */
	public boolean canModifyCategory() {
		return canCreateCategory();
	}

	/**
	 * Může uživatel přesunout kategorii ?
	 */
	public boolean canMoveCategory() {
		return canModifyCategory();
	}

	/**
	 * Může uživatel smazat kategorii ?
	 */
	public boolean canDeleteCategory() {
		return canModifyCategory();
	}

	/**
	 * =======================================================================
	 * Různé
	 * =======================================================================
	 */

	/**
	 * Může se uživatel přihlásit ?
	 */
	public boolean canLogin() {
		return user == null;
	}

	/**
	 * Může se uživatel odhlásit ?
	 */
	public boolean canLogout() {
		return user != null;
	}

	/**
	 * Může daný uživatel zobrazit detaily o uživateli X ?
	 */
	public boolean canShowUserDetails(UserInfoDTO anotherUser) {

		// TODO tohle je spíš otázka kódu, než oprávnění
		// nelze zobrazit detail od žádného uživatele
		if (anotherUser == null)
			return false;

		// host nemůže zobrazovat nic
		if (user == null)
			return false;

		// uživatel může vidět detaily o sobě
		if (user.getId().equals(anotherUser.getId()))
			return true;

		// administrator může vidět detaily od všech uživatelů
		if (user.getRoles().contains(Role.ADMIN))
			return true;

		return false;
	}

	/**
	 * Může se uživatel zaregistrovat ?
	 */
	public boolean canRegistrate() {

		if (user == null) {

			// jenom host se může registrovat
			try {
				CoreConfiguration configuration = new ConfigurationUtils<CoreConfiguration>(
						new CoreConfiguration(), CoreConfiguration.CONFIG_PATH)
						.loadExistingOrCreateNewConfiguration();

				return configuration.isRegistrations();

			} catch (JAXBException e) {
				e.printStackTrace();
				return false;
			}

		}
		// jinak false
		return false;
	}

}
