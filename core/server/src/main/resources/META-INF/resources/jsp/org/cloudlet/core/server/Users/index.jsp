<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="users">
  <div class="container-fluid">
    <div class="page-header">
      <h1>系统用户</h1>
    </div>
    <div class="input-append" align="right">
      <input id="search-name" class="span2" id="appendedInputButtons" type="text">
      <button id="search-btn" class="btn" onclick="searchData();" type="button">查询</button>
    </div>

    <div id="usersGrid"></div>
  </div>
</section>
<jsp:include page="footer.jsp"></jsp:include>

<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">修改用户信息</h3>
  </div>
  <div class="modal-body">
    <form id="editform" class="form-horizontal">
      <div>
        <input type="hidden" id="uri">
      </div>
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
      <div class="control-group" id="check-box"> 
        <div class="controls">
          <label class="checkbox"> <input id="chk-pwd" type="checkbox" onclick="showpwddiv()" > 点击修改密码
          </label>         
        </div>
      </div>      
    </form>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>    
    <button id="save-edit" type="submit" class="btn btn-primary" onclick="createInstance();">保存</button>
  </div>
</div>


<script type="text/javascript">
	function showpwddiv(){
		var $divpwd = $("<div id='div-pwd'>"+
	       "<div class='control-group'>"+
	        "<label class='control-label' for='password'>密码</label>"+
	        "<div class='controls'>"+
	        " <div class='input-append' data-role='acknowledge-input'>"+
	         "<input type='password' id='password' name='password' placeholder='输入密码'  required='required' data-type='password' />"+
	         " <div data-role='acknowledgement'>"+
	          " <i></i>"+
	          "</div>"+
	          "</div>"+
	        "</div>"+
	      "</div>"+
	      "<div class='control-group'>"+
	        "<label class='control-label' for='confirmPwd'>确认密码</label>"+
	        "<div class='controls'>"+
	        " <div class='input-append' data-role='acknowledge-input'>"+
	          "<input type='password' id='confirmPwd' name='confirmPwd' placeholder'输入确认密码' required='required' data-type='chkpwd' />"+
	          " <div data-role='acknowledgement'>"+
	          " <i></i>"+
	          "</div>"+
	        "</div>"+
	      "</div>"+
	      "</div>");
	      
		if($("#chk-pwd").get(0).checked){
			$divpwd.insertAfter($("#check-box"));			
			$().acknowledgeinput();
		}else{
			$("#div-pwd").remove();			
		}
	}
	//响应查询按钮
	function searchData() {
		var searchname = $("#search-name").val();
		var uri = searchname == "" ? "/users" : "/users" + "?search=name\|"
				+ searchname;
		$("#usersGrid").simplePagingGrid("refresh", uri);
	}
	
	//初始化编辑form页面
	var editFormini = function(){
	$("#username").val(null);
	$("#inputEmail").val(null);
	$("#phone").val(null);	
	$("#uri").val(null);
};

//后台调用输入框的blur事件	
var editFormBlur = function() {
	$("#username").blur();
	$("#inputEmail").blur();
	$("#phone").blur();	
};


	//响应修改按钮
	//查询数据并填充表单数据
	function editItem(item) {
		$.ajax({
			type : 'get',
			url : item.id,
			dataType : 'json',
			success : function(data) {
				$("#div-pwd").remove();
				$("#chk-pwd").get(0).checked =false;
				$("#username").val(data.name);
				$("#inputEmail").val(data.email);
				$("#phone").val(data.phone);
				$("#uri").val(data.uri);
				$('#myModal').modal('show');				
			}
		});
	}

	/* //提交表单数据更新数据库
	$('#editform').myValidate("save-edit", function() {
		$.ajax({
			type : 'put',
			url : $("#uri").val(),
			data : $("#editform").serialize(),//序列化表单里所有的内容
			success : function(data) {
				$('#myModal').modal('hide');
				$("#usersGrid").simplePagingGrid("refresh");
			}
		});
	}); */

	$().ready(function() {
		$().acknowledgeinput();
	});

	function createInstance() {
		var options = {
			url : $("#uri").val(),
			type : 'put',
			data : $('#editform').formSerialize(),
			success : function(data) {
				$('#myModal').modal('hide');
				$("#usersGrid").simplePagingGrid("refresh");
			}
		};

		$("#editform").ajaxSubmit(options);
		return false;
	}
	
	//响应删除按钮
	function deleteItem(item) {
		if (!confirm("是否确定删除此数据?")) {
			return false;
		}
		$.ajax({
			type : 'delete',
			url : item.id,
			success : function() {
				//刷新table
				$("#usersGrid").simplePagingGrid("refresh");
			}
		});
	}

	//编辑Table数据
	$("#usersGrid")
			.simplePagingGrid(
					{
						dataUrl : '/users',
						columnNames : [ "#", "姓名", "Email", "手机", "修改", "删除" ],
						columnKeys : [ "rowId", "name", "email", "phone", "修改", "删除" ],
						columnWidths : [ "5%", "15%", "25%", "20%", "12%",
								"13%" ],
						sortable : [ false, true, false, false, false, false ],
						initialSortColumn : "name",
						cellTemplates : [
								null,
								null,
								null,
								null,
								"<button id='{{uri}}' onclick='editItem(this)' class='btn'>修改</button>",
								"<button id='{{uri}}' onclick='deleteItem(this)' class='btn'>删除</button>" ]

					});
</script>
