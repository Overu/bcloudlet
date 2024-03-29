/* =========================================================
 * bootstrap-acknowledgeinput.js - v0.4
 * http://averagemarcus.github.com/Bootstrap-AcknowledgeInputs/
 * =========================================================
 * Requirements:
 * --------------
 * jQuery (1.9.1 recommended) - http://jquery.com/
 * Bootstrap (css) - http://twitter.github.com/bootstrap
 *
 * Recommended: 
 * --------------
 * Font-Awesome - http://fortawesome.github.com/Font-Awesome/
 * (This allows coloured icons)
 *
 * ========================================================= 
 * Copyright 2013 Marcus Noble
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================= */

(function( $ ) {   
    "use strict";     
    $.fn.acknowledgeinput = function(options){
        var acknowledgeVars = {
            success_color: "#468847",
            danger_color: "#bd362f",
            icon_success: "icon-ok",
            icon_danger: "icon-warning-sign",
            update_on: "change",
            update_blur: "blur",
            update_focus: "focus",
            default_state: "visible"
        };

        var updateIcons = function (inputEl) {
            function modify_classes(isSuccess, iconClass) {
                var colour = isSuccess ? acknowledgeVars.success_color : acknowledgeVars.danger_color;
                inputEl.parent().find('[data-role=acknowledgement]').css('color', colour).find('i').removeClass().addClass(iconClass);
            }
            function isNotNullOrEmpty(value){
                return (inputEl.val() !== "" && inputEl.val() !== null);
            }
            //Setup default
            inputEl.parent().find('[data-role=acknowledgement]').addClass('add-on').find('i').removeClass();
            var re;
            var data_type = inputEl.data('type') === undefined ? "text" : inputEl.data('type');
            var required = inputEl.attr("required") === undefined ? false : inputEl.attr("required").toLowerCase() === "required";

            if (data_type.toLowerCase() === "text") {
                if (isNotNullOrEmpty(inputEl.val())) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "email") {
                re = /^(([^<>()\[\]\\.,;:\s@\"]+(\.[^<>()\[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                var isEmail = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isEmail) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isEmail)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "tel") {
                re = /^(\+)?( |-|\(|\)|[0-9]){4,50}$/;
                var isTel = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isTel) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isTel)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "number") {
                re = /^(\-)?([0-9])+$/;
                var isNumber = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isNumber) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isNumber)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "integer") {
                re = /^(\-)?(([1-9])([0-9])+|0)$/;
                var isInt = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isInt) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isInt)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "decimal") {            	
            	//补充小数点加零
            	re = /^(\-)?([0-9])+$/;
                var isNumber = re.test(inputEl.val());
                if (isNumber) {
                	inputEl.val(inputEl.val()+".00");
				}
                re = /^(\-)?(([0-9])+(\.)([0-9])+|0)$/;
                var isDecimal = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isDecimal) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isDecimal)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "currency") {
                re = /^(([0-9])+((\.|,)?([0-9]){2,2})?)$/;
                var isCurrency = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isCurrency) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isCurrency)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "colour" || data_type.toLowerCase() === "color") {
                var isColour = $('<span></span>').css({ color : 'transparent' }).css({ color : inputEl.val() }).css('color') !== 'transparent';
                
                if (isNotNullOrEmpty(inputEl.val()) && isColour) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isColour)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            } else if (data_type.toLowerCase() === "url") {
                re = /^(https?:\/\/)?([\da-z\.\-]+)\.([a-z\.]{2,6})([\/\w \.\-]*)*\/?$/;
                var isUrl = re.test(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isUrl) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isUrl)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            }
            else if (data_type.toLowerCase() === "password") {
            	var isPassword = checkPwd(inputEl.val());

                if (isNotNullOrEmpty(inputEl.val()) && isPassword) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isPassword)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            }else if (data_type.toLowerCase() === "confirmpwd") {
            	var isChkPwd = confirmPwd(inputEl.val());               

                if (isNotNullOrEmpty(inputEl.val()) && isChkPwd) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isChkPwd)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            }else if (data_type.toLowerCase() === "mobile") {
            	 re = /^(13|15|18)[0-9]{9}$/;
                 var isMobile = re.test(inputEl.val());        

                if (isNotNullOrEmpty(inputEl.val()) && isMobile) {
                    modify_classes(true, acknowledgeVars.icon_success);
                } else if (required || (isNotNullOrEmpty(inputEl.val()) && !isMobile)) {
                    modify_classes(false, acknowledgeVars.icon_danger);
                }
            }
        };

        var confirmPwd = function(value){
        	var inputObj = $("input[type='password']");
        	var pwd1 = $.trim(inputObj.eq(0).val());
        	var pwd2 = $.trim(inputObj.eq(1).val());
        	if(pwd2!=''){
        		if(pwd1 == pwd2){	
        			return true;
        		}else{
        			return false;
        		}
        	}else{
        		return false;
        	}
        	
        };
        	
        var checkPwd = function(value){
        	if(value.length < 6 )
        	{		
        		return false;        		
        	}else{
        		return true;
        	}
        };
        
        
        $.extend(acknowledgeVars, options);

        $('[data-role=acknowledge-input]').find('input:not([type=radio]):not([type=checkbox]),textarea,select').each(function () {
            updateIcons($(this));
            if (acknowledgeVars.default_state != 'visible') {
                $(this).parent().find('[data-role=acknowledgement]').addClass('add-on').find('i').removeClass();
            }
        }).on(acknowledgeVars.update_on, function () {
            updateIcons($(this));
        }).on(acknowledgeVars.update_blur, function () {
            updateIcons($(this));
        }).on(acknowledgeVars.update_focus, function () {
            updateIcons($(this));
        });
    };
}) ( window.jQuery );