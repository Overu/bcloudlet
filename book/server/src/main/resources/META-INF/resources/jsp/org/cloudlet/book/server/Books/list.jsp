<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul id="paybook">
  <c:forEach var="i" items="${it.items}" varStatus="status">
    <li class="itm" book_id="4fdcac0e5e3611e2956f00163e0123ac"><a href="${ i.uri }" class="cover"
      hidefocus="hidefocus">
        <div class="wrap">
          <img src="${i.coverUrl}" ondragstart="return false;" oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

          <div class="desc">
            <p>${i.summary}</p>
          </div>
        </div>
    </a> <a class="title" href="${ i.uri }" hidefocus="hidefocus"> <em class="num_bc hot">${status.count}.</em>${i.title}
    </a>
      <p class="author">
        <span>${i.authors}</span><span>薛好大</span>
      </p>

      <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">
        <ul class="five">
          <li class='red'></li>
          <li class='red'></li>
          <li class='red'></li>
          <li class='red'></li>
          <li></li>
        </ul>
        <span style="display: none;" itemprop="ratingValue">8.19</span> <span class="num">( <span itemprop="reviewCount">238</span> )
        </span>
      </div>
      <div class="price0 price">
        <span>¥ ${i.new_price}</span>
        <del>¥ ${i.price}</del>
      </div></li>
  </c:forEach>
</ul>

