package com.lti.training.servlet;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lti.manager.DatabaseUserManager;
import com.lti.manager.InMemoryUserManager;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.lti")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username=request.getParameter("username");
		String password=request.getParameter("password");
	
			String rememberMe =request.getParameter("rememberMe");
			DatabaseUserManager mgr= new DatabaseUserManager();
	//		InMemoryUserManager mgr=new InMemoryUserManager();
			boolean isValid= mgr.isValidUser(username,password);
			if(isValid) {
				String usernameEncode = Base64.getEncoder().encodeToString(username.getBytes()); 
				 
				String passwordEncode = Base64.getEncoder().encodeToString(password.getBytes()); 
				
				Cookie c1=new Cookie("tom",usernameEncode);
				Cookie c2=new Cookie("jerry",passwordEncode);
				c1.setMaxAge(60*60);
				c2.setMaxAge(60*60);
				response.addCookie(c1);
				response.addCookie(c2);;
				
			
		
			response.sendRedirect("welcome.html");
	}
		else
			response.sendRedirect("login.html");
	}
}

