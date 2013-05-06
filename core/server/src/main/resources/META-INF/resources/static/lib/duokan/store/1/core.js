define(function(require, exports, module) {
  var $ = require('jquery');
  /**
   * 激活样式
   */
  exports.pushCSSText = function(_css) {
    var node = $('<style>' + _css + '</style>');
    $('head').append(node)
  }
  /**
   * 类生成器，通过此api定义的类具有以下特性：<br/>
   * [ul]
   *   具有静态扩展接口extend
   *   init作为类的初始化函数，如果继承了其他类，则可以通过__supInit调用父类初始化函数
   * [/ul]
   * [code]
   *   // 定义类A
   *   var A = NEJ.C();
   *   // 初始化
   *   A.prototype.init = function(){
   *       // do init
   *   };
   *   
   *   // 定义类B，并继承自A
   *   var B = NEJ.C();
   *   B._$extend(A);
   *   // 初始化
   *   B.prototype.__init = function(){
   *       // 调用A的初始化逻辑
   *       this.__supInit(arguments);
   *       // TODO B的初始化逻辑
   *   };
   * [/code]
   */
  exports.create = function(){
    var _class = function(){
      return this.init.apply(this,arguments);
    }
    
    _class.extend = function(parent){
      this._$super = parent;
      this._$supro = parent.prototype;
      // 构建原型链
      var _parent = function(){};
      _parent.prototype = parent.prototype;
      this.prototype = new _parent();
      // 设置初始函数
      var _pro = this.prototype;
      _pro.constructor = this;
      _pro.init = function(){
        this.supInit.apply(this,arguments);
      };
      var _crt = parent;
      _pro.supInit = function(){
        var _method = _crt.prototype.init;
        // 到达顶部之后,回到parent
        _crt = _crt._$super || parent;
        if(!!_method)
          _method.apply(this,arguments);
         //防止坏连(init漏写supInit)
         _crt = parent;
      };
      //返回当前构造函数的原型
      return _pro;
    };

    return _class;
  };
  /**
   * AOP增强操作，增强操作接受一个输入参数包含以下信息
   * 
   * [ntb]
   *  参数名称       | 参数类型          | 参数描述
   *  ------------------------------------
   *  args    | Array    | 函数调用时实际输入参数，各增强操作中可以改变值后将影响至后续的操作
   *  stopped | Boolean  | 是否结束操作，终止后续操作
   *  value   | Variable | 输出结果
   * [/ntb]
   * 
   * @api    {Function.prototype._$aop}
   * @param  {Function} 之前操作，接受一个输入参数，见描述信息
   * @param  {Function} 之后操作，接受一个输入参数，见描述信息
   * @return {Function} 增强后操作函数
   */
  Function.prototype.aop = function(_before,_after){
    var f = function(){return !1;},
        _after = _after||f,
        _before = _before||f,
        _handler = this;
    return function(){
        var _event = {args:NEJ.R.slice.call(arguments,0)};
        _before(_event);
        if (!_event.stopped){
            _event.value = _handler
                  .apply(this,_event.args);
            _after(_event);
        } 
        return _event.value;
    };
  };
  
});