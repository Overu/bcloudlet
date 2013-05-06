<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${it.title}</title>
</head>
<body>
  <h1>${it.title}</h1>
  <p>Collection</p>
  <p>${it.uri}</p>
  <ul>
    <c:forEach var="i" items="${it.items}" varStatus="status">
      <li><a href="${i.uri}">${i.title}</a></li>
    </c:forEach>
  </ul>
</body>
</html>
