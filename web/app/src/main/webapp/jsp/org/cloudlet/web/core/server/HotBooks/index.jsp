<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<title>畅销榜 - 睿泰书城</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="author" content="duokan" />
<meta property="wb:webmaster" content="614a83f3edbfc3be" />

<meta name="description" content="多看精品书城畅销榜，网罗时下热门图书。多看阅读支持iPhone,iPad,安卓,Kindle平台下载阅读。" />

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2" />
<link type="text/css" rel="stylesheet" href="/static/lib/css/style.css?201303081413" />
<!--[if IE 6]><script type="text/javascript">document.execCommand("BackgroundImageCache", false, true);</script><![endif]-->
<!--[if IE]><script src="/static/lib/gallery/html5.js"></script><![endif]-->
<!-- <script src="/static/lib/firebug-lite/build/firebug-lite.js"></script> -->
<script src="/static/lib/seajs/2.0.0/sea.js"></script>
<script>
	seajs.config({
		base : '/static/lib/',
		alias : {
			'jquery' : 'gallery/jquery/1.8.3/jquery',
			'es5-safe' : 'gallery/es5-safe/es5-safe'
		},
		map : [ [ /^(.*\/static\/lib\/.*\.(?:css|js))(?:.*)$/i,
				'$1?201303081413' ] ],
		preload : [ Function.prototype.bind ? '' : 'es5-safe', 'jquery',
				'gallery/underscore/1.0/underscore' ]
	});
	// FIXME 临时解决方案
	window.onLoadImg = function(evt) {
		evt.style.display = 'block'
	}
</script>

