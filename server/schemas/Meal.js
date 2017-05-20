var mongoose = require( 'mongoose' );

var mealSchema = mongoose.Schema({
    name: String
});

var Meal = module.exports = mongoose.model('Meal', mealSchema);
