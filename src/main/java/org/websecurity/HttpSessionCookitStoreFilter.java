package org.websecurity;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.session.HttpSessionCookieStore;
import org.websecurity.session.HttpSessionStore;

/**
 * session´æ´¢ÔÚcookieÖÐ
 * @author weijian.zhongwj
 *
 */
public class HttpSessionCookitStoreFilter implements Filter{

	private String key;

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
			HttpSessionStore httpSessionStore = new HttpSessionCookieStore(httpRequest,httpResponse, key);
			httpSessionStore.deseriable(httpRequest.getSession());
			filterChain.doFilter(httpRequest, httpResponse);
			httpSessionStore.seriable(httpRequest.getSession());
			return ;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String key = filterConfig.getInitParameter("encryKey");
		if(key == null || key.length() != 16){
			throw new ServletException("encrykey(" + key + ") length must be 16");
		}
		this.key = key;
	}

}
