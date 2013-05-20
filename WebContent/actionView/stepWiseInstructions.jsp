<!DOCTYPE html>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title> MeetYourMakerz!</title >
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<script src="http://code.jquery.com/jquery-1.7.2.min.js" ></script>
<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js" ></script>
<style type="text/css">
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

#title {
	text-align: center;
	text-decoration: underline;
	font-family: cursive;
}

#container {
	color: blue;
    font-size: 18px;
    position: relative;
    margin: 0 auto;
}

#submit {
	text-align: center;
	margin-top: 5px;
}

body {
	background: none repeat scroll 0 0 whitesmoke;
}

#title:hover {
	background: none;
	text-decoration: none; 
	-webkit-transform: scale(1.4);
	-moz-transform: scale(1.4);
	transform: scale(1.4);
}

#instructions {
	margin-top: 5px;
}

.steps {
	margin-top: 4px;
}

.step {
	text-decoration: underline;
}

</style>
<script type="text/javascript">
	/* $(document).ready(function() {
   });  */
	
	function proceedToScribling() {
		window.location = "startScribbling.html";
    }
</script>
</head>
<body>

	<div id="container" style="text-align:left">
	  <div id="wrapper">
			<%-- <s:form action="saveEmail" id="userEmailForm" style="text-align:center" onsubmit="return false;">
				<s:textfield label="Enter Email " type="text" name="userEmail" id="userEmail"/>
				<s:submit type="button" id="submitButton" value="Save" onclick="checkVal();"/>
				<div id="error"></div>
			</s:form> --%>
			<div id="title">
				Step-By-Step Guide To use the app!
			</div>
			<div id="instructions">
				<div class="steps"><span class="step">Step 1:</span> In the active window write the letters “hey jude” using your normal hand writing 
						style (Do not write cursive). If you made an error simply press “clear” to clear the 
						screen and write again.
				</div>
				
				<div class="steps"><span class="step">Step 2.</span> Press Save.</div> 

				<div class="steps"><span class="step">Step 3.</span> Rewrite the phrase the way you wrote it the first time. Try to recreate it as 
			            			close to the first time as possible including style, speed etc.
			    </div>		
								
				<div class="steps"><span class="step">Step 4.</span> Press Match.</div>			
				
				<div class="steps"><span class="step">Today's Prize</span> - The participant who is able to recreate his own 
									handwritten the phrase the best gets <span class="step">$50</span>.
				</div>
			</div>
			<div id="submit">
				<input type="button" id="submitButton" value="Start Scribbling ->" onclick="proceedToScribling();" />
			</div>
		</div>
		
	</div>

</body>
</html>