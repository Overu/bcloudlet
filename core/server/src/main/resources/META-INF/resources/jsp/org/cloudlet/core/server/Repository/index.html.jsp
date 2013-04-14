<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<title>${it.title}</title>
<body>
<jsp:setProperty property="title" name="it" value="My Repo"/>
<% Object o = request.getAttribute("it"); %>
It: <%= o %><br/>
<h1>${it.title}</h1>
</body>
</html>