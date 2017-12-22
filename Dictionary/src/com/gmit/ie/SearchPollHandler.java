package com.gmit.ie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchPollHandler
 */
@WebServlet("/results")
public class SearchPollHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ConcurrentHashMap<Integer, String> outQueue; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchPollHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException 
	{
		outQueue = JobWorkerHandler.getOutQueue();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out  = response.getWriter();
		String word = request.getAttribute("word").toString();
		int jobNumber = (int) request.getAttribute("jobNumber");
		Job job = new Job(jobNumber, word);
		
		if(outQueue.contains(jobNumber))
		{
			//Display results
			String definition = outQueue.get(jobNumber);
			
		} else
		{
			response.setIntHeader("Refresh", 10);
			out.printf("<p  align=\"center\">Looking for <b>%s</b>, please wait...</p>",word);
			out.println();
			out.printf("<p  align=\"center\">Job Number: <b>%d</b></p>",jobNumber);			
		}
	}

}
