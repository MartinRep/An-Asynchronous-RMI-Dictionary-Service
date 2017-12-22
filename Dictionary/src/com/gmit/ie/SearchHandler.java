package com.gmit.ie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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
@WebServlet(asyncSupported = true, name = "Find-definition", description = "Word quering server", urlPatterns = { "/get" })
public class SearchHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArrayBlockingQueue<Job> inQueue = new ArrayBlockingQueue<>(10);
	private static int jobNumber = 0;
	int thisJobNumber = 0;
	private static ConcurrentHashMap<Integer, String> outQueue = new ConcurrentHashMap<>();

       
	public void init() throws ServletException {
		ServletContext ctx = getServletContext(); //The servlet context is the application itself.
		//Initialize JobWorersHandler singleton to share resorces across application
		JobWorkerHandler.init(inQueue, outQueue); 
	}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SearchHandler() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out  = response.getWriter();
        String word = request.getParameter("word");
        out.println("<h1  align=\"center\">Search Dictionary</h1>");
        if(word == null) 
        {
        	jobNumber++;
            thisJobNumber = jobNumber;
        	out.println("<div align=\"center\"> <form> <label for=\"word\">Search for Word: </label> <input name=\"word\" type=\"text\" placeholder=\"Enter word here\" required autofocus> </form> </div>");
        }
        else
        {
        	System.out.println(thisJobNumber);
        	//Put job in a blocking queue
    		try {
				inQueue.put(new Job(thisJobNumber, word));
				//Redirect to result polling page
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/results");
				request.setAttribute("word", word);
	        	request.setAttribute("jobNumber", thisJobNumber);
	        	dispatcher.forward(request,response);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
        }
        
	}
	
	@Override
	public void destroy()
	{
		JobWorkerHandler.Shutdown();
	}

}
