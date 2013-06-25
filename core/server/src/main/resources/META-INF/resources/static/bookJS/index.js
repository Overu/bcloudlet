// 所有模块都通过 define 来定义
	define(function(require, exports, module) {
		 
	  // 通过 require 引入依赖
	  var $ = require('jquery');
	  
	  var juicer2 = require('../juicer/juicer-min.js');
	  
	  $().ready(function() {			
			$.getJSON('/books/r/newsale?start=0&limit=8',function(data){
				var tpl = document.getElementById('jst-more').innerHTML;
				var html = juicer(tpl, data);
				var newsale = $("#newsale");
				newsale.append(html);
			});
			$.getJSON('/books/r/recommendation?start=0&limit=8',function(data){
				var tpl = document.getElementById('jst-more').innerHTML;
				var html = juicer(tpl, data);
				var recommendation = $("#recommendation");
				recommendation.append(html);
			});
			$.getJSON('/books/r/latest?start=0&limit=8',function(data){
				var tpl = document.getElementById('jst-more').innerHTML;
				var html = juicer(tpl, data);
				var latest = $("#latest");
				latest.append(html);
			});
		});
	});