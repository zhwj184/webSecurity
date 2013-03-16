webSecurity
===========

web安全框架



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
