define(function(require, exports, module) {
  var $ = require('jquery');
  
  window.clickpay = function(bookid, deviceid, appid) {
	  $.ajax({
		  type : 'POST',
	      contentType : "application/x-www-form-urlencoded",
	      url : '../../orders',
	      data : {
	    	  book : bookid,
	    	  deviceid : deviceid,
	    	  appid : appid
	      },
	      complete : function(data, textStatus) {
	    	  //window.location.href = 'admin.html';
	      }
	  });
  }
  
});