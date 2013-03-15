package org.cloudlet.web.core.server;

public enum BookRank {

  HOT("畅销榜", "hot", BookQuery.class), MONTHLY("月度榜", "monthly", BookQuery.class), RATED("好评榜", "rated", BookQuery.class), FREE("免费榜",
      "free", BookQuery.class), LATEST("最新上架", "latest", BookQuery.class);

  public static BookRank getByPath(String path) {
    for (BookRank rank : values()) {
      if (rank.path.equals(path)) {
        return rank;
      }
    }
    return null;
  }

  public final String title;

  public final String path;

  public final Class<? extends BookQuery> type;

  private BookRank(String title, String path, Class<? extends BookQuery> type) {
    this.title = title;
    this.path = path;
    this.type = type;
  }

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the type
   */
  public Class<? extends BookQuery> getType() {
    return type;
  }

}