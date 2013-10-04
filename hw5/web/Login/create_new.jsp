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
    <title>Create New Account</title>
</head>
<body>
<h1>Create New Account</h1>

<p>Please enter user name and password.</p>

<form action="AccountCreationServlet" method="post">
    User Name: <input type="text" name="name"/> <br/>
    Password: <input type="text" name="password"/>
    <input type="submit" value="create"/>
</form>
</body>
</html>