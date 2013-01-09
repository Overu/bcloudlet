package org.cloudlet.web.core.client.v2;

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

import org.cloudlet.web.core.CorePackage;
import org.cloudlet.web.core.Registry;
import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.client.style.BaseResources;

import java.util.logging.Logger;

public class CoreClientModule extends AbstractGinModule {

  @Singleton
  public static class Launcher implements PlaceChangeEvent.Handler {

    @Inject
    EventBus eventBus;

    @Inject
    PlaceHistoryHandler historyHandler;

    @Inject
    ResourceExplorer explorer;

    @Inject
    UserEditor userEditor;

    @Inject
    UserGrid userGrid;

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
      Resource place = (Resource) event.getNewPlace();
      place.render(main);
    }

    private void start() {

      Registry.setWidget(CorePackage.REPOSITORY, Resource.CONTAINER, explorer);

      Registry.setWidget(CorePackage.USERFEED, "", userGrid);

      Registry.setWidget(CorePackage.USERFEED, "new", userEditor);

      BaseResources.INSTANCE();
      main = new SimplePanel();
      main.getElement().setId("main");
      RootPanel.get().add(main);

      eventBus.addHandler(PlaceChangeEvent.TYPE, this);

      historyHandler.handleCurrentHistory();
    }
  }

  private static final Logger logger = Logger.getLogger(CoreClientModule.class.getName());

  @Root
  @Provides
  @Singleton
  public Resource getHomePage(final Resource root) {
    root.setResourceType(CorePackage.REPOSITORY);
    root.setTitle("Repository");
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
  PlaceHistoryHandler placeHistoryHandlerProvider(final PlaceHistoryMapper historyMapper, final PlaceController placeController,
      final EventBus eventBus, @Root final Resource homePlace) {
    PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
    placeHistoryHandler.register(placeController, eventBus, homePlace);
    return placeHistoryHandler;
  }

}