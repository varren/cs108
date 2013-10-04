package Login.Servlets;

import Login.Model.AccountManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AccountCreationListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public AccountCreationListener() {

    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        AccountManager accMan = new AccountManager();
        ServletContext sc = sce.getServletContext();
        sc.setAttribute(AccountManager.ATTRIBUTE_NAME, accMan);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }


}
