var main = function() {

	$('.jscroll').jscroll({
		debug: false,
		autoTrigger: true,
		autoTriggerUntil: false,
		loadingHtml: '<img src="./images/loading.gif" alt="Loading" /> Loading...',
		padding: 20,
		nextSelector: 'a.jscroll-next:last',
		contentSelector: 'li',
		pagingSelector: '',
		callback: false
	});

	$('#post-new-message').keypress(function(event) {
		if (event.which === 13) {			
			alert('You have submitted the post!');
			event.preventDefault();
		}
	})

};


$(document).ready(main);