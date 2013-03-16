package org.websecurity.test;


import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class annotation
 */
@WebServlet(urlPatterns={"/security"},initParams={@WebInitParam(name="f", value="valuef"),@WebInitParam(name="g", value="valueg")})
public class MySecurityTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MySecurityTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//xss params filter
		//url:
		System.out.println(request.getParameter("xssparam")); //output:
		System.out.println(request.getParameterMap().toString());
		
		//cookie white list output filter
		System.out.println(request.getCookies().toString());
		response.addCookie(new Cookie("name", "valName"));//valid
		response.addCookie(new Cookie("clrf", "valName\r\n<script>"));//valid
		try{
			response.addCookie(new Cookie("invalidName", "invalidvalName"));//not valid, throw runtimeexception
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//cookie maxsize filter
		response.addCookie(new Cookie("id", ByteBuffer.allocate(4 * 1024 + 2).toString()));//valid
		
		//head security filter
		response.setHeader("aaa\r\nbbb", "ccc\r\\ddd\n");
		
		//session store to cookie
		System.out.println(request.getSession().getAttribute("sescookie"));
		request.getSession().setAttribute("sescookie", "sessioncookiestoretest");
		
		//rediction filter
//		response.sendRedirect("http://www.163.com");//failed
		
		//status filter
		response.setStatus(404, "<script>alert(1)</script>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("hello, world");
	}

}
