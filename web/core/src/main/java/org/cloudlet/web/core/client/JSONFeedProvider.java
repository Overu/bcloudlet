package org.cloudlet.web.core.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Resource;

import java.util.ArrayList;
import java.util.List;

public class JSONFeedProvider<F extends Feed<E>, E extends Resource> extends
    JSONResourceProvider<F> {

  @Override
  public void read(F feed, JSONObject data) {
    super.read(feed, data);
    JSONValue value = data.get(Feed.ENTRIES);
    if (value != null) {
      List<E> entries = new ArrayList<E>();
      JSONArray array = value.isArray();
      if (array != null) {
        for (int i = 0; i < array.size(); i++) {
          JSONObject object = array.get(i).isObject();
          E child = (E) JSONResourceProvider.readResource(object);
          entries.add(child);
          child.setParent(feed.getSelf());
        }
      } else {
        JSONObject object = value.isObject();
        E child = (E) JSONResourceProvider.readResource(object);
        entries.add(child);
        child.setParent(feed.getSelf());
      }
      feed.setEntries(entries);
    }
  }

  @Override
  public JSONObject write(F model) {
    JSONObject json = super.write(model);
    return json;
  }

}
