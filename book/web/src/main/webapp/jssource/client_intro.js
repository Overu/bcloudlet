define(function(require, exports, module) {
  var $ = require('jquery')
  $.fn.moveBanner = function(options) {
     var moveCondition ={
       moveBox:'#childrenbox',
       moveChilden:'#childrenbox .desc',
       moveLeft:'.dk-prev',
       moveRight:'.dk-next',
       nav:'.tab>ul>li',
       oneImgWidth:208
     }
    var num =0;
    var i=0;
    var opts = $.extend({},moveCondition,options);
    var boxwidth = $(moveCondition.moveChilden).width()*$(moveCondition.moveChilden).size();
    $(moveCondition.moveBox).width(boxwidth);
    $(moveCondition.moveRight).bind("click",function(){
      if(Math.abs(num) >= boxwidth - $(moveCondition.moveChilden).width()){
       return false
      } else{
        $(moveCondition.moveLeft).show()
        num -= moveCondition.oneImgWidth;
        i++;
        $(moveCondition.moveBox).animate({left:num},300,function(){
        $(moveCondition.nav).find('a').removeClass('crt');
        $(moveCondition.nav).eq(i).find('a').addClass('crt')
        })
      }
      if(Math.abs(num) >= boxwidth - $(moveCondition.moveChilden).width()){
        $(moveCondition.moveRight).hide()
      } 
    })
    $(moveCondition.moveLeft).bind("click",function(){
      if(num == 0 ){
        return false
      } else{
        $(moveCondition.moveRight).show()
        num += moveCondition.oneImgWidth;
        i--;
        $(moveCondition.moveBox).animate({left:num},300,function(){
        $(moveCondition.nav).find('a').removeClass('crt');
        $(moveCondition.nav).eq(i).find('a').addClass('crt')
        })
      }
      if (Math.abs(num) == 0){
       $(moveCondition.moveLeft).hide()
      }
    })
    $(moveCondition.nav).each(function(){
      $(moveCondition.nav).click(function(){
        var index = $(this).index()
        if ( index != $(moveCondition.nav).last().index() ) {
           $(moveCondition.moveRight).show()
        } else {
           $(moveCondition.moveRight).hide()
        }
        if ( index != $(moveCondition.nav).first().index() ) {
           $(moveCondition.moveLeft).show()
        } else {
           $(moveCondition.moveLeft).hide()
        }
        i = index;
        var movenum =-( index*moveCondition.oneImgWidth )
        num = movenum;
        $(moveCondition.moveBox).animate({left:movenum},300)
        $(moveCondition.nav).find('a').removeClass('crt');
        $(this).find('a').addClass('crt')
      })
    })
  }
   $('.m-readerhelp').hover(
     function(){
       if( $('.step2>a').hasClass('crt') ) {
          $('.m-readerhelp>.prev,.m-readerhelp>.next').show()
       } else if( $('.step1>a').hasClass('crt') ) {
          $('.m-readerhelp>.next').show()
       } else if( $('.step3>a').hasClass('crt') ) {
          $('.m-readerhelp>.prev').show()
       }
     },
     function(){
       $('.m-readerhelp>.prev,.m-readerhelp>.next').hide()
     }
   )
   $('#childrenbox').moveBanner()          //选书 、客户端、下载动画
   
  // module.exports = $.noConflict(true);
});
