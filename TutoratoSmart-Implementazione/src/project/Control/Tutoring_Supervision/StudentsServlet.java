package project.Control.Tutoring_Supervision;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.Model.StudentBean;
import project.Model.StudentDAO;

/**
 * 
 *  Servlet implementation class StudentsServlet
 *  
 */

@WebServlet("/StudentsTutoringSupervision")
public class StudentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Constructor
	 *
     * @see HttpServlet#HttpServlet()
     */	
	
    public StudentsServlet() {
        super();
    }

    /**
     * Method doGet()
     * 
   	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   	 */
	
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	return;
	}
	
    
    /**
     * Method doPost()
     * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentDAO studentDAO = new StudentDAO();
		Collection<StudentBean> studentsCollection = null;
		
		String startDate = request.getParameter("startDate");
		if (startDate == null || startDate == "") {
			startDate="2019-01-01";
		}
		Date start = Date.valueOf(startDate);
		
		String finishDate = request.getParameter("finishDate");
		if(finishDate == null || finishDate == "") {
			finishDate="2019-12-31";
		}
		Date finish = Date.valueOf(finishDate);
				
		try {
			studentsCollection = studentDAO.doRetrieveAllByDates(null, start, finish);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		if (studentsCollection != null) {
			request.removeAttribute("studentsCollection");
			request.setAttribute("studentsCollection", studentsCollection);
			request.setAttribute("startDate", startDate);
			request.setAttribute("finishDate", finishDate);
		}
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("/View/commission/studentsList.jsp");
		dispatcher.forward(request, response);
	}
}
