package org.cloudlet.web.core.server;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = Reference.TYPE_NAME)
@TypeDef(name = ContentType.NAME, typeClass = ContentType.class)
public class Reference {

  public static final String SOURCE = "source";
  @Id
  @Column(length = 128)
  private String id;

  @Type(type = ContentType.NAME)
  @Columns(columns = { @Column(name = "sourceType"), @Column(name = "sourceId") })
  private Item source;

  @Type(type = ContentType.NAME)
  @Columns(columns = { @Column(name = "targetType"), @Column(name = "targetId") })
  private Content target;

  private String path;

  private boolean contaiment;

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Reference";

  public String getId() {
    return id;
  }

  public String getPath() {
    return path;
  }

  public Item getSource() {
    return source;
  }

  public Content getTarget() {
    return target;
  }

  public boolean isContaiment() {
    return contaiment;
  }

  public void setContaiment(boolean contaiment) {
    this.contaiment = contaiment;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setSource(Item source) {
    this.source = source;
  }

  public void setTarget(Content target) {
    this.target = target;
  }

}
