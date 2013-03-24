define(function(require, exports, module) {
  var $ = require('jquery');
  var common = require('./common'); 
  
  //图片加载完成后显示
  var onLoadImg =function(){                                         
    $('img').each(function(){
      $(this).load(function(){
        $(this).removeClass('f-dpn')
      }) 
    })
    var img =document.getElementsByTagName('img')
    for( var i =0; i< img.length; i++){
      if (img[i].complete){
        $(img[i]).removeClass('f-dpn')
      }
    }
  }
  

  
  module.exports = onLoadImg()
  
  //看过此书的人还看过动画
  var bookpostion = 0;                                                
  var length = $('#related-book>ul>li').size();
  if( length <= 5 ) {
    $('#botton-btn').hide();
  }
  var li_width = 131;
  $('#related-book > ul').width(li_width*length)
  $('.w-tab-btn>.right').click(function(){
    if( Math.abs( bookpostion ) >= $('#related-book>ul').width()-655){ 
     return false
    } else{
      $('.w-tab-btn>.left').removeClass("left-final")
      bookpostion-=655;
      $('#related-book>ul').stop(true, true);
      $('#related-book>ul').animate({left:bookpostion}, "4000",function(){
        if( Math.abs( bookpostion ) >= $('#related-book>ul').width()-655){ 
          $('.w-tab-btn>.right').addClass("right-final")
        }
      });
    }
  })
  $('.w-tab-btn>.left').click(function(){
    if( bookpostion == 0){ 
      return false
    } else {
      $('.w-tab-btn>.right').removeClass("right-final")
      bookpostion+=655
      $('#related-book>ul').stop(true, true);
      $('#related-book>ul').animate({left: bookpostion}, "4000",function(){
        if( bookpostion == 0){ 
          $('.w-tab-btn>.left').addClass("left-final")
        } 
      });
    }
  }) 
  
   //文章显示隐藏
  var oldHeight= 0;
  $('#book-content p').each(function(){
     oldHeight = oldHeight 
               + $(this).outerHeight() 
               + (+$(this).css('margin-top').slice(0,-2))
               + (+$(this).css('margin-bottom').slice(0,-2));
  });          
  var newHeight=220;
  if(oldHeight<=newHeight){
    $('.showall-txt').hide();
  }
  $('#book-content').height(newHeight).css('overflow','hidden')
  $('.showall-txt').toggle(
    function(){
      $('#book-content').animate({height:oldHeight},500,function(){
        $('.showall-txt>a').html('隐藏部分').removeClass('w-more-open').addClass('w-more-close')
      })
    },
    function(){
      $('#book-content').animate({height:newHeight},500,function(){
        $('.showall-txt>a').html('显示全部').removeClass('w-more-close').addClass('w-more-open')
      })
    }
  )

  $.fn.pointout=function(options){                                //提示语
    var setoptions = {
      firstColor:'#ccc',//获取焦点没有输入时提示语颜色
      secondColor:'#666',//有输入时提示语颜色
      treeColor:'#aaa',//没有输入提示语颜色
      wordsName:'#emailpassworld span' //提示语class或id
    }
    var opts = $.extend(setoptions,options);
    return $(this).each(function(){
      var $this = $(this)
      $this.val('');
      $this.bind('input propertychange',function(){
        $(this).val().trim() == '' ? $(setoptions.wordsName).show()
                                   : $(setoptions.wordsName).hide();
      });
       $this.bind({
         focus:function(){
           $(setoptions.wordsName).css('color',setoptions.firstColor)
           $this.css('color',setoptions.secondColor)
         },
         focusout:function(){
           if($this.val() != '' ) {
             $(setoptions.wordsName).css('color',setoptions.secondColor)
           } else {
             $(setoptions.wordsName).css('color',setoptions.treeColor)
           }
         }
      })
      $(setoptions.wordsName).click(function(){
          var $that = $this;
           $(setoptions.wordsName).css('color',setoptions.firstColor)
           $that.focus()
      })
    })
  }



  /*
   * 登录框提示
   */  
  $('#dkemail').pointout({ wordsName:'#emailts'})//提示语
  $('#dkpassword').pointout({ wordsName:'#wordts'})//提示语
  $('#dkredeem').pointout({ wordsName:'.deem-title'})//兑换码提示语
  $('.j-comment-title').pointout({ wordsName:'#title-warning'})
  $('.j-comment-content').pointout({ wordsName:'#comment-content'})
  
  $("input[name='findpassword']").pointout();

  function dropDownMenu(obj){//下拉菜单显示
    var hidden = $(obj).parents(".w-dropdown-menu").children(".w-dropdown-menu .cnt").is(":hidden");
    $(".cnt-top").hide();
    $(obj).parents(".w-dropdown-menu").children(".w-dropdown-menu .cnt").toggle();
  }
   $(".w-dropdown-menu .btn").click(function(){dropDownMenu(this);return false;});
   $(".w-dropdown-menu .w-btn").click(function(){dropDownMenu(this);return false;});
   $(document).click(function () { $(".w-dropdown-menu .cnt,#m-changelog").hide() });
   
    // 帐号相关
    var token =common. getCookie('token');
    var name = common.getCookie('name');
    var email = common.getCookie('user_id');
    if (token !='' && token !=null && token != undefined ) {
      if ( name =='' || name ==null || name == undefined ) {
        name = email;
      } 
      $('.w-login').hide();
      $('#w-person').show()
      $('#username').html(name) 
    } else {
      $('#w-login').show();
      $('#w-person').hide();
    }
    $('#exit').click(function(){
      $('#w-login').show();
      $('#w-person').hide();
      exit();
      var query = location.pathname.substring(1);
      var u ="u/";
      if ( query.indexOf(u)== -1 ) {
        location.reload();
      } else {
        document.location ="/"
      }
    });
    function exit(){
      var email=common.getCookie('user_id');
      var token=common.getCookie('token');
      var deviceid=common.getCookie('device_id');
      var appid='web';
      var format='json';
      url= '/dk_id/api/account/logged/'+ encodeURIComponent(email);
      common.delCookie('token');
      common.delCookie('name');
      common.delCookie('user_id')
      common.delBasicCookie('token');
      common.delBasicCookie('name');
      common.delBasicCookie('user_id');
      $.ajax({
        url:url,
        success:function(data){ },
        data: 'token='+encodeURIComponent(token)+'&deviceid='+encodeURIComponent(deviceid)+'&appid='+appid+'&format=json',
        dataType:'json',
        type:'post'
      });
    }
  // 为了跳回做设置
  $('.m-nav ul li a').click(function(){
    var href=$(this).attr('href');
    common.addBasicCookie('href',href,0);
  });
  $('#gotologin').click(function(){
    common.addBasicCookie('href',window.location,0);
    window.location='/login';
  })
    
  // 搜索
  $('#dk-text').val('搜索书名或者作者...');
  $('#dk-text').mouseout(function(){
    if ( $('#dk-text').val()!= '搜索书名或者作者...' ) {
      $('.searchform').addClass('crt')
    } else {
      $('.searchform').removeClass('crt')
    }
    
  })
  $('#dk-text').mouseover(function(){
    $('.searchform').addClass('crt')
  })
  function goTo(_url) { 
    setTimeout(function(){
      window.location.href = _url;
    },0);
  };
  var text = '搜索书名或者作者...';
  $('#searchbotton').click(function(){
    var keywords=$('#dk-text').val();
    if( keywords != '' && keywords != null && keywords !=undefined && keywords!=text){
      keywords = keywords.replace(/[\.\/]/g, ' ');
      var _encode = encodeURIComponent(keywords);
      goTo("/search/" + _encode + "/1");
    }
  });
  var interval = null;
  
  $('#dk-text').bind('input propertychange',showEnter);
  function showEnter() {
    if($('#dk-text').val()!='' && $('#dk-text').val()!= '搜索书名或者作者...' ) {
       $('#searchbotton').fadeIn();
    } else{
       $('#searchbotton').fadeOut();
    }
  }
   //出版列表页 
    var press_name = common.getArgs("press_name")
    if ( press_name != undefined ) {
      $('.press_name').html(press_name)
    }
  $('#dk-text').bind({
    keydown: function (e) {
      var key = e.which ;//兼容IE(e.keyCode)和Firefox(e.which)
      if (key == "13") {
        var ANNC=$.trim('搜索书名或者作者...');
        var keywords=$('#dk-text').val();
        if( keywords != '' && keywords != null && keywords !=undefined && keywords!=ANNC){
          keywords = keywords.replace(/[\.\/]/g, ' ');
          var _encode = encodeURIComponent(keywords);
          goTo("/search/" + _encode + "/1");
        }
      }
    },
    focus: function () {
      if($('#dk-text').val()==text){
        $('#dk-text').val('');
        $('.searchform').addClass('crt')
        $('#searchbotton').fadeOut();
      }
      interval = setInterval(showEnter, 100);
    },
    focusout: function () {
      if($('#dk-text').val()==''){
        $('#dk-text').val(text);
        $('.searchform').removeClass('crt')
      }
      clearInterval(interval);
      interval = null;
    }
  });
    
// 回到顶部按钮
var isIE=!!window.ActiveXObject;
var isIE6=isIE&&!window.XMLHttpRequest;
function setBackToTop() {
  if ($('#topcontrol').length == 0 || isIE6) return;
  function backToTop () {//返回顶部
    var maright=( $(window).width()-1010 )/2+1016 ;
    $('#topcontrol').css("left",maright)
    $(window).resize(function(){
       var maright=( $(window).width()-1010 )/2+1016;
       if($(window).width()<=1010){
        $('#topcontrol').css("left",1010)
       }else if($(window).width()>1010){
        $('#topcontrol').css("left",maright)
      }
    })
  }
  
  function setPosition () {
    if (!isIE6) {
      var scrollHeight = $(document).scrollTop();
      var backToTop = $('#topcontrol');
      (scrollHeight > 0)? backToTop.show(): backToTop.hide();
    }
  }
  
  $(window).bind("scroll" , function(){
    setPosition();
    backToTop();
  });
  $('#topcontrol').click(function(){
    $(window).scrollTop(0);
  })
}
    

  setBackToTop(); // 回到顶部
  
  function setNavFix() {  //导航静止
    $(window).scroll(function () {
      var scrollHeight = $(document).scrollTop();
      var cnt = $("#navFix");
      (scrollHeight >=87) ? cnt.addClass("w-fixed") : cnt.removeClass("w-fixed");
    });
  }
  setNavFix()  
  
  //changelog
  $('#changelog').click(function(_event){
    //替换href,目的是防止被抓取
    var _a = $('#log-more a');
    _a.attr('href',_a.data('location'));
    
    _event.stopPropagation();
    $('.m-changelog').show();
    if( $('.m-changelog > .wrap > ul').height() >= 180 ) {
      $('#log-more').show();
    }
  })

});






