<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.cloudlet.org/jsp/web" prefix="w"%>
<w:include page="header.jsp"></w:include>
<section id="users">
  <div class="container-fluid">
    <div class="page-header">
      <h1>系统用户</h1>
    </div>
    <div class="input-append" align="right">
      <input id="search-name" class="span2" id="appendedInputButtons" type="text">
      <button id="search-btn" class="btn" onclick="searchData();" type="button">查询</button>
    </div>

    <div id="usersGrid"></div>
  </div>
</section>

<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">修改用户信息</h3>
  </div>
  <div class="modal-body">
    <form id="editform" class="form-horizontal">
      <div>
        <input type="hidden" id="uri">
      </div>
      <div class="control-group">
        <label class="control-label" for="username">姓名</label>
        <div class="controls">
          <div class="input-append" data-role="acknowledge-input">
            <input type="text" id="username" name="name" placeholder="输入用户真实姓名" required="required" data-type="text" check-type="required" />
            <div data-role="acknowledgement">
              <i></i>
            </div>
          </div>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="inputEmail">邮箱</label>
        <div class="controls">
          <div class="input-append" data-role="acknowledge-input">
            <input type="text" id="inputEmail" name="email" placeholder="123@163.com" required="required" data-type="email"
              check-type="email" />
            <div data-role="acknowledgement">
              <i></i>
            </div>
          </div>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="phone">手机</label>
        <div class="controls">
          <div class="input-append" data-role="acknowledge-input">
            <input type="text" id="phone" name="phone" placeholder="13800138000" required="required" data-type="mobile" check-type="mobile"
              maxlength="11">
            <div data-role="acknowledgement">
              <i></i>
            </div>
          </div>
        </div>
      </div>
      <div class="control-group" id="check-box">
        <div class="controls">
          <label class="checkbox"> <input id="chk-pwd" type="checkbox" onclick="showpwddiv()"> 点击修改密码
          </label>
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
	seajs.use([ '/static/bookJS/navbar.js', '/static/bookJS/common.js',
			'/static/user/usersedit.js' ]);
</script>

<w:include page="footer.jsp"></w:include>
