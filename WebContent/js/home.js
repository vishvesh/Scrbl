/**
 * @author Vishvesh Deshmukh Created : 2nd February, 2013 Project : Scrbl
 */
var pageX = [], pageY = [], timeArray = [], lastPoint, click, lastClick;

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
				throw new RangeError(
						"The variable 'point' has an unsupported number of dimensions");
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
				throw new RangeError(
						"The variable 'point' has an unsupported number of dimensions");
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
				throw new RangeError(
						"The variable 'point' has an unsupported number of dimensions");
			}
			break;
		default:
			throw new RangeError(
					'The calling object has an unsupported number of dimensions');
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

	var container = $('#container');
	// container.draggable();

	/** **** For Testing on Browsers with a CLICK EVENT **** * */
	$("#canvas").on('mousedown',function(e) {
		e.preventDefault();
		
		pageX.push(e.pageX);
		pageY.push(e.pageY);
		
		lastClick = 0;
		
		timeArray.push(lastClick);
	$("#canvas").bind('mousemove',function(event) {
		// e.preventDefault();
		var point = new Point(event.pageX - offset.left, event.pageY - offset.top);
		if (lastPoint !== undefined && lastPoint !== null) {
			context.beginPath();
			context .moveTo(lastPoint.x, lastPoint.y);
			context.lineTo(point.x, point.y);
			context.stroke();
		}
		lastPoint = point;

		var date = new Date();
		click = date.getTime();

		var secondClick = click - lastClick;
		console.log("Time from 1st px to 2nd : " + secondClick);		
		timeArray.push(secondClick);	
		lastClick = click;		
		pageX.push(event.pageX);
		pageY.push(event.pageY);
		});
	});

	$("#canvas").on('mouseup', function() {

		lastPoint = null;
		// clearInterval(this.varName);
		$("#canvas").unbind('mousemove');
	});

	document.getElementById('canvas').addEventListener('touchstart', function() {
				lastClick = 0;
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
		lastPoint = point;
		var date = new Date();
		click = date.getTime();
		var secondClick = click - lastClick;
		console.log("Time from 1st px to 2nd : "+ secondClick);
		timeArray.push(secondClick);
		lastClick = click;
		pageX.push(touch.pageX);
		pageY.push(touch.pageY);
	}, false);

	document.getElementById('canvas').addEventListener('touchend', function() {
				lastPoint = null;
			}, false);
		});

function writeToExcel() {
	console.log("pageX : " + JSON.stringify(pageX));
	console.log("pageY : " + JSON.stringify(pageY));
	console.log("TimeArray : " + JSON.stringify(timeArray));
	
	var clientIp;
	if(typeof client !=='undefined')
		clientIp = client;
	else
		clientIp = null;
	console.log("Client : "+clientIp);
	
	alert("Done Scribbling?");
	
  $.ajax({ 
	  url: '/Scrbl/writeValues', 
	  type: 'POST', 
	  data: {pageX:JSON.stringify(pageX), pageY: JSON.stringify(pageY), timeArray: JSON.stringify(timeArray), client: clientIp}, 
	  success: function(data){
		  //$('#ajaxResponse').html(data); 
	  } 
  }); 
}

function clearScreen() {
	var canvas = document.getElementById('canvas');
	var context = canvas.getContext("2d");
	context.clearRect(0, 0, context.canvas.width, context.canvas.height);
	pageX.length = 0;
	pageY.length = 0;
	timeArray.length = 0;
	// context.clearRect(0, 0, canvas.width, canvas.height);

}

/*
 * var CanvasDrawr = function(options) { var canvas =
 * document.getElementById(options.id), ctxt = canvas.getContext("2d");
 * canvas.style.width = '100%' canvas.width = canvas.offsetWidth;
 * canvas.style.width = ''; ctxt.lineWidth = options.size ||
 * Math.ceil(Math.random() * 35); ctxt.lineCap = options.lineCap || "round";
 * ctxt.pX = undefined; ctxt.pY = undefined; var lines = [ , , ]; var offset =
 * $(canvas).offset();
 * 
 * 
 * 
 * var self = { init : function() { canvas.addEventListener('touchstart',
 * self.preDraw, false); canvas.addEventListener('touchmove', self.draw, false); },
 * preDraw : function(event) { $ .each( event.touches, function(i, touch) { var
 * id = touch.identifier, colors = [ "red", "green", "yellow", "blue",
 * "magenta", "orangered" ], mycolor = colors[Math .floor(Math .random()
 * colors.length)]; lines[id] = { x : this.pageX - offset.left, y : this.pageY -
 * offset.top, color : mycolor }; }); event.preventDefault(); }, draw :
 * function(event) { var e = event, hmm = {}; $ .each( event.touches,
 * function(i, touch) { var id = touch.identifier, moveX = this.pageX -
 * offset.left - lines[id].x, moveY = this.pageY - offset.top - lines[id].y; var
 * ret = self.move(id, moveX, moveY); lines[id].x = ret.x; lines[id].y = ret.y;
 * }); event.preventDefault(); }, move : function(i, changeX, changeY) {
 * ctxt.strokeStyle = lines[i].color; ctxt.beginPath(); ctxt.moveTo(lines[i].x,
 * lines[i].y); ctxt.lineTo(lines[i].x + changeX, lines[i].y + changeY);
 * ctxt.stroke(); ctxt.closePath(); return { x : lines[i].x + changeX, y :
 * lines[i].y + changeY }; } }; return self.init(); }; $(function() { var
 * super_awesome_multitouch_drawing_canvas_thingy = new CanvasDrawr( { id :
 * "example", size : 15 }); });
 */

/*
 * $.ajax({ type: "POST", url: panel.requestUri, data: "currentPage=" +
 * panel.page, success: function(data){ $(currentProductTab).html(data);
 * panel.pagesArray[panel.page] = data; panel.page++; } });
 * 
 * var createAjaxLoader = function() { var $_div = $("<div>");
 * $_div.addClass("ajaxLoader"); var $_img = $("<img>"); $_img.attr("alt",
 * "loading"); $_img.attr("src", "images/siteRedesignBeta/ajax-loader.gif");
 * $_div.html($_img); return $_div; };
 */