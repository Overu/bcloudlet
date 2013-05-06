<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Navbar ================================================== -->
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
      </button>
      <a class="brand" href="/admin.html">睿泰书城管理控制台</a>
      <div class="nav-collapse collapse">
        <ul class="nav">
          <li class=""><a href="/books/admin.html">书库</a></li>
          <li class=""><a href="/users/admin.html">用户</a></li>
          <li class=""><a href="/settings.html">系统设置</a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
//设置navbar选中高亮显示
$(function(){
	 //alert(window.location.href);
	 //alert(window.location.host);
	 //var host = window.location.host;
	 var href = window.location.href;
	 //去掉http的URL
	 var temphref = href.substr(7);
	 //得到URI目录的二级地址
	 var splithref = temphref.split("/")[1];
	 //取得navbar的值
	 $('.nav :first').children().each(function(){
		 var ahref = $(this).children().attr('href');
		 if(ahref.indexOf(splithref)>0){
			 $(this).addClass('active');
		 }			
	});				 
});				

</script>
