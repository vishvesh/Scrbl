  var pageX = [];
  var pageY = [];
  var timeArray = [];
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
      
      	/** **** For Testing on Browsers with a CLICK EVENT **** **/
	      $(document).on('mousedown', function(e) {
	    	  e.preventDefault();
	    	  var counter = $('#counter');
				var varCounter = 0;
				 this.varName = setInterval(function() {
		  	    if (varCounter <= 10) {
		  	    	counter.html("Timer in Seconds : "+varCounter++);
		  	    } 
		  	}, 1000);
				
	    	    $(document).bind('mousemove', function(e) {
	    	    	var date = new Date();
		    	    var time = date.getMilliseconds();
		    	    pageX.push(e.pageX);
		    	    pageY.push(e.pageY);
		    	    timeArray.push(time);
		    	    container.html("page X : "+e.pageX + " : page Y : "+e.pageY + " : time in Milli Secs : "+time);
	    	    });
	    	});
	
	    	$(document).on('mouseup', function() {
	    		$('#displayCoordinates').html("X : "+pageX.toString()+"\n Y : "+pageY.toString()+"\n Time : "+timeArray.toString());
	    		clearInterval(this.varName);
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
	    	
	    	var getXYOnTouchmove =	document.addEventListener('touchmove', function(event) {
	    	    event.preventDefault();
	    	    var touch = event.touches[0];
	    	    var date = new Date();
	    	    var time = date.getMilliseconds();
	    	    container.html("page X : "+touch.pageX + " : page Y : "+touch.pageY + " : time in Milli Secs : "+time);
	    	    console.log("Touch x:" + touch.pageX + ", y:" + touch.pageY);
	    	}, false);
	    	
	     	var touchStart = document.addEventListener('touchstart', function(event) {
	     		event.preventDefault();
	     		var counter = $('#counter');
	     		var varCounter = 0;
		    	var varName = setInterval(function() {
		    	    if (varCounter <= 10) {
		    	    	counter.html("Timer in Seconds : "+varCounter++);
		    	    } else {
		    	        clearInterval(varName);
		    	    }
		    	}, 1000);
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
	  console.log("pageX.toString() : "+pageX.toString());
		$.ajax({
		  url: '/Scrbl/writeValues',
		  type: 'POST',
		  //data: { "pageX=": encodeURIComponent(pageX.toString()), "pageY=": pageY.toString(), "timeArray=": timeArray.toString() },
		  data: {pageX: encodeURIComponent(pageX.toString())},
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