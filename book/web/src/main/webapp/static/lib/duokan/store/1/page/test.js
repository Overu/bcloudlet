define("duokan/store/1/page/test", function(require, exports, module) {
	
	var $ = require('jquery');
	
		 function bookpage(a, b) {
			var c = Math.ceil(a);
			$("#total-page").html(c),
			1 >= c && $(".w-page").hide();
			for (var e = 1; c >= e; e++) {
				if (e > 5 && c > e) var f = "<li style='display:none'><a>" + e + "</a></li>";
				else var f = "<li><a>" + e + "</a></li>";
				$("#bookpage-a").append(f)
			}
			c > 5 && $("#bookpage-a>li").eq(4).after("<li class='ellipsis'>...</li>"),
			$("#bookpage-a>li").eq(0).addClass("crt"),
			$(".pre").addClass("init"),
			$("#bookpage-a").delegate("li", "click",
			function() {
				if ("crt" == $(this).attr("class")) return ! 1;
				$(".ellipsis").empty().remove(),
				$("#bookpage-a>li").not($("#bookpage-a>li:first")).not($("#bookpage-a>li:last")).hide();
				var a = $(this).index(),
				e = a - 2,
				f = a + 3;
				0 > e && (e = 0),
				$("#bookpage-a>li").slice(e, f).show(),
				$("#bookpage-a>li:eq(1)").is(":hidden") && $("#bookpage-a>li:last").prev("li").is(":hidden") ? ($("#bookpage-a>li").eq(f).after("<li class='ellipsis'>...</li>"), $("#bookpage-a>li").eq(e).before("<li class='ellipsis'>...</li>")) : $("#bookpage-a>li:eq(1)").is(":visible") ? 5 == c && 1 == a || $("#bookpage-a>li").eq(f).after("<li class='ellipsis'>...</li>") : $("#bookpage-a>li:last").prev("li").is(":visible") && d("#bookpage-a>li").eq(e).before("<li class='ellipsis'>...</li>"),
				$("#bookpage-a>li").removeClass("crt"),
				$(this).addClass("crt");
				var g = $(this).text();
				$("#one-page").html(g),
				g == c && c > 1 ? d(".next").addClass("init") : $(".next").removeClass("init"),
				$(this).index() > 0 ? (comment.start = 10 * (g - 1) + 1, book.category.listStart = 10 * (g - 1), $(".pre").removeClass("init")) : 0 == $(this).index() && (comment.start = 1, category.listStart = 0, $(".pre").addClass("init"), $(".next").removeClass("init")),
				"replylist" == b ? comment.comment_LIst(10) : "commentlist" == b ? comment.commentLIst() : "category" == b && category.categoryLIst()
			}),
			$(".pre").click(function() {
				return $(this).hasClass("init") ? !1 : ($("#bookpage-a>li.crt").prev().click(), void 0)
			}),
			$(".next").click(function() {
				return $(this).hasClass("init") ? !1 : ($("#bookpage-a>li.crt").next().click(), void 0)
			})
		}
		 bookpage(99, 5);
		
});