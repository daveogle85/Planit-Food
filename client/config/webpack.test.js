var webpack = require('webpack');
var helpers = require('./helpers');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    devtool: 'inline-source-map',

    resolve: {
        extensions: [
            '.ts', '.js'
        ],
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
                        options: {
                            configFileName: helpers.root('tsconfig.json')
                        }
                    },
                    'angular2-template-loader'
                ]
            }, {
                test: /\.html$/,
                loader: 'html-loader'

            }, {
                test: /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)$/,
                loader: 'null-loader'
            }, {
                test: /\.css$/,
                exclude: helpers.root('app'),
                loader: 'null-loader'
            }, {
                test: /\.scss$/,
                exclude: /node_modules/,
                loaders: ['raw-loader', 'sass-loader'] // sass-loader not scss-loader
            }, {
                test: /\.css$/,
                include: helpers.root('app'),
                loader: ['to-string-loader', 'css-loader']
            }
        ]
    },

    plugins: [
        new webpack.ContextReplacementPlugin(
        // The (\\|\/) piece accounts for path separators in *nix and Windows
        /angular(\\|\/)core(\\|\/)(esm(\\|\/)src|src)(\\|\/)linker/, helpers.root('./app'), {}),
        new webpack.ProvidePlugin({$: 'jquery', jQuery: 'jquery', "window.jQuery": 'jquery'})
    ]
}
