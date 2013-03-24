package org.cloudlet.book.server;

import java.util.ArrayList;
import java.util.List;

public enum BookRank {

  HOT("畅销榜", "hot", RankedBooks.class), MONTHLY("月度榜", "monthly", RankedBooks.class), RATED("好评榜", "rated", RankedBooks.class), FREE("免费榜",
      "free", RankedBooks.class), RECOMMENDATION("精品推荐", "recommendation", BargainBooks.class), NEWSALE("最新特价", "newsale",
      BargainBooks.class), FREEZONE("免费专区", "freezone", BargainBooks.class), LATEST("最新上架", "latest", BargainBooks.class);

  public static BookRank getByPath(String path) {
    for (BookRank rank : values()) {
      if (rank.path.equals(path)) {
        return rank;
      }
    }
    return null;
  }

  public static <T extends SalesBooks> List<BookRank> getRanksByClass(Class<T> clz) {
    List<BookRank> ranks = new ArrayList<BookRank>();
    for (BookRank rank : values()) {
      if (rank.getType().equals(clz)) {
        ranks.add(rank);
      }
    }
    return ranks;
  }

  public final String title;

  public final String path;

  public final Class<? extends SalesBooks> type;

  private BookRank(String title, String path, Class<? extends SalesBooks> type) {
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
  public Class<? extends SalesBooks> getType() {
    return type;
  }

}