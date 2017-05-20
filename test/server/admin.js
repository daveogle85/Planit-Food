// var server = require("../../server/server");
// var db = require("../../server/services/db");
// var request = require("request");
// var assert = require("chai").assert;
// var sinon = require("sinon");
// var admin = require("../../server/controllers/admin");
// var auth = require("../../server/controllers/auth");
// var twitterClient = require("../../server/services/twitterClient");
// var userModel = require("../../server/models/userModel");
//
// var testPort = 52684;
// var baseUrl = "http://localhost:" + testPort;
// var testToken = "123123";
//
// var admins = [{
//     _id: "admins",
//     users: ["user1"]
// }];
//
// var blacklist = [{
//     _id: "blacklist",
//     users: ["badUser1"]
// }];
//
// var badUser1 = {_id: "badUser1"};
//
// describe("admin", function() {
//     var cookieJar;
//     var mockDb;
//     var client;
//     var serverInstance;
//     var dbCollections;
//     var adminUsers;
//     var blacklistObj;
//     beforeEach(function() {
//         cookieJar = request.jar();
//         dbCollections = {
//             users: {
//                 find: sinon.stub(),
//                 update: sinon.stub(),
//                 remove: sinon.stub(),
//                 insert: sinon.stub()
//             }
//         };
//         client = {
//             stream: sinon.stub(),
//             get: sinon.stub()
//         };
//         mockDb = {
//             collection: sinon.stub()
//         };
//
//         adminUsers = {
//             toArray: sinon.stub()
//         };
//
//         blacklistObj = {
//             toArray: sinon.stub()
//         };
//
//         adminUsers.toArray.callsArgWith(0, null, admins);
//         blacklistObj.toArray.callsArgWith(0, null, blacklist);
//
//         dbCollections.users.update.callsArgWith(2, null, "Updated");
//         dbCollections.users.remove.yields("", "");
//         dbCollections.users.find.withArgs({
//             "_id": "admins"
//         }).returns(adminUsers);
//         dbCollections.users.find.withArgs({
//             "_id": "blacklist"
//         }).returns(blacklistObj);
//         dbCollections.users.insert.yields("", "");
//         mockDb.collection.withArgs("users").returns(dbCollections.users);
//         db.set(mockDb);
//         twitterClient.setClient(client);
//         serverInstance = server(testPort);
//     });
//     afterEach(function() {
//         serverInstance.close();
//     });
//
//     function authenticateUser(token, callback) {
//         auth.sessions[token] = {
//             user: "testuser"
//         };
//         cookieJar.setCookie(request.cookie("sessionToken=" + token), baseUrl);
//         callback();
//     }
//
//     describe("GET user type", function () {
//         it("returns true if the user is admin", function() {
//             var admin = userModel.getUserType("user1", function(err, response) {
//                 assert.isTrue(response);
//             });
//         });
//         it("returns false if the user is admin", function() {
//             var admin = userModel.getUserType("bob", function(err, response) {
//                 assert.isFalse(response);
//             });
//         });
//     });
//     describe("GET /admin/user", function() {
//         var requestUrl = baseUrl + "/admin/user";
//         it("responds with status code 401 if user not authenticated", function(done) {
//             request({
//                 url: requestUrl,
//                 jar: cookieJar
//             }, function(error, response) {
//                 assert.equal(response.statusCode, 401);
//                 done();
//             });
//         });
//         it("responds with status code 200 if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 200);
//                     done();
//                 });
//             });
//         });
//         it("responds with a body that is a JSON representation of the list of admin users if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response, body) {
//                     assert.deepEqual(JSON.parse(body), admins[0].users);
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 500 if database error", function(done) {
//             adminUsers.toArray.callsArgWith(0, {
//                 err: "Database error"
//             }, null);
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 500);
//                     done();
//                 });
//             });
//         });
//     });
//
//     describe("POST /admin/user", function() {
//         var requestUrl = baseUrl + "/admin/user";
//         it("responds with status code 401 if user not authenticated", function(done) {
//             request({
//                 method: "POST",
//                 json: admins,
//                 url: requestUrl,
//                 jar: cookieJar
//             }, function(error, response) {
//                 assert.equal(response.statusCode, 401);
//                 done();
//             });
//         });
//         it("responds with status code 201 if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "POST",
//                     json: admins,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 201);
//                     done();
//                 });
//             });
//         });
//         it("responds with an appropriate response if the item was updated if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "POST",
//                     json: admins,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.body, "Created");
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 500 if database error", function(done) {
//             dbCollections.users.remove.callsArgWith(1, {
//                 err: "Database error"
//             }, null);
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "POST",
//                     json: admins,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 500);
//                     done();
//                 });
//             });
//         });
//     });
//
//     describe("GET /admin/blacklist", function() {
//         var requestUrl = baseUrl + "/admin/blacklist";
//         it("responds with status code 401 if user not authenticated", function(done) {
//             request({
//                 url: requestUrl,
//                 jar: cookieJar
//             }, function(error, response) {
//                 assert.equal(response.statusCode, 401);
//                 done();
//             });
//         });
//         it("responds with status code 200 if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 200);
//                     done();
//                 });
//             });
//         });
//         it("responds with a body that is a JSON representation of the list of admin users if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response, body) {
//                     assert.deepEqual(JSON.parse(body), blacklist[0].users);
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 500 if database error", function(done) {
//             blacklistObj.toArray.callsArgWith(0, {
//                 err: "Database error"
//             }, null);
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 500);
//                     done();
//                 });
//             });
//         });
//     });
//
//     describe("POST /admin/blacklist", function() {
//         var requestUrl = baseUrl + "/admin/blacklist";
//         it("responds with status code 401 if user not authenticated", function(done) {
//             request({
//                 method: "POST",
//                 json: blacklist,
//                 url: requestUrl,
//                 jar: cookieJar
//             }, function(error, response) {
//                 assert.equal(response.statusCode, 401);
//                 done();
//             });
//         });
//         it("responds with status code 201 if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "POST",
//                     json: blacklist,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 201);
//                     done();
//                 });
//             });
//         });
//         it("responds with an appropriate response if the item was updated if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "POST",
//                     json: blacklist,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.body, "Created");
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 500 if database error", function(done) {
//             dbCollections.users.remove.callsArgWith(1, {
//                 err: "Database error"
//             }, null);
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "POST",
//                     json: blacklist,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 500);
//                     done();
//                 });
//             });
//         });
//     });
//
//     describe("PUT /admin/blacklist", function() {
//         var requestUrl = baseUrl + "/admin/blacklist";
//         it("responds with status code 401 if user not authenticated", function(done) {
//             request({
//                 method: "PUT",
//                 json: badUser1,
//                 url: requestUrl,
//                 jar: cookieJar
//             }, function(error, response) {
//                 assert.equal(response.statusCode, 401);
//                 done();
//             });
//         });
//         it("responds with status code 201 if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "PUT",
//                     json: badUser1,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 204);
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 500 if database error", function(done) {
//             dbCollections.users.update.callsArgWith(2, {
//                 err: "Database error"
//             }, null);
//             authenticateUser(testToken, function() {
//                 request({
//                     method: "PUT",
//                     json: badUser1,
//                     url: requestUrl,
//                     jar: cookieJar,
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 500);
//                     done();
//                 });
//             });
//         });
//     });
//
//     describe("GET /admin/stream", function() {
//         var requestUrl = baseUrl + "/admin/stream";
//         it("responds with status code 401 if user not authenticated", function(done) {
//             request({
//                 url: requestUrl,
//                 jar: cookieJar
//             }, function(error, response) {
//                 assert.equal(response.statusCode, 401);
//                 done();
//             });
//         });
//         it("responds with status code 400 if user is authenticated", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl,
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 400);
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 200 on getStatus query", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl + "?getStatus=true",
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 200);
//                     done();
//                 });
//             });
//         });
//         it("responds with status code 200 on refreshTweets query", function(done) {
//             authenticateUser(testToken, function() {
//                 request({
//                     url: requestUrl + "?refreshTweets=true",
//                     jar: cookieJar
//                 }, function(error, response) {
//                     assert.equal(response.statusCode, 200);
//                     done();
//                 });
//             });
//         });
//     });
// });
