package org.cloudlet.web.core.client.mobile;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.web.core.client.Resource;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ResourceData {

  private static final String[] title = {
      "小说", "两性情感", "经管", "计算机", "科学与自然", "文学", "少儿", "生活", "传记", "旅游", "成功励志", "历史", "政治与军事", "艺术", "社会科学 ", "杂志", "教育", "外遇", "法律",
      "资讯及其他", "原创文学"};

  private List<Resource> category;

  @Inject
  private ResourceData(Provider<Resource> placeProvider) {
    List<Resource> categoryList = getCategory();
    for (int i = 0; i < title.length; i++) {
      Resource resource = placeProvider.get();
      resource.setTitle(title[i]);
      categoryList.add(resource);
    }
  }

  public List<Resource> getCategory() {
    if (category == null) {
      category = new ArrayList<Resource>();
    }
    return category;
  }

}
