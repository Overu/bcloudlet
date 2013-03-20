<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<title>畅销榜 - 睿泰书城</title>
<jsp:include page="../meta.jsp"></jsp:include>
</head>
<body id="page-duokan-com" itemscope itemtype="http://schema.org/WebPage">
  <noscript>请使用支持脚本的浏览器！</noscript>
  <div class="g-doc">
    <jsp:include page="../header.jsp"></jsp:include>
    <div class="g-bd">
      <div class="w-tabox w-tabox-1">
        <jsp:include page="../rank.jsp"></jsp:include>
        <!--畅销榜-->
        <div id='book-rank'>
          <div class="content">
            <div class="w-booklist w-booklist-1">
              <jsp:include page="../Books/list.jsp"></jsp:include>
            </div>
          </div>
          <div class="w-load">
            <div class="spin"></div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="../sider.jsp"></jsp:include>
    <jsp:include page="../footer.jsp"></jsp:include>
  </div>
  <jsp:include page="../Book/template.html"></jsp:include>
  <script>
			seajs.use([ 'duokan/store/1/page/book',
					'duokan/store/1/page/patched' ])
		</script>
  <script>
			seajs.use('duokan/store/1/page/act_rank_sale');
		</script>
</body>
</html>
