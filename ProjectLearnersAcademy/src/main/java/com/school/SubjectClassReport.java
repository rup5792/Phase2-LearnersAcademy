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

/**
 * Servlet implementation class SubjectClassReport
 */
@WebServlet("/SubjectClassReport")
public class SubjectClassReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectClassReport() {
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
			ResultSet rst=stmt.executeQuery("select * from teacher join subjects on teacher.subj_ID=subjects.subj_ID join class on subjects.class_ID=class.class_ID");
			//out.println("Executed a select operation.<br>");
			
			out.println("<html><body>");
			out.println("<h3>Class Report based on subjects:</h3>");
			out.println("<table border=1 style='border-collapse:collapse' cellpadding='12px'><tr><th>Class</th><th>Subject Name</th><th>Taught By</th></tr>");
			while(rst.next())
			{
				String subjName=rst.getString("subj_name");
				String tchName=rst.getString("tch_name");
				int className=rst.getInt("class_name");
				out.println("<tr><td>"+ className +"</td><td>"+ subjName +"</td><td>"+ tchName +"</td></tr>");
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
