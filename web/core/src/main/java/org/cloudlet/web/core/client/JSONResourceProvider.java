package org.cloudlet.web.core.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.WebPlatform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONResourceProvider<T extends Resource> implements JSONObjectProvider<T> {

  public static boolean readBoolean(JSONObject data, String field) {
    Boolean b = readBooleanObject(data, field);
    return b == null ? false : b.booleanValue();
  }

  public static Boolean readBooleanObject(JSONObject data, String field) {
    JSONValue value = data.get(field);
    if (value != null) {
      return value.isBoolean().booleanValue();
    }
    return null;
  }

  public static Date readDate(JSONObject data, String field) {
    Double d = readDoubleObject(data, field);
    if (d != null) {
      return new Date(d.longValue());
    }
    return null;
  }

  public static double readDouble(JSONObject data, String field) {
    Double d = readDoubleObject(data, field);
    if (d != null) {
      return d.doubleValue();
    }
    return 0;
  }

  public static Double readDoubleObject(JSONObject data, String field) {
    JSONValue value = data.get(field);
    if (value != null) {
      return value.isNumber().doubleValue();
    }
    return null;
  }

  public static int readInt(JSONObject data, String field) {
    Double d = readDoubleObject(data, field);
    if (d != null) {
      return d.intValue();
    }
    return 0;
  }

  public static Integer readInteger(JSONObject data, String field) {
    Double d = readDoubleObject(data, field);
    if (d != null) {
      return d.intValue();
    }
    return null;
  }

  public static long readLong(JSONObject data, String field) {
    Double d = readDoubleObject(data, field);
    if (d != null) {
      return d.longValue();
    }
    return 0;
  }

  public static Long readLongObject(JSONObject data, String field) {
    Double d = readDoubleObject(data, field);
    if (d != null) {
      return d.longValue();
    }
    return null;
  }

  public static Resource readResource(JSONObject json) {
    String type;
    if (json.containsKey("@xsi.type")) {
      type = json.get("@xsi.type").isString().stringValue();
    } else {
      type = json.keySet().iterator().next();
      json = json.get(type).isObject();
    }
    ResourceType objectType = WebPlatform.getInstance().getResourceType(type);
    Resource resource = objectType.createInstance();
    readResource(resource, json);
    return resource;
  }

  public static void readResource(Resource resource, JSONObject json) {
    JSONObjectProvider<Resource> provider =
        resource.getResourceType().getProvider(JSONObjectProvider.class);
    provider.read(resource, json);
    resource.setNativeData(json);
  }

  public static void readRoot(Resource resource, JSONObject json) {
    String type = json.keySet().iterator().next();
    json = json.get(type).isObject();
    readResource(resource, json);
  }

  public static String readString(JSONObject data, String field) {
    JSONValue value = data.get(field);
    if (value != null) {
      if (value.isString() != null) {
        return value.isString().stringValue();
      }
      if (value.isNumber() != null) {
        Double.toString(value.isNumber().doubleValue());
      }
      if (value.isNull() != null) {
        return null;
      }
    }
    return null;
  }

  public static JSONObject writeResource(Resource res) {
    JSONObject root = new JSONObject();

    ResourceType<? extends Resource> type = res.getResourceType();
    JSONObjectProvider<Resource> provider = type.getProvider(JSONObjectProvider.class);
    JSONObject json = provider.write(res);
    root.put(type.getName(), json);
    return root;
  }

  public static void writeString(JSONObject json, String field, String email) {
    if (email != null) {
      json.put(field, new JSONString(email));
    }
  }

  @Override
  public T read(Object loadConfig, JSONObject data) {
    T model = (T) loadConfig;
    read(model, data);
    return model;
  }

  @Override
  public void read(T model, JSONObject data) {
    model.setId(readString(data, Resource.ID));
    model.setPath(readString(data, Resource.PATH));
    model.setTitle(readString(data, Resource.TITLE));
    model.setChildrenCount(readLong(data, Resource.CHILDREN_COUNT));

    JSONValue value = data.get(Resource.CHILDREN);
    if (value != null) {
      List<Resource> children = new ArrayList<Resource>();
      JSONArray array = value.isArray();
      if (array != null) {
        for (int i = 0; i < array.size(); i++) {
          JSONObject object = array.get(i).isObject();
          Resource child = JSONResourceProvider.readResource(object);
          children.add(child);
          child.setParent(model.getSelf());
        }
      } else {
        JSONObject object = value.isObject();
        Resource child = JSONResourceProvider.readResource(object);
        children.add(child);
        child.setParent(model.getSelf());
      }
      model.setChildren(children);
    }
  }

  @Override
  public JSONObject write(T model) {
    JSONObject json = new JSONObject();
    json.put("@xsi.type", new JSONString(model.getResourceType().getName()));
    if (model.getId() != null) {
      json.put(Resource.ID, new JSONString(model.getId()));
    }
    if (model.getPath() != null) {
      json.put(Resource.PATH, new JSONString(model.getPath()));
    }
    if (model.getTitle() != null) {
      json.put(Resource.TITLE, new JSONString(model.getTitle()));
    }
    return json;
  }

}
