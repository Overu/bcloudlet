package org.cloudlet.book.server;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import org.cloudlet.core.server.Comments;
import org.cloudlet.core.server.CoreUtil;
import org.cloudlet.core.server.Item;
import org.cloudlet.core.server.Media;
import org.cloudlet.core.server.Repository;
import org.cloudlet.core.server.Tag;
import org.cloudlet.core.server.WebPlatform;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Book.TYPE_NAME)
@Produces("text/html;qs=5")
public class Book extends Item {

  @Transactional
  static class BookService extends ContentService {
    protected void addTag(Book parent, Tag tag) {
      parent.doAddTag(tag);
    }
  }

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Book";

  public static final String SOURCE = "source";

  public static final String FULL = "full";

  public static final String COMMENTS = "comments";

  public static final String COVER = "cover";

  public static final String TRIAL = "trial";

  public static final String SIZE = "size";

  public static final String SERIAL_NUMBER = "serialNumber";

  public static final String COPYRIGHT = "copyright";

  public static final String DATE_UPDATED = "dateUpdated";

  public static final String PROMOTION_PRICE = "promotionPrice";

  public static final String HOT = "hot";

  public static final String FEATURED = "featured";

  public static final String DATE_PUBLISHED = "datePublished";

  public static final String TAG = "tag";

  public static final String TAGS = "tags";

  public static final String PROMOTED = "promoted";

  public static final String PRICE = "price";

  public static final String AUTHOR = "author";

  @OneToOne(cascade = CascadeType.ALL)
  private Media cover;

  @OneToOne(cascade = CascadeType.ALL)
  private Media source;

  @OneToOne
  private Media trial;

  @OneToOne
  private Media full;

  private boolean featured;

  private boolean promoted;

  private float price;

  private float new_price;

  private float paper_price;

  // @FormParam("tag")
  private transient Set<String> addTags;

  // @FormParam("dtag")
  private transient Set<String> deleteTags;

  @OneToOne
  private Comments comments;

  private String authors;

  private float score;

  private String editors;

  private int weight;

  private String copyright;

  private Date datePublished;

  private String serialNumber;

  private long size;

  @ManyToMany
  private Set<Tag> tags;

  @Inject
  private transient Repository repo;

  public void addTag(Tag tag) {
    WebPlatform.get().getInstance(BookService.class).addTag(this, tag);
  }

  @Override
  public void doLoad() {
    super.doLoad();
    comments.doLoad();
  }

  @Override
  public void doUpdate() {
    if (addTags != null) {
      for (String value : addTags) {
        Tag tag = repo.getTags().getOrCreateTag(value, getType());
        doAddTag(tag);
      }
    }
    if (deleteTags != null && tags != null && !tags.isEmpty()) {
      for (String value : deleteTags) {
        Tag tag = repo.getTags().getOrCreateTag(value, getType());
        doDeleteTag(tag);
      }
    }
    super.doUpdate();
  }

  public String getAuthors() {
    return authors;
  }

  @Path(COMMENTS)
  public Comments getComments() {
    return comments;
  }

  public String getCopyright() {
    return copyright;
  }

  @Path(COVER)
  public Media getCover() {
    return cover;
  }

  public String getCoverUrl() {
    if (cover != null) {
      return cover.getUriBuilder().path("cover.jpg").build().toString();
    }
    return null;
  }

  public Date getDatePublished() {
    return datePublished;
  }

  public String getEditors() {
    return editors;
  }

  @Path(FULL)
  public Media getFull() {
    return full;
  }

  public String getFullUrl() {
    if (full != null) {
      return full.getUriBuilder().path("full.zip").build().toString();
    }
    return null;
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

  public float getScore() {
    return score;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public long getSize() {
    return size;
  }

  @Path(SOURCE)
  public Media getSource() {
    return source;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  @Path(TRIAL)
  public Media getTrial() {
    return trial;
  }

  public String getTrialUrl() {
    if (trial != null) {
      return trial.getUriBuilder().path("trial.zip").build().toString();
    }
    return null;
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
    Media cover = params.getFirst(COVER);
    if (cover != null) {
      this.cover = cover;
      this.cover.setParent(this);
    }
    Media source = params.getFirst(SOURCE);
    if (source != null) {
      this.source = source;
      this.source.setParent(this);
    }
  }

  @Override
  public void readParams(MultivaluedMap<String, String> params) {
    super.readParams(params);
    String tagVal = params.getFirst(Book.TAG);
    if (tagVal != null) {
      Tag tag = repo.getTags().getOrCreateTag(tagVal, getType());
      this.addTag(tag);
    }
  }

  public void setAuthors(String author) {
    this.authors = author;
  }

  public void setComments(Comments comments) {
    this.comments = comments;
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

  public void setEditors(String editors) {
    this.editors = editors;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public void setFull(Media official) {
    this.full = official;
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

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public void setTrial(Media trial) {
    this.trial = trial;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  protected void doAddTag(Tag tag) {
    if (tags == null) {
      tags = new HashSet<Tag>();
    }
    if (tags.add(tag)) {
      tag.setWeight(tag.getWeight() + 1);
      tag.doUpdate();
    }
  }

  protected void doDeleteTag(Tag tag) {
    if (tags != null && tags.remove(tag)) {
      tag.setWeight(tag.getWeight() - 1);
      tag.doUpdate();
    }
  }

  @Override
  protected boolean doInit() {
    super.doInit();
    comments = createChild(Book.COMMENTS, Comments.class);
    return true;
  }

}
