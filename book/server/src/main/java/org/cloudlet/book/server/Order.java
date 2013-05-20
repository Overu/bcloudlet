package org.cloudlet.book.server;

import com.google.inject.Inject;

import org.cloudlet.core.server.CoreUtil;
import org.cloudlet.core.server.Item;
import org.cloudlet.core.server.Repository;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Order.TYPE_NAME)
public class Order extends Item {

  public enum Status {
    ORDER(0), PAY(1);

    public static Status getStatusByPath(int path) {
      for (Status status : values()) {
        if (status.getPath() == path) {
          return status;
        }
      }
      return null;
    }

    private int path;

    private Status(int path) {
      this.setPath(path);
    }

    public int getPath() {
      return path;
    }

    public void setPath(int path) {
      this.path = path;
    }

  }

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Order";

  public static final String DEVICEID = "deviceId";
  public static final String APPID = "appid";
  public static final String STATUS = "status";
  public static final String DATEORDERED = "dateOrdered";
  public static final String BOOK = "book";

  @Inject
  private transient Repository repo;

  @OneToOne
  protected Book book;

  protected Date dateOrdered;

  protected String deviceId;

  protected String appId;

  private Status status;

  public String getAppId() {
    return appId;
  }

  public Book getBook() {
    return book;
  }

  public Date getDateOrdered() {
    return dateOrdered;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public String getType() {
    return Order.TYPE_NAME;
  }

  @Override
  public void readParams(MultivaluedMap<String, String> params) {
    super.readParams(params);
    if (params.containsKey(DEVICEID)) {
      this.setDeviceId(params.getFirst(DEVICEID));
    }
    if (params.containsKey(APPID)) {
      this.setAppId(params.getFirst(APPID));
    }
    if (params.containsKey(BOOK)) {
      Book book = ((BookStore) repo).getBooks().getChild(params.getFirst(BOOK));
      this.setBook(book);
    }
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public void setDateOrdered(Date dateOrdered) {
    this.dateOrdered = dateOrdered;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

}
