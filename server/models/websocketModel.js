// var express = require("express");
// var router = express.Router();
// var winston = require("../services/logger");
// var trackedModel = require("./trackedModel");
//
// var namespace = null;
//
// exports.init = function(ns) {
//     namespace = ns;
//     namespace.on("connection", connection);
// };
//
// var connection = function(socket) {
//     winston.info("User connected to websocket");
//     socket.on("disconnect", disconnect);
// };
//
// var disconnect = function() {
//     winston.info("User disconnected from websocket");
// };
//
// exports.newTweet = function(tweet, type) {
//     if (type !== "no match" && type !== "retweet") {
//         namespace.emit("tweet", {
//             data: tweet,
//             type: type
//         });
//     }
// };
//
// exports.newCounts = function(tweet) {
//     namespace.emit("counts", {
//         id: tweet._id,
//         retweet_count: tweet.content.retweet_count,
//         favorite_count: tweet.content.favorite_count
//     });
// };
//
// exports.triggerClientReload = function() {
//     namespace.emit("reload", {});
// };
//
// exports.emitStreamMessage = function(status) {
//     namespace.emit("stream", {
//         streamStatus: status
//     });
// };
