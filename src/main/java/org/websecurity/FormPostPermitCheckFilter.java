package org.websecurity;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

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
 * 只允许表单post提交
 * @author weijian.zhongwj
 *
 */
public class FormPostPermitCheckFilter implements Filter{

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
			if(!Valid(httpRequest.getRequestURI(), httpRequest.getMethod())){
				throw new RuntimeException("this requestUrI : " + httpRequest.getRequestURI() + " only permit post, but now is " + httpRequest.getMethod());	
			}
		}
		filterChain.doFilter(request, response);
	}
	

	private boolean Valid(String requestURI, String method) {		
		if(!"POST".equalsIgnoreCase(method)){
			for(String patternUri: SecurityConstant.onlyPostUrlList){
				if(Pattern.matches(patternUri, requestURI)){
					return false;		
				}
			}
		}
		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String list = filterConfig.getInitParameter("onlyPostUrlList");
		if(list == null || list.isEmpty()){
			return ;
		}
		String[] onlyPostUrlList = list.split(",");
		SecurityConstant.onlyPostUrlList.addAll(Arrays.asList(onlyPostUrlList));
	}

}
