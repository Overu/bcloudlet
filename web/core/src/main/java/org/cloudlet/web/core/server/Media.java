package org.cloudlet.web.core.server;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.Entity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Media.TYPE_NAME)
public class Media extends Content {

  private String mimeType;

  private String source;

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Media";

  @XmlTransient
  public File getFile() {
    if (id == null) {
      id = CoreUtil.randomID();
    }
    String filePath = CoreUtil.getDataLocation() + "/localhost/media/" + id;
    return new File(filePath);
  }

  @GET
  @Path("{fileName}")
  @Produces(MediaType.WILDCARD)
  public Response getMedia(@PathParam("fileName") String fileName) {
    String disposition = "attachment; filename*=UTF-8'en'" + CoreUtil.toURLEncoded(fileName) + ";";
    return Response.ok().entity(openStream()).type(getMimeType()).header("Content-Disposition", disposition).build();
  }

  public String getMimeType() {
    return mimeType;
  }

  public String getSource() {
    return source;
  }

  @Override
  public String getType() {
    return Media.TYPE_NAME;
  }

  public InputStream openStream() {
    try {
      return new FileInputStream(getFile());
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  public void read(InputStream inputStream) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    try {
      File file = getFile();
      file.getParentFile().mkdirs();
      file.createNewFile();
      in = new BufferedInputStream(inputStream);
      out = new FileOutputStream(file);
      byte[] buffer = new byte[1024];
      for (int bytesRead = in.read(buffer); bytesRead > 0; bytesRead = in.read(buffer)) {
        out.write(buffer, 0, bytesRead);
      }
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void write(OutputStream out) throws IOException {
    InputStream in = null;
    try {
      File file = getFile();
      in = new BufferedInputStream(new FileInputStream(file));
      byte[] buffer = new byte[1024];
      for (int bytesRead = in.read(buffer); bytesRead > 0; bytesRead = in.read(buffer)) {
        out.write(buffer, 0, bytesRead);
      }
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
  }

}
