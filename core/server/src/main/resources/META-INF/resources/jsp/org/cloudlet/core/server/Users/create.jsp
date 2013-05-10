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
          <form id="createform" class="form-horizontal">
            <div class="control-group">
              <label class="control-label" for="username">姓名</label>
              <div class="controls">
                <input type="text" id="username" name="name" placeholder="输入用户真实姓名" check-type="required" required-message="姓名不能为空！" />
              </div>
            </div>
           <div class="control-group">
              <label class="control-label" for="password">密码</label>
              <div class="controls">
                <input type="password" id="password" name="password" placeholder="输入密码" check-type="passWord"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="confirmPwd">确认密码</label>
              <div class="controls">
                <input type="password" id="confirmPwd" name="confirmPwd" placeholder="输入确认密码" check-type="confirmPwd"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="inputEmail">邮箱</label>
              <div class="controls">
                <input type="text" id="inputEmail" name="email" placeholder="123@163.com" check-type="mail" mail-message="邮箱格式不正确！">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="phone">手机</label>
              <div class="controls">
                <input type="text" id="phone" name="phone" placeholder="13800138000" check-type="mobile" maxlength="11">
              </div>
            </div>
            <!-- <div class="control-group">
              <label class="control-label" for="state">籍贯</label>
              <div class="controls">
                <input type="text" id="state" name="state" placeholder="北京" check-type="mobile" maxlength="11">
              </div>
            </div> -->
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
			$('#createform').myValidate("save_From", function() {
				$.ajax({
					type : 'POST',
					url : './',
					data : $("#createform").serialize(),//序列化表单里所有的内容
					success : function(data) {
						window.location.href = "www.baidu.com";
					},
					complete:function() {
						//window.location.href = "www.baidu.com";
						window.location.replace("http://www.baidu.com");  
					}
				});				
			});
		</script>

</body>
</html>
