package com.how2java.service.impl;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.how2java.mapper.CategoryMapper;
import com.how2java.pojo.Category;
import com.how2java.service.CategoryService;
import com.how2java.util.Page;
 
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