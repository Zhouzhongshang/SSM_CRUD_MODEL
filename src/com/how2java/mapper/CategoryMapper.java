package com.how2java.mapper;
  
import java.util.List;
 
import com.how2java.pojo.Category;
import com.how2java.util.Page;
  
/**
 * Mapper中传进来的参数为对象，其属性可以直接拿到
 * 例如：page  #{start} #{count}
 *      category #{id}
 * @author Administrator
 *
 */
public interface CategoryMapper {
  
    public int add(Category category); 
        
    public void delete(int id); 
        
    public Category get(int id); 
      
    public int update(Category category);  
        
    public List<Category> list();
    
   /* public List<Category> list(Page page);
     
    public int total(); */
     
}