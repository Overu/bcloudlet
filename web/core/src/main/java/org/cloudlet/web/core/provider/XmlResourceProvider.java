package org.cloudlet.web.core.provider;

import org.cloudlet.web.core.shared.DataGraph;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Resource;
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

  private void writeResource(XMLStreamWriter writer, Resource resource) throws XMLStreamException {
    writer.writeStartElement(resource.getResourceType().getName());
    if (resource.getTitle() != null) {
      writer.writeAttribute(Resource.TITLE, resource.getTitle());
    }
    writer.writeAttribute(Resource.PATH, resource.getPath());
    writer.writeAttribute(Resource.URI, resource.getUri());
    if (resource.getContent() != null) {
      writer.writeCharacters(resource.getContent());
    }
    Collection<Resource> rels = resource.getChildren();
    if (rels != null && !rels.isEmpty()) {
      for (Resource rel : rels) {
        writeResource(writer, rel);
      }
    }
    if (resource instanceof Feed) {
      Feed feed = (Feed) resource;
      List<Resource> entries = feed.getList();
      if (entries != null && !entries.isEmpty()) {
        for (Resource entry : entries) {
          writeResource(writer, entry);
        }
      }
    }
    writer.writeEndElement();
  }
}
