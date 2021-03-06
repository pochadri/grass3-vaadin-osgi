package org.myftp.gattserver.grass3.articles.basic.image;

import org.myftp.gattserver.grass3.articles.parser.interfaces.AbstractElementTree;
import org.myftp.gattserver.grass3.articles.parser.interfaces.IContext;

public class ImageTree extends AbstractElementTree {

	private String link;

	public ImageTree(String link) {
		this.link = link;
	}

	@Override
	public void generateElement(IContext ctx) {
		ctx.print("<img src=\"" + link + "\" alt=\"" + link + "\" />");
	}

}
