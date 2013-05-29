<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="header.jsp"></jsp:include>

<section id="newbook">
  <div class="page-header">
    <h1>上传新书</h1>
  </div>
  <form id="newBook" class="form-horizontal">
    <div class="control-group">
      <label class="control-label" for="booktitle">名称</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="booktitle" name="title" placeholder="bookname" required="required" data-type="text"  check-type="required"  required-message="请输入图书名称"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for=""source"">图书内容</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="file" id="source" name="" source"" required="required" data-type="text"  check-type="required" required-message="请选择图书内容"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="cover">图书封面</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="file" id="cover" name="cover" required="required" data-type="text"  check-type="required"  required-message="请选择图书封面图片"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>

    <div class="control-group">
      <label class="control-label" for="tags">图书类别</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <select id="tags" required="required" name="tags" multiple="multiple" data-role="multiselect">
            <option value="小说">小说</option>
            <option value="文学">文学</option>
            <option value="杂志">杂志</option>
            <option value="计算机">计算机</option>
            <option value="法律">法律</option>
          </select>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="authors">作者</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="authors" name="authors" placeholder="睿泰" required="required" data-type="text"  check-type="required"  required-message="请输入图书作者"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="price">价格</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="price" name="price" placeholder="19.99"  required="required" data-type="decimal"  check-type="decimal"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="new_price">最新价格</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="new_price" name="new_price" placeholder="9.99" required="required" data-type="decimal"  check-type="decimal"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="paper_price">纸质书价格</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="paper_price" name="paper_price" placeholder="39.99" required="required" data-type="decimal"  check-type="decimal"/>
          <div data-role="acknowledgement">
            <i></i>
          </div>
        </div>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="summary">摘要</label>
      <div class="controls">
        <textarea style="width: 500px;" cols="50" rows="3" id="summary" name="summary"></textarea>
      </div>
    </div>

    <div class="control-group">
      <div class="controls">
        <!-- <a href="javascript:void(0)" id="save-book" role="button" class="btn">上传</a>  -->
        <button type="button" id="save-book" class="btn">上传</button>
        <a href="create.html" role="button" class="btn">重置</a>
      </div>
    </div>
  </form>
</section>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
	$().ready(function() {
		$().acknowledgeinput();		
	});
	function createInstance(){
		 var options = {
				 	url:'./',
				 	contentType:"multipart/form-data",
				 	type:'post',
				 	data: $('#newBook').formSerialize(),
		            success : function(json) { 		            	
						window.location.href = 'books.html';
		             },
		             complete : function(data, textStatus) {
			 				if (textStatus == "success") {
			 					window.location.href = 'books.html';
			 				} else {
			 					window.location.href = 'create.html';
			 				}

			 			}
		        };		
			$("#newBook").ajaxSubmit(options);
			return false;
	}
	
	$('#newBook').myValidate("save-book", createInstance); 
</script>
