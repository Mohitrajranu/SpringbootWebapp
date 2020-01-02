<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Forgot Password</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="shortcut icon" type="image/png" href="https://bluealgo.com/bluealgo/img/bluealgologo.jpg">
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
	
				<div class="col-md-12 main-section-form" id="div1">
						<form id = "form2" action="forgot" method="post">
							<div class="row">
								<div class="col-md-12">
								<c:if test="${not empty errorMessage}">
                                        Error: ${errorMessage}
                                  </c:if>
                                  <c:if test="${not empty successMessage}">
                                        Success: ${successMessage}
                                  </c:if>
								
								
									<div class="form-group">
										<label>Email Id</label>
										<input type="text" name="email" id="email" placeholder="Enter Email Id" class="form-control uploadtxt1">
									    <input type="hidden" name="projectname" id ="projectname" value="${projectname}"  >
									</div>
								</div>
								
								<div class="col-md-12 text-center">
									<div class="form-group">
									   <input type="submit" class="btn btn-defualt" id="btn1" value="Verify">
									</div>
								</div>
							</div>
						</form>
					</div> 
				</div>
			</div>
		</div>
	</div>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
		
</script>
<script>
    $(document).ready(function(){  

      var checkField;

      //checking the length of the value of message and assigning to a variable(checkField) on load
      checkField = $("input#email").val().length;  

      var enableDisableButton = function(){         
        if(checkField > 0){
          $('#btn1').removeAttr("disabled");
        } 
        else {
          $('#btn1').attr("disabled","disabled");
        }
      }        

      //calling enableDisableButton() function on load
      enableDisableButton();            

      $('input#email').keyup(function(){ 
        //checking the length of the value of message and assigning to the variable(checkField) on keyup
        checkField = $("input#email").val().length;
        //calling enableDisableButton() function on keyup
        enableDisableButton();
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