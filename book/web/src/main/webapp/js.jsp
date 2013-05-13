<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	//提取URL中的模块的根路径
	function subStrPath(str) {
		return str.lastIndexOf("/") == 0 ? str : str.substring(0, str.lastIndexOf("/"));
	}
	//设置navbar选中高亮显示
	$(function() {
		var pathname = location.pathname;
		if (pathname.length <= 0) {
			return;
		}
		if(pathname.lastIndexOf("/") == 0) {
			window.location.href = pathname + "/index.html";
			return;
		}
		var split = pathname.split("/");
		$("ul.nav>li>a").each(function() {
			if (subStrPath($(this).attr("href")) == subStrPath(pathname) || $(this).attr("href") == (split[2])) {
				$(this).parent().addClass("active");
			}
		});
	});
</script>