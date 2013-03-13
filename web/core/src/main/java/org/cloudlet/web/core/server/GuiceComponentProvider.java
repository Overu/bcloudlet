package org.cloudlet.web.core.server;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.inject.ServiceBindingBuilder;
import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.spi.ComponentProvider;

import java.util.Set;
import java.util.logging.Logger;

public class GuiceComponentProvider implements ComponentProvider {

  private class RepositoryFactory implements Factory<Repository> {

    private final RepositoryProvider repoProvider;

    public RepositoryFactory() {
      repoProvider = WebPlatform.get().getInstance(RepositoryProvider.class);
    }

    @Override
    public void dispose(Repository instance) {
    }

    @Override
    public Repository provide() {
      Repository repo = repoProvider.get();
      resourceContext.initResource(repo);
      return repo;
    }
  }

  private static final Logger LOGGER = Logger.getLogger(GuiceComponentProvider.class.getName());

  private ServiceLocator locator = null;

  JerseyResourceContext resourceContext;

  @SuppressWarnings("unchecked")
  @Override
  public boolean bind(Class<?> component, Set<Class<?>> providerContracts) {

    if (locator == null) {
      throw new IllegalStateException("Guice component is not initialized properly.");
    }

    LOGGER.info("Bind " + component);
    if (!Repository.class.isAssignableFrom(component)) {
      LOGGER.warning("Out of Guice's control " + component);
      return false;
    }

    // TODO register intercepters
    DynamicConfiguration dc = Injections.getConfiguration(locator);
    final ServiceBindingBuilder bindingBuilder = Injections.newFactoryBinder(new RepositoryFactory());
    bindingBuilder.to(component);
    for (Class contract : providerContracts) {
      bindingBuilder.to(contract);
    }

    Injections.addBinding(bindingBuilder, dc);

    dc.commit();
    return true;
  }

  @Override
  public void done() {
    // TODO bind ExceptionMapper ?
  }

  @Override
  public void initialize(final ServiceLocator locator) {
    this.locator = locator;
    resourceContext = locator.getService(JerseyResourceContext.class);
  }

}