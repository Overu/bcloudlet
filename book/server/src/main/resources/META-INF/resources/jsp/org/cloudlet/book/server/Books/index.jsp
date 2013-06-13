<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<section>
  <div class="container-fluid">
    <div class="page-header">
      <h1>推荐阅读</h1>
    </div>
    <div>
      <jsp:include page="booklist.jsp"></jsp:include>
    </div>
  </div>
</section>
<w:include page="footer.jsp"></w:include>