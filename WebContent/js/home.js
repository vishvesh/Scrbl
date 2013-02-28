  $(document).ready( function()
    {
      var container = $('#container');
      container.draggable();
      
    /* container.click(function(data){
    	container.mousemove(function(data){
    		console.log("page X : "+data.pageX + " : page Y : "+data.pageY);
    	}); 
     }); */
      
      	/** **** For Testing on Browsers with a CLICK EVENT **** **/
	      $(document).on('mousedown', function(e) {
	    	    $(document).bind('mousemove', function(e) {
	    	    	container.html("page X : "+e.pageX + " : page Y : "+e.pageY);
	    	    });
	    	});
	
	    	$(document).on('mouseup', function() {
	    	    $(document).unbind('mousemove');
	    	});
	    	
	    /** **** For Testing on Browsers with a TOUCH EVENT **** **/
	    	$(document).on('touchstart', function(e) {
	    	    $(document).bind('touchmove', function(e) {
	    	    	container.html("page X : "+e.pageX + " : page Y : "+e.pageY);
	    	    });
	    	});
	
	    	$(document).on('touchend', function() {
	    	    $(document).unbind('touchmove');
	    	});
      
      
      /*container.mousemove(function(data){
    	  if(data.which == 1)
    		  {
    		  	console.log("page X : "+data.pageX + " : page Y : "+data.pageY);
    		  }
      });*/
      
      function touchHandler(event)
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
        		  							first.clientY, false, false, false, false, 0/*left*/, null);
          first.target.dispatchEvent(simulatedEvent);
          //event.preventDefault();
      }

      function init() 
      {
          document.addEventListener("touchstart", touchHandler, true);
          document.addEventListener("touchmove", touchHandler, true);
          document.addEventListener("touchend", touchHandler, true);
          document.addEventListener("touchcancel", touchHandler, true);    
      }
      
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