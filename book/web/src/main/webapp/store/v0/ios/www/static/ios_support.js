//fgnass.github.com/spin.js#v1.2.4
(function(a,b,c){function g(a,c){var d=b.createElement(a||"div"),e;for(e in c)d[e]=c[e];return d}function h(a){for(var b=1,c=arguments.length;b<c;b++)a.appendChild(arguments[b]);return a}function j(a,b,c,d){var g=["opacity",b,~~(a*100),c,d].join("-"),h=.01+c/d*100,j=Math.max(1-(1-a)/b*(100-h),a),k=f.substring(0,f.indexOf("Animation")).toLowerCase(),l=k&&"-"+k+"-"||"";return e[g]||(i.insertRule("@"+l+"keyframes "+g+"{"+"0%{opacity:"+j+"}"+h+"%{opacity:"+a+"}"+(h+.01)+"%{opacity:1}"+(h+b)%100+"%{opacity:"+a+"}"+"100%{opacity:"+j+"}"+"}",0),e[g]=1),g}function k(a,b){var e=a.style,f,g;if(e[b]!==c)return b;b=b.charAt(0).toUpperCase()+b.slice(1);for(g=0;g<d.length;g++){f=d[g]+b;if(e[f]!==c)return f}}function l(a,b){for(var c in b)a.style[k(a,c)||c]=b[c];return a}function m(a){for(var b=1;b<arguments.length;b++){var d=arguments[b];for(var e in d)a[e]===c&&(a[e]=d[e])}return a}function n(a){var b={x:a.offsetLeft,y:a.offsetTop};while(a=a.offsetParent)b.x+=a.offsetLeft,b.y+=a.offsetTop;return b}var d=["webkit","Moz","ms","O"],e={},f,i=function(){var a=g("style");return h(b.getElementsByTagName("head")[0],a),a.sheet||a.styleSheet}(),o={lines:12,length:7,width:5,radius:10,color:"#000",speed:1,trail:100,opacity:.25,fps:20,zIndex:2e9,className:"spinner",top:"auto",left:"auto"},p=function q(a){if(!this.spin)return new q(a);this.opts=m(a||{},q.defaults,o)};p.defaults={},p.prototype={spin:function(a){this.stop();var b=this,c=b.opts,d=b.el=l(g(0,{className:c.className}),{position:"relative",zIndex:c.zIndex}),e=c.radius+c.length+c.width,h,i;a&&(a.insertBefore(d,a.firstChild||null),i=n(a),h=n(d),l(d,{left:(c.left=="auto"?i.x-h.x+(a.offsetWidth>>1):c.left+e)+"px",top:(c.top=="auto"?i.y-h.y+(a.offsetHeight>>1):c.top+e)+"px"})),d.setAttribute("aria-role","progressbar"),b.lines(d,b.opts);if(!f){var j=0,k=c.fps,m=k/c.speed,o=(1-c.opacity)/(m*c.trail/100),p=m/c.lines;!function q(){j++;for(var a=c.lines;a;a--){var e=Math.max(1-(j+a*p)%m*o,c.opacity);b.opacity(d,c.lines-a,e,c)}b.timeout=b.el&&setTimeout(q,~~(1e3/k))}()}return b},stop:function(){var a=this.el;return a&&(clearTimeout(this.timeout),a.parentNode&&a.parentNode.removeChild(a),this.el=c),this},lines:function(a,b){function e(a,d){return l(g(),{position:"absolute",width:b.length+b.width+"px",height:b.width+"px",background:a,boxShadow:d,transformOrigin:"left",transform:"rotate("+~~(360/b.lines*c)+"deg) translate("+b.radius+"px"+",0)",borderRadius:(b.width>>1)+"px"})}var c=0,d;for(;c<b.lines;c++)d=l(g(),{position:"absolute",top:1+~(b.width/2)+"px",transform:b.hwaccel?"translate3d(0,0,0)":"",opacity:b.opacity,animation:f&&j(b.opacity,b.trail,c,b.lines)+" "+1/b.speed+"s linear infinite"}),b.shadow&&h(d,l(e("#000","0 0 4px #000"),{top:"2px"})),h(a,h(d,e(b.color,"0 0 1px rgba(0,0,0,.1)")));return a},opacity:function(a,b,c){b<a.childNodes.length&&(a.childNodes[b].style.opacity=c)}},!function(){var a=l(g("group"),{behavior:"url(#default#VML)"}),b;if(!k(a,"transform")&&a.adj){for(b=4;b--;)i.addRule(["group","roundrect","fill","stroke"][b],"behavior:url(#default#VML)");p.prototype.lines=function(a,b){function e(){return l(g("group",{coordsize:d+" "+d,coordorigin:-c+" "+ -c}),{width:d,height:d})}function k(a,d,f){h(i,h(l(e(),{rotation:360/b.lines*a+"deg",left:~~d}),h(l(g("roundrect",{arcsize:1}),{width:c,height:b.width,left:b.radius,top:-b.width>>1,filter:f}),g("fill",{color:b.color,opacity:b.opacity}),g("stroke",{opacity:0}))))}var c=b.length+b.width,d=2*c,f=-(b.width+b.length)*2+"px",i=l(e(),{position:"absolute",top:f,left:f}),j;if(b.shadow)for(j=1;j<=b.lines;j++)k(j,-2,"progid:DXImageTransform.Microsoft.Blur(pixelradius=2,makeshadow=1,shadowopacity=.3)");for(j=1;j<=b.lines;j++)k(j);return h(a,i)},p.prototype.opacity=function(a,b,c,d){var e=a.firstChild;d=d.shadow&&d.lines||0,e&&b+d<e.childNodes.length&&(e=e.childNodes[b+d],e=e&&e.firstChild,e=e&&e.firstChild,e&&(e.opacity=c))}}else f=k(a,"animation")}(),a.Spinner=p})(window,document);

