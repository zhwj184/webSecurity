package org.websecurity.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.SecurityFilter;
import org.websecurity.session.HttpSessionCookieStore;
import org.websecurity.session.HttpSessionStore;

/**
 * session´æ´¢ÔÚcookieÖÐ
 * 
 * @author weijian.zhongwj
 * 
 */
public class HttpSessionCookitStoreFilter implements SecurityFilter {

	private String key;

	@Override
	public void doFilterInvoke(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSessionStore httpSessionStore = new HttpSessionCookieStore(request,
				response, key);
		httpSessionStore.deseriable(request.getSession());
		httpSessionStore.seriable(request.getSession());

	}

}
