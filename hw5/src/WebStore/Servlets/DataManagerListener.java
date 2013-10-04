package WebStore.Servlets;/* User: Peter  Date: 04.10.13  Time: 11:04 */

import WebStore.Model.DataManager;
import WebStore.Model.ShoppingCart;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;


@WebListener()
public class DataManagerListener implements ServletContextListener,HttpSessionListener {

    // Public constructor is required by servlet spec
    public DataManagerListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      ServletContext context = sce.getServletContext();
       context.setAttribute(DataManager.ATTRIBUTE_NAME, new DataManager());
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
       httpSessionEvent.getSession().setAttribute(ShoppingCart.ATTRIBUTE_NAME, new ShoppingCart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
