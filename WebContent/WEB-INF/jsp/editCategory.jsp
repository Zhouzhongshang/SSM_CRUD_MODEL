<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
 <div style="width:500px;margin:0px auto;text-align:center">
	
	
	<div style="text-align:center;margin-top:40px">
		<!-- 编辑方法跳转到这个页面来 修改然后post提交 -->
		<%-- <form method="post" action="updateCategory">
			分类名称： <input name="name" value="${c.name}" type="text"> <br>
			<input value="${c.id}" name="id"><br>
			<input type="submit" value="提交"><br>
		</form> --%>
		
		 <form method="post" action="../categories/${c.id}"><!-- url的定义目录 -->
            <input type="hidden" name="_method" value="PUT">
            分类名称： <input name="name" value="${c.name}" type="text"> <br><br>
            <input type="submit" value="修改分类">
        </form>

	</div>	
 </div>