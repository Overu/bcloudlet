<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<div class="row">
    <div class="span3 bs-docs-sidebar">
      <ul class="nav nav-list bs-docs-sidenav">
        <div>
          <h3>图书分类</h3>
        </div>
        <c:forEach var="i" items="${it.tags.items}">          
            <li><a href="/books/t/${i.value}" hidefocus="hidefocus"><span>${i.value}</span></a></li>         
        </c:forEach>
      </ul>
    </div>
<div class="span9">
  <section>   
    <div class="container-fluid">
      <div>
        <section id="thumbnails">
          <div>
            <h5>全部图书>${it.tag.value}</h5>
          </div>
          <div class="row-fluid">
            <ul class="thumbnails">
              <c:forEach var="i" items="${it.items}" varStatus="status">
              <c:if test="${status.count%4==1}"><ul class="thumbnails"></c:if>   
                <li class="span3" book_id="${i.id}">
                  <div class="thumbnail">
                  <a href="${i.uri}"><img src="${i.coverUrl}"/></a>                    
                     <div class="caption">
                    <a href="${ i.uri }" style="height: 50px;">${ i.title }</a>
                    <p class="author">
                      <span>retech</span>
                    </p>
                    <div class="price0 price">
                      <c:choose>
                        <c:when test="${i.new_price !=  0}">
                          <span>¥ ${ i.new_price }</span>
                          <del>¥ ${ i.price }</del>
                        </c:when>
                        <c:when test="${i.price !=  0}">
                          <span>¥ ${ i.price }</span>
                        </c:when>
                        <c:otherwise>
                          <span class="price1">免费</span>
                        </c:otherwise>
                      </c:choose>
                    </div>
                    </div></li>
                    <c:if test="${status.count%4==0}"></ul></c:if> 
              </c:forEach>
            </ul>
      </div>
     </section>
</div>
 </div>
<w:include page="footer.jsp"></w:include>
