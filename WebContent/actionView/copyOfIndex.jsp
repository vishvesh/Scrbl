<!DOCTYPE html>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title> MeetYourMakerz!</title >
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<script src="http://code.jquery.com/jquery-1.7.2.min.js" ></script>
<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js" ></script>
<%-- <script type = "text/javascript" src="http://l2.io/ip.js?var=c"></script> --%> 
<%-- <script src="js/home.js" ></script> --%>
<link rel="stylesheet" type="text/css" href="css/home.css">
<style type="text/css">
#wrapper{
  position:absolute;
  left:36%;
  top: 36px;
}
#submitButton {
  color: #900;
  background: #FF0;
  font-weight: bold;
  border: 1px solid #900;
}
 
#submitButton:hover {
  color: #FFF;
  background: #900;
  cursor: pointer;
}

.errorClass {
	color: red;
	font-family: Comic Sans MS;
}
</style>
<script type="text/javascript">
	/* $(document).ready(function() {
   });  */
	
	function checkVal() {
		var userEmail = $('#userEmail').val();
		
		
		if(userEmail == '' || userEmail.length == 0) {
			$('#error').html("Email Cannot Be Blank!").addClass('errorClass');
			return false;
		}
		else if($.trim(userEmail) == '') {
			$('#error').html("Umm! So many Spaces?").addClass('errorClass');
			return false;
		}
		else if(!IsEmail(userEmail)) {
			$('#error').html("Please Enter a Valid Email").addClass('errorClass');
			return false;
		}
		else {
			//$('#userEmailForm').submit();
			document.forms["userEmailForm"].submit();
		}
		//return false;
	}
	
	function IsEmail(email) {
	  var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
	}
</script>
</head>
<body>

	<div id="container" style="text-align:center">
	  <div id="wrapper">
			<s:form action="saveEmail" id="userEmailForm" style="text-align:center" onsubmit="return false;">
				<s:textfield label="Enter Email " type="text" name="userEmail" id="userEmail"/>
				<s:submit type="button" id="submitButton" value="Save" onclick="checkVal();"/>
				<div id="error"></div>
			</s:form>
		</div>
		
	</div>

</body>
</html>