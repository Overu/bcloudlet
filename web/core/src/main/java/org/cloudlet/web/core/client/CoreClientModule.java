package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.cloudlet.web.core.client.style.BaseResources;
import org.cloudlet.web.core.shared.Root;

import java.util.logging.Logger;

public class CoreClientModule extends AbstractGinModule {

  @Singleton
  public static class Launcher extends Initializer implements PlaceChangeEvent.Handler {

    @Inject
    EventBus eventBus;

    @Inject
    PlaceHistoryHandler historyHandler;

    SimplePanel main;

    @Override
    public void onPlaceChange(final PlaceChangeEvent event) {
      Resource place = (Resource) event.getNewPlace();
      place.render(main);
    }

    @Override
    protected void init() {
      BaseResources.INSTANCE();
      main = new SimplePanel();
      main.getElement().setId("main");
      RootPanel.get().add(main);
      eventBus.addHandler(PlaceChangeEvent.TYPE, this);
      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        @Override
        public void execute() {
          historyHandler.handleCurrentHistory();
        }
      });
    }
  }

  private static final Logger logger = Logger.getLogger(CoreClientModule.class.getName());

  @Root
  @Provides
  @Singleton
  public Resource getHomePage(final Resource root) {
    // root.setResourceType(CorePackage.Repository);
    // root.setTitle("Repository");
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
