<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Reset Password</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <link type="text/css" href="<c:url value='/assets/css/bootstrap.min.css' />" rel="stylesheet" />
	<link href="https://fonts.googleapis.com/css?family=Roboto+Condensed&display=swap" rel="stylesheet">
	 <link type="text/css" href="<c:url value='/assets/css/custom.css' />" rel="stylesheet" />
    
	<!-- <style type="text/css">
    #div1{
  display: block;
    }

   #div2{
  display: none;
  }
</style>
 -->
</head>
<body>
	<div class="container" >
		<div class="row">
			<div class="col-md-3 main-section">
				<div class="row">
					<!-- <div class="col-md-12 text-center">
						<img src="image/doctiger-logo.png">
					</div> -->
					<div class="col-md-12 main-section-form" id="div2">
						<form id="form" action="reset" method="post">
							<div class="row">
								<div class="col-md-12">
								<c:if test="${not empty error}">
                                        Error: ${error}
                                  </c:if>
									<div class="form-group">
										<label>Password</label>
										<input type="hidden" name="token" id ="token" value="${resetToken}"  >
										<input type="password" name="password" id="password" placeholder="Enter Password" class="form-control uploadtxt1" required>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label>Confirm Password</label>
										<input type="password" name="confirm_password" id="confirm_password" placeholder="Enter Confirm Password" class="form-control uploadtxt2" required>
									
									</div>
									<div class="form-group">
							          <span class="error" style="color:red"></span><br />
						           </div>
								</div>
								<div class="col-md-12 text-center">
									<div class="form-group">
									<!-- <input class="btn-primary form-control" id="button"
								style="max-width: 100px;" type="submit" value="CHANGE"> -->
										<input type="submit"  class="btn btn-defualt sum-btn btn-block" disabled="disabled" id="submit" value="Set My Password">
									</div>
								</div>
							</div>
						</form>
					</div>
					<!-- <div class="col-md-12 main-section-form" id="div1">
						<form id = "form2" class="" method="">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label>Email Id</label>
										<input type="text" name="emailid" placeholder="Enter Email Id" class="form-control uploadtxt1">
									</div>
								</div>
								
								<div class="col-md-12 text-center">
									<div class="form-group">
									   <button type="button" class="btn btn-defualt" id="btn1">Verify</button>
									</div>
								</div>
							</div>
						</form>
					</div> -->
				</div>
			</div>
		</div>
	</div>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
		var allowsubmit = false;
		$(function(){
			//on keypress 
			$('#confirm_password').keyup(function(e){
				//get values 
				var pass = $('#password').val();
				var confpass = $(this).val();
				
				//check the strings
				
				
				if(pass == confpass){
					//if both are same remove the error and allow to submit
					$('.error').text('');
					allowsubmit = true;
					document.getElementById("submit").disabled = !allowsubmit;
				}else{
					//if not matching show error and not allow to submit
					allowsubmit = false;
					document.getElementById("submit").disabled = !allowsubmit;
					$('.error').text('Password not matching');
					
					
				}
			});
			
			
		});
	</script>

<!-- 
 <script src="<c:url value='/assets/js/custom.js' />" type="text/javascript"></script>
<script type="text/javascript">
$('button').click(function(){ 
  if(this.id == 'btn1'){
  console.log("Enter");
    $('#div2').show();
    $('#div1').hide();
  }else{
    $('#div1').show();
    $('#div2').hide();
  }
})
</script> -->
</body>
</html>