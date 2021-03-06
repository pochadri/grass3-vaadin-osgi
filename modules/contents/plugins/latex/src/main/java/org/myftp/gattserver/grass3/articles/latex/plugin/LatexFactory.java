package org.myftp.gattserver.grass3.articles.latex.plugin;

import org.myftp.gattserver.grass3.articles.editor.api.EditorButtonResources;
import org.myftp.gattserver.grass3.articles.parser.interfaces.AbstractParserPlugin;
import org.myftp.gattserver.grass3.articles.parser.interfaces.IPluginFactory;

/**
 * 
 * @author gatt
 */
public class LatexFactory implements IPluginFactory {

	private final String tag = "TEX";

	public String getTag() {
		return tag;
	}

	public AbstractParserPlugin getPluginParser() {
		return new LatexElement(tag);
	}

	public EditorButtonResources getEditorButtonResources() {
		EditorButtonResources resources = new EditorButtonResources(tag);
		resources.setTagFamily("LaTeX");
		return resources;
	}

}
