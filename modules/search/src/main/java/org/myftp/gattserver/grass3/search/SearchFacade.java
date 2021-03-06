package org.myftp.gattserver.grass3.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cz.CzechAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.myftp.gattserver.grass3.model.dto.UserInfoDTO;
import org.myftp.gattserver.grass3.search.service.ISearchConnector;
import org.myftp.gattserver.grass3.search.service.ISearchField;
import org.myftp.gattserver.grass3.search.service.SearchEntity;
import org.myftp.gattserver.grass3.search.service.SearchHit;
import org.myftp.gattserver.grass3.windows.template.GrassWindow;

import com.vaadin.terminal.ExternalResource;

public enum SearchFacade {

	INSTANCE;

	public Set<String> getSearchModulesIds() {
		ConnectorAggregator aggregator = ConnectorAggregator.getInstance();
		return aggregator.getSearchConnectorsById().keySet();
	}

	private String getHighlightedField(Query query, Analyzer analyzer,
			String fieldName, String fieldValue) throws IOException,
			InvalidTokenOffsetsException {
		Formatter formatter = new SimpleHTMLFormatter("<strong>", "</strong>");
		QueryScorer queryScorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, queryScorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(queryScorer,
				Integer.MAX_VALUE));
		highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
		return highlighter.getBestFragment(analyzer, fieldName, fieldValue);
	}

	// private String getMatchedFieldName(Explanation explanation) {
	// final String PREFIX = "\\(MATCH\\) weight\\(";
	// final int PREFIX_STR_LENGTH = PREFIX.length() - 3;
	//
	// Pattern pattern = Pattern.compile(PREFIX + "[^:]+:");
	// Matcher matcher = pattern.matcher(explanation.toString());
	//
	// if (matcher.find()) {
	// String output = matcher.group();
	// return output.substring(PREFIX_STR_LENGTH, output.length() - 1);
	// } else {
	// return "";
	// }
	// }

	/**
	 * Search funkce
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 */
	public List<SearchHit> search(String queryText,
			Set<Enum<? extends ISearchField>> searchFields, String moduleId,
			UserInfoDTO user, GrassWindow grassWindow) throws IOException,
			ParseException, InvalidTokenOffsetsException {

		// StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		CzechAnalyzer analyzer = new CzechAnalyzer(Version.LUCENE_36);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,
				analyzer);

		/**
		 * Tady by šlo asi rozšiřovat i existující index (z disku/DB)
		 */
		Directory index = new RAMDirectory();
		IndexWriter w = new IndexWriter(index, config);

		/**
		 * Hledej dle search connectoru
		 */
		ConnectorAggregator aggregator = ConnectorAggregator.getInstance();
		ISearchConnector connector = aggregator.getSearchConnectorsById().get(
				moduleId);

		/**
		 * Pokud nebyly vybrány explicitně položky k prohledávání, prohledáváme
		 * všechny
		 */
		if (searchFields == null || searchFields.isEmpty())
			searchFields = new HashSet<Enum<? extends ISearchField>>(
					Arrays.asList(connector.getSearchFields()));

		/**
		 * Získej dostupné obsahy
		 */
		List<SearchEntity> searchEntities = connector
				.getAvailableSearchEntities(user);

		/**
		 * Projdi všechny dostupné obsahy
		 */
		for (SearchEntity searchEntity : searchEntities) {
			Document doc = new Document();

			// sestav dokument z nabízených polí
			for (SearchEntity.Field field : searchEntity.getFields()) {
				doc.add(new Field(((ISearchField) field.getName())
						.getFieldName(), field.getContent(), Field.Store.YES,
						field.isTokenized() ? Index.ANALYZED : Index.NO));
			}

			String url = new ExternalResource(grassWindow.getWindow(
					searchEntity.getLink().getViewerClass()).getURL()
					+ searchEntity.getLink().getSuffix()).getURL();

			// přidej link
			doc.add(new Field(connector.getLinkFieldName(), url,
					Field.Store.YES, Index.NO));

			w.addDocument(doc);
		}

		// zavři index
		w.close();

		/**
		 * Query
		 */
		List<String> queries = new ArrayList<String>();
		List<String> fieldNames = new ArrayList<String>();
		for (Enum<? extends ISearchField> searchField : searchFields) {
			queries.add(queryText);
			fieldNames.add(((ISearchField) searchField).getFieldName());
		}
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_36,
				queries.toArray(new String[0]),
				fieldNames.toArray(new String[0]), analyzer);

		/**
		 * Search
		 */
		int hitsPerPage = 100;
		IndexReader reader = IndexReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		List<SearchHit> hitList = new ArrayList<SearchHit>();
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);

			String highlight = "";
			String fieldName = "";
			for (Enum<? extends ISearchField> searchField : searchFields) {
				fieldName = ((ISearchField) searchField).getFieldName();
				highlight = getHighlightedField(query, analyzer, fieldName,
						d.get(fieldName));
				// je co zvýrazňovat
				if (highlight != null && highlight.isEmpty() == false) {
					hitList.add(new SearchHit(highlight, fieldName, d
							.get(connector.getLinkFieldName())));
				}
			}

		}

		searcher.close();

		return hitList;

	}
}
