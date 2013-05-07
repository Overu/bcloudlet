<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer class="g-ft">
  <div class="m-cprt">
    Copyright&copy;Retechcorp.com <a href="http://www.miibeian.gov.cn/" target="_blank" hidefocus="hidefocus">京ICP备XXX号</a> <a
      href="http://www.gapp.gov.cn/" target="_blank" hidefocus="hidefocus">电复证字第XXX号</a>
  </div>
</footer>
<script src="/static/jquery/jquery.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
//设置navbar选中高亮显示
$(function(){
   var href = window.location.href;
   var lastSlash = href.lastIndexOf("/");
   if (lastSlash != href.length-1) {
	   var end = href.substring(lastSlash+1);
	   if(end.length>5){
		   if(end.substring(end.length-5)!=".html"){
			   href = href +"/index.html" ;
		   }
	   }else{
		   href = href +"/index.html" ;
	   }	  
   } else {
	   href = href +"index.html" ;
   }
   lastSlash = href.lastIndexOf("/");
   var parent = href.substring(0, lastSlash);
   $('.nav :first').children().each(function(){
       var ahref = $(this).children().attr('href');
       if (ahref.charAt(0)=="/") {
    	   ahref = window.location.protocol + "//"+window.location.host+ahref;
       }
       if(ahref.indexOf(parent)==0){
         $(this).addClass('active');
       }      
  });   
   $('.nav :last').children().each(function(){
       var ahref = $(this).children().attr('href');
       ahref = parent +"/"+ ahref;
       if(ahref==href){
         $(this).addClass('active');
       }      
  });
});       
</script>
