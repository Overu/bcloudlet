package org.cloudlet.core.server;
// package org.cloudlet.web.core.server;
//
// import org.apache.shiro.config.Ini.Section;
// import org.cloudlet.web.core.bean.BookBean;
// import org.cloudlet.web.core.bean.BookFeedBean;
// import org.cloudlet.web.core.bean.FeedBean;
// import org.cloudlet.web.core.bean.GroupBean;
// import org.cloudlet.web.core.bean.GroupFeedBean;
// import org.cloudlet.web.core.bean.GroupMember;
// import org.cloudlet.web.core.bean.MemberFeedBean;
// import org.cloudlet.web.core.bean.RepositoryBean;
// import org.cloudlet.web.core.bean.UserBean;
// import org.cloudlet.web.core.bean.UserFeedBean;
// import org.cloudlet.web.core.bean.UserMember;
// import org.glassfish.jersey.jettison.JettisonConfiguration;
// import org.glassfish.jersey.jettison.JettisonJaxbContext;
//
// import java.util.HashMap;
// import java.util.Map;
//
// import javax.ws.rs.ext.ContextResolver;
// import javax.ws.rs.ext.Provider;
// import javax.xml.bind.JAXBContext;
//
// @Provider
// public final class JaxbContextResolver implements ContextResolver<JAXBContext> {
//
// private final JAXBContext context;
//
// public JaxbContextResolver() throws Exception {
// Map<String, String> jsonXml2JsonNs = new HashMap<String, String>();
// // jsonXml2JsonNs.put("http://cloudlet.org", "");
// jsonXml2JsonNs.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
// this.context =
// new JettisonJaxbContext(JettisonConfiguration.mappedJettison().xml2JsonNs(jsonXml2JsonNs)
// .build(), FeedBean.class, UserFeedBean.class, GroupFeedBean.class, UserBean.class,
// GroupBean.class,
// MemberFeedBean.class, UserMember.class, GroupMember.class, RepositoryBean.class,
// BookFeedBean.class, BookBean.class, Section.class);
// }
//
// @Override
// public JAXBContext getContext(Class<?> objectType) {
// return context;
// }
// }
