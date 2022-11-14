// this function stores the username that the user inputs upon login
function getUsername() {
  var username = document.getElementById("username").value;
  //alert(username);
  return username;
}

// this function stores the password that the user inputs upon login
function getPassword() {
  var password = document.getElementById("password").value;
  //alert(password);
  return password;
}

// this function stores the username that the user inputs upon registering
function getRegistrationUsername() {
  var regUser = document.getElementById("regUser").value;
  //alert(regUser);
  return regUser;
}

// this function stores the email that the user inputs upon registering
function getRegistrationEmail() {
  var regEmail = document.getElementById("regEmail").value;
  //alert(regEmail);
  return regEmail;
}

// this function stores the password that the user inputs upon registering
function getRegistrationPassword() {
  var regPass = document.getElementById("regPass").value;
  //alert(regPass);
  return regPass;
}

// this function stores the re-entered password that the user inputs upon registering
function getReEnteredPassword() {
  var regRePass = document.getElementById("regRePass").value;
  //alert(regRePass);
  return regRePass;
}
