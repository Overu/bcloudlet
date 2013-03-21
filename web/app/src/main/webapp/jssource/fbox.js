define(function(require, exports, module) {
  var $ = require('jquery'),
      core = require('../../core'),
      $$Top = require('../../widget/top/top'),
      $$Event = require('../../widget/event');
  
  var $$FBox = core.create(),
      _pro = $$FBox.extend($$Event);
  
  _pro.init = function(_opt){
    this.supInit(_opt);
    
    this.__body = $(_opt.body);
    this.__module = this.__body.find(_opt.module || '.js-sns');
    this.__list = this.__module.find('li');
    this.__dft = this.__list.eq(_opt.dft || 2);
    this.__clazz = _opt.clazz || 'crt'
    this.__toTop = this.__body.find(_opt.toTop || '.gotop');
    this.__width = _opt.width || 1280;
    
    
    this.__isWide();
    
    this.__list.toArray().forEach(this.__onHover,this);
    
    this.__onResize();
    this.__doPosition();
    $(window).bind('resize', this.__onResize.bind(this));
    $(window).bind('resize', this.__doPosition.bind(this));
    
    this.__goTop();
    this.__body.show();
  };
  
  
  _pro.__isWide = function() {
    this.__tag = $(document).width() > this.__width;
  };
  
  _pro.__doPosition = function(){
    $(window).width() > 1070 
             ? this.__body.css('right',(($(window).width() - 1010) / 2 - 34) + 'px' )
             : this.__body.css('right','0px' );
  };
  
  _pro.__goTop = function(){
    new $$Top({body:this.__toTop});
  };
  
  _pro.__onHover = function(_itm){
    var _clazz = this.__clazz;
    _itm = $(_itm);
    _itm.hover(
      function(){
        this.__list.removeClass(_clazz);
        _itm.addClass(_clazz);
      }.bind(this),
      function(){
        if(!this.__tag){
          _itm.removeClass(_clazz);
        }
      }.bind(this));
  };
  
  _pro.__onResize = function(){
    this.__isWide();
    !!this.__tag ? this.__dft.addClass(this.__clazz) 
                 : this.__list.removeClass(this.__clazz);
    !!this.__tag ? this.__module.removeClass('m-sns-2').addClass('m-sns-1') 
                 : this.__module.removeClass('m-sns-1').addClass('m-sns-2');
  };
  
  var config = require('../../config');
  if(!!config.browser.trident2)
    _pro.__doPosition = function(){return !1;};
  
  return $$FBox;    
});