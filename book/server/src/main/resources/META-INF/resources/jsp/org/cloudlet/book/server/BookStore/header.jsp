<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>${it.title }</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/static/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/static/css/main.css" rel="stylesheet">
<link href="/static/simpleGrid/css/simplePagingGrid-0.4.css" rel="stylesheet">
<link rel="stylesheet" href="/static/bootstrap/css/font-awesome.min.css">
<link href="/static/css/books.css" rel="stylesheet">
<script type="text/javascript" src="/static/lib/seajs/2.0.0/sea.js"></script>
<script type="text/javascript">
	seajs.config({
		 // Enable plugins
		  plugins: ['shim'],

		  // Configure alias
		  alias: {
		    'jquery': {
		      src: '/static/jquery/jquery.min.js',
		      exports: 'jQuery'
		    }
		  }
	});
	
	// FIXME 临时解决方案
	  window.onLoadImg = function(evt) {
	    evt.style.display = 'block'
	  }
</script>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">
  <!-- Navbar ================================================== -->
  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="brand" href="/">睿泰科技</a>
        <div class="nav-collapse collapse">
          <ul class="nav">
            <li class=""><a href="/books/">图书</a></li>
            <li class=""><a href="/courses/">课件</a></li>
            <li class=""><a href="/users/">用户</a></li>
          </ul>
        </div>
        <div class="w-search">
      <div class="searchform" href="javascript:void(0);" hidefocus="hidefocus">
        <input type="text" id="dk-text" class="text" spellcheck="false" autocapitalize="off" autocomplete="off" autocorrect="off" hidefocus="hidefocus" value="搜索书名或者作者..."> <span title="搜索" class="btn" id="searchbotton" style="display: none"></span>
      </div>
      <div class="m-pctrl">
      <div id="w-login" style="">
        <a href="/login" id="gotologin" hidefocus="hidefocus">登录</a><span class="w-sep">|</span><a href="/logout" hidefocus="hidefocus">注册</a>
      </div>
      <div class="m-person" id="w-person" style="display: none; ">
        <div class="w-dropdown-menu">
          <div class="ttl">
            <a href="/u/mybook" class="username" id="username" hidefocus="hidefocus"></a><a class="btn" href="javascript:void(0);" hidefocus="hidefocus">&nbsp;</a>
          </div>
          <div class="cnt" style="display: none; ">
            <ul class="list">
              <li><a href="/u/mybook" hidefocus="hidefocus">已购图书 </a></li>
              <li><a href="/u/redeem" hidefocus="hidefocus">我的兑换码 </a></li>
              <li><a href="/u/invite" hidefocus="hidefocus">我的邀请码</a></li>
              <li><a href="/u/settings" hidefocus="hidefocus">帐号设置</a></li>
            </ul>
            <a href="javascript:void(0);" class="exit" id="exit" hidefocus="hidefocus">退出</a> <span class="arr0"></span> <span class="arr1"></span>
          </div>
        </div>
      </div>
    </div>
    </div>
      </div>
    </div>
  </div>