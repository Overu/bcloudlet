package org.cloudlet.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class ShopExplorer extends CompositeView {

  interface Binder extends UiBinder<Widget, ShopExplorer> {
  }

  private static Binder binder = GWT.create(Binder.class);

  @UiField
  HTMLPanel hd;
  @UiField
  HTMLPanel bgr;
  @UiField
  HTMLPanel bgl;
  @UiField
  HTMLPanel ft;

  @Inject
  HeaderView headerView;
  @Inject
  CategoryView categoryView;
  @Inject
  BillBoardView billBoardView;
  @Inject
  SliderView sliderView;
  @Inject
  RecommendBox recommendBox;
  @Inject
  UpdataBox updataBox;
  @Inject
  SaleBox saleBox;
  @Inject
  WrapBar wrapBar1;
  @Inject
  WrapBar wrapBar2;
  @Inject
  WrapBar wrapBar3;

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    hd.add(headerView);

    bgl.add(categoryView);
    bgl.add(billBoardView);

    bgr.add(sliderView);
    bgr.add(recommendBox);
    bgr.add(wrapBar1);
    bgr.add(updataBox);
    bgr.add(wrapBar2);
    bgr.add(saleBox);
    bgr.add(wrapBar3);
  }
}
