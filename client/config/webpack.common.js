var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var helpers = require('./helpers');

module.exports = {
  entry: {
    'polyfills': './app/polyfills.ts',
    'vendor': './app/vendor.ts',
    'app': './app/main.ts'
  },

  resolve: {
    extensions: ['.ts', '.js', '.sass', '.css'],
    modules: [helpers.root('app'), helpers.root('node_modules')],
    alias: {
        jquery: 'jquery/src/jquery'
    }
  },

  module: {
    rules: [
      {
        test: /\.ts$/,
        loaders: [
          {
            loader: 'awesome-typescript-loader',
            options: { configFileName: helpers.root('tsconfig.json') }
          } , 'angular2-template-loader'
        ]
      },
      {
        test: /\.json$/,
        use: 'json-loader'
      },
      {
        test: /\.html$/,
        loader: 'html-loader'
      },
      {
        test: /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)$/,
        loader: 'file-loader?name=assets/[name].[hash].[ext]'
      },
      {
        test: /\.css$/,
        exclude: [helpers.root('app')],
        loader: ExtractTextPlugin.extract({ fallbackLoader: 'style-loader', loader: 'css-loader' })
      },
      {
        test: /\.scss$/,
        exclude: /node_modules/,
        loaders: ['exports-loader?module.exports.toString()','css-loader','resolve-url-loader','sass-loader?sourceMap']
      },
      {
        test: /\.css$/,
        include: helpers.root('app'),
        use: [
            {loader: 'css-loader'},
            {loader: 'reslove-url-loader'},
            {loader: 'to-string-loader'}
        ]
    }]
  },

  plugins: [
    // Workaround for angular/angular#11580
    new webpack.ContextReplacementPlugin(
      // The (\\|\/) piece accounts for path separators in *nix and Windows
      /angular(\\|\/)core(\\|\/)(esm(\\|\/)app|app)(\\|\/)linker/,
      helpers.root('app'), // location of your src
      {} // a map of your routes
    ),

    // Webpack is not smart enough to keep the vendor code out of the app.js bundle. The CommonsChunkPlugin does that job
    new webpack.optimize.CommonsChunkPlugin({
      name: ['app', 'vendor', 'polyfills']
    }),

    new HtmlWebpackPlugin({
      template: 'index.html'
    })
  ]
};
