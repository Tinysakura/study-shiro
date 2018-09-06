package com.cfh.studyshiro.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 封装的cookieUtil
 * @author Mr.Chen
 * date: 2018年8月4日 下午2:53:01
 */
public class CookieUtil {
	private static final String COOKIE_NAME = "studyshiro";
	
	//将sessionId以指定的键存储在cookie中
	public void setToken(HttpServletResponse response,String token){
		Cookie cookie = new Cookie(COOKIE_NAME, token);
		cookie.setMaxAge(60 * 60 * 24 * 365);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
			
		response.addCookie(cookie);
	}
	
	//读取cookie中的token
	public String getToken(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		
		for(Cookie c:cookies){
			if(c.getName().equals(COOKIE_NAME)){
				return c.getValue();
			}
		}
		
		return null;
	}
	
	//清除cookie中的token
	public void delToken(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		
		for(Cookie c:cookies){
			if(c.getName().equals(COOKIE_NAME)){
				c.setMaxAge(0);//代表删除此cookie
				response.addCookie(c);
				
				return;
			}
		}
	}

	
}
