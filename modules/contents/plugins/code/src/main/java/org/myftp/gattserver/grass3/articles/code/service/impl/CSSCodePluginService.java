package org.myftp.gattserver.grass3.articles.code.service.impl;

import org.myftp.gattserver.grass3.articles.code.CodeFactory;
import org.myftp.gattserver.grass3.articles.editor.api.EditorButtonResources;
import org.myftp.gattserver.grass3.articles.parser.interfaces.IPluginFactory;
import org.myftp.gattserver.grass3.articles.service.IPluginService;

public class CSSCodePluginService implements IPluginService {

	private CodeFactory factory = new CodeFactory("CSS","sh_css");

	public IPluginFactory getPluginFactory() {
		return factory;
	}

	public EditorButtonResources getEditorButtonResources() {
		return factory.getEditorButtonResources();
	}

}