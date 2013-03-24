package org.cloudlet.core.server;

import com.google.inject.Inject;

import org.cloudlet.core.servlet.Google;
import org.cloudlet.core.servlet.Yahoo;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.provider.ProvidersDefinition;
import org.scribe.up.provider.impl.GoogleProvider;
import org.scribe.up.provider.impl.YahooProvider;

import java.util.ArrayList;
import java.util.List;

import io.buji.oauth.OAuthFilter;
import io.buji.oauth.OAuthRealm;
import io.buji.oauth.filter.OAuthRolesAuthorizationFilter;

public class OAuthInitializer {

  @Inject
  public OAuthInitializer(OAuthRealm realm, OAuthFilter filter, @Google OAuthRolesAuthorizationFilter googleFilter,
      @Yahoo OAuthRolesAuthorizationFilter yahooFilter) {

    List<OAuthProvider> providers = new ArrayList<OAuthProvider>();
    YahooProvider yahooProvider = new YahooProvider();
    yahooProvider.setKey("dj0yJmk9aEhFZVM1dmJBSXpQJmQ9WVdrOVRWQnVjRUpJTlRZbWNHbzlNVEF4TmpBd05UQTJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Yw--");
    yahooProvider.setSecret("ce0914bc2f5cff2a862eccdf33e690425fce8006");
    providers.add(yahooProvider);
    yahooFilter.setProvider(yahooProvider);

    GoogleProvider googleProvider = new GoogleProvider();
    googleProvider.setKey("anonymous");
    googleProvider.setSecret("anonymous");
    providers.add(googleProvider);
    googleFilter.setProvider(googleProvider);

    ProvidersDefinition providersDef = new ProvidersDefinition();
    providersDef.setProviders(providers);
    providersDef.setBaseUrl("http://localhost:8080/shiro-oauth");

    realm.setProvidersDefinition(providersDef);
    realm.setDefaultRoles("ROLE_USER");

    filter.setProvidersDefinition(providersDef);
    filter.setFailureUrl("/error.jsp");
  }

}
