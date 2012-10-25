package org.cloudlet.epub.bookprocessor;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.cloudlet.epub.datamodel.Book;
import org.cloudlet.epub.datamodel.Resource;
import org.cloudlet.epub.datamodel.Spine;
import org.cloudlet.epub.datamodel.SpineReference;
import org.cloudlet.epub.message.BookProcessor;


/**
 * Removes Sections from the page flow that differ only from the previous section's href by the '#' in the url.
 * 
 * @author paul
 *
 */
public class SectionHrefSanityCheckBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		book.getSpine().setSpineReferences(checkSpineReferences(book.getSpine()));
		return book;
	}

	private static List<SpineReference> checkSpineReferences(Spine spine) {
		List<SpineReference> result = new ArrayList<SpineReference>(spine.size());
		Resource previousResource = null;
		for(SpineReference spineReference: spine.getSpineReferences()) {
			if(spineReference.getResource() == null
					|| StringUtils.isBlank(spineReference.getResource().getHref())) {
				continue;
			}
			if(previousResource == null
					|| spineReference.getResource() == null
					|| ( ! (spineReference.getResource().getHref().equals(previousResource.getHref())))) {
				result.add(spineReference);
			}
			previousResource = spineReference.getResource();
		}
		return result;
	}
}
