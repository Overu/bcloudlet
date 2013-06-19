<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <div class="row-fluid">
    <ul class="thumbnails">
      <c:forEach var="i" items="${it.items}" varStatus="status">
        <li class="span3" book_id="${i.id}"><a href="${i.uri}"></a>
          <div class="thumbnail">
            <img src="${i.coverUrl}" /> <a class="title" href="${ i.uri }" hidefocus="hidefocus">${ i.title }</a>
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
            </div></li>
      </c:forEach>
    </ul>


</div>


