package org.websecurity;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.config.SecurityConstant;

/**
 * 
 * @author weijian.zhongwj
 *
 */
public class DefaultBaseSecurityFilter implements Filter{

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
			filterChain.doFilter(new SecurityHttpServletRequest(httpRequest), new SecurityHttpServletResponse(httpResponse));
			return ;
		}
		filterChain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initCookieWhiteList(filterConfig);
		initWhitefilePostFixList(filterConfig);
		initOnlyPostUrlList(filterConfig);
		initCookieStoreEncryKey(filterConfig);
	}
	
	public void initCookieWhiteList(FilterConfig filterConfig) throws ServletException {
		String list = filterConfig.getInitParameter("cookieWhiteList");
		if(list == null || list.isEmpty()){
			return ;
		}
		String[] cookieList = list.split(",");
		SecurityConstant.cookieWhiteList.addAll(Arrays.asList(cookieList));
	}
	
	public void initWhitefilePostFixList(FilterConfig filterConfig) throws ServletException {
		String list = filterConfig.getInitParameter("whitefilePostFixList");
		if(list == null || list.isEmpty()){
			return ;
		}
		String[] cookieList = list.split(",");
		SecurityConstant.whitefilePostFixList.addAll(Arrays.asList(cookieList));
	}
	
	public void initOnlyPostUrlList(FilterConfig filterConfig) throws ServletException {
		String list = filterConfig.getInitParameter("onlyPostUrlList");
		if(list == null || list.isEmpty()){
			return ;
		}
		String[] onlyPostUrlList = list.split(",");
		SecurityConstant.onlyPostUrlList.addAll(Arrays.asList(onlyPostUrlList));
	}


	public void initCookieStoreEncryKey(FilterConfig filterConfig) throws ServletException {
		String key = filterConfig.getInitParameter("encryKey");
		if(key == null || key.length() != 16){
			throw new ServletException("encrykey(" + key + ") length must be 16");
		}
		SecurityConstant.key = key;
	}
}
