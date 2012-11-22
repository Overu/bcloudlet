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
    JSONArray records;
    List<E> entries = new ArrayList<E>();
    if (value != null && (records = value.isArray()) != null) {
      for (int i = 0; i < records.size(); i++) {
        JSONObject json = records.get(i).isObject();
        E user = (E) JSONResourceProvider.readResource(json);
        user.setParent(feed.getSelf());
        entries.add(user);
      }
    }
    feed.setEntries(entries);
  }

  @Override
  public JSONObject write(F model) {
    JSONObject json = super.write(model);
    return json;
  }

}
