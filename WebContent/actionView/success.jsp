<!DOCTYPE html>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title> MeetYourMakerz!</title >
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<script src="http://code.jquery.com/jquery-1.7.2.min.js" ></script>
<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js" ></script>
<script src="js/jquery-touch-punch/jquery.ui.touch-punch.min.js" ></script>
<script type = "text/javascript" src="http://l2.io/ip.js?var=c"></script> 
<script src="js/home.js" ></script>
<link rel="stylesheet" type="text/css" href="css/home.css">
</head>
<body>
<div id="container">
  <!-- s:property value="name"/-->
  <div id="canvasPlaceholder">
  	<canvas id="canvas" height=150 width=380></canvas>
  </div>
<div id="writeValues">
	<span id="ajaxResponse"></span>
	<!-- <input type="button" value="Write Values" onclick="writeValues()"/> -->
	<input type="button" value="Save" onclick="save()"/>
	<input type="button" value="Match" onclick="match()"/>
	<input type="button" value="Clear" onclick="clearScreen()"/>
</div>
</div>


</body>
</html>
