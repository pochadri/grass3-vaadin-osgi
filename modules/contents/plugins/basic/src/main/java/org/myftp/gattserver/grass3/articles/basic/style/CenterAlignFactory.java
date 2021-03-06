package org.myftp.gattserver.grass3.articles.basic.style;

import java.util.List;

import org.myftp.gattserver.grass3.articles.editor.api.EditorButtonResources;
import org.myftp.gattserver.grass3.articles.parser.interfaces.AbstractElementTree;
import org.myftp.gattserver.grass3.articles.parser.interfaces.AbstractParserPlugin;

/**
 * 
 * @author gatt
 */
public class CenterAlignFactory extends StyleFactory {

	public final static String TAG = "ALGNCT";

	public CenterAlignFactory() {
		super(TAG);
	}

	public AbstractParserPlugin getPluginParser() {
		return new StyleElement(TAG) {

			@Override
			protected StyleTree getTree(List<AbstractElementTree> elist) {
				return new CenterAlignTree(elist);
			}
		};
	}

	public EditorButtonResources getEditorButtonResources() {
		EditorButtonResources resources = new EditorButtonResources(TAG);
		resources.setImageName("articles/basic/img/algnc_16.png");
		resources.setDescription("");
		resources.setTagFamily("Formátování");
		return resources;
	}

}
