define(function(require, exports, module) {
  var statistic = function() {
    // 屏蔽 ctest.duokan.com 
    if(location.href.indexOf('ctest') != -1) return;
    window._hmt = window._hmt || [];
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?2d8f9b400e4b1e50b94358d8bb45b3de";
    var s = document.getElementsByTagName("script")[0]; 
    s.parentNode.insertBefore(hm, s);
  }
  
  return statistic;
});