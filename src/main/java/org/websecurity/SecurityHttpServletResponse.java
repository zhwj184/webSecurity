package org.websecurity;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.websecurity.config.SecurityConstant;
import org.websecurity.util.ResponseHeaderSecurityCheck;
import org.websecurity.util.XssUtil;

/**
 * @author weijian.zhongwj
 *
 */
public class SecurityHttpServletResponse extends HttpServletResponseWrapper{
	
	private static final int MAX_COOKIE_SIZE = 4 * 1024;
	
	private int length = 0;

	public SecurityHttpServletResponse(HttpServletResponse response) {
		super(response);
	}
	
	@Override
	public void addCookie(Cookie cookie) {
		if(length + cookie.getValue().length() > MAX_COOKIE_SIZE){
			//
			return ;
		}
		if(!isInWhiteList(cookie)){
			throw new RuntimeException("cookie:" + cookie.getName() + " is not in whitelist,not valid.");
		}
		super.addCookie(ResponseHeaderSecurityCheck.checkCookie(cookie));
		length +=  cookie.getValue().length();
	}
	
	@Override
	public void setDateHeader(String name, long date) {
		super.setDateHeader(ResponseHeaderSecurityCheck.filterCLRF(name), date);
	}
	@Override
	public void setIntHeader(String name, int value) {
		super.setIntHeader(ResponseHeaderSecurityCheck.filterCLRF(name), value);
	}
	
	@Override
	public void addHeader(String name, String value) {
		super.addHeader(ResponseHeaderSecurityCheck.filterCLRF(name), XssUtil.xssFilter(ResponseHeaderSecurityCheck.filterCLRF(value), null));
	}
	
	@Override
	public void setHeader(String name, String value) {
		super.setHeader(ResponseHeaderSecurityCheck.filterCLRF(name), XssUtil.xssFilter(ResponseHeaderSecurityCheck.filterCLRF(value), null));
	}
	
	@Override
	public void sendRedirect(String location) throws IOException {
		if(!ResponseHeaderSecurityCheck.checkRedirectValid(location)){
			throw new RuntimeException("redirect location " + location + " is not valid.");
		}
		super.sendRedirect(location);
	}
	
	@Override
	public void setStatus(int sc, String sm) {
		super.setStatus(sc, XssUtil.xssFilter(sm, null));
	}	
	private boolean isInWhiteList(Cookie cookie) {
		if(cookie == null || cookie.getName() == null){
			return false;
		}
		for(String name : SecurityConstant.cookieWhiteList){
			if(name.equalsIgnoreCase(cookie.getName())){
				return true;
			}
		}
		return false;
	}

}
