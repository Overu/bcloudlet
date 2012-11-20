package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.SectionServiceImpl;

@ImplementedBy(SectionServiceImpl.class)
public interface SectionService extends ResourceService<Section> {

}
