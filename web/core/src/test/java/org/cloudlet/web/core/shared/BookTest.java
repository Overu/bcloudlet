package org.cloudlet.web.core.shared;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class BookTest extends WebTest {

  @Inject
  CoreResourceConfig config;

  @Inject
  Repository repo;

  @Inject
  BookFeed books;

  @Test
  public void testCreateBook() throws JAXBException {
    System.out.println(UUID.randomUUID().toString());
    books.load();
    long total = books.getChildrenCount();
    Book book = new Book();
    long nextTotal = total + 1;
    book.setTitle("Book " + nextTotal);
    books.createEntry(book);
    books.load();
    assertEquals(nextTotal, books.getChildrenCount());

    DataGraph dg = books.load();

    JAXBContext jc =
        JAXBContext.newInstance(Repository.class, DataGraph.class, GroupFeed.class, UserFeed.class,
            User.class, View.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(dg, os);
    System.out.println(os.toString());
  }

  @Override
  protected ResourceConfig configure() {
    enable(TestProperties.LOG_TRAFFIC);
    return config;
  }

}
