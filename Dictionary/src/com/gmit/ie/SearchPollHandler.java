package com.gmit.ie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchPollHandler
 */
@WebServlet("/response")
public class SearchPollHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out  = response.getWriter();
		String word = request.getAttribute("word").toString();
		int jobNumber = (int) request.getAttribute("jobNumber");
		response.setIntHeader("Refresh", 10);
		out.printf("<p  align=\"center\">Looking for <b>%s</b>, please wait...</p>",word);
		out.println();
		out.printf("<p  align=\"center\">Job Number: <b>%d</b>, please wait...</p>",jobNumber);
	}

}
