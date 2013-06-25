<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <div class="row-fluid" id="books">    
      <c:forEach var="i" items="${it.items}" varStatus="status" >
      <c:if test="${status.count%4==1}"><ul class="thumbnails"></c:if>      
        <li class="span3" book_id="${i.id}">
          <div class="thumbnail">
            <a href="${i.uri}"><img src="${i.coverUrl}"/></a>
            <div class="caption">
            <a class="title" href="${ i.uri }" hidefocus="hidefocus">${ i.title }</a>
            <p class="author">
              <span>retech</span>
            </p>           
            <div>
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
            </div></div></li>
            <c:if test="${status.count%4==0}"></ul></c:if>            
      </c:forEach>
</div>
<jsp:include page="template.html"></jsp:include>
 <script>
      seajs.use('/static/bookJS/booklist.js');      
</script>


