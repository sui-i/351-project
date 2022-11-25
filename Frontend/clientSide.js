var username = document.getElementById("username");
var password = document.getElementById("password");
var form = document.getElementById("form");
form.addEventListener("submit", function (e) {
  e.preventDefault();
  if (username.value && password.value) {
    //var string = String(username.value) + "," + String(password.value);
    // var x = JSON.stringify(string);
    socket.emit("output", username.value, password.value);
    username.value = "";
    password.value = "";
  }
});