</head>
<body id="page-duokan-com" itemscope itemtype="http://schema.org/WebPage">
  <noscript>请使用支持脚本的浏览器！</noscript>
  <div class="g-doc">
    <header class="g-hd">
      <div class="m-header">
        <div class="m-logo">
          <a href="/" title="多看精品书城" hidefocus="hidefocus">多看精品书城</a>
        </div>
        <nav class="m-nav">
          <ul itemprop="breadcrumb">
            <li id="index"><a href="/" hidefocus="hidefocus">首页</a></li>
            <li id="rank"><a href="/r/%E7%95%85%E9%94%80%E6%A6%9C" hidefocus="hidefocus">排行榜</a></li>
            <li id="bargain"><a href="/r/%E7%83%AD%E9%97%A8%E6%8E%A8%E8%8D%90" hidefocus="hidefocus">精品<span class="dot">·</span>特价
            </a></li>

            <li id="category"><a href="/%E5%B0%8F%E8%AF%B4/c/14-1" class="usename" hidefocus="hidefocus">分类</a></li>
            <li><a href="http://home.duokan.com/index.html" target="_blank" hidefocus="hidefocus">客户端</a></li>
            <li><a href="http://bbs.duokan.com/" target="_blank" hidefocus="hidefocus">论坛</a></li>
          </ul>
        </nav>
        <div class="w-search">
          <div class="searchform" href="javascript:void(0);" hidefocus="hidefocus">
            <input type="text" id="dk-text" class="text" spellcheck="false" autocapitalize="off" autocomplete="off" autocorrect="off"
              hidefocus="hidefocus" value="搜索书名或者作者..." /> <span title="搜索" class="btn" id="searchbotton" style="display: none"></span>
          </div>
        </div>
        <div class="m-pctrl">
          <div id='w-login' style="display: none">
            <a href="/login" id="gotologin" hidefocus="hidefocus">登录</a><span class="w-sep">|</span><a href="/reg" hidefocus="hidefocus">注册</a>
          </div>
          <div class="m-person" id="w-person" style="display: none">
            <div class="w-dropdown-menu">
              <div class="ttl">
                <a href="/u/mybook" class="username" id="username" hidefocus="hidefocus"></a><a class="btn" href="javascript:void(0);"
                  hidefocus="hidefocus">&nbsp;</a>
              </div>
              <div class="cnt">
                <ul class="list">
                  <li><a href="/u/mybook" hidefocus="hidefocus">已购图书 </a></li>
                  <li><a href="/u/redeem" hidefocus="hidefocus">我的兑换码 </a></li>
                  <li><a href="/u/invite" hidefocus="hidefocus">我的邀请码</a></li>
                  <li><a href="/u/settings" hidefocus="hidefocus">帐号设置</a></li>
                </ul>
                <a href="javascript:void(0);" class="exit" id="exit" hidefocus="hidefocus">退出</a> <span class="arr0"></span> <span
                  class="arr1"></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>

    <div class="g-bd">
      <div class="w-tabox w-tabox-1">
        <div id="navFix" class="w-tab1 rank-nav">
          <ul>

            <li class="crt"><a href="/r/%E7%95%85%E9%94%80%E6%A6%9C" hidefocus="hidefocus">畅销榜</a></li>

            <li class=""><a href="/r/%E6%9C%88%E5%BA%A6%E6%A6%9C" hidefocus="hidefocus">月度榜</a></li>

            <li class=""><a href="/r/%E5%A5%BD%E8%AF%84%E6%A6%9C" hidefocus="hidefocus">好评榜</a></li>

            <li class=""><a href="/r/%E5%85%8D%E8%B4%B9%E6%A6%9C" hidefocus="hidefocus">免费榜</a></li>

            <li class=""><a href="/r/%E6%9C%80%E6%96%B0" hidefocus="hidefocus">最新上架</a></li>

          </ul>
        </div>
        <!--畅销榜-->
        <div id='book-rank'>
          <div class="content">
            <div class="w-booklist w-booklist-1">
              <jsp:include page="../Books/list.jsp"></jsp:include>
            </div>
          </div>
          <div class="w-load">
            <div class="spin"></div>
          </div>
        </div>
      </div>
    </div>

    <div class="w-fbox js-fixed" style="display: none">
      <div class="gotop">
        <a href="javascript:void(0);" hidefocus="hidefocus" title="回到顶部"><span class="cnt">回到顶部</span></a>
      </div>
      <div class="m-sns m-sns-2 js-sns">
        <div class="wrap">
          <ul>
            <li><a href="http://e.weibo.com/duokan" class="sina first" hidefocus="hidefocus"><span class="cnt"><span
                  class="img"></span>
                  <p>@多看阅读</p></span><span class="line"></span></a></li>
            <li><a href="http://e.t.qq.com/duokan_book" class="qq" hidefocus="hidefocus"><span class="cnt"><span class="img"></span>
                  <p>@多看阅读</p></span><span class="line"></span></a></li>
            <li class=""><a href="javascript:void(0);" class="weixin" hidefocus="hidefocus"><span class="cnt cnt2"><span
                  class="img"></span>
                  <p>
                    微信：<em>duokan</em>
                  </p></span><span class="line"></span></a></li>
            <li class="final"><a href="javascript:void(0);" class="miliao" hidefocus="hidefocus"><span class="cnt cnt2"><span
                  class="img"></span>
                  <p>
                    米聊：<em>800383</em>
                  </p></span><span class="line"></span></a></li>
          </ul>
        </div>
      </div>
    </div>
    <footer class="g-ft">
      <div class="m-cprt">
        Copyright&copy;duokan.com <a href="http://www.miibeian.gov.cn/" target="_blank" hidefocus="hidefocus">京ICP备10047083号</a> <a
          href="http://www.gapp.gov.cn/" target="_blank" hidefocus="hidefocus">电复证字第01D010号</a>
      </div>
    </footer>
  </div>


  <script>
			seajs.use([ 'duokan/store/1/page/book',
					'duokan/store/1/page/patched' ])
		</script>

  <script>
			seajs.use('duokan/store/1/page/act_rank_sale');
		</script>

</body>
</html>
