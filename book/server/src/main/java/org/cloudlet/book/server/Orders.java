package org.cloudlet.book.server;

import org.cloudlet.book.server.Order.Status;
import org.cloudlet.core.server.CoreUtil;
import org.cloudlet.core.server.Folder;
import org.cloudlet.core.server.User;

import javax.persistence.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Orders.TYPE_BAME)
@Produces("text/html;qs=5")
public class Orders extends Folder<Order> {

  public static final String TYPE_BAME = CoreUtil.PREFIX + "Orders";

  @Override
  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Order doCreate(Order order) {
    return super.doCreate(order);
  }

  @Override
  public Class<Order> getEntryType() {
    return Order.class;
  }

  @Override
  public Order newContent() {
    Order order = super.newContent();
    order.setStatus(Status.PAY);
    return order;
  }
}
