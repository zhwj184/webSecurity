package org.websecurity.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssUtil {
	
	private static String xssType = "<script[^>]*?>.*?</script>";
	
	private static Pattern xssPattern = Pattern.compile(xssType);
	
	public static String xssFilter(String input, String filterType){
		if(input == null || input.isEmpty()){
			return input;
		}
		if(filterType == null || !XssFilterTypeEnum.checkValid(filterType)){
			filterType = XssFilterTypeEnum.ESCAPSE.getValue();
		}
		if(filterType.equals(XssFilterTypeEnum.ESCAPSE.getValue())){
			Matcher matcher = xssPattern.matcher(input);
			if(matcher.find()){
				return matcher.group().replace("<", "&lt;").replace(">", "&gt;");
			}
		}
		if(filterType.equals(XssFilterTypeEnum.DELETE.getValue())){
			return input.replaceAll(xssType, "");
		}
		return input;
	}
	
	public static enum XssFilterTypeEnum{
		ESCAPSE("escapse"),
		NO("no"),
		DELETE("delete");
		
		private String value ;
		private XssFilterTypeEnum(String type){
			this.value = type;
		}
		public String getValue(){
			return this.value;
		}
		
		public static boolean checkValid(String type){
			if(type == null){
				return false;
			}
			return (ESCAPSE.getValue().equals(type) || NO.getValue().equals(type) || DELETE.getValue().equals(type));
		}
	}

}

