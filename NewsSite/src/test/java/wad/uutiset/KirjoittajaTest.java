/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.uutiset;

import java.util.ArrayList;
import java.util.List;
import wad.domain.Writer;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wad.domain.Category;
import wad.domain.NewsItem;

/**
 *
 * @author Jolle
 */
public class KirjoittajaTest {
    
    private Category category;
    private Writer writer;
    private NewsItem newsItem;
    
    public KirjoittajaTest() {
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
     public void testGetNimi() {
     
     writer = new Writer();
     writer.setName("Jaakko");
         assertEquals("Jaakko", writer.getName());
     
     }
     
     @Test
     public void testGetUutinen() {
         writer = new Writer();
         
         newsItem = new NewsItem();
         newsItem.setHeading("Hevoskauppa");
         
         List<NewsItem> news = new ArrayList<>();
         news.add(newsItem);
         
         writer.setNews(news);
         
         assertEquals(newsItem, writer.getNews().get(0));
     }
}
