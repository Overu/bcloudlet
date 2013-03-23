package org.cloudlet.web.core.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

import org.cloudlet.web.boot.server.BootModule;
import org.cloudlet.web.core.provider.XmlResourceBinder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.ServiceFinderBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreResourceConfig extends ResourceConfig {

  private static final URI BASE_URI = URI.create("http://localhost:8080/web/");

  public static void main(String[] args) {
    try {
      System.out.println("JSON with JAXB Jersey Example App");
      final Injector injector = Guice.createInjector(new BootModule());
      PersistService persistService = injector.getInstance(PersistService.class);
      persistService.start();
      CoreResourceConfig app = injector.getInstance(CoreResourceConfig.class);
      final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, app);

      System.out.println(String.format("Application started.%nTry out %s%nHit enter to stop it...", BASE_URI));
      System.in.read();
      server.stop();
      persistService.stop();
    } catch (IOException ex) {
      Logger.getLogger(CoreResourceConfig.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Inject
  public CoreResourceConfig() {
    // super(JaxbContextResolver.class);
    super(Repository.class, JacksonFeature.class, XmlResourceBinder.class);
    register(new ServiceFinderBinder<ResourceMethodInvocationHandlerProvider>(ResourceMethodInvocationHandlerProvider.class));
  }

}
