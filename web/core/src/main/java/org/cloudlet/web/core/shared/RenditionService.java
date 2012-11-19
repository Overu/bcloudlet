package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.RenditionServiceImpl;

@ImplementedBy(RenditionServiceImpl.class)
public interface RenditionService extends Service<Rendition> {

}
