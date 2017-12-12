/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.uutiset;

import java.io.Writer;
import java.util.Locale.Category;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wad.domain.NewsItem;

/**
 *
 * @author Jolle
 */
public class UutinenTest {
    
    private Category category;
    private Writer writer;
    private NewsItem newsItem;
    
    public UutinenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testOtsikko() {
         newsItem = new NewsItem();
         newsItem.setHeading("Lukekaa seiskaa");
         
         assertEquals("Lukekaa seiskaa", newsItem.getHeading());
     }
     
      @Test
     public void testIngressi() {
         newsItem = new NewsItem();
         newsItem.setLead("Lukekaa seiskaa");
         
         assertEquals("Lukekaa seiskaa", newsItem.getLead());
     }
     
      @Test
     public void testTeksi() {
         newsItem = new NewsItem();
         newsItem.setBody("Lukekaa seiskaa");
         
         assertEquals("Lukekaa seiskaa", newsItem.getBody());
     }
     
     @Test
     public void testCount() {
         newsItem = new NewsItem();
         newsItem.setCount(5);
         
         assertEquals(5,newsItem.getCount());
     }
}
