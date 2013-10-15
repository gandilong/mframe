package com.thang.tools.model;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.thang.tools.util.JsonUtils;

public class Action {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ActionValues values;
	
	@ModelAttribute
	public void init(HttpServletRequest request,HttpServletResponse response,Model model){
		this.request=request;
		this.response=response;
		this.values=new ActionValues(request);
		model.addAttribute("values", values);
		response.setContentType("text/html;charset=UTF-8");
	}
	
	
	protected void print(Object msg){
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out.print(msg);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	protected void printJSON(Object obj){
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out.print(JsonUtils.toJsonStr(obj));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
    protected void printJSON(String name,Object obj){
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out.print(JsonUtils.toJsonStr(name, obj));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
    
    public HttpServletRequest getRequest() {
		return request;
	}
    
    public HttpServletResponse getResponse() {
		return response;
	}
    
    public ActionValues getValues() {
		return values;
	}
    
    public ActionValues getValues(boolean page) {
    	values.setPage(page);
		return values;
	}
	
}
