<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul id="paybook">
  <%
    int count = 0;
  %>
  <c:forEach var="i" items="${it.items}">
    <%
      count++;
    %>
    <li class="itm" book_id="4fdcac0e5e3611e2956f00163e0123ac"><a href="/%E8%BF%8E%E7%94%B7%E8%80%8C%E4%B8%8A/b/18437" class="cover"
      hidefocus="hidefocus">
        <div class="wrap">
          <img src="${i.coverUrl}" ondragstart="return false;" oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

          <div class="desc">
            <p>${i.summary}</p>
          </div>
        </div>
    </a> <a class="title" href="/%E8%BF%8E%E7%94%B7%E8%80%8C%E4%B8%8A/b/18437" hidefocus="hidefocus"> <em class="num_bc hot"><%=count%>.</em>${i.title}
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
