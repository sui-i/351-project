// Unused code

var username = document.getElementById("username");
var password = document.getElementById("password");
var form = document.getElementById("form");
var formData = new FormData();
form.addEventListener("submit", function (e) {
  e.preventDefault();
  if (username.value && password.value) {
    formData.append("username", username.value);
    formData.append("password", password.value);
    username.value = "";
    password.value = "";
    window.location.href = "/static/frontPage.html";
  }
});
