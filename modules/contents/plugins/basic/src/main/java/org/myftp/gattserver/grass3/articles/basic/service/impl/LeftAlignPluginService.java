package org.myftp.gattserver.grass3.articles.basic.service.impl;

import org.myftp.gattserver.grass3.articles.basic.style.LeftAlignFactory;
import org.myftp.gattserver.grass3.articles.editor.api.EditorButtonResources;
import org.myftp.gattserver.grass3.articles.parser.interfaces.IPluginFactory;
import org.myftp.gattserver.grass3.articles.service.IPluginService;

public class LeftAlignPluginService implements IPluginService {

	private LeftAlignFactory factory = new LeftAlignFactory();

	public IPluginFactory getPluginFactory() {
		return factory;
	}

	public EditorButtonResources getEditorButtonResources() {
		return factory.getEditorButtonResources();
	}

}
