package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Orders.TYPE_BAME)
public class Orders extends Feed<Order> {

  public static final String TYPE_BAME = CoreUtil.PREFIX + "Orders";

  @Override
  public Class<Order> getEntryType() {
    return Order.class;
  }

}
