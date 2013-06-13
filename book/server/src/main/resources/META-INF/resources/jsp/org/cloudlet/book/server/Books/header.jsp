<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include parent="true" page="header.jsp"></w:include>
<header class="jumbotron subhead" id="overview">
  <div class="container">
    <ul class="nav nav-pills">
      <li><a href="/books/">首页</a></li>
      <li><a href="/books/r/hot">排行榜</a></li>
      <li><a href="/books/r/recommendation">精品<span class="dot">·</span>特价
      </a></li>
      <li><a href="/books/t">分类</a></li>
      <li><a href="/client.html">客户端</a></li>
    </ul>
  </div>
</header>
<div class="container">