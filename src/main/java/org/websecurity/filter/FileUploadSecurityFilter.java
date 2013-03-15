package org.websecurity.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
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
	public void doFilterInvoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request = new UploadFileExtensionFilterHttpServletRequest(request);
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
