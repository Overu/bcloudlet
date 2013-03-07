package org.cloudlet.web.core.server;

import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.shared.CorePackage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Media)
@XmlType(name = CorePackage.Media)
@Entity(name = CorePackage.Media)
@Table(name = CorePackage.Media)
public class Media extends Content {

  private String mimeType;

  private String source;

  @Override
  @DELETE
  public void delete() {
    getService().delete(this);
  }

  @XmlTransient
  public File getFile() {
    if (id == null) {
      id = CoreUtil.randomID();
    }
    String filePath = "D:/DevData/files/" + id;
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

  @Override
  public String getResourceType() {
    return CorePackage.Media;
  }

  @Override
  @XmlTransient
  public MediaService getService() {
    return (MediaService) super.getService();
  }

  @Override
  @XmlTransient
  public Class<MediaService> getServiceType() {
    return MediaService.class;
  }

  public String getSource() {
    return source;
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

  @Override
  @PUT
  public Media update() {
    return getService().update(this);
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

  @Override
  protected void doLoad() {
  }

  @Override
  protected <T extends Content> T findChild(String path) {
    return null;
  }

  @Override
  protected void init() {
  }
}
