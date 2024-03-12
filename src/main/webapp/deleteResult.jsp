<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/header.jsp"/>
<html>
<head>
    <title>Delete Patient</title>
</head>
<body>
<div class="main">
    <h1>Patient Deleted</h1>
    <%
        String searchResult = (String) request.getAttribute("result");
        out.println(searchResult);
    %>
</div>
</body>
</html>
