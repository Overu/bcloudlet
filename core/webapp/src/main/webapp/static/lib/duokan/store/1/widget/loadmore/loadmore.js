define(function(require, exports, module) {
  var $ = require('jquery'),
      core = require('../../core'),
      common = require('../../common'),
      business = require('../../business'),
      juicer = require('gallery/juicer/0.6.4/juicer'),
      $$Score = require('../score/score'),
      $$Event = require('../event');      
      
  var $$LoadMore = core.create(),
      _pro = $$LoadMore.extend($$Event);
  
  /**
   * @param _opt {Object} 
   * @config url {String} 请求的url
   * @config limit {Number} 每次加载的数量
   * @config offset {Number} 每次请求的偏移量
   * @config tpl {String} 请求的模板节点ID
   */    
  _pro.init = function(_opt){
    this.supInit(arguments);
    this.target = $('#' + _opt.target) || $(document.body);
    this.url = _opt.url;
    this.args = {};
    this.args.page_length = _opt.limit || 30;
    this.args.start = _opt.offset || 0;
    this.args.s = _opt.search || '';
    
    this.more = !0;
    this.tpl = $('#' + _opt.tpl).html();
    
    this.__canLoad = !0;
    
    $(window).scroll(this._onscroll.bind(this));                     
    this.setEvent('onloadmore',_opt.onloadmore || this._onloadmore);
  };
  /**
   * 滚动事件回调
   */
  _pro._onscroll = function() {
    if (!this.more) return;
    var hight=$(document).height();               
    var clientHeight=$(window).height();          
    var scorllTop=$(window).scrollTop();
    if(scorllTop + clientHeight == hight){
      //FIXME 
      $('.spin').show();
      common.loadingIcon('.w-load')
      
      console.log('onscroll->' + this.__canLoad);
      if(!!this.__canLoad){
        console.log('pre send ->' + this.__canLoad);
        this.__canLoad = !1;
        this.fireEvent('onloadmore');
      }
    }
  };
  /**
   * 触发loadmore回调,可扩展此函数
   */
  _pro._onloadmore = (function() {
    var _build = function(args){
      var data = {};
      for(var x in args){
        !!args[x] && (data[x] = args[x]);
      }
      return data;
    };
    return function() {
      var data = _build(this.args);
      business.ajax(this.url,data,this._onajaxsuccess.bind(this),this._onajaxcomplete.bind(this));
    }
  })();
  /**
   * 请求成功
   */
  _pro._onajaxsuccess = function(data) {
    //在juicer中注册评分的方法
    juicer.register('showStar',function(score){
      return new $$Score(score).toString();
    });
    //在juicer中注册修改图片后缀的方法
    juicer.register('imgAdapt',function(_img,_type){
      return _img.replace(/\!.+/,'!' + _type);
    });
    //格式化价格
    juicer.register('formatPrice',function(_price) {
      return Number(_price).toFixed(2);
    });
    // 注册
    juicer.register('buildAuthor',common.buildAuthor);
    data.offset = this.args.start;
    $(juicer(this.tpl,data)).appendTo(this.target);
    this.args.start += data.count;
    this.more = data.more;     
    //FIXME
    if(!this.search && this.args.start > 300)
      this.more = !1;
  };
  /**
   * 请求完成
   */
  _pro._onajaxcomplete = function() {
    this.__canLoad = !0;
    console.log('complete ->' + this.__canLoad);
    $('.spin').hide();
    $('.spin').empty(); 
  };
  
  module.exports = $$LoadMore;
  
});