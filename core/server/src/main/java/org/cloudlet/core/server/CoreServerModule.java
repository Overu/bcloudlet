package org.cloudlet.core.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.hibernate.ejb.AvailableSettings;

import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.logging.Logger;

@Singleton
public class CoreServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(CoreServerModule.class.getName());

  private Properties props = new Properties();

  private RepositoryProvider provider;

  private DependencyScanner scanner;

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    ServiceLoader<RepositoryProvider> rpLoader = ServiceLoader.load(RepositoryProvider.class);
    Iterator<RepositoryProvider> providers = rpLoader.iterator();
    while (providers.hasNext()) {
      provider = providers.next();
      bind(Repository.class).toProvider(provider);
      break;
    }

    if (provider != null) {
      scanner = new DependencyScanner(provider.getType());
      props.put(AvailableSettings.SCANNER, scanner);
    }

    install(new JpaPersistModule("persist.jpaUnit").properties(props)); // TODO read from

    requestStaticInjection(InjectionListener.class);

    bind(WebPlatform.class).asEagerSingleton();

    // MethodInterceptor finderInterceptor = new JpaFinderProxy();
    // requestInjection(finderInterceptor);
    // bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }

}