package org.cloudlet.epub.bookprocessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;


import org.apache.commons.io.IOUtils;
import org.cloudlet.epub.Constants;
import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.datamodel.Resource;
import org.cloudlet.epub.message.BookProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Cleans up regular html into xhtml.
 * Uses HtmlCleaner to do this.
 * 
 * @author paul
 *
 */
public class TextReplaceBookProcessor extends HtmlBookProcessor implements BookProcessor {

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(TextReplaceBookProcessor.class);
	
	public TextReplaceBookProcessor() {
	}

	public byte[] processHtml(Resource resource, Book book, String outputEncoding) throws IOException {
		Reader reader = resource.getReader();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(out, Constants.ENCODING);
		for(String line: IOUtils.readLines(reader)) {
			writer.write(processLine(line));
			writer.flush();
		}
		return out.toByteArray();
	}

	private String processLine(String line) {
		return line.replace("&apos;", "'");
	}
}
