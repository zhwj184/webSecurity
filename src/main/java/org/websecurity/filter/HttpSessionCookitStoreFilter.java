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

import org.websecurity.SecurityFilter;
import org.websecurity.session.HttpSessionCookieStore;
import org.websecurity.session.HttpSessionStore;

/**
 * session´æ´¢ÔÚcookieÖÐ
 * @author weijian.zhongwj
 *
 */
public class HttpSessionCookitStoreFilter implements SecurityFilter{

	private String key;


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

}
