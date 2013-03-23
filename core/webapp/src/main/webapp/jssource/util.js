define(function(require, exports, module) {
  var $ = require('jquery'),
      util = exports;
      
  util.object2string = function(_object,_split,_encode){
    if (!_object) return '';
    var _arr = [];
    for(var x in _object){
        _arr.push(encodeURIComponent(x)+'='+(!!_encode?
                  encodeURIComponent(_object[x]):_object[x]));
    }
    return _arr.join(_split||',');
  };
  
  util.object2query = function(_object){
    return util.object2string(_object,'&',!0);
  };
  
  util.string2object = function(_string,_split){
    var _obj = {};
    (_string||'').split(_split).forEach(
    function(_name){
        var _brr = _name.split('=');
        if (!_brr||!_brr.length) return;
        var _key = _brr.shift();
        if (!_key) return;
        _obj[decodeURIComponent(_key)] = 
             decodeURIComponent(_brr.join('='));
    });
    return _obj;
  };
  
  util.query2object = function(_object){
    return util.string2object(_object,'&',!0);  
  };
  
  util.parseUrl = (function(){
    var _reg0 = /^https?:\/\/.*?\//i,
        _reg1 = /[?#]/;
    return function(_url){
      // http://a.b.com/a/b/c?a=aa&b=bb#c=cc
      var _result = {href:_url};
      // /a/b/c?a=aa&b=bb#c=cc
      // 0 - /a/b/c
      // 1 - a=aa&b=bb
      // 2 - c=cc
      _url = (_url||'').replace(_reg0,'/').split(_reg1);
      // for /?/a/b?a=aa&b=bb
      var _count = 1;
      if (_url[0]=='/'&&
         (_url[1]||'').indexOf('/')==0)
          _count = 2;
      // /a/b/c or /?/a/b
      _result.path = _url.splice(0,_count).join('?');
      _result.query = util.query2object(_url.join('&'));
      return _result;
    };
    
  })();
  
  util.getPageBox = function(){
        var _body0 = document.body,
            _body1 = document.documentElement,
            _result = {
                scrollTop:Math.max(_body0.scrollTop,_body1.scrollTop)
               ,scrollLeft:Math.max(_body0.scrollLeft,_body1.scrollLeft)
               ,clientWidth:Math.max(_body0.clientWidth,_body0.offsetWidth,
                                     _body1.clientWidth,_body1.offsetWidth)
               ,clientHeight:Math.max(_body0.clientHeight,_body0.offsetHeight,
                                      _body1.clientHeight,_body1.offsetHeight)};
        _result.scrollWidth  = Math.max(_result.clientWidth,
                                        _body0.scrollWidth,_body1.scrollWidth);
        _result.scrollHeight = Math.max(_result.clientHeight,
                                        _body0.scrollHeight,_body1.scrollHeight);
        return _result;
    };
    
});   