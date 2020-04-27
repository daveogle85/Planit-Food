// var express = require("express");
// var router = express.Router();
// var ws = require("../services/ws");
// var wsModel = require("../models/websocketModel");
//
// router.init = function() {
//     var namespace = ws.getWS();
//     wsModel.init(namespace);
// };
//
// router.newTweet = function(tweet, type) {
//     wsModel.newTweet(tweet, type);
// };
//
// router.newCounts = function(tweet) {
//     wsModel.newCounts(tweet);
// };
// 
// router.emitStreamMessage = function(status) {
//     wsModel.emitStreamMessage(status);
// };
//
// module.exports = router;
