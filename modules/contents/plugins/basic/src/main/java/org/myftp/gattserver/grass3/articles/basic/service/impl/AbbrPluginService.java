package org.myftp.gattserver.grass3.articles.basic.service.impl;

import org.myftp.gattserver.grass3.articles.basic.abbr.AbbrFactory;
import org.myftp.gattserver.grass3.articles.editor.api.EditorButtonResources;
import org.myftp.gattserver.grass3.articles.parser.interfaces.IPluginFactory;
import org.myftp.gattserver.grass3.articles.service.IPluginService;

public class AbbrPluginService implements IPluginService {

	private AbbrFactory factory = new AbbrFactory();

	public IPluginFactory getPluginFactory() {
		return factory;
	}

	public EditorButtonResources getEditorButtonResources() {
		return factory.getEditorButtonResources();
	}

}
