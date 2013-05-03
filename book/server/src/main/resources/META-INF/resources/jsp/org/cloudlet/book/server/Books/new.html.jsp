<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>上传新书</title>
<jsp:include page="/admin/meta.jsp"></jsp:include>
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

    <!-- Docs nav
    ================================================== -->
    <div class="row">
      <div class="span3 bs-docs-sidebar">
        <ul class="nav nav-list bs-docs-sidenav">
          <li><a href="new.html"><i class="icon-chevron-right"></i> 上传新书</a></li>
          <li><a href="admin.html"><i class="icon-chevron-right"></i> 我的图书</a></li>
          <li><a href="#progress"><i class="icon-chevron-right"></i> 已购图书</a></li>
          <li><a href="#media"><i class="icon-chevron-right"></i> 我的收藏</a></li>
          <li><a href="#misc"><i class="icon-chevron-right"></i> 推荐新书</a></li>
        </ul>
      </div>
      <div class="span9">
        <section id="newbook">
          <div class="page-header">
            <h1>上传新书</h1>
          </div>
          <form class="form-horizontal">
            <div class="control-group">
              <label class="control-label" for="bookname">书籍名称</label>
              <div class="controls">
                <input type="text" id="bookname" placeholder="bookname">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="summny">描述</label>
              <div class="controls">
                <textarea rows="3" id="summy"></textarea>
              </div>
            </div>
            <div class="control-group">
              <div class="controls">
                <button type="submit" class="btn">上传</button>
              </div>
            </div>
          </form>
        </section>
      </div>
    </div>
  </div>
  <jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>