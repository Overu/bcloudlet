package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Orders.TYPE)
@XmlType(name = Orders.TYPE)
@Entity(name = Orders.TYPE)
@Table(name = Orders.TYPE)
public class Orders extends Feed<Order> {

  public static final String TYPE = CorePackage.PREFIX + "Orders";

  @Override
  public Class<Order> getEntryType() {
    return Order.class;
  }

  @Override
  public Class<OrderService> getServiceType() {
    return OrderService.class;
  }

}
