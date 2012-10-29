package org.cloudlet.web.core.server;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupsFeed;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UsersFeed;
import org.glassfish.jersey.jettison.JettisonConfiguration;
import org.glassfish.jersey.jettison.JettisonJaxbContext;

@Provider
public final class JaxbContextResolver implements ContextResolver<JAXBContext> {

	private final JAXBContext context;

	public JaxbContextResolver() throws Exception {
		Map<String, String> jsonXml2JsonNs = new HashMap<String, String>();
		// jsonXml2JsonNs.put("http://cloudlet.org", "");
		jsonXml2JsonNs.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		this.context = new JettisonJaxbContext(JettisonConfiguration
				.mappedJettison().xml2JsonNs(jsonXml2JsonNs).build(),
				Feed.class, UsersFeed.class, GroupsFeed.class, User.class,
				Group.class);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		return context;
	}
}
