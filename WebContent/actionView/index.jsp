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
<%-- <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script> --%>
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
	
		//Supportive function for HTML5 Geolocation!
	    function showLocation(position) {
		  var latitude = position.coords.latitude;
		  var longitude = position.coords.longitude;
		  console.log("Latitude : " + latitude + " Longitude: " + longitude);
		}
	
	  //Supportive function for HTML5 Geolocation!
		function errorHandler(err) {
		  if(err.code == 1) {
			  console.log("Error: Access is denied!");
		  }else if( err.code == 2) {
			  console.log("Error: Position is unavailable!");
		  }
		}
	
		 //Function which routes user to view the pdf on the checkbox toggle!
		 function validate() {
		    var informedConsentCheckbox = document.getElementById('informedConsentCheckbox');
			if (informedConsentCheckbox.checked == 1) {
				//console.log("Checked");
				window.location.href = "submitPersonalInfo.html";
			} else {
				//console.log("Unchecked");
			} 
		}
	 
    $(document).ready(function() {
    	var host = location.host;
   	 	//console.log("Host : "+host);
   	 	
   	 	/**
   	 	* Not using the HTML5 Geolocation as of now.. will incorporate later on!
   	 	*/
   	 	//Try the HTML5 geolocation to get user's location!
   	 	/* if(navigator.geolocation) {
	      // timeout at 60000 milliseconds (60 seconds)
	      //var options = {timeout:60000}; //can add options as a parameter to getCurrentPosition()
	      navigator.geolocation.getCurrentPosition(showLocation, errorHandler);
	      
	   } else {
	      console.log("Sorry, browser does not support geolocation!");
	   } */
   	 
    	$('#pdf').on('click', function() {
  		  window.location.href = "viewInformedConsentPdf.html?host="+host+"";
  	   });	
     });
</script>
</head>
<body>

	<div id="container" style="text-align:center">
	  <div id="wrapper">
			<%-- <s:form action="saveEmail" id="userEmailForm" style="text-align:center" onsubmit="return false;"> --%>
				<%-- <s:textfield label="Enter Email " type="text" name="userEmail" id="userEmail"/> --%>
				<%-- <s:submit type="button" id="submitButton" value="Save" onclick="checkVal();"/> --%>
				<div id="error"></div>
			<%-- </s:form> --%>
			
			<input type = "checkbox" name = "informedConsentCheckbox" id = "informedConsentCheckbox" onclick="validate();"/>
			I am 18 years of age or older and hereby give my consent having read the <a id="pdf" href="javascript:void(0);">Informed Consent Form.</a>
		</div>
		
	</div>

</body>
</html>