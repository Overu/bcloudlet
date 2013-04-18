<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{ "title": "${it.title}", "uri": "${it.uri}", "items":[
  <c:forEach var="i" items="${it.items}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    {"title": "${i.title}", "uri": "${i.uri}", "content": "${i.content}"}
  </c:forEach>
  ]
}
