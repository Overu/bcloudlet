<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Books</title>
</head>
<body>
  <h1>${it.title}</h1>
  <h2>Item List</h2>
  <ul>
    <c:forEach var="i" items="${it.items}">
      <li><a href="${i.uri}">${i.title}</a>
    </c:forEach>
  </ul>
</body>
</html>
