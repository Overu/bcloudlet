package org.cloudlet.epub.bookprocessor;


import java.io.IOException;


import org.cloudlet.epub.Constants;
import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.datamodel.Resource;
import org.cloudlet.epub.message.BookProcessor;
import org.cloudlet.epub.service.MediatypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for BookProcessors that only manipulate html type resources.
 * 
 * @author paul
 *
 */
public abstract class HtmlBookProcessor implements BookProcessor {

	private final static Logger log = LoggerFactory.getLogger(HtmlBookProcessor.class); 
	public static final String OUTPUT_ENCODING = "UTF-8";

	public HtmlBookProcessor() {
	}

	@Override
	public Book processBook(Book book) {
		for(Resource resource: book.getResources().getAll()) {
			try {
				cleanupResource(resource, book);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return book;
	}

	private void cleanupResource(Resource resource, Book book) throws IOException {
		if(resource.getMediaType() == MediatypeService.XHTML) {
			byte[] cleanedHtml = processHtml(resource, book, Constants.ENCODING);
			resource.setData(cleanedHtml);
			resource.setInputEncoding(Constants.ENCODING);
		}
	}

	protected abstract byte[] processHtml(Resource resource, Book book, String encoding) throws IOException;
}
