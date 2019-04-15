package com.how2java.test;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import com.how2java.service.CategoryService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisTest {
 
    @Autowired
    private CategoryService categoryService;
 
    @Test
    public void testAddTwo() {
        categoryService.deleteAll();
        categoryService.addTwo();
         
    }
 
}