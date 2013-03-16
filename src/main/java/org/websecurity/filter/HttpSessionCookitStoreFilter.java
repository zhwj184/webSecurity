package org.websecurity.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.config.SecurityConstant;
import org.websecurity.session.HttpSessionCookieStore;
import org.websecurity.session.HttpSessionStore;

/**
 * session´æ´¢ÔÚcookieÖÐ
 * 
 * @author weijian.zhongwj
 * 
 */
public class HttpSessionCookitStoreFilter implements Filter {
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
			HttpSessionStore httpSessionStore = new HttpSessionCookieStore(httpRequest,
					httpResponse, SecurityConstant.key);
			httpSessionStore.deseriable(httpRequest.getSession());
			filterChain.doFilter(request, response);
			httpSessionStore.seriable(httpRequest.getSession());
			return ;
		}
		filterChain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initCookieStoreEncryKey(filterConfig);
	}

	public void initCookieStoreEncryKey(FilterConfig filterConfig) throws ServletException {
		String key = filterConfig.getInitParameter("encryKey");
		if(key == null || key.length() != 16){
			throw new ServletException("encrykey(" + key + ") length must be 16");
		}
		SecurityConstant.key = key;
	}
	

}
