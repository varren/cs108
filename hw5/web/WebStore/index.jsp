<%@ page import="WebStore.Model.DataManager" %>
<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 04.10.13
  Time: 9:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students Store</title>
</head>
<body>
    <h1>Students Store</h1>
<p>Items available:</p>
    <%!
        public String liDecorator(String id, String name){
            return "<li><a href=\"show-product.jsp?id=" + id + "\">" + name + "</a></li>";
        }
    %>
<ul>
    <%
        DataManager dm = (DataManager) application.getAttribute(DataManager.ATTRIBUTE_NAME);
        for(DataManager.Product p: dm.getProductsList())
            out.print(liDecorator(p.id, p.name));
    %>

</ul>
</body>
</html>