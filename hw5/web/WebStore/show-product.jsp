<%@ page import="WebStore.Model.DataManager" %>
<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 04.10.13
  Time: 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DataManager dm = (DataManager) application.getAttribute(DataManager.ATTRIBUTE_NAME);
    DataManager.Product product =  dm.getProductInfo(request.getParameter("id"));
%>
<html>
<head>
    <title><%=product.name%></title>
</head>
<body>



<h1><%= product.name %></h1>
<img src="<%="/store-images/"+product.img%>" alt="<%=product.name%>">


<form action="ShoppingCartServlet" method="post">
    $<%=product.price%> <input name="productID" type="hidden" value="<%= product.id %>"/>
    <input type="submit" value="Add to cart"/>
</form>

</body>
</html>