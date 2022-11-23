var net = require("net");
var client = net.connect(3000, "localhost");
client.write("Hello from node.js");
client.end();
