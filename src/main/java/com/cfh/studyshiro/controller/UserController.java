package com.cfh.studyshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/loginpage")
	public String loginpage(){
		return "loginpage";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(String userName,String passWord){
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(userName,passWord);
		
		subject.getSession().setAttribute("user", "dashabi");
		
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			return e.getMessage();
		}
		
		return "login success";
	}
	
	/**
	 * 测试shiro注解
	 */
	@RequestMapping("/admin")
	@ResponseBody
	//@RequiresRoles("admin")
	public String admin(){
		Subject subject = SecurityUtils.getSubject();
		return (String) subject.getSession().getAttribute("user");
	}
	
	/**
	 * 测试shiro注解
	 */
	@RequestMapping("/users")
	@ResponseBody
	//@RequiresRoles({"users","admin"})
	public String users(){
		return "users see";
	}
	
	/**
	 * 测试shiro注解
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("user:delete")
	public String delete(){
		return "require delete permission";
	}
	
	/**
	 * 测试shiro过滤器
	 */
	@RequestMapping("/perms1")
	@ResponseBody
	public String perms1(){
		return "require delete permission";
	}
	
	/**
	 * 测试shiro过滤器
	 */
	@RequestMapping("/perms2")
	@ResponseBody
	public String perms2(){
		return "require delete permission";
	}

}
