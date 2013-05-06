<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="i" items="${it.books.items}" varStatus="status" begin="0" end="9">
  <li class="itm" book_id="${ i.id }"><a href="${ i.uri }" class="cover" hidefocus="hidefocus">
      <div class="wrap">
        <img src="${ i.coverUrl }" alt="${ i.title }" ondragstart="return false;" oncontextmenu="return false;" onload="onLoadImg(this)"
          style="display: none" />
      </div>
  </a> <a class="title" href="${ i.uri }" hidefocus="hidefocus">${ i.title }</a>
  <p class="author"><span>retech</span></p>
    <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">
      <ul class="five">
        <li class='red'></li>
        <li class='red'></li>
        <li class='red'></li>
        <li class='red'></li>
        <li class='halfive'></li>
      </ul>
      <span style="display: none;" itemprop="ratingValue">8.6</span><span class="num">( <span itemprop="reviewCount">83</span> )
      </span>
    </div>
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

