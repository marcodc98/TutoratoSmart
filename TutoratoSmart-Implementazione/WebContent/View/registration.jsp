<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Registrazione Studente</title>
	<link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="<%=request.getContextPath() %>/img/favicon.ico" type="image/x-icon">
</head>

<body>
	<%@ include file="/partials/header.jsp" %>
	
	<div class="content text-center" style="width: 40%;margin: 0 auto; margin-top: 50px; border: 2px solid #232F3E; border-radius:8px;">
		<div class="panel">
			<h2>Registrazione</h2>
			<p>Compila tutti i campi per registrarti</p>
		</div>
		
		<div class="container-fluid" style="margin: 25px;">
			<form method="POST" action="<%= response.encodeURL("/TutoratoSmart/Registration") %>">
				<div class="row row-space">
					<div class="row">
						<div class="alert alert-warning" id="errorDiv" role="alert" style="display:none;"></div>
					</div>
					
				  	<div class="row">
				  		<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
				  			<label for="Email" class="control-label">Inserire l'email</label>
							<input type="email" class="form-control" id="Email" name="Email" placeholder="Email" maxlength="45" onkeydown="lowerCaseF(this)">
						</div>
						<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
							<label for="RegistrationNumber" class="control-label">Inserire la matricola</label>
							<input class="form-control" id="RegistrationNumber" type="text" name="RegistrationNumber" maxlength="10" placeholder="Matricola" onkeydown="upperCaseF(this)">
						</div>
					</div>
					
					<div class="row">
				  		<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
				  			<label for="Password" class="control-label">Inserire la password</label>
							<input type="password" class="form-control" style="height: 35px;" id="Password" placeholder="Password" name="Password" maxlength="10">
						</div>
						<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
							<label for="VerifyPassword" class="control-label">Confermare la password</label>
							<input type="password" class="form-control" style="height: 35px;" id="VerifyPassword" placeholder="ConfermaPassword" name="VerifyPassword" maxlength="10">
						</div>
				    </div>
				    
				    <div class="row">
				    	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
				    		<label for="FirstName" class="control-label">Inserire il nome</label>
							<input type="text" class="form-control" id="FirstName" name="FirstName" placeholder="Nome" maxlength="30">
						</div>
						<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
							<label for="LastName" class="control-label">Inserire il cognome</label>
		        			<input class="form-control" id="LastName" type="text" name="LastName" placeholder="Cognome" maxlength="30"> 
			    		</div>
				    </div>
				    
				    <div class="row">
				    	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
				    		<label for="TelephoneNumber" class="control-label">Inserire il numero di telefono</label>
							<input class="form-control" id="TelephoneNumber" type="text" name="TelephoneNumber" maxlength="10" placeholder="Numero di Telefono">
						</div>
						<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
							<label for="Sex" class="control-label">Specificare il genere</label><br>
							<label class="radio-inline"> <input type="radio" name="Sex" id="M" value="M" checked> M </label>
							<label class="radio-inline"> <input type="radio" name="Sex" id="F" value="F"> F </label>
						</div>
				    </div>
				</div>  
			  
		    	<div class="text center" style="margin-bottom: 25px;">
					<label for="AcademicYear" class="control-label">Specificare l'anno accademico</label><br>
					<select id="AcademicYear" name="AcademicYear">
						<option value="1">Primo Anno - Triennale</option>
						<option value="2">Secondo Anno - Triennale</option>
						<option value="3">Terzo Anno - Triennale</option>
						<option value="4">Primo Anno - Magistrale</option>
						<option value="5">Secondo Anno - Magistrale</option>
						<option value="6">Fuori Corso</option>
					</select>
				</div>
								
				<div class="row">
					<div class="alert alert-success text-center" id="successDiv" role="alert" style="display:none;margin:25px;margin-top:0px;">Registrazione completata con successo</div>
					<div class="alert alert-danger" id="failureDiv" role="alert" style="display:none;margin:25px;margin-top:0px;">Registrazione fallita</div>		
				</div>
				
				<div class="panel"></div>
				<div style="margin-top: 25px;">
					<input class="btn btn-primary" id="register" type="button" onclick="validateInputs()" name="registraButton" value="Registra">		        	
		        </div>		        
			</form>
		</div>
	</div>
		
	<%@ include file="/partials/footer.jsp" %>
	
	<script src="<%= request.getContextPath() %>/js/validationScript.js"></script>
	<script src="<%= request.getContextPath() %>/js/registrationScript.js"></script>
</body>
</html>
