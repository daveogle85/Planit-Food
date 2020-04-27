// var db = require("../services/db");
// var winston = require("../services/logger");
//
// // Get the currently active wallView
// exports.getWallView = function(done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.find({
//         current: true,
//     }).toArray(function(err, result) {
//         if (err) {
//             return done(err, null);
//         }
//         done(err, result[0]);
//     });
// };
//
// // Set the currently active wall view
// // currentName = the name of the wall view to make current
// exports.setActiveWallView = function(currentName, done) {
//     var wallViews = db.get().collection("wallViews");
//
//     // Remove old active wallView
//     this.getWallView(function(err, result) {
//         if (err) {
//             return done(err, null);
//         }
//         if (result === currentName) {
//             return done(err, currentName);
//         }
//         wallViews.update({
//             current: true
//         }, {
//             $set: {
//                 current: false
//             }
//         }, function(err, response) {
//             if (err) {
//                 return done(err, null);
//             }
//             // Set new active wall view
//             wallViews.update({
//                 name: currentName
//             }, {
//                 $set: {
//                     current: true
//                 }
//             }, function(err, response) {
//                 return done(err, currentName);
//             });
//         });
//     });
// };
//
// // Get the current limit of tweets to show on a wall
// exports.getCurrentBricksInWall = function(done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.find({
//         name: "wall"
//     }).toArray(function(err, result) {
//         if (err) {
//             return done(err, null);
//         }
//         done(err, result[0].limit);
//     });
// };
//
// // Update the limit of bricks to show in the wall
// exports.setCurrentBricksInWall = function(newLimit, done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.update({
//         name: "wall"
//     }, {
//         $set: {
//             "limit": newLimit
//         }
//     }, function(err, response) {
//         return done(err, newLimit);
//     });
// };
//
// exports.getScrollDuration = function(done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.find({
//         name: "random"
//     }).toArray(function(err, response) {
//         if (err) {
//             return done(err, null);
//         }
//         done(err, response[0]);
//     });
// };
//
// exports.setScrollDuration = function(duration, done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.update({
//         name: "random"
//     }, {
//         $set: {
//             "scrollDuration": duration
//         }
//     }, function(err, response) {
//         done(err, response);
//     });
// };
//
// exports.getCycleDuration = function(done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.find({
//         name: "randomWall"
//     }).toArray(function(err, response) {
//         if (err) {
//             return done(err, null);
//         }
//         done(err, response[0]);
//     });
// };
//
// exports.setCycleDuration = function(duration, done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.update({
//         name: "randomWall"
//     }, {
//         $set: {
//             "cycleDuration": duration
//         }
//     }, function(err, response) {
//         done(err, response);
//     });
// };
//
// exports.getNumColumns = function(done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.find({
//         name: "randomWall"
//     }).toArray(function(err, response) {
//         if (err) {
//             return done(err, null);
//         }
//         done(err, response[0]);
//     });
// };
//
// exports.setNumColumns = function(numColumns, done) {
//     var wallViews = db.get().collection("wallViews");
//     wallViews.update({
//         name: "randomWall"
//     }, {
//         $set: {
//             "numColumns": numColumns
//         }
//     }, function(err, response) {
//         done(err, response);
//     });
// };
