<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>  
    <div class="span9">
      <section>
        <div id="brand" class="w-slider" unselectable="on" onselectstart="return false;"></div>
        <div class="container-fluid">
          <div>
            <section id="thumbnails">
              <div >
                <h1>推荐阅读</h1>
                <!-- <a href="/books/r/recommendation" class="w-more" hidefocus="hidefocus">更多</a> -->
              </div>
              <jsp:include page="booklist.jsp"></jsp:include>
            </section>
             <section id="thumbnails">
              <div >
                <h1>最新上架</h1>
                <!-- <a href="/books/r/recommendation" class="w-more" hidefocus="hidefocus">更多</a> -->
              </div>
              <jsp:include page="booklist.jsp"></jsp:include>
            </section>
             <section id="thumbnails">
              <div >
                <h1>最新特价</h1>
                <!-- <a href="/books/r/recommendation" class="w-more" hidefocus="hidefocus">更多</a> -->
              </div>
              <jsp:include page="booklist.jsp"></jsp:include>
            </section>
          </div>
        </div>
      </section>
    </div>
  <w:include page="footer.jsp"></w:include>