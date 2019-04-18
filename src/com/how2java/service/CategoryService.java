package com.how2java.service;
 
import java.util.List;

import com.how2java.pojo.Category;
import com.how2java.util.Page;
 
public interface CategoryService {
 
    List<Category> list();

	void delete(Category category);

	Category get(int id);

	void updateCategory(Category category);

	void addCategory(Category category);
	
	public List<Category> chaxun(String name);
	
	/**
	 * 事务管理
	 */
	void addTwo();
	 
    void deleteAll();
    
    /*
     * sql分页查询
     * 
     * int total();
    
    List<Category> list(Page page);
    
    */
 
}