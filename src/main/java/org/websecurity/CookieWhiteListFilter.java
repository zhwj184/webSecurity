package org.websecurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * cookie白名单过滤,输入输出cookie白名单验证过滤
 * @author weijian.zhongwj
 *
 */
public class CookieWhiteListFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			filterChain.doFilter(new CookieWhiteFilterHttpServletRequest(httpRequest), new CookieWhiteFilterHttpServletResponse(httpResponse));
			return ;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String list = filterConfig.getInitParameter("cookieWhiteList");
		if(list == null || list.isEmpty()){
			return ;
		}
		String[] cookieList = list.split(",");
		SecurityConstant.cookieWhiteList.addAll(Arrays.asList(cookieList));
	}
	private Cookie[] filter(Cookie[] cookies) {
		if(cookies == null || cookies.length == 0){
			return null;
		}
		List<Cookie> cookieList = new ArrayList<Cookie>();
		for(Cookie cookie: cookies){
			if(isInWhiteList(cookie)){
				cookieList.add(cookie);
			}
		}
		return cookieList.toArray(new Cookie[cookieList.size()]);
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
	
	public class CookieWhiteFilterHttpServletRequest extends HttpServletRequestWrapper{

		public CookieWhiteFilterHttpServletRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Cookie[] getCookies() {
			return filter(super.getCookies());
		}

	}
	
	public class CookieWhiteFilterHttpServletResponse extends HttpServletResponseWrapper{

		public CookieWhiteFilterHttpServletResponse(HttpServletResponse response) {
			super(response);
		}
		
		@Override
		public void addCookie(Cookie cookie) {
			if(!isInWhiteList(cookie)){
				throw new RuntimeException("cookie:" + cookie.getName() + " is not in whitelist,not valid.");
			}
			super.addCookie(cookie);
		}	
	}

}
