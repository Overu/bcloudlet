<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="span3 bs-docs-sidebar">
  <ul class="nav nav-list bs-docs-sidenav">
    <li><a href="create.html"><i class="icon-chevron-right"></i>添加用户</a></li>
    <li><a href="index.html"><i class="icon-chevron-right"></i>系統用戶</a></li>
    <li><a href="admin.html"><i class="icon-chevron-right"></i>管理员</a></li>
    <li><a href="latest.html"><i class="icon-chevron-right"></i>待审批用户</a></li>
    <li><a href="pending.html"><i class="icon-chevron-right"></i>待审批用户</a></li>
    <li><a href="archived.html"><i class="icon-chevron-right"></i>归档用户</a></li>
  </ul>
</div>
<script type="text/javascript">
$(function(){
	var href = window.location.href;
	 //去掉http的URL
	 var temphref = href.substr(7);
	 //得到URI目录的二级地址
	 var splithref = temphref.split("/")[2];
	 //alert(splithref);
	 if(splithref==null||splithref==""){
		 splithref = "index.html"; 
	 }
	 //alert(splithref);
	 //取得navbar的值
	 $('.nav :last').children().each(function(){
		 var ahref = $(this).children().attr('href');
		 if(ahref==splithref){
			 $(this).addClass('active');
		 }			
	});		
});
</script>
