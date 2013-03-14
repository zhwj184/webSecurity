package org.websecurity.session;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.websecurity.util.AESUtil;

/**
 * session信息保存在cookie中
 * @author weijian.zhongwj
 *
 */
public class HttpSessionCookieStore implements HttpSessionStore{
	
	private static final String sessionCookieName = "tmp_app";
	
	private static final char sep=(char)1;
	private static final char sep2=(char)2;
	
	private HttpServletRequest httpServletRequest;
	
	private HttpServletResponse httpServletResponse;
	
	private String key;
	
	public HttpSessionCookieStore(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String key){
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		this.key = key;
	}

	@Override
	public void deseriable (HttpSession session) {
		Cookie[] cookies = httpServletRequest.getCookies();
		if(cookies == null || cookies.length == 0){
			return ;
		}
		for(Cookie cookie: cookies){
			if(cookie.getName().equals(sessionCookieName)){
				try {
					String value = AESUtil.Decrypt(cookie.getValue(), key);
					String[] kvs = value.split(sep2 + "");
					if(kvs == null || kvs.length == 0){
						return ;
					}
					for(String kv: kvs){
						String[] param = kv.split(sep + "");
						if(param == null || param.length == 0 || param.length != 2){
							continue;
						}
						session.setAttribute(param[0], param[1]);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void seriable(HttpSession session) {
		StringBuilder sb = new StringBuilder();
		Enumeration<String> enums = session.getAttributeNames();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			sb.append(name +  sep + session.getAttribute(name) + sep2);
		}
		try {
			httpServletResponse.addCookie(getCookie(AESUtil.Encrypt(sb.toString(), key)));
		} catch (Exception e) {
		}
	}

	private Cookie getCookie(String encrypt) {
		Cookie cookie = new Cookie(sessionCookieName, encrypt);
		return cookie;
	}

}
