// 所有模块都通过 define 来定义
	define(function(require, exports, module) {
		 
	  // 通过 require 引入依赖
	  var $ = require('jquery');
	  
	  var juicer2 = require('../juicer/juicer-min.js');
	  
	  var pathname = window.location.pathname;  
	 
	  $().ready(function() {
		  window.onscroll=function(){
		        if($(document).scrollTop()>=$(document).height()-$(window).height()){
		        	 var start = $("#books li").size();
		       	  
		       	  var url = pathname+"?start="+(start+1) + "&limit=8";
		        	$.getJSON(url,function(data){
						var tpl = document.getElementById('jst-more').innerHTML;
						var html = juicer(tpl, data);
						$("#books").append(html);
					});
		        }
		    } 
		});
	});