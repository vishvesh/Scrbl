document.createElement("header");
document.createElement("footer");
document.createElement("section");
document.createElement("aside");
document.createElement("nav");
document.createElement("article");
var speed = 800;
var easing = 'easeInOutBack';
$(window).load(
		function() {
			setTimeout(load_logo, 1500);
			if (window.location.hash) {
				var hashval = (location.hash).replace('#!', '');
				if (hashval != 'home')
					LoadContent(hashval);
			}
			$(window).hashchange(function() {
				if (location.hash) {
					var hashval = (location.hash).replace('#!', '');
					LoadContent(hashval);
				} else
					LoadContent('home');
			});
			// check placeholder browser support
			if (!Modernizr.input.placeholder) {
				// set placeholder values
				$('.contact-form').find('[placeholder]').each(function() {
					if ($(this).val() == '') {
						$(this).addClass('grey');
						$(this).val($(this).attr('placeholder'));
					}
				});
				// focus and blur of placeholders
				$('[placeholder]').focus(function() {
					if ($(this).val() == $(this).attr('placeholder')) {
						$(this).val('');
						$(this).removeClass('placeholder').removeClass('grey');
					}
				}).blur(
						function() {
							if ($(this).val() == ''
									|| $(this).val() == $(this).attr(
											'placeholder')) {
								$(this).val($(this).attr('placeholder'));
								$(this).addClass('placeholder')
										.addClass('grey');
							}
						});
			}
			$('.projects').mouseenter(function() {
			}).mouseleave(function() {
			})
		});
function load_logo() {
	var logo_animate_type = "easeOutBounce";
	var logo = "/path/to/image";
	var top_logo = "/path/to/image";
	var bottom_logo = "/path/to/image";
	var $img = $(document.createElement("img"));
	var $img1 = $(document.createElement("img"));
	var $img2 = $(document.createElement("img"));
	$img.attr('src', logo).hide();
	$img1.attr('src', top_logo).hide();
	$img2.attr('src', bottom_logo).hide();
	$('#logo-animation #top-part').html($img1);
	$('#logo-animation #bottom-part').html($img2);
	$img1.stop().show('slide', {
		'direction' : 'up',
		'easing' : logo_animate_type
	}, speed);
	$img2.stop().show('slide', {
		'direction' : 'down',
		'easing' : logo_animate_type
	}, speed, function() {
		$('#logo-animation').html($img.show());
	});
}
function LoadContent(hashval) {
	var content_len = $('.animatebox:visible').length;
	if ($('#projects-container .project-animatebox:visible').length > 0) {
		var temp_len = $('#projects-container .project-animatebox:visible').length;
		$('#projects-container .project-animatebox').each(function(i, val) {
			var direction = $(this).find('.animate_direction').val();
			$(this).hide("slide", {
				direction : direction,
				easing : easing
			}, speed, function() {
				if (i == temp_len - 1) {
					$('#projects-container').hide('slide', {
						direction : 'up'
					}, 'fast', function() {
						callback()
					});
				}
			});
		});
		return false;
	}
	$('.animatebox:visible').each(function(i, val) {
		var direction = $(this).find('input.animate_direction').val();
		if (direction != undefined) {
			$(this).hide("slide", {
				direction : direction,
				easing : easing
			}, 800, function() {
				if (i == content_len - 1) {
					$('.top-container:visible').hide('slide', {
						direction : 'up'
					}, 'fast');
					setTimeout(callback, 1000);
				}
			});
		}
	});
	function callback() {
		switch (hashval) {
		case 'home':
		case 'contact':
		case 'resume':
			$('#' + hashval + '-container').show('slide', {
				direction : 'up'
			}, 'fast');
			var $show_divs = $('#' + hashval + '-container .animatebox');
			$show_divs.each(function(i, val) {
				var direction = $(this).find('input.animate_direction').val();
				if (direction != undefined) {
					$(this).show("slide", {
						direction : direction,
						easing : easing
					}, 800);
				}
			});
			break;
		case 'projects':
			LoadProjects();
			break;
		case ((hashval.split("/")).length > 1
				&& (hashval.split("/"))[0] == "projects"
				&& (hashval.split("/"))[0].length > 0 ? hashval : false):
			LoadProjects((hashval.split("/"))[1]);
			break;
		}
	}
}
function LoadProjects(project) {
	if (!project) {
		// console.log("Load All Projects");
		$('#projects-container').show('slide', {
			direction : 'up'
		}, 'fast', showProjects);
		return false;
	}
	// console.log("Load project: " + project);
}
function showProjects() {
	$('#projects-container .project-animatebox').each(function(i, val) {
		var direction = $(this).find('.animate_direction').val();
		$(this).show("slide", {
			direction : direction,
			easing : easing
		}, speed);
	});
}
function hideProjects() {
	$('#projects-container .project-animatebox:visible:first').hide("scale", {
		percent : 100,
		origin : [ 'top', 'left' ],
		easing : easing
	}, 800, hideProjects);
}
function contact() {
	console.log('submitted');
	var $name = $('#name'), $email = $('#email'), $phone = $('#phone'), $notes = $('#notes');
	var ret_val = true;
	if ($.trim($name.val()) == "" || !(checkEmail($email.val()))
			|| $.trim($notes.val()) == "")
		ret_val = false;
	if (ret_val) {
		var mail_options = {
			MailUser : true,
			name : $name.val(),
			phone : $phone.val(),
			email : $email.val(),
			notes : $notes.val()
		};
		$.post('./include/module/mailer.php', mail_options, function(data) {
			$('.contact-form').html(data);
		});
	} else
		console.log('Invalid values');
}
function checkEmail(email) {
	var filter = /^([a-zA-Z0-9_.-])+@[0-9a-zA-Z]+[\.]{1}[0-9a-zA-Z]+[\.]?[0-9a-zA-Z]+$/;
	if (!filter.test(email))
		return false;
	return true;
}
function checkPhone(phone) {
	var filter = /^(\(?\d\d\d\)?)?( |-|\.)?\d\d\d( |-|\.)?\d{4,4}(( |-|\.)?[ext\.]+ ?\d+)?$/;
	if (!filter.test(phone))
		return false;
	return true;
}
function PhoneMasking(phoneobj, phone, event) {
	var entered = String.fromCharCode(event.which);
	var charCode = (event.which) ? event.which : event.keyCode;
	if ((/([^0-9])/.test(entered) || phone.length >= 10) && charCode != 8
			&& charCode != 9 && charCode != 13 && charCode != 37
			&& charCode != 39 && charCode != 46) {
		event.preventDefault();
		return false;
	}
	var tempphone = phone.replace(/([^0-9]*)/g, "");
	phoneobj.value = tempphone;
	tempphone = tempphone.replace(/([a-zA-Z\s()-]*)/g, "");
	if (tempphone.length == 10) {
		tempphone = tempphone.slice(0, 10);
		var subphone = tempphone.split("");
		var formatphone = "(";
		for ( var i = 0; i < subphone.length; i++) {
			if (i < 3)
				formatphone += subphone[i];
			else if (i >= 3 && i < 6) {
				if (i == 3)
					formatphone += ") " + subphone[i];
				else
					formatphone += subphone[i];
			} else if (i >= 6 && i < 10) {
				if (i == 6)
					formatphone += "-" + subphone[i];
				else
					formatphone += subphone[i];
			}
		}
		phoneobj.value = formatphone;
	}
}
