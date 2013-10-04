package WebStore.Servlets;

import WebStore.Model.ShoppingCart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/* User: Peter  Date: 04.10.13  Time: 12:57 */
@WebServlet(name = "ShoppingCartServlet", urlPatterns = {"/WebStore/ShoppingCartServlet"})
public class ShoppingCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute(ShoppingCart.ATTRIBUTE_NAME);

        //just added new param
        String id =  request.getParameter("productID");

        if (id != null) {
            cart.addProduct(id, 1);

        } else {
            Enumeration<String> params = request.getParameterNames();
            ShoppingCart newCart = new ShoppingCart();
            while(params.hasMoreElements()){
                id = params.nextElement();
                newCart.addProduct(id, request.getParameter(id), cart.getQuantityOf(id));
            }
            cart = newCart;
        }

        session.setAttribute(ShoppingCart.ATTRIBUTE_NAME, cart);

        RequestDispatcher rd = request.getRequestDispatcher("shopping-cart.jsp");
        rd.forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
