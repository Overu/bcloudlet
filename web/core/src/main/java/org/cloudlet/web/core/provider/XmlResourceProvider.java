package org.cloudlet.web.core.provider;

import org.cloudlet.web.core.shared.DataGraph;
import org.cloudlet.web.core.shared.Entry;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Resource;
import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

@Produces("application/ios+xml")
@Consumes("application/ios+xml")
@Singleton
public class XmlResourceProvider extends AbstractMessageReaderWriterProvider<DataGraph<Resource>> {

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
      MediaType mediaType) {
    return true;
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
      MediaType mediaType) {
    return true;
  }

  @Override
  public DataGraph<Resource> readFrom(Class<DataGraph<Resource>> type, Type genericType,
      Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
      InputStream entityStream) throws IOException, WebApplicationException {
    return null;
  }

  @Override
  public void writeTo(DataGraph<Resource> t, Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
      OutputStream entityStream) throws IOException, WebApplicationException {
    try {
      XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(entityStream);
      writer.writeStartDocument();
      writeResource(writer, t.root);
      writer.writeEndDocument();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    }
  }

  private void writeEntry(XMLStreamWriter writer, Entry entry) throws XMLStreamException {
    writer.writeStartElement(entry.getResourceType().getName());
    writeResourceElements(writer, entry);
    if (entry.getContent() != null) {
      writer.writeCharacters(entry.getContent());
    }
    List<Resource> rels = entry.getChildren();
    if (rels != null && !rels.isEmpty()) {
      for (Resource rel : rels) {
        writeResource(writer, rel);
      }
    }
    writer.writeEndElement();
  }

  private void writeFeed(XMLStreamWriter writer, Feed<Entry> feed) throws XMLStreamException {
    writer.writeStartElement(feed.getResourceType().getName());
    writeResourceElements(writer, feed);
    if (feed.getContent() != null) {
      writer.writeCharacters(feed.getContent());
    }
    List<Entry> entries = feed.getEntries();
    if (entries != null && !entries.isEmpty()) {
      for (Entry entry : entries) {
        writeEntry(writer, entry);
      }
    }
    writer.writeEndElement();
  }

  private void writeResource(XMLStreamWriter writer, Resource resource) throws XMLStreamException {
    if (resource instanceof Entry) {
      writeEntry(writer, (Entry) resource);
    } else if (resource instanceof Feed) {
      writeFeed(writer, (Feed<Entry>) resource);
    }
  }

  private void writeResourceElements(XMLStreamWriter writer, Resource resource)
      throws XMLStreamException {
    if (resource.getTitle() != null) {
      writer.writeAttribute(Resource.TITLE, resource.getTitle());
    }
    writer.writeAttribute(Resource.PATH, resource.getPath());
    writer.writeAttribute(Resource.URI, resource.getUri());
  }
}
