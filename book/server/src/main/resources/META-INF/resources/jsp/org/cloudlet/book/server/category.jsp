<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="w-txtlist2">
  <c:forEach var="i" items="${it.tags.items}">
    <li><a href="/books/t/${ i.value }" hidefocus="hidefocus"><span>${i.value}</span><em class="num">44</em></a></li>
  </c:forEach>
</ul>