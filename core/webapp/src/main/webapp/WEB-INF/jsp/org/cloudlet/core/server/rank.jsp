<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.cloudlet.core.server.BookRank"%>

<div id="navFix" class="w-tab1 rank-nav">
  <ul>
    <c:forEach var="i" items="${it.ranks}" varStatus="status">
      <li class="<c:if test="${it.path eq i.path}">crt</c:if>"><a href="/books/r/${i.path}" hidefocus="hidefocus">${i.title}</a></li>
    </c:forEach>
  </ul>
</div>