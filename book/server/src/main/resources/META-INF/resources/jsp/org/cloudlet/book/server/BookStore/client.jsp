<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>睿泰-客户端1</title>
<jsp:include page="/meta.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="/static/lib/css/client_style.css">
<link rel="stylesheet" type="text/css" href="/static/lib/css/reset.css">
<style type="text/css">
body {
  line-height: initial;;
  text-align: initial; 
}

.nav_ajust {
  margin: 0 18px;
  text-indent: -9999px;
  display: block;
}

div#wrap>footer {
  color: rgb(102, 102, 102);
  position: relative;
  z-index: 10;
  height: 55px;
  background: rgb(238, 237, 233);
  width: initial;
}

div#wrap>header {
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 10;
  height: 71px;
  background: rgb(55, 49, 42) url(/static/lib/images/nav.bg.png) repeat-x 0 0;
}

div#wrap>header>div {
  width: 1010px;
  margin: 0 auto;
}

/* div#wrap li a {
  color: rgb(89, 182, 3);
} */
</style>
</head>
<body>
  <div id="wrap">
    <jsp:include page="/header.jsp"></jsp:include>
    <div id="brand">
      <div class="slide"
        style="height: 303px; margin-top: 0px; margin-bottom: 29px; background: url(/static/lib/images/webbookstor-bg1.png) repeat-x 0 0;">
        <a href="http://book.duokan.com/"><img src="/static/lib/images/webbookstor-bg2.png"></a>
      </div>
    </div>
    <div id="content">
      <section class="inner">
        <article id="ios">
          <header>睿泰阅读 for iOS</header>
          <div class="box">
            <p class="meta">
              版本: 2.0.0 文件大小: 57.2MB<br> 适合机型: iOS 4.3+ 更新时间: 2013-03-09
            </p>
            <ul>
              <li><span>极致排版:</span>精选多字体混排，媲美纸书阅读体验</li>
              <li><span>手势设计:</span>丰富的手势设计，可以单指添加书签<br> 双指拖拽导航窗口和调节亮度</li>
              <li><span>文内交互:</span>支持文内注解、书摘、批注等操作</li>
              <li><span>全新尝试:</span>正版图书的一种全新尝试</li>
              <li><span>社交元素:</span>社交元素与阅读相结合，分享内容到微博</li>
            </ul>
            <p class="act">
              <a class="down" href="http://itunes.apple.com/cn/app/duo-kan-yue-du/id517850153?ls=1&mt=8"><img
                src="/static/lib/images/clear.gif"></a> <a class="more" href="http://home.duokan.com/dk_reader.html"><img
                src="/static/lib/images/clear.gif"></a>
            </p>
          </div>
          <img class="preview" src="/static/lib/images/device.ios.png" alt="duokan for ios preview"> <i class="name">ios</i>
        </article>
        <article id="android">
          <header>睿泰阅读 for Android</header>
          <p class="meta">
            版本: 1.9.1 文件大小: 8.2MB<br> 适合机型: Android 2.2+ 更新时间: 2012-12-18
          </p>
          <img class="preview" src="/static/lib/images/device.android.png" alt="duokan for android preview">
          <div class="box">
            <ul>
              <li><span>格式支持:</span>TXT / EPUB</li>
              <li><span>排版效果:</span>优雅的中文排版，多字体/图文混排 <br>间距处理、标点禁排和压缩</li>
              <li><span>海量书城:</span>数百万册图书联网搜索</li>
              <li><span>书籍匹配:</span>独有的封面等元数据联网匹配功能</li>
              <li><span>阅历系统:</span>更加科学的规划阅读时间</li>
            </ul>
            <!-- <ul class="setup_box">
              <li class="setup_usehlpe"><em>&nbsp;</em><a style="text-decoration: underline" href="javascript:void(0);"
                onclick="qqapp_dl_apk(this);" ex_url="多看阅读 " appname="多看阅读 " asistanturl="" asistanturlid="990349" title="使用应用助手一键安装到手机">应用助手一键安装</a></li>
              <li class="setup_threesixzreo" style="border-bottom: 1px solid #E3E2E1;"><em>&nbsp;</em><a
                style="text-decoration: underline"
                href="zhushou360://type=apk&name=%E5%A4%9A%E7%9C%8B%E9%98%85%E8%AF%BB&url=http://down.mumayi.com/42861/360box">360手机助手一键安装</a></li>
              <li class="setup_threesixzreo" style="border-bottom: 1px solid #E3E2E1;"><em>
                  <div style="width: 44px; position: relative; overflow: hidden;">
                    <img style="border: none" src="./client_files/logo_new.png" height="44">
                  </div>
              </em> <a class="zhushou-download" style="text-decoration: underline" href="javascript:;" id="zhushou-download" title="多看阅读"
                onclick="iframe(event);" meta-packagename="" meta-versionname="" meta-url="http://www.appchina.com/app/com.duokan.reader/"
                meta-icon="" meta-name="多看阅读" meta-channel="m0053" meta-filetype="apk">应用汇一键安装</a></li>
              <li class="setup_threesixzreo"><em
                style="background: url(http://s.wdjimg.com/style/images/wdj/new-header-icon.png) -2px -9px;">&nbsp;</em> <a
                style="text-decoration: underline" href="http://apps.wandoujia.com/apps/com.duokan.reader/download" name="多看阅读"
                onclick="return wdapi_apkdl_m(this, &#39;source&#39;);" title="通过豌豆荚下载">豌豆荚一键安装</a></li>
            </ul> -->
            <p class="act">
              <a class="down" href="http://bbs.duokan.com/forum/thread-75839-1-1.html"><img src="/static/lib/images/clear.gif"></a> <a
                class="more" href="http://bbs.duokan.com/forum/forum-49-1.html"><img src="/static/lib/images/clear.gif"></a> <br>
            </p>
          </div>
          <i class="name">android</i>
        </article>
      </section>
    </div>
    <jsp:include page="/footer.jsp"></jsp:include>
  </div>
  <script>
			seajs.use([ 'duokan/store/1/page/book' ]);

			window.onload = function() {
				var win = $(window), topH = $('#topbar').height(), brandH = $(
						'#brand').height(), cursor = $('#topbar .cursor'), nav = $('#topbar .nav'), article = $('.scroll_article'), move, lastIdx, timeout = false;
				nav.each(function(idx, el) {
					if (idx < 3) {
						$(el)
								.click(
										function(e) {
											e.preventDefault();
											var val = (idx == 0) ? 0
													: $(article[idx - 1])
															.offset().top + 18;
											$('html,body').animate({
												scrollTop : val
											}, 500);
										});
					}
				});
			}
		</script>

  <!--[if lt IE 7]>
<script src="http://b.duokan.com/static/portal/correctPNG.js"></script>
<![endif]-->
</body>
</html>