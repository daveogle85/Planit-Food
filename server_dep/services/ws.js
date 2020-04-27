// var io = require("socket.io");
var http = require("http");
//
// var ws = null;
//
var init = function(app) {
    var server = http.createServer(app);
    // ws = io(server);
    return server;
};
//
// var getWS = function() {
//     return ws;
// };
//
module.exports = {
    // ws: ws,
    init: init,
    // getWS: getWS
};
