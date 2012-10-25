package org.cloudlet.web.core.service;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cloudlet.web.core.server.GroupServiceImpl;
import org.cloudlet.web.core.server.UserServiceImpl;
import org.cloudlet.web.core.shared.JaxbContextResolver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jettison.JettisonBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class CoreApplication extends ResourceConfig {

	public CoreApplication() {
		super(GroupServiceImpl.class, UserServiceImpl.class,
				JaxbContextResolver.class);
		addBinders(new JettisonBinder());
	}

	private static final URI BASE_URI = URI
			.create("http://localhost:8080/core/");

	public static void main(String[] args) {
		try {
			System.out.println("JSON with JAXB Jersey Example App");

			final HttpServer server = GrizzlyHttpServerFactory
					.createHttpServer(BASE_URI, createApp());

			System.out
					.println(String
							.format("Application started.%nTry out %s%nHit enter to stop it...",
									BASE_URI));
			System.in.read();
			server.stop();
		} catch (IOException ex) {
			Logger.getLogger(CoreApplication.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static CoreApplication createApp() {
		return new CoreApplication();
	}
}
