// var express = require("express");
// var createServer = require("../../server/server");
// var webdriver = require("selenium-webdriver");
// var istanbul = require("istanbul");
// var path = require("path");
// var fs = require("fs");
//
// var testPort = 52684;
// var baseUrl = "http://localhost:" + testPort;
// var instrumenter = new istanbul.Instrumenter();
// var collector = new istanbul.Collector();
// var gatheringCoverage = process.env.running_under_istanbul;
// var coverageFilename = "build_artifacts/coverage-e2e.json";
// var driver;
// var server;
//
// module.exports.setupDriver = function() {
//     driver = new webdriver.Builder().forBrowser("chrome").build();
// };
//
// module.exports.setupServer = function(done) {
//     server = createServer(testPort, done);
// };
//
// module.exports.teardownServer = function(done) {
//     server.close(done);
// };
//
// module.exports.teardownDriver = function() {
//     driver.quit();
// };
//
// module.exports.navigateToLoginSite = function() {
//     driver.get(baseUrl + "/login");
//     driver.wait(webdriver.until.elementLocated(webdriver.By.id("loginscreen")));
// };
//
// module.exports.getLoginText = function() {
//     return driver.findElement(webdriver.By.css("#loginButton")).getText();
// };
//
// module.exports.elementExistsById = function(id, waitId) {
//     if (waitId) {
//         driver.wait(webdriver.until.elementLocated(webdriver.By.id(waitId)));
//     }
//     return driver.findElement(webdriver.By.id(id)).then(function(arg) {
//         return true;
//     }, function(err) {
//         return false;
//     });
// };
//
// module.exports.clickElementById = function(id) {
//     return driver.findElement(webdriver.By.id(id)).click();
// };
//
// module.exports.pauseTest = function(n) {
//     return driver.sleep(n);
// };
//
// module.exports.loginToSite = function() {
//     driver.findElement(webdriver.By.id("loginButton")).click();
//     // driver.wait(webdriver.until.elementLocated(webdriver.By.id("oauth_form")));
//     // driver.findElement(webdriver.By.id("username_or_email")).sendKeys("@cw_scott_logic");
//     // driver.findElement(webdriver.By.id("password")).sendKeys("sltestpw1");
//     // driver.findElement(webdriver.By.id("allow")).click();
//     driver.wait(webdriver.until.elementLocated(webdriver.By.id("wall")));
// };
