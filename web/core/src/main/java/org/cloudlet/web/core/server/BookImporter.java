package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class BookImporter {

  PrintWriter writer;

  @Inject
  Provider<Repository> repo;

  private static final String DEVICE_INFO =
      "build=2012120701; device=D002-F5805035-921D-4426-BF91-81F65004FEFC; token=; userid=fantongx@gmail.com";

  private String[] dataFiles;

  private int count;

  public PrintWriter getWriter() {
    return writer;
  }

  public void importDuoKan() throws Exception {
    setup();
    importTags();
    Tags tags = repo.get().getTags();
    tags.load();
    for (Tag tag : tags.getEntries()) {
      importBooks(tag);
    }
  }

  public void setup() {
    File dataFolder = new File(CoreUtil.getDataLocation() + "/source");
    String path = dataFolder.getAbsolutePath();
    writer.println(path);
    File[] bookFiles = dataFolder.listFiles();
    dataFiles = new String[bookFiles.length];
    for (int i = 0; i < bookFiles.length; i++) {
      File file = bookFiles[i];
      dataFiles[i] = file.getName();
    }
    writer.println(dataFiles);
  }

  public void setWriter(PrintWriter writer) {
    this.writer = writer;
  }

  private void importBooks(Tag tag) throws IOException, HttpException, JSONException {
    HttpClient client = new HttpClient();
    String url = "http://book.duokan.com/store/v0/ios/category/" + tag.getId() + "?start=0&page_length=8";
    GetMethod httpMethod = new GetMethod(url);
    httpMethod.addRequestHeader("Cookie", DEVICE_INFO);
    // ProxyHost proxy = new ProxyHost("127.0.0.1", 8888);
    // client.getHostConfiguration().setProxyHost(proxy);
    client.executeMethod(httpMethod);
    int statusCode = httpMethod.getStatusCode();
    writer.println(statusCode + " " + httpMethod.getStatusText() + " " + url);
    if (statusCode == 200) {
      String body = httpMethod.getResponseBodyAsString();
      writer.println(body);
      JSONObject json = new JSONObject(body);
      JSONArray jsonArr = (JSONArray) json.get("items");
      Books books = repo.get().getBooks();
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

          Set<Tag> bTags = new HashSet<Tag>();
          bTags.add(tag);
          book.setTags(bTags);
          books.createEntry(book);

          String coverUrl = jsonObj.getString("cover");

          Media cover = new Media();
          cover.setPath(Book.COVER);
          cover.setSource(coverUrl);
          cover.read(new URL(coverUrl).openStream());
          book.createReference(cover);
          book.setCover(cover);

          String dataPath = (CoreUtil.getDataLocation() + "/source/" + dataFiles[(++count) % dataFiles.length]);
          Media source = new Media();
          source.setPath(Book.SOURCE);
          source.read(new FileInputStream(dataPath));
          book.createReference(source);
          book.setSource(source);

          Media full = new Media();
          full.setPath(Book.FULL);
          full.read(new FileInputStream(dataPath));
          book.createReference(full);
          book.setFull(full);

          Media trial = new Media();
          trial.setPath(Book.TRIAL);
          trial.read(new FileInputStream(dataPath));
          book.createReference(trial);
          book.setTrial(trial);

          book.update();

          importComments(book);
        } else {
          Set<Tag> tags = book.getTags();
          tags.add(tag);
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
    writer.println(statusCode + " " + httpMethod.getStatusText() + " " + url);
    if (statusCode == 200) {
      String charset = httpMethod.getResponseCharSet();
      String body = httpMethod.getResponseBodyAsString();
      writer.println(body);
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
    writer.println(statusCode + " " + httpMethod.getStatusText() + " " + url);
    if (statusCode == 200) {
      String body = httpMethod.getResponseBodyAsString();
      writer.println(body);
      JSONObject json = new JSONObject(body);
      JSONArray arr = (JSONArray) json.get("items");
      Tags tags = repo.get().getTags();
      for (int i = 0; i < arr.length(); i++) {
        JSONObject catJson = (JSONObject) arr.get(i);
        String id = (String) catJson.get("category_id");
        Tag tag = tags.getEntry(id);
        if (tag == null) {
          tag = new Tag();
          tag.setId(id);
          tag.setPath(id);
          tag.setTitle(catJson.getString("titles"));
          tag.setValue(catJson.getString("label"));
          tag.setSummary(catJson.getString("description"));
          tag.setTargetType(Book.TYPE_NAME);
          tags.createEntry(tag);
        }
      }
    }
  }

  // Note: 将Mysql的编码从utf8转换成utf8mb4
  // http://www.cnblogs.com/vincentchan/archive/2012/09/25/2701266.html
  private String readContent(JSONObject jsonComment) throws JSONException {
    String txt = jsonComment.getString("content");
    if (txt.length() > 255) {
      txt = txt.substring(0, 255);
    }
    return txt;
  }
}
