 	// 所有模块都通过 define 来定义
	define(function(require, exports, module) {

	  // 通过 require 引入依赖
	  var $ = require('jquery');	
	  $().ready(function() {
			$().acknowledgeinput();		
		});
		function createInstance(){
			 var options = {
					 	url:'./',
					 	contentType:"multipart/form-data",
					 	type:'post',
					 	data: $('#newBook').formSerialize(),
			            success : function(json) { 		            	
							window.location.href = 'books.html';
			             },
			             complete : function(data, textStatus) {
				 				if (textStatus == "success") {
				 					window.location.href = 'books.html';
				 				} else {
				 					window.location.href = 'create.html';
				 				}

				 			}
			        };		
				$("#newBook").ajaxSubmit(options);
				return false;
		}
		
		$('#newBook').myValidate("save-book", createInstance);
	});