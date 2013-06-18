/**
 * @author Vishvesh Deshmukh 
 * Created : 2nd February, 2013 
 * Project : Scrbl
 */
var pageX = [], 
	pageY = [], 
	timeArray = [], 
	lastPoint, 
	click,
	lastClick;
	
	//console.log("Browser name: "+BrowserDetect.browser+" :  BrowserDetect.version : "+BrowserDetect.version+" : BrowserDetect.OS : "+BrowserDetect.OS);
	
	console.log(bd.b+" "+bd.v+" : "+bd.o);
	
/*var pointArray = {
	points: []
};*/
var pointArray = [];

/*var ci;
if(typeof c !=='undefined')
	ci = c;
else
	ci = null;*/

function Point(x, y, z) {
	if (x === undefined) {
		throw new ReferenceError('At least one dimension is required');
	}
	if (typeof x != 'number') {
		throw new TypeError("Variable 'x' is nonnumerical");
	} else if (y !== undefined && typeof y != 'number') {
		throw new TypeError("Variable 'y' is nonnumerical");
	} else if (z !== undefined && typeof z != 'number') {
		throw new TypeError("Variable 'z' is nonnumerical");
	}

	this.x = x;
	this.y = y;
	this.z = z;
}

Point.prototype.add = function(point) {
	if (point instanceof Point) {
		switch (this.numberOfDimensions) {
		case 1:
			switch (point.numberOfDimensions) {
			case 1:
				return new Point(this.x + point.x);
			case 2:
				return new Point(this.x + point.x, point.y);
			case 3:
				return new Point(this.x + point.x, point.y, point.z);
			default:
				throw new RangeError("The variable 'point' has an unsupported number of dimensions");
			}
			break;
		case 2:
			switch (point.numberOfDimensions) {
			case 1:
				return new Point(this.x + point.x, this.y);
			case 2:
				return new Point(this.x + point.x, this.y + point.y);
			case 3:
				return new Point(this.x + point.x, this.y + point.y, point.z);
			default:
				throw new RangeError("The variable 'point' has an unsupported number of dimensions");
			}
			break;
		case 3:
			switch (point.numberOfDimensions) {
			case 1:
				return new Point(this.x + point.x, this.y, this.z);
			case 2:
				return new Point(this.x + point.x, this.y + point.y, this.z);
			case 3:
				return new Point(this.x + point.x, this.y + point.y, this.z
						+ point.z);
			default:
				throw new RangeError("The variable 'point' has an unsupported number of dimensions");
			}
			break;
		default:
			throw new RangeError('The calling object has an unsupported number of dimensions');
		}
	}
	throw new TypeError("Variable 'point' is not a point");
};

Point.prototype.__defineGetter__('numberOfDimensions', function() {
	if (this.z !== undefined) {
		return 3;
	} else if (this.y !== undefined) {
		return 2;
	}
	return 1;
});

Point.prototype.is1D = function() {
	return this.y === undefined && this.z === undefined;
};

Point.prototype.is2D = function() {
	return this.y !== undefined && this.z === undefined;
};

Point.prototype.is3D = function() {
	return this.z !== undefined;
};

Point.prototype.multiplyMagnitude = function(number) {
	if (typeof number != 'number') {
		throw new TypeError("Variable 'number' is nonnumerical");
	}

	if (this.z !== undefined) {
		return new Point(this.x * number, this.y * number, this.z * number);
	} else if (this.y !== undefined) {
		return new Point(this.x * number, this.y * number);
	}
	return new Point(this.x * number);
};

$(document).ready(
	function() {
		var message = "";
		function clickIE() {
			if (document.all) {
				(message);
				return false;
			}
		}
	function clickNS(e) {
		if (document.layers || (document.getElementById && !document.all)) {
			if (e.which == 2 || e.which == 3) {
				(message);
				return false;
			}
		}
	}
	if (document.layers) {
		document.captureEvents(Event.MOUSEDOWN);
		document.onmousedown = clickNS;
	} else {
		document.onmouseup = clickNS;
		document.oncontextmenu = clickIE;
	}
	document.oncontextmenu = new Function("return false");

	var context = document.getElementById('canvas').getContext('2d');
	var offset = $('#canvas').offset();
	context.lineWidth = 1;

	//var container = $('#container');
	// container.draggable();
	
	/** **** For Testing on Browsers with a CLICK EVENT **** * */
	$("#canvas").on('mousedown',function(e) {
		e.preventDefault();		
		var point = new Point(e.pageX - offset.left, e.pageY - offset.top);
		pointArray.push(point.x, point.y);
		//pointArray.points.push(e.pageX - offset.left, e.pageY - offset.top);
		pageX.push(point.x);
		pageY.push(point.y);
		
		var date = new Date();
		lastClick = date.getTime();
		
		timeArray.push(lastClick);
		
		
	$("#canvas").bind('mousemove',function(event) {
		// e.preventDefault();
		var point = new Point(event.pageX - offset.left, event.pageY - offset.top);
		
		if (lastPoint !== undefined && lastPoint !== null) {
			context.beginPath();
			context.moveTo(lastPoint.x, lastPoint.y);
			context.lineTo(point.x, point.y);
			context.stroke();
		}
		//pointArray.points.push(event.pageX - offset.left, event.pageY - offset.top);
		pointArray.push(event.pageX - offset.left, event.pageY - offset.top);
		//console.log("POINT : "+point);
		pageX.push(point.x);
		pageY.push(point.y);
		
		lastPoint = point;

		var date = new Date();
		click = date.getTime();

		//var secondClick = click - lastClick;
		//console.log("Time from 1st px to 2nd : " + secondClick);		
		timeArray.push(click);	
		//lastClick = click;		
		
		});
	});

	$("#canvas").on('mouseup', function() {
		lastPoint = null;
		
		pageX.push(0);
		pageY.push(0);
		pointArray.push(0,0);
		timeArray.push(0);
		
		// clearInterval(this.varName);
		$("#canvas").unbind('mousemove');
	});

	document.getElementById('canvas').addEventListener('touchstart', function(e) {

		var point = new Point(e.pageX - offset.left, e.pageY - offset.top);
		pointArray.push(point.x, point.y);
		//pointArray.points.push(e.pageX - offset.left, e.pageY - offset.top);
		pageX.push(point.x);
		pageY.push(point.y);
		
		var date = new Date();
		lastClick = date.getTime();
		
		timeArray.push(lastClick);
		
	}, false);
	
	document.getElementById('canvas').addEventListener('touchmove', function(event) {
		event.preventDefault();
		var touch = event.touches[0];
		var point = new Point(touch.pageX - offset.left, touch.pageY - offset.top);

		if (lastPoint !== undefined && lastPoint !== null) {
			context.beginPath();
			context.moveTo(lastPoint.x, lastPoint.y);
			context.lineTo(point.x, point.y);
			context.stroke();
		}
		//pointArray.points.push(event.pageX - offset.left, event.pageY - offset.top);
		pointArray.push(touch.pageX - offset.left, touch.pageY - offset.top);
		//console.log("POINT : "+point);
		pageX.push(point.x);
		pageY.push(point.y);
		
		lastPoint = point;

		var date = new Date();
		click = date.getTime();

		//var secondClick = click - lastClick;
		//console.log("Time from 1st px to 2nd : " + secondClick);		
		timeArray.push(click);	
		//lastClick = click;

	}, false);

	document.getElementById('canvas').addEventListener('touchend', function() {
				lastPoint = null;
				
				pageX.push(0);
				pageY.push(0);
				pointArray.push(0,0);
				timeArray.push(0);
			}, false);
		});

function writeValues() {
	//console.log("pageX : " + JSON.stringify(pageX));
	//console.log("pageY : " + JSON.stringify(pageY));
	console.log("TimeArray : " + JSON.stringify(timeArray));
	console.log("PointArray : " + JSON.stringify(pointArray));
	//console.log("X LENGTH : "+pageX.length + ": Y LENGTH : "+pageY.length + " TIME LENGTH : "+timeArray.length);
	
	alert("Done Scribbling?");
	
  $.ajax({ 
	  url: 'writeValues.html', 
	  type: 'POST', 
	  data: {timeArray: JSON.stringify(timeArray), pointArray: JSON.stringify(pointArray)}, 
	  success: function(data){
		  //$('#ajaxResponse').html(data); 
	  } 
  }); 
}

