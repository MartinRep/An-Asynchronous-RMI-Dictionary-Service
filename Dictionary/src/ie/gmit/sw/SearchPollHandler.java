package ie.gmit.sw;

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
 * This servlet is called when Request is submitted and waiting for response from server.
 * Simple if statment determines if result is back from server or not
 */
@WebServlet("/results")
public class SearchPollHandler extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static ConcurrentHashMap<Integer, String> outQueue; 
    public SearchPollHandler() 
    {
        super();
    }


	public void init(ServletConfig config) throws ServletException 
	{}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Get method receives Job number from SearchHandler after user enters input.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out  = response.getWriter();
		String word = request.getAttribute("word").toString();
		int jobNumber = (int) request.getAttribute("jobNumber");
		// Synchs outQueue HashMap with the rest of the application through JobWorkerHandler Singleton 
		outQueue = JobWorkerHandler.getOutQueue();
		if(outQueue.containsKey(jobNumber))
		{
			//Display results
			String definition = outQueue.get(jobNumber);
			out.printf("<p  align=\"center\"><b>%s</b>: <div style=\"white-space: pre-wrap;\">%s</div></p>",word, definition);
			out.println();
			out.printf("<p  align=\"center\"><button onclick=\"window.location.href=' /Dictionary/'\">Home</button></p>");
			
		} else
		{
			response.setIntHeader("Refresh", 10);
			out.printf("<p  align=\"center\">Looking for <b>%s</b>, please wait...</p>",word);
			out.println();
			out.printf("<p  align=\"center\">Job Number: <b>%d</b></p>",jobNumber);
		}
	}

}
