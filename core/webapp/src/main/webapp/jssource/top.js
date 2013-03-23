define(function(require, exports, module) {
  var $ = require('jquery'),
      core = require('../../core'),
      $$Event = require('../event');
  
  var $$Top = core.create(),
      _pro = $$Top.extend($$Event);
  
  /**
   * @param body  {Node}   回到顶部的节点
   *        limit {Number} 离开顶部多远,显示节点
   */
  _pro.init = function(_opt){
    this.supInit(_opt);
    
    this.__body = $(_opt.body);
    this.__limit = _opt.limit || 20;
    
    $(window).bind('scroll',this.__onscroll.bind(this));
    this.__body.bind('click',this.__backTop.bind(this));
    this.__toggle();
  };
  
  _pro.__toggle = function(_scrollTop){
    _scrollTop = _scrollTop || $(window).scrollTop();
    _scrollTop < this.__limit ? this.__body.hide() : this.__body.show();
  };
  
  _pro.__onscroll = function(){
    var _scrollTop = $(window).scrollTop();
    this.__toggle(_scrollTop);
  };
  
  _pro.__backTop = function(){
    $(window).scrollTop(0);
  };
  
  return $$Top;
});