package project.Control.Request_Management;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.Model.AppointmentBean;
import project.Model.AppointmentDAO;
import project.Model.UserBean;

/**
 * 
 *  Servlet implementation class ShowAppointment
 *  
 */

@WebServlet("/ShowAppointmentRequestManagement")
public class ShowAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Constructor
	 *
     * @see HttpServlet#HttpServlet()
     */	
    
    public ShowAppointmentServlet() {
    	super();
    }
    
    /**
     * Method doGet()
     * 
   	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   	 */
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean tutor = (UserBean) request.getSession(false).getAttribute("user");
		
		AppointmentDAO appointmentDAO = new AppointmentDAO();
		
		int flag = Integer.parseInt(request.getParameter("flag"));	// Flag 1 = visualizza storico appuntamenti di un tutor (loggato)
																	// Flag 2 = dettagli singolo appuntamento.
		
		AppointmentBean appointment = new AppointmentBean();
		
		if (flag == 1) {											// Storico appuntamenti di un tutor
			Collection<AppointmentBean> appointmentsCollection;
			try {
				appointmentsCollection = appointmentDAO.doRetrieveAllByTutor(tutor.getEmail());
				
				request.removeAttribute("appointmentsCollection");
				request.setAttribute("appointmentsCollection", appointmentsCollection);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/View/tutor/appointmentsList.jsp");
		    dispatcher.forward(request, response);		    
		    return;			
		}
		
		else if (flag == 2) {
			String requestId = request.getParameter("id");
			
			int id = -1;
			
			if(requestId != null && requestId != "")
				id = Integer.parseInt(requestId);
		     
		    try {
		    	appointment = appointmentDAO.doRetrieveById(id);
		    	request.getSession(false).removeAttribute("appointment");
				request.getSession(false).setAttribute("appointment", appointment);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/View/tutor/appointmentInfo.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/View/home.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
	}

	/**
     * Method doPost()
     * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return;
	}
}
