<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<div>
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
</div>
<div class="row">
  <div class="span3">
    <ul class="thumbnails">
      <li class="span3">
        <div class="thumbnail">
          <img src="${it.coverUrl}" alt="${it.title}">
          <h4>${it.title}</h4>
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
                <span>免费</span>
              </c:otherwise>
            </c:choose>
          </div>
          <span id="paybootn" class="btn btn-primary"> 购买</span> <span class="btn" id="mygift">赠送</span>
          <div class="cnt">

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

      </li>
    </ul>

  </div>
  <div class="span9">
    <section>
      <h1>${it.title}</h1>
      <article id="book-content" hidefocus="hidefocus" style="height: 220px; overflow: hidden;">
        <p>${it.summary}</p>
      </article>     
    </section>
    <section>
     
        <div class="navbar">
          <div class="navbar-inner">
            <a class="brand" href="#">用户评论( <em>${ it.comments.count }</em> 条 )
            </a>
            <ul class="nav">
              <li><a href="javascript:void(0);" hidefocus="hidefocus">最热</a></li>
              <li><a href="javascript:void(0);" hidefocus="hidefocus">最新</a></li>              
            </ul>
          </div>
        </div>
      
      <c:forEach var="i" items="${it.comments.items}">
        <li comments_id="${ i.id }"><h4>
            <a href="">${ i.title }</a>
          </h4>
          <div>
            <p>${ i.content }</p>
          </div>
          <div class="">
            <span class="">2012-08-21</span>
            <div class="">
              <a class="">有用&nbsp;(&nbsp;<span class="">14</span>&nbsp;)
                <div class=" " style="display: none"></div></a><span class="sep">-</span><a href="" class="">回复&nbsp;(&nbsp;0&nbsp;)</a><span
                class=""></span>
            </div>
          </div>
          <div class="" style="display: none">
            <div class="form">
              <textarea style="minHeight: 20px" name="write_reply" class=""></textarea>
              <input type="button" class="" value="回复">
            </div>
            <div class="" style="display: none">
              登录后，可回复评论。
              <div class="" title="关闭"></div>
            </div>
            <div class=" "></div>
          </div></li>
      </c:forEach>
      </ul>
      <ul id="newest"></ul>
      <div class="no_comment" style="display: none">暂无评论~</div>
      <div class="more residue">
        <a href="" class="" id="commentlist-more"
          hidefocus="hidefocus">后面还有 <em id="comm_total">${ it.comments.count }</em> 条评论，查看全部
        </a>
      </div>
    </section>
  </div>
</div>


<w:include page="footer.jsp"></w:include>
