define(function(require, exports, module) {
  var $ = require('jquery'),
      core = require('../core'),
      config = require('../config');
  
  for(var version in config.browser){
    if(!!config.browser[version])
      require.async('./' + version);
  }
});