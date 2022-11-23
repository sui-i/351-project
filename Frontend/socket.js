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
  socket.on("output", (login) => {
    console.log(login);
  });
  //console.log("a user conneced");
});
