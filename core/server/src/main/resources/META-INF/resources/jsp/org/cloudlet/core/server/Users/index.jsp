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
        <%-- <section id="newbook">
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
                  <td><button onclick="deleteItem('${i.uri}')" class="btn">删除</button></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <div id="page" class="pagination pagination-centered">
            <ul id="ul_page">
            </ul>
          </div>
        </section> --%>
        <section id="newbook">
          <div class="container-fluid">
            <div class="page-header">
              <h1>系统用户</h1>
              </div>              
              <div id="usersGrid"></div>            
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

  <!-- Modal -->
  <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3 id="myModalLabel">Modal header</h3>
    </div>
    <div class="modal-body">
      <form id="editform" class="form-horizontal">
            <div>
              <input type="hidden" id="uri" >
            </div>
            <div class="control-group">
              <label class="control-label" for="username">姓名</label>
              <div class="controls">
                <input type="text" id="username" name="name" placeholder="输入用户真实姓名" check-type="required" required-message="姓名不能为空！" />
              </div>
            </div>
            <!-- <div class="control-group">
              <label class="control-label" for="password">密码</label>
              <div class="controls">
                <input type="password" id="password" name="password" placeholder=".........." check-type="passWord"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="confirmPwd">确认密码</label>
              <div class="controls">
                <input type="password" id="confirmPwd" name="confirmPwd" placeholder=".........." check-type="confirmPwd"/>
              </div>
            </div> -->
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
          </form>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
      <button id="save-edit" class="btn btn-primary">Save changes</button>
    </div>
  </div>


  <script type="text/javascript">
  
//响应修改按钮
	function editItem(item) {
		$.ajax({
			type : 'get',
			url : item.id,
			dataType: 'json',
			success : function(data) {
				$("#username").val(data.name);
				$("#inputEmail").val(data.email);
				$("#phone").val(data.phone);
				$("#uri").val(data.uri);
				$('#myModal').modal('show')
			}
		});
	}
	
	$('#editform').myValidate("save-edit", function() {
		$.ajax({
			type : 'put',
			url : $("#uri").val(),
			data : $("#editform").serialize(),//序列化表单里所有的内容
			success : function(data) {
				$('#myModal').modal('hide');
				window.location.href = "index.html";
			},
			complete:function() {
				window.location.href = "index.html";
			}
		});				
	});
	
			//响应删除按钮
  			function deleteItem(item) {
				$.ajax({
					type : 'delete',
					url : item.id,
					success : function() {
						window.location.reload();
					}
				});
			}

			//构建显示列表数据
			function editTable(data){		        		
    					        			        		
    	    }
			
			//页面初始化查询数据
/* 			$(function() {
				
				 $.ajax({
  		        	type: 'get',
  		        	url: '/users',
  		        	dataType: 'json',
  		        	success: function(data){ */
  		        		
  		        		$("#usersGrid").simplePagingGrid({
  		        			dataUrl: '/users',
  				            columnNames: ["#", 
  				                          "姓名", 
  				                          "Email",
  				                          "手机",
  				                          "修改",
  				                          "删除"],
  				            columnKeys: ["",
  				                         "name",
  				                         "email",
  				                         "phone",
  				                          "修改",
  				                          "删除"],
  				            columnWidths: ["5%", "15%", "25%","25%","10%","10%"],
  				            sortable: [false,true, false, false, false, false],
  				            initialSortColumn: "name",
  				            cellTemplates:[
  				                           null,
  				                           null,
  				                           null,
  				                           null,
  				                           "<button id='{{uri}}' onclick='editItem(this)' class='btn'>修改</button>",
  				                           "<button id='{{uri}}' onclick='deleteItem(this)' class='btn'>删除</button>"],
  				                         dataFunction: function(currentPage, pageSize, sortedColumn, sortOrder) {
  				                        	 var start = currentPage*pageSize;
  				                        	$.ajax({
  				            		        	type: 'get',
  				            		        	url: '/users?start='+start+"&limit="+pageSize,
  				            		        	dataType: 'json',
  				            		        	success: function(data){
  				            		        		return data;	
  				            		        	}
  				            		        	});
  				                        	return data;
  				                         }
  /* 				            data: {
  				                currentPage: data.items,
  				                totalRows: data.count
  		                    } */
  				        });
/*   		        	}	        	
  		        });
				 
				
			}); */
		</script>
</body>
</html>
