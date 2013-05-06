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
      <jsp:include page="sidebar.jsp"></jsp:include>
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
  <jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
