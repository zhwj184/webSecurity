webSecurity
===========

web安全框架,主要用servlet filter方式覆盖httpServletRequest和HttpServletResponse方式增加一些输入输出的过滤，

主要实现的安全包括：

1. XSS过滤（获取用户输入参数和参数值进行XSS过滤，对Header和cookie value值进行XSS过滤（转码Script标签的< > 符号），

2. 对Response的setStatus(int sc, String sm)方法 sm错误信息进行XSS过滤；

3. 对Header的CLRF进行过滤；

4. 对cookie大小和cookie的白名单进行验证；

5. 对文件上传后缀白名单进行验证；

6. 对只允许POST提交的url进行验证；

7. CSRF攻击 tokenID防御支持；

8. SESSION通过加密存储到cookie支持；

9. 静态资源路径去除../上级目录符号；

使用指南：只需要在web.xml中配置对应的filter即可。

HttpSessionCookitStoreFilter是session存储到cookie的支持，encryKey加密密钥；

DefaultBaseSecurityFilter是默认的安全过滤filter，

securityFilterList可以配置对应的filter；

CookieWhiteListFilter：cookie白名单配置，如果配置这个，则需要配置参数cookieWhiteList；

CsrfTokenCkeckFilter：对post表单提交进行csrf token验证；使用CsrfTokenIdCreator生成csrf tokenid后放入表单还有session中，key名称必须为csrf_开头；为了支持多个form表单；

FileUploadSecurityFilter：文件上传后缀白名单验证,需要配置whitefilePostFixList参数；

FormPostPermitCheckFilter;只允许post提交的url列表，需要配置onlyPostUrlList参数；

	redirectWhiteList：是配置重定向白名单url参数；

StaticFilePathSecurityFilter：url的../上级路径过滤；


	<filter>
		<filter-name>HttpSessionCookitStoreFilter</filter-name>
		<filter-class>org.websecurity.filter.HttpSessionCookitStoreFilter</filter-class>
		<init-param>	
			<param-name>encryKey</param-name>
			<param-value>1234567887654321</param-value>
		</init-param>
	</filter>

	<filter>
		<filter-name>DefaultBaseSecurityFilter</filter-name>
		<filter-class>org.websecurity.DefaultBaseSecurityFilter</filter-class>
		<init-param>
			<param-name>securityFilterList</param-name><!-- ,org.websecurity.filter.CsrfTokenCkeckFilter -->
			<param-value>org.websecurity.filter.CookieWhiteListFilter,org.websecurity.filter.FormPostPermitCheckFilter</param-value>
		</init-param>
		<init-param>
			<param-name>cookieWhiteList</param-name>
			<param-value>id,JESSIONID,name,clrf</param-value>
		</init-param>
		<init-param>
			<param-name>onlyPostUrlList</param-name>
			<param-value>/d/sssecurity, /user/aaa/name*</param-value><!-- 支持正则匹配 -->
		</init-param>
		<init-param>
			<param-name>whitefilePostFixList</param-name>
			<param-value>jpg,png,doc,xls</param-value>
		</init-param>
		<init-param>
			<param-name>encryKey</param-name>
			<param-value>1234567887654321</param-value>
		</init-param>
		<init-param>
			<param-name>redirectWhiteList</param-name>
			<param-value>http://localhost:8080/[0-9A-Za-z]*,http://www.taobao.com/[0-9A-Za-z]*</param-value>
		</init-param>
	</filter>

	<filter-mapping>
		<filter-name>HttpSessionCookitStoreFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<filter-mapping>
		<filter-name>DefaultBaseSecurityFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
