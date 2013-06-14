<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<div class="container">
  <!-- Docs nav    ================================================== -->
  <div class="row">
    <div class="span3 bs-docs-sidebar">
      <!--导航-->
      <div class="g-sd">
        <div class="m-directory">
          <div class="w-box">
            <div class="w-ttl">
              <h3>全部图书分类</h3>
            </div>
            <div class="cnt">
              <ul class="w-txtlist2 nav nav-list">
                <c:forEach var="i" items="${it.items}">
                <c:forEach var="h" items="${i.tags}">
                  <li><a href="/books/t/${h.value}" hidefocus="hidefocus"><span>${h.value}</span><em class="num">${h.weight}</em></a></li>
                </c:forEach>
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!--导航 end-->
    </div>
    <div class="span9">
      <section>
      <div id="brand" class="w-slider" unselectable="on" onselectstart="return false;">
      </div>
        <div class="container-fluid">
          <div class="page-header">
            <h1>推荐阅读</h1>
          </div>
          <div>
            <jsp:include page="booklist.jsp"></jsp:include>
          </div>
        </div>
      </section>
    </div>
    <w:include page="footer.jsp"></w:include>