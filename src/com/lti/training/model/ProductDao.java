package com.lti.training.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lti.training.exception.DataAccessException;

public class ProductDao {
	
	public List<Product> fetchProducts(int from, int to) throws DataAccessException{
		Connection conn=null;
		ResultSet rs=null;
	   PreparedStatement stmt=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="hr";
			String pass="hr";
			conn=DriverManager.getConnection(url, user, pass);
			String sql=
					"\r\n" + 
					"SELECT T.* FROM ( \r\n" + 
					"SELECT T.*, rowNum as rowIndex\r\n" + 
					"FROM (\r\n" + 
					"    SELECT *\r\n" + 
					"    FROM tbl_product\r\n" + 
					")T)T\r\n" + 
					"WHERE rowIndex > ? AND rowIndex <= ?";
			stmt=conn.prepareStatement(sql);
			
			stmt.setInt(1,from);
			stmt.setInt(2, to);
			rs=stmt.executeQuery();
			
			List<Product> products=new ArrayList<Product>();
			while(rs.next()) {
				Product p=new Product();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setPrice(rs.getDouble(3));
				p.setQuantity(rs.getInt(4));
				products.add(p);
			}
			System.out.println("hellooooo");
			System.out.println("abccccc");
					return products;
		}

		catch(ClassNotFoundException e) {
			throw new DataAccessException("unable to load JDBC driver");
		}
		catch(SQLException e) {
			throw new DataAccessException("problem while fetching products from DB",e);
		}
		
       finally
       {
    	   try {
    		   conn.close();
    	   }
    	   catch(Exception e){
    		   
    		   
    	   }
       }
	}
}
