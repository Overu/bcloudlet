define(function(require, exports, module) {
  var $ = require('jquery'),
      core = require('../../core'),
      common = require('../../common'),
      business = require('../../business'),
      juicer = require('gallery/juicer/0.6.4/juicer');
      $$Event = require('../event');   
      
      
 var _tpl = '<ul class="five">\
               {@each i in range(0,5)}\
                 <li class="${ list[i] }"></li>\
               {@/each}\
             </ul>';
      
  var $$Score = core.create(),
      _pro = $$Score.extend($$Event);
      
  _pro.init = function(score,tpl){
    this.supInit(arguments);
    
    this.score = score;
    this.tpl = tpl;
     
    this.format();
     
    var _score = this.math(this.score),
        _list = this.translate(_score);
        
    this._$final = juicer(this.tpl || _tpl, {list:_list,score:this.score});
  };
  
  _pro.format = function(){
    (this.score > 10) && (this.score = 10);
    (this.score < 0) && (this.score = 0);
  };
      
      
  _pro.math = function(score){
    var _tmp, _result = 0;
    
    if(score >=2  && score < 2.5){
      _result = 1;
     }
      
    if(score >= 2.5 && score < 7.5){
      _tmp = Math.floor(score) + 0.5;
      _tmp = score >= _tmp ? _tmp : _tmp - 1;
      _result = (_tmp - 2.5)/2 + 1.5
    } 
      
    if(score >= 7.5 && score < 9.5){
      _tmp = Math.floor(score) + 0.5;
      _tmp = score >= _tmp ? _tmp : _tmp - 1;
      _result = (_tmp - 7.5) + 4;
    }
      
    if(score >= 9.5){
      _result = 5;
    }
    
    return _result;
  }
  
  _pro.translate = function(score){
    var style = {'1':'red', '0.5':'halfive'},
        _list = [1,0.5],
        _result = [];
        
    _list.forEach(function(i){
      
      while(score > i){
         score = score - i;
         _result.push(style[i + '']);     
      }
      
    });
    
    while(_result.length < 5){
      _result.push('');
    }
    return _result;
  }
  
  _pro.toString = function(){
    return this._$final;
  };

  module.exports = $$Score;
});