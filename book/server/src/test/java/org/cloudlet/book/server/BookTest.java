package org.cloudlet.book.server;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

import org.cloudlet.core.server.Comment;
import org.cloudlet.core.server.Comments;
import org.cloudlet.core.server.CoreTest;
import org.cloudlet.core.server.CoreUtil;
import org.cloudlet.core.server.Groups;
import org.cloudlet.core.server.Media;
import org.cloudlet.core.server.Repository;
import org.cloudlet.core.server.Tag;
import org.cloudlet.core.server.Tags;
import org.cloudlet.core.server.User;
import org.cloudlet.core.server.Users;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class BookTest extends CoreTest {

  public static void main(String[] args) {
    UUID id1 = CoreUtil.parseUUID("91b7221c-6b92-11e2-8088-00163e0123ac");
    System.out.println(id1);
    UUID id2 = CoreUtil.parseUUID("91b7221c6b9211e2808800163e0123ac");
    System.out.println(id2);
    System.out
        .println(URLDecoder
            .decode("http%3A%2F%2Fbook.duokan.com%2Fstore%2Fv0%2Fios%2Fwww%2Fipad_detail.html%3Fbook_id%3D66fff13e6a0211e299d100163e0123ac%26title%3D%25E5%2594%2590%25E7%25AB%258B%25E6%25B7%25872013%25E6%2598%259F%25E5%25BA%25A7%25E8%25BF%2590%25E7%25A8%258B"));
  }

  @Inject
  BookStore repo;

  @Inject
  JacksonJaxbJsonProvider jsonProvider;

  private static final String DEVICE_INFO =
      "build=2012120701; device=D002-F5805035-921D-4426-BF91-81F65004FEFC; token=; userid=fantongx@gmail.com";

  @Inject
  BookImporter importer;

  @Test
  public void testCreateBook() throws Exception {
    Books books = repo.getBooks();
    books.load();
    long total = books.getCount();
    Book book = books.newEntry();
    total = total + 1;
    book.setPath("book" + total);
    book.setTitle("娱乐 " + total);
    // book.setContentStream(new
    // ByteArrayInputStream("Good work".getBytes()));

    Tags tags = repo.getTags();
    Tag tag = new Tag();
    tag.setTitle("example tag");
    tag.setValue("tag" + System.currentTimeMillis());
    tag.setTargetType(Book.TYPE_NAME);
    tags.createChild(tag);

    books.createChild(book);

    book.addTag(tag);

    Comments comments = book.getComments();
    for (int i = 0; i < 5; i++) {
      Comment comment = new Comment();
      comment.setContent("how are you " + (i + 1));
      comments.createChild(comment);
    }

    Media cover = new Media();
    cover.setPath("cover");
    cover.setTitle("Cover");
    InputStream stream = getClass().getResourceAsStream("/covers/sanguo.jpg");
    cover.read(stream);
    book.createChild(cover);

    book.setCover(cover);

    book.update();
    books.load();
    assertEquals(total, books.getCount());
    for (int i = 0; i < 10; i++) {
      Section section = new Section();
      section.setPath("section" + i);
      section.setTitle("第" + i + "章");
      // section.setContent("<p>北京时间11月11日晚，香港国际会展中心，跳水女皇郭晶晶与名门家族第三代霍启刚的第三场婚宴举行，包括三任香港特首、李嘉诚、刘德华、成龙、伏明霞等各界社会名流到场。这场婚礼耗时近八个小时，宾客多达1800人。</p>");
      book.createChild(section);
    }

    books.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, Groups.class, Users.class, User.class, Media.class, Books.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }

  @Test
  public void testImportDuoKanBook() throws Exception {
    importer.setWriter(new PrintWriter(System.out));
    importer.importDuoKan();
  }
}
