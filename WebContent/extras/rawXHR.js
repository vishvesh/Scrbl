/**
 * @author Vishvesh Deshmukh
 * Raw Ajax XHR Call implemented to use instead of JQuery's Ajax call if we encounter Performance Hits!
 * @param fileName
 * @param method
 * @param params
 * @param callback
 */
function load(fileName, method, params, callback) {
	var xhr;

	if (typeof XMLHttpRequest !== 'undefined') {
		xhr = new XMLHttpRequest();
	} else {
		var versions = [ 'MSXML2.XmlHttp.5.0', 'MSXML2.XmlHttp.4.0',
				'MSXML2.XmlHttp.3.0', 'MSXML2.XmlHttp.2.0', 'Microsoft.XmlHttp' ];
		for ( var i = 0; i < versions.length; i++) {
			try {
				xhr = new ActiveXObject(versions[i]);
				break;
			} catch (e) {

			}
		}
	}
	xhr.onreadystatechange = function() {
		readyState = xhr.readyState;

		if (readyState == 4) {
			if (xhr.status == 200) {
				callback(xhr);
			}
		}
	}
	var queryString = '';
	if (typeof params === 'object') {
		for ( var paramName in params) {
			queryString += (queryString.length == 0 ? '' : '&') + paramName
					+ '=' + encodeURIComponent(params[paramName]);
		}
	}
	var url = fileName;
	if (method.toLowerCase() == 'get') {
		url += '?' + queryString;
	}
	xhr.open(method, url, true);
	if (method.toLowerCase() == 'post') {
		xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
	}
	if (method.toLowerCase() == 'post') {
		xhr.send(queryString);
	} else {
		xhr.send(null);
	}
}

//Calling it
load('get-data.jsp', 'POST', {
	name : 'Vishvesh',
	title : 'Deshmukh'
}, function(xhr) {
	 //document.getElementById('response').innerHTML = xhr.responseText;
	 var response = JSON.parse(xhr.responseText);  
	 var container = document.getElementById('container');  
	 for(var i = 0, len = response.length; i < len; i++) {  
	   container.innerHTML += '<li><strong>' + response[i].name + '</strong> : ' + response[i].title + '</li>';  
	 }  
});
