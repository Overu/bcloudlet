<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<div class="container">
  <!-- Docs nav    ================================================== -->
  <div class="row">
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
    <div class="span9">
<section>
  <div class="container-fluid">
    <div class="page-header">
      <h1>推荐阅读</h1>
    </div>
    <div>
      <jsp:include page="booklist.jsp"></jsp:include>
    </div>
  </div>
</section>
</div>
<w:include page="footer.jsp"></w:include>