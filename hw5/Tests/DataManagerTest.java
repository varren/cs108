import WebStore.Model.DataManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DataManagerTest {
    private static final int DEFAULT_SQL_INPUT_SIZE = 14;
    private DataManager dm;

    @Before
    public void setUp() throws Exception {
        dm = new DataManager();
    }

    @Test
    public void getProductsListTest() {
        ArrayList<DataManager.Product> data = dm.getProductsList();
        assertTrue(data.size() == DEFAULT_SQL_INPUT_SIZE);
        //("HC","Classic Hoodie","Hoodie.jpg",40),
        assertEquals("HC", data.get(0).id);
        assertEquals("Classic Hoodie", data.get(0).name);
        assertEquals("Hoodie.jpg", data.get(0).img);
        assertEquals(new BigDecimal("40.00"), data.get(0).price);
    }

    @Test
    public void getProductsListWithParamsTest() {
        Set<String> dataFromSet = new HashSet<String>();
        dataFromSet.add("HC");
        dataFromSet.add("TS");

        ArrayList<DataManager.Product> data = dm.getProductsList(dataFromSet);
        assertTrue(data.size() == 2);
        //"TS","Seal Tee","SealTShirt.jpg",19.95
        assertEquals("TS", data.get(1).id);
        assertEquals("Seal Tee", data.get(1).name);
        assertEquals("SealTShirt.jpg", data.get(1).img);
        assertEquals(new BigDecimal("19.95"), data.get(1).price);
    }

    @Test
    public void getProductTest() {
        DataManager.Product product = dm.getProductInfo("HC");
        assertEquals("Classic Hoodie", product.name);
        assertEquals("Hoodie.jpg", product.img);
        assertEquals(new BigDecimal("40.00"), product.price);

        DataManager.Product product2 = dm.getProductInfo("something else");
        assertNull(product2);

        DataManager.Product product3 = dm.getProductInfo("HC11111");
        assertNull(product3);
    }
}
