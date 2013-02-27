  $(document).ready( function()
    {
      var container = $('#container');
      container.draggable();
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