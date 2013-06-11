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
</script>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">
  <!-- Navbar ================================================== -->
  <div class="navbar navbar-inverse navbar-fixed-top">
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
      </div>
    </div>
  </div>