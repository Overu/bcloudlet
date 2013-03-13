define(function(require, exports, module) {
  var $$LoadMore= require('../widget/loadmore/loadmore');
  
  var loadMore = new $$LoadMore({
    target : 'paybook',
    tpl : 'jst-more',
    offset : 30,
    url : '/store/v0/web/hot_pay'
  });
  
});
  
  // r = new Rank('畅销榜','#paybook','/store/v0/web/hot_pay',300,'yes');});