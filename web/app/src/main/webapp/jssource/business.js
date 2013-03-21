define(function(require, exports, module) {
  var $ = require('jquery'),
      util = require('./util');
  /**
   * 返回值类型为json,的普通get请求
   */
  exports.ajax = function(url,data,onsuccess,oncomplete,onerror){
    if($.isFunction(data)){
      onsuccess = data;
      oncomplete = onsuccess;
      onerror = oncomplete;
    }
      
    $.ajax({
        url : url,
        data : data,
        success : onsuccess,
        dataType : 'json',
        type : 'get',
        complete : oncomplete,
        error : onerror 
    })
  }
  /**
   * 添加分享到新浪微博的按钮
   */
  exports.addSina = function(_target,_obj){
    var param = {
      url : 'http://book.duokan.com/',         // 本书的url
      pic : '',                   // 分享图片的路径(可选)
      title : '多看精品书城',         // 分享的文字内容(可选，默认为所在页面的title)
      type : '3',
      count : '',                 // 是否显示分享数，1显示(可选)
      appkey : '3117897982',      // 您申请的应用appkey,显示分享来源(可选)
      ralateUid : '',             // 关联用户的UID，分享微博会@该用户(可选)
      language : 'zh_cn',         // 设置语言，zh_cn|zh_tw(可选)
      rnd : +new Date()
    };
    _obj = _obj || window.dk_data.share;
    param = $.extend(param,_obj);
    _target.html('<iframe allowTransparency="true" frameborder="0" scrolling="no" src="http://hits.sinajs.cn/A1/weiboshare.html?' + util.object2query(param) + '" width="16" height="16"></iframe>')
  };
  
  
});
