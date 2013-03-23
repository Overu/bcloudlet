package org.cloudlet.web.core.client.mobile;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.web.core.client.Initializer;
import org.cloudlet.web.core.client.Registry;
import org.cloudlet.web.core.server.Books;
import org.cloudlet.web.core.server.Content;
import org.cloudlet.web.core.server.Repository;

@Singleton
public class MobileApp extends Initializer {

  @Inject
  Provider<MobileExplorer> explorer;

  @Inject
  Provider<BookFeedViewer> recommended;

  @Inject
  Provider<BookDetail> bookDetail;
  @Inject
  Provider<HeaderView> headerView;
  @Inject
  Provider<CategoryView> categoryView;
  @Inject
  Provider<BillBoardView> billBoardView;
  @Inject
  Provider<SliderView> sliderView;
  @Inject
  Provider<RecommendBox> recommendBox;
  @Inject
  Provider<UpdataBox> updataBox;
  @Inject
  Provider<SaleBox> saleBox;
  @Inject
  Provider<ShopExplorer> shopExplorer;

  @Override
  protected void init() {
    Registry.setWidget(Repository.TYPE_NAME, Content.HOME, explorer);
    Registry.setWidget(Books.TYPE_NAME, Content.HOME, recommended);
    Registry.setWidget(Books.TYPE_NAME, Content.EDIT, bookDetail);
  }
}