package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bs.TestLdap;


/**
*
* @author Mitodru Niyogi
*/

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "LoginServlet", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String email = request.getParameter("email");
	   // String password = request.getParameter("password");
	    
	    String email = "vineet";
	    String password = "#Nja@568";


	    boolean flag=TestLdap.Test(email, password);
	    flag=true;
	    System.out.println("fl1"+flag);
	    HttpSession session=request.getSession();  
        session.setAttribute("email",email);  
        session.setAttribute("sperid",request.getParameter("sperid")); 
        System.out.println("Session "+session.getAttribute("email"));
	    
	    if(flag)
	    {
	    	
	    	 RequestDispatcher rd = request.getRequestDispatcher("Welcome.jsp");
	    	    rd.forward(request, response);
	    	    return;
	    //strUrl = SUCCESS;
	    }else{
	    	 response.getWriter().println("<h1>Failure! Invalid Username or Password! Try it again!!</h1>");
	    //   strUrl = FAILURE;
	    }
	
	}

}
