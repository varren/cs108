<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 03.10.13
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Please enter another user name and password</title>
</head>
<body>
<h1>The name <%= request.getParameter("name")%> is already in use</h1>

<p>Please enter another user name and password.</p>

<form action="AccountCreationServlet" method="post">
    User Name: <input type="text" name="name"/> <br/>
    Password: <input type="text" name="password"/>
    <input type="submit" value="create"/>
</form>
</body>
</html>