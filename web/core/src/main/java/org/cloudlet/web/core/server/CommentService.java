package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class CommentService extends FeedService<Comments, Comment> {

  public CommentService() {
    super(Comments.class, Comment.class);
  }

  @Override
  protected void init(Comment comment) {
    super.init(comment);
    Replies replies = new Replies();
    replies.setPath("replies");
    comment.createReference(replies);
    comment.setReplies(replies);
    comment.update();
  }

}
