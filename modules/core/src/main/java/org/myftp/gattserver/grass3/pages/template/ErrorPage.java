package org.myftp.gattserver.grass3.pages.template;

import org.myftp.gattserver.grass3.util.GrassRequest;

import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public abstract class ErrorPage extends AbstractGrassPage {

	private static final long serialVersionUID = 3728073040878360420L;

	public ErrorPage(GrassRequest request) {
		super(request);
	}

	@Override
	protected void createQuotes(CustomLayout layout) {
	}

	@Override
	protected void createContent(CustomLayout layout) {

		CustomLayout contentLayout = new CustomLayout("oneColumn");
		layout.addComponent(contentLayout, "content");

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);
		horizontalLayout.setWidth("100%");
		contentLayout.addComponent(horizontalLayout, "content");

		Label label = new Label(getErrorText());
		label.addStyleName("error-label");
		Embedded img = new Embedded(null, getErrorImage());

		horizontalLayout.addComponent(img);
		horizontalLayout.addComponent(label);
		horizontalLayout.setComponentAlignment(img, Alignment.MIDDLE_LEFT);
		horizontalLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}

	@Override
	protected void createMenu(CustomLayout layout) {
	}

	protected abstract String getErrorText();

	protected abstract Resource getErrorImage();

}
