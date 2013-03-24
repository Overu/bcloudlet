define(function(require, exports, module) {
    var _platform  = window.navigator.platform,
        _useragent = window.navigator.userAgent;
    /**
     * 平台判断信息
     * 
     * [ntb]
     *  参数名称       | 参数类型          | 参数描述
     *  ------------------------------------
     *  mac      | Boolean    | is mac os
     *  win      | Boolean    | is windows os
     *  linux    | Boolean    | is linux os
     *  ipad     | Boolean    | is ipad device
     *  iphone   | Boolean    | is iphone device
     *  android  | Boolean    | is android system
     *  ios      | Boolean    | is ios system
     *  tablet   | Boolean    | is tablet
     *  desktop  | Boolean    | is desktop env
     * [/ntb]
     * 
     * @const  {nej.p._$IS}
     * @type   {Object}
     */
    var _is = {
        mac        : _platform
       ,win        : _platform
       ,linux      : _platform
       ,ipad       : _useragent
       ,ipod       : _useragent
       ,iphone     : _platform
       ,android    : _useragent
    };
    for(var x in _is)
        _is[x] = new RegExp(x,'i').test(_is[x]);
    _is.ios = _is.ipad||_is.iphone||_is.ipod;
    _is.tablet = _is.ipad;
    _is.desktop = _is.mac||_is.win||(_is.linux&&!_is.android);
    
    var $ = require('jquery');
    
    exports.browser = {
      gecko : $.browser.gecko
     ,webkit: $.browser.webkit
     ,presto: $.browser.presto
     // split trident with ie10(html5/css3 support)
     ,trident  : $.browser.msie && +$.browser.version == 10
     ,trident1 : $.browser.msie && +$.browser.version > 6 && $.browser.version < 10
     // fix for ie6
     ,trident2 : $.browser.msie && +$.browser.version <= 6
    }
    
    exports.platform = _is;   
});