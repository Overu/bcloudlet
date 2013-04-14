<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{ "title": "${it.title}", "uri": "${it.uri}","count": ${it.count}, "limit": ${it.limit}, "items":[
<c:forEach var="i" items="${it.items}" varStatus="status">
  <c:if test="${status.index > 0}">,</c:if>
    {"id": "${i.id}", "title": "${i.title}", "uri": "${i.uri}", "weight": ${i.weight}, "coverUrl": "${i.coverUrl}", "price": ${i.price}, "new_price": ${i.new_price}, "authors": "${i.authors}", "editors": "${i.editors}", "summary": "${i.summary}"}
  </c:forEach>
] }
