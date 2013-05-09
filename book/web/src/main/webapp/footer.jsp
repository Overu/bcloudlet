<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer class="g-ft">
  <div class="m-cprt">
    Copyright&copy;Retechcorp.com <a href="http://www.miibeian.gov.cn/" target="_blank" hidefocus="hidefocus">京ICP备XXX号</a> <a
      href="http://www.gapp.gov.cn/" target="_blank" hidefocus="hidefocus">电复证字第XXX号</a>
  </div>
</footer>
<script src="/static/jquery/jquery.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/bootstrap/validation/bootstrap-validation.js"></script>

<script type="text/javascript">
	//提取URL中的模块的根路径
	function subStrPath(str) {
		return str.substring(0, str.lastIndexOf("/"));
	}

	//设置navbar选中高亮显示
	$(function() {
		var pathname = location.pathname;
		if (pathname.length <= 0) {
			return;
		}
		$("ul.nav>li>a").each(function() {
			if (subStrPath(pathname) == subStrPath($(this).attr("href"))) {
				$(this).parent().addClass("active");
			}
		});
	});
</script>
