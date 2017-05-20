// var server = require("../../server/server");
// var db = require("../../server/services/db");
// var request = require("request");
// var assert = require("chai").assert;
// var sinon = require("sinon");
// var auth = require("../../server/controllers/auth");
// var twitterClient = require("../../server/services/twitterClient");
//
// var testPort = 52684;
// var baseUrl = "http://localhost:" + testPort;
//
// var testToken = "123123";
//
// describe("auth", function() {
//     var cookieJar;
//     var serverInstance;
//     var client;
//     beforeEach(function() {
//         cookieJar = request.jar();
//         client = {
//             stream: sinon.stub(),
//             get: sinon.stub()
//         };
//         twitterClient.setClient(client);
//         serverInstance = server(testPort);
//     });
//     afterEach(function() {
//         serverInstance.close();
//     });
//
//     function authenticateUser(token, callback) {
//         auth.sessions = {};
//         cookieJar.setCookie(request.cookie("sessionToken=" + token), baseUrl);
//         callback();
//     }
//
//     describe("GET /oauth", function() {
//         var requestUrl = baseUrl + "/oauth";
//         it("redirects to the twitter auth page", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 200);
//                     assert.isTrue(response.request.uri.href.indexOf("https://api.twitter.com/oauth/authenticate?") > -1);
//                     done();
//                 });
//             });
//         });
//         it("requests for admin page redirect to login if authenthication fails", function(done) {
//             var requestUrl = baseUrl + "/admin";
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 200);
//                     assert.equal(response.request.uri.href, "http://localhost:52684/login");
//                     done();
//                 });
//             });
//         });
//         it("responds with 401 if authentithication fails", function(done) {
//             var requestUrl = baseUrl + "/admin/user";
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 401);
//                     done();
//                 });
//             });
//         });
//     });
// });
