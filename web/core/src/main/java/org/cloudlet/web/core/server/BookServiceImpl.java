package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Book;
import org.cloudlet.web.core.shared.BookService;

@Singleton
public class BookServiceImpl extends ResourceServiceImpl<Book> implements BookService {
}
