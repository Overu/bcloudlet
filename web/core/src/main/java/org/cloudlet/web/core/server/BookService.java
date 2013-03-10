package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class BookService extends FeedService<Books, Book> {

  public BookService() {
    super(Books.class, Book.class);
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
