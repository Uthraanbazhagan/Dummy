package com.lti.training.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lti.training.exception.DataAccessException;
import com.lti.training.model.Product;
import com.lti.training.model.ProductDao;
// com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

@WebServlet("/ProductControllerServlet")
public class ProductControllerServlet extends HttpServlet {
	
	int pgsize=5;
	int currentPosition=0;
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page=request.getParameter("page");
		if(page!=null) {
			if(page.equals("next"))
				currentPosition+=pgsize;
			else if(page.equals("prev"))
				currentPosition-=pgsize;
		}
		else
			currentPosition=0;
		ProductDao dao=new ProductDao();
		try {
		List<Product> product=dao.fetchProducts(currentPosition, currentPosition+pgsize);
		HttpSession session=request.getSession();
		session.setAttribute("current5products", product);
		response.sendRedirect("viewProducts.jsp");
		}
		catch(DataAccessException e) {
			throw new ServletException("product controllerservlet encounterd"+"problem while accessing dao",e);
		}
		
	}

	
	

}
