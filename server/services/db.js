var winston = require("./logger");
// var MongoClient = require("mongodb").MongoClient;
var url = process.env.DB_URI || "";

var state = {
    db: null,
};

exports.connect = function(done) {
    if (state.db) {
        return done();
    }

    // MongoClient.connect(url, function(err, db) {
    //     winston.info("Connecting to datbase");
    //     if (err) {
    //         return done(err);
    //     }
    //     state.db = db;
    //     // setIndexes();
    //     winston.info("Database successfully connected: " + url);
    //     done();
    // });
};

// function setIndexes() {
//     winston.info("Indexing tweets date");
//     state.db.collection("tweets").createIndex({
//         "content.created_at": 1
//     }, function(err, response) {
//         if (err) {
//             winston.warn("Error creating index on content.created_at");
//             winston.warn(err);
//         } else {
//             winston.info(response);
//         }
//     });
// }

exports.get = function() {
    return state.db;
};

exports.set = function(db) {
    winston.info("setting database instance");
    state.db = db;
    return;
};

exports.close = function(done) {
    if (state.db) {
        winston.info("closing database instance");
        state.db.close(function(err, result) {
            state.db = null;
            state.mode = null;
            done(err);
        });
    }
};
