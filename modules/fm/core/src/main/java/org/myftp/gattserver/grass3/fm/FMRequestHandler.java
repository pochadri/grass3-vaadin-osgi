package org.myftp.gattserver.grass3.fm;

import org.myftp.gattserver.grass3.SpringContextHelper;
import org.myftp.gattserver.grass3.config.IConfigurationService;
import org.myftp.gattserver.grass3.fm.config.FMConfiguration;
import org.myftp.gattserver.grass3.ui.util.AbstractFileRequestHandler;

// TODO připraveno na stahování/otevírání souborů

public class FMRequestHandler extends AbstractFileRequestHandler {

	private static final long serialVersionUID = 7154339775034959876L;

	public FMRequestHandler() {
		super(FMConfiguration.FM_PATH);
	}

	/**
	 * Zjistí dle aktuální konfigurace kořenový adresář
	 */
	@Override
	protected String getRootDir() {
		IConfigurationService configurationService = (IConfigurationService) SpringContextHelper
				.getBean("configurationService");
		FMConfiguration configuration = new FMConfiguration();
		configurationService.loadConfiguration(configuration);
		return configuration.getRootDir();
	}

}
