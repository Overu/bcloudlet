define(function(require, exports, module) {
  var core = require('../core.js'),
      $ = require('jquery');
  /**
   * 事件基类
   */
  var $$Event = core.create();
  // Event.prototype = $.extend({},Event.prototype,{
  $$Event.prototype ={  
    constructor : $$Event,
    
    init:function(){
      this.initEvent(arguments[0]);
    },
    initEvent:function(_opt){
      this.__events = {};
      if(!!_opt && $.isPlainObject(_opt)){
         for(var x in _opt){
           this.addEvent(x,_opt[x]);
         }       
      }
    },
    addEvent:function(){
      this.setEvent.apply(this,arguments);
    },
    setEvent:function(_type,_event){
      if (!!_type && $.isFunction(_event)) 
            this.__events[_type.toLowerCase()] = _event;
        return this;
    },
    fireEvent:function(_type){
      var _event = this.__events[(_type||'').toLowerCase()];
      if (!_event) return this;
      var _args = Array.prototype.slice.call(arguments,1);
      // single event
      if (!$.isArray(_event))
          return _event.apply(this,_args);
      // event list
      _event.forEach(
        function(_handler){
            try{_handler.apply(this,_args);}catch(e){}
        },this);
      return this;
    },
    removeEvent:function(_type){
      var _type = (_type||'').toLowerCase();
      if (!!_type){
          delete this.__events[_type];
      }else{
        for(var x in this.__events){
          this.removeEvent(x);
        }
      }
      return this;
    },
    hasEvent:function(_type){
      var _event = this.__events[_type.toLowerCase()];
      return !!_event;
    }
  }
  
  module.exports = $$Event;
});