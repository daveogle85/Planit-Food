var testing = require("selenium-webdriver/testing");
var assert = require("chai").assert;
var helpers = require("./e2eHelpers");

// testing.describe("end to end", function() {
//     this.timeout(20000);
//     testing.before(helpers.setupDriver);
//     testing.beforeEach(helpers.setupServer);
//     testing.afterEach(helpers.teardownServer);
//     testing.after(function() {
//         helpers.teardownDriver();
//     });
//
//     testing.describe("on page load", function() {
//         testing.it("displays login button", function() {
//             helpers.navigateToLoginSite();
//             helpers.getLoginText().then(function(text) {
//                 assert.equal(text, "Login with Twitter");
//             });
//         });
//         testing.it("login button click takes user to the admin", function() {
//             helpers.navigateToLoginSite();
//             helpers.loginToSite();
//             helpers.pauseTest(4000).then(function() {
//                 helpers.elementExistsById("admin", "admin").then(function(exists) {
//                     assert.isTrue(exists, "admin page should be displayed");
//                 });
//             });
//         });
//     });
    // testing.describe("Admin page", function() {
    //     testing.it("Displays the category component with correct category sliders", function() {
    //         helpers.teardownDriver();
    //         helpers.setupDriver();
    //         helpers.navigateToSite();
    //         helpers.loginToSite();
    //         helpers.elementExistsById("admin", "admin").then(function(exists) {
    //             assert.isTrue(exists, "admin page should be displayed");
    //             helpers.elementExistsById("users-slider").then(function(users) {
    //                 assert.isTrue(users, "user slider should be displayed");
    //                 helpers.elementExistsById("hash-slider").then(function(hash) {
    //                     assert.isTrue(hash, "hash slider should be displayed");
    //                     helpers.elementExistsById("speakers-slider").then(function(speakers) {
    //                         assert.isTrue(speakers, "speakers slider should be displayed");
    //                     });
    //                 });
    //             });
    //         });
    //     });
    //     testing.it("Displays the chart component", function() {
    //         helpers.teardownDriver();
    //         helpers.setupDriver();
    //         helpers.navigateToSite();
    //         helpers.loginToSite();
    //         helpers.elementExistsById("chart-component", "admin").then(function(exists) {
    //             assert.isTrue(exists, "chart should be displayed");
    //         });
    //     });
    // });
// });
