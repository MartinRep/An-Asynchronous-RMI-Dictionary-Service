package com.gmit.ie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Dictionary
 */
@WebServlet(asyncSupported = true, name = "job-server", description = "Word quering server", urlPatterns = { "/get" })
public class SearchHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init() throws ServletException {
		ServletContext ctx = getServletContext(); //The servlet context is the application itself.
		
		//Reads the value from the <context-param> in web.xml. Any application scope variables 
		//defined in the web.xml can be read in as follows:
		//String environmentalVariable = ctx.getInitParameter("SOME_GLOBAL_OR_ENVIRONMENTAL_VARIABLE"); 
	}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SearchHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
        PrintWriter out  = response.getWriter();
        String word = request.getParameter("word");
        out.println("<h1  align=\"center\">Search Dictionary</h1>");
        if(word == null) 
        {
            out.println("<div align=\"center\"> <form> <label for=\"word\">Search for Word: </label> <input name=\"word\" type=\"text\" placeholder=\"Enter word here\" required autofocus> </form> </div>");
        }
        else
        {
			//Get singleton instance.
        	//Put job in a blocking queue through singleton
        	//redirect to waiting page with job number from singleton
        	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/response");
        	request.setAttribute("word", word);
        	int jobNumber = 123;
        	request.setAttribute("jobNumber", jobNumber);
        	dispatcher.forward(request,response);
        }
        
	}

}
