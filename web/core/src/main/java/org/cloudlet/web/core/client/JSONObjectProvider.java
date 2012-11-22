package org.cloudlet.web.core.client;

import com.google.gwt.json.client.JSONObject;

import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.writer.DataWriter;

public interface JSONObjectProvider<M> extends DataReader<M, JSONObject>, DataWriter<M, JSONObject> {

  void read(M model, JSONObject data);

}
