package org.cloudlet.core.server;

import com.google.inject.Inject;

import org.cloudlet.core.servlet.Google;
import org.cloudlet.core.servlet.Yahoo;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Clients;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.oauth.client.YahooClient;

import io.buji.pac4j.ClientFilter;
import io.buji.pac4j.ClientRealm;
import io.buji.pac4j.filter.ClientRolesAuthorizationFilter;

public class OAuthInitializer {

  @Inject
  public OAuthInitializer(ClientRealm realm, ClientFilter filter, @Google ClientRolesAuthorizationFilter googleFilter,
      @Yahoo ClientRolesAuthorizationFilter yahooFilter) {

    YahooClient yahooClient = new YahooClient();
    yahooClient.setKey("dj0yJmk9aEhFZVM1dmJBSXpQJmQ9WVdrOVRWQnVjRUpJTlRZbWNHbzlNVEF4TmpBd05UQTJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Yw--");
    yahooClient.setSecret("ce0914bc2f5cff2a862eccdf33e690425fce8006");
    yahooFilter.setClient((BaseClient) yahooClient);

    Google2Client googleClient = new Google2Client();
    googleClient.setKey("764053730132.apps.googleusercontent.com");
    googleClient.setSecret("IaIrAiTqMhZnejpuEUVkcMS1");
    googleClient.setCallbackUrl("http://localhost:8080/shiro-oauth");
    googleFilter.setClient((BaseClient) googleClient);

    Clients clients = new Clients();
    clients.setClients(yahooClient, googleClient);
    clients.setCallbackUrl("http://localhost:8080/shiro-oauth");

    realm.setClients(clients);
    realm.setDefaultRoles("ROLE_USER");

    filter.setClients(clients);
    filter.setFailureUrl("/error.jsp");
  }
}
