<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Select a Patient to Edit:</h2>
    <ul>
        <%
            List<String> patients = (List<String>) request.getAttribute("patientNames");
            for (String patient : patients)
            {
                //String patientId = "123"; // Get patient ID dynamically
                String href = "patientDetails?patient=" + patient; // Update to servlet URL
        %>
        <li><a href="<%=href%>"><%=patient%></a>
        </li>
        <% } %>
    </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>