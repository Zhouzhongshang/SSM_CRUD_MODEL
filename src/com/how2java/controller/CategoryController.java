package com.how2java.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.pojo.Category;
import com.how2java.service.CategoryService;
import com.how2java.util.Page;

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
 * 
 * 四：由于spring的RequestParam注解接收的参数是来自于requestHeader中，即请求头
 * ，也就是在url中，格式为xxx?username=123&password=456
 * 
 * ，而RequestBody注解接收的参数则是来自于requestBody中，即请求体中 。
 * 因此综上所述，如果为get请求时，后台接收参数的注解应该为RequestParam，
 * 如果为post请求时，则后台接收参数的注解就是为RequestBody。附上两个例子
 * 
 * 另外，还有一种应用场景，接口规范为resultful风格时，举个例子：如果要获取某个id下此条问题答案的查询次数的话 ，则后台就需要动态获取参数
 * ，其注解为@PathVariable，并且requestMapping中的value应为value="/{id}/queryNum"，
 * 
 * 五：html：放在WebContent目录下 才能获取到
 *     jsp:放在WEVB_INF目录下
 *     
 * @author Administrator
 *
 */
@Controller
@RequestMapping("")
public class CategoryController {

	public static final Logger log = Logger.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;
/**
 * 基于restFUl的增删该查：
 *  增加    categories：            POST     
 *  删除    categories/{id}: Delete
 *  改动    categories/{id}:  PUT
 *  查询    categories:  GET
 *  获取    categories/{name}:  GET
 * @param page
 * @return
 * 
 * 重定向是跳转的
 * 直接写字符串是直接找页面的
 * 方法没定义http 没有post 多半是url有问题， 格式有问题
 */
	@RequestMapping(value="/categories",method=RequestMethod.GET)
	public ModelAndView listCategory(Page page) {// 作为参数会被自动放进去的呢
		ModelAndView mav = new ModelAndView("listCategory");

		PageHelper.offsetPage(page.getStart(), page.getCount());// 分页查询 start
																// count
		List<Category> cs = categoryService.list();
		// int total = categoryService.total(); //总记录数
		int total = (int) new PageInfo<>(cs).getTotal();

		page.caculateLast(total); // 最后一页开始
		log.info("cs：" + cs);
		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;

		// return "listCategory";

	}

	// 删除方法
	@RequestMapping(value="/categories/{id}",method=RequestMethod.DELETE)
	public String delete(Category category) {
		categoryService.delete(category);
		return "redirect:/categories";
	}

	// 编辑方法
	@RequestMapping(value="/categories/{id}",method=RequestMethod.GET)
	public ModelAndView editCategory(Category category) {

		/*
		 * Category c= categoryService.get(category.getId()); return
		 * "editCategory";
		 */

		ModelAndView mav = new ModelAndView("editCategory");
		Category c = categoryService.get(category.getId());
		System.out.println(c.getId() + ":" + c.getName());
		mav.addObject("c", c);
		return mav;
	}

	// 更新方法
	@RequestMapping(value="/categories/{id}",method=RequestMethod.PUT)
	public String updateCategory(Category category) {
		categoryService.updateCategory(category);
		return "redirect:/categories";
	}

	// 添加方法
	@RequestMapping(value="/categories",method=RequestMethod.POST)
	public String addCategory(Category category) {
		categoryService.addCategory(category);
		return "redirect:/categories";
	}
	
	/*//模糊查询的分页
	@RequestMapping(value="/categories/{name}",method=RequestMethod.GET)
	public ModelAndView chaxun(Category category,Page page){
		ModelAndView mav = new ModelAndView("listCategory");
		
		PageHelper.offsetPage(page.getStart(), page.getCount());// 分页查询 start
		log.info("name:"+category.getName());
		List<Category> cs = categoryService.chaxun(category.getName());//模糊查询的结果
		int total = (int) new PageInfo<>(cs).getTotal();
		
		page.caculateLast(total); // 最后一页开始
		log.info("cs：" + cs);
		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
		//return "redirect:/categories";
	}*/
	
