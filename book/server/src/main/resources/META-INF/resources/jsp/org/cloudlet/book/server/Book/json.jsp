<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{ "id": "${it.id}", "title": "${it.title}", "summary": "${it.summary}", "uri": "${it.uri}", "coverUrl": "${it.coverUrl}", "comments": "${it.comments.items}", "authors": "${it.authors}", "editors": "${it.editors}", "price": ${it.price}, "new_price": ${it.new_price}, "fullUrl": "${it.fullUrl}"}
