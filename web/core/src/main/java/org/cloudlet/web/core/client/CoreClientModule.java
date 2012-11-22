package org.cloudlet.web.core.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.cloudlet.web.core.client.style.BaseResources;
import org.cloudlet.web.core.shared.CorePackage;
import org.cloudlet.web.core.shared.DynaResource;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.Root;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebPlatform;
import org.cloudlet.web.core.shared.WebView;

import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedMap;

public class CoreClientModule extends AbstractGinModule {

  @Singleton
  public static class Launcher implements PlaceChangeEvent.Handler {

    @Inject
    EventBus eventBus;

    @Inject
    ResourceManager resourceManager;

    @Inject
    PlaceHistoryHandler historyHandler;

    @Inject
    UserGrid userGrid;

    @Inject
    UserFeedEditor userForm;

    @Inject
    UserEditor userModify;

    @Inject
    RepositoryExplorer explorer;

    SimplePanel main;

    @Root
    @Inject
    Resource rootEntry;

    @Inject
    public Launcher() {

      new Timer() {
        @Override
        public void run() {
          start();
        }
      }.schedule(1);
    }

    @Override
    public void onPlaceChange(final PlaceChangeEvent event) {
      final Resource resource = (Resource) event.getNewPlace();
      if (resource instanceof DynaResource) {
        loadResource(resource, new AsyncCallback<Resource>() {
          @Override
          public void onFailure(Throwable caught) {
          }

          @Override
          public void onSuccess(Resource result) {
            MultivaluedMap<String, String> paramMap = resource.getQueryParameters();
            String renditionKind = paramMap.getFirst(Resource.RENDITION);
            Resource rendition =
                result.getRendition(renditionKind == null ? Resource.SELF : renditionKind);
            paramMap.remove(Resource.RENDITION);
            rendition.setQueryParameters(paramMap);
            render(rendition, main);
          }
        });
      } else {
        render(resource, main);
      }
    }

    private void start() {
      Repository.TYPE.setWidget(Resource.SELF, explorer);

      UserFeed.TYPE.setWidget(UserFeed.LIST, userGrid);
      UserFeed.TYPE.setWidget(UserFeed.NEW, userForm);
      User.TYPE.setWidget(Resource.SELF, userModify);

      Resource.TYPE.setProvider(JSONObjectProvider.class, new JSONResourceProvider<Resource>());
      User.TYPE.setProvider(JSONObjectProvider.class, new JSONUserProvider());

      BaseResources.INSTANCE();
      main = new SimplePanel();
      main.getElement().setId("main");
      RootPanel.get().add(main);

      eventBus.addHandler(PlaceChangeEvent.TYPE, this);

      historyHandler.handleCurrentHistory();
    }
  }

  private static final Logger logger = Logger.getLogger(CoreClientModule.class.getName());

  public static void loadResource(final Resource proxy, final AsyncCallback<Resource> callback) {
    final StringBuilder url = new StringBuilder("api").append(proxy.getUri());
    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url.toString());
    builder.setHeader("Accept", "application/json");
    try {
      builder.sendRequest(url.toString(), new RequestCallback() {
        @Override
        public void onError(Request request, Throwable exception) {
          // TODO handle error
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
          if (response.getStatusCode() != Response.SC_OK) {
            // TODO handle bad request
            return;
          }

          JSONObject dg = JSONParser.parseLenient(response.getText()).isObject();
          JSONObject data = dg.get("dataGraph").isObject().get("root").isObject();
          Resource resource = JSONResourceProvider.readResource(data);
          if (proxy instanceof DynaResource) {
            resource.setParent(proxy.getParent()); // TODO load recursively
          } else {
            proxy.readFrom(resource);
            resource = proxy;
          }
          callback.onSuccess(resource);
        }
      });
    } catch (RequestException e) {
    }
  }

  public static void render(Resource resource, AcceptsOneWidget panel) {
    renderResources(resource, panel, null);
  }

  public static void renderResource(final Resource resource, final AcceptsOneWidget panel,
      final AsyncCallback<IsWidget> callback) {
    if (resource instanceof DynaResource) {
      final DynaResource delegate = (DynaResource) resource;
      loadResource(resource, new AsyncCallback<Resource>() {
        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess(Resource result) {
          delegate.setDelegate(result);
          renderResource(result, panel, callback);
        }
      });
    } else {
      Object widget = resource.getWidget();
      if (widget instanceof IsWidget) {
        appendWidget(resource, (IsWidget) widget, panel, callback);
      } else if (widget instanceof Provider) {
        appendWidget(resource, ((Provider<IsWidget>) widget).get(), panel, callback);
      } else if (widget instanceof AsyncProvider) {
        AsyncProvider<IsWidget> provider = (AsyncProvider<IsWidget>) widget;
        provider.get(new AsyncCallback<IsWidget>() {
          @Override
          public void onFailure(final Throwable caught) {
            if (callback != null) {
              callback.onFailure(caught);
            }
          }

          @Override
          public void onSuccess(final IsWidget result) {
            appendWidget(resource, result, panel, callback);
          }
        });
      } else {
        logger.info("No widget for " + resource.getUri() + ". Skip to child content");
        if (panel instanceof IsWidget && callback != null) {
          callback.onSuccess((IsWidget) panel);
        }
      }
    }
  }

  public static void renderResources(final Resource resource, final AcceptsOneWidget panel,
      final AsyncCallback<IsWidget> callback) {
    Resource parent = resource.getParent();
    if (parent != null) {
      renderResources(parent, panel, new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          logger.info("Failured to load parent widget. Show " + this + " directly.");
          renderResource(resource, panel, callback);
        }

        @Override
        public void onSuccess(final IsWidget result) {
          if (result instanceof AcceptsOneWidget) {
            renderResource(resource, (AcceptsOneWidget) result, callback);
          } else {
            logger.info(result.getClass().getName()
                + " must implement AcceptsOneWidget to render child widget.");
          }
        }
      });
    } else {
      renderResource(resource, panel, callback);
    }
  }

  private static void appendWidget(Resource resource, IsWidget widget,
      final AcceptsOneWidget panel, final AsyncCallback<IsWidget> callback) {
    resource.setWidget(widget);
    panel.setWidget(widget);
    if (widget instanceof WebView) {
      ((WebView) widget).setValue(resource);
    }
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }

  @Root
  @Provides
  @Singleton
  public Resource getHomePage(final Repository repo) {
    return repo;
  }

  @Override
  protected void configure() {
    logger.finest("configure");
    bind(PlaceHistoryMapper.class).to(ResourceManager.class).in(Singleton.class);
    bind(Launcher.class).asEagerSingleton();
    bind(WebPlatform.class).to(ClientWebPlatform.class).asEagerSingleton();
    bind(CorePackage.class).asEagerSingleton();
  }

  @Provides
  @Singleton
  PlaceController placeControllerProvider(final EventBus eventBus) {
    PlaceController placeController = new PlaceController(eventBus);
    return placeController;
  }

  @Provides
  @Singleton
  PlaceHistoryHandler placeHistoryHandlerProvider(final PlaceHistoryMapper historyMapper,
      final PlaceController placeController, final EventBus eventBus, @Root final Resource homePlace) {
    PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
    placeHistoryHandler.register(placeController, eventBus, homePlace);
    return placeHistoryHandler;
  }

}
