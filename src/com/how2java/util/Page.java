package com.how2java.util;
 /**
  * mysql:select * from category_ limit 0,2
  *      :mysql计数是从0开始的，总共两个大小
  * @author Administrator
  *
  */
public class Page {
 
    int start=0;//开始位置 第一页
    int count = 2;
    int last = 0;//最后一页的开始位置 last=total(已知)-count   total-余数
    public int getStart() {
    	//System.out.println("getStart");
        return start;
    }
    public void setStart(int start) {
    	//System.out.println("setpage");
    	/*if(start<0){
    		start=0;
    	}*/
    	/*if(start>last){
    		start=last;
    	}*/
        this.start = start;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getLast() {
        return last;
    }
    public void setLast(int last) {
        this.last = last;
    }
     
    public void caculateLast(int total) {
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count)
            last = total - count;
        // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else
            last = total - total % count;      
    }
 
}