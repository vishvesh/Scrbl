<!DOCTYPE html>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title> Scrbl - No need to remember passwords anymore!</title >
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<script src="http://code.jquery.com/jquery-1.7.2.min.js" ></script>
<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js" ></script>
<script src="js/jquery-touch-punch/jquery.ui.touch-punch.min.js" ></script>
<script src="js/home.js" ></script>
<link rel="stylesheet" type="text/css" href="css/home.css">
</head>
<body>
<div id= "container">
  <!-- s:property value="name"/-->
  <div id="canvasPlaceholder">
  	<canvas id="example" height=450 width=300></canvas>
  </div>
</div>

<style media="screen">
canvas {
	border: 1px solid #ccc
}
</style>
<script>
	var CanvasDrawr = function(options) {
		var canvas = document.getElementById(options.id), ctxt = canvas
				.getContext("2d");
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
	});
	//
</script>

</body>
</html>
