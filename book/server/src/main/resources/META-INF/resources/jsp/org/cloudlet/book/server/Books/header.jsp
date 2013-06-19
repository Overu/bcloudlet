<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include parent="true" page="header.jsp"></w:include>
<header class="jumbotron subhead m-header" id="overview">
  <div class="container">
  <div class="m-logo">
      <a href="/" title="睿泰书城" hidefocus="hidefocus" class="m-logo">睿泰书城</a>
    </div>
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
<!-- Docs nav    ================================================== -->
  <div class="row">
    <div class="span3 bs-docs-sidebar">
      <ul class="nav nav-list bs-docs-sidenav">
        <div>
          <h3>图书分类</h3>
        </div>
        <c:forEach var="i" items="${it.tags.items}">          
            <li><a href="/books/t/${i.value}" hidefocus="hidefocus"><span>${i.value}</span></a></li>         
        </c:forEach>
      </ul>
    </div>