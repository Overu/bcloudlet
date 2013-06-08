
// 所有模块都通过 define 来定义
	define(function(require, exports, module) {

	  // 通过 require 引入依赖
	  var $ = require('jquery');
	  //响应查询按钮
	function searchData() {
		var searchname = $("#search-name").val();
		var uri = searchname == "" ? "/books" : "/books" + "?search=title\|"
				+ searchname;
		$("#booksGrid").simplePagingGrid("refresh", uri);
	}

	//初始化编辑form页面
	var editFormini = function(){
		
	$("#booktitle").val(null);
		$("#price").val(null);
		$("#authors").val(null);
		$("#new_price").val(null);
		$("#paper_price").val(null);
		$('option', $('#tags')).each(function(element) {
			$(this).removeAttr('selected').prop('selected', false);
		});
		$('#tags').multiselect('refresh');
		$("#booksummary").val(null);
		$("#uri").val(null);
	};

	//后台调用输入框的blur事件	
	var editFormBlur = function() {
		$("#booktitle").blur();
		$("#price").blur();
		$("#authors").blur();
		$("#new_price").blur();
		$("#paper_price").blur();
		$('#tags').change();		
	};

	//响应修改按钮
	//查询数据并填充表单数据
	function editItem(item) {
		$.ajax({
			type : 'get',
			url : item.id,
			dataType : 'json',
			success : function(data) {
				editFormini();
				$("#booktitle").val(data.title);
				$("#price").val(data.price);
				$("#authors").val(data.authors);
				$("#new_price").val(data.new_price);
				$("#paper_price").val(data.paper_price);
				for ( var i = 0; i < data.tags.length; i++) {
					var item = data.tags[i];
					var tag = item.value;
					$('#tags').multiselect('select', tag);
				}
				$("#booksummary").val(data.summary);
				$("#uri").val(data.uri);
				editFormBlur();
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
		return false;
	}
	$('#editform').myValidate("save-edit", createInstance); 
	
	//响应删除按钮
	function deleteItem(item) {
		
		bootbox.confirm("是否确定删除此数据?", "取消", "确定", function(result){
			if(result){
				$.ajax({
					type : 'delete',
					url : item.id,
					success : function() {
						//刷新table
						$("#booksGrid").simplePagingGrid("refresh");
					}
				});
			};			
		});	
	}

	//编辑Table数据
	$("#booksGrid")
			.simplePagingGrid(
					{
						dataUrl : '/books',
						columnNames : [ "#", "书名", "作者", "最新价格", "修改", "删除" ],
						columnKeys : [ "rowId", "title", "authors", "new_price",
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
	});