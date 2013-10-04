/* User: Peter  Date: 03.10.13  Time: 11:45 */


import Login.Model.AccountManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountManagerTests {
    private AccountManager am;

    @Before
    public void setUp() throws Exception {
        am = new AccountManager();
        // 2 records just for testing already created in new AccountManager() like this:
        // createNewAccount("Patrick", "1234");
        // createNewAccount("Molly", "FloPup");
    }

    @Test
    public void hasAccountTest(){
        assertTrue(am.hasAccount("Patrick"));
        assertTrue(am.hasAccount("Molly"));
        assertFalse(am.hasAccount("Bob"));
        assertFalse(am.hasAccount("FloPup"));
    }

    @Test
    public void isCorrectPasswordText(){
        assertTrue(am.isCorrectPassword("Patrick", "1234"));
        assertTrue(am.isCorrectPassword("Molly", "FloPup"));
        assertFalse(am.isCorrectPassword("Molly", ""));
        assertFalse(am.isCorrectPassword("Bob", ""));
    }

    @Test
    public void createNewAccountTest(){
        am.createNewAccount("Bob", "1234");
        assertTrue(am.hasAccount("Bob"));
        assertTrue(am.isCorrectPassword("Bob", "1234"));
    }



}
