package org.websecurity;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.websecurity.util.XssUtil;
import org.websecurity.util.XssUtil.XssFilterTypeEnum;

public class SecurityHttpServletRequest extends HttpServletRequestWrapper{

	public SecurityHttpServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		return XssUtil.xssFilter(super.getParameter(XssUtil.xssFilter(name,XssFilterTypeEnum.DELETE.getValue())), null);
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String,String[]> paramsMap  = super.getParameterMap();
		if(paramsMap == null || paramsMap.isEmpty()){
			return paramsMap;
		}
		Map<String,String[]> resParamsMap = new HashMap<String,String[]>();
		Iterator<Entry<String,String[]>> iter = paramsMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,String[]> entry = iter.next();
			resParamsMap.put((XssUtil.xssFilter(entry.getKey(),XssFilterTypeEnum.DELETE.getValue())), filterList(entry.getValue()));
		}
		return resParamsMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Enumeration<String> enums = super.getParameterNames();
		Vector<String> vec = new Vector<String>();
		while(enums.hasMoreElements()){
			String value = enums.nextElement();
			vec.add(XssUtil.xssFilter(value,null));
		}
		return vec.elements();
	}
	@Override
	public String[] getParameterValues(String name) {
		return filterList(super.getParameterValues(name));
	}
	private String[] filterList(String[] value) {
		if(value == null || value.length == 0){
			return value;
		}
		List<String> resValueList = Arrays.asList(value);
		for(String val: value){
			resValueList.add(XssUtil.xssFilter(val,null));
		}
		return resValueList.toArray(new String[resValueList.size()]);
	}

}
