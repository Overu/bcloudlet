 	// 所有模块都通过 define 来定义
	define(function(require, exports, module) {
		 
	  // 通过 require 引入依赖
	  var $ = require('jquery');
	  
	  var acknowledgeinput = require('../bootstrap/validation/bootstrap-acknowledgeinput.js');
	  var validation = require('../bootstrap/validation/bootstrap-validation-form.js');
	  var bootbox = require('../bootstrap/js/bootbox.min.js');
	  var form = require('../jquery/jquery.form.js');
	  var multiselect = require('../bootstrap/js/bootstrap-multiselect.js');
	  var simplePagingGrid = require('../simpleGrid/js/simplePagingGrid-0.5.0.2.js');
	  var handlebars = require('../simpleGrid/js/handlebars-1.0.rc.1.js');
	  var bootstrap = require('../bootstrap/js/bootstrap.min.js');
	  
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