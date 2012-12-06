package org.cloudlet.web.core.client;

import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;

import org.cloudlet.web.core.service.FeedBean;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public class QueryBuilder {

  public final static String START = "start";
  public final static String LIMIT = "limit";

  private static QueryBuilder INSTANCE;

  public static QueryBuilder get(MultivaluedMap<String, String> params) {
    INSTANCE = new QueryBuilder(params);
    return INSTANCE;
  }

  private MultivaluedMap<String, String> params;

  private QueryBuilder(MultivaluedMap<String, String> params) {
    this.params = params;
  }

  public void buildQuery(String type, String field, String value) {
    StringBuilder sb = new StringBuilder();
    sb.append(field).append("|").append(value);
    params.add(type, sb.toString());
  }

  public void clear() {
    params.remove(LIMIT);
    params.remove(START);
    params.remove(FeedBean.SORT);
    params.remove(FeedBean.SEARCH);
  }

  public void filter(FilterPagingLoadConfig loadConfig) {
    for (FilterConfig filter : loadConfig.getFilters()) {
      String field = filter.getField();
      String test = filter.getValue();
      buildQuery(FeedBean.SEARCH, field, test);
    }
  }

  public void limit(String limit, String offset) {
    params.putSingle(LIMIT, limit);
    params.putSingle(START, offset);
  }

  public void sort(PagingLoadConfig config) {
    List<? extends SortInfo> sorts = config.getSortInfo();
    if (sorts.size() > 0) {
      for (SortInfo sort : sorts) {
        buildQuery(FeedBean.SORT, sort.getSortField(), sort.getSortDir().name());
      }
    }
  }

}
