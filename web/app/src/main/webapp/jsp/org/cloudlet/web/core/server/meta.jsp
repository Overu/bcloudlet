<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<link type="text/css" rel="stylesheet" href="/static/lib/css/index.css?201303081413" />