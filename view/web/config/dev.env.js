'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://10.108.11.46/api"'
  // BASE_API: '"http://127.0.0.1:8086/"'
})
