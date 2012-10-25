package org.cloudlet.epub.bookprocessor;

import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.datamodel.Identifier;
import org.cloudlet.epub.message.BookProcessor;

/**
 * If the book has no identifier it adds a generated UUID as identifier.
 * 
 * @author paul
 *
 */
public class FixIdentifierBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		if(book.getMetadata().getIdentifiers().isEmpty()) {
			book.getMetadata().addIdentifier(new Identifier());
		}
		return book;
	}
}
