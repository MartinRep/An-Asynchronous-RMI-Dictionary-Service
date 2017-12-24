package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Dictionary. This Servlet is a Facade for Find Directory Service
 * On Initialization it calls JobWorkerHandler singleton to initialize inQueue and outQueue 
 */
@WebServlet(asyncSupported = true, name = "Find-definition", description = "Word quering server", urlPatterns = { "/get" })
public class SearchHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayBlockingQueue<Job> inQueue;
	private int thisJobNumber = 0;
	private JobWorkerHandler jobWorkerHandler;

       
	public void init() throws ServletException {
		//ServletContext ctx = getServletContext(); //The servlet context is the application itself.
		//Initialize JobWorersHandler singleton to share resorces across application
		jobWorkerHandler = JobWorkerHandler.init();
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
	 * If statement determines state of the page. If user entered requested word or not yet.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inQueue=JobWorkerHandler.getInQueue();
		response.setContentType("text/html");
        PrintWriter out  = response.getWriter();
        String word = request.getParameter("word");
        out.println("<h1  align=\"center\">Search Dictionary</h1>");        
        if(word == null) 
        {
        	//Job number indicator
			thisJobNumber = jobWorkerHandler.getJobNumber();
        	out.println("<div align=\"center\"> <form> <label for=\"word\">Search for Word: </label> <input name=\"word\" type=\"text\" placeholder=\"Enter word here\" required autofocus> <input name=\"jobNumber\" type=\"hidden\" value=\""+thisJobNumber +"\"> <input type=\"submit\" value=\"Submit\">  </form> </div>");
			//Home button
			out.print("<p  align=\"center\"><button onclick=\"window.location.href=' /Dictionary/'\">Home</button></p>");
		    
        }
        else
        {
        	thisJobNumber = Integer.parseInt(request.getParameter("jobNumber"));
        	//Put job in a blocking queue
    		try {
				inQueue.put(new Job(thisJobNumber, word, JobType.GET));
				//Redirect to result polling page
	        	response.sendRedirect("results?word=" + word + "&jobNumber="+ thisJobNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
        }
        
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
	/*
	 * Safely destroys ThreadPool to avoid potential memory leaks.
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy()
	{
		JobWorkerHandler.Shutdown();
	}

}