function getArgs(strParame) {
    var args = new Object();
    var query;
    if(arguments.length == 2)
        query = arguments[1];
    else
        query = location.search.substring(1);
    
    var pairs = query.split("&");
    for(var i = 0; i < pairs.length; i++) {
        var pos = pairs[i].indexOf('=');
        if (pos == -1) continue;
        var argname = pairs[i].substring(0,pos);
        var value = pairs[i].substring(pos+1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args[strParame];
}

function getCookie(c_name) {
  var i,x,y,ARRcookies=document.cookie.split(";");
  for (i=0;i<ARRcookies.length;i++) {
    x=ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
    y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
    x=x.replace(/^\s+|\s+$/g,"");
    if (x==c_name)
    {
      return unescape(y);
    }
  }
  return null;
}

function htmlEncode(str)
{
    var s = "";
    if (str.length == 0)
        return "";
    s = str.replace(/&/g, "&gt;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br>");
    return s;
}

function htmlDecode(str)
{
    var s = "";
    if (str.length == 0)
        return "";
    s = str.replace(/&gt;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&nbsp;/g, " ");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    s = s.replace(/<br>/g, "\n");
    return s;
}

// 摘自iScroll
var has3d = ('WebKitCSSMatrix' in window && 'm11' in new WebKitCSSMatrix()),
    // Device sniffing
    isIthing = (/iphone|ipad/gi).test(navigator.appVersion),
    isTouch = ('ontouchstart' in window),
    // Event sniffing
    START_EVENT = isTouch ? 'touchstart' : 'mousedown',
    MOVE_EVENT = isTouch ? 'touchmove' : 'mousemove',
    END_EVENT = isTouch ? 'touchend' : 'mouseup',
    // Translate3d helper
    translateOpen = 'translate' + (has3d ? '3d(' : '('),
    translateClose = has3d ? ',0)' : ')';

function getTouches(event) {
    if (event.touches)
        return event.touches;
    else
        return event.originalEvent.touches;
    
    return undefined;
}
function setTransition(ele) {
    ele.addClass('move');
}
function setFastTransition(ele) {
    ele.addClass('fastmove');
}
function removeTransition(ele) {
    ele.removeClass('move').removeClass('fastmove');
}
function translateX(ele, x) {
    ele.css("transform", translateOpen+x+"px, 0"+translateClose);
    ele.css("-ms-transform", translateOpen+x+"px, 0"+translateClose);
    ele.css("-moz-transform", translateOpen+x+"px, 0"+translateClose);
    ele.css("-o-transform", translateOpen+x+"px, 0"+translateClose);
    ele.css("-webkit-transform", translateOpen+x+"px, 0"+translateClose);
}
function translateXPercent(ele, x) {
    ele.css("transform", translateOpen+x+"%, 0"+translateClose);
    ele.css("-ms-transform", translateOpen+x+"%, 0"+translateClose);
    ele.css("-moz-transform", translateOpen+x+"%, 0"+translateClose);
    ele.css("-o-transform", translateOpen+x+"%, 0"+translateClose);
    ele.css("-webkit-transform", translateOpen+x+"%, 0"+translateClose);
}

function preloadImage(image_array, item) {
    var image_count = image_array.length;
    
    function afterLoadImage() {
        image_count = image_count - 1;
        
        if (image_count == 0) {
            $(item).show();
        }
    }
    
    for (i = 0 ; i < image_array.length; i++) {
        img = new Image();
        img.src = image_array[i];
        
        if (img.complete)
            afterLoadImage();
        else
            img.onload = afterLoadImage();
    }
}
// 淡入淡出的幻灯片
function slide(sildes, buttons, highlighter, time) {
    var slide_index = 0;
    var slide_count = 0;
    var interval = null;
    
    function showSlide(i) {
        var current = i, previous = slide_index;
        $(sildes).eq(current).css('z-index', '2');
        $(sildes).eq(current).attr('show', 'yes');
        
        $(sildes).eq(previous).css('z-index', '1');
        $(sildes).eq(previous).attr('show', 'no');
        
        l = $(buttons).eq(i).position().left;
        translateX($(highlighter), l);
        
        $(buttons+"[enter='yes']").attr('enter', 'no');
        $(buttons).eq(i).attr('enter', 'yes');
    };
    
    function autoShowSlide () {
        showSlide((slide_index+1) % slide_count)
        slide_index = (slide_index+1) % slide_count;
    }
    
    this.setupSlide = function () {
        slide_count = $(sildes).size();
        interval = setInterval(autoShowSlide, time);
        
        $(buttons).each(function (index) {
            $(this).bind("touchstart", function (event) {
                event.preventDefault();
                if (index == slide_index)
                    return;
                clearInterval(interval);
                
                showSlide(index);
                slide_index = index;
                
                interval = setInterval(autoShowSlide, time);
            });
        });
        
        setFastTransition($(sildes));
        setFastTransition($(highlighter));
    }
    
    this.bind = function () {
        this.setupSlide();
        translateX($(highlighter), 0); // 解决文字粗细变化的问题        
    }
    
    this.startInterval = function () {
        if (interval == null)
            interval = setInterval(autoShowSlide, time);
    }
    this.stopInterval = function () {
        if (interval != null) {
            clearInterval(interval);
            interval = null;
        }
    }
    this.adjustHighlighter = function () {
        l = $(buttons).eq(slide_index).position().left;
        translateX($(highlighter), l);
    }
}
function bindTouchScroll(item, end_callback, time) {
    var ele = $(item);
    var s_x = 0;
    var s_y = 0;
    var x = 0;
    var s_t = 0;
    var c_step = 0; // 当前展示的第几幅图片
    var touch_count = 0;
    var step_width = ele.eq(0).width();
    var step_count = ele.size();
    var act = false;
    var is_new_touch = true;
    var interval = null;
    
    
    // 绑定循环播放
    function showNextSlide() {
      if (c_step < 0 || c_step >= step_count) {
        return;
      }
      c_step = (c_step+1)%step_count;
      setFastTransition($(item));
      translateXPercent(ele, -c_step * 100);
      if (end_callback != null)
        end_callback(c_step);
    };
    function startSlide() {
      if (time != null && interval == null)
        interval = setInterval(showNextSlide, time);
    }
    function stopSlide() {
      if (interval != null) {
        clearInterval(interval);
        interval = null;
      }
    }
    
    // 绑定手指触碰
    ele.bind("touchstart", function (event) {
        touches = getTouches(event);
        
        if (touch_count == 0) {
            s_x = x = touches[0].clientX;
            s_y = touches[0].clientY;
            s_t = (new Date()).getTime();
            removeTransition(ele);
            act = false;
            is_new_touch = true;
            
            stopSlide();
        }
        touch_count = touches.length;
    });
    
    ele.bind("touchmove", function (event) {
        touches = getTouches(event);
        
        x = touches[0].clientX;
        y = touches[0].clientY;
        
        if (is_new_touch && x != s_x && y != s_y && (Math.abs(x - s_x) > 5 || Math.abs(y - s_y) > 5)) {
            is_new_touch = false;
            act = 5 * Math.abs(x - s_x) > 3 * Math.abs(y - s_y);
        }
        
        if (act) {
            if (c_step == 0 && x - s_x > 0 || c_step == step_count - 1 && x - s_x < 0)
                translateX(ele, (x - s_x)/2 - step_width*c_step);
            else
                translateX(ele, x - s_x - step_width*c_step);
            
            event.preventDefault();
        }
    });
    ele.bind("touchend", function (event) {
        if (touch_count == 0) // 防止多指事件重入
            return;
        touches = getTouches(event);
        touch_count = touches.length;
        if (touch_count != 0) // 在完全放开以后再触发动画
            return;
        if (!act)
            return;
        
        l = (new Date()).getTime() - s_t;
        delta_step = 0;
        
        offset = 0;
        setTransition(ele);
				
        if (((x < s_x - 20 || x > s_x + 20) && l < 300)) { /* 快速擦动 */
            setFastTransition(ele);
            offset = x - s_x;
        }
        else if (x < s_x - step_width/2 || x > s_x + step_width/2) { /* 一般移动 */
            offset = x - s_x;
            offset = offset > 0 ? offset - step_width/2 : offset + step_width/2;
        }
        
        delta_step = 0;
        if (offset != 0) {
            delta_step = parseInt(offset/step_width) + offset/Math.abs(offset);
            
            if (c_step - delta_step < 0 || c_step - delta_step >= step_count) {
                if (delta_step < 0)
                    delta_step = c_step - step_count + 1
                else 
                    delta_step = c_step;
            }
        }
        // console.log('offset'+offset+',delta'+delta_step+',width'+step_width);
        if (delta_step == 0) {
          setFastTransition(ele);
        }
        
        translateXPercent(ele, (delta_step - c_step) * 100);
        c_step = c_step - delta_step;
        
        if (end_callback != null)
            end_callback(c_step);
        
        startSlide();
    });
    
    startSlide();
    
    return {
      start: function () {
        startSlide();
      },
      stop: function () {
        stopSlide();
      }
    };
}

function reloadImage(url) {
  var count = 0;
  return function () {
    if (count < 3) {
      $(this).attr('src', $(this).attr('src'));
      count++;
    } else {
      if (url != null && url != undefined)
        $(this).attr('src', url);
      $(this).unbind('error');
    }
  }
}

// 延迟加载selector指定的图片，如果不传入selector，则默认延迟加载所有图片
// 延迟加载的图片必须拥有height属性，并且将需要图片地址放在_src属性中
function lazyLoadImage(selector) {
  if (selector==null)
    selector = 'img';
  
  $(window).unbind('scroll');
  $(window).scroll(function(e) {
    var window_height = window.innerHeight;
    $(selector).each( function(index, element) {
      var ele = $(element);
      var _src = ele.attr('_src');
      if (_src != null) {
        var top = ele.offset().top;
        var scroll_top = document.body.scrollTop;
        var height = ele.height();
        if (top < scroll_top + window_height && top > scroll_top - height) {
          ele.bind('error', reloadImage(ele.attr('src')));
          ele.attr('src', _src);
          ele.removeAttr('_src');
        }
      }
    });
  });
  
  $(window).scroll();
}

function lazyLoadImageForIScroll(selector, wrapper, inner) {
  if (selector==null)
    selector = 'img';

  var window_height = $(wrapper).height();
  $(selector).each( function(index, element) {
    var ele = $(element);
    var _src = ele.attr('_src');
    if (_src != null) {
      var top = ele.offset().top;
      var height = ele.height();
      if (top < window_height && top + height > 0 ) {
        ele.bind('error', reloadImage(ele.attr('src')));
        ele.attr('src', _src);
        ele.removeAttr('_src');
      }
    }
  });
}

function parseDate(str) {
  var strDate = str.split("-");  
  return new Date(strDate[0],(strDate[1]-1),strDate[2])
}
// 倒计时
// finish_time是距离1970年1月1日午夜的秒数。
function countDown(ele, finish_time, callback) {
  var current = new Date();
  current = current.getTime() / 1000;
  this._next_call = function () {
    countDown(ele, finish_time, callback);
  }
  var gap = finish_time - current;
  if (gap > 0) {
    if (gap > 86400) {
      $(ele).html('多于1天');
      setTimeout(this._next_call, gap - 86399);
    } else if (gap > 28800) {
      $(ele).html('少于1天');
      setTimeout(this._next_call, gap - 28799);
    } else if (gap > 7200) {
      $(ele).html('少于8小时');
      setTimeout(this._next_call, gap - 7199);
    } else if (gap > 1800) {
      $(ele).html('少于2小时');
      setTimeout(this._next_call, gap - 1799);
    } else if (gap > 900) {
      $(ele).html('少于30分钟');
      setTimeout(this._next_call, gap - 899);
    } else if (gap > 300) {
      $(ele).html('少于15分钟');
      setTimeout(this._next_call, gap - 299);
    } else if (gap > 60) {
      $(ele).html('少于5分钟');
      setTimeout(this._next_call, gap - 59);
    } else {
      $(ele).html('少于1分钟');
      setTimeout(this._next_call, gap);
    }
  } else {
    $(ele).html('已到期');
    if (callback != undefined || callback != null) {
      callback();
    }
  }
}

// 从昵称中隐藏邮箱地址
function removeNickEmail(nick) {
	return nick ? nick.replace(/@[^\.]+\..+$/, "@...") : nick;
}

function addLoad(ele, get_url, callback, error_callback) {
  var spinner = new Spinner({
    lines: 10,
    length: 3,
    width: 2,
    radius: 4,
    color: '#333',
    speed: 1,
    trail: 38,
    shadow: false,
    hwaccel: true, 
    className: 'spinner',
    top: 'auto',
    left: 'auto'
  });
  var target = $(ele+' .spin')[0];
     $(ele).show();
    $(ele+' span').html('载入中...');
    spinner.spin(target);
    
    $.ajax({
      url: get_url(),
      success: function (data) {
        $(ele).hide();
          spinner.stop();
          callback(data);
      },
      dataType: 'json',
      error: function () {
        if (error_callback != null)
          error_callback();
       
      }
    });

}


function loadAjaxSpin(ele, get_url, callback, error_callback) {
  var spinner = new Spinner({
    lines: 10,
    length: 3,
    width: 2,
    radius: 4,
    color: '#333',
    speed: 1,
    trail: 38,
    shadow: false,
    hwaccel: true, 
    className: 'spinner',
    top: 'auto',
    left: 'auto'
  });
  var target = $(ele+' .spin')[0];
    function _next_call() {
    $(ele).unbind('click');
    $(ele+' span').html('载入中...');
    spinner.spin(target);
    
    $.ajax({
      url: get_url(),
      success: function (data) {
          callback(data);
          spinner.stop();
        
          $(ele+' span').html('点击获取更多...');
          $(ele).click(_next_call);
      },
      dataType: 'json',
      error: function () {
        if (error_callback != null)
          error_callback();
        $(ele).click(_next_call);
      }
    });
  };
  $(ele).unbind('click');
  $(ele).click(_next_call);
}
function Status() {}
Status.OK = 0;
// 通用错误码
Status.ILLEGAL_ARGS = 1;
Status.NEW_VERSION_REQUIRED = 2;
Status.MAINTAINING = 3;
Status.SERVER_ERROR = 4;
Status.SERVER_CONFIG_ERROR = 5;
Status.NOTHING_DONE = 6;
Status.UNKNOWN_PLATFORM = 7;
Status.UNEXPECTED_DATABASE_ERROR = 8;
Status.UNEXPECTED_ERROR = 9;
  
  // 登录错误码
Status.EXPIRED = 1001;
Status.NO_LOGIN = 1002;
Status.RELOGIN = 1003;
  
  // 订单错误码
Status.ILLEGAL_ORDER_ID = 10001;
Status.ORDER_NOT_EXIST = 10002;
Status.ORDER_EXISTS = 10003;
Status.ORDER_FROZEN = 10004;
Status.ILLEGAL_BOOK_ID = 10005;
Status.BOOK_NOT_FOUND = 10006;
Status.ABNORMAL_BOOK_STATE = 10007;
Status.BOOK_PURCHASED = 10008;

function handleError(code, callback) {
  switch(code)
  {
  case status.OK:
    break;
  case status.NEW_VERSION_REQUIRED:
    alert('请升级到最新的客户端');
    break;
  case status.MAINTAINING:
    alert('系统维护中');
    break;
  
  
  // 登录错误码
  case status.EXPIRED:
    break;
  case status.NO_LOGIN:
    break;
  case status.RELOGIN:
    break;
  
  // 订单错误码
  case status.ILLEGAL_ORDER_ID:
    break;
  case status.ORDER_NOT_EXIST:
    break;
  case status.ORDER_EXISTS:
    break;
  case status.ORDER_FROZEN:
    break;
  case status.ILLEGAL_BOOK_ID:
    break;
  case status.BOOK_NOT_FOUND:
    break;
  case status.ABNORMAL_BOOK_STATE:
    break;
  case status.BOOK_PURCHASED:
    break;
  default:
    alert('发生错误：'+code);
  }
  if (callback != null)
    callback();
}

var base_url = window.location.protocol + '//' + window.location.host;
base_url += '/store/v0/ios/www/';

function ipadBookURL(book_id, title) {
  return base_url + 'ipad_detail.html?book_id=' + book_id + '&title=' + encodeURIComponent(title);
}
function ipadListURL(list_id, title) {
  if (title == null) title = '';
  return base_url + 'ipad_list_detail.html?list_id=' + list_id + '&title=' + encodeURIComponent(title);
}
function ipadBookNoteURL(book_id, title) {
  return base_url + 'ipad_book_note.html?book_id=' + book_id + '&title=' + encodeURIComponent(title);
}
function iphoneBookURL(book_id, title) {
  return base_url + 'iphone_detail.html?book_id=' + book_id + '&title=' + encodeURIComponent(title);
}
function iphoneListURL(list_id, title) {
  if (title == null) title = '';
  return base_url + 'iphone_list_detail.html?list_id=' + list_id + '&title=' + encodeURIComponent(title);
}
function iphoneBookNoteURL(book_id, title) {
  return base_url + 'iphone_book_note.html?book_id=' + book_id + '&title=' + encodeURIComponent(title);
}
function iphoneCategoryURL(category_id, title) {
  return base_url + 'iphone_category_detail.html?category_id=' + category_id + '&title=' + encodeURIComponent(title);
}
function iphoneSearchURL(key) {
  return base_url + 'iphone_search.html?s=' + encodeURIComponent(key);
}
function navigateTo(url){
  document.location = '/__duokan_appapi/cmd/navigate/opennew?url=' + encodeURIComponent(url);
}
function navigateToDefault(url){
  document.location = '/__duokan_appapi/cmd/navigate/opendefault?url=' + encodeURIComponent(url);
}
function navigateToChannel(name){
  document.location = '/__duokan_appapi/cmd/navigate/store?channel='+name;
}
function notifyAppLoaded() {
  if (getCookie('build') != null) {
    document.location = '/__duokan_appapi/cmd/navigate/didload';
  }
}
function openReading(wich){
  var build=getCookie('build');
  var book_id = getCookie('book_id');
  if (book_id == null) {
    book_id = getArgs('book_id')
  };
  if(build>2012080101){
    $(wich).click(function(){
       document.location='/__duokan_appapi/cmd/books/open?bookid='+book_id;
    })
  }
}
function dkLogin(){
  var user_id = getCookie('userid');
  if (user_id == null) {
    user_id = getArgs('userid')
  }
  if( user_id==null || user_id=='' || user_id == undefined){
    document.location = '/__duokan_appapi/cmd/account/login';
  }
  return false;
}

function reLogin(){
  document.location = '/__duokan_appapi/cmd/account/login';
}
function clickFullBook(book_id, title, url, cover, price, revision) {
  var s = '/__duokan_appapi/cmd/books/get?bookid=' + book_id
    + '&contenttype=full'
    + '&coverurl=' + encodeURIComponent(cover)
    + '&bookurl='  + encodeURIComponent(url)
    + '&title=' + encodeURIComponent(title)
    + '&price=' + price
    + '&currency=CNY'
    + '&revision=' + encodeURIComponent(revision);
  
  document.location = s;
}
function clickTrialBook(book_id, title, url, cover, revision) {
  document.location = '/__duokan_appapi/cmd/books/get?bookid=' + book_id
    + '&contenttype=trial'
    + '&coverurl=' + encodeURIComponent(cover)
    + '&bookurl=' + encodeURIComponent(url)
    + '&title=' + encodeURIComponent(title)
    + '&revision=' + encodeURIComponent(revision);
}
function clickExportBookNotes(book_id, title, author) {
  document.location = '/__duokan_appapi/cmd/books/export/readingnotes?bookid=' + book_id
    + '&title=' + encodeURIComponent(title)
    + '&author=' + encodeURIComponent(author);
}
function showGift(book_id,status){
  document.location = '/__duokan_appapi/data/?bookid=' + book_id
    +'&type='+type;
}
function setHover(ele) {
  ele.bind("touchstart", function (event) {
    if (!$(this).hasClass('hover'))
      $(this).addClass('hover');
  });
  ele.bind("touchmove", function (event) {
    $(this).removeClass('hover');
  });
  ele.bind("touchend", function (event) {
    $(this).removeClass('hover');
  });
}
function getSizeText(size) {
  if (size > 0){
    if (size > 1024 * 1024) {
      return (size / (1024 * 1024)).toFixed(2)+'&nbsp;MB'
    } else {
      return (size / 1024).toFixed(2)+'&nbsp;KB'
    }
  } else {
    return '未知';
  }
}

function loadingIcon(ele) {
  notifyAppLoaded();
  var spinner = new Spinner({
    lines: 10,
    length: 3,
    width: 2,
    radius: 4,
    color: '#333',
    speed: 1,
    trail: 38,
    shadow: false,
    hwaccel: true, 
    className: 'spinner',
    top: 'auto',
    left: 'auto'
  });
  var target = $(ele+' .spin')[0];
    $(ele).show();
    $(ele+' span').html('载入中...');
    spinner.spin(target);
}

function postiontop(showBox){ 
  var wich_top=( $(window).height()-$(showBox).height() )/2;
  var wich_bot=$(window).height()+$(showBox).height(); 
  var wich_left=( $(window).width()-$(showBox).width() )/2;
  $(showBox).css({top: wich_bot,left:wich_left});
  $(showBox).animate({top:wich_top},300)
   $(window).unbind('resize');
  $(window).resize(function(){
    var wich_top2=( $(window).height()-$(showBox).height() )/2;
    var wich_left2=( $(window).width()-$(showBox).width() )/2;
    $(showBox).css({top:wich_top2,left:wich_left2});
  });
}

function postioncenter(showBox){ 
  var wich_top=( $(window).height()-$(showBox).height() )/2;
  var wich_left=( $(window).width()-$(showBox).width() )/2;
  $(showBox).css({top:wich_top,left:wich_left});
  $(window).unbind('resize');
  $(window).scroll(function(){
    var wich_top2=( $(window).height()-$(showBox).height() )/2+$(window).scrollTop();
    var wich_left2=( $(window).width()-$(showBox).width() )/2;
    $(showBox).css({top:wich_top2,left:wich_left2});
  })
  $(window).resize(function(){
    var wich_top3=( $(window).height()-$(showBox).height() )/2;
    var wich_left3=( $(window).width()-$(showBox).width() )/2;
    $(showBox).css({top:wich_top3,left:wich_left3});
  });
}
//返回上级页面
function returnpage(){
  document.location = '/__duokan_appapi/cmd/navigate/close';
}

(function($){
  jQuery.fn.extend({
    changecontent:function(content_calss){//要显示的内容class//要切换nav样式的calss;
                    return $(this).each(function(index){
                      var obj=$(this);
                      var con=""+'.'+content_calss+"";
                      $(con).hide();
                      $(con).eq(0).show();
                      obj.click(function(){
                        if($(con).is(":animated")){return;}
                        else{
                          obj.siblings().removeAttr("active");
                          obj.attr("active","yes");
                          $(con).hide();
                          $(con).eq(index).show();
                       };
                     });
                   });
   },
   update:function(){
            return $(this).click(function(){
              document.location.href='/__duokan_appapi/cmd/books/updateall';
              $(this).empty().text('正在下载');
              $(this).unbind('click');
              $(this).removeClass('infor_new').addClass('infor_over');
            })
  },

  })
})(jQuery)




