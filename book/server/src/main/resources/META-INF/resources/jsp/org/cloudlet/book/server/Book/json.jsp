<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{ "id": "${it.id}", "title": "${it.title}", "summary": "${it.summary}", "uri": "${it.uri}", "coverUrl": "${it.coverUrl}"
, "comments": {
  "items": [
  <c:forEach var="i" items="${it.comments.items}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    {"id": "${i.id}", "title": "${i.title}", "content": "${i.content}", "uri": "${i.uri}"}
  </c:forEach>
  ], "count": ${ it.comments.count }}
  , "authors": "${it.authors}", "editors": "${it.editors}", "price": ${it.price}, "new_price": ${it.new_price}, "fullUrl": "${it.fullUrl}"}
