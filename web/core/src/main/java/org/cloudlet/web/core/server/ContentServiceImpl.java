package org.cloudlet.web.core.server;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.shared.Content;
import org.cloudlet.web.core.shared.ContentService;
import org.cloudlet.web.core.shared.Rendition;
import org.cloudlet.web.core.shared.Resource;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;

public class ContentServiceImpl<T extends Content> extends ServiceImpl<T> implements
    ContentService<T> {

  @Override
  public Resource createChild(T parent, MultivaluedMap<String, String> params,
      final String contentType, final Integer length, final InputStream inputStream) {
    try {
      FileUpload fileUpload = new FileUpload();
      FileItemIterator iter = fileUpload.getItemIterator(new RequestContext() {

        @Override
        public String getCharacterEncoding() {
          return "UTF-8";
        }

        @Override
        public int getContentLength() {
          return length;
        }

        @Override
        public String getContentType() {
          return contentType;
        }

        @Override
        public InputStream getInputStream() throws IOException {
          return inputStream;
        }
      });

      Rendition res = null;
      while (iter.hasNext()) {
        FileItemStream value = iter.next();
        String key = value.getFieldName();
        InputStream in = value.openStream();
        try {
          if (value.isFormField()) {
            String strValue = Streams.asString(in, "UTF-8");
            params.add(key, strValue);
          } else {
            res = new Rendition();
            res.setInputStream(in);
            res.setPath(value.getName());
            res.setMimeType(value.getContentType());
            res.setParent(parent);
          }
        } finally {
          IOUtils.closeQuietly(in);
        }

      }
      if (res != null) {
        String path = params.getFirst(Resource.PATH);
        String title = params.getFirst(Resource.TITLE);
        if (path != null) {
          res.setPath(path);
        }
        if (title != null) {
          res.setTitle(title);
        }
        res.save();
        return res;
      }
    } catch (Exception e) {
      // VirusFoundException, VirusFoundException will be handled by ServiceEndPointUtil centrally
      if (e.getCause() != null
          && e.getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
        throw new WebApplicationException();
      } else if (e instanceof FileUploadBase.SizeLimitExceededException) {
        throw new WebApplicationException();
      } else {
        throw new WebApplicationException();
      }
    } finally {
    }
    return null;
  }
}
