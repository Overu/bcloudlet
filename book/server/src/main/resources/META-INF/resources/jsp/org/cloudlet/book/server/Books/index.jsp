<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>  
   <!-- Docs nav    ================================================== -->
  <div class="row">
    <div class="span3 bs-docs-sidebar">      
      <ul class="nav nav-list bs-docs-sidenav">
        <div >
          <h4 >图书分类</h4>
        </div>        
        <c:forEach var="i" items="${it.tags.items}">          
            <li><a href="/books/t/${i.value}" hidefocus="hidefocus"><span>${i.value}</span></a></li>         
        </c:forEach>
      </ul>
    </div>
    <div class="span9">
      <section>
        <div id="brand" class="w-slider" unselectable="on" onselectstart="return false;">
         <ul id="slides" class="slides">
             <li><a
              href="/%E5%B8%9D%E5%9B%BD%E6%9C%80%E5%90%8E%E7%9A%84%E8%8D%A3%E8%80%80%EF%BC%88%E5%85%A8%E4%BA%8C%E5%86%8C%EF%BC%89/b/5f30dabe70d711e2808800163e0123ac"><img
                 src='http://i.duokan.com/i/2013/03/ed2183f1ca584141.jpg' alt="帝国最后的荣耀（全二册）" hidefocus="hidefocus" onload='onLoadImg(this)'
                style="display: none" /></a></li>
        </div>
        <div class="container-fluid">
          <div>
            <section id="thumbnails">
              <div class="w-ttl">
                <h4>推荐阅读</h4>
                <a href="/books/r/recommendation" class="w-more" hidefocus="hidefocus">更多</a>
              </div>
              <jsp:include page="booklist.jsp"></jsp:include>
            </section>
             <section id="thumbnails">
              <div >
                <h4>最新上架</h4>
                <a href="/books/r/recommendation" class="w-more" hidefocus="hidefocus">更多</a>
              </div>
              <jsp:include page="booklist.jsp"></jsp:include>
            </section>
             <section id="thumbnails">
              <div >
                <h4>最新特价</h4>
                <a href="/books/r/recommendation" class="w-more" hidefocus="hidefocus">更多</a>
              </div>
              <jsp:include page="booklist.jsp"></jsp:include>
            </section>
          </div>
        </div>
      </section>
    </div>
    </div>
  <w:include page="footer.jsp"></w:include>