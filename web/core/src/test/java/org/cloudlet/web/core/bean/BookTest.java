package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

import org.cloudlet.web.core.server.Book;
import org.cloudlet.web.core.server.BookFeed;
import org.cloudlet.web.core.server.BookTag;
import org.cloudlet.web.core.server.GroupFeed;
import org.cloudlet.web.core.server.Media;
import org.cloudlet.web.core.server.Repository;
import org.cloudlet.web.core.server.Section;
import org.cloudlet.web.core.server.User;
import org.cloudlet.web.core.server.UserFeed;
import org.cloudlet.web.core.shared.Root;
import org.htmlparser.beans.StringBean;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class BookTest extends CoreTest {

  @Inject
  Repository repo;

  @Root
  @Inject
  BookFeed books;

  @Test
  public void testCreateBook() throws Exception {
    System.out.println(UUID.randomUUID().toString());
    books.load();
    long total = books.getChildrenCount();
    Book book = books.newEntry();
    total = total + 1;
    book.setPath("book" + total);
    book.setTitle("娱乐 " + total);
    // book.setContentStream(new
    // ByteArrayInputStream("Good work".getBytes()));
    books.createEntry(book);

    Media cover = book.create(Media.class);
    cover.setPath("cover");
    cover.setTitle("Cover");
    InputStream stream = getClass().getResourceAsStream("/covers/sanguo.jpg");
    cover.read(stream);
    cover.save();

    book.setCover(cover);
    book.save();
    books.load();
    assertEquals(total, books.getChildrenCount());
    for (int i = 0; i < 10; i++) {
      Section section = book.create(Section.class);
      section.setPath("section" + i);
      section.setTitle("第" + i + "章");
      section.setContent("<p>北京时间11月11日晚，香港国际会展中心，跳水女皇郭晶晶与名门家族第三代霍启刚的第三场婚宴举行，包括三任香港特首、李嘉诚、刘德华、成龙、伏明霞等各界社会名流到场。这场婚礼耗时近八个小时，宾客多达1800人。</p>");
      book.createChild(section);
    }

    books.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, GroupFeed.class, UserFeed.class, User.class, Media.class, BookFeed.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }

  @Test
  public void testImportBook() throws Exception {
    System.out.println(UUID.randomUUID().toString());
    books.load();
    long total = books.getChildrenCount();

    Random priceRandom = new Random(1000);

    StringBean sb = new StringBean();
    sb.setLinks(false);

    File folder = new File("D:\\电子书");
    for (File subFolder : folder.listFiles()) {
      String name = subFolder.getName();
      BookTag tag = (BookTag) books.getByPath(name);
      if (tag == null) {
        tag = books.create(BookTag.class);
        tag.setTitle(name);
        tag.setPath(name);
        tag.save();
      }
      for (File epubFile : subFolder.listFiles()) {

        FileInputStream fis = new FileInputStream(epubFile);
        nl.siegmann.epublib.domain.Book ebook = new EpubReader().readEpub(fis);

        Book book = books.newEntry();
        total = total + 1;
        book.setPath("book" + total);
        book.setTitle(ebook.getTitle());

        book.setTag1(tag);

        Metadata metadata = ebook.getMetadata();

        List<Author> authors = metadata.getAuthors();
        if (authors != null && !authors.isEmpty()) {
          book.setAuthor(authors.get(0).getFirstname() + authors.get(0).getLastname());
        }

        List<String> rights = metadata.getRights();
        if (rights != null && !rights.isEmpty()) {
          book.setCopyright(rights.get(0));
        }

        book.setPrice(priceRandom.nextInt(1000));
        book.setPromotionPrice((float) (book.getPrice() * 0.7));
        book.setFeatured(priceRandom.nextInt(1000) % 2 == 0);
        book.setPromoted(priceRandom.nextInt(1000) % 2 == 0);
        List<Date> dates = metadata.getDates();
        for (Date d : dates) {
          String str = d.getValue();
          if (str.length() == 0) {
            continue;
          }
          java.util.Date date = new java.util.Date(java.util.Date.parse(str));
          switch (d.getEvent()) {
          case CREATION:
            break;
          case PUBLICATION:
            book.setDatePublished(date);
            break;
          case MODIFICATION:
            book.setDateUpdated(date);
            break;
          default:
            break;
          }
        }
        // book.setContentStream(new
        // ByteArrayInputStream("Good work".getBytes()));
        books.createEntry(book);

        Resource coverRes = ebook.getCoverImage();

        Media cover = book.create(Media.class);
        cover.setPath("cover");
        cover.setTitle("Cover");
        InputStream stream;
        if (coverRes != null) {
          stream = coverRes.getInputStream();
        } else {
          stream = getClass().getResourceAsStream("/covers/sanguo.jpg");
        }
        cover.read(stream);
        cover.save();
        book.setCover(cover);
        book.save();
        books.load();

        for (TOCReference toc : ebook.getTableOfContents().getTocReferences()) {
          Resource res = toc.getResource();
          Section section = book.create(Section.class);
          section.setPath(res.getHref());
          section.setTitle(res.getTitle());
          book.createChild(section);

          Media media = section.create(Media.class);
          section.setMedia(media);
          media.setTitle(res.getTitle());
          media.read(res.getInputStream());
          media.save();

          section.save();
        }
      }
    }

    books.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, GroupFeed.class, UserFeed.class, User.class, Media.class, BookFeed.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }
}
