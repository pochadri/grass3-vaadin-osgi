package org.myftp.gattserver.grass3.search;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.myftp.gattserver.grass3.search.service.SearchHit;
import org.myftp.gattserver.grass3.windows.template.OneColumnWindow;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SearchWindow extends OneColumnWindow {

	private static final long serialVersionUID = -7063551976547889914L;

	public static final String NAME = "search";

	private SearchFacade searchFacade = SearchFacade.INSTANCE;

	public SearchWindow() {
		setName(NAME);
	}

	@Override
	protected void createContent(VerticalLayout layout) {

		layout.setMargin(true);
		layout.setSpacing(true);

		// TODO pokud je null
		Set<String> moduleIds = searchFacade.getSearchModulesIds();

		HorizontalLayout searchLayout = new HorizontalLayout();
		layout.addComponent(searchLayout);
		searchLayout.setWidth("100%");
		searchLayout.setSpacing(true);

		final VerticalLayout outputLayout = new VerticalLayout();
		layout.addComponent(outputLayout);

		final TextField searchField = new TextField();
		searchField.setWidth("100%");
		searchLayout.addComponent(searchField);
		searchLayout.setExpandRatio(searchField, 1);

		final ComboBox moduleCombo = new ComboBox();
		for (String moduleId : moduleIds) {
			moduleCombo.addItem(moduleId);
		}
		moduleCombo.setNullSelectionAllowed(false);
		moduleCombo.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		moduleCombo.setImmediate(true);
		searchLayout.addComponent(moduleCombo);

		Button searchButton = new Button("Hledat", new Button.ClickListener() {

			private static final long serialVersionUID = -9210575562255933575L;

			public void buttonClick(ClickEvent event) {

				String searchText = (String) searchField.getValue();
				try {
					List<SearchHit> hits = searchFacade.search(searchText,
							null, (String) moduleCombo.getValue(),
							getApplication().getUser(), SearchWindow.this);
					outputLayout.removeAllComponents();
					for (SearchHit hit : hits) {
						String link = hit.getContentLink();
						outputLayout.addComponent(new Link(link,
								new ExternalResource(link)));
						outputLayout.addComponent(new Label(hit
								.getHitFieldText(), Label.CONTENT_XHTML));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (InvalidTokenOffsetsException e) {
					e.printStackTrace();
				}

			}
		});
		searchLayout.addComponent(searchButton);

	}

}