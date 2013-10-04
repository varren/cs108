package WebStore.Model;/* User: Peter  Date: 04.10.13  Time: 10:40 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class DataManager {
    public static final String ATTRIBUTE_NAME = "Data Manager";
    public class Product{
        public String id;
        public String name;
        public String img;
        public BigDecimal price;

    }

    public DataManager(){

    }

    public Product getProductInfo(String productID){
        Product product = null;
        Connection con = MyDBDemo.getConnection();

        try {
            String query = "SELECT * FROM products WHERE productid = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, productID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.id = (String) rs.getObject(1);
                product.name = (String) rs.getObject(2);
                product.img = (String) rs.getObject(3);
                product.price = (BigDecimal) rs.getObject(4);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }
    public ArrayList<Product> getProductsList(){
        return getProductsList(null);
    }

    public ArrayList<Product> getProductsList(Set<String> idSet){


        ArrayList<Product> result = new ArrayList<Product>();
        Connection con = MyDBDemo.getConnection();
        try {
            String query = "SELECT * FROM products";
            if(idSet!=null) {
                query+=" WHERE productid IN (";
                String ids = "";
                for(String id:idSet){
                    ids+= "'" + id +"',"  ;
                }

                query+= ids.substring(0, ids.length() - 1) + ")";
            }

            query+=";";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Product product = new Product();

                product.id = (String) rs.getObject(1);
                product.name = (String) rs.getObject(2);
                product.img = (String) rs.getObject(3);
                product.price = (BigDecimal) rs.getObject(4);

                result.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}
