var router = require("express").Router();
var auth = require("./auth");
var ws = require("./websocket");
// var admin = require("./admin");
//
// router.use("/api/tracked", trackedItems);
//
// router.use("/api/split", tweetSplit);
//
// router.use("/api/wallView", wallView);
//
// router.use("/api/tweet", users);
//
// router.use("/oauth", auth.router);
//
// router.use("/admin", admin);
//
router.get("/", function(req, res) {
     res.render("index");
});
//
// ws.init();
//
module.exports = router;
