// var express = require("express");
// var router = express.Router();
// var session = require("express-session");
// var sessions = {};
// var Flutter = require("flutter");
// var twitter = require("../services/twitterClient");
// var winston = require("../services/logger");
// var userModel = require("../models/userModel");
// var rootUrl = twitter.getCallbackUrl();
// var flutter = new Flutter({
//     loginCallback: rootUrl + "oauth/callback",
//     consumerKey: process.env.TWITTER_CONSUMER_KEY,
//     consumerSecret: process.env.TWITTER_CONSUMER_SECRET,
//     completeCallback: rootUrl + "/admin", //"http://localhost:8080/",
//
//     authCallback: function(req, res, next) {
//         if (req.error) {
//             winston.error(req.error);
//             return;
//         }
//
//         getType(req.results.screen_name, function(admin) {
//             if (admin) {
//                 var accessToken = req.session.oauthAccessToken;
//                 //var secret = req.session.oauthAccessTokenSecret;
//                 winston.info("setting user access token");
//                 sessions[accessToken] = {
//                     user: req.results.screen_name
//                 };
//                 res.cookie("sessionToken", accessToken);
//                 winston.info("redirect to home page");
//                 res.redirect("/admin");
//             } else {
//                 res.redirect("/");
//             }
//         });
//     },
//     cache: false
// });
//
// var getType = function(user, done) {
//     userModel.getUserType(user, function(err, admin) {
//         if (err) {
//             return done(false);
//         }
//         done(admin);
//     });
// };
//
// router.get("/", flutter.connect);
//
// router.get("/callback", flutter.auth);
//
// router.get("/auth", function(req, res) {
//     var loggedIn = sessions[req.session.oauthAccessToken];
//     if (loggedIn) {
//         getType(loggedIn.user, function(admin) {
//             res.send(JSON.stringify({
//                 loggedIn: loggedIn,
//                 type: (admin ? "admin" : "default")
//             }));
//         });
//     } else {
//         res.send(JSON.stringify({
//             loggedIn: {
//                 user: "guest"
//             },
//             type: "default"
//         }));
//     }
// });
//
// var removeAdmins = function(admins) {
//     for (var i = 0; i < admins.length; i++) {
//         var admin = admins[i];
//         for (var token in sessions) {
//             if (sessions[token]) {
//                 if (sessions[token].user === admin) {
//                     delete sessions[token];
//                 }
//             }
//         }
//     }
// };
//
// module.exports = {
//     sessions: sessions,
//     removeAdmins: removeAdmins,
//     router: router
// };
