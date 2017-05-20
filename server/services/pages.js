var mongoose = require( 'mongoose' );
var winston = require("./logger");

var meal = function () {
    return new Promise(function(fulfill, reject) {
        var Meal = mongoose.model('Meal');
        Meal.create({
          name : "Chips 'n' mash",
      }, function(err, meal) {
          var strOutput;
          if (err) {
            console.log(err);
            strOutput = 'Oh dear, we\'ve got an error';
            reject(strOutput);
          } else {
            console.log('Meal created: ' + meal);
            strOutput = meal.name;
            fulfill(strOutput);
          }
        });
    });
}

var schemas = [{
    name: "Meal",
    create: meal
}];

exports.index = async function () {
    for ( var i = 0; i < schemas.length; i++ ) {
        winston.info("Creating schema " + schemas[i].name);
        await schemas[i].create();
    }
};
