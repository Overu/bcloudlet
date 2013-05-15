<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="mybooks">
  <div class="container-fluid">
    <div class="page-header">
      <h1>系统用户</h1>
    </div>
    <div class="input-append" align="right">
      <input id="search-name" class="span2" id="appendedInputButtons" type="text">
      <button id="search-btn" class="btn" onclick="searchData();" type="button">查询</button>
    </div>
    <div id="booksGrid"></div>
  </div>
</section>
<jsp:include page="footer.jsp"></jsp:include>

<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">修改书籍信息</h3>
  </div>
  <div class="modal-body">
    <form id="editform" class="form-horizontal">
      <div>
        <input type="hidden" id="uri">
      </div>
      <div class="control-group">
        <label class="control-label" for="booktitle">书名</label>
        <div class="controls">
          <input type="text" id="booktitle" name="title" placeholder="输入书名" check-type="required" required-message="书名不能为空！" />
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="booksummary">摘要</label>
        <div class="controls">
          <textarea rows="3" cols="30" id="booksummary" name="summary" placeholder="输入书籍摘要" check-type="required" required-message="摘要不能为空！"></textarea>
          
        </div>
      </div>
    <!--   <div class="control-group">
        <label class="control-label" for="password">密码</label>
        <div class="controls">
          <input type="password" id="password" name="password" placeholder="输入密码" check-type="passWord" />
        </div>
      </div> -->
      <!-- <div class="control-group">
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
      </div> -->
    </form>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    <button id="save-edit" class="btn btn-primary">保存</button>
  </div>
</div>


<script type="text/javascript">
	//响应查询按钮
	function searchData() {
		var searchname = $("#search-name").val();
		var uri = searchname == "" ? "/books" : "/books" + "?search=title\|"
				+ searchname;
		$("#booksGrid").simplePagingGrid("refresh", uri);
	}

	//响应修改按钮
	//查询数据并填充表单数据
	function editItem(item) {
		$.ajax({
			type : 'get',
			url : item.id,
			dataType : 'json',
			success : function(data) {
				$("#booktitle").val(data.title);
				$("#booksummary").val(data.summary);
				/* $("#password").val(data.passwordHash);
				$("#confirmPwd").val(data.passwordHash);
				$("#phone").val(data.phone); */
				$("#uri").val(data.uri);
				$('#myModal').modal('show')
			}
		});
	}

	//提交表单数据更新数据库
	$('#editform').myValidate("save-edit", function() {
		$.ajax({
			type : 'put',
			url : $("#uri").val(),
			data : $("#editform").serialize(),//序列化表单里所有的内容
			success : function(data) {
				$('#myModal').modal('hide');
				$("#booksGrid").simplePagingGrid("refresh");
			}
		});
	});

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
				$("#booksGrid").simplePagingGrid("refresh");
			}
		});
	}

	//编辑Table数据
	$("#booksGrid")
			.simplePagingGrid(
					{
						dataUrl : '/books',
						columnNames : [ "#", "书名", "作者", "修改", "删除" ],
						columnKeys : [ "rowId", "title", "authors", "修改", "删除" ],
						columnWidths : [ "5%", "30%","20%", "12%",
								"13%" ],
						sortable : [ false, true, false, false, false ],
						initialSortColumn : "title",
						cellTemplates : [
								null,
								null,
								null,
								"<button id='{{uri}}' onclick='editItem(this)' class='btn'>修改</button>",
								"<button id='{{uri}}' onclick='deleteItem(this)' class='btn'>删除</button>" ]

					});
</script>
