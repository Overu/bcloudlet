package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.BookServiceImpl;

@ImplementedBy(BookServiceImpl.class)
public interface BookService extends ResourceService<Book> {

}
