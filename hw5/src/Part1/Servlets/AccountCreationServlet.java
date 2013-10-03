package Part1.Servlets;

import Part1.Model.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* User: Peter  Date: 03.10.13  Time: 10:35 */

@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = (AccountManager)getServletContext().getAttribute(AccountManager.ATTRIBUTE_NAME);

        if(!accountManager.hasAccount(request.getParameter("name"))){
            accountManager.createNewAccount(request.getParameter("name"),request.getParameter("password"));
            RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
            rd.forward(request,response);
        } else{
            RequestDispatcher rd = request.getRequestDispatcher("create_fail.jsp");
            rd.forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
