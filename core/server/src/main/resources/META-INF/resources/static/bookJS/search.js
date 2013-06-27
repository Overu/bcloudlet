define(function(require, exports, module) {

	// 通过 require 引入依赖
	var $ = require('jquery');
	$("#search-text").bind("keydown", function() {
		var searchtext = $("#search-text").val();
		if (searchtext != null && searchtext != "") {
			$("#searchbotton").show();
		} else {
			$("#searchbotton").hide();
		}
	}).bind("keyup", function() {
		var searchtext = $("#search-text").val();
		if (searchtext != null && searchtext != "") {
			$("#searchbotton").show();
		} else {
			$("#searchbotton").hide();
		}
	});
	$("#searchbotton").click(function() {
		if($("#search-text").val()==null||$("#search-text").val()==""){
			$("#searchbotton").hide();
			return;
		}
		window.location.href = "/books/search/" + $("#search-text").val();
	});
});