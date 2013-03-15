package org.websecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.SecurityFilter;

/**
 * 静态文件路径安全过滤
 * @author weijian.zhongwj
 *
 */
public class StaticFilePathSecurityFilter implements SecurityFilter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			filterChain.doFilter(new UriNormalizerHttpServletRequest(httpRequest), httpResponse);
			return ;
		}
		filterChain.doFilter(request, response);
	}


	private String nomalize(String requestURI) {
		if(requestURI == null || requestURI.isEmpty()){
			return requestURI;
		}
		String resourceName = null;
		resourceName = requestURI.replace("\\", "/").replace("..", "").replace(".", "");
		return resourceName;
	}
	private StringBuffer nomalize(StringBuffer requestURL) {
		if(requestURL == null || requestURL.length() == 0){
			return requestURL;
		}
		String resourceName = null;
		resourceName = requestURL.toString().replace("\\", "/").replace("..", "").replace(".", "");
		return new StringBuffer(resourceName);
	}

	
	public class UriNormalizerHttpServletRequest extends HttpServletRequestWrapper{

		public UriNormalizerHttpServletRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getRequestURI() {
			return nomalize(super.getRequestURI());
		}

		@Override
		public StringBuffer getRequestURL() {
			return nomalize(super.getRequestURL());
		}
		@Override
		public String getPathInfo() {
			return nomalize(super.getPathInfo());
		}
		@Override
		public String getContextPath() {
			return nomalize(super.getContextPath());
		}
		@Override
		public String getServletPath() {
			return nomalize(super.getServletPath());
		}
		@Override
		public String getPathTranslated() {
			return nomalize(super.getPathTranslated());
		}

	}

}
