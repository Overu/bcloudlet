package org.cloudlet.core.client;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;


import java.util.ArrayList;
import java.util.List;

@Singleton
public class ResourceData {

  private static final String[] TITLE = {
      "小说", "两性情感", "经管", "计算机", "科学与自然", "文学", "少儿", "生活", "传记", "旅游", "成功励志", "历史", "政治与军事", "艺术", "社会科学 ", "杂志", "教育", "外遇", "法律",
      "资讯及其他", "原创文学"};

  private static final String[] BILLBOARD = {
      "打造Facebook", "格鲁夫给经理人的第一课", "重来：更为简单有效的商业思维", "迎男而上", "写给大家看的设计书", "思考的乐趣", "正能量", "檀香刑", "旧制度与大革命", "我是个算命先生"};

  private List<Resource> category;
  private List<Resource> billboard;

  @Inject
  private ResourceData(Provider<Resource> placeProvider) {
    List<Resource> categoryList = getCategory();
    List<Resource> billBoardList = getBillBoard();
    for (int i = 0; i < TITLE.length; i++) {
      Resource resource = placeProvider.get();
      resource.setTitle(TITLE[i]);
      categoryList.add(resource);
    }
    for (int i = 0; i < BILLBOARD.length; i++) {
      Resource resource = placeProvider.get();
      resource.setTitle(BILLBOARD[i]);
      billBoardList.add(resource);
    }
  }

  public List<Resource> getBillBoard() {
    if (billboard == null) {
      billboard = new ArrayList<Resource>();
    }
    return billboard;
  }

  public List<Resource> getCategory() {
    if (category == null) {
      category = new ArrayList<Resource>();
    }
    return category;
  }

}
