package org.cloudlet.web.core.server;

import java.util.Set;

import org.cloudlet.web.core.shared.ContentService;
import org.cloudlet.web.core.shared.WebPlatform;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.inject.ServiceBindingBuilder;
import org.glassfish.jersey.server.spi.ComponentProvider;

public class GuiceComponentProvider implements ComponentProvider {

	private class GuiceFactory<T> implements Factory<T> {

		private Class<T> clz;

		private GuiceFactory(Class<T> clz) {
			this.clz = clz;
		}

		@Override
		public T provide() {
			return WebPlatform.getInstance().getInjector().getInstance(clz);
		}

		@Override
		public void dispose(T instance) {
		}
	}

	private ServiceLocator locator = null;

	@Override
	public void initialize(final ServiceLocator locator) {
		this.locator = locator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean bind(Class<?> component, Set<Class<?>> providerContracts) {

		if (locator == null) {
			throw new IllegalStateException(
					"Guice component is not initialized properly.");
		}

		if (!ContentService.class.isAssignableFrom(component)) {
			return false;
		}

		// TODO register intercepters
		DynamicConfiguration dc = Injections.getConfiguration(locator);
		final ServiceBindingBuilder bindingBuilder = Injections
				.newFactoryBinder(new GuiceFactory(component));
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

}