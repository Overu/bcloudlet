package org.cloudlet.web.core.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.mgwt.ui.client.MGWTStyle;

public class WebPlace extends Place {

	@Inject
	Provider<WebPlace> placeProvider;

	@Inject
	Provider<WebPlaceManager> placeManager;

	private String path;

	private String title;

	private WebPlace parent;

	private List<WebPlace> children = new ArrayList<WebPlace>();

	private WebPlace welcomePlace;

	private IsWidget widget;

	private ImageResource buttonImage;

	private String buttonText;

	private Provider<? extends IsWidget> widgetProvider;

	private AsyncProvider<? extends IsWidget> asyncWidgetProvider;

	private static final Logger logger = Logger.getLogger(WebPlace.class
			.getName());

	private Entry content;

	public void addChild(final WebPlace place) {
		children.add(place);
		place.parent = this;
	}

	public WebPlace findChild(final String uri) {
		String[] pair = uri.split("\\?");
		String viewType = pair.length > 1 ? pair[1] : null;
		String[] segments = pair[0].split("/");
		WebPlace result = this;
		for (String path : segments) {
			if (path.length() == 0) {
				continue;
			}
			result = result.getChild(path);
		}

		return result;
	}

	public ImageResource getButtonImage() {
		if (buttonImage == null) {
			buttonImage = MGWTStyle.getTheme().getMGWTClientBundle()
					.tabBarFavoritesImage();
		}
		return buttonImage;
	}

	public String getButtonText() {
		return buttonText;
	}

	public WebPlace getChild(final int index) {
		return children.get(index);
	}

	public WebPlace getChild(final String path) {
		for (WebPlace p : children) {
			if (path.equals(p.getPath())) {
				return p;
			}
		}
		return null;
	}

	public List<WebPlace> getChildren() {
		return children;
	}

	public Entry getContent() {
		return content;
	}

	public WebPlace getParent() {
		return parent;
	}

	public String getPath() {
		return path;
	}

	public String getTitle() {
		if (title == null) {
			return getPath();
		}
		return title;
	}

	public String getUri() {
		return getUriBuilder().toString();
	}

	public StringBuilder getUriBuilder() {
		if (parent == null) {
			return new StringBuilder();
		} else {
			StringBuilder builder = parent.getUriBuilder();
			if (path != null) {
				builder.append("/").append(path);
			}
			return builder;
		}
	}

	public WebPlace getWelcomePlace() {
		return welcomePlace;
	}

	public IsWidget getWidget() {
		return widget;
	}

	public boolean isChildOf(final WebPlace place) {
		if (place == this) {
			return true;
		} else if (parent != null) {
			return parent.isChildOf(place);
		} else {
			return false;

		}
	}

	public void render(final AcceptsOneWidget panel) {
		render(panel, null);
	}

	public void setButtonImage(final ImageResource buttonImage) {
		this.buttonImage = buttonImage;
	}

	public void setButtonText(final String buttonText) {
		this.buttonText = buttonText;
	}

	public void setContent(final Entry content) {
		this.content = content;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setWelcomePlace(final WebPlace place) {
		this.welcomePlace = place;
		addChild(place);
	}

	public void setWidget(
			final AsyncProvider<? extends IsWidget> asyncWidgetProvider) {
		this.asyncWidgetProvider = asyncWidgetProvider;
	}

	public void setWidget(final IsWidget widget) {
		this.widget = widget;
	}

	public void setWidget(final Provider<? extends IsWidget> provider) {
		this.widgetProvider = provider;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getTitle()).append(" (").append(getUri()).append(")");
		return builder.toString();
	}

	private void append(final AcceptsOneWidget panel,
			final AsyncCallback<AcceptsOneWidget> callback) {
		if (widget == null && widgetProvider == null
				&& asyncWidgetProvider == null) {
			Object view = null;
			if (view != null) {
				if (view instanceof AsyncProvider) {
					asyncWidgetProvider = (AsyncProvider<IsWidget>) view;
				} else if (view instanceof Provider) {
					widgetProvider = (Provider<IsWidget>) view;
				} else if (view instanceof IsWidget) {
					widget = (IsWidget) view;
				}
			}
		}
		if (widget == null && widgetProvider == null
				&& asyncWidgetProvider == null) {
			widget = new SimplePanel();
		}
		if (widget != null) {
			showWidget(panel, callback);
		} else if (widgetProvider != null) {
			widget = widgetProvider.get();
			showWidget(panel, callback);
		} else if (asyncWidgetProvider != null) {
			asyncWidgetProvider.get(new AsyncCallback<IsWidget>() {
				@Override
				public void onFailure(final Throwable caught) {
					String msg = "Network connection cloud be lost. Failed to load widget for "
							+ this
							+ " asynchronously.\r\n"
							+ caught.getMessage();
					logger.severe(msg);
					showError(panel, msg);
					if (callback != null) {
						callback.onFailure(caught);
					}
				}

				@Override
				public void onSuccess(final IsWidget result) {
					widget = result;
					showWidget(panel, callback);
				}
			});
		}
	};

	private void render(final AcceptsOneWidget panel,
			final AsyncCallback<AcceptsOneWidget> callback) {
		if (parent != null) {
			parent.render(panel, new AsyncCallback<AcceptsOneWidget>() {
				@Override
				public void onFailure(final Throwable caught) {
					logger.info("Failured to load parent widget. Show " + this
							+ " directly.");
					append(panel, callback);
				}

				@Override
				public void onSuccess(final AcceptsOneWidget result) {
					append(result, callback);
				}
			});
		} else {
			append(panel, callback);
		}
	}

	private void showError(final AcceptsOneWidget panel, final String message) {
		Label label = new Label(message);
		panel.setWidget(label);
	}

	private void showWidget(final AcceptsOneWidget panel,
			final AsyncCallback<AcceptsOneWidget> callback) {
		panel.setWidget(widget);
		if (widget instanceof AcceptsOneWidget) {
			if (callback != null) {
				callback.onSuccess((AcceptsOneWidget) widget);
			}
		}
	}
}
