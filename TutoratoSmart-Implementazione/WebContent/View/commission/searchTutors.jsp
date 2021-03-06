<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Ricerca Tutor</title>
	<link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="<%=request.getContextPath() %>/img/favicon.ico" type="image/x-icon">
</head>

<body>
	<%@ include file="/partials/header.jsp" %>
		
	<div class="content text-center" style="width: 50%;margin: 0 auto; margin-top: 50px; border: 2px solid #232F3E; border-radius:8px;">
		<div class="panel">
			<h2>Ricerca Tutor</h2>
			<p class="text-center">Selezionare le date per ricercare i tutor</p>
		</div>
		
		<div class="container-fluid" style="margin: 25px;">
			<form method="POST" action="<%= response.encodeRedirectURL(request.getContextPath() + "/TutorsTutoringSupervision") %>">
				<div class="row row-space">
				  	<div class="row">
				  		<div class="form-group col-lg-6">
			                <label for="startDate" class="control-label">Selezionare la data iniziale del periodo di ricerca</label>
			                <input type="date" class="form-control" id="startDate" name="startDate">
		            	</div>
		        	
						<div class="form-group col-lg-6">
		                	<label for="finishDate" class="control-label">Selezionare la data finale del periodo di ricerca</label>
		                	<input type="date" class="form-control" id="finishDate" name="finishDate">
		            	</div>
				    </div>
				</div>
				
				<div >		        	
		        	<button type="submit" class="btn btn-primary">Cerca</button>
		        </div>
			</form>
		</div>
	</div>
   
    <%@ include file="/partials/footer.jsp" %>
    
    <script src="<%= request.getContextPath() %>/js/searchScript.js"></script>
</body>
</html>