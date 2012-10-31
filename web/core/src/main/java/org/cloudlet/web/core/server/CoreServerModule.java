package org.cloudlet.web.core.server;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.cloudlet.web.core.WebPlatform;
import org.cloudlet.web.core.service.GroupFeedService;
import org.cloudlet.web.core.service.GroupService;
import org.cloudlet.web.core.service.UserFeedService;
import org.cloudlet.web.core.service.UserService;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.jpa.JpaPersistModule;

@Singleton
public class CoreServerModule extends AbstractModule {

	private final Logger logger = Logger.getLogger(CoreServerModule.class
			.getName());

	@java.lang.Override
	protected void configure() {
		logger.finest("Begin configure " + getClass().getName());

		install(new JpaPersistModule("persist.jpaUnit")); // TODO read from

		requestStaticInjection(WebPlatform.class);

		MethodInterceptor finderInterceptor = new JpaFinderProxy();
		requestInjection(finderInterceptor);
		bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
		bind(UserService.class).to(UserServiceImpl.class);
		bind(UserFeedService.class).to(UserFeedServiceImpl.class);
		bind(GroupService.class).to(GroupServiceImpl.class);
		bind(GroupFeedService.class).to(GroupFeedServiceImpl.class);
		logger.finest("End configure " + getClass().getName());
	}
}