var winston = require("./logger");
var mongoose = require( 'mongoose' );

// Build the connection string
var localDbURI = 'mongodb://localhost:27017';
var url = process.env.DB_URI || localDbURI;

exports.connect = function(done){
    mongoose.connect(url);

    // CONNECTION EVENTS
    // When successfully connected
    mongoose.connection.on('connected', function () {
      console.log('Mongoose default connection open to ' + url);
      winston.info('Mongoose default connection open to ' + url);
    });

    // If the connection throws an error
    mongoose.connection.on('error',function (err) {
      console.log('Mongoose default connection error: ' + err);
      winston.error('Mongoose default connection error: ' + err);
    });

    // When the connection is disconnected
    mongoose.connection.on('disconnected', function () {
      console.log('Mongoose default connection disconnected');
       winston.error('Mongoose default connection disconnected');
    });

    // If the Node process ends, close the Mongoose connection
    process.on('SIGINT', function() {
      mongoose.connection.close(function () {
        console.log('Mongoose default connection disconnected through app termination');
         winston.error('Mongoose default connection disconnected through app termination');
        process.exit(0);
      });
    });
    return done();
}

require('../schemas/Meal');
