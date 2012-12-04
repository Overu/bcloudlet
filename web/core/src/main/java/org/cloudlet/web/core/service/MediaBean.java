package org.cloudlet.web.core.service;

import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.Media;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Media.TYPE)
@XmlType(name = Media.TYPE)
@Entity(name = Media.TYPE)
@Table(name = Media.TYPE)
public class MediaBean extends ResourceBean {

  private String mimeType;

  @XmlTransient
  public File getFile() {
    if (id == null) {
      id = UUID.randomUUID().toString();
    }
    String filePath = "D:/DevData/files/" + id;
    return new File(filePath);
  }

  public String getMimeType() {
    return mimeType;
  }

  @Override
  public String getResourceType() {
    return Media.TYPE;
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
