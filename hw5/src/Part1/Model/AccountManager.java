package Part1.Model;

import java.util.HashMap;

public class AccountManager {
    public static final String ATTRIBUTE_NAME = "Account Manager";
    private HashMap<String, String> data;

    public AccountManager(){
        data = new HashMap<String, String>();
        // 2 records just for testing
        createNewAccount("Patrick", "1234");
        createNewAccount("Molly", "FloPup");
    }

    public boolean hasAccount(String name){
        return data.containsKey(name);
    }

    public boolean isCorrectPassword(String name, String password){
        return hasAccount(name) && data.get(name).equals(password);
    }

    public void createNewAccount(String name, String password){
        data.put(name,password);
    }

}
