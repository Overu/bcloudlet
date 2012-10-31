package org.cloudlet.web.core.client;

import java.util.logging.Logger;

import org.cloudlet.web.core.shared.HomePlace;
import org.cloudlet.web.core.shared.WebPlace;
import org.cloudlet.web.core.shared.WebPlaceManager;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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

public class CoreClientModule extends AbstractGinModule {

	@Singleton
	public static class Render implements PlaceChangeEvent.Handler {

		@Inject
		EventBus eventBus;

		@Inject
		PlaceController placeController;

		@Inject
		PlaceHistoryHandler historyHandler;

		SimplePanel main;

		@Inject
		public Render() {
			new Timer() {
				@Override
				public void run() {
					start();
				}
			}.schedule(1);
		}

		@Override
		public void onPlaceChange(final PlaceChangeEvent event) {
			WebPlace place = (WebPlace) event.getNewPlace();
			if (place.getWelcomePlace() != null) {
				gotoPlace(place.getWelcomePlace());
			} else {
				place.render(main);
			}
		}

		private void gotoPlace(final WebPlace place) {
			logger.info("Loading " + place.getUri());
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					placeController.goTo(place);
				}
			});
		}

		private void start() {

			main = new SimplePanel();
			main.getElement().setId("main");
			RootPanel.get().add(main);

			eventBus.addHandler(PlaceChangeEvent.TYPE, this);

			historyHandler.handleCurrentHistory();
		}
	}

	private static final Logger logger = Logger
			.getLogger(CoreClientModule.class.getName());

	@Override
	protected void configure() {
		logger.finest("configure");
		bind(PlaceHistoryMapper.class).to(WebPlaceManager.class).in(
				Singleton.class);
		bind(Render.class).asEagerSingleton();
	}

	@HomePlace
	@Provides
	@Singleton
	public WebPlace getHomePage(final WebPlace homePlace, UserGrid usersGrid) {
		homePlace.setWidget(usersGrid);
		return homePlace;
	}

	@Provides
	@Singleton
	PlaceController placeControllerProvider(final EventBus eventBus) {
		PlaceController placeController = new PlaceController(eventBus);
		return placeController;
	}

	@Provides
	@Singleton
	PlaceHistoryHandler placeHistoryHandlerProvider(
			final PlaceHistoryMapper historyMapper,
			final PlaceController placeController, final EventBus eventBus,
			@HomePlace final WebPlace homePlace) {
		PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(
				historyMapper);
		placeHistoryHandler.register(placeController, eventBus, homePlace);
		return placeHistoryHandler;
	}

}