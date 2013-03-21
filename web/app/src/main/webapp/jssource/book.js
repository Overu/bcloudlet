define(function(require, exports, module) {
  var utils = require('./utils');                            //图片加载完成后显示
  var common = require('./common');                          //公用文件
      common.initialize()
  
  /**
   * 当前页面导航栏高亮
   * 所有页面都需要加载
   */    
  var $ = require('jquery');   
  (function($nav,clazz){
    var _opt = [
      ['^/$']
      ,['/r/' + encodeURI('免费榜'),
        '/r/' + encodeURI('月度榜'),
        '/r/' + encodeURI('畅销榜'),
        '/r/' + encodeURI('好评榜'),
        '/r/' + encodeURI('最新')]
      ,['/r/' + encodeURI('热门推荐'),
        '/r/' + encodeURI('限免特价'),
        '/r/' + encodeURI('免费') + '$']
      ,['/c/']
    ];
    
    _opt.forEach(function(_itm,_index){
      _itm.forEach(function(_reg){
        var _reg = new RegExp(_reg);
        if(_reg.test(location.pathname))
          $nav.eq(_index).addClass(clazz);
      });
    });
      
  })($('.m-nav li'), 'crt');
      
});

