<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书详情</title>
</head>
<body>
  <h1>${it.title}</h1>
  <p>作者：${it.authors}</p>
  <p>${it.summary}</p>
  <h2>分类</h2>
  <ul>
    <c:forEach var="i" items="${it.tags}">
      <li><a href="${i.uri}">${i.value}</a>
    </c:forEach>
  </ul>
  <h2>点评</h2>
  <ul>
    <c:forEach var="i" items="${it.comments.items}">
      <li>${i.content}</a>
    </c:forEach>
  </ul>
</body>
</html>
