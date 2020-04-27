// var auth = require("../controllers/auth");
// module.exports = function(req, res, next) {
//     var authenticated = false;
//     if (req.cookies.sessionToken) {
//         var user = auth.sessions[req.cookies.sessionToken];
//         if (user) {
//             authenticated = true;
//             next();
//         }
//     }
//     if (!authenticated) {
//         if (req.originalUrl === "/admin") {
//             res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//             res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//             res.setHeader("Expires", "0"); // Proxies.
//             res.redirect(301, "/login");
//         } else {
//             res.sendStatus(401);
//         }
//     }
// };
