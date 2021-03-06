package org.myftp.gattserver.grass3.service.impl;

import java.util.Set;

import org.myftp.gattserver.grass3.security.Role;
import org.myftp.gattserver.grass3.service.ISettingsService;
import org.myftp.gattserver.grass3.windows.UserSettingsWindow;
import org.myftp.gattserver.grass3.windows.template.SettingsWindow;

public class UserSettingsService implements ISettingsService {

	public SettingsWindow getSettingsWindowNewInstance() {
		return new UserSettingsWindow();
	}

	public Class<? extends SettingsWindow> getSettingsWindowClass() {
		return UserSettingsWindow.class;
	}

	public String getSettingsCaption() {
		return "Uživatelé";
	}

	public boolean isVisibleForRoles(Set<Role> roles) {
		return roles.contains(Role.ADMIN);
	}

}
