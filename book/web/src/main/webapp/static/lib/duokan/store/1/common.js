define(function(require, exports, module) {
  var $ = require('jquery')
                                      
  var pluses = /\+/g;       //jquery.cookie.js
  function raw(s) {
    return s;
  }
  function decoded(s) {
    return decodeURIComponent(s.replace(pluses, ' '));
  }
  var config = $.cookie = function (key, value, options) {
    // write
    if (value !== undefined) {
      options = $.extend({}, config.defaults, options);

      if (value === null) {
        options.expires = -1;
      }
      if (typeof options.expires === 'number') {
        var days = options.expires, t = options.expires = new Date();
        t.setDate(t.getDate() + days);
      }
      value = config.json ? JSON.stringify(value) : String(value);
      return (document.cookie = [
        encodeURIComponent(key), '=', config.raw ? value : encodeURIComponent(value),
        options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
        options.path    ? '; path=' + options.path : '',
        options.domain  ? '; domain=' + options.domain : '',
        options.secure  ? '; secure' : ''
      ].join(''));
    }
    // read
    var decode = config.raw ? raw : decoded;
    var cookies = document.cookie.split('; ');
    for (var i = 0, parts; (parts = cookies[i] && cookies[i].split('=')); i++) {
      if (decode(parts.shift()) === key) {
        var cookie = decode(parts.join('='));
        return config.json ? JSON.parse(cookie) : cookie;
      }
    }
    return null;
  };
  config.defaults = {};
  $.removeCookie = function (key, options) {
    if ($.cookie(key) !== null) {
      $.cookie(key, null, options);
      return true;
    }
    return false;
  };
  
  //////////////////////
  var isIE=!!window.ActiveXObject;
  var isIE6=isIE&&!window.XMLHttpRequest;
  function getBasicHost() {
    var host = window.location.host;
    if (host.indexOf('172.27') >= 0 || host.toLowerCase().indexOf('dkmars') >= 0) {
      return host;
    } else if (host == "127.0.0.1") {
      return "127.0.0.1";
    } else if (host.toLowerCase().indexOf('localhost') >= 0) {
      return "localhost";
    } else {
      return "duokan.com";
    }
  }
  var common ={                                                  //公用模块
    addCookie:function(objName, objValue, objDays){                        //添加Cookie
      if (objDays > 0)
        $.cookie(objName, objValue, { expires: objDays, path: '/' });
      else
        $.cookie(objName, objValue, { path: '/' });
    },
    getCookie:function(c_name){
      return $.cookie(c_name);
    },
    delCookie:function(cookieName){                                       //删除Cookie
      $.removeCookie(cookieName, { path: '/' });
    },
    getArgs:function(name) {                                        //获取location 参数
      query = location.pathname.substring(1);
      var pairs = query.split("/");
      var index;
      if(name == 'title') {
        index = 0
      } else if ( name == 'book_id' ) {
        if(!!window.dk_data && !!window.dk_data.book_id)
          return decodeURIComponent(window.dk_data.book_id);
        /* 从url中匹配 */
        for ( var i in pairs ) {
          if ( pairs[i] == "b" ) {
            index = parseInt(i)+1;
          }
        }
      } else if ( name == 'list_id' ) {
        for ( var i in pairs ) {
          if ( pairs[i] == "l" ) {
            index = parseInt(i)+1;
          }
        }
      } else if ( name == 'press_name' ) {
        for ( var i in pairs ) {
          if ( pairs[i] == "p" ) {
            index = parseInt(i)-1;
          }
        }
      } else if ( name == 'comment_id' ) {
        for ( var i in pairs ) {
          if ( pairs[i] == "comment" ) {
            index = parseInt(i)+1;
          }
        }
      }
      var value = decodeURIComponent( pairs[index] )
      return value
    },
   
    getArgs2:function(strParame) {
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
    },
    //根据正则解析url参数,算法同服务器
    getArgsByReg:function(_reg){
      var _url= window.location.href.replace(/https?\:\/\//,''),
      _urls = _url.match(_reg);
      _urls.forEach(function(value,index){
        _urls[index] = decodeURI(_urls[index]);
      });
      _urls.shift()
      return _urls;
    },
    
    getSizeText:function (size) {
      if (size>0){
        if (size > 1000000) {
          return (size/1000000).toFixed(2)+'&nbsp;MB'
        } else {
          return (size/1000).toFixed(2)+'&nbsp;KB'
        }
      } else {
        return '未知';
      }
    },
   
    commentLIstUlHeight:null,
    jQueryAjax:function(get_url,callback){                               //jquery ajax
      $.ajax({
        url: get_url,
        success: function (data) {
          callback(data);
        },
        complete:function(){
          if($('.comment-hot').hasClass('crt')){
            common.commentLIstUlHeight=$('#hottest').height();
          } else{
            common.commentLIstUlHeight=$('#newest').height();
          }
        },
        dataType: 'json',
        type:'get'
      })
    },
    jump:function (time,element,url) { //X秒跳转
      window.setTimeout(function(){
        time--;
        if(time > 0) {
           $(element).text( time ) ;
           common.jump(time,element,url)
        } else {
          document.location=url;
        }
      }, 1000);
    },
    htmlEncode: function(str) {
      var s = "";
      if (str.length == 0)
        return "";
        s = str.replace(/&/g, "&amp;");
        s = s.replace(/</g, "&lt;");
        s = s.replace(/>/g, "&gt;");
        s = s.replace(/ /g, "&nbsp;");
        s = s.replace(/\'/g, "&#39;");
        s = s.replace(/\"/g, "&quot;");
        s = s.replace(/\n/g, "<br>");
        return s;
    },
    removeNickEmail:function (nick) {                                  // 从昵称中隐藏邮箱地址
      return nick ? nick.replace(/@[^\.]+\..+$/, "") : nick;
    },
    buildContent: function (str) { //内容
      var sep = '\n';
      if (str.search('\r\n') != -1)
        sep = '\r\n';
        var paras = str.split(sep);
        var content = "";
        var regex = new RegExp(sep, 'g');
        
        paras.forEach(function(v,i){
          if (paras[i].length > 0) {
            var s = common.htmlEncode(paras[i].trim());
            content += '<p>' +  s.replace(regex, '</p><p>') + '</p>';
          }
        });
        
        // for (var i in paras) {
          // if (paras[i].length > 0) {
            // var s = common.htmlEncode(paras[i].trim());
            // content += '<p>' +  s.replace(regex, '</p><p>') + '</p>';
          // }
        // }
        return content;
    },
    nbs: /^\s*(\s*?)\s+$/, 
    removepace: function(wich_p,setClass){
      $(wich_p).each(function(index){     //去除空的<p>
        var text = $(wich_p).eq(index).text();
          if( common.nbs.test(text) == true || text == '') {
            var nextext = $(this).next('p').text();
             if( common.nbs.test(nextext) == false || text !='' ) {
                $(this).next('p').addClass(setClass)
             }
            $(wich_p).eq(index).empty().remove();
          }
      })
    },
    loadingIcon:function (ele) {   //大的loading图标
      var spinner = new Spinner({
        lines: 12,
        length: 0,
        width: 6,
        radius: 14,
        corners: 1,
        color: '#333',
        speed: 1,
        trail: 60,
        shadow: false,
        hwaccel: false, 
        className: 'spinner',
        top: 0,
        left: 0
      });
      var target = $(ele+' .spin')[0];
      spinner.spin(target);
      return spinner;
    },
    smallLoadingIcon:function (ele) { //小的loading图标
      var spinner = new Spinner({
        lines: 12,
        length: 5,
        width: 2,
        radius: 4,
        color: '#333',
        speed: 1,
        trail: 50,
        shadow: false,
        hwaccel: false, 
        className: 'spinner',
        top: 0,
        left: 0
      });
      var target = $(ele+' .spin')[0];
      spinner.spin(target);
      return spinner;
    },
    showStarLi:function (score){   //评星
      if (score < 2) {
        var star="<ul class='five'><li></li><li></li><li></li><li></li><li></li></ul>";
        return star;
      } else if (score < 2.5){
        var star="<ul class='five'><li class=red></li><li></li><li></li><li></li><li></li></ul>";
        return star;
      } else if (score < 3.5) {
        var star="<ul class='five'><li class=red></li><li class=halfive></li><li></li><li></li><li></li></ul>";
        return star;
      } else if (score < 4.5) {
        var star="<ul class='five'><li class=red></li><li class=red></li><li></li><li></li><li></li></ul>";
        return star;
      } else if (score < 5.5) {
        var star="<ul class='five'> <li class=red></li> <li class=red></li> <li class=halfive></li> <li></li> <li></li></ul>";
        return star;
      } else if (score < 6.5){
        var star="<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li></li> <li></li></ul>";
        return star;
      } else if(score < 7.5){
        var star="<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=halfive></li> <li></li></ul>";
        return star;
      } else if(score < 8.5){
        var star="<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> <li></li></ul>";
        return star;
      } else if(score < 9.5){
        var star="<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> <li class=halfive></li></ul>";
        return star;
      } else if (score <=10) {
        var star="<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> </ul>";
        return star;
      } else {
        var star="<ul class='five'><li></li><li></li><li></li><li></li><li></li></ul>";
        return star;
      }
    },
    addBasicCookie:function (objName, objValue, objDays){ //添加cookie
      if (objDays > 0)
        $.cookie(objName, objValue, { expires: objDays, path: '/', domain: getBasicHost() });
      else
        $.cookie(objName, objValue, { path: '/', domain: getBasicHost() });
    },
    delBasicCookie:function (cookieName) {
      $.removeCookie(cookieName, {path: '/', domain: getBasicHost() });
    },
    generateDeviceID:function () {
      var device = common.getCookie('device_id');
      if (device == null || device == '') {
        // 随机字符串
        var  x="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
        var  tmp="";
        for(var  i=0; i < 12; i++) {
          tmp  +=  x.charAt(Math.ceil(Math.random()*100000000)%x.length);
        }
        device = 'D900'+tmp;
      }
      common.addBasicCookie('device_id', device, 360);
      common.addBasicCookie('app_id', 'web', 360);
    },
    initialize:function(){                          //初始化
      // 确保拥有唯一标识
      common.generateDeviceID();
      // 为IE6添加hover支持
      if (isIE6) {
        $('.m-slider .wrap').bind({
          mouseenter: function() {
            $(this).addClass('hover');
         },
         mouseleave:
            function() {
              $(this).removeClass('hover');
            }
         });
         $('.m-slider .wrap, .w-booklist, .w-booklist2, w-txtlist').delegate('li', {
           mouseenter: function() {
             $(this).addClass('hover');
         },
         mouseleave:
           function() {
             $(this).removeClass('hover');
           }
         })
       }
    },
    buildAuthor:function (str) { //创建作者
      var str = str || '佚名'
      var sep = '\n';
      if (str.search('\r\n') != -1)
        var sep = '\r\n';
      var content = "";
      var paras = str.split(sep);
      var count = paras.length > 2 ? 2 : paras.length;
      for (var i = 0; i < count; i++) {
        content += '<span>' + paras[i] + '</span>';
      }
      if (paras.length > 2) {
        content += '<span>' + paras[2];
        if (paras.length > 3) 
          content += '&nbsp;等</span>';
        else
          content += '</span>';
      }
      return content;
    },
    getSizeText:function (size) {
      if (size>0){
        if (size > 1000000) {
          return (size/1000000).toFixed(2)+'&nbsp;MB'
        } else {
          return (size/1000).toFixed(2)+'&nbsp;KB'
        }
      } else {
        return '未知';
      }
    },
    showStar:function(data) {     //评分及评星显示
      if (data.result == 0) {
        if(data.score_count>0){
          var grade=data.score_count+" 个评分";
        } else{
          var grade='评分个数不足';
        }
        $('.num').append(grade)
        var score=data.score;
        if (score<2) {
        } else if (score<2.5) {
          $('.book_five .five>li:eq(0)').addClass('red')
        } else if (score<3.5) {
          $('.book_five .five>li:eq(0)').addClass('red')
          $('.book_five .five>li:eq(1)').addClass('halfive')
        } else if (score<4.5) {
          $('.book_five .five>li:lt(2)').addClass('red')
        } else if (score<5.5) {
          $('.book_five .five>li:lt(2)').addClass('red')
          $('.book_five .five>li:eq(2)').addClass('halfive')
        } else if (score<6.5) {
          $('.book_five .five>li:lt(3)').addClass('red')
        } else if (score<7.5) {
          $('.book_five .five>li:lt(3)').addClass('red')
          $('.book_five .five>li:eq(3)').addClass('halfive')
        } else if (score<8.5) {
          $('.book_five .five>li:lt(4)').addClass('red')
        } else if (score<9.5) {
          $('.book_five .five>li:lt(4)').addClass('red')
          $('.book_five .five>li:eq(4)').addClass('halfive')
        } else if (score<=10) {
          $('.book_five .five>li:lt(5)').addClass('red')
        }
      } 
    },
    postioncenter:function (showBox){  //弹出框剧中显示
      var wich_top=( $(window).height()-$(showBox).height() )/2;
      var wich_left=( $(window).width()-$(showBox).width() )/2;
      if (wich_top <0){ wich_top = 0 }
      $(showBox).css({top:wich_top,left:wich_left});
      $(window).unbind('resize');
      $(window).scroll(function(){
        var wich_top2=( $(window).height()-$(showBox).height() )/2+ $(window).scrollTop();
        var wich_left2=( $(window).width()-$(showBox).width() )/2;
        if (wich_top2 <0){ wich_top2 = 0 }
        $(showBox).css({top:wich_top2,left:wich_left2});
      })
      $(window).resize(function(){
        var wich_top3=( $(window).height()-$(showBox).height() )/2;
        var wich_left3=( $(window).width()-$(showBox).width() )/2;
          if (wich_top3 <0){ wich_top3 = 0 }
        $(showBox).css({top:wich_top3,left:wich_left3});
      });
    },
    bookpage:function(total,page){ //分页
      var count= Math.ceil(total);
      $('#total-page').html(count)
      if(count <= 1){
        $('.w-page').hide();
      }
      for(var i=1; i<=count; i++){
        if ( i > 5 && i < count ) {
           var li="<li style='display:none'><a>"+i+"</a></li>";
        } else{
          var li="<li><a>"+i+"</a></li>";
        }
        $('#bookpage-a').append(li)
      }
      if(count>5){
        $('#bookpage-a>li').eq(4).after("<li class='ellipsis'>...</li>")
      }
      $('#bookpage-a>li').eq(0).addClass('crt')
      $('.pre').addClass('init')
      $('#bookpage-a').delegate('li','click',function(){
        if ( $(this).attr( 'class' ) == 'crt'  ) {
          return false
        }
        $('.ellipsis').empty().remove();
        $('#bookpage-a>li').not($('#bookpage-a>li:first')).not($('#bookpage-a>li:last')).hide();
        var thisIndex = $(this).index();
        var beginIndex = thisIndex-2;
        var endIndex=thisIndex+3;
        if ( beginIndex < 0 ) {
          beginIndex = 0
        }
        $('#bookpage-a>li').slice(beginIndex,endIndex).show()
        if( $('#bookpage-a>li:eq(1)').is(':hidden') && $('#bookpage-a>li:last').prev("li").is(':hidden') ){
          $('#bookpage-a>li').eq(endIndex).after("<li class='ellipsis'>...</li>");
          $('#bookpage-a>li').eq(beginIndex).before("<li class='ellipsis'>...</li>");
        } else if ( $('#bookpage-a>li:eq(1)').is(':visible')  ) {
          if(count == 5 && thisIndex == 1) {
    
          } else {
            $('#bookpage-a>li').eq(endIndex).after("<li class='ellipsis'>...</li>");
          }
        } else if( $('#bookpage-a>li:last').prev("li").is(':visible') ){
          $('#bookpage-a>li').eq(beginIndex).before("<li class='ellipsis'>...</li>");
        }
        $('#bookpage-a>li').removeClass('crt')
        $(this).addClass('crt')
        var number=$(this).text();
        $('#one-page').html(number)
        if(number == count && count>1){
          $('.next').addClass('init')
        } else {
          $('.next').removeClass('init')
        }
        if($(this).index()>0){
          comment.start = ( number-1 )*10+1;
          book.category.listStart = ( number-1 )*10;
          $('.pre').removeClass('init')
        } else if( $(this).index() == 0 ){
          comment.start=1;
          category.listStart = 0
          $('.pre').addClass('init')
          $('.next').removeClass('init')
        }
        if(page == 'replylist'){
          comment.comment_LIst(10)
        } else if ( page == 'commentlist' ) {
          comment.commentLIst()
        } else if(page == 'category') {
          category.categoryLIst()
        }
      })
      $('.pre').click(function(){      //上一页
        if ( $(this).hasClass('init')) {
          return false;
        }
        $('#bookpage-a>li.crt').prev().click();
      })
     
      $('.next').click(function(){
        if ( $(this).hasClass('init') ) {
          return false;
        }
       $('#bookpage-a>li.crt').next().click();
      })
     
    }
  }
  module.exports = common
});

  //spin.js
  (function(a,b,c){function g(a,c){var d=b.createElement(a||"div"),e;for(e in c)d[e]=c[e];return d}function h(a){for(var b=1,c=arguments.length;b<c;b++)a.appendChild(arguments[b]);return a}function j(a,b,c,d){var g=["opacity",b,~~(a*100),c,d].join("-"),h=.01+c/d*100,j=Math.max(1-(1-a)/b*(100-h),a),k=f.substring(0,f.indexOf("Animation")).toLowerCase(),l=k&&"-"+k+"-"||"";return e[g]||(i.insertRule("@"+l+"keyframes "+g+"{"+"0%{opacity:"+j+"}"+h+"%{opacity:"+a+"}"+(h+.01)+"%{opacity:1}"+(h+b)%100+"%{opacity:"+a+"}"+"100%{opacity:"+j+"}"+"}",0),e[g]=1),g}function k(a,b){var e=a.style,f,g;if(e[b]!==c)return b;b=b.charAt(0).toUpperCase()+b.slice(1);for(g=0;g<d.length;g++){f=d[g]+b;if(e[f]!==c)return f}}function l(a,b){for(var c in b)a.style[k(a,c)||c]=b[c];return a}function m(a){for(var b=1;b<arguments.length;b++){var d=arguments[b];for(var e in d)a[e]===c&&(a[e]=d[e])}return a}function n(a){var b={x:a.offsetLeft,y:a.offsetTop};while(a=a.offsetParent)b.x+=a.offsetLeft,b.y+=a.offsetTop;return b}var d=["webkit","Moz","ms","O"],e={},f,i=function(){var a=g("style");return h(b.getElementsByTagName("head")[0],a),a.sheet||a.styleSheet}(),o={lines:12,length:7,width:5,radius:10,color:"#000",speed:1,trail:100,opacity:.25,fps:20,zIndex:2e9,className:"spinner",top:"auto",left:"auto"},p=function q(a){if(!this.spin)return new q(a);this.opts=m(a||{},q.defaults,o)};p.defaults={},p.prototype={spin:function(a){this.stop();var b=this,c=b.opts,d=b.el=l(g(0,{className:c.className}),{position:"relative",zIndex:c.zIndex}),e=c.radius+c.length+c.width,h,i;a&&(a.insertBefore(d,a.firstChild||null),i=n(a),h=n(d),l(d,{left:(c.left=="auto"?i.x-h.x+(a.offsetWidth>>1):c.left+e)+"px",top:(c.top=="auto"?i.y-h.y+(a.offsetHeight>>1):c.top+e)+"px"})),d.setAttribute("aria-role","progressbar"),b.lines(d,b.opts);if(!f){var j=0,k=c.fps,m=k/c.speed,o=(1-c.opacity)/(m*c.trail/100),p=m/c.lines;!function q(){j++;for(var a=c.lines;a;a--){var e=Math.max(1-(j+a*p)%m*o,c.opacity);b.opacity(d,c.lines-a,e,c)}b.timeout=b.el&&setTimeout(q,~~(1e3/k))}()}return b},stop:function(){var a=this.el;return a&&(clearTimeout(this.timeout),a.parentNode&&a.parentNode.removeChild(a),this.el=c),this},lines:function(a,b){function e(a,d){return l(g(),{position:"absolute",width:b.length+b.width+"px",height:b.width+"px",background:a,boxShadow:d,transformOrigin:"left",transform:"rotate("+~~(360/b.lines*c)+"deg) translate("+b.radius+"px"+",0)",borderRadius:(b.width>>1)+"px"})}var c=0,d;for(;c<b.lines;c++)d=l(g(),{position:"absolute",top:1+~(b.width/2)+"px",transform:b.hwaccel?"translate3d(0,0,0)":"",opacity:b.opacity,animation:f&&j(b.opacity,b.trail,c,b.lines)+" "+1/b.speed+"s linear infinite"}),b.shadow&&h(d,l(e("#000","0 0 4px #000"),{top:"2px"})),h(a,h(d,e(b.color,"0 0 1px rgba(0,0,0,.1)")));return a},opacity:function(a,b,c){b<a.childNodes.length&&(a.childNodes[b].style.opacity=c)}},!function(){var a=l(g("group"),{behavior:"url(#default#VML)"}),b;if(!k(a,"transform")&&a.adj){for(b=4;b--;)i.addRule(["group","roundrect","fill","stroke"][b],"behavior:url(#default#VML)");p.prototype.lines=function(a,b){function e(){return l(g("group",{coordsize:d+" "+d,coordorigin:-c+" "+ -c}),{width:d,height:d})}function k(a,d,f){h(i,h(l(e(),{rotation:360/b.lines*a+"deg",left:~~d}),h(l(g("roundrect",{arcsize:1}),{width:c,height:b.width,left:b.radius,top:-b.width>>1,filter:f}),g("fill",{color:b.color,opacity:b.opacity}),g("stroke",{opacity:0}))))}var c=b.length+b.width,d=2*c,f=-(b.width+b.length)*2+"px",i=l(e(),{position:"absolute",top:f,left:f}),j;if(b.shadow)for(j=1;j<=b.lines;j++)k(j,-2,"progid:DXImageTransform.Microsoft.Blur(pixelradius=2,makeshadow=1,shadowopacity=.3)");for(j=1;j<=b.lines;j++)k(j);return h(a,i)},p.prototype.opacity=function(a,b,c,d){var e=a.firstChild;d=d.shadow&&d.lines||0,e&&b+d<e.childNodes.length&&(e=e.childNodes[b+d],e=e&&e.firstChild,e=e&&e.firstChild,e&&(e.opacity=c))}}else f=k(a,"animation")}(),a.Spinner=p})(window,document);




