package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.MediaServiceImpl;

@ImplementedBy(MediaServiceImpl.class)
public interface MediaService extends ResourceService<Media> {

}
