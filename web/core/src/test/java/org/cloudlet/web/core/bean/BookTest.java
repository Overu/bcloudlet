package org.cloudlet.web.core.bean;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.service.BookBean;
import org.cloudlet.web.core.service.BookFeedBean;
import org.cloudlet.web.core.service.GroupFeedBean;
import org.cloudlet.web.core.service.MediaBean;
import org.cloudlet.web.core.service.RepositoryBean;
import org.cloudlet.web.core.service.SectionBean;
import org.cloudlet.web.core.service.UserBean;
import org.cloudlet.web.core.service.UserFeedBean;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class BookTest extends CoreTest {

  @Inject
  RepositoryBean repo;

  @Root
  @Inject
  BookFeedBean books;

  @Test
  public void testCreateBook() throws Exception {
    System.out.println(UUID.randomUUID().toString());
    books.load();
    long total = books.getChildrenCount();
    BookBean book = books.newEntry();
    total = total + 1;
    book.setPath("book" + total);
    book.setTitle("娱乐 " + total);
    // book.setContentStream(new
    // ByteArrayInputStream("Good work".getBytes()));
    books.createEntry(book);

    MediaBean cover = book.create(MediaBean.class);
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
      SectionBean section = book.create(SectionBean.class);
      section.setPath("section" + i);
      section.setTitle("第" + i + "章");
      section.setContent("<p>北京时间11月11日晚，香港国际会展中心，跳水女皇郭晶晶与名门家族第三代霍启刚的第三场婚宴举行，包括三任香港特首、李嘉诚、刘德华、成龙、伏明霞等各界社会名流到场。这场婚礼耗时近八个小时，宾客多达1800人。</p>");
      book.createChild(section);
    }

    books.load();

    JAXBContext jc =
        JAXBContext.newInstance(RepositoryBean.class, GroupFeedBean.class, UserFeedBean.class, UserBean.class, MediaBean.class,
            BookFeedBean.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }

}
