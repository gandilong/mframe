package com.thang.web.system;


import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thang.service.system.ResourceManager;
import com.thang.service.system.RoleManager;
import com.thang.service.system.UserManager;
import com.thang.tools.model.Action;
import com.thang.tools.model.ActionValues;
import com.thang.tools.model.ResultValues;
import com.thang.tools.util.SystemUtils;

@Controller
@RequestMapping("system/user")
public class UserAction extends Action{

	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private ResourceManager resourceManager;
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
		   ResultValues user=userManager.get(values);
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
		
		ActionValues values=getValues(false);
		
		//得到所有角色数据
		List<ResultValues> roles=roleManager.list("system.role.query", values);
		
		//得到所有资源数据
		List<ResultValues> resources=resourceManager.list("system.resource.query", values);
		
		//得到用户拥有的角色数据
		values.put("uid", SystemUtils.getUser().getId());
		List<String> user_roles=roleManager.listObj("getRoleNameByUser", values);
		List<String> user_resources=roleManager.listObj("getResourceNameByUser", values);
		
		values.add("roles", roles);
		values.add("resources", resources);
		
		values.add("user_roles", user_roles);
		values.add("user_resources", user_resources);
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
		List<ResultValues> users=userManager.query(values);
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
	
	/**
	 * 重置用户密码
	 */
	@RequestMapping("resetPassword")
	public void resetPassword(){
		ActionValues values=getValues(false);
		values.put("loginPass",DigestUtils.md5Hex("000000"));
		userManager.update("update sys_user_info set login_pass=:loginPass where id=:id ", values);
	}
	
	/**
	 * 停用或启用 用户
	 */
	@RequestMapping("used")
	public void used(){
		ActionValues values=getValues(false);
		if(1==values.getInt("used")){
			userManager.update("update sys_user_info set used=0 where id=:id ", values);	
		}else{
			userManager.update("update sys_user_info set used=1 where id=:id ",values);
		}
		
	}
	
	@RequestMapping("updateRole")
	public void updateRole(){
		ActionValues values=getValues();
		if(values.isNotEmpty("opt")&&"delete".equals(values.getStr("opt"))){
			userManager.update("delete from sys_user_role_info where role=:rid and user=:uid", values);
		}else{
			userManager.update("insert into sys_user_role_info(role,user)values(:rid,:uid)", values);
		}
	}
	
	@RequestMapping("updateResource")
	public void updateResource(){
		ActionValues values=getValues();
		if(values.isNotEmpty("opt")&&"delete".equals(values.getStr("opt"))){
			userManager.update("delete from sys_user_resource_info where resource=:resid and user=:uid", values);
		}else{
			userManager.update("insert into sys_user_resource_info(resource,user)values(:resid,:uid)", values);
		}
	}
	
}
