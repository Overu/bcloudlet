<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
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
</script>