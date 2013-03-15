package org.websecurity.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.SecurityFilter;

public class CsrfTokenCkeckFilter implements SecurityFilter{
	
	
	private static final String CSRFTOKEN_PREFIX = "csrf_";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			if(httpRequest.getMethod().equals("POST")){
				String csrfTokenKey = getTokenName(httpRequest);
				long csrfTokenId = (Long) httpRequest.getSession().getAttribute(csrfTokenKey);
				long paramCsrfToken = Long.parseLong(httpRequest.getParameter(csrfTokenKey));
				if(csrfTokenId != paramCsrfToken){
					throw new RuntimeException("post method csrf token not valid.");
				}
			}
			return ;
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenName(HttpServletRequest httpRequest) {
		Iterator<Entry<String,String[]>> iter = httpRequest.getParameterMap().entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,String[]> entry = iter.next();
			if(entry.getKey().startsWith(CSRFTOKEN_PREFIX)){
				return entry.getKey();
			}
		}
		return null;
	}

}
