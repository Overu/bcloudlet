<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户管理</title>
<jsp:include page="/admin/meta.jsp"></jsp:include>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">
  <jsp:include page="/admin/navbar.jsp"></jsp:include>
  <header class="jumbotron subhead" id="overview">
    <div class="container">
      <h2>用户管理</h2>
      <p class="lead">Dozens of reusable components built to provide navigation, alerts, popovers, and more.</p>
    </div>
  </header>
  <div class="container">
    <!-- Docs nav
    ================================================== -->
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