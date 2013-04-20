<!DOCTYPE html>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title> MeetYourMakerz!</title >
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<script src="http://code.jquery.com/jquery-1.7.2.min.js" ></script>
<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js" ></script>
<script type = "text/javascript" src="http://l2.io/ip.js?var=c"></script> 
<%-- <script src="js/home.js" ></script> --%>
<link rel="stylesheet" type="text/css" href="css/home.css">
<style>
#wrapper{
     position:absolute;
     left:40%;
}
</style>
</head>
<body>

	<div id="container" style="text-align:center">
	  <!-- s:property value="name"/-->
	  <div id="wrapper">
			<s:form action="scrbl" id="login-form" style="text-align:center">
				<s:textfield label="Enter Email " type="text" name="userEmail" id="userEmail" placeholder = "Enter Email Here" />
				<s:submit type="button" value="Save"/>
			</s:form>
		</div>
	</div>


</body>
</html>
