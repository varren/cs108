<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 28.09.13
  Time: 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h1>Welcome to Homework 5</h1>

<p>Please log in.</p>

<form action="LoginServlet" method="post">
    User Name: <input type="text" name="name"/> <br/>
    Password: <input type="text" name="password"/>
    <input type="submit" value="login"/>
</form>
<a href="create_new.jsp">Create New Account</a>
</body>
</html>