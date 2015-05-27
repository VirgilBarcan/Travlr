// checking mail validity
function isValidEmail(email){
	var re = /\S+@\S+\.\S+/;
    return re.test(email);
	/*
	if (email === "virgil.barcan@info.uaic.ro"){
		return true;
	}
	else{
		return false;
	}
	*/
}

// checking if username is not a void string
function isValidUsername(username){
    return username.length > 0;
}

//check email validity while user inserts the email and respond with appropriate style
function OnInputEmailLogin (event) {
    if (event.target.value === ''){
        $('#email-login').removeClass('has-error');
        $('#email-login-glyphicon').removeClass('glyphicon-remove');
        $('#email-login').removeClass('has-success');
        $('#email-login-glyphicon').removeClass('glyphicon-ok');
        $('#username-login').attr("disabled", null);
    }
    else {
        var isValid = isValidEmail(event.target.value);
        $('#email-login').addClass('has-error');
        $('#email-login-glyphicon').addClass('glyphicon-remove');
        $('#username-login').attr("disabled", 'disabled');
        $('#username-login-div').removeClass('has-error');
        $('#username-login-div').removeClass('has-success');
        if (isValid === true) {
            document.getElementById('email-login').className += ' has-success';
            document.getElementById('email-login-glyphicon').className += ' glyphicon-ok';
            $('#email-login').removeClass('has-error');
            $('#email-login-glyphicon').removeClass('glyphicon-remove');
        }
        else {
            $('#email-login').addClass('has-error');
            $('#email-login-glyphicon').addClass('glyphicon-remove');
        }
    }
}

function OnChangeEmailLogin(event) {
    var isValid = isValidEmail(event.target.value);
    if (isValid === true){
        document.getElementById('email-login').className += ' has-success';
        document.getElementById('email-login-glyphicon').className += ' glyphicon-ok';
        $('#email-login').removeClass('has-error');
        $('#email-login-glyphicon').removeClass('glyphicon-remove');
    }
    else{
    }
}

function OnInputUsernameLogin(event) {
    if (event.target.value === ''){
        $('#email-login-input').attr("disabled", null);
    }
    else{
        $('#email-login-input').attr("disabled", 'disabled');
    }
}

function OnChangeUsernameLogin(event) {
    if (event.target.value === '') {
        document.getElementById('username-login-div').className += ' has-error';
    }
    else {
        var isValid = isValidUsername(event.target.value);
        if (isValid === true) {
            $('#username-login-div').removeClass('has-error');
            document.getElementById('username-login-div').className += ' has-success';
        }
    }
}

function OnInputEmailRegister (event) {
    if (event.target.value === ''){
        $('#email-register').removeClass('has-error');
        $('#email-register-glyphicon').removeClass('glyphicon-remove');
        $('#email-register').removeClass('has-success');
        $('#email-register-glyphicon').removeClass('glyphicon-ok');
    }
    else{
        var isValid = isValidEmail(event.target.value);
        $('#email-register').addClass('has-error');
        $('#email-register-glyphicon').addClass('glyphicon-remove');
        if (isValid === true){
            document.getElementById('email-register').className += ' has-success';
            document.getElementById('email-register-glyphicon').className += ' glyphicon-ok';
            $('#email-register').removeClass('has-error');
            $('#email-register-glyphicon').removeClass('glyphicon-remove');
        }
        else{
            $('#email-register').addClass('has-error');
            $('#email-register-glyphicon').addClass('glyphicon-remove');
        }
    }
}

function OnChangeEmailRegister(event) {
    var isValid = isValidEmail(event.target.value);
    if (isValid === true){
        document.getElementById('email-register').className += ' has-success';
        document.getElementById('email-register-glyphicon').className += ' glyphicon-ok';
        $('#email-register').removeClass('has-error');
        $('#email-register-glyphicon').removeClass('glyphicon-remove');
    }
}

function OnChangeUsernameRegister(event) {
    if (event.target.value === '') {
        document.getElementById('username-register-div').className += ' has-error';
    }
    else {
        var isValid = isValidUsername(event.target.value);
        if (isValid === true) {
            $('#username-register-div').removeClass('has-error');
            document.getElementById('username-register-div').className += ' has-success';
        }
    }
}