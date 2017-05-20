var server = require("./server/server");
var port = process.env.PORT || 3000;
server(port);
console.log("Server running on port " + port);
