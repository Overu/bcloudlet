package org.cloudlet.web.core.server;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

public interface Taggable extends Resource {

  void addTag(Tag tag);

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path(Repository.TAGS)
  Taggable addTags(@FormParam("tag") String tag);

  Set<Tag> getTags();

  void setTags(Set<Tag> tags);

}
