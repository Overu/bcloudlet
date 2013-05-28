<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="newbook">
  <div class="page-header">
    <h1>添加用户</h1>
  </div>
  <form id="createform" class="form-horizontal" >
    <div class="control-group">
      <label class="control-label" for="username">姓名</label>
      <div class="controls">
      <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="username" name="name" placeholder="输入用户真实姓名" required="required" data-type="text" />
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="password">密码</label>
      <div class="controls">
       <div class="input-append" data-role="acknowledge-input">
        <input type="password" id="password" name="password" placeholder="输入密码" required="required" data-type="password" maxlength="16"/>
        <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="confirmPwd">确认密码</label>
      <div class="controls">
      <div class="input-append" data-role="acknowledge-input">
        <input type="password" id="confirmPwd" name="confirmPwd" placeholder="输入确认密码" required="required" data-type="chkpwd" />
         <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="inputEmail">邮箱</label>
      <div class="controls">
      <div class="input-append" data-role="acknowledge-input">
        <input type="text" id="inputEmail" name="email" placeholder="123@163.com" required="required" data-type="email"/>
         <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="phone">手机</label>
      <div class="controls">
       <div class="input-append" data-role="acknowledge-input">
        <input type="text" id="phone" name="phone" placeholder="13800138000" required="required" data-type="mobile" maxlength="11">
         <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
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
        <!-- <a href="javascript:void(0)" id="save_From" onclick="createInstance();" role="button" class="btn">保存</a> -->
        <button id="save_From" type="button" class="btn" onclick="createInstance();">保存</button>
        <a href="create.html" role="button" class="btn">重置</a>
      </div>
    </div>
  </form>
</section>
<jsp:include page="footer.jsp"></jsp:include>

<script type="text/javascript">
$().ready(function() {
	$().acknowledgeinput();		
});

function createInstance(){
	 var options = {
			 	url:'./',			 	
			 	type:'POST',			 
			 	data: $('#createform').serialize(),
	            success : function(data) { 		            	
					window.location.href = 'index.html';
	             },
	             complete : function(data, textStatus) {
	 				if (textStatus == "success") {
	 					window.location.href = 'index.html';
	 				} else {
	 					window.location.href = 'create.html';
	 				}

	 			}
	        };		
		$("#createform").ajaxSubmit(options);
		return false;
/* 		
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
	}); */
}

/* 	$('#createform').myValidate("save_From", function() {
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
	}); */
</script>
