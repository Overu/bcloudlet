package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class ReplyService extends FeedService<Replies, Reply> {

  public ReplyService() {
    super(Replies.class, Reply.class);
  }

}
