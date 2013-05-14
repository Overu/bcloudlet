<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="mybooks">
  <div class="page-header">
    <h1>我的图书</h1>
  </div>
  <table class="table table-striped table-hover">
    <tbody>
      <tr>
        <th>#</th>
        <th>书名</th>
        <th>作者</th>
        <th>出版</th>
        <th>更新日期</th>
        <th>修改</th>
        <th>删除</th>
      </tr>
      <c:forEach var="i" items="${it.items}" varStatus="status">
        <tr>
          <td>${status.count}</td>
          <td>${i.title}</td>
          <td>${i.authors}</td>
          <td>出版</td>
          <td>更新日期</td>
          <td>修改</td>
          <td>删除</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <div id="page" class="pagination pagination-centered">
    <ul id="ul_page">
    </ul>
  </div>
</section>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
      $(function() {
        var index = 1;
        //取得查询数据的总结果
        var count = ${it.count};
        //取得当前查询的开始值与结束值
        var start = ${it.start};
        var limit = ${it.limit};
        
        if (count == 0) {
          return;
        }

        var pageCount = count % 10 == 0 ? count / 10 : count / 10 + 1;
        var $url = $("#ul_page");

        //设置前翻页的属性
        var pre_href = "javaScript:void()";
        var next_href = "javaScript:void()";

        if (pageCount == 1) {

        } else if (pageCount == 2) {
          if (start == 0) {
            pre_href = "javaScript:void()";
            next_href = "?start=" + (start + 10) + "&limit="
                + (count - 10);

          } else {
            pre_href = "?start=" + (start - 10) + "&limit=" + 10;
            next_href = "javaScript:void()";
          }
        } else {
          if (start == 0) {
            pre_href = "javaScript:void()";
          } else {
            pre_href = "?start=" + (start - 10) + "&limit=" + 10;
          }
          if ((count - start - 10) <= 0) {
            next_href = "javaScript:void()";

          } else {
            if ((count - start - 10) < 10) {
              next_href = "?start=" + (start + 10) + "&limit="
                  + (count - start - 10);
            } else {
              next_href = "?start=" + (start + 10) + "&limit="
                  + 10;
            }
          }
        }

        var $a_prev = $("<a href="+pre_href+">Prev</a>");
        var $a_next = $("<a href="+next_href+">next</a>");
        if (start == 0) {
          $a_prev.attr("unable", true);
          $a_prev.addClass("disable");
        }
        if ((count - start - 10) <= 0) {
          $a_next.attr("unable", true);
          $a_next.addClass("disable");
        }
        var $li_per = $("<li></li>").append($a_prev);
        var $li_next = $("<li></li>").append($a_next);

        $url.append($li_per);
        for (index; index <= pageCount; index++) {
          $li = $("<li></li>");
          var href = "";
          if (index == 1) {
            href = "?start=0&limit=10";
          } else if (index == pageCount) {
            href = "?start=" + (index - 1) * 10 + "&limit="
                + (count - (index - 1) * 10);
          } else {
            href = "?start=" + (index - 1) * 10 + "&limit=" + 10;
          }
          var $a = $("<a href="+href+" id="+index+">" + index
              + "</a>");
          $li.append($a);
          if ((start / 10 + 1) == index) {
            $li.addClass("active");
          }
          $url.append($li);
        }
        $url.append($li_next);
      });
    </script>