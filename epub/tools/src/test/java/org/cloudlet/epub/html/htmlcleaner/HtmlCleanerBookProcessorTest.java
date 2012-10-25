package org.cloudlet.epub.html.htmlcleaner;

import java.io.IOException;

import org.cloudlet.epub.Constants;
import org.cloudlet.epub.bookprocessor.HtmlCleanerBookProcessor;
import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.datamodel.Resource;
import org.cloudlet.epub.service.MediatypeService;


import junit.framework.TestCase;

public class HtmlCleanerBookProcessorTest extends TestCase {

	public void testSimpleDocument1() {
		Book book = new Book();
		String testInput = "<html><head><title>title</title></head><body>Hello, world!</html>";
		String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>Hello, world!</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String actualResult = new String(processedHtml, Constants.ENCODING);
			assertEquals(expectedResult, actualResult);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	public void testSimpleDocument2() {
		Book book = new Book();
		String testInput = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>test page</title><link foo=\"bar\" /></head><body background=\"red\">Hello, world!</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String result = new String(processedHtml, Constants.ENCODING);
			assertEquals(Constants.DOCTYPE_XHTML + "\n" + testInput, result);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	public void testSimpleDocument3() {
		Book book = new Book();
		String testInput = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>test page</title></head><body>Hello, world! ß</body></html>";
		try {
			Resource resource = new Resource(null, testInput.getBytes(Constants.ENCODING), "test.html", MediatypeService.XHTML, Constants.ENCODING);
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String result = new String(processedHtml, Constants.ENCODING);
			assertEquals(Constants.DOCTYPE_XHTML + "\n" + testInput, result);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	public void testSimpleDocument4() {
		Book book = new Book();
		String testInput = "<html><head><title>title</title></head><body>Hello, world!\nHow are you ?</html>";
		String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>Hello, world!\nHow are you ?</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String actualResult = new String(processedHtml, Constants.ENCODING);
			assertEquals(expectedResult, actualResult);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}


	public void testMetaContentType() {
		Book book = new Book();
		String testInput = "<html><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body>Hello, world!</html>";
		String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.ENCODING + "\" /></head><body>Hello, world!</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String actualResult = new String(processedHtml, Constants.ENCODING);
			assertEquals(expectedResult, actualResult);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	public void testDocType1() {
		Book book = new Book();
		String testInput = "<html><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body>Hello, world!</html>";
		String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.ENCODING + "\" /></head><body>Hello, world!</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String actualResult = new String(processedHtml, Constants.ENCODING);
			assertEquals(expectedResult, actualResult);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}

	public void testDocType2() {
		Book book = new Book();
		String testInput = Constants.DOCTYPE_XHTML + "\n<html><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body>Hello, world!</html>";
		String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.ENCODING + "\" /></head><body>Hello, world!</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String actualResult = new String(processedHtml, Constants.ENCODING);
			assertEquals(expectedResult, actualResult);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}

	public void testXmlNS() {
		Book book = new Book();
		String testInput = "<html><head><title>title</title></head><body xmlns:xml=\"xml\">Hello, world!</html>";
		String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>Hello, world!</body></html>";
		try {
			Resource resource = new Resource(testInput.getBytes(Constants.ENCODING), "test.html");
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String actualResult = new String(processedHtml, Constants.ENCODING);
			assertEquals(expectedResult, actualResult);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	public void testApos() {
		Book book = new Book();
		String testInput = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>test page</title></head><body>'hi'</body></html>";
		try {
			Resource resource = new Resource(null, testInput.getBytes(Constants.ENCODING), "test.html", MediatypeService.XHTML, Constants.ENCODING);
			book.getResources().add(resource);
			HtmlCleanerBookProcessor htmlCleanerBookProcessor = new HtmlCleanerBookProcessor();
			byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.ENCODING);
			String result = new String(processedHtml, Constants.ENCODING);
			assertEquals(Constants.DOCTYPE_XHTML + "\n" + testInput, result);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
}
