package com.cfh.studyshiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 自定义的shiroFilter
 * 只要一组权限中满足任何一个即可
 * @author Mr.Chen
 * date: 2018年8月3日 下午8:21:46
 */
public class RolesOrFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		//1.获取请求主体
		Subject subeject = getSubject(request, response);
		
		String[] roles = (String[])mappedValue;
		
		for(String role:roles){
			if(subeject.hasRole(role)){
				return true;
			}
		}
		
		return false;
	}

}
