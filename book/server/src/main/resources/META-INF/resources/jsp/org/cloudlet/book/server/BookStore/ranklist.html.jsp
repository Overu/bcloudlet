<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="i" items="${it.books.items}" varStatus="status" begin="0" end="9">
  <c:choose>
    <c:when test="${status.index ==  0}">
      <li class="itm1 first"><em class="hot j-png">${ status.count }</em> <a href="${ i.uri }" title="${ i.title }"
        hidefocus="hidefocus"> <img src="${ i.coverUrl }" alt="${ i.title }" />
          <div class="info">
            <p class="title">${ i.title }</p>
            <p class="author">
              <span>retech</span>
            </p>
          </div>
      </a></li>
    </c:when>
    <c:otherwise>
      <li class="itm<c:if test="${status.index > 0 && status.index < 3}"> itm2</c:if>"><em>${ status.count }</em><a href="${ i.uri }"
        title="${ i.title }" hidefocus="hidefocus">${ i.title }</a></li>
    </c:otherwise>
  </c:choose>
</c:forEach>

