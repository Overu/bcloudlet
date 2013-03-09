package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class BookService extends FeedService<Books, Book> {

  public BookService() {
    super(Books.class, Book.class);
  }

  public void addTag(Book book, Tag tag) {
    tag.createReference(book);
    Set<Tag> tags = new HashSet<Tag>();
    tags.add(tag);
    book.setTags(tags);
    book.update();
  }

  @Override
  protected void init(Book book) {
    super.init(book);
    Comments comments = new Comments();
    comments.setPath(Book.COMMENTS);
    book.createReference(comments);
    book.setComments(comments);
    book.update();
  }
}
