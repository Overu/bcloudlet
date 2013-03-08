package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class OrderService extends FeedService<Replies, Reply> {

  public OrderService() {
    super(Replies.class, Reply.class);
  }

}
