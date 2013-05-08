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
  <jsp:include page="header.jsp"></jsp:include>
  <div class="container">
    <!-- Docs nav
    ================================================== -->
    <div class="row">
      <jsp:include page="sidebar.jsp"></jsp:include>
      <div class="span9">
        <section id="newbook">
          <div class="page-header">
            <h1>添加用户</h1>
          </div>
          <form id="createFrom" class="form-horizontal">
            <div class="control-group">
              <label class="control-label" for="bookname">姓名</label>
              <div class="controls">
                <input type="text" name="name" placeholder="输入用户真实姓名">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="summny">邮箱</label>
              <div class="controls">
                <input type="text" name="email" placeholder="e.g. mike@example.com">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="summny">手机</label>
              <div class="controls">
                <input type="text" name="phone" placeholder="e.g. +86-13800138000">
              </div>
            </div>
            <div class="control-group">
              <div class="controls">
                <button type="submit" class="btn" id="save_From">保存</button>
              </div>
            </div>
          </form>
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
			$(function() {
				$("#save_From").click(function() {
					$.ajax({
						type : 'POST',
						url : './',
						data : $("#createFrom").serialize(),//序列化表单里所有的内容
						success : function(data) {
							window.location.href = "index.html";
						}
					});
					return false;
				});
			});
		</script>
</body>
</html>
