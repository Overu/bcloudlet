package org.cloudlet.epub.message;

import java.io.OutputStream;

import org.cloudlet.epub.datamodel.Resource;



public interface HtmlProcessor {
	
	void processHtmlResource(Resource resource, OutputStream out);
}
