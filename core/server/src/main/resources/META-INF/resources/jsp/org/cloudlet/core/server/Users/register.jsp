<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="newbook">
  <div class="page-header">
    <h1>上传新书</h1>
  </div>
  <form class="form-horizontal">
    <div class="control-group">
      <label class="control-label" for="bookname">书籍名称</label>
      <div class="controls">
        <input type="text" id="bookname" placeholder="bookname">
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="summny">描述</label>
      <div class="controls">
        <textarea rows="3" id="summy"></textarea>
      </div>
    </div>
    <div class="control-group">
      <div class="controls">
        <button type="submit" class="btn">上传</button>
      </div>
    </div>
  </form>
</section>
<jsp:include page="footer.jsp"></jsp:include>

