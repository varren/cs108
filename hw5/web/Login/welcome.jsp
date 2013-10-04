<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 28.09.13
  Time: 6:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome <%= request.getParameter("name")%></title>
</head>
<body>
   <h1>Welcome <%= request.getParameter("name")%></h1>
</body>
</html>