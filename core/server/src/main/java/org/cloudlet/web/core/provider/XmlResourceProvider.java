package org.cloudlet.web.core.provider;

import org.cloudlet.web.core.server.Content;
import org.cloudlet.web.core.server.Item;
import org.cloudlet.web.core.server.Folder;
import org.cloudlet.web.core.server.Reference;
import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
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
public class XmlResourceProvider extends AbstractMessageReaderWriterProvider<Content> {

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  @Override
  public Content readFrom(Class<Content> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
    return null;
  }

  @Override
  public void writeTo(Content t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
    try {
      XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(entityStream);
      writer.writeStartDocument();
      writeResource(writer, t);
      writer.writeEndDocument();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    }
  }

  private void writeResource(XMLStreamWriter writer, Content content) throws XMLStreamException {
    writer.writeStartElement(content.getType());
    if (content.getTitle() != null) {
      writer.writeAttribute(Content.TITLE, content.getTitle());
    }
    writer.writeAttribute(Content.PATH, content.getPath());
    writer.writeAttribute(Content.URI, content.getUri());
    // if (content.getContent() != null) {
    // writer.writeCharacters(content.getContent());
    // }
    Collection<? extends Content> contents;
    if (content instanceof Item) {
      List<Reference> refs = ((Item) content).getReferences();
      for (Reference ref : refs) {
        writeResource(writer, ref.getTarget());
      }
    } else {
      contents = ((Folder) content).getItems();
      for (Content rel : contents) {
        writeResource(writer, rel);
      }
    }

    writer.writeEndElement();
  }
}
