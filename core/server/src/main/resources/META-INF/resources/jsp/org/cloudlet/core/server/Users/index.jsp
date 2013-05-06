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
            <h1>系统用户</h1>
          </div>
          <table class="table table-striped table-hover">
            <tbody>
              <tr>
                <th>#</th>
                <th>姓名</th>
                <th>登录名</th>
                <th>Email</th>
                <th>手机</th>
                <th>修改</th>
                <th>删除</th>
              </tr>
              <c:forEach var="i" items="${it.items}" varStatus="status">
                <tr>
                  <td>${status.count}</td>
                  <td>${i.title}</td>
                  <td>${i.name}</td>
                  <td>${i.email}</td>
                  <td>${i.phone}</td>
                  <td>修改</td>
                  <td>删除</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <div id="page" class="pagination pagination-centered">
            <ul id="ul_page">
            </ul>
          </div>
        </section>
      </div>
    </div>
  </div>
  <jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
