 	// 所有模块都通过 define 来定义
	define(function(require, exports, module) {

	  // 通过 require 引入依赖
	  var $ = require('jquery');	
	  $().ready(function() {
			$().acknowledgeinput();		
		});

		$('#createform').myValidate("save_From", createInstance); 
				
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
		}
	});