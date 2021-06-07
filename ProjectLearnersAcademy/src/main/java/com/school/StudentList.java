package com.school;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.DBConnection;

/**
 * Servlet implementation class StudentList
 */
@WebServlet("/StudentList")
public class StudentList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			
			InputStream in=getServletContext().getResourceAsStream("/WEB-INF/config.properties");
			Properties props=new Properties();
			props.load(in);
			
			DBConnection conn=new DBConnection(props.getProperty("url"),props.getProperty("userid"),props.getProperty("password"));
			//out.println("DB Connection initialized.<br>");
		
			Statement stmt=conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rst=stmt.executeQuery("select * from student join class where student.class_ID=class.class_ID");
			//out.println("Executed a select operation.<br>");
			
			out.println("<html><body>");
			out.println("<h3>List of students:</h3>");
			out.println("<table border=1 style='border-collapse:collapse' cellpadding='12px'><tr><th>Student ID</th><th>Name</th><th>Address</th><th>Age</th><th>Class</th></tr>");
			while(rst.next())
			{
				int stuId=rst.getInt("stu_ID");
				String stuName=rst.getString("stu_name");
				String stuAddress=rst.getString("stu_address");
				int stuAge=rst.getInt("stu_age");
				int className=rst.getInt("class_name");
				out.println("<tr><td>"+ stuId +"</td><td>"+ stuName +"</td><td>"+ stuAddress +"</td><td>"+ stuAge +"</td><td>"+ className +"</td></tr>");
			}
			
			stmt.close();
			out.println("</table><br><br>");
			out.println("<a href='/ProjectLearnersAcademy' style='text-decoration:none;color:#fff;background-color:#0275D8;padding:20px 15px;'>Go Back</a>");
			out.println("</body></html>");
			conn.closeConnection();
			
		}catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
