<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <a href="javascript:void(0);" class="exit" id="exit" hidefocus="hidefocus">退出</a> <span class="arr0"></span> <span class="arr1"></span>
          </div>
        </div>
      </div>
    </div>
  </div>
</header>