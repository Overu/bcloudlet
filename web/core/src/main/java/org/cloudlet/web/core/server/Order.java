package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Order.TYPE)
@XmlType(name = Order.TYPE)
@Entity(name = Order.TYPE)
@Table(name = Order.TYPE)
public class Order extends Entry {

  public static final String TYPE = CorePackage.PREFIX + "Order";

  @OneToOne
  protected Book book;

  protected Date dateOrdered;

  protected String deviceId;

  protected String appId;

  protected String status;

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

  @Override
  public String getResourceType() {
    return Order.TYPE;
  }

  @Override
  public Class<OrderService> getServiceType() {
    return OrderService.class;
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

}
