package com.cfh.studyshiro.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 一个自定义shiro realm
 * @author Mr.Chen
 * date: 2018年8月3日 下午3:52:28
 */
public class CustomeRealm extends AuthorizingRealm{
	/**
	 * 模拟数据源
	 * 真实情况下数据应该来自配置文件，数据库或缓存
	 */
	Map<String, String> users = new HashMap<String, String>();
	
	public CustomeRealm(){
		users.put("liqian", "12345");
	}
	
	//模拟数据源
	public Set<String> getRoles(String userName){
		//根据用户名查找对应的角色
		//...
		Set<String> roles = new HashSet<String>();
		roles.add("admin");
		roles.add("users");
		
		return roles;
	}
	
	//模拟数据源
	public Set<String> getPermissons(String userName){
		//根据用户名查找对应的权限
		//...
		Set<String> permissons = new HashSet<String>();
		permissons.add("user:delete");
		
		return permissons;
	}

	//授权过程
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		System.out.println("授权");
		String userName = (String) principals.getPrimaryPrincipal();
		
		Set<String> roles = getRoles(userName);
		Set<String> permissions = getPermissons(userName);
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}

	//认证过程
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		//从token中获取相关信息
		String userName = (String)token.getPrincipal();
		
		String passWord = users.get(userName);
		
		if(passWord == null){
			return null;
		}
		
		String credentials = new String((char[])token.getCredentials());
		
		if(passWord.equals(credentials)){
			SimpleAuthenticationInfo authenticationInfo =
					new SimpleAuthenticationInfo(userName,passWord,"CustomeRealm");
			
			//authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));//给密码加盐
			return authenticationInfo;
		}
		
		return null;
	}

}
