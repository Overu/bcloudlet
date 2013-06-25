<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<div>
  <section>
    <div class="container-fluid">
      <div>
        <section id="thumbnails">
          <div>
            <jsp:include page="rank.jsp"></jsp:include>
          </div>
          <jsp:include page="../Books/booklist.jsp"></jsp:include>          
        </section>
      </div>
  </section>
</div>
<w:include page="footer.jsp"></w:include>