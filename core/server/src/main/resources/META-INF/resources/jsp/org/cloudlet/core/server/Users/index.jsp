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
  <jsp:include page="header.jsp"></jsp:include>
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
                  <td><button onclick="deleteItem('${i.uri}')">删除</button></td>
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
  <footer class="footer">
    <div class="container" style="height: 5%;">
      <jsp:include page="/footer.jsp"></jsp:include>
    </div>
  </footer>
  <script type="text/javascript">
			function deleteItem(uri) {
				$.ajax({
					type : 'delete',
					url : uri,
					success : function() {
						window.location.reload();
					}
				});
			}
		</script>
</body>
</html>
