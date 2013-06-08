<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<section id="mybooks">
  <div class="container-fluid">
    <div class="page-header">
      <h1>图书</h1>
    </div>
    <div class="input-append" align="right">
      <input id="search-name" class="span2" id="appendedInputButtons" type="text">
      <button id="search-btn" class="btn" onclick="searchData();" type="button">查询</button>
    </div>
    <div id="booksGrid"></div>
  </div>
</section>
<jsp:include page="footer.jsp"></jsp:include>

<!-- Modal -->
<div id="myModal"  style="width: 50%;" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">修改书籍信息</h3>
  </div>
  <div class="modal-body">
    <form id="editform" class="form-horizontal">
      <div>
        <input type="hidden" id="uri">
      </div>     
    <div class="control-group">
      <label class="control-label" for="booktitle">名称</label>
      <div class="controls">
        <div class="input-append" data-role="acknowledge-input">
          <input type="text" id="booktitle" name="title" placeholder="bookname" required="required" data-type="text" check-type="required"/>
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
          <input type="text" id="authors" name="authors" placeholder="睿泰" required="required" data-type="text"  check-type="required"/>
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
          <input type="text" id="price" name="price" placeholder="19.99" required="required" data-type="decimal"  check-type="decimal"/>
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
        <textarea style="width: 300px;" cols="50" rows="3" id="summary" name="summary"></textarea>
      </div>
    </div>   
    </form>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    <button id="save-edit" type="button" class="btn btn-primary">保存</button>
  </div>
</div>
<script>
 seajs.use(['/static/bookJS/navbar.js','/static/bookJS/common.js','/static/bookJS/booksedit.js']);
</script>
