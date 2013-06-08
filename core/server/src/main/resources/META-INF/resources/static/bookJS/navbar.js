//所有模块都通过 define 来定义
define(function(require, exports, module) {

  // 通过 require 引入依赖
  var $ = require('jquery'); 
	//提取URL中的模块的根路径
	function subStrPath(str) {
		var lastIndex = str.lastIndexOf("/");
		return lastIndex == 0 ? str : str.substring(0, lastIndex);
	}
	//设置navbar选中高亮显示
	$(function() {
		var pathname = location.pathname;
		if (pathname.length <= 0) {
			return;
		}
		var split = pathname.split("/");
		$("ul.nav>li>a").each(function() {
			var attrStr = $(this).attr("href");
			var $parent = $(this).parent();
			var pathstr = subStrPath(pathname);
			if(pathstr == pathname && attrStr == "index.html") {
				$parent.addClass("active");
				return;
			}
			if (subStrPath(attrStr) == pathstr || attrStr == (split[2])) {
				$parent.addClass("active");
			}
		});
	});

});