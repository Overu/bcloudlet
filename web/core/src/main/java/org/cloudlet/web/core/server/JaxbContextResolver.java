package org.cloudlet.web.core.server;

import org.apache.shiro.config.Ini.Section;
import org.cloudlet.web.core.shared.Book;
import org.cloudlet.web.core.shared.BookFeed;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupFeed;
import org.cloudlet.web.core.shared.GroupMember;
import org.cloudlet.web.core.shared.MemberFeed;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.UserMember;
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
            MemberFeed.class, UserMember.class, GroupMember.class, Repository.class,
            BookFeed.class, Book.class, Section.class);
  }

  @Override
  public JAXBContext getContext(Class<?> objectType) {
    return context;
  }
}
