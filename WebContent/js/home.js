  var pageX = [];
  var pageY = [];
  var timeArray = [];
  var milliseconds;
$(document).ready( function()
    {
      var container = $('#container');
      container.draggable();
      
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
	      $(document).on('mousedown', function(e) {
	    	  e.preventDefault();
	    	  /*var counter = $('#counter');
				var varCounter = 0;
				 this.varName = setInterval(function() {
		  	    if (varCounter <= 10) {
		  	    	counter.html("Timer in Seconds : "+varCounter++);
		  	    } 
		  	}, 1000);*/
	    	  //var touch = event.touches[0];
	    	  	
	    	  	var time = 0;
	     		pageX.push(e.pageX);
		    	pageY.push(e.pageY);
		    	//timeArray.push(0);
		    	
		    	//var d = new Date();
		    	//var m = d.getTime();
		    	//console.log("1st Touch Time : "+m);
		    	lastClick = 0;
		    	//milliseconds =
		    	console.log("Should print this only 1ce");
		    	timeArray.push(lastClick);
		    	console.log("1st Touch Time : "+lastClick);
		    	
		    	
	    	    $(document).bind('mousemove', function(e) {
	    	    	var date = new Date();
			    	click = date.getTime();
			    	
			    	var secondClick = click - lastClick;
			    	console.log("Second Click : "+secondClick);
			    	
			    	timeArray.push(secondClick);
			    	
			    	lastClick = click;
			    	console.log("Last CLick : "+lastClick);
			    	
			    	pageX.push(e.pageX);
		    	    pageY.push(e.pageY);
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
		    	    $('#displayCoordinates').html("X : "+pageX.toString()+"\n Y : "+pageY.toString()+"\n Time : "+timeArray.toString());
	    	    });
	    	});
	
	    	$(document).on('mouseup', function() {
	    		
	    		
	    		//clearInterval(this.varName);
	    	    $(document).unbind('mousemove');
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
	    	
	    	document.addEventListener('touchstart', function() {
	    		lastClick = 0;
	    		timeArray.push(lastClick);
	     	}, false);
	    	
	    	document.addEventListener('touchmove', function(event) {
	    	    event.preventDefault();
	    	    var touch = event.touches[0];
	    	    /*var date = new Date();
	    	    var time = date.getMilliseconds();
	    	    //container.html("page X : "+touch.pageX + " : page Y : "+touch.pageY + " : time in Milli Secs : "+time);
	    	    //console.log("Touch x:" + touch.pageX + ", y:" + touch.pageY);
	    	    pageX.push(touch.pageX);
	    	    pageY.push(touch.pageY);
	    	    timeArray.push(time);*/
	    	    
	    	    var date = new Date();
		    	click = date.getTime();
		    	
		    	var secondClick = click - lastClick;
		    	console.log("Second Click : "+secondClick);
		    	
		    	timeArray.push(secondClick);
		    	
		    	lastClick = click;
		    	console.log("Last CLick : "+lastClick);
		    	
		    	pageX.push(touch.pageX);
	    	    pageY.push(touch.pageY);
	    	    //container.html("page X : "+touch.pageX + " : page Y : "+touch.pageY + " : time in Milli Secs : "+time);
	    	    //$('#displayCoordinates').html("X : "+pageX.toString()+"\n Y : "+pageY.toString()+"\n Time : "+timeArray.toString());
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
	  console.log("pageY.toString() : "+JSON.stringify(timeArray));
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