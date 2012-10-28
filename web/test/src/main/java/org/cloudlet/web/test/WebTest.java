package org.cloudlet.web.test;

import java.net.URI;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;

import org.cloudlet.web.boot.server.BootModule;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.internal.ServiceFinderBinder;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import com.google.common.collect.Maps;
import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;

public abstract class WebTest {

	public static class WebTestModule extends GuiceBerryModule {
		@Override
		protected void configure() {
			super.configure();
			install(new TestModule());
			install(new BootModule());
		}
	}

	@Rule
	public final GuiceBerryRule guiceBerry = new GuiceBerryRule(
			WebTestModule.class);

	private static final Logger LOGGER = Logger.getLogger(JerseyTest.class
			.getName());
	/**
	 * Holds the default test container factory class to be used for running the
	 * tests.
	 */
	private static Class<? extends TestContainerFactory> testContainerFactoryClass;
	/**
	 * The test container factory which creates an instance of the test
	 * container on which the tests would be run.
	 */
	private TestContainerFactory testContainerFactory;
	/**
	 * The test container on which the tests would be run.
	 */
	private TestContainer tc;
	private Client client;
	private ApplicationHandler application;
	/**
	 * JerseyTest property bag that can be used to configure the test behavior.
	 * These properties can be overridden with a system property.
	 */
	private final Map<String, String> propertyMap = Maps.newHashMap();
	/**
	 * JerseyTest forced property bag that can be used to configure the test
	 * behavior. These property cannot be overridden with a system property.
	 */
	private final Map<String, String> forcedPropertyMap = Maps.newHashMap();

	private ResourceConfig getResourceConfig(Application app) {
		return ResourceConfig.forApplication(app);
	}

	/**
	 * Programmatically enable a feature with a given name. Enabling of the
	 * feature may be overridden via a system property.
	 * 
	 * @param featureName
	 *            name of the enabled feature.
	 */
	protected final void enable(String featureName) {
		// TODO: perhaps we could reuse the resource config for the test
		// properties?
		propertyMap.put(featureName, Boolean.TRUE.toString());
	}

	/**
	 * Programmatically disable a feature with a given name. Disabling of the
	 * feature may be overridden via a system property.
	 * 
	 * @param featureName
	 *            name of the disabled feature.
	 */
	protected final void disable(String featureName) {
		propertyMap.put(featureName, Boolean.FALSE.toString());
	}

	/**
	 * Programmatically force-enable a feature with a given name. Force-enabling
	 * of the feature cannot be overridden via a system property. Use with care!
	 * 
	 * @param featureName
	 *            name of the force-enabled feature.
	 */
	protected final void forceEnable(String featureName) {
		forcedPropertyMap.put(featureName, Boolean.TRUE.toString());
	}

	/**
	 * Programmatically force-disable a feature with a given name.
	 * Force-disabling of the feature cannot be overridden via a system
	 * property. Use with care!
	 * 
	 * @param featureName
	 *            name of the force-disabled feature.
	 */
	protected final void forceDisable(String featureName) {
		forcedPropertyMap.put(featureName, Boolean.FALSE.toString());
	}

	/**
	 * Programmatically set a value of a property with a given name. The
	 * property value may be overridden via a system property.
	 * 
	 * @param propertyName
	 *            name of the property.
	 * @param value
	 *            property value.
	 */
	protected final void set(String propertyName, String value) {
		propertyMap.put(propertyName, value);
	}

	/**
	 * Programmatically force-set a value of a property with a given name. The
	 * force-set property value cannot be overridden via a system property.
	 * 
	 * @param propertyName
	 *            name of the property.
	 * @param value
	 *            property value.
	 */
	protected final void forceSet(String propertyName, String value) {
		forcedPropertyMap.put(propertyName, value);
	}

	protected boolean isEnabled(String featureName) {
		return Boolean.valueOf(getProperty(featureName));
	}

	private String getProperty(String propertyName) {
		if (forcedPropertyMap.containsKey(propertyName)) {
			return forcedPropertyMap.get(propertyName);
		}

		final Properties sysprops = System.getProperties();
		if (sysprops.containsKey(propertyName)) {
			return sysprops.getProperty(propertyName);
		}

		if (propertyMap.containsKey(propertyName)) {
			return propertyMap.get(propertyName);
		}

		return null;
	}

	/**
	 * Return an JAX-RS application that defines how the application in the test
	 * container is configured.
	 * <p>
	 * If a constructor is utilized that does not supply an application
	 * descriptor then this method must be overridden to return an application
	 * descriptor, otherwise an {@link UnsupportedOperationException} exception
	 * will be thrown.
	 * <p>
	 * If a constructor is utilized that does supply an application descriptor
	 * then this method does not require to be overridden and will not be
	 * invoked.
	 * 
	 * @return the application descriptor.
	 */
	protected Application configure() {
		throw new UnsupportedOperationException(
				"The configure method must be implemented by the extending class");
	}

	/**
	 * Sets the test container factory to to be used for testing.
	 * 
	 * @param testContainerFactory
	 *            the test container factory to to be used for testing.
	 */
	protected final void setTestContainerFactory(
			TestContainerFactory testContainerFactory) {
		this.testContainerFactory = testContainerFactory;
	}

	/**
	 * Returns an instance of {@link TestContainerFactory} class. This instance
	 * can be set by a constructor (
	 * {@link #JerseyTest(org.glassfish.jersey.test.spi.TestContainerFactory)},
	 * as an application {@link Providers Provider} or the
	 * {@link TestContainerFactory} class can be set as a
	 * {@value org.glassfish.jersey.test.TestProperties#CONTAINER_FACTORY}
	 * property.
	 * 
	 * @return an instance of {@link TestContainerFactory} class.
	 * @throws TestContainerException
	 *             if the initialization of {@link TestContainerFactory}
	 *             instance is not successful.
	 */
	protected TestContainerFactory getTestContainerFactory()
			throws TestContainerException {
		if (testContainerFactory == null) {
			if (testContainerFactoryClass == null) {

				final String tcfClassName = getProperty(TestProperties.CONTAINER_FACTORY);
				if ((tcfClassName == null)) {
					Set<TestContainerFactory> testContainerFactories = Providers
							.getProviders(application.getServiceLocator(),
									TestContainerFactory.class);

					if (testContainerFactories.size() >= 1) {
						// if default factory is present, use it.
						for (TestContainerFactory tcFactory : testContainerFactories) {

							if (tcFactory
									.getClass()
									.getName()
									.equals(TestProperties.DEFAULT_CONTAINER_FACTORY)) {
								LOGGER.log(
										Level.CONFIG,
										"Found multiple TestContainerFactory implementations, using default {0}",
										tcFactory.getClass().getName());

								testContainerFactoryClass = tcFactory
										.getClass(); // is this necessary?
								return tcFactory;
							}
						}

						if (testContainerFactories.size() != 1) {
							LOGGER.log(
									Level.WARNING,
									"Found multiple TestContainerFactory implementations, using {0}",
									testContainerFactories.iterator().next()
											.getClass().getName());
						}

						testContainerFactoryClass = testContainerFactories
								.iterator().next().getClass();
						return testContainerFactories.iterator().next();

					}
				} else {
					try {
						testContainerFactoryClass = Class.forName(tcfClassName)
								.asSubclass(TestContainerFactory.class);
					} catch (ClassNotFoundException ex) {
						throw new TestContainerException(
								"The default test container factory class name, "
										+ tcfClassName + ", cannot be loaded",
								ex);
					} catch (ClassCastException ex) {
						throw new TestContainerException(
								"The default test container factory class, "
										+ tcfClassName
										+ ", is not an instance of TestContainerFactory",
								ex);
					}
				}
			}

			try {
				return testContainerFactoryClass.newInstance();
			} catch (Exception ex) {
				throw new TestContainerException(
						"The default test container factory, "
								+ testContainerFactoryClass
								+ ", could not be instantiated", ex);
			}
		}

		return testContainerFactory;
	}

	/**
	 * Create a web resource whose URI refers to the base URI the Web
	 * application is deployed at.
	 * 
	 * @return the created web resource
	 */
	public WebTarget target() {
		return client().target(tc.getBaseUri());
	}

