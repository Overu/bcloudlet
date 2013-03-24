package org.cloudlet.web.logging.shared.rpc;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public interface ChannelContext extends RequestContext {
  Request<String> getToken(String clientId);
}
