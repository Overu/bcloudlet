<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="newbook">
  <div class="page-header">
    <h1>上传新书</h1>
  </div>
  <form id="newBook" class="form-horizontal" enctype="multipart/form-data" method="post" action="./" target="/admin.html">
    <div class="control-group">
      <label class="control-label" for="booktitle">名称</label>
      <div class="controls">
        <input type="text" id="booktitle" name="title" placeholder="bookname" check-type="required" required-message="书名不能为空！">
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="summary">摘要</label>
      <div class="controls">
        <textarea cols="50" rows="3" id="summary" name="summary"></textarea>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="full">图书内容</label>
      <div class="controls">        
        <input type="file" id="source" name="source"  check-type="required" multiple required-message="图书内容不能为空！">
      </div>
    </div>
     <div class="control-group">
      <label class="control-label" for="cover">图书封面</label>
      <div class="controls">        
        <input type="file" id="cover" name="cover" multiple  required-message="图书封面图片不能为空！">
      </div>
    </div>
    <div class="control-group">
      <div class="controls">
        <a href="javascript:void(0)" id="save-book" role="button" class="btn">上传</a> 
        <button type="submit"  class="btn">上传</button>
        <a href="create.html" role="button" class="btn">重置</a>
      </div>
    </div>    
  </form>
</section>
<jsp:include page="footer.jsp"></jsp:include>
<!-- <script type="text/javascript">
  $('#newBook').myValidate("save-book", function() {
    $.ajax({
      type : 'POST',
      contentType : "multipart/form-data",
      url : './',
      data : $("#newBook"),//序列化表单里所有的内容         
      complete : function(data, textStatus) {
    	  //window.location.href = 'admin.html';
      }
    });
  });
</script> -->
