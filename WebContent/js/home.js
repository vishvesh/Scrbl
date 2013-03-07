/**
 * @author Vishvesh Deshmukh
 * Created : 2nd February, 2013
 * Project : Scrbl
*/
var pageX = [];
  var pageY = [];
  var timeArray = [];
  var milliseconds;
  
  /*var CanvasDrawr = function(options) {
		var canvas = document.getElementById(options.id), ctxt = canvas
				.getContext("2d");
		canvas.style.width = '100%';
		canvas.width = canvas.offsetWidth;
		canvas.style.width = '';
		ctxt.lineWidth = options.size
				|| Math.ceil(Math.random() * 35);
		ctxt.lineCap = options.lineCap || "round";
		ctxt.pX = undefined;
		ctxt.pY = undefined;
		var lines = [ , , ];
		var offset = $(canvas).offset();
		var self = {
			init : function() {
				canvas.addEventListener('mousedown',
						self.preDraw, false);
				canvas.addEventListener('mousemove', self.draw,
						false);
			},
			preDraw : function(event) {
				$
						.each(
								event.touches,
								function(i, touch) {
									var id = touch.identifier, colors = [
											"red", "green",
											"yellow", "blue",
											"magenta",
											"orangered" ], mycolor = colors[Math
											.floor(Math
													.random()
													* colors.length)];
									lines[id] = {
										x : this.pageX
												- offset.left,
										y : this.pageY
												- offset.top,
										color : mycolor
									};
								});
				event.preventDefault();
			},
			draw : function(event) {
				var e = event, hmm = {};
				$
						.each(
								event.touches,
								function(i, touch) {
									var id = touch.identifier, moveX = this.pageX
											- offset.left
											- lines[id].x, moveY = this.pageY
											- offset.top
											- lines[id].y;
									var ret = self.move(id,
											moveX, moveY);
									lines[id].x = ret.x;
									lines[id].y = ret.y;
								});
				event.preventDefault();
			},
			move : function(i, changeX, changeY) {
				ctxt.strokeStyle = lines[i].color;
				ctxt.beginPath();
				ctxt.moveTo(lines[i].x, lines[i].y);
				ctxt.lineTo(lines[i].x + changeX, lines[i].y
						+ changeY);
				ctxt.stroke();
				ctxt.closePath();
				return {
					x : lines[i].x + changeX,
					y : lines[i].y + changeY
				};
			}
		};
		return self.init();
	};*/
  
  function Point(x, y, z) {
	    if (x === undefined) {
	        throw new ReferenceError('At least one dimension is required');
	    }
	    if (typeof x != 'number') {
	        throw new TypeError("Variable 'x' is nonnumerical");
	    }
	    else if (y !== undefined && typeof y != 'number') {
	        throw new TypeError("Variable 'y' is nonnumerical");
	    }
	    else if (z !== undefined && typeof z != 'number') {
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
	                return new Point(this.x + point.x, this.y + point.y, this.z + point.z);
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
	    }
	    else if (this.y !== undefined) {
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
	    }
	    else if (this.y !== undefined) {
	        return new Point(this.x * number, this.y * number);
	    }
	    return new Point(this.x * number);
	};
  
