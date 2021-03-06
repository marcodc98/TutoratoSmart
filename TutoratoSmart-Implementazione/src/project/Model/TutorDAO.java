 package project.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import project.Control.DBConnection;
import project.Utils.Utils;

/**
 * Questa classe � un manager che si occupa di interagire con il database. Gestisce le query riguardanti Tutor
 */
public class TutorDAO  {		
	public TutorDAO() {
		super();
	}

	/** 
	 * @return un ArrayList di tutti i tutor registrati nel database
	 * @throws SQLException lancia un'eccezione SQL
	 */
	public synchronized ArrayList<TutorBean> doRetrieveAll() throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		ArrayList<TutorBean> tutorList = new ArrayList<TutorBean>();
		String sql = "select * from TUTOR,TS_USER WHERE TS_USER.Email = TUTOR.Email";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			TutorBean bean = null;
        	String email = rs.getString("Email");
			String password = rs.getString("Pwd");
			int userRole = rs.getInt("UserRole");
			String firstname = rs.getString("FirstName");
			String lastname = rs.getString("LastName");
			String telephoneNumber = rs.getString("TelephoneNumber");
			String sex = rs.getString("Sex");
			String registrationNumber = rs.getString("RegistrationNumber");
			String state = rs.getString("State");
			Date startDate = rs.getDate("StartDate");
			Date finishDate = rs.getDate("FinishDate");
			String commissionMember = rs.getString("CommissionMember");
			int registerId = rs.getInt("RegisterId");
			
			
			bean = new TutorBean(email, password, userRole, firstname, lastname, telephoneNumber, sex, registrationNumber, startDate, finishDate, commissionMember, state, registerId);
			tutorList.add(bean);
		}
		return tutorList;
	}
	
	/** 
	 * @param mail email del tutor
	 * @return un tutor, tramite mail, registrato nel database
	 * @throws SQLException lancia un'eccezione SQL
	 */
	public synchronized TutorBean doRetrieveByMail(String mail) throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		PreparedStatement preparedStatement = null;
		
		TutorBean bean = new TutorBean();
		
		String selectSql = "SELECT SQL_NO_CACHE * FROM TS_USER,TUTOR WHERE TS_USER.Email = ? AND TS_USER.Email = TUTOR.Email";
		
		try {
			preparedStatement = connection.prepareStatement(selectSql);
			preparedStatement.setString(1, mail);
			
			// System.out.println("Tutor doRetrieveByMail: " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.wasNull()) {
				System.out.println("Errore esecuzione query!");
	        } else {
	        	int count = rs.last() ? rs.getRow() : 0;
	            if (count == 1) {
	            	bean.setEmail(rs.getString("Email"));
					bean.setPwd(rs.getString("Pwd"));
					bean.setRole(rs.getInt("UserRole"));
					bean.setFirstName(rs.getString("FirstName"));
					bean.setLastName(rs.getString("LastName"));
					bean.setTelephoneNumber(rs.getString("TelephoneNumber"));
					bean.setSex(rs.getString("Sex"));
					bean.setRegistrationNumber(rs.getString("RegistrationNumber"));
					bean.setState(rs.getString("State"));
					bean.setStartDate(rs.getDate("StartDate"));
					bean.setFinishDate(rs.getDate("FinishDate"));
					bean.setCommissionMember(rs.getString("CommissionMember"));
					bean.setRegisterId(rs.getInt("RegisterId"));
					
				//	System.out.println("Tutor Trovato con la email!");
	            }
	        }
		} catch (SQLException e) {
			// System.out.println("Utente non trovato!");
			return null;
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		}
		return bean;
	}
	
	/** 
	 * @param bean bean
	 * @param totalHours ore totali
	 * @throws SQLException lancia un'eccezione SQL
	 */
	@SuppressWarnings("resource")
	public synchronized void doSave(TutorBean bean, int totalHours) throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		PreparedStatement preparedStatement = null;
		
		RegisterDAO registerDAO = new RegisterDAO();
		
		String insertSql = "INSERT INTO TS_USER(Email,Pwd,UserRole,FirstName,LastName,TelephoneNumber,Sex,RegistrationNumber) VALUES (?,?,?,?,?,?,?,?)";
		String insertSql2 = "INSERT INTO TUTOR (Email,StartDate,CommissionMember,RegisterId) VALUES (?,?,?,?)";
		
		try {
			connection.setAutoCommit(false);
						
			preparedStatement = connection.prepareStatement(insertSql);
			preparedStatement.setString(1, bean.getEmail());
			preparedStatement.setString(2, Utils.sha256(bean.getPwd()));
			preparedStatement.setInt(3, 2);
			preparedStatement.setString(4, bean.getFirstName());
			preparedStatement.setString(5, bean.getLastName());
			preparedStatement.setString(6, bean.getTelephoneNumber());
			preparedStatement.setString(7, bean.getSex());
			preparedStatement.setString(8, bean.getRegistrationNumber());
			
			// System.out.println("User doSave: "+ preparedStatement.toString());
			
			preparedStatement.executeUpdate();
			
			int idRegister = registerDAO.doSave(totalHours);
			
			preparedStatement = connection.prepareStatement(insertSql2);
			preparedStatement.setString(1, bean.getEmail());			
			preparedStatement.setDate(2, bean.getStartDate());
			preparedStatement.setString(3, bean.getCommissionMember());
			preparedStatement.setInt(4, idRegister);
						
		 	// System.out.println("Tutor doSave: "+ preparedStatement.toString());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		}	
	}
	
	/** 
	 * @param order order
	 * @param startResearchDate data di inizio della ricerca
	 * @param finishResearchDate data di fine della ricerca
	 * @return una Collection di tutor, tramite date di inizio e di fine, registrati nel database
	 * @throws SQLException lancia un'eccezione SQL
	 */
	public synchronized Collection<TutorBean> doRetrieveAllByDates(String order, Date startResearchDate, Date finishResearchDate) throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		PreparedStatement preparedStatement = null;
		
		Collection<TutorBean> list = new LinkedList<TutorBean>();
		
		String selectSql = "SELECT DISTINCT SQL_NO_CACHE * FROM TUTOR,TS_USER WHERE TUTOR.Email = TS_USER.Email && FinishDate >= ? AND FinishDate <= ?";
		
		if(order!=null && !order.equals("")) {
			selectSql +=" ORDER BY " + order;
		}
		
		try {
			preparedStatement = connection.prepareStatement(selectSql);
			preparedStatement.setDate(1, startResearchDate);
			preparedStatement.setDate(2, finishResearchDate);
			
			// System.out.println("Tutor doRetrieveAllByDates: " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				TutorBean bean = new TutorBean();
				bean.setEmail(rs.getString("Email"));
				bean.setPwd(rs.getString("Pwd"));
				bean.setFirstName(rs.getString("FirstName"));
				bean.setLastName(rs.getString("LastName"));
				bean.setTelephoneNumber(rs.getString("TelephoneNumber"));
				bean.setSex(rs.getString("Sex"));
				bean.setRegistrationNumber(rs.getString("RegistrationNumber"));
				bean.setState(rs.getString("State"));
				bean.setStartDate(rs.getDate("StartDate"));
				bean.setFinishDate(rs.getDate("FinishDate"));
				bean.setCommissionMember(rs.getString("CommissionMember"));
				bean.setRegisterId(rs.getInt("RegisterId"));
				
				list.add(bean);
			}			
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		}
		return list;
	}
	
	/** 
	 * @param order order
	 * @return una Collection di tutor, tramite le attivita, registrati nel database
	 * @throws SQLException lancia un'eccezione SQL
	 */
	public synchronized Collection<TutorBean> doRetrieveAllActive(String order) throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		PreparedStatement preparedStatement = null;
		
		Collection<TutorBean> list = new LinkedList<TutorBean>();
		
		String selectSql = "SELECT SQL_NO_CACHE * FROM TUTOR,TS_USER WHERE TS_USER.Email = TUTOR.Email && State = 'Attivo'";
		
		if(order!=null && !order.equals("")) {
			selectSql +=" ORDER BY " + order;
		}
		
		try {
			preparedStatement = connection.prepareStatement(selectSql);
			
			// System.out.println("Tutor doRetrieveAllActive: " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				TutorBean bean = new TutorBean();
				bean.setEmail(rs.getString("Email"));
				bean.setPwd(rs.getString("Pwd"));
				bean.setFirstName(rs.getString("FirstName"));
				bean.setLastName(rs.getString("LastName"));
				bean.setTelephoneNumber(rs.getString("TelephoneNumber"));
				bean.setSex(rs.getString("Sex"));
				bean.setRegistrationNumber(rs.getString("RegistrationNumber"));
				bean.setState(rs.getString("State"));
				bean.setStartDate(rs.getDate("StartDate"));
				bean.setFinishDate(rs.getDate("FinishDate"));
				bean.setCommissionMember(rs.getString("CommissionMember"));
				bean.setRegisterId(rs.getInt("RegisterId"));
				
				list.add(bean);
			}			
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		}
		return list;
	}

	public TutorBean doRetrieveByRegisterId(int idRegister) throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		PreparedStatement preparedStatement = null;
		
		TutorBean bean = new TutorBean();
		
		String selectSql = "SELECT SQL_NO_CACHE * FROM TUTOR WHERE TUTOR.RegisterId = ?";
		
		try {
			preparedStatement = connection.prepareStatement(selectSql);
			preparedStatement.setInt(1, idRegister);
			
			// System.out.println("Tutor doRetrieveByRegisterId: " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.wasNull()) {
				System.out.println("Errore esecuzione query!");
	        } else {
	        	int count = rs.last() ? rs.getRow() : 0;
	            if (count == 1) {
	            	bean.setEmail(rs.getString("Email"));
					bean.setState(rs.getString("State"));
					bean.setStartDate(rs.getDate("StartDate"));
					bean.setFinishDate(rs.getDate("FinishDate"));
					bean.setCommissionMember(rs.getString("CommissionMember"));
					bean.setRegisterId(rs.getInt("RegisterId"));
					
					// System.out.println("Tutor Trovato con l'id del registro!");
	            }
	        }
		} catch (SQLException e) {
			// System.out.println(e.getMessage());
			return null;
		} finally {
			if(preparedStatement != null)
				preparedStatement.close();
		}
		return bean;
	}

	@SuppressWarnings("resource")
	public void doUpdateState(TutorBean tutor) throws SQLException {
		Connection connection = DBConnection.getInstance().getConn();
		PreparedStatement preparedStatement = null;
		
		String selectDate = "SELECT MAX(ActivityDate) AS ActivityDate FROM ACTIVITY_TUTOR WHERE RegisterId = ? AND State = 'Convalidata'";
		String updateSql = "UPDATE TUTOR SET State='Non Attivo',FinishDate = ? WHERE Email = ?";		
		
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(selectDate);
			preparedStatement.setInt(1, tutor.getRegisterId());
			
			ResultSet rs = preparedStatement.executeQuery();
			Date maxDate = null;
			
			while(rs.next()) {
				maxDate = rs.getDate("ActivityDate");				
			}
			
			preparedStatement = connection.prepareStatement(updateSql);
			preparedStatement.setDate(1, maxDate);
			preparedStatement.setString(2, tutor.getEmail());
			
			// System.out.println("Tutor doUpdate: " + preparedStatement.toString());
			preparedStatement.executeUpdate();
			
			connection.commit();
		}
		finally {
			if(preparedStatement != null)
				preparedStatement.close();
		}		
	}
}
