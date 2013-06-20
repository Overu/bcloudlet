<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.cloudlet.book.server.BookRank"%>

<div id="navFix">
  <ul class="nav  nav-pills">
    <c:forEach var="i" items="${it.ranks}" varStatus="status">
      <li ><a href="/books/r/${i.path}" hidefocus="hidefocus">${i.title}</a></li>
    </c:forEach>
  </ul>
</div>