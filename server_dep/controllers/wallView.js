// var express = require("express");
// var router = express.Router();
// var wallViewModel = require("../models/wallViewModel");
// var auth = require("../middleware/auth");
// var winston = require("../services/logger");
// var wsModel = require("../models/websocketModel");
//
// router.get("/", function(req, res) {
//     wallViewModel.getWallView(function(err, response) {
//         if (err) {
//             winston.warn("error getting wall view");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Wall view retrived");
//             res.json(response);
//         }
//     });
// });
//
// router.get("/scrollDuration", function(req, res) {
//     wallViewModel.getScrollDuration(function(err, response) {
//         if (err) {
//             winston.warn("error getting scroll duration");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Scroll duration retrived");
//             res.json(response);
//         }
//     });
// });
//
// router.put("/scrollDuration", auth, function(req, res) {
//     var scrollDuration = req.body.scrollDuration;
//     wallViewModel.setScrollDuration(scrollDuration, function(err, response) {
//         if (err) {
//             winston.warn("Error in setting scroll duration");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Successfully set scroll duration");
//             res.sendStatus(204);
//             wsModel.triggerClientReload();
//         }
//     });
// });
//
// router.get("/cycleDuration", function(req, res) {
//     wallViewModel.getCycleDuration(function(err, response) {
//         if (err) {
//             winston.warn("error getting cycle duration");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Cycle duration retrived");
//             res.json(response);
//         }
//     });
// });
//
// router.put("/cycleDuration", auth, function(req, res) {
//     var cycleDuration = req.body.cycleDuration;
//     wallViewModel.setCycleDuration(cycleDuration, function(err, response) {
//         if (err) {
//             winston.warn("Error in setting cycle duration");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Successfully set cycle duration");
//             res.sendStatus(204);
//             wsModel.triggerClientReload();
//         }
//     });
// });
//
// router.get("/numColumns", function(req, res) {
//     wallViewModel.getNumColumns(function(err, response) {
//         if (err) {
//             winston.warn("error getting number of columns");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Number of columns retrived");
//             res.json(response);
//         }
//     });
// });
//
// router.put("/numColumns", auth, function(req, res) {
//     var numColumns = +req.body.numColumns;
//     wallViewModel.setNumColumns(numColumns, function(err, response) {
//         if (err) {
//             winston.warn("Error in setting number of columns");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Successfully set number of columns");
//             res.sendStatus(204);
//             wsModel.triggerClientReload();
//         }
//     });
// });
//
// // Update the currently active wall view:
// // /api/wallView/wall will set wall as the current view
// router.put("/:name", auth, function(req, res) {
//     wallViewModel.setActiveWallView(req.params.name, function(err, response) {
//         if (err) {
//             winston.warn("error setting wall view");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Wall view " + response + " set");
//             res.statusCode = 200;
//             res.json(response);
//             wsModel.triggerClientReload();
//         }
//     });
// });
//
// router.get("/wallLimit", function(req, res) {
//     wallViewModel.getCurrentBricksInWall(function(err, response) {
//         if (err) {
//             winston.warn("error getting bricks in wall");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Wall bricks limit retrived");
//             res.json(response);
//         }
//     });
// });
//
// // Update the limit for bricks in the wall view
// // /api/wallView/wallLimit/10 will set number of bricks to 10
// router.put("/wallLimit/:limit", auth, function(req, res) {
//     wallViewModel.setCurrentBricksInWall(req.params.limit, function(err, response) {
//         if (err) {
//             winston.warn("error setting bricks in wall limit");
//             winston.warn(err);
//             res.set("error", err);
//             res.sendStatus(500);
//         } else {
//             winston.info("Bricks in wall set to " + response);
//             res.statusCode = 200;
//             res.json(response);
//             wsModel.triggerClientReload();
//         }
//     });
// });
//
// module.exports = router;
