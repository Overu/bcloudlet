<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
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
        <input type="password" id="password" name="password" placeholder="输入密码" check-type="passWord" />
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="confirmPwd">确认密码</label>
      <div class="controls">
        <input type="password" id="confirmPwd" name="confirmPwd" placeholder="输入确认密码" check-type="confirmPwd" />
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
        <a href="javascript:void(0)" id="save_From" role="button" class="btn">保存</a> <a href="create.html" role="button" class="btn">重置</a>
      </div>
    </div>
  </form>
</section>
<jsp:include page="footer.jsp"></jsp:include>

<script type="text/javascript">
	$('#createform').myValidate("save_From", function() {
		$.ajax({
			type : 'POST',
			url : './',
			data : $("#createform").serialize(),//序列化表单里所有的内容					
			complete : function(data, textStatus) {
				if (textStatus == "success") {
					window.location.href = 'index.html';
				} else {
					window.location.href = 'create.html';
				}

			}
		});
	});
</script>