	/**
	 * Json
	 * @param category
	 * @return
	 */
	    @ResponseBody
	    @RequestMapping("/submitCategory")
	    public String submitCategory(@RequestBody Category category) { //post提交
	        System.out.println("SSM接受到浏览器提交的json，并转换为Category对象:"+category);
	        Category category2 = new Category();
	        category2.setId(101);
	        category2.setName("成功");
	        Object json = JSONObject.toJSON(category2);
	        return json.toString();
	        /**
	         * JSONObject:key value 形式的
	         */
	    }
	     
	    @ResponseBody
	    @RequestMapping("/getOneCategory")
	    public String getOneCategory() {
	         Category c = new Category();
	         c.setId(100);
	         c.setName("第100个分类");
	         Object json = JSONObject.toJSON(c);
	      /*   JSONObject json= new JSONObject();
	         json.put("category", JSONObject.toJSON(c));//这里传递的是json对象   key  value的形式 category : {"name":"第100个分类"，"id":100}
	        
	         *  json:{"category":{"name":"第100个分类","id":100}}
	         *  json.toJSONString():{"category":{"name":"第100个分类","id":100}}
	         *  json的传输相当于对字符串的传输。
	         
	         System.out.println("json:"+json+"\n"+"json.toJSONString():"+json.toJSONString());*/
	        /* return json.toJSONString();//返回json串  客户端要的是串
	         * return 
	         * 
	         * Syso
*/	         System.out.println(json); 
	         return json.toString();
	         }
	    @ResponseBody
	    @RequestMapping("/getManyCategory")
	    public String getManyCategory() {
	        List<Category> cs = new ArrayList<>();
	        for (int i = 0; i < 10; i++) {
	            Category c = new Category();
	            c.setId(i);
	            c.setName("分类名称:"+i);
	            cs.add(c);
	        }
	 System.out.println("cs:"+cs+"\n"+"JSONObject.toJSON(cs)"+JSONObject.toJSON(cs)+"\n"+"JSONObject.toJSON(cs).toString()"+JSONObject.toJSON(cs).toString());
	        return JSONObject.toJSON(cs).toString();//先转json 再转字符串
	    }

	    public static void main(String[] args) {
			ArrayList<Integer> cs=new ArrayList<>();
		   for (int i = 0; i < 10; i++) {
			cs.add(i);
		}
		   System.out.println("cs"+cs);
		}
	    /**
	     *  ArrayList<Integer>: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]      集合
	     *  List<Category> :  [Category [id=0, name=分类名称:0], Category [id=1, name=分类名称:1], Category [id=2, 
name=分类名称:2], Category [id=3, name=分类名称:3], Category [id=4, name=分类名称:4], Catego
ry [id=5, name=分类名称:5], Category [id=6, name=分类名称:6], Category [id=7, name=分类名称:
7], Category [id=8, name=分类名称:8], Category [id=9, name=分类名称:9]]    集合
         JSONObject.toJSON(cs) :[{"name":"分类名称:0","id":0},{"name":"分类名称:1","id":1},{"name":
"分类名称:2","id":2},{"name":"分类名称:3","id":3},{"name":"分类名称:4","id":4},{"name":"分类名称
:5","id":5},{"name":"分类名称:6","id":6},{"name":"分类名称:7","id":7},{"name":"分类名称:8","
id":8},{"name":"分类名称:9","id":9}]         JSON格式的对象
         JSONObject.toJSON(cs).toString()：[{"name":"分类名称:0","id":0},{"name":"分类名称:1","id":1},{"name":
"分类名称:2","id":2},{"name":"分类名称:3","id":3},{"name":"分类名称:4","id":4},{"name":"分类名称
:5","id":5},{"name":"分类名称:6","id":6},{"name":"分类名称:7","id":7},{"name":"分类名称:8","
id":8},{"name":"分类名称:9","id":9}]         JSON格式的串 返回
	     */
	  /**/
}