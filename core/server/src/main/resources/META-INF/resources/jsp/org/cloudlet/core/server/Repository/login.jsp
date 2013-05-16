<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<title>睿泰书城</title>
<jsp:include page="/meta.jsp"></jsp:include>
<style type="text/css">
.oauth-panel {
  float: left;
  margin: 0;
  padding-left: 30px;
}

.oauth-panel>h4 {
  font-size: 16px;
  line-height: 20px;
  margin: 10px 0;
  font-family: inherit;
  font-weight: bold;
  color: inherit;
  text-rendering: optimizelegibility;
  font-family: "Microsoft Yahei", '微软雅黑', '宋体', "Helvetica Neue", Helvetica, Arial, sans-serif;
}

.oauth-panel>li {
  margin-top: 15px;
  height: 20px;
  line-height: 20px;
  padding-left: 0 !important;
}

.oauth-panel>li>span {
  display: block;
  float: left;
  width: 20px;
  height: 20px;
  margin-right: 5px;
  background: url("./static/lib/images/footer-sns-icons.png") no-repeat;
}

.qq {
  background-position: -120px 0!important;
}

.sinaweibo {
  background-position: -40px 0!important;
}

.renren {
  background-position: -20px 0!important;
}

.douban {
  background-position: -60px 0!important;
}
</style>
</head>
<body>
  <div class="g-doc">
    <header class="g-hd">
      <div class="m-header">
        <div class="m-logo">
          <a href="/" title="多看精品书城" hidefocus="hidefocus">睿泰阅读<span></span></a>
        </div>
      </div>
    </header>
    <div class="g-bd1">
      <div class="m-login">
        <div class="w-box3">
          <div class="content">
            <h2 class="ttl">欢迎登录睿泰阅读帐户</h2>
            <div class="w-msg w-msg-error" style="display: none;">登录名不能为空。</div>
            <!-- <div class="w-form j-form"> -->
            <form class="j-form w-form" action="" method="post">
              <ul>
                <li>
                  <div class="w-txtbox">
                    <!-- <p class="txt" style="color: rgb(170, 170, 170); display: block;">邮箱</p> -->
                    <input type="text" class="w-txt js-invalid" name="username" spellcheck="false" autocapitalize="off" autocomplete="off"
                      autocorrect="off" data-required="true" id="auto-id-6" placeholder="用户">
                  </div>
                </li>
                <li>
                  <div class="w-txtbox">
                    <!-- <p class="txt" style="color: rgb(170, 170, 170);">密码</p> -->
                    <input type="password" class="w-txt js-invalid" name="password" data-required="true" id="auto-id-7" placeholder="密码">
                  </div>
                </li>
              </ul>
              <div class="act" style="position: relative">
                <div class="loadnext" style="position: absolute; top: 20px; right: 170px;">
                  <div class="spin"></div>
                </div>
                <input type="submit" class="w-btn2" style="float: right;" id="login" value="登　录" hidefocus="hidefocus">
                <div class="w-chkbox" style="font-size: 12px;">
                  <p>
                    还没有睿泰阅读帐号？<a href="/reg" hidefocus="hidefocus">立即注册&gt;&gt;</a>
                  </p>
                  <input id="checkbox" name="duration" type="checkbox" checked="checked"><label>30天免登录</label><a href="/forget"
                    hidefocus="hidefocus">忘记密码？</a>
                </div>
              </div>
            </form>
            <!--</div>-->
          </div>
          <div class="aside" style="top: 100px;">
            <ul class="oauth-panel">
              <h4>使用合作平台登录</h4>
              <li><span class="qq"></span><a href="#">用QQ帐号登录</a></li>
              <li><span class="sinaweibo"></span><a href="#">用新浪微博帐号登录</a></li>
              <li><span class="renren"></span><a href="#">用人人网帐号登录</a></li>
              <li><span class="douban"></span><a href="#">用豆瓣帐号登录</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="/footer.jsp"></jsp:include>
  <!-- <form action="" method="post">
    <input type="text" name="username"> <input type="text" name="password"> <input type="submit" value="登录">
  </form> -->
</body>
</html>