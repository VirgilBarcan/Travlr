var main = function() {

    $('#post-new-message').keypress(function(event) {
        if (event.which === 13) {           
            alert('You have submitted the post!');
            event.preventDefault();
        }
    })

};


$(document).ready(main);