$(document).ready( function()
    {
	

	var message="";
	function clickIE() {if (document.all) {(message);return false;}}
	function clickNS(e) {if
	(document.layers||(document.getElementById&&!document.all)) {
	if (e.which==2||e.which==3) {(message);return false;}}}
	if (document.layers)
	{document.captureEvents(Event.MOUSEDOWN);document.onmousedown=clickNS;}
	else{document.onmouseup=clickNS;document.oncontextmenu=clickIE;}
	document.oncontextmenu=new Function("return false");
	
	var lastPoint;
    var context = document.getElementById('canvas').getContext('2d');
    var offset = $('#canvas').offset();
    context.lineWidth = 1;
    
    /*jQuery('body').mousemove(function (event) {
        
    }).mouseleave(function (event) {
        lastPoint = null;
    });*/

	
      var container = $('#container');
      //container.draggable();
      
    /* container.click(function(data){
    	container.mousemove(function(data){
    		console.log("page X : "+data.pageX + " : page Y : "+data.pageY);
    	}); 
     }); */		
      var varName;
      var date;
      var click;
      var lastClick;
      
      	/** **** For Testing on Browsers with a CLICK EVENT **** **/
	      $("#canvas").on('mousedown', function(e) {
	    	  e.preventDefault();
	    	  /*var counter = $('#counter');
				var varCounter = 0;
				 this.varName = setInterval(function() {
		  	    if (varCounter <= 10) {
		  	    	counter.html("Timer in Seconds : "+varCounter++);
		  	    } 
		  	}, 1000);*/
	    	  //var touch = event.touches[0];
	    	 /* var click = $(e).click();
	    	  console.log("CLICK : "+click);*/

	    	  	var time = 0;
	     		pageX.push(e.pageX);
		    	pageY.push(e.pageY);
		    	//timeArray.push(0);
		    	
		    	//var d = new Date();
		    	//var m = d.getTime();
		    	//console.log("1st Touch Time : "+m);
		    	lastClick = 0;
		    	//milliseconds =
		    	//console.log("Should print this only 1ce");
		    	timeArray.push(lastClick);
		    	//console.log("1st Touch Time : "+lastClick);
		    	
		    	
	    	    $("#canvas").bind('mousemove', function(event) {
	    	    	//e.preventDefault();
	    	    	var point = new Point(event.pageX - offset.left, event.pageY - offset.top);
	    	        if (lastPoint !== undefined && lastPoint !== null) {
	    	            context.beginPath();
	    	            context.moveTo(lastPoint.x, lastPoint.y);
	    	            context.lineTo(point.x, point.y);
	    	            context.stroke();
	    	        }
	    	        lastPoint = point;
	    	        //console.log("Last Point : "+lastPoint);
	    	    	
	    	    	var date = new Date();
			    	click = date.getTime();
			    	
			    	var secondClick = click - lastClick;
			    	console.log("Time from 1st px to 2nd : "+secondClick);
			    	
			    	timeArray.push(secondClick);
			    	
			    	lastClick = click;
			    	//console.log("Last CLick : "+lastClick);
			    	
			    	pageX.push(event.pageX);
		    	    pageY.push(event.pageY);
			    	//console.log("milliseconds : "+milliseconds);
		    	    
		    	    //console.log("TIME 2 : "+time2);
		    	    //var timez = new Date();
		    	    //var time2 = timez.getMilliseconds() - milliseconds;
		    	    //console.log("New Time : "+time2);
		    	    
		    	    //var tim = timez.getSeconds();
		    	    //console.log("TIM : "+tim);
		    		//console.log("TIME 2 : "+time2);
		    		//console.log("UTC : "+timez.getUTCMilliseconds());
		    	    //container.html("page X : "+e.pageX + " : page Y : "+e.pageY + " : time in Milli Secs : "+time);
		    	    //$('#displayCoordinates').html("X : "+pageX.toString()+"\n Y : "+pageY.toString()+"\n Time : "+timeArray.toString());
	    	    });
	    	});
	
	    	$("#canvas").on('mouseup', function() {
	    		
	    		lastPoint = null;
	    		//clearInterval(this.varName);
	    	    $("#canvas").unbind('mousemove');
	    	});
	    	
	    	//console.log("coming fine");
	    	
	    	//console.log("executes");
	    /** **** For Testing on Browsers with a TOUCH EVENT **** **/
	    	/*$(document).on('touchstart', function(event) {
	    	    $(document).bind('touchmove', function(event) {
	    	    	event.preventDefault();
	    	    	touch = event.touches[0];
	    	    	console.log("Touch x:" + touch.pageX + ", y:" + touch.pageY);
	    	    	container.html("page X : "+touch.pageX + " : page Y : "+touch.pageY);
	    	    });
	    	});
	
	    	$(document).on('touchend', function() {
	    	    $(document).unbind('touchmove');
	    	});*/
	    	
	    	document.getElementById('canvas').addEventListener('touchstart', function() {
	    		lastClick = 0;
	    		timeArray.push(lastClick);
	     	}, false);
	    	
	    	document.getElementById('canvas').addEventListener('touchmove', function(event) {
	    	    event.preventDefault();
	    	    var touch = event.touches[0];
	    	    /*var date = new Date();
	    	    var time = date.getMilliseconds();
	    	    //container.html("page X : "+touch.pageX + " : page Y : "+touch.pageY + " : time in Milli Secs : "+time);
	    	    //console.log("Touch x:" + touch.pageX + ", y:" + touch.pageY);
	    	    pageX.push(touch.pageX);
	    	    pageY.push(touch.pageY);
	    	    timeArray.push(time);*/
	    	    
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
		    	console.log("Time from 1st px to 2nd : "+secondClick);
		    	
		    	timeArray.push(secondClick);
		    	
		    	lastClick = click;
		    	//console.log("Last CLick : "+lastClick);
		    	
		    	pageX.push(touch.pageX);
	    	    pageY.push(touch.pageY);
	    	    //container.html("page X : "+touch.pageX + " : page Y : "+touch.pageY + " : time in Milli Secs : "+time);
	    	    //$('#displayCoordinates').html("X : "+pageX.toString()+"\n Y : "+pageY.toString()+"\n Time : "+timeArray.toString());
	    	}, false);
	    	
	    	document.getElementById('canvas').addEventListener('touchend', function() {
	    		lastPoint = null;
	     	}, false);
	     	
	    	//varName;
	    	
      /*container.mousemove(function(data){
    	  if(data.which == 1)
    		  {
    		  	console.log("page X : "+data.pageX + " : page Y : "+data.pageY);
    		  }
      });*/
      
      /*function touchHandler(event)
      {
          var touches = event.changedTouches,
              first = touches[0],
              type = "";
               switch(event.type)
          {
              case "touchstart": type="mousedown"; break;
              case "touchmove":  type="mousemove"; break;        
              case "touchend":   type="mouseup"; break;
              default: return;
          }

                   //initMouseEvent(type, canBubble, cancelable, view, clickCount, 
          //           screenX, screenY, clientX, clientY, ctrlKey, 
          //           altKey, shiftKey, metaKey, button, relatedTarget);

          var simulatedEvent = document.createEvent("MouseEvent");
          simulatedEvent.initMouseEvent(type, true, true, window, 1, first.screenX, first.screenY, first.clientX, 
        		  							first.clientY, false, false, false, false, 0left, null);
          first.target.dispatchEvent(simulatedEvent);
          //event.preventDefault();
      }

      function init() 
      {
          document.addEventListener("touchstart", touchHandler, true);
          document.addEventListener("touchmove", touchHandler, true);
          document.addEventListener("touchend", touchHandler, true);
          document.addEventListener("touchcancel", touchHandler, true);    
      }*/
      
      /*document.getElementById('container').addEventListener('touchstart', function(ev) {

    	    if (ev.touches.item(0) == ev.targetTouches.item(0))
    	    {
    	        *//**
    	         * If the first touch on the surface is also targeting the
    	         * "touchable" element, the code below should execute.
    	         * Since targetTouches is a subset of touches which covers the
    	         * entire surface, TouchEvent.touches >= TouchEvents.targetTouches
    	         * is always true.
    	         *//*
    	        document.write('Hello Touch Events!');
    	        console.log("1 finger touched");
    	    }

    	    if (ev.touches.length == ev.targetTouches.length)
    	    {
    	        *//**
    	         * If all of the active touch points are on the "touchable"
    	         * element, the length properties should be the same.
    	         *//*
    	        document.write('All points are on target element')
    	    }

    	    if (ev.touches.length > 1)
    	    {
    	        *//**
    	         * On a single touch input device, there can only be one point
    	         * of contact on the surface, so the following code can only
    	         * execute when the terminal supports multiple touches.
    	         *//*
    	        document.write('Hello Multiple Touch!');
    	    }

    	}, false);*/     
    }
  );
  
  function writeToExcel(){
	  console.log("pageX.toString() : "+JSON.stringify(pageX));
	  console.log("pageY.toString() : "+JSON.stringify(pageY));
	  console.log("TimeArray.toString() : "+JSON.stringify(timeArray));
	  alert("Done Scribbling?");
		$.ajax({
		  url: '/Scrbl/writeValues',
		  type: 'POST',
		  //dataType: 'json',
		  //contentType: 'application/json, charset=utf-8',
		  //data: { "pageX=": encodeURIComponent(pageX.toString()), "pageY=": pageY.toString(), "timeArray=": timeArray.toString() },
		  data: {pageX: JSON.stringify(pageX), pageY: JSON.stringify(pageY), timeArray: JSON.stringify(timeArray)},
		  success: function(data){
			  //$('#ajaxResponse').html(data);
		  }
		});
	}
  
  /*var CanvasDrawr = function(options) {
		var canvas = document.getElementById(options.id), ctxt = canvas.getContext("2d");
		canvas.style.width = '100%'
		canvas.width = canvas.offsetWidth;
		canvas.style.width = '';
		ctxt.lineWidth = options.size
				|| Math.ceil(Math.random() * 35);
		ctxt.lineCap = options.lineCap || "round";
		ctxt.pX = undefined;
		ctxt.pY = undefined;
		var lines = [ , , ];
		var offset = $(canvas).offset();
		
		
		
		var self = {
			init : function() {
				canvas.addEventListener('touchstart',
						self.preDraw, false);
				canvas.addEventListener('touchmove', self.draw,
						false);
			},
			preDraw : function(event) {
				$
						.each(
								event.touches,
								function(i, touch) {
									var id = touch.identifier, colors = [
											"red", "green",
											"yellow", "blue",
											"magenta",
											"orangered" ], mycolor = colors[Math
											.floor(Math
													.random()
													* colors.length)];
									lines[id] = {
										x : this.pageX
												- offset.left,
										y : this.pageY
												- offset.top,
										color : mycolor
									};
								});
				event.preventDefault();
			},
			draw : function(event) {
				var e = event, hmm = {};
				$
						.each(
								event.touches,
								function(i, touch) {
									var id = touch.identifier, moveX = this.pageX
											- offset.left
											- lines[id].x, moveY = this.pageY
											- offset.top
											- lines[id].y;
									var ret = self.move(id,
											moveX, moveY);
									lines[id].x = ret.x;
									lines[id].y = ret.y;
								});
				event.preventDefault();
			},
			move : function(i, changeX, changeY) {
				ctxt.strokeStyle = lines[i].color;
				ctxt.beginPath();
				ctxt.moveTo(lines[i].x, lines[i].y);
				ctxt.lineTo(lines[i].x + changeX, lines[i].y
						+ changeY);
				ctxt.stroke();
				ctxt.closePath();
				return {
					x : lines[i].x + changeX,
					y : lines[i].y + changeY
				};
			}
		};
		return self.init();
	};
	$(function() {
		var super_awesome_multitouch_drawing_canvas_thingy = new CanvasDrawr(
				{
					id : "example",
					size : 15
				});
	});*/
  
/*$.ajax({
	type: "POST",
	url: panel.requestUri,
	data: "currentPage=" + panel.page,
	success: function(data){
		$(currentProductTab).html(data);
		panel.pagesArray[panel.page] = data;
		panel.page++;
	}
});	

var createAjaxLoader = function()
{
	var $_div = $("<div>");
	$_div.addClass("ajaxLoader");
	var $_img = $("<img>");
	$_img.attr("alt", "loading");
	$_img.attr("src", "images/siteRedesignBeta/ajax-loader.gif");
	$_div.html($_img);
	return $_div;
};*/