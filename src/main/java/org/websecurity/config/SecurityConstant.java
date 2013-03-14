package org.websecurity.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SecurityConstant {
	
	public static final List<String> cookieWhiteList = new ArrayList<String>();
	
	public static final List<String> onlyPostUrlList = new ArrayList<String>();
	
	public static final List<String> whitefilePostFixList = new ArrayList<String>();
	
	public static final List<Pattern> redirectLocationWhiteList = new ArrayList<Pattern>();

}