	/**
	 * Create a web resource whose URI refers to the base URI the Web
	 * application is deployed at plus the path specified in the argument.
	 * 
	 * This method is an equivalent of calling {@code target().path(path)}.
	 * 
	 * @param path
	 *            Relative path (from base URI) this target should point to.
	 * @return the created web resource
	 */
	public WebTarget target(String path) {
		return target().path(path);
	}

	/**
	 * Get the client that is configured for this test.
	 * 
	 * @return the configured client.
	 */
	public Client client() {
		if (client == null) {
			client = getClient(tc, application);
		}
		return client;
	}

	/**
	 * Set up the test by invoking {@link TestContainer#start() } on the test
	 * container obtained from the test container factory.
	 * 
	 * @throws Exception
	 *             if an exception is thrown during setting up the test
	 *             environment.
	 */
	@Before
	public void setUp() throws Exception {
		if (tc == null) {
			ResourceConfig config = getResourceConfig(configure());
			config.addBinders(new ServiceFinderBinder<TestContainerFactory>(
					TestContainerFactory.class));
			this.application = new ApplicationHandler(config);

			this.tc = getContainer(application, getTestContainerFactory());
		}
		tc.start();
	}

	/**
	 * Tear down the test by invoking {@link TestContainer#stop() } on the test
	 * container obtained from the test container factory.
	 * 
	 * @throws Exception
	 *             if an exception is thrown during tearing down the test
	 *             environment.
	 */
	@After
	public void tearDown() throws Exception {
		tc.stop();
	}

	private TestContainer getContainer(ApplicationHandler application,
			TestContainerFactory tcf) {
		if (application == null) {
			throw new IllegalArgumentException("The application cannot be null");
		}

		return tcf.create(getBaseUri(), application);
	}

	/**
	 * Creates an instance of {@link Client}.
	 * 
	 * Checks whether TestContainer provides ClientConfig instance and if not,
	 * empty new {@link org.glassfish.jersey.client.ClientConfig} instance will
	 * be used to create new client instance.
	 * 
	 * This method is called exactly once when JerseyTest is created.
	 * 
	 * @param tc
	 *            instance of {@link TestContainer}
	 * @param applicationHandler
	 *            instance of {@link ApplicationHandler}
	 * @return A Client instance.
	 */
	protected Client getClient(TestContainer tc,
			ApplicationHandler applicationHandler) {
		ClientConfig cc = tc.getClientConfig();

		if (cc == null) {
			cc = new ClientConfig();
		}

		// check if logging is required
		if (isEnabled(TestProperties.LOG_TRAFFIC)) {
			cc.register(new LoggingFilter(LOGGER,
					isEnabled(TestProperties.DUMP_ENTITY)));
		}

		configureClient(cc);

		return ClientFactory.newClient(cc);
	}

	/**
	 * Can be overridden by subclasses to conveniently configure the client
	 * instance used by the test.
	 * 
	 * @param clientConfig
	 *            Client configuration that can be modified to configure the
	 *            client.
	 */
	protected void configureClient(ClientConfig clientConfig) {
		// nothing
	}

	/**
	 * Returns the base URI of the application.
	 * 
	 * @return The base URI of the application
	 */
	protected URI getBaseUri() {
		return UriBuilder.fromUri("http://localhost/").port(getPort()).build();
	}

	/**
	 * Get the port to be used for test application deployments.
	 * 
	 * @return The HTTP port of the URI
	 */
	protected final int getPort() {
		final String value = System.getProperty(TestProperties.CONTAINER_PORT);
		if (value != null) {

			try {
				final int i = Integer.parseInt(value);
				if (i <= 0) {
					throw new NumberFormatException("Value not positive.");
				}
				return i;
			} catch (NumberFormatException e) {
				LOGGER.log(Level.CONFIG, "Value of "
						+ TestProperties.CONTAINER_PORT
						+ " property is not a valid positive integer [" + value
						+ "]." + " Reverting to default ["
						+ TestProperties.DEFAULT_CONTAINER_PORT + "].", e);
			}
		}
		return TestProperties.DEFAULT_CONTAINER_PORT;
	}
}
