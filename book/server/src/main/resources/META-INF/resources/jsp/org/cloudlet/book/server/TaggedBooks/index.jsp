<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<title>${it.tag.value}- 多看精品书城</title>
<jsp:include page="../meta.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="/static/lib/css/category.css?201303081413" />
</head>
<body id="page-duokan-com" itemscope itemtype="http://schema.org/WebPage">
  <noscript>请使用支持脚本的浏览器！</noscript>
  <div class="g-doc">
    <jsp:include page="../header.jsp"></jsp:include>
    <div class="g-bd">
      <div class="g-mn">
        <div class="g-mnc">
          <div class="m-category-list">
            <div class="w-box2">
              <div class="w-ttl">
                <h3 id='category_name'>${it.tag.value}</h3>
                <div class="w-tab">
                  <ul>
                    <li class="crt"><a href="/%E4%BC%A0%E8%AE%B0/c/13-1" hidefocus="hidefocus"> 热门畅销 </a></li>
                    <li class=""><a href="/%E4%BC%A0%E8%AE%B0/c/13-1-1" hidefocus="hidefocus"> 最新更新 </a></li>
                  </ul>
                </div>
                <div class="w-page2">
                  <em><span id='one-page'>1</span>/<span id='total-page'>12</span></em> <a class="pre init" href="javascript:void(0);"
                    hidefocus="hidefocus">上一页</a> <a class="next " href="/%E4%BC%A0%E8%AE%B0/c/13-2" hidefocus="hidefocus">下一页</a>
                </div>
              </div>
              <div class="w-booklist w-booklist-2">
                <ul id="categorylist">
                  <c:forEach var="i" items="${it.items}">
                    <li class="itm">
                      <div class="cover">
                        <a href="/%E6%A2%81%E5%90%AF%E8%B6%85%E4%BC%A0%EF%BC%88%E5%85%A8%E4%BA%8C%E5%86%8C%EF%BC%89/b/13357"
                          hidefocus="hidefocus"> <img src="${i.coverUrl}" alt="${i.title}" onload="onLoadImg(this)"
                          style="display: none" />
                        </a>
                      </div>
                      <div class="info">
                        <a href="/%E6%A2%81%E5%90%AF%E8%B6%85%E4%BC%A0%EF%BC%88%E5%85%A8%E4%BA%8C%E5%86%8C%EF%BC%89/b/13357"
                          hidefocus="hidefocus">
                          <h4>${i.title}</h4>
                        </a>
                        <p class="author">
                          <span>${i.authors ? i.authors : "retech"}</span>
                        </p>
                        <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">
                          <ul class="five">
                            <li class='red'></li>
                            <li class='red'></li>
                            <li class='red'></li>
                            <li class='red'></li>
                            <li class='halfive'></li>
                          </ul>
                          <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span
                            itemprop="reviewCount">19</span> )
                          </span>
                        </div>
                        <div class="price0 price">
                          <span>¥ ${i.new_price}</span>
                          <del>¥ ${i.price}</del>
                        </div>
                        <div class="desc">
                          <p>${i.summary}</p>
                        </div>
                      </div>
                    </li>
                  </c:forEach>

                </ul>
              </div>
              <!--w-page-->

              <div class="w-page">
                 <div class="wrap">
                <a href="javascript:void(0);" class="pre init" hidefocus="hidefocus">上一页</a>
                  <ul id="bookpage-a">
 <!-- 
                    <li class="crt"><a href="javascript:void(0);" hidefocus="hidefocus">1</a></li>

                    <li class=""><a href="/%E4%BC%A0%E8%AE%B0/c/13-2" hidefocus="hidefocus">2</a></li>

                    <li class=""><a href="/%E4%BC%A0%E8%AE%B0/c/13-3" hidefocus="hidefocus">3</a></li>

                    <li class="ellipsis">...</li>

                    <li class=""><a href="/%E4%BC%A0%E8%AE%B0/c/13-12" hidefocus="hidefocus">12</a></li>
-->
                  </ul>
                  <a href="/%E4%BC%A0%E8%AE%B0/c/13-2" class="next " hidefocus="hidefocus">下一页</a>
                </div> 
              </div>

              <!--w-page-->
            </div>
          </div>
        </div>
      </div>
      <!--导航-->
      <div class="g-sd">
        <div class="m-directory">
          <div class="w-box">
            <div class="w-ttl">
              <h3>全部图书分类</h3>
            </div>
            <div class="cnt">
              <ul class="w-txtlist2">
                <c:forEach var="i" items="${it.root.tags.items}">
                  <li><a href="/books/t/${i.value}" hidefocus="hidefocus"><span>${i.value}</span><em class="num">${i.weight}</em></a></li>
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!--导航 end-->
    </div>
    <jsp:include page="../sider.jsp"></jsp:include>
    <jsp:include page="../footer.jsp"></jsp:include>
  </div>
  <script>
			seajs.use([ 'duokan/store/1/page/book',
					'duokan/store/1/page/patched', 'duokan/store/1/page/test' ]);
	</script>
</body>
</html>
