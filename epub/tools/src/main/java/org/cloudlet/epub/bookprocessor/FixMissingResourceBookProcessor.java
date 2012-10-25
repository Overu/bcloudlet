package org.cloudlet.epub.bookprocessor;

import java.util.Collection;

import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.datamodel.TOCReference;
import org.cloudlet.epub.message.BookProcessor;



public class FixMissingResourceBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		return book;
	}

	private void fixMissingResources(Collection<TOCReference> tocReferences, Book book) {
		for (TOCReference tocReference:  tocReferences) {
			if (tocReference.getResource() == null) {
				
			}
		}
	}
}
