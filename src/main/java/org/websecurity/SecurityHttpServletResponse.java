package org.websecurity;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author weijian.zhongwj
 *
 */
public class SecurityHttpServletResponse extends HttpServletResponseWrapper{

	public SecurityHttpServletResponse(HttpServletResponse response) {
		super(response);
	}
	
	@Override
	public void addCookie(Cookie cookie) {
		//cookie security filter
		super.addCookie(cookie);
	}
	
	@Override
	public void setDateHeader(String name, long date) {
		// header security filter
		super.setDateHeader(name, date);
	}
	@Override
	public void setIntHeader(String name, int value) {
		// header security filter
		super.setIntHeader(name, value);
	}
	
	@Override
	public void addHeader(String name, String value) {
		// header security filter
		super.addHeader(name, value);
	}
	
	@Override
	public void setHeader(String name, String value) {
		// header security filter
		super.setHeader(name, value);
	}
	
	@Override
	public void sendRedirect(String location) throws IOException {
		// redirect security filter
		super.sendRedirect(location);
	}
	
	@Override
	public void setStatus(int sc, String sm) {
		//status security filter
		super.setStatus(sc, sm);
	}
	

}
