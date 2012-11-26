package org.cloudlet.web.core.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.client.style.BaseResources;

import java.util.logging.Logger;

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
      ResourcePlace place = (ResourcePlace) event.getNewPlace();
      place.render(main);
    }

    private void start() {
      ClientPlatform.register(Repository.class);
      ClientPlatform.register(UserFeed.class);
      ClientPlatform.register(User.class);

      ClientPlatform.setWidget(Repository.class, "", explorer);
      ClientPlatform.setWidget(User.class, "", userModify);
      ClientPlatform.setWidget(UserFeed.class, UserFeedEditor.NEW, userForm);
      ClientPlatform.setWidget(UserFeed.class, UserGrid.LIST, userGrid);

      BaseResources.INSTANCE();
      main = new SimplePanel();
      main.getElement().setId("main");
      RootPanel.get().add(main);

      eventBus.addHandler(PlaceChangeEvent.TYPE, this);

      historyHandler.handleCurrentHistory();
    }
  }

  private static final Logger logger = Logger.getLogger(CoreClientModule.class.getName());

  @RootPlace
  @Provides
  @Singleton
  public ResourcePlace getHomePage(final ResourcePlace root) {
    root.setResourceType(Repository.class);
    return root;
  }

  @Override
  protected void configure() {
    logger.finest("configure");
    bind(PlaceHistoryMapper.class).to(ResourceManager.class).in(Singleton.class);
    bind(Launcher.class).asEagerSingleton();
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
      @RootPlace final ResourcePlace homePlace) {
    PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
    placeHistoryHandler.register(placeController, eventBus, homePlace);
    return placeHistoryHandler;
  }

}
