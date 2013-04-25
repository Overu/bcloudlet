<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>图书库</title>
<jsp:include page="../../../../../admin/meta.jsp"></jsp:include>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">
  <jsp:include page="../../../../../admin/navbar.jsp"></jsp:include>

  <!-- Subhead
================================================== -->
  <header class="jumbotron subhead" id="overview">
    <div class="container">
      <h1>图书</h1>
      <p class="lead">Dozens of reusable components built to provide navigation, alerts, popovers, and more.</p>
    </div>
  </header>


  <div class="container">

    <!-- Docs nav
    ================================================== -->
    <div class="row">
      <div class="span3 bs-docs-sidebar">
        <ul class="nav nav-list bs-docs-sidenav">
          <li><a href="#dropdowns"><i class="icon-chevron-right"></i> 上传新书</a></li>
          <li><a href="#buttonGroups"><i class="icon-chevron-right"></i> 我的图书</a></li>
          <li><a href="#progress"><i class="icon-chevron-right"></i> 已购图书</a></li>
          <li><a href="#media"><i class="icon-chevron-right"></i> 我的收藏</a></li>
          <li><a href="#misc"><i class="icon-chevron-right"></i> 推荐新书</a></li>
        </ul>
      </div>
      <div class="span9">
        <section id="dropdowns">
          <div class="page-header">
            <h1>我的图书</h1>
          </div>
          <table class="table table-striped">
            <tbody>
              <tr>
                <th>#</th>
                <th>书名</th>
                <th>作者</th>
                <th>出版
                </td>
                <th>更新日期</th>
              </tr>
              <c:forEach var="i" items="${it.items}" varStatus="status">
                <tr>
                  <td>${status.count}</td>
                  <td>${i.title}</td>
                  <td>${i.authors}</td>
                  <td>出版</td>
                  <td>更新日期</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>

      </div>
    </div>

  </div>



  <!-- Footer
    ================================================== -->
  <footer class="footer">
    <div class="container">
      <p>
        Designed and built with all the love in the world by <a href="http://twitter.com/mdo" target="_blank">@mdo</a> and <a
          href="http://twitter.com/fat" target="_blank">@fat</a>.
      </p>
      <p>
        Code licensed under <a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache License v2.0</a>, documentation
        under <a href="http://creativecommons.org/licenses/by/3.0/">CC BY 3.0</a>.
      </p>
      <p>
        <a href="http://glyphicons.com">Glyphicons Free</a> licensed under <a href="http://creativecommons.org/licenses/by/3.0/">CC BY
          3.0</a>.
      </p>
      <ul class="footer-links">
        <li><a href="http://blog.getbootstrap.com">Blog</a></li>
        <li class="muted">&middot;</li>
        <li><a href="https://github.com/twitter/bootstrap/issues?state=open">Issues</a></li>
        <li class="muted">&middot;</li>
        <li><a href="https://github.com/twitter/bootstrap/blob/master/CHANGELOG.md">Changelog</a></li>
      </ul>
    </div>
  </footer>



  <!-- Le javascript
    ================================================== -->
  <!-- Placed at the end of the document so the pages load faster -->
  <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
  <script src="assets/js/jquery.js"></script>
  <script src="assets/js/bootstrap-transition.js"></script>
  <script src="assets/js/bootstrap-alert.js"></script>
  <script src="assets/js/bootstrap-modal.js"></script>
  <script src="assets/js/bootstrap-dropdown.js"></script>
  <script src="assets/js/bootstrap-scrollspy.js"></script>
  <script src="assets/js/bootstrap-tab.js"></script>
  <script src="assets/js/bootstrap-tooltip.js"></script>
  <script src="assets/js/bootstrap-popover.js"></script>
  <script src="assets/js/bootstrap-button.js"></script>
  <script src="assets/js/bootstrap-collapse.js"></script>
  <script src="assets/js/bootstrap-carousel.js"></script>
  <script src="assets/js/bootstrap-typeahead.js"></script>
  <script src="assets/js/bootstrap-affix.js"></script>

  <script src="assets/js/holder/holder.js"></script>
  <script src="assets/js/google-code-prettify/prettify.js"></script>

  <script src="assets/js/application.js"></script>


  <!-- Analytics
    ================================================== -->
  <script>
			var _gauges = _gauges || [];
			(function() {
				var t = document.createElement('script');
				t.type = 'text/javascript';
				t.async = true;
				t.id = 'gauges-tracker';
				t.setAttribute('data-site-id', '4f0dc9fef5a1f55508000013');
				t.src = '//secure.gaug.es/track.js';
				var s = document.getElementsByTagName('script')[0];
				s.parentNode.insertBefore(t, s);
			})();
		</script>

</body>
</html>
