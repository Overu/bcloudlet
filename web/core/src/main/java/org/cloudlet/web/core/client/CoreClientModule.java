package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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

import org.cloudlet.web.core.client.style.BaseResources;
import org.cloudlet.web.core.shared.Content;
import org.cloudlet.web.core.shared.ContentProxy;
import org.cloudlet.web.core.shared.CorePackage;
import org.cloudlet.web.core.shared.HomePlace;
import org.cloudlet.web.core.shared.ObjectType;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.View;
import org.cloudlet.web.core.shared.WebPlaceManager;
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
    RepositoryExplorer repositoryExplorer;

    SimplePanel main;

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
      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        @Override
        public void execute() {
          Content content = (Content) event.getNewPlace();
          if (content instanceof View || content.getDefaultView() == null) {
            renderContent(content, main);
          } else {
            placeController.goTo(content.getDefaultView());
          }
        }
      });
    }

    private void start() {
      Repository.TYPE.setWidget(View.FOLDER, repositoryExplorer);

      UserFeed.TYPE.setWidget(View.HOME, userGrid);
      UserFeed.TYPE.setWidget(View.POST, userForm);
      User.TYPE.setWidget(View.HOME, userModify);

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

  public static Content readContent(JSONObject json) {
    String type = json.get("@xsi.type").isString().stringValue();
    ObjectType objectType = WebPlatform.getInstance().getByName(type);
    Content content = objectType.createInstance();
    content.setPath(readString(json, Content.PATH));
    content.setTitle(readString(json, Content.TITLE));
    content.setTotalCount(readLong(json, Content.TOTAL_COUNT));
    // content.readFrom(root);
    content.setNativeData(json);
    return content;
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

  public static String readString(JSONObject json, String field) {
    JSONValue value = json.get(field);
    if (value != null) {
      return value.isString().stringValue();
    }
    return null;
  }

  public static void renderContent(Content content, AcceptsOneWidget panel) {
    renderContent(content, panel, null);
  }

  public static void renderContent(final Content content, final AcceptsOneWidget panel,
      final AsyncCallback<IsWidget> callback) {
    Content parent = content.getParent();
    if (parent != null) {
      renderContent(parent, panel, new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          logger.info("Failured to load parent widget. Show " + this + " directly.");
          renderCurrent(content, panel, callback);
        }

        @Override
        public void onSuccess(final IsWidget result) {
          if (result instanceof AcceptsOneWidget) {
            renderCurrent(content, (AcceptsOneWidget) result, callback);
          } else {
            logger.info(result.getClass().getName()
                + " must implement AcceptsOneWidget to render child widget.");
          }
        }
      });
    } else {
      renderCurrent(content, panel, callback);
    }
  }

  public static void renderCurrent(final Content content, final AcceptsOneWidget panel,
      final AsyncCallback<IsWidget> callback) {
    if (content instanceof ContentProxy) {
      final ContentProxy proxy = (ContentProxy) content;
      if (proxy.isLoaded()) {
        if (proxy.getRealContent() != null) {
          renderCurrent(proxy.getRealContent(), panel, callback);
        } else {
          // TODO show item not found
        }
      } else {
        String data = null;
        final StringBuilder url = proxy.getUriBuilder();
        url.insert(0, "api");
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url.toString());
        builder.setHeader("Accept", "application/json");
        try {
          builder.sendRequest(data, new RequestCallback() {
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
              Content content = readContent(root);
              content.setParent(proxy.getParent());// TODO read parent?
              proxy.setLoaded(true);
              proxy.setRealContent(content);
              renderCurrent(content, panel, callback);
            }

          });
        } catch (RequestException e) {

        }
      }
      return;
    }
    Object widget = content.getWidget();
    if (widget != null) {
      appendWidget(content, (IsWidget) widget, panel, callback);
    } else {
      ObjectType contentType = content.getObjectType();
      String widgetId = content instanceof View ? content.getPath() : View.FOLDER;
      contentType =
          content instanceof View ? content.getParent().getObjectType() : content.getObjectType();
      widget = contentType.getWidget(widgetId);
      if (widget instanceof IsWidget) {
        appendWidget(content, (IsWidget) widget, panel, callback);
      } else if (widget instanceof Provider) {
        appendWidget(content, ((Provider<IsWidget>) widget).get(), panel, callback);
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
            appendWidget(content, result, panel, callback);
          }
        });
      } else {
        logger.info("No widget for " + content.getUri() + ". Skip to child content");
        if (panel instanceof IsWidget && callback != null) {
          callback.onSuccess((IsWidget) panel);
        }
      }
    }
  }

  private static void appendWidget(Content content, IsWidget widget, final AcceptsOneWidget panel,
      final AsyncCallback<IsWidget> callback) {
    content.setWidget(widget);
    panel.setWidget(widget);
    if (widget instanceof WebView) {
      ((WebView) widget).setPlace(content);
    }
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }

  @HomePlace
  @Provides
  @Singleton
  public Content getHomePage(final ContentProxy homePlace) {
    return homePlace;
  }

  @Override
  protected void configure() {
    logger.finest("configure");
    bind(PlaceHistoryMapper.class).to(WebPlaceManager.class).in(Singleton.class);
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
      final PlaceController placeController, final EventBus eventBus,
      @HomePlace final Content homePlace) {
    PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
    placeHistoryHandler.register(placeController, eventBus, homePlace);
    return placeHistoryHandler;
  }

}
