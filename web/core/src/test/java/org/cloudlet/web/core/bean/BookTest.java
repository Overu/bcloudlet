package org.cloudlet.web.core.bean;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.server.Book;
import org.cloudlet.web.core.server.BookFeed;
import org.cloudlet.web.core.server.GroupFeed;
import org.cloudlet.web.core.server.Media;
import org.cloudlet.web.core.server.Repository;
import org.cloudlet.web.core.server.Section;
import org.cloudlet.web.core.server.User;
import org.cloudlet.web.core.server.UserFeed;
import org.cloudlet.web.core.shared.Root;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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

    JAXBContext jc =
        JAXBContext.newInstance(Repository.class, GroupFeed.class, UserFeed.class, User.class, Media.class,
            BookFeed.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }

}
