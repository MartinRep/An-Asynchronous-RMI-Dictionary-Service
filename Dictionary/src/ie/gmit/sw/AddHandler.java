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
@WebServlet(asyncSupported = true, name = "Add-definition", description = "Word quering server", urlPatterns = { "/put" })
public class AddHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArrayBlockingQueue<Job> inQueue;
	int thisJobNumber = 0;
	JobWorkerHandler jobWorkerHandler;

       
	public void init() throws ServletException {
		//ServletContext ctx = getServletContext(); //The servlet context is the application itself.
		//Initialize JobWorersHandler singleton to share resorces across application
		jobWorkerHandler = JobWorkerHandler.init();
		inQueue = JobWorkerHandler.getInQueue();
	}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AddHandler() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * If statement determines state of the page. If user entered requested word or not yet.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out  = response.getWriter();
        String word = request.getParameter("word");
        String definition = request.getParameter("definition");
        out.println("<h1  align=\"center\">Search Dictionary</h1>");
        if(word == null || definition == null) 
        {
        	//Job number indicator
        	thisJobNumber = jobWorkerHandler.getJobNumber();
        	out.println("<div align=\"center\"> <form> <label for=\"word\">Add Word: </label> <input name=\"word\" type=\"text\" placeholder=\"Enter word here\" required autofocus> </br> </br> <label for=\"definition\">Add Definition: </label> <input name=\"definition\" type=\"text\" placeholder=\"Enter definition here\"> <input name=\"jobNumber\" type=\"hidden\" value=\""+thisJobNumber +"\"> <input type=\"submit\" value=\"Submit\"></form> </div>");
			//Home button
			out.print("<p  align=\"center\"><button onclick=\"window.location.href=' /Dictionary/'\">Home</button></p>");
        }
        else
        {
        	thisJobNumber = Integer.parseInt(request.getParameter("jobNumber"));
        	//Put job in a blocking queue
    		try {
				inQueue.put(new Job(thisJobNumber, word, JobType.ADD, definition));
				//Redirect to result polling page
				response.sendRedirect("results?word=" + word + "&jobNumber="+ thisJobNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
        }
        
	}
	/*
	 * Safely destroys ThreadPool to avoid potenial memory leaks.
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy()
	{
		JobWorkerHandler.Shutdown();
	}

}