var image;

function viewSavedImage() {
	var context = document.getElementById('canvas').getContext('2d');
	context.clearRect(0, 0, context.canvas.width, context.canvas.height);
	
	if(!image) {
		$.ajax({ 
		  url: 'getImageUrl.html', 
		  type: 'GET', 
		  success: function(data) {
			//console.log("IMAGE URL : "+data.imageUrl);
			  if(typeof data.imageUrl == 'undefined') {

				  context.strokeStyle = "navy";
				  context.font = 'italic 24px san-serif';
				  context.textBaseline = 'middle';
				  context.strokeText('Nothing Found to View!', 10, 20);
				  context.strokeText('To View, please ScribbleIn & Save!', 10, 60);
			  } else {
			
			  image = new Image();
				image.src = data.imageUrl;
				image.onload = function() {
					context.drawImage(image, 0, 0);
				};
			  }
		  } 
	  });
	} else {
		 context.drawImage(image, 0, 0);
	 }
}

function viewInstructions() {
	window.location = "baseAction.html";
}

function save() {
	/*console.log("pageX : " + JSON.stringify(pageX));
	console.log("pageY : " + JSON.stringify(pageY));
	console.log("TimeArray : " + JSON.stringify(timeArray));
	console.log("PointArray : " + JSON.stringify(pointArray));*/
	//console.log("X LENGTH : "+pageX.length + ": Y LENGTH : "+pageY.length + " TIME LENGTH : "+timeArray.length);
	
	if(pageX.length == 0 || pageY.length == 0 || timeArray.length == 0 || pointArray.length == 0) {
		alert("Please Scribble before clicking Save!");
		return false;
	}
    var canvas = document.getElementById('canvas');
	
	var base64ImageUrl = canvas.toDataURL('image/png');
	//console.log("Un-Replaced DATA URL : "+base64ImageUrl);
	
	//dataUrl = dataUrl.replace(/^data:image\/(png|jpeg);base64,/, "");
	//console.log("Replaced DATA URL : "+dataUrl);
	
	alert("Done Scribbling? Now Scribble again & click Match!");
	
  $.ajax({ 
	  url: 'save.html', 
	  type: 'POST', 
	  data: {pageX: JSON.stringify(pageX), pageY: JSON.stringify(pageY),timeArray: JSON.stringify(timeArray), 
		  pointArray: JSON.stringify(pointArray), base64ImageUrl: base64ImageUrl}, 
	  success: function(data) {
		  //$('#ajaxResponse').html(data); 
	  } 
  }); 
}

function match() {
	if(timeArray.length == 0 || pointArray.length == 0) {
		alert("Please Scribble before clicking Match!");
		return false;
	}
	$.ajax({ 
		  url: 'match.html', 
		  type: 'POST',
		  data: {timeArray: JSON.stringify(timeArray), pointArray: JSON.stringify(pointArray)},
		  success: function(data){
			  window.location = "thankYou.html";
			  //$('#ajaxResponse').html(data); 
			  //alert("Template Saved!");
		  } 
	  });
}

function clearScreen() {
	var canvas = document.getElementById('canvas');

	var context = canvas.getContext("2d");
	context.clearRect(0, 0, context.canvas.width, context.canvas.height);
	//pageX.length = 0;
	//pageY.length = 0;
	timeArray.length = 0;
	pointArray.length = 0;
	
	/*$.ajax({ 
		  url: 'clean.html', 
		  type: 'POST',
		  //data: {timeArray: JSON.stringify(timeArray), pointArray: JSON.stringify(pointArray)},
		  success: function(data){
			  //$('#ajaxResponse').html(data); 
			  //alert("Template Saved!");
		  } 
	  });*/
	// context.clearRect(0, 0, canvas.width, canvas.height);
}