<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>图书库</title>
<jsp:include page="/admin/meta.jsp"></jsp:include>
<script src="/static/bootstrap/js/bootstrap.js"></script>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">
  <jsp:include page="/admin/navbar.jsp"></jsp:include>
  <header class="jumbotron subhead" id="overview">
    <div class="container">
      <h2>图书</h2>
      <p class="lead">Dozens of reusable components built to provide navigation, alerts, popovers, and more.</p>
    </div>
  </header>
  <div class="container">
    <div class="row">
      <div class="span3 bs-docs-sidebar">
        <ul class="nav nav-list bs-docs-sidenav">
          <li><a href="create.html"><i class="icon-chevron-right"></i> 上传新书</a></li>
          <li><a href="books.html"><i class="icon-chevron-right"></i> 我的图书</a></li>
          <li><a href="#progress"><i class="icon-chevron-right"></i> 已购图书</a></li>
          <li><a href="#media"><i class="icon-chevron-right"></i> 我的收藏</a></li>
          <li><a href="#misc"><i class="icon-chevron-right"></i> 推荐新书</a></li>
        </ul>
      </div>
      <div class="span9">