package com.how2java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.pojo.Category;
import com.how2java.service.CategoryService;
import com.how2java.util.Page;
//http://how2j.cn/frontmyQuestion
import sun.awt.dnd.SunDragSourceContextPeer;

// 告诉spring mvc这是一个控制器类
/**
 * 
 * 一： 1. 首先浏览器上访问路径 /listCategory 2.
 * tomcat根据web.xml上的配置信息，拦截到了/listCategory，并将其交由DispatcherServlet处理。 3.
 * DispatcherServlet 根据springMVC的配置，将这次请求交由CategoryController类进行处理，所以需要进行这个类的实例化
 * 4. 在实例化CategoryController的时候，注入CategoryServiceImpl。
 * (自动装配实现了CategoryService接口的的实例，只有CategoryServiceImpl实现了CategoryService接口，
 * 所以就会注入CategoryServiceImpl) 5. 在实例化CategoryServiceImpl的时候，又注入CategoryMapper 6.
 * 根据ApplicationContext.xml中的配置信息，将CategoryMapper和Category.xml关联起来了。 7.
 * 这样拿到了实例化好了的CategoryController,并调用 list 方法 8.
 * 在list方法中，访问CategoryService,并获取数据，并把数据放在"cs"上，接着服务端跳转到listCategory.jsp去 9.
 * 最后在listCategory.jsp 中显示数据
 * 
 * 二： <form action="addProduct"> 产品名称 ：
 * <input type="text" name="name" value=""><br />
 * 产品价格： <input type="text" name="price" value=""><br />
 * <input type="submit" value="增加商品"> </form>
 * 
 * 注： addProduct.jsp 提交的name和price会自动注入到参数 product里 注：
 * 参数product会默认被当做值加入到ModelAndView 中，相当于： <br data-filtered="filtered">
 * mav.addObject("product",product);<br data-filtered="filtered">
 * 
 * <%@ page language="java" contentType="text/html; charset=UTF-8"
 * pageEncoding="UTF-8" isELIgnored="false"%> 产品名称： ${product.name}<br>
 * 产品价格： ${product.price}
 * 
 * 三：这里的page对象之所在前端jsp页面拿到是因为，默认加入了 mav.addObject("page",page)
 * 是的，就是因为提交了start参数。 springmvc框架会自动把start参数注入到Page对象的start属性上。
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("listCategory")
	public ModelAndView listCategory(Page page) {//作为参数会被自动放进去的呢
		ModelAndView mav = new ModelAndView("listCategory");

		PageHelper.offsetPage(page.getStart(), page.getCount());// 分页查询 start count
		List<Category> cs = categoryService.list();
		// int total = categoryService.total(); //总记录数
		int total = (int) new PageInfo<>(cs).getTotal();

		page.caculateLast(total); // 最后一页开始

		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
		
		//return "listCategory";
		
	}
	
	//删除方法
	@RequestMapping("deleteCategory")
	public String delete(int id){
		categoryService.delete(id);
		return "redirect:/listCategory";
	}
	
	//编辑方法
	@RequestMapping("editCategory")
	public ModelAndView editCategory(Category category){
		
	/*	Category c= categoryService.get(category.getId());
		return "editCategory";
		*/
		
		ModelAndView mav = new ModelAndView("editCategory");
		Category c= categoryService.get(category.getId());
		System.out.println(c.getId()+":"+c.getName());
		mav.addObject("c", c);
		return mav;
	}
	
	//更新方法
	@RequestMapping("updateCategory")
	public String updateCategory(Category category){
		categoryService.updateCategory(category);
		return "redirect:/listCategory";
	}

}