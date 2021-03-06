<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% Collection<ActivityTutorBean> activitiesCollection = (Collection<ActivityTutorBean>) request.getAttribute("activitiesCollection");  
       
    if (activitiesCollection == null){
		response.sendRedirect(response.encodeRedirectURL("/TutoratoSmart/ShowRegisterTutoringActivityManagement"));
		return;
	}    
%>

<!DOCTYPE html>
<html>

<%@ page import="java.util.*,project.Utils.Utils,project.Model.ActivityTutorBean,project.Model.TutorBean,project.Model.TutorDAO,project.Model.RegisterDAO,project.Model.RegisterBean" %>

<head>
	<meta charset="UTF-8">
	<title>Registro Tutor</title>
	<link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="<%=request.getContextPath() %>/img/favicon.ico" type="image/x-icon">
	
    <style>
	    table,th,td{
	    	border: 1px solid black;
	    	border-collapse: collapse;
	    	text-align:center;
	    }
	    th {
	    	background-color:#4d94ff;
	     	color: white;
	    }
	    th,td {
	    	padding:8px;
	    }
    </style>   
</head>

<body>
	<%@ include file="/partials/header.jsp" %>
	
	<div class="content text-center" style="width: 70%;margin: 0 auto; margin-top: 50px; border: 2px solid #232F3E; border-radius:8px;">
		<div class="panel">
			<h2 align="center">Registro di tutorato - <%= user.getFirstName() %> <%= user.getLastName() %></h2>
			
			<% 	
				TutorDAO tutorDAO = new TutorDAO();
				TutorBean tutor = tutorDAO.doRetrieveByMail(user.getEmail());
				RegisterDAO registerDAO = new RegisterDAO();
				RegisterBean register = registerDAO.doRetrieveById(tutor.getRegisterId());
			%>
			<table style="width: 95%;margin: 0 auto;margin-bottom: 25px">
				<tr>
					<th class="text-center">Numero registro</th>
					<th class="text-center">Stato</th>
					<th class="text-center">Ore Totali</th>
					<th class="text-center">Ore Convalidate</th>
					<th class="text-center">Percentuale di completamento</th>
				</tr>
				<tr>
					<td><%= register.getIdRegister() %></td>
					<td><%= register.getState() %></td>
					<td><%= register.getTotalHours() %></td>
					<td><%= register.getValidatedHours() %></td>
					<td><%= register.getPercentageComplete() %> %</td>
				</tr>
			</table>
		</div>
				
		<div id="registerDiv">
			<table style="width: 95%;margin: 0 auto;margin-bottom: 25px">
				<tr>					
       			<% if (activitiesCollection.isEmpty()) { %>
					<th class="text-center">Nessuna attivit&aacute registrata!</th>
				<%  } else  { %>
						<th class="text-center">Categoria</th>
		    			<th class="text-center">Data</th>
		    			<th class="text-center">Stato</th>
		    			<th class="text-center">Dettagli</th>
	    		</tr>
					<% 	Iterator<?> it = activitiesCollection.iterator();
					 	for(int i = 0 ; i < activitiesCollection.size() && it.hasNext(); i++) { 
				 			ActivityTutorBean bean = (ActivityTutorBean) it.next(); %>
				 			<tr>
					 			<td><%= bean.getCategory() %></td>
					 			<td><%= bean.getActivityDate() %></td>
					 			<td><%= bean.getState() %></td>
					 			<td><a href ="<%= response.encodeURL(request.getContextPath() + "/ShowActivityTutoringActivityManagement?id=" + bean.getIdActivity()) %>">Mostra</a></td>
					 		</tr>		 
					<%	} 
				} %>			
			</table>
		</div>
		
		<div class="panel"></div>
		<div style="margin-bottom: 25px;">
			<% if (register.getState().equals("Approvato")) { %>
				<input class="btn btn-primary" type="button" value="Genera registro" onclick="generateRegister()">		        	
			<% } else { %>			
				<a href="View/tutor/activity.jsp"><input class="btn btn-success" type="button" value="Aggiungi attivit&aacute"></a>		        	
			<% } %>
		</div>
	</div>   
    
	<%@ include file="/partials/footer.jsp" %>
	
	<div id="registerDivPdf">
		<table class="text-center">
			<tr>
	     		<th width="17%" class="text-center">Categoria</th>
	    		<th class="text-center">Data</th>
	    		<th class="text-center">Orario inizio</th>
	    		<th class="text-center">Orario fine</th>
	    		<th width="30%" class="text-center">Commento</th>
	   		</tr>
			<% 	Iterator<?> it = activitiesCollection.iterator();
			 	for(int i = 0 ; i < activitiesCollection.size() && it.hasNext(); i++) { 
		 			ActivityTutorBean bean = (ActivityTutorBean) it.next(); %>
	 			<tr>
		 			<td width="17%" class="text-center"><%= bean.getCategory() %></td>
		 			<td class="text-center"><%= bean.getActivityDate() %></td>
		 			<td class="text-center"><%= Utils.getTimeAsString(bean.getStartTime()) %></td>
		 			<td class="text-center"><%= Utils.getTimeAsString(bean.getFinishTime()) %></td>
		 			<td width="30%"><%= bean.getDetails() %></td>
		 		</tr>
		 	<% } %>	
		</table>
	</div> 
	
	<script>
		$(document).ready(function(){
			$("#registerDivPdf").hide();
		});
	</script>
	<script src="https://unpkg.com/jspdf@latest/dist/jspdf.min.js"></script>
	<script src="/TutoratoSmart/js/fileGenerationScript.js"></script>
</body>
</html>