define("duokan/store/1/page/test", function(require, exports, module) {

	var $ = require('jquery');

	function getArgs(strParame) {
		var args = new Object();
		var query;
		if (arguments.length == 2)
			query = arguments[1];
		else
			query = location.search.substring(1);

		var pairs = query.split("&");
		for ( var i = 0; i < pairs.length; i++) {
			var pos = pairs[i].indexOf('=');
			if (pos == -1)
				continue;
			var argname = pairs[i].substring(0, pos);
			var value = pairs[i].substring(pos + 1);
			value = decodeURIComponent(value);
			args[argname] = value;
		}
		return args[strParame];
	}
	
	var index = getArgs("index");

	function bookpage(total, page, index) { // 分页
		var count = Math.ceil(total);
		$('#total-page').html(count)
		if (count <= 1) {
			$('.w-page').hide();
		}
		for ( var i = 1; i <= count; i++) {
			if (i > 5 && i < count) {
				var li = "<li style='display:none'><a href='/books/t/文学?index=" + i + "'>" + i + "</a></li>";
			} else {
				var li = "<li><a href='/books/t/文学?index=" + i + "'>" + i + "</a></li>";
			}
			$('#bookpage-a').append(li)
		}
		if (count > 5) {
			$('#bookpage-a>li').eq(4).after("<li class='ellipsis'>...</li>")
		}
		$('#bookpage-a>li').eq(0).addClass('crt')
		$('.pre').addClass('init')
		if (index >= 1 && index <= count) {
			$("#bookpage-a > li").not(".ellipsis").eq(index - 1).each(function() {
				if ($(this).attr('class') == 'crt') {
					return false
				}
				$('.ellipsis').empty().remove();
				$('#bookpage-a>li').not($('#bookpage-a>li:first')).not(
						$('#bookpage-a>li:last')).hide();
				var thisIndex = $(this).index();
				var beginIndex = thisIndex - 2;
				var endIndex = thisIndex + 3;
				if (beginIndex < 0) {
					beginIndex = 0
				}
				$('#bookpage-a>li').slice(beginIndex, endIndex).show()
				if ($('#bookpage-a>li:eq(1)').is(':hidden')
						&& $('#bookpage-a>li:last').prev("li")
								.is(':hidden')) {
					$('#bookpage-a>li').eq(endIndex).after(
							"<li class='ellipsis'>...</li>");
					$('#bookpage-a>li').eq(beginIndex).before(
							"<li class='ellipsis'>...</li>");
				} else if ($('#bookpage-a>li:eq(1)').is(':visible')) {
					if (count == 5 && thisIndex == 1) {

					} else {
						$('#bookpage-a>li').eq(endIndex).after(
								"<li class='ellipsis'>...</li>");
					}
				} else if ($('#bookpage-a>li:last').prev("li").is(
						':visible')) {
					$('#bookpage-a>li').eq(beginIndex).before(
							"<li class='ellipsis'>...</li>");
				}
				$('#bookpage-a>li').removeClass('crt')
				$(this).addClass('crt')
				var number = $(this).text();
				$('#one-page').html(number)
				if (number == count && count > 1) {
					$('.next').addClass('init')
				} else {
					$('.next').removeClass('init')
				}
				if ($(this).index() > 0) {
					$('.pre').removeClass('init')
				} else if ($(this).index() == 0) {
					$('.pre').addClass('init')
					$('.next').removeClass('init')
				}
			});

		/*$('#bookpage-a').delegate('li', 'click', function() {
					if ($(this).attr('class') == 'crt') {
						return false
					}
					$('.ellipsis').empty().remove();
					$('#bookpage-a>li').not($('#bookpage-a>li:first')).not(
							$('#bookpage-a>li:last')).hide();
					var thisIndex = $(this).index();
					var beginIndex = thisIndex - 2;
					var endIndex = thisIndex + 3;
					if (beginIndex < 0) {
						beginIndex = 0
					}
					$('#bookpage-a>li').slice(beginIndex, endIndex).show()
					if ($('#bookpage-a>li:eq(1)').is(':hidden')
							&& $('#bookpage-a>li:last').prev("li")
									.is(':hidden')) {
						$('#bookpage-a>li').eq(endIndex).after(
								"<li class='ellipsis'>...</li>");
						$('#bookpage-a>li').eq(beginIndex).before(
								"<li class='ellipsis'>...</li>");
					} else if ($('#bookpage-a>li:eq(1)').is(':visible')) {
						if (count == 5 && thisIndex == 1) {

						} else {
							$('#bookpage-a>li').eq(endIndex).after(
									"<li class='ellipsis'>...</li>");
						}
					} else if ($('#bookpage-a>li:last').prev("li").is(
							':visible')) {
						$('#bookpage-a>li').eq(beginIndex).before(
								"<li class='ellipsis'>...</li>");
					}
					$('#bookpage-a>li').removeClass('crt')
					$(this).addClass('crt')
					var number = $(this).text();
					$('#one-page').html(number)
					if (number == count && count > 1) {
						$('.next').addClass('init')
					} else {
						$('.next').removeClass('init')
					}
					if ($(this).index() > 0) {
						comment.start = (number - 1) * 10 + 1;
						book.category.listStart = (number - 1) * 10;
						$('.pre').removeClass('init')
					} else if ($(this).index() == 0) {
						comment.start = 1;
						category.listStart = 0
						$('.pre').addClass('init')
						$('.next').removeClass('init')
					}
					if (page == 'replylist') {
						comment.comment_LIst(10)
					} else if (page == 'commentlist') {
						comment.commentLIst()
					} else if (page == 'category') {
						category.categoryLIst()
					}
				})*/
		}
		$('.pre').click(function() { // 上一页
			if ($(this).hasClass('init')) {
				return false;
			}
			var _index =  parseInt(index) - 1;
			location.href = "/books/t/文学?index="+ _index;
			//$('#bookpage-a>li.crt').prev().click();
		})

		$('.next').click(function() {
			if ($(this).hasClass('init')) {
				return false;
			}
			var _index = parseInt(index) + 1;
			location.href = "/books/t/文学?index="+ _index;
			//$('#bookpage-a>li.crt').next().click();
		})

	}
	bookpage(66, 5, index);

});