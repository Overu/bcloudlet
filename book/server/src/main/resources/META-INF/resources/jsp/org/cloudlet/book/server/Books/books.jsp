<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="mybooks">
  <div class="container-fluid">
    <div class="page-header">
      <h1>图书</h1>
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
      <label class="control-label" for="booktitle">名称</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="booktitle" name="title" placeholder="bookname" required="required" data-type="text" />
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="tags">图书类别</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <select id="tags" required="required" name="tags" multiple="multiple" data-role="multiselect">
            <option value="小说">小说</option>
            <option value="文学">文学</option>
            <option value="杂志">杂志</option>
            <option value="计算机">计算机</option>
            <option value="法律">法律</option>
          </select>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="authors">作者</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="authors" name="authors" placeholder="作者" required="required" data-type="text" />
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="price">价格</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="price" name="price" placeholder="价格(两位小数)" required="required" data-type="decimal" />
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="new_price">最新价格</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="new_price" name="new_price" placeholder="最新价格(两位小数)" required="required" data-type="decimal" />
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="paper_price">纸质书价格</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="paper_price" name="paper_price" placeholder="纸质书籍价格(两位小数)" required="required" data-type="decimal" />
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="summary">摘要</label>
      <div class="controls">
        <textarea style="width: 500px;" cols="50" rows="3" id="summary" name="summary"></textarea>
      </div>
    </div>   
    </form>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    <button id="save-edit" type="button" class="btn btn-primary" onclick="createInstance();">保存</button>
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
				$("#booktitle").blur();
				$("#price").val(data.price);
				$("#price").blur();
				$("#authors").val(data.authors);
				$("#authors").blur();
				$("#new_price").val(data.new_price);
				$("#new_price").blur();
				$("#paper_price").val(data.paper_price);
				$("#paper_price").blur();
				/* $("#tags").val(data.tags); */
				$("#booksummary").val(data.summary);
				/* $("#password").val(data.passwordHash);
				$("#confirmPwd").val(data.passwordHash);
				$("#phone").val(data.phone); */
				$("#uri").val(data.uri);
				$('#myModal').modal('show')
			}
		});
	}

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
				$("#booksGrid").simplePagingGrid("refresh");
			}
		};

		$("#editform").ajaxSubmit(options);
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
				$("#booksGrid").simplePagingGrid("refresh");
			}
		});
	}

	//编辑Table数据
	$("#booksGrid")
			.simplePagingGrid(
					{
						dataUrl : '/books',
						columnNames : [ "#", "书名", "作者", "价格", "修改", "删除" ],
						columnKeys : [ "rowId", "title", "authors", "price",
								"修改", "删除" ],
						columnWidths : [ "5%", "30%", "10%", "10%", "12%",
								"13%" ],
						sortable : [ false, true, false, true, false, false,
								false ],
						initialSortColumn : "title",
						cellTemplates : [
								null,
								null,
								null,
								null,
								"<button id='{{uri}}' onclick='editItem(this)' class='btn'>修改</button>",
								"<button id='{{uri}}' onclick='deleteItem(this)' class='btn'>删除</button>" ]

					});
</script>
