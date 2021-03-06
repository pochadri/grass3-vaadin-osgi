package org.myftp.gattserver.grass3.articles.parser.interfaces;

import org.myftp.gattserver.grass3.articles.parser.PluginBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gatt
 */
public abstract class AbstractParser {

	/**
	 * Projde článek a vytvoří ElementTree
	 * 
	 * @param pluginBag
	 *            objekt s daty, která je předáván mezi parser pluginy
	 * @return ElementTree AST strom pro finální generování výsledného článku
	 */
	public abstract AbstractElementTree parse(PluginBag pluginBag);

	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void log(String log) {
		logger.info(log);
	}

}
