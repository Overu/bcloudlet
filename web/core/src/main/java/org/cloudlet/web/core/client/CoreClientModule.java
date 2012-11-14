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
import com.google.gwt.json.client.JSONValue;
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

import org.cloudlet.web.core.client.RequestBuilderBase.Callback;
import org.cloudlet.web.core.client.style.BaseResources;
import org.cloudlet.web.core.shared.Content;
import org.cloudlet.web.core.shared.CorePackage;
import org.cloudlet.web.core.shared.Entry;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.ResourceProxy;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.Root;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebPlatform;
import org.cloudlet.web.core.shared.WebView;

import java.util.Date;
import java.util.logging.Logger;

public class CoreClientModule extends AbstractGinModule {

  @Singleton
  public static class Launcher implements PlaceChangeEvent.Handler {

    @Inject
    EventBus eventBus;

    @Inject
    PlaceController placeController;

    @Inject
    PlaceHistoryHandler historyHandler;

    @Inject
    UserGrid userGrid;

    @Inject
    UserFrom userForm;

    @Inject
    UserModify userModify;

    @Inject
    RepositoryExplorer explorer;

    SimplePanel main;

    @Root
    @Inject
    Entry rootEntry;

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
      Resource resource = (Resource) event.getNewPlace();
      if (resource instanceof ResourceProxy) {
        final StringBuilder url = new StringBuilder("api").append(resource.getUri());
        try {
          RequestBuilderBase.GET(url.toString()).Accept("application/json").bindPlace(resource)
              .bindCallback(new Callback<Resource>() {
                @Override
                public void onSuccess(Resource resource) {
                  render(resource, main);
                }
              }).send();
        } catch (RequestException e) {
          e.printStackTrace();
        }
        // loadResource((ResourceProxy) resource, new AsyncCallback<Resource>() {
        // @Override
        // public void onFailure(Throwable caught) {
        // }
        //
        // @Override
        // public void onSuccess(Resource result) {
        // render(result, main);
        // }
        // });
      } else {
        render(resource, main);
      }
    }

    protected void loadResource(final ResourceProxy proxy, final AsyncCallback<Resource> callback) {
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
            JSONObject root = dg.get("dataGraph").isObject().get("root").isObject();
            Resource resource = readResource(root);
            resource.setParent(proxy.getParent()); // TODO load recursively
            callback.onSuccess(resource);
          }
        });
      } catch (RequestException e) {
      }
    }

    private void start() {
      Repository.TYPE.setWidget(Content.HOME_WIDGET, explorer);

      UserFeed.TYPE.setWidget(Feed.LIST_WIDGET, userGrid);
      UserFeed.TYPE.setWidget(Feed.POST_WIDGET, userForm);
      User.TYPE.setWidget(Content.HOME_WIDGET, userModify);

      BaseResources.INSTANCE();
      main = new SimplePanel();
      main.getElement().setId("main");
      RootPanel.get().add(main);

      eventBus.addHandler(PlaceChangeEvent.TYPE, this);

      historyHandler.handleCurrentHistory();
    }
  }

  private static final Logger logger = Logger.getLogger(CoreClientModule.class.getName());

  public static boolean readBoolean(JSONObject json, String field) {
    Boolean b = readBooleanObject(json, field);
    return b == null ? false : b.booleanValue();
  }

  public static Boolean readBooleanObject(JSONObject json, String field) {
    JSONValue value = json.get(field);
    if (value != null) {
      return value.isBoolean().booleanValue();
    }
    return null;
  }

  public static Date readDate(JSONObject json, String field) {
    Double d = readDoubleObject(json, field);
    if (d != null) {
      return new Date(d.longValue());
    }
    return null;
  }

  public static double readDouble(JSONObject json, String field) {
    Double d = readDoubleObject(json, field);
    if (d != null) {
      return d.doubleValue();
    }
    return 0;
  }

  public static Double readDoubleObject(JSONObject json, String field) {
    JSONValue value = json.get(field);
    if (value != null) {
      return value.isNumber().doubleValue();
    }
    return null;
  }

  public static int readInt(JSONObject json, String field) {
    Double d = readDoubleObject(json, field);
    if (d != null) {
      return d.intValue();
    }
    return 0;
  }

  public static Integer readInteger(JSONObject json, String field) {
    Double d = readDoubleObject(json, field);
    if (d != null) {
      return d.intValue();
    }
    return null;
  }

  public static long readLong(JSONObject json, String field) {
    Double d = readDoubleObject(json, field);
    if (d != null) {
      return d.longValue();
    }
    return 0;
  }

  public static Long readLongObject(JSONObject json, String field) {
    Double d = readDoubleObject(json, field);
    if (d != null) {
      return d.longValue();
    }
    return null;
  }

  public static Resource readResource(JSONObject json) {
    String type = json.get("@xsi.type").isString().stringValue();
    ResourceType objectType = WebPlatform.getInstance().getResourceType(type);
    Resource content = objectType.createInstance();
    content.setPath(readString(json, Resource.PATH));
    content.setTitle(readString(json, Resource.TITLE));
    if (content instanceof Content) {
      ((Content) content).setChildrenCount(readLong(json, Content.CHILDREN_COUNT));
    }
    // content.readFrom(root);
    content.setNativeData(json);
    return content;
  }

  public static String readString(JSONObject json, String field) {
    JSONValue value = json.get(field);
    if (value != null) {
      return value.isString().stringValue();
    }
    return null;
  }

  public static void render(Resource resource, AcceptsOneWidget panel) {
    renderResources(resource, panel, null);
  }

  public static void renderResource(final Resource resource, final AcceptsOneWidget panel,
      final AsyncCallback<IsWidget> callback) {
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
      ((WebView) widget).setPlace(resource);
    }
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }

  @Root
  @Provides
  @Singleton
  public Entry getHomePage(final Repository repo) {
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
      final PlaceController placeController, final EventBus eventBus, @Root final Entry homePlace) {
    PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
    placeHistoryHandler.register(placeController, eventBus, homePlace);
    return placeHistoryHandler;
  }

}
