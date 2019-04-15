package com.how2java.service.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.how2java.mapper.CategoryMapper;
import com.how2java.pojo.Category;
import com.how2java.service.CategoryService;
 
@Service
public class CategoryServiceImpl  implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;
     
    public List<Category> list(){
        return categoryMapper.list();
    }

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		categoryMapper.delete(id);
	}

	@Override
	public Category get(int id) {
		// TODO Auto-generated method stub
		return categoryMapper.get(id);
	}

	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
		categoryMapper.update(category);
	}

	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		categoryMapper.add(category);
	}

	/**
	 * 以下做事务管理  加上事务注解 
	 */
	@Override
	/*@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")*/
	public void addTwo() {
		// TODO Auto-generated method stub
		Category c1 = new Category();
        c1.setName("短的名字");
        categoryMapper.add(c1);
         
        Category c2 = new Category();
        c2.setName("名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,");
        categoryMapper.add(c2);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		List<Category> cs = categoryMapper.list();
		for (Category category : cs) {
			categoryMapper.delete(category.getId());
		}
	}

	/*@Override
	public int total() {
		// TODO Auto-generated method stub
		return categoryMapper.total();
	}

	@Override
	public List<Category> list(Page page) {
		// TODO Auto-generated method stub
		return categoryMapper.list(page);
	}*/
 
}