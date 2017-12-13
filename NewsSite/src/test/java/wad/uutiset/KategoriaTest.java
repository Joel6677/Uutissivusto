/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.uutiset;


import java.util.ArrayList;
import java.util.List;
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
public class KategoriaTest {
    
    private Category category;
    
    private NewsItem newsItem;
    
    public KategoriaTest() {
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
     public void testSetNews() {
         category = new Category();
         newsItem = new NewsItem();
         newsItem.setHeading("Otsikko");
         
         List<NewsItem> news = new ArrayList<>();
         news.add(newsItem);
         
         category.setNews(news);
         
         assertEquals(newsItem, category.getNews().get(0));
         
     }
}
