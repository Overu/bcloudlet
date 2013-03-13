<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<title>免费专区 - 多看精品书城<%=new java.util.Date()%></title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="author" content="duokan" />
<meta property="wb:webmaster" content="614a83f3edbfc3be" />

<meta name="description" content="多看精品书城免费专区，免费图书本本经典。多看阅读支持iPhone,iPad,安卓,Kindle平台下载阅读。" />

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

            <li class=""><a href="/r/%E7%83%AD%E9%97%A8%E6%8E%A8%E8%8D%90" hidefocus="hidefocus">精品推荐</a></li>

            <li class=""><a href="/r/%E9%99%90%E5%85%8D%E7%89%B9%E4%BB%B7" hidefocus="hidefocus">最新特价</a></li>

            <li class="crt"><a href="/r/%E5%85%8D%E8%B4%B9" hidefocus="hidefocus">免费专区</a></li>

          </ul>
        </div>
        <!--畅销榜-->
        <div id='book-rank'>
          <div class="content">
            <div class="w-booklist w-booklist-1">
              <ul id="paybook">






                <li class="itm" book_id="608461668a0d11e282ed00163e0123ac"><a
                  href="/i%E5%A4%A9%E4%B8%8B%E7%BD%91%E5%95%86%E7%B2%BE%E9%80%89%EF%BC%88%E5%88%9B%E5%88%8A%E5%8F%B7%EF%BC%89/b/24672"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/03/fe84a0ec1d7e894c.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>唯一一本专门办给电商从业者的电子杂志。</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/i%E5%A4%A9%E4%B8%8B%E7%BD%91%E5%95%86%E7%B2%BE%E9%80%89%EF%BC%88%E5%88%9B%E5%88%8A%E5%8F%B7%EF%BC%89/b/24672"
                  hidefocus="hidefocus"> i天下网商精选（创刊号）</a>

                  <p class="author">
                    <span>许维</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">9.0</span> <span class="num">( <span itemprop="reviewCount">16</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="e10775b8870a11e2a3f500163e0123ac"><a
                  href="/%E5%86%99%E4%BD%9C%E5%A5%87%E8%BF%B9%EF%BC%8C%E5%A6%82%E4%BD%95%E4%B8%80%E4%B8%AA%E6%9C%88%E8%B5%9A4%E4%B8%87%E7%BE%8E%E5%85%83/b/24662"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/03/ae4ec05d5770f246.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>科技革命，生产者与消费者直接对接！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E5%86%99%E4%BD%9C%E5%A5%87%E8%BF%B9%EF%BC%8C%E5%A6%82%E4%BD%95%E4%B8%80%E4%B8%AA%E6%9C%88%E8%B5%9A4%E4%B8%87%E7%BE%8E%E5%85%83/b/24662"
                  hidefocus="hidefocus"> 写作奇迹，如何一个月赚4万美元</a>

                  <p class="author">
                    <span>李书航</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.0</span> <span class="num">( <span itemprop="reviewCount">48</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="047d406270eb11e2808800163e0123ac"><a
                  href="/%E9%9F%B3%E4%B9%90%E5%A4%A9%E5%A0%82%EF%BC%881st/2013%EF%BC%89/b/21717" class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/03/ba361518f8d5a1de.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>休刊6年后，《音乐天堂》满血复活！</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E9%9F%B3%E4%B9%90%E5%A4%A9%E5%A0%82%EF%BC%881st/2013%EF%BC%89/b/21717" hidefocus="hidefocus">

                    音乐天堂（1st/2013）</a>

                  <p class="author">
                    <span>李文枫</span><span>蓝靛</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.8</span> <span class="num">( <span itemprop="reviewCount">180</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="a9f8fb7a6e8011e2808800163e0123ac"><a href="/%E4%B9%8C%E5%90%88%E4%B9%8B%E4%BC%97/b/20817"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/ee4f8fa52fde0495.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>获著名心理学家弗洛伊德高度评价的书</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E4%B9%8C%E5%90%88%E4%B9%8B%E4%BC%97/b/20817" hidefocus="hidefocus"> 乌合之众</a>

                  <p class="author">
                    <span>【法】古斯塔夫·勒庞</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.5</span> <span class="num">( <span itemprop="reviewCount">29</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="2e8ddd00797211e2b27a00163e0123ac"><a
                  href="/%E7%B2%BE%E5%93%81%E9%98%85%E8%AF%BB%E6%97%B6%E5%85%89%EF%BC%88%E7%AC%AC8%E6%9C%9F%EF%BC%89/b/21737" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/03/ecd979860a0dd414.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>书与影，哪个少年派更接近上帝？茅于轼毁誉参半，身边人怎样评价？</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E7%B2%BE%E5%93%81%E9%98%85%E8%AF%BB%E6%97%B6%E5%85%89%EF%BC%88%E7%AC%AC8%E6%9C%9F%EF%BC%89/b/21737"
                  hidefocus="hidefocus"> 精品阅读时光（第8期）</a>

                  <p class="author">
                    <span>多看</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span itemprop="reviewCount">83</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="c3611e6470d611e2808800163e0123ac"><a href="/%E5%BE%AE%E5%8A%BF%E5%8A%9B/b/21637" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/317639c3ca7da6f6.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>边缘媒体人，社会化“湿人”，黑马良驹博文精选。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E5%BE%AE%E5%8A%BF%E5%8A%9B/b/21637" hidefocus="hidefocus"> 微势力</a>

                  <p class="author">
                    <span>黑马良驹</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.1</span> <span class="num">( <span itemprop="reviewCount">21</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="b3fee504814e11e2a01e00163e0123ac"><a
                  href="/Leiphone%E7%B2%BE%E9%80%89%EF%BC%882013.01%EF%BC%89/b/23887" class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/31ad8e40bb6dfa49.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>雷锋网第一本电子杂志，召集移动互联网最狂热信徒！</p>
                      </div>
                    </div>
                </a> <a class="title" href="/Leiphone%E7%B2%BE%E9%80%89%EF%BC%882013.01%EF%BC%89/b/23887" hidefocus="hidefocus">

                    Leiphone精选（2013.01）</a>

                  <p class="author">
                    <span>张海春</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.5</span> <span class="num">( <span itemprop="reviewCount">84</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="14d54c40797211e2b27a00163e0123ac"><a href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.4/b/21732"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/1d661b1bd9f4087d.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>虎嗅电子刊第四弹，个性化的商业新闻笔记！</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.4/b/21732" hidefocus="hidefocus"> 虎嗅精选NO.4</a>

                  <p class="author">
                    <span>蔡钰</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.8</span> <span class="num">( <span itemprop="reviewCount">86</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="32559c666b8f11e2808800163e0123ac"><a
                  href="/%E6%88%91%E4%BB%AC%E7%9A%84%E6%95%B0%E5%AD%97%E5%87%BA%E7%89%88%E6%A2%A6/b/20762" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/545b4bfcfac67f46.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>胡晓东谈数字出版，专栏+访谈首次结集，独家发布！</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E6%88%91%E4%BB%AC%E7%9A%84%E6%95%B0%E5%AD%97%E5%87%BA%E7%89%88%E6%A2%A6/b/20762"
                  hidefocus="hidefocus"> 我们的数字出版梦</a>

                  <p class="author">
                    <span>胡晓东</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.5</span> <span class="num">( <span itemprop="reviewCount">71</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="ed9161fe091f11e29b4000163e0123ac"><a href="/%E5%AE%B9%E6%96%8B%E9%9A%8F%E7%AC%94/b/5907"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/f5b197ad63335953.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>毛泽东生前所看的最后一部书。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E5%AE%B9%E6%96%8B%E9%9A%8F%E7%AC%94/b/5907" hidefocus="hidefocus"> 容斋随笔</a>

                  <p class="author">
                    <span>洪迈</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span itemprop="reviewCount">14</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="516df386797311e2b27a00163e0123ac"><a
                  href="/%E6%96%B0%E5%8D%8E%E9%98%85%E8%AF%BB%EF%BC%882013.1%EF%BC%89/b/21742" class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/041c1c5249597d87.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>主编严歌苓：用心做国内最好的文学杂志。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E6%96%B0%E5%8D%8E%E9%98%85%E8%AF%BB%EF%BC%882013.1%EF%BC%89/b/21742" hidefocus="hidefocus">

                    新华阅读（2013.1）</a>

                  <p class="author">
                    <span>严歌苓</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span itemprop="reviewCount">61</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="1482a26a80bb11e2afd200163e0123ac"><a
                  href="/%E7%A0%81%E5%86%9C%EF%BC%88%E7%AC%AC4%E6%9C%9F%EF%BC%89/b/23727" class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/12401c07caeb5acf.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>本期主题：活的信息</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E7%A0%81%E5%86%9C%EF%BC%88%E7%AC%AC4%E6%9C%9F%EF%BC%89/b/23727" hidefocus="hidefocus"> 码农（第4期）</a>

                  <p class="author">
                    <span>图灵社区</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.3</span> <span class="num">( <span itemprop="reviewCount">64</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="f0ffa464797111e2b27a00163e0123ac"><a href="/36%E6%B0%AA%EF%BC%882013.2%EF%BC%89/b/21727"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/83230c63cc126fbb.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>氪星人带你穿越未来：2500年谁主宰地球，人类还是机器人？</p>
                      </div>
                    </div>
                </a> <a class="title" href="/36%E6%B0%AA%EF%BC%882013.2%EF%BC%89/b/21727" hidefocus="hidefocus"> 36氪（2013.2）</a>

                  <p class="author">
                    <span>王壮</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span itemprop="reviewCount">43</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="ce68f1a8797111e2b27a00163e0123ac"><a
                  href="/%E9%BB%91%E8%93%9D%7C122%E6%9C%9F%E2%80%A2%E6%AE%B5%E6%9E%97%E4%B8%93%E5%8F%B7/b/21722" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/03/3daaa73113af3a65.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>致力艺术，保护个性。最好的，黑蓝才专注。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E9%BB%91%E8%93%9D%7C122%E6%9C%9F%E2%80%A2%E6%AE%B5%E6%9E%97%E4%B8%93%E5%8F%B7/b/21722"
                  hidefocus="hidefocus"> 黑蓝|122期•段林专号</a>

                  <p class="author">
                    <span>黑蓝文学</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class="red"></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">9.69</span> <span class="num">( <span itemprop="reviewCount">23</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="feefd35a6b7511e28a7d00163e0123ac"><a
                  href="/%E8%85%BE%E8%AE%AF%E7%A7%91%E6%8A%80%C2%B7%E7%A7%91%E6%8A%80%E6%96%B0%E5%B8%B8%E6%80%81/b/20677" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/8157d4f94364659d.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>传统互联网垂垂老矣，移动互联才是早晨八九点钟的太阳</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E8%85%BE%E8%AE%AF%E7%A7%91%E6%8A%80%C2%B7%E7%A7%91%E6%8A%80%E6%96%B0%E5%B8%B8%E6%80%81/b/20677"
                  hidefocus="hidefocus"> 腾讯科技·科技新常态</a>

                  <p class="author">
                    <span>杨福</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.4</span> <span class="num">( <span itemprop="reviewCount">164</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="a042a6286f7d11e2808800163e0123ac"><a
                  href="/%E7%B2%BE%E5%93%81%E9%98%85%E8%AF%BB%E6%97%B6%E5%85%89%EF%BC%88%E7%AC%AC7%E6%9C%9F%EF%BC%89/b/20907" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/02/83b5c3c53d25446c.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>春运途中窥视王小波的内丹，多看集体两百余人拜新年！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E7%B2%BE%E5%93%81%E9%98%85%E8%AF%BB%E6%97%B6%E5%85%89%EF%BC%88%E7%AC%AC7%E6%9C%9F%EF%BC%89/b/20907"
                  hidefocus="hidefocus"> 精品阅读时光（第7期）</a>

                  <p class="author">
                    <span>多看</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.19</span> <span class="num">( <span itemprop="reviewCount">105</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="020b7bf86ab711e28a7d00163e0123ac"><a
                  href="/%E7%88%B1%E8%8C%83%E5%84%BF2012%E5%B9%B4%E5%BA%A6%E7%B2%BE%E9%80%89%E7%89%B9%E5%88%8A%EF%BC%88%E4%B8%8A%EF%BC%89/b/20667"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/9ba3556a26607c52.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>在新年之际回顾2012，唤醒那些值得珍惜的回忆。</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E7%88%B1%E8%8C%83%E5%84%BF2012%E5%B9%B4%E5%BA%A6%E7%B2%BE%E9%80%89%E7%89%B9%E5%88%8A%EF%BC%88%E4%B8%8A%EF%BC%89/b/20667"
                  hidefocus="hidefocus"> 爱范儿2012年度精选特刊（上）</a>

                  <p class="author">
                    <span>黄龙中</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.5</span> <span class="num">( <span itemprop="reviewCount">157</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="902aa16e6ab911e28a7d00163e0123ac"><a
                  href="/%E7%88%B1%E8%8C%83%E5%84%BF2012%E5%B9%B4%E5%BA%A6%E7%B2%BE%E9%80%89%E7%89%B9%E5%88%8A%EF%BC%88%E4%B8%8B%EF%BC%89/b/20672"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/8ce0d95a30433060.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>在新年之际回顾2012，唤醒那些值得珍惜的回忆。</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E7%88%B1%E8%8C%83%E5%84%BF2012%E5%B9%B4%E5%BA%A6%E7%B2%BE%E9%80%89%E7%89%B9%E5%88%8A%EF%BC%88%E4%B8%8B%EF%BC%89/b/20672"
                  hidefocus="hidefocus"> 爱范儿2012年度精选特刊（下）</a>

                  <p class="author">
                    <span>黄龙中</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span itemprop="reviewCount">106</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="b92c1c5c6b7f11e28a7d00163e0123ac"><a
                  href="/%E7%9C%8B%E8%A7%81%EF%BC%88%E8%AF%95%E8%AF%BB%E7%89%88%EF%BC%89/b/20712" class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/13e435cf9685cdf3.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>柴静首度出书讲述十年央视生涯，视频试读版</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E7%9C%8B%E8%A7%81%EF%BC%88%E8%AF%95%E8%AF%BB%E7%89%88%EF%BC%89/b/20712" hidefocus="hidefocus">

                    看见（试读版）</a>

                  <p class="author">
                    <span>柴静</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">9.19</span> <span class="num">( <span itemprop="reviewCount">686</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="304219d26a7b11e299d100163e0123ac"><a href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.3/b/20417"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/9e79bb950229a520.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>虎嗅电子刊第三弹，个性化的商业新闻笔记！</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.3/b/20417" hidefocus="hidefocus"> 虎嗅精选NO.3</a>

                  <p class="author">
                    <span>蔡钰</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">9.0</span> <span class="num">( <span itemprop="reviewCount">147</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="caa48534660511e2bee100163e0123ac"><a
                  href="/%E5%88%9B%E6%84%8F%E4%BA%94%E6%8A%8A%E5%88%80%EF%BC%88%E5%A4%A7%E5%B8%88%E8%BD%BB%E6%9D%BE%E8%AF%BB003%EF%BC%89/b/19512"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/b73ac99a6e9dc3f6.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>想要出类拔萃，就必须拥有创意思考的能力！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E5%88%9B%E6%84%8F%E4%BA%94%E6%8A%8A%E5%88%80%EF%BC%88%E5%A4%A7%E5%B8%88%E8%BD%BB%E6%9D%BE%E8%AF%BB003%EF%BC%89/b/19512"
                  hidefocus="hidefocus"> 创意五把刀（大师轻松读003）</a>

                  <p class="author">
                    <span>【美】乔希·林克纳</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">7.7</span> <span class="num">( <span itemprop="reviewCount">126</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="13bcb2b0660511e2bee100163e0123ac"><a
                  href="/%E5%BE%AE%E6%97%B6%E9%97%B4%E7%AE%A1%E7%90%86%E6%9C%AF%EF%BC%88%E5%A4%A7%E5%B8%88%E8%BD%BB%E6%9D%BE%E8%AF%BB002%EF%BC%89/b/19507"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/7c76dfd7c4c10af1.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>每天18分钟，你的人生可以不一样！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E5%BE%AE%E6%97%B6%E9%97%B4%E7%AE%A1%E7%90%86%E6%9C%AF%EF%BC%88%E5%A4%A7%E5%B8%88%E8%BD%BB%E6%9D%BE%E8%AF%BB002%EF%BC%89/b/19507"
                  hidefocus="hidefocus"> 微时间管理术（大师轻松读002）</a>

                  <p class="author">
                    <span>【美】彼得·布雷格曼</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.1</span> <span class="num">( <span itemprop="reviewCount">658</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="c6758acc660411e2bee100163e0123ac"><a
                  href="/%E4%BD%95%E4%B8%8D%E8%BF%99%E4%B8%AA%E5%91%A8%E6%9C%AB%E6%9D%A5%E5%88%9B%E4%B8%9A%EF%BC%88%E5%A4%A7%E5%B8%88%E8%BD%BB%E6%9D%BE%E8%AF%BB001%EF%BC%89/b/19502"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/7adda0fb8cb25f5a.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>用54小时开一家新公司！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E4%BD%95%E4%B8%8D%E8%BF%99%E4%B8%AA%E5%91%A8%E6%9C%AB%E6%9D%A5%E5%88%9B%E4%B8%9A%EF%BC%88%E5%A4%A7%E5%B8%88%E8%BD%BB%E6%9D%BE%E8%AF%BB001%EF%BC%89/b/19502"
                  hidefocus="hidefocus"> 何不这个周末来创业（大师轻松读001）</a>

                  <p class="author">
                    <span>【美】马克·内格</span><span>【美】克林特·尼尔森</span><span>【法】弗兰克·诺里戈特</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.0</span> <span class="num">( <span itemprop="reviewCount">206</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="d8032ee2608811e2957400163e0123ac"><a href="/36%E6%B0%AA%EF%BC%882013.1%EF%BC%89/b/18537"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/311c99e63a5b9171.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>盘点科技行业旧岁收获，预测2013年“神奇”事件。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/36%E6%B0%AA%EF%BC%882013.1%EF%BC%89/b/18537" hidefocus="hidefocus"> 36氪（2013.1）</a>

                  <p class="author">
                    <span>王壮</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.9</span> <span class="num">( <span itemprop="reviewCount">141</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="2f9a9f9e615811e2946a00163e0123ac"><a
                  href="/%E9%BB%91%E8%93%9D%7C121%E6%9C%9F%E2%96%AA%E9%BB%91%E5%A4%A9%E6%89%8D%E4%B8%93%E5%8F%B7/b/18557" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/e2f5f144d29328ba.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>艺术写作，各具个性。只有最好的文学才值得黑蓝专注。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E9%BB%91%E8%93%9D%7C121%E6%9C%9F%E2%96%AA%E9%BB%91%E5%A4%A9%E6%89%8D%E4%B8%93%E5%8F%B7/b/18557"
                  hidefocus="hidefocus"> 黑蓝|121期▪黑天才专号</a>

                  <p class="author">
                    <span>黑蓝文学</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.8</span> <span class="num">( <span itemprop="reviewCount">69</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="fab6882e57d911e2820200163e0123ac"><a
                  href="/%E4%B8%AD%E5%A4%AE%E7%BC%96%E8%AF%91%E5%87%BA%E7%89%88%E7%A4%BE2013%E9%87%8D%E7%82%B9%E4%B9%A6%E7%9B%AE/b/16382"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/248a0d426891d447.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>中央编译出版社2013重点书目！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E4%B8%AD%E5%A4%AE%E7%BC%96%E8%AF%91%E5%87%BA%E7%89%88%E7%A4%BE2013%E9%87%8D%E7%82%B9%E4%B9%A6%E7%9B%AE/b/16382"
                  hidefocus="hidefocus"> 中央编译出版社2013重点书目</a>

                  <p class="author">
                    <span>中央编译出版社</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.0</span> <span class="num">( <span itemprop="reviewCount">46</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="ede0c76858ab11e2820200163e0123ac"><a
                  href="/%E5%B0%8F%E9%98%85%E8%AF%BB_1%EF%BC%88%E5%88%9B%E5%88%8A%E5%8F%B7%EF%BC%89/b/17207" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2013/01/2da5c647aa401075.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>“阅读”，其实是一件很“小”的事</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E5%B0%8F%E9%98%85%E8%AF%BB_1%EF%BC%88%E5%88%9B%E5%88%8A%E5%8F%B7%EF%BC%89/b/17207"
                  hidefocus="hidefocus"> 小阅读 1（创刊号）</a>

                  <p class="author">
                    <span>广西师范大学出版社（上海）有限公司</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.5</span> <span class="num">( <span itemprop="reviewCount">323</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="06cb9cb0500c11e288f600163e0123ac"><a href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.2/b/16372"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2012/12/f2a1d4f9fa4b80c3.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>虎嗅第一本电子杂志，个性化的商业新闻笔记。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.2/b/16372" hidefocus="hidefocus"> 虎嗅精选NO.2</a>

                  <p class="author">
                    <span>蔡钰</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.6</span> <span class="num">( <span itemprop="reviewCount">125</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="b260f472500b11e288f600163e0123ac"><a href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.1/b/16367"
                  class="cover" hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2012/12/f9a03a4192388020.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>虎嗅第一本电子杂志，个性化的商业新闻笔记。</p>
                      </div>
                    </div>
                </a> <a class="title" href="/%E8%99%8E%E5%97%85%E7%B2%BE%E9%80%89NO.1/b/16367" hidefocus="hidefocus"> 虎嗅精选NO.1</a>

                  <p class="author">
                    <span>蔡钰</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='halfive'></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.9</span> <span class="num">( <span itemprop="reviewCount">221</span>
                      )
                    </span>
                  </div></li>

                <li class="itm" book_id="b60ad0a04a8b11e2b5b200163e0123ac"><a
                  href="/%E7%B2%BE%E5%93%81%E9%98%85%E8%AF%BB%E6%97%B6%E5%85%89%EF%BC%88%E7%AC%AC6%E6%9C%9F%EF%BC%89/b/16202" class="cover"
                  hidefocus="hidefocus">
                    <div class="wrap">
                      <img src="http://c.duokan.com/cover/2012/12/eac85c380d42acfb.jpg!ss" ondragstart="return false;"
                        oncontextmenu="return false;" onload="onLoadImg(this)" style="display: none" />

                      <div class="desc">
                        <p>2012年末日饭集结号令，听“诺奖”莫言语出惊人！</p>
                      </div>
                    </div>
                </a> <a class="title"
                  href="/%E7%B2%BE%E5%93%81%E9%98%85%E8%AF%BB%E6%97%B6%E5%85%89%EF%BC%88%E7%AC%AC6%E6%9C%9F%EF%BC%89/b/16202"
                  hidefocus="hidefocus"> 精品阅读时光（第6期）</a>

                  <p class="author">
                    <span>多看</span>
                  </p>



                  <div class="w-starfive" itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">

                    <ul class="five">
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li class='red'></li>
                      <li></li>
                    </ul>

                    <span style="display: none;" itemprop="ratingValue">8.4</span> <span class="num">( <span itemprop="reviewCount">237</span>
                      )
                    </span>
                  </div></li>

              </ul>
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




  <script type="text/template" id="jst-more">
{@each items as itm, index}
<li class="itm" book_id ="${ itm['book_id'] }">
<a class="cover" href="/${ itm['afs'] }/b/${itm['sid']}" hidefocus = "hidefocus">
<div class="wrap">
<img src="${ itm.cover|imgAdapt,'ss' }" ondragstart="return false;" oncontextmenu="return false;" onload="onLoadImg(this)" style="display:none" />
<div class="desc"><p>${ itm['summary'] }</p></div>
</div>
</a>
<div class="info">
<a class="title" href="/${ itm['afs'] }/b/${itm['sid']}">

${ itm['title'] }
</a>
{@if itm['authors'] != '' }
<p class="author">$${ itm['authors']| buildAuthor }</p>
{@else}
<p class="author">$${ itm['editors']| buildAuthor }</p>
{@/if}
<div class="w-starfive">
$${ itm.score|showStar }
<span class="num">( ${ itm['score_count'] } )</span>
</div>

<div class="desc"><p>${ itm['summary'] }</p></div>
</div>
</li>
{@/each}
</script>




  <script>
			seajs.use([ 'duokan/store/1/page/book',
					'duokan/store/1/page/patched' ])
		</script>

  <script>
			seajs.use('duokan/store/1/page/act_list_free');
		</script>

</body>
</html>
