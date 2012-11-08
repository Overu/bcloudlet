package org.cloudlet.web.core.server;

import org.cloudlet.web.core.DataGraph;
import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.GroupMember;
import org.cloudlet.web.core.MemberFeed;
import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.UserMember;
import org.glassfish.jersey.jettison.JettisonConfiguration;
import org.glassfish.jersey.jettison.JettisonJaxbContext;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

@Provider
public final class JaxbContextResolver implements ContextResolver<JAXBContext> {

  private final JAXBContext context;

  public JaxbContextResolver() throws Exception {
    Map<String, String> jsonXml2JsonNs = new HashMap<String, String>();
    // jsonXml2JsonNs.put("http://cloudlet.org", "");
    jsonXml2JsonNs.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
    this.context =
        new JettisonJaxbContext(JettisonConfiguration.mappedJettison().xml2JsonNs(jsonXml2JsonNs)
            .build(), Feed.class, UserFeed.class, GroupFeed.class, User.class, Group.class,
            MemberFeed.class, UserMember.class, GroupMember.class, Repository.class, DataGraph.class);
  }

  @Override
  public JAXBContext getContext(Class<?> objectType) {
    return context;
  }
}
