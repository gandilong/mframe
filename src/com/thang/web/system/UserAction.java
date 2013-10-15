package com.thang.web.system;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thang.service.system.UserManager;
import com.thang.tools.model.Action;
import com.thang.tools.model.ActionValues;
import com.thang.tools.model.DataValues;

@Controller
@RequestMapping("system/user")
public class UserAction extends Action{

	@Autowired
	private UserManager userManager;
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String loginForm(){
		
		if(!SecurityUtils.getSubject().isAuthenticated()){
			getValues(false).put("error", "3");
			return "login";	
		}
		return "redirect:/main";
	}
	
	/**
	 * 判断登陆账号是否存在
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exist")
	public String exist(){
		ActionValues values=getValues(false);
		if(values.isNotEmpty("id")&&!"0".equals(values.getStr("id"))){
			return "true";
		}else{
		   DataValues user=userManager.get(values);
		   if(null!=user){
			  return "false";
		   }
		}
		return "true";
	}
	
	/**
	 * 系统管理 -->权限-->用户管理列表页面
	 * @return
	 */
	@RequestMapping("list")
	public String list(){
		return "system/user/list";
	}
	
	/**
	 * 系统管理 -->权限-->用户管理表单页面
	 * @return
	 */
	@RequestMapping("form")
	public String form(){
		ActionValues values=getValues(false);
		if(values.isNotEmpty("id")){
			values.putAll(userManager.get(values));
		}
		return "system/user/form";
	}
	
	/**
	 * 获得列表数据
	 */
	@RequestMapping("listData")
	public void listData(){
		ActionValues values=getValues(true);
		List<DataValues> users=userManager.query(values);
		printJSON(users);
	}
	
	/**
	 * 保存表单数据
	 */
	@RequestMapping("formSave")
	public void formSave(){
		ActionValues values=getValues(false);
		if(values.isNotEmpty("id")&&!"0".equals(values.getStr("id"))){
			userManager.toUpate(values);
		}else{
		    userManager.toInsert(values);
		}
		print(0);
	}
	
	/**
	 * 删除一条记录
	 */
	@RequestMapping("formDelete")
	public void formDelete(){
		userManager.toDelete(getValues(false));
		print(0);
	}
	
}
