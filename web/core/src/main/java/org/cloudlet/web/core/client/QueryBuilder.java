package org.cloudlet.web.core.client;

import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;

import org.cloudlet.web.core.shared.CorePackage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public class QueryBuilder {

  public final static String START = "start";
  public final static String LIMIT = "limit";

  public static QueryBuilder get(MultivaluedMap<String, String> params) {
    return new QueryBuilder(params);
  }

  private MultivaluedMap<String, String> params;
  private List<String> fields;

  private QueryBuilder(MultivaluedMap<String, String> params) {
    this.params = params;
    fields = new ArrayList<String>();
  }

  public void buildQuery(String type, String field, String value) {
    StringBuilder sb = new StringBuilder();
    sb.append(field).append("|").append(value);
    setParam(type, sb.toString(), false);
  }

  public void clear() {
    if (fields == null || fields.size() == 0) {
      return;
    }
    for (String field : fields) {
      params.remove(field);
    }
  }

  public String encode(String strIn, String targetCode) {
    String strOut;
    if (strIn == null || strIn.equals("")) {
      return strIn;
    }
    try {
      strOut = new String(strIn.getBytes(), targetCode);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
    return strOut;
  }

  public void filter(List<FilterConfig> filters) {
    for (FilterConfig filter : filters) {
      String field = filter.getField();
      String test = filter.getValue();
      buildQuery(CorePackage.SEARCH, field, test);
    }
  }

  public void limit(String limit, String offset) {
    setParam(LIMIT, limit, true);
    setParam(START, offset, true);
  }

  public void sort(List<? extends SortInfo> sorts) {
    for (SortInfo sort : sorts) {
      buildQuery(CorePackage.SORT, sort.getSortField(), sort.getSortDir().name());
    }
  }

  private void setParam(String type, String value, boolean isSingle) {
    String encodeStr = encode(value, "GBK");
    if (isSingle) {
      params.putSingle(type, encodeStr);
    } else {
      params.add(type, encodeStr);
    }
    fields.add(type);
  }

}
