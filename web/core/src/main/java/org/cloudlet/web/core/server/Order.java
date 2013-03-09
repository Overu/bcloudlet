package org.cloudlet.web.core.server;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Order.TYPE_NAME)
public class Order extends Entry {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Order";

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
  public Class<OrderService> getServiceType() {
    return OrderService.class;
  }

  @Override
  public String getType() {
    return Order.TYPE_NAME;
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
