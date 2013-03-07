package org.cloudlet.web.core.server;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.htmlparser.beans.StringBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
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
  RepositoryService repoSvc;

  @Inject
  BookService booksSvc;

  @Inject
  BookTagService tagsSvc;

  @Inject
  JacksonJaxbJsonProvider jsonProvider;

  private static final String DEVICE_INFO =
      "build=2012120701; device=D002-F5805035-921D-4426-BF91-81F65004FEFC; token=; userid=fantongx@gmail.com";

  private String[] dataFiles;
  private int count;

  @Before
  public void setupData() {
    String dataPath = BookTest.class.getResource("/data").getFile();
    File dataFolder = new File(dataPath);
    File[] bookFiles = dataFolder.listFiles();
    dataFiles = new String[bookFiles.length];
    for (int i = 0; i < bookFiles.length; i++) {
      File file = bookFiles[i];
      dataFiles[i] = file.getName();
    }
    System.out.println(dataFiles);
  }

  @Test
  public void testCreateBook() throws Exception {
    Books books = booksSvc.getRoot();
    books.load();
    long total = books.getTotalEntries();
    Book book = books.newEntry();
    total = total + 1;
    book.setPath("book" + total);
    book.setTitle("娱乐 " + total);
    // book.setContentStream(new
    // ByteArrayInputStream("Good work".getBytes()));
    books.createEntry(book);

    Comments comments = book.getComments();
    for (int i = 0; i < 5; i++) {
      Comment comment = new Comment();
      comment.setContent("how are you " + (i + 1));
      comments.createEntry(comment);
    }

    Media cover = new Media();
    cover.setPath("cover");
    cover.setTitle("Cover");
    InputStream stream = getClass().getResourceAsStream("/covers/sanguo.jpg");
    cover.read(stream);
    book.createReference(cover);

    book.setCover(cover);

    book.update();
    books.load();
    assertEquals(total, books.getTotalEntries());
    for (int i = 0; i < 10; i++) {
      Section section = new Section();
      section.setPath("section" + i);
      section.setTitle("第" + i + "章");
      // section.setContent("<p>北京时间11月11日晚，香港国际会展中心，跳水女皇郭晶晶与名门家族第三代霍启刚的第三场婚宴举行，包括三任香港特首、李嘉诚、刘德华、成龙、伏明霞等各界社会名流到场。这场婚礼耗时近八个小时，宾客多达1800人。</p>");
      book.createReference(section);
    }

    books.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, Groups.class, Users.class, User.class, Media.class, Books.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }

  public void testImportBook() throws Exception {
    Repository repo = repoSvc.getRoot();
    Books books = booksSvc.getRoot();
    books.load();
    long total = books.getTotalEntries();

    Random priceRandom = new Random(1000);

    StringBean sb = new StringBean();
    sb.setLinks(false);

    File folder = new File("D:\\电子书");
    for (File subFolder : folder.listFiles()) {
      String name = subFolder.getName();
      BookTag tag = (BookTag) repo.getChild(name);
      if (tag == null) {
        tag = new BookTag();
        tag.setTitle(name);
        tag.setPath(name);
        repo.createReference(tag);
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
          book.setAuthors(authors.get(0).getFirstname() + authors.get(0).getLastname());
        }

        List<String> rights = metadata.getRights();
        if (rights != null && !rights.isEmpty()) {
          book.setCopyright(rights.get(0));
        }

        book.setPrice(priceRandom.nextInt(1000));
        book.setNew_price((float) (book.getPrice() * 0.7));
        book.setFeatured(priceRandom.nextInt(1000) % 2 == 0);
        book.setPromoted(priceRandom.nextInt(1000) % 2 == 0);
        List<Date> dates = metadata.getDates();
        for (Date d : dates) {
          String str = d.getValue();
          if (str.length() == 0) {
            continue;
          }
          // java.util.Date date = new java.util.Date(java.util.Date.parse(str));
          // switch (d.getEvent()) {
          // case CREATION:
          // break;
          // case PUBLICATION:
          // book.setDatePublished(date);
          // break;
          // case MODIFICATION:
          // book.setDateUpdated(date);
          // break;
          // default:
          // break;
          // }
        }
        // book.setContentStream(new
        // ByteArrayInputStream("Good work".getBytes()));
        books.createEntry(book);

        Resource coverRes = ebook.getCoverImage();

        Media cover = new Media();
        cover.setPath("cover");
        cover.setTitle("Cover");
        InputStream stream;
        if (coverRes != null) {
          stream = coverRes.getInputStream();
        } else {
          stream = getClass().getResourceAsStream("/covers/sanguo.jpg");
        }
        cover.read(stream);
        book.createReference(cover);
        book.setCover(cover);
        book.update();
        books.load();

        for (TOCReference toc : ebook.getTableOfContents().getTocReferences()) {
          Resource res = toc.getResource();
          Section section = new Section();
          section.setPath(res.getHref());
          section.setTitle(res.getTitle());
          book.createReference(section);

          Media media = new Media();
          media.setTitle(res.getTitle());
          media.read(res.getInputStream());
          section.createReference(section);

          section.setMedia(media);
          section.update();
        }
      }
    }

    books.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, Groups.class, Users.class, User.class, Media.class, Books.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(books, os);
    System.out.println(os.toString());
  }

  @Test
  public void testImportDuoKan() throws Exception {
    importTags();
    BookTags tags = tagsSvc.getRoot();
    tags.load();
    for (BookTag tag : tags.getEntries()) {
      importBooks(tag);
    }
  }

  @Test
  public void testSchema() {
    System.out.println("ok");
  }

  private void importBooks(BookTag tag) throws IOException, HttpException, JSONException {
    HttpClient client = new HttpClient();
    String url = "http://book.duokan.com/store/v0/ios/category/" + tag.getId() + "?start=0&page_length=8";
    GetMethod httpMethod = new GetMethod(url);
    httpMethod.addRequestHeader("Cookie", DEVICE_INFO);
    // ProxyHost proxy = new ProxyHost("127.0.0.1", 8888);
    // client.getHostConfiguration().setProxyHost(proxy);
    client.executeMethod(httpMethod);
    int statusCode = httpMethod.getStatusCode();
    System.out.println(statusCode + " " + httpMethod.getStatusText() + " " + url);
    if (statusCode == 200) {
      String body = httpMethod.getResponseBodyAsString();
      System.out.println(body);
      JSONObject json = new JSONObject(body);
      JSONArray jsonArr = (JSONArray) json.get("items");
      Books books = booksSvc.getRoot();
      for (int i = 0; i < jsonArr.length(); i++) {
        JSONObject jsonObj = (JSONObject) jsonArr.get(i);
        String book_id = jsonObj.getString("book_id");
        Book book = books.getEntry(book_id);
        if (book == null) {
          book = new Book();
          book.setId(book_id);
          book.setPath(book_id);
          book.setTitle(jsonObj.getString("title"));
          book.setPrice((float) jsonObj.getDouble("price"));
          if (jsonObj.has("new_price")) {
            book.setNew_price((float) jsonObj.getDouble("new_price"));
          }
          book.setSummary(jsonObj.getString("summary"));
          book.setTag1(tag);
          books.createEntry(book);

          String coverUrl = jsonObj.getString("cover");

          Media cover = new Media();
          cover.setPath(Book.COVER);
          cover.setSource(coverUrl);
          cover.read(new URL(coverUrl).openStream());
          book.createReference(cover);
          book.setCover(cover);

          String dataPath = ("/data/" + dataFiles[(++count) % dataFiles.length]);
          Media source = new Media();
          source.setPath(Book.SOURCE);
          source.read(BookTest.class.getResourceAsStream(dataPath));
          book.createReference(source);
          book.setSource(source);

          Media full = new Media();
          full.setPath(Book.FULL);
          full.read(BookTest.class.getResourceAsStream(dataPath));
          book.createReference(full);
          book.setFull(full);

          Media trial = new Media();
          trial.setPath(Book.TRIAL);
          trial.read(BookTest.class.getResourceAsStream(dataPath));
          book.createReference(trial);
          book.setTrial(trial);

          book.update();

          importComments(book);
        } else {
          if (book.getTag1() == null) {
            book.setTag1(tag);
          } else if (book.getTag2() == null) {
            book.setTag2(tag);
          } else if (book.getTag3() == null) {
            book.setTag3(tag);
          }
          book.update();
        }
      }
    }
  }

  private void importComments(Book book) throws IOException, HttpException, JSONException {
    HttpClient client = new HttpClient();
    String bookId = book.getId();
    String url =
        "http://book.duokan.com/comment/v0/get_book_comments?app_id=web&book_id=" + bookId
            + "&device_id=D900NEXM6WII2DIX&start_index=1&count=6&order_type=1";
    GetMethod httpMethod = new GetMethod(url);
    httpMethod.addRequestHeader("Cookie", DEVICE_INFO);
    // ProxyHost proxy = new ProxyHost("127.0.0.1", 8888);
    // client.getHostConfiguration().setProxyHost(proxy);
    client.executeMethod(httpMethod);
    int statusCode = httpMethod.getStatusCode();
    System.out.println(statusCode + " " + httpMethod.getStatusText() + " " + url);
    if (statusCode == 200) {
      String charset = httpMethod.getResponseCharSet();
      String body = httpMethod.getResponseBodyAsString();
      System.out.println(body);
      JSONObject json = new JSONObject(body);
      JSONArray jsonComments = (JSONArray) json.get("comments");
      Comments comments = book.getComments();
      for (int i = 0; i < jsonComments.length(); i++) {
        JSONObject jsonComment = (JSONObject) jsonComments.get(i);
        String id = jsonComment.getString("comment_id");
        Comment comment = comments.getEntry(id);
        if (comment == null) {
          comment = new Comment();
          comment.setId(id);
          comment.setTitle(jsonComment.getString("title"));
          comment.setContent(readContent(jsonComment));
          // comment.setAuthorName(jsonComment.getString("nick_name"));
          comments.createEntry(comment);

          Replies replies = comment.getReplies();

          JSONArray jsonReplies = (JSONArray) jsonComment.get("reply");
          for (int j = 0; j < jsonReplies.length(); j++) {
            JSONObject jsonReply = (JSONObject) jsonReplies.get(j);
            String replyId = jsonReply.getString("reply_id");

            Reply reply = replies.getEntry(replyId);
            if (reply == null) {
              reply = new Reply();
              reply.setId(replyId);
              reply.setContent(readContent(jsonReply));
              // reply.setAuthorName(jsonReply.getString("nick_name"));
              replies.createEntry(reply);
            }
          }
        }
      }
    }
  }

  private void importTags() throws IOException, HttpException, JSONException {
    HttpClient client = new HttpClient();
    String url = "http://book.duokan.com/store/v0/ios/category/all";
    GetMethod httpMethod = new GetMethod(url);
    httpMethod.addRequestHeader("Cookie", DEVICE_INFO);
    // ProxyHost proxy = new ProxyHost("127.0.0.1", 8888);
    // client.getHostConfiguration().setProxyHost(proxy);
    client.executeMethod(httpMethod);
    int statusCode = httpMethod.getStatusCode();
    System.out.println(statusCode + " " + httpMethod.getStatusText() + " " + url);
    if (statusCode == 200) {
      String body = httpMethod.getResponseBodyAsString();
      System.out.println(body);
      JSONObject json = new JSONObject(body);
      JSONArray arr = (JSONArray) json.get("items");
      BookTags tags = tagsSvc.getRoot();
      for (int i = 0; i < arr.length(); i++) {
        JSONObject catJson = (JSONObject) arr.get(i);
        String id = (String) catJson.get("category_id");
        BookTag tag = tags.getEntry(id);
        if (tag == null) {
          tag = new BookTag();
          tag.setId(id);
          tag.setPath(id);
          tag.setTitle(catJson.getString("titles"));
          tag.setLabel(catJson.getString("label"));
          tag.setSummary(catJson.getString("description"));
          tags.createEntry(tag);
        }
      }
    }
  }

  // Note: 将Mysql的编码从utf8转换成utf8mb4
  // http://www.2cto.com/database/201204/127743.html
  private String readContent(JSONObject jsonComment) throws JSONException {
    String txt = jsonComment.getString("content");
    if (txt.length() > 255) {
      txt = txt.substring(0, 255);
    }
    return txt;
  }
}
