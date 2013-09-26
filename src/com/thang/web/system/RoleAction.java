package com.thang.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thang.entity.system.Role;
import com.thang.service.system.RoleManager;
import com.thang.tools.model.Action;
import com.thang.tools.model.ActionValues;

@Controller
@RequestMapping("system/role")
public class RoleAction extends Action{

	@Autowired
	private RoleManager roleManager;
	
	/**
	 * 系统管理 -->权限-->角色管理
	 * @return
	 */
	@RequestMapping("list")
	public String list(){
		return "system/role/list";
	}
	
	/**
	 * 系统管理 -->权限-->角色管理
	 * @return
	 */
	@RequestMapping("form")
	public String form(){
		return "system/role/form";
	}
	
	@RequestMapping("listData")
	public void listData(){
		ActionValues values=getValues();
		List<Role> roles=roleManager.query(values);
		printJSON(roles);
	}
	
}