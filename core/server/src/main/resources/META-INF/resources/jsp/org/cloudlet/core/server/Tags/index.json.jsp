<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{ "title": "${it.title}", "uri": "${it.uri}","count": ${it.count} , "items":[
  <c:forEach var="i" items="${it.items}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    { "id": "${i.id}", "value": "${i.value}", "title": "${i.title}", "uri": "${i.uri}", "weight": ${i.weight} }
  </c:forEach>
  ]
}
