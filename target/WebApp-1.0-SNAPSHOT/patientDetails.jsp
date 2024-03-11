<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Oldest Person</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h1>Patient Details</h1>
    <%
        String details = (String) request.getAttribute("result");
        if (details != null && !details.isEmpty())
        {
    %>
    <p><%=details%></p>
    <% } else { %>
    <p>No data available.</p>
    <% } %>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
