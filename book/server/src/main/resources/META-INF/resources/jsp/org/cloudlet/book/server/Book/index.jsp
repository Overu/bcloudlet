<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../meta.jsp"></jsp:include>
<title>图书详情</title>
<link type="text/css" rel="stylesheet" href="/static/lib/css/other.css">
<link type="text/css" rel="stylesheet" href="/static/lib/css/book.css">
<link type="text/css" rel="stylesheet" href="/static/lib/css/bdsstyle.css">
<style type="text/css">
</style>
</head>
<body>
  <div class="g-doc">
    <jsp:include page="../header.jsp"></jsp:include>

    <div class="g-bd1">

      <div class="w-crumbs">
        <c:forEach var="i" items="${it.tags}">
          <span>当前位置：</span>
          <a href="/" hidefocus="hidefocus">首页</a>
          <span class="sep">&gt;</span>
          <a href="/books/t/${ i.value }" id="dkclassify" hidefocus="hidefocus"> ${ i.value }</a>
          <span class="sep">&gt;</span>
          <span id="dkcurrent">${it.title}</span>
        </c:forEach>
      </div>

      <div class="g-mn">
        <div class="g-mnc">
          <section class="m-bookintro">
          <h1>${it.title}</h1>
          <article class="intro" id="book-content" hidefocus="hidefocus" style="height: 220px; overflow: hidden;">
          <p>${it.summary}</p>
          </article>
          <div class="more showall-txt">
            <a class="w-more-open" href="javascript:void(0);" hidefocus="hidefocus">显示全部</a>
          </div>
          </section>
        </div>
      </div>

      <div class="g-sd">
        <div class="g-sd">
          <div class="m-bookdetail" itemscope="" itemtype="http://schema.org/Book">
            <div class="cover" id="cover-img">
              <img itemprop="image" src="${it.coverUrl}" alt="${it.title}" ondragstart="return false;" oncontextmenu="return false;"
                onload="onLoadImg(this);" style="display: block;">
            </div>
            <div class="desc">
              <div class="act" id="loadbox">
                <div class="price">
                  <em>¥ <span>${it.new_price}</span></em>
                  <del>
                    ¥ <span itemprop="price">${it.price}</span>
                  </del>
                </div>
                <span id="paybootn" class="w-btn w-btn1" condition="needtobuy">购买</span> <span class="w-btn" id="mygift">赠送</span>
                <div class="loadnext">
                  <div class="spin"></div>
                </div>
              </div>
              <div class="cnt">
                <div class="w-starfive" itemprop="aggregateRating" itemscope="" itemtype="http://schema.org/AggregateRating">
                  <ul class="five">
                    <li class="red"></li>
                    <li class="red"></li>
                    <li class="red"></li>
                    <li class="red"></li>
                    <li></li>
                  </ul>
                  <span style="display: none;" itemprop="ratingValue">7.8</span> <span class="num">( <span itemprop="reviewCount">10</span>
                    )
                  </span>
                </div>
                <table>
                  <tbody>
                    <tr id="writer-name">
                      <td class="col0">作者：</td>
                      <td class="author"><span itemprop="author">${it.authors}</span></td>
                    </tr>
                    <tr>
                      <td class="col0">版权：</td>
                      <td class="published" itemprop="copyrightHolder">retech</td>
                    </tr>
                    <tr id="publishedtime">
                      <td class="col0">出版：</td>
                      <td itemprop="datePublished">2011-01-01</td>
                    </tr>
                    <tr>
                      <td class="col0">更新：</td>
                      <td class="updated"><span itemprop="dateModified">2013-01-10</span></td>
                    </tr>
                    <tr>
                      <td class="col0">大小：</td>
                      <td class="size">3.50MB</td>
                    </tr>
                    <tr>
                      <td class="col0 isbn-name">书号：</td>
                      <td class="isbn" itemprop="isbn">978-7-8020-1852-5</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <%-- <h1>${it.title}</h1>
        <p>作者：${it.authors}</p>
        <p>${it.summary}</p>
        <h2>分类</h2>
        <ul>
          <c:forEach var="i" items="${it.tags}">
            <li><a href="${i.uri}">${i.value}</a>
          </c:forEach>
        </ul>
        <h2>点评</h2>
        <ul>
          <c:forEach var="i" items="${it.comments.items}">
            <li>${i.content}</a>
          </c:forEach>
        </ul> --%>
      </div>
    </div>

    <jsp:include page="../footer.jsp"></jsp:include>

  </div>
  <script type="text/javascript">
			seajs.use([ 'duokan/store/1/page/book',
					'duokan/store/1/page/patched' ]);
		</script>
</body>
</html>
