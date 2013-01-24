package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Book)
@XmlType(name = CorePackage.Book)
@Entity(name = CorePackage.Book)
@Table(name = CorePackage.Book)
@DefaultField(key = "title", value = "图书")
public class Book extends Resource {

  @OneToOne
  private Media cover;

  @OneToOne
  private Media source;

  private boolean featured;

  private boolean promoted;

  private float price;

  private float promotionPrice;

  @OneToOne
  private BookTag tag1;

  @OneToOne
  private BookTag tag2;

  @OneToOne
  private BookTag tag3;

  private String author;

  private String copyright;

  private Date datePublished;

  private Date dateUpdated;

  private String serialNumber;

  private long size;

  public String getAuthor() {
    return author;
  }

  public String getCopyright() {
    return copyright;
  }

  @Path("cover")
  public Media getCover() {
    return cover;
  }

  public Date getDatePublished() {
    return datePublished;
  }

  public Date getDateUpdated() {
    return dateUpdated;
  }

  public float getPrice() {
    return price;
  }

  public float getPromotionPrice() {
    return promotionPrice;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Book;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public long getSize() {
    return size;
  }

  @Path("source")
  public Media getSource() {
    return source;
  }

  public BookTag getTag1() {
    return tag1;
  }

  public BookTag getTag2() {
    return tag2;
  }

  public BookTag getTag3() {
    return tag3;
  }

  public boolean isFeatured() {
    return featured;
  }

  public boolean isPromoted() {
    return promoted;
  }

  @Override
  public void readMedia(MultivaluedMap<String, Media> params) {
    super.readMedia(params);
    Media cover = params.getFirst(CorePackage.COVER);
    if (cover != null) {
      this.cover = cover;
      cover.setParent(this);
    }
    Media source = params.getFirst(CorePackage.SOURCE);
    if (source != null) {
      this.source = source;
      source.setParent(this);
    }
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public void setCover(Media cover) {
    if (cover != null) {
      cover.setParent(this);
    }
    this.cover = cover;
  }

  public void setDatePublished(Date datePublished) {
    this.datePublished = datePublished;
  }

  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setPromoted(boolean promoted) {
    this.promoted = promoted;
  }

  public void setPromotionPrice(float promotionPrice) {
    this.promotionPrice = promotionPrice;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public void setSource(Media source) {
    if (source != null) {
      source.setParent(this);
    }
    this.source = source;
  }

  public void setTag1(BookTag tag1) {
    this.tag1 = tag1;
  }

  public void setTag2(BookTag tag2) {
    this.tag2 = tag2;
  }

  public void setTag3(BookTag tag3) {
    this.tag3 = tag3;
  }

}
