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

// this function stores the verification code that the user inputs upon verification
function getCode() {
  var code = document.getElementById("code").value;
  //alert(code);
  return code;
}

const getUsername = document.getElementById("username");
const getPassword = document.getElementById("password");
const getLogin = document.getElementById("getLoginInfo");

const URL = "http://localhost:3000";

getLogin.addEventListener("click", postInfo);
async function postInfo(e) {
  e.preventDefault();
  // in case of no input, the function terminates itself
  if (getUsername == "") {
    return;
  }
  const response = await fetch(URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      parcel: getUsername.value,
    }),
  });
}
