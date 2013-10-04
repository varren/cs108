package WebStore.Model;/* User: Peter  Date: 04.10.13  Time: 14:35 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ShoppingCart {
    public static final String ATTRIBUTE_NAME = "Shopping Cart";
    private HashMap<String,Integer> cart = new HashMap<String, Integer>();

    public ShoppingCart(){

    }

    public void addProduct(String id, int quantity) {
        if (cart.containsKey(id))
            cart.put(id, cart.get(id) + quantity);
        else cart.put(id, quantity);
    }
    public void addProduct(String id, String stringQuantity,int defaultQuantity) {
        int quantity;

        try{
            quantity = Integer.parseInt(stringQuantity);
        }catch (Exception e){
            quantity = defaultQuantity;
        }

        if(quantity != 0 ) addProduct(id,quantity);


    }

    public int getQuantityOf(String id) {
        return cart.get(id);
    }
    public Set<String> getProductIDs(){
        return cart.keySet();
    }
    public boolean contains(String id){
        return cart.containsKey(id);
    }
}
