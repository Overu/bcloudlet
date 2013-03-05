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
public class Book extends Entry {

  @OneToOne
  private Media cover;

  @OneToOne
  private Media source;

  private boolean featured;

  private boolean promoted;

  private float price;

  private float new_price;

  private float paper_price;

  @OneToOne
  private BookTag tag1;

  @OneToOne
  private BookTag tag2;

  @OneToOne
  private BookTag tag3;

  private String authors;

  private float score;

  private String editors;

  private int weight;

  private String copyright;

  private Date datePublished;

  private String serialNumber;

  private long size;

  public String getAuthors() {
    return authors;
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

  public String getEditors() {
    return editors;
  }

  public float getNew_price() {
    return new_price;
  }

  public float getPaper_price() {
    return paper_price;
  }

  public float getPrice() {
    return price;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Book;
  }

  public float getScore() {
    return score;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  @Override
  public Class<BookService> getServiceType() {
    return BookService.class;
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

  public int getWeight() {
    return weight;
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

  public void setAuthors(String author) {
    this.authors = author;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public void setCover(Media cover) {
    this.cover = cover;
  }

  public void setDatePublished(Date datePublished) {
    this.datePublished = datePublished;
  }

  /**
   * @param editors the editors to set
   */
  public void setEditors(String editors) {
    this.editors = editors;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public void setNew_price(float promotionPrice) {
    this.new_price = promotionPrice;
  }

  public void setPaper_price(float paper_price) {
    this.paper_price = paper_price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setPromoted(boolean promoted) {
    this.promoted = promoted;
  }

  /**
   * @param score the score to set
   */
  public void setScore(float score) {
    this.score = score;
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

  /**
   * @param weight the weight to set
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }

}
