
// 所有模块都通过 define 来定义
	define(function(require, exports, module) {

	  // 通过 require 引入依赖
	  var $ = require('jquery');
	  function showpwddiv(){
			var $divpwd = $("<div id='div-pwd'>"+
		       "<div class='control-group'>"+
		        "<label class='control-label' for='password'>密码</label>"+
		        "<div class='controls'>"+
		        " <div class='input-append' data-role='acknowledge-input'>"+
		         "<input type='password' id='password' name='password' placeholder='输入密码'  required='required' data-type='passWord' check-type='passWord' maxlength='16' />"+
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
		          "<input type='password' id='confirmPwd' name='confirmPwd' placeholder'输入确认密码' required='required' data-type='confirmPwd'  check-type='confirmPwd' />"+
		          " <div data-role='acknowledgement'>"+
		          " <i></i>"+
		          "</div>"+
		        "</div>"+
		      "</div>"+
		      "</div>");
		      
			if($("#chk-pwd").get(0).checked){
				$divpwd.insertAfter($("#check-box"));			
				$().acknowledgeinput();
				$.fn.validateBlurForm();
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
					editFormini();
					$("#chk-pwd").get(0).checked =false;
					$("#username").val(data.name);
					$("#inputEmail").val(data.email);
					$("#phone").val(data.phone);
					$("#uri").val(data.uri);
					
					editFormBlur();
					$('#myModal').modal('show');				
				}
			});
		}

		//提交表单数据更新数据库
		$('#editform').myValidate("save-edit", createInstance); 

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
			bootbox.confirm("是否确定删除此数据?", "取消", "确定", function(result){
				if(result){
					$.ajax({
						type : 'delete',
						url : item.id,
						success : function() {
							//刷新table
							$("#usersGrid").simplePagingGrid("refresh");
						}
					});
				};			
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
	});