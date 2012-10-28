package org.cloudlet.web.core.server;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.cloudlet.web.core.shared.GroupService;
import org.cloudlet.web.core.shared.UserService;
import org.cloudlet.web.core.shared.WebPlatform;

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
		bind(GroupService.class).to(GroupServiceImpl.class);
		logger.finest("End configure " + getClass().getName());
	}
}