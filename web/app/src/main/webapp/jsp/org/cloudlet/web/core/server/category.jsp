<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="w-txtlist2">
  <c:forEach var="i" items="${it.tags.items}">
    <li><a href="/%E5%B0%8F%E8%AF%B4/c/14-1" hidefocus="hidefocus"><span>${i.value}</span></a></li>
  </c:forEach>
</ul>