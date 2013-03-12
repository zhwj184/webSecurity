package org.websecurity;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SecurityHttpServletRequest extends HttpServletRequestWrapper{

	public SecurityHttpServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		// xss security filter
		return super.getParameter(name);
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		// xss security filter
		return super.getParameterMap();
	}
	@Override
	public Enumeration<String> getParameterNames() {
		// xss security filter
		return super.getParameterNames();
	}
	@Override
	public String[] getParameterValues(String name) {
		// xss security filter
		return super.getParameterValues(name);
	}
}
