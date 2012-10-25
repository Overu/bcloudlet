package org.cloudlet.epub.bookprocessor;

import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.message.BookProcessor;

/**
 * In the future this will split up too large html documents into smaller ones.
 * 
 * @author paul
 *
 */
public class HtmlSplitterBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		return book;
	}

}
