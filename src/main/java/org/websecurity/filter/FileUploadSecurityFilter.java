package org.websecurity.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.websecurity.SecurityFilter;
import org.websecurity.config.SecurityConstant;

/**
 * 文件上传安全过滤
 * @author weijian.zhongwj
 *
 */
public class FileUploadSecurityFilter implements SecurityFilter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			filterChain.doFilter(new UploadFileExtensionFilterHttpServletRequest(httpRequest), httpResponse);
			return ;
		}
		filterChain.doFilter(request, response);
	}
	

	public class UploadFileExtensionFilterHttpServletRequest extends HttpServletRequestWrapper{

		public UploadFileExtensionFilterHttpServletRequest(HttpServletRequest request) {
			super(request);
		}
		@Override
		public Collection<Part> getParts() throws IOException, ServletException {
			Collection<Part> parts = super.getParts();
			if(parts == null || parts.isEmpty()){
				return parts;
			}
			List<Part> resParts = new ArrayList<Part>();
			for(Part part: parts){
				for(String extension: SecurityConstant.whitefilePostFixList){
					if(part.getName().toUpperCase().endsWith(extension)){
						resParts.add(part);
					}
				}
			}
			return resParts;
		}
		@Override
		public Part getPart(String name) throws IOException, ServletException {
			Part part = super.getPart(name);
			for(String extension: SecurityConstant.whitefilePostFixList){
				if(part.getName().toUpperCase().endsWith(extension)){
					return part;
				}
			}
			return null;
		}
	}

}
