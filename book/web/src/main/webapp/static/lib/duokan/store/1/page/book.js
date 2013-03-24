define("duokan/store/1/page/../utils", [],function(a, b, c) {
	function l(a) {
		d(a).parents(".w-dropdown-menu").children(".w-dropdown-menu .cnt").is(":hidden"),
		d(".cnt-top").hide(),
		d(a).parents(".w-dropdown-menu").children(".w-dropdown-menu .cnt").toggle()
	}
	function p() {
		var a = e.getCookie("user_id"),
		b = e.getCookie("token"),
		c = e.getCookie("device_id"),
		f = "web";
		url = "/dk_id/api/account/logged/" + encodeURIComponent(a),
		e.delCookie("token"),
		e.delCookie("name"),
		e.delCookie("user_id"),
		e.delBasicCookie("token"),
		e.delBasicCookie("name"),
		e.delBasicCookie("user_id"),
		d.ajax({
			url: url,
			success: function() {},
			data: "token=" + encodeURIComponent(b) + "&deviceid=" + encodeURIComponent(c) + "&appid=" + f + "&format=json",
			dataType: "json",
			type: "post"
		})
	}
	function q(a) {
		setTimeout(function() {
			window.location.href = a
		},
		0)
	}
	function t() {
		console.log("xxx"),
		"" != d("#dk-text").val() && "搜索书名或者作者..." != d("#dk-text").val() ? d("#searchbotton").fadeIn() : d("#searchbotton").fadeOut()
	}
	function x() {
		function a() {
			var a = (d(window).width() - 1010) / 2 + 1016;
			d("#topcontrol").css("left", a),
			d(window).resize(function() {
				var a = (d(window).width() - 1010) / 2 + 1016;
				1010 >= d(window).width() ? d("#topcontrol").css("left", 1010) : d(window).width() > 1010 && d("#topcontrol").css("left", a)
			})
		}
		function b() {
			if (!w) {
				var a = d(document).scrollTop(),
				b = d("#topcontrol");
				a > 0 ? b.show() : b.hide()
			}
		}
		0 == d("#topcontrol").length || w || (d(window).bind("scroll",
		function() {
			b(),
			a()
		}), d("#topcontrol").click(function() {
			d(window).scrollTop(0)
		}))
	}
	function y() {
		d(window).scroll(function() {
			var a = d(document).scrollTop(),
			b = d("#navFix");
			a >= 87 ? b.addClass("w-fixed") : b.removeClass("w-fixed")
		})
	}
	var d = a("gallery/jquery/1.8.3/jquery"),
	e = a("./common"),
	f = function() {
		d("img").each(function() {
			d(this).load(function() {
				d(this).removeClass("f-dpn")
			})
		});
		for (var a = document.getElementsByTagName("img"), b = 0; a.length > b; b++) a[b].complete && d(a[b]).removeClass("f-dpn")
	};
	c.exports = f();
	var g = 0,
	h = d("#related-book>ul>li").size();
	5 >= h && d("#botton-btn").hide();
	var i = 131;
	d("#related-book > ul").width(i * h),
	d(".w-tab-btn>.right").click(function() {
		return Math.abs(g) >= d("#related-book>ul").width() - 655 ? !1 : (d(".w-tab-btn>.left").removeClass("left-final"), g -= 655, d("#related-book>ul").stop(!0, !0), d("#related-book>ul").animate({
			left: g
		},
		"4000",
		function() {
			Math.abs(g) >= d("#related-book>ul").width() - 655 && d(".w-tab-btn>.right").addClass("right-final")
		}), void 0)
	}),
	d(".w-tab-btn>.left").click(function() {
		return 0 == g ? !1 : (d(".w-tab-btn>.right").removeClass("right-final"), g += 655, d("#related-book>ul").stop(!0, !0), d("#related-book>ul").animate({
			left: g
		},
		"4000",
		function() {
			0 == g && d(".w-tab-btn>.left").addClass("left-final")
		}), void 0)
	});
	var j = 0;
	d("#book-content p").each(function() {
		j = j + d(this).outerHeight() + +d(this).css("margin-top").slice(0, -2) + +d(this).css("margin-bottom").slice(0, -2)
	});
	var k = 220;
	k >= j && d(".showall-txt").hide(),
	d("#book-content").height(k).css("overflow", "hidden"),
	d(".showall-txt").toggle(function() {
		d("#book-content").animate({
			height: j
		},
		500,
		function() {
			d(".showall-txt>a").html("隐藏部分").removeClass("w-more-open").addClass("w-more-close")
		})
	},
	function() {
		d("#book-content").animate({
			height: k
		},
		500,
		function() {
			d(".showall-txt>a").html("显示全部").removeClass("w-more-close").addClass("w-more-open")
		})
	}),
	d.fn.pointout = function(a) {
		var b = {
			firstColor: "#ccc",
			secondColor: "#666",
			treeColor: "#aaa",
			wordsName: "#emailpassworld span"
		};
		return d.extend(b, a),
		d(this).each(function() {
			var a = d(this);
			a.val(""),
			a.bind("input propertychange",
			function() {
				"" == d(this).val().trim() ? d(b.wordsName).show() : d(b.wordsName).hide()
			}),
			a.bind({
				focus: function() {
					d(b.wordsName).css("color", b.firstColor),
					a.css("color", b.secondColor)
				},
				focusout: function() {
					"" != a.val() ? d(b.wordsName).css("color", b.secondColor) : d(b.wordsName).css("color", b.treeColor)
				}
			}),
			d(b.wordsName).click(function() {
				var c = a;
				d(b.wordsName).css("color", b.firstColor),
				c.focus()
			})
		})
	},
	d("#dkemail").pointout({
		wordsName: "#emailts"
	}),
	d("#dkpassword").pointout({
		wordsName: "#wordts"
	}),
	d("#dkredeem").pointout({
		wordsName: ".deem-title"
	}),
	d(".j-comment-title").pointout({
		wordsName: "#title-warning"
	}),
	d(".j-comment-content").pointout({
		wordsName: "#comment-content"
	}),
	d("input[name='findpassword']").pointout(),
	d(".w-dropdown-menu .btn").click(function() {
		return l(this),
		!1
	}),
	d(".w-dropdown-menu .w-btn").click(function() {
		return l(this),
		!1
	}),
	d(document).click(function() {
		d(".w-dropdown-menu .cnt,#m-changelog").hide()
	});
	var m = e.getCookie("token"),
	n = e.getCookie("name"),
	o = e.getCookie("user_id");
	"" != m && null != m && void 0 != m ? (("" == n || null == n || void 0 == n) && (n = o), d(".w-login").hide(), d("#w-person").show(), d("#username").html(n)) : (d("#w-login").show(), d("#w-person").hide()),
	d("#exit").click(function() {
		d("#w-login").show(),
		d("#w-person").hide(),
		p();
		var a = location.pathname.substring(1),
		b = "u/"; - 1 == a.indexOf(b) ? location.reload() : document.location = "/"
	}),
	d(".m-nav ul li a").click(function() {
		var a = d(this).attr("href");
		e.addBasicCookie("href", a, 0)
	}),
	d("#gotologin").click(function() {
		e.addBasicCookie("href", window.location, 0),
		window.location = "/login"
	}),
	d("#dk-text").val("搜索书名或者作者..."),
	d("#dk-text").mouseout(function() {
		"搜索书名或者作者..." != d("#dk-text").val() ? d(".searchform").addClass("crt") : d(".searchform").removeClass("crt")
	}),
	d("#dk-text").mouseover(function() {
		d(".searchform").addClass("crt")
	});
	var r = "搜索书名或者作者...";
	d("#searchbotton").click(function() {
		var a = d("#dk-text").val();
		"" != a && null != a && void 0 != a && a != r && q("/search/" + encodeURIComponent(a) + "/1")
	});
	var s = null;
	d("#dk-text").bind("input propertychange", t);
	var u = e.getArgs("press_name");
	void 0 != u && d(".press_name").html(u),
	d("#dk-text").bind({
		keydown: function(a) {
			var b = a.which;
			if ("13" == b) {
				var c = d.trim("搜索书名或者作者..."),
				e = d("#dk-text").val();
				if ("" != e && null != e && void 0 != e && e != c) {
					var f = encodeURIComponent(e);
					q("/search/" + f + "/1")
				}
			}
		},
		focus: function() {
			d("#dk-text").val() == r && (d("#dk-text").val(""), d(".searchform").addClass("crt"), d("#searchbotton").fadeOut()),
			s = setInterval(t, 100)
		},
		focusout: function() {
			"" == d("#dk-text").val() && (d("#dk-text").val(r), d(".searchform").removeClass("crt")),
			clearInterval(s),
			s = null
		}
	});
	var v = !!window.ActiveXObject,
	w = v && !window.XMLHttpRequest;
	x(),
	y(),
	d("#changelog").click(function() {
		var a = d("#log-more a");
		a.attr("href", a.data("location")),
		window.event ? event.cancelBubble = !0 : event && event.stopPropagation(),
		d(".m-changelog").show(),
		d(".m-changelog > .wrap > ul").height() >= 180 && d("#log-more").show()
	}),
	d("#log-more").click(function() {}),
	"更新日志" == d("#dkcurrent").text() && d("#changelog")
}),
define("duokan/store/1/page/../common", [],
function(a, b, c) {
	function f(a) {
		return a
	}
	function g(a) {
		return decodeURIComponent(a.replace(e, " "))
	}
	function k() {
		var a = window.location.host;
		return a.indexOf("172.27") >= 0 || a.toLowerCase().indexOf("dkmars") >= 0 ? a: "127.0.0.1" == a ? "127.0.0.1": a.toLowerCase().indexOf("localhost") >= 0 ? "localhost": "duokan.com"
	}
	var d = a("gallery/jquery/1.8.3/jquery"),
	e = /\+/g,
	h = d.cookie = function(a, b, c) {
		if (void 0 !== b) {
			if (c = d.extend({},
			h.defaults, c), null === b && (c.expires = -1), "number" == typeof c.expires) {
				var e = c.expires,
				i = c.expires = new Date;
				i.setDate(i.getDate() + e)
			}
			return b = h.json ? JSON.stringify(b) : String(b),
			document.cookie = [encodeURIComponent(a), "=", h.raw ? b: encodeURIComponent(b), c.expires ? "; expires=" + c.expires.toUTCString() : "", c.path ? "; path=" + c.path: "", c.domain ? "; domain=" + c.domain: "", c.secure ? "; secure": ""].join("")
		}
		for (var m, j = h.raw ? f: g, k = document.cookie.split("; "), l = 0; m = k[l] && k[l].split("="); l++) if (j(m.shift()) === a) {
			var n = j(m.join("="));
			return h.json ? JSON.parse(n) : n
		}
		return null
	};
	h.defaults = {},
	d.removeCookie = function(a, b) {
		return null !== d.cookie(a) ? (d.cookie(a, null, b), !0) : !1
	};
	var i = !!window.ActiveXObject,
	j = i && !window.XMLHttpRequest,
	l = {
		addCookie: function(a, b, c) {
			c > 0 ? d.cookie(a, b, {
				expires: c,
				path: "/"
			}) : d.cookie(a, b, {
				path: "/"
			})
		},
		getCookie: function(a) {
			return d.cookie(a)
		},
		delCookie: function(a) {
			d.removeCookie(a, {
				path: "/"
			})
		},
		getArgs: function(a) {
			query = location.pathname.substring(1);
			var c, b = query.split("/");
			if ("title" == a) c = 0;
			else if ("book_id" == a) {
				if (window.dk_data && window.dk_data.book_id) return decodeURIComponent(window.dk_data.book_id);
				for (var d in b)"b" == b[d] && (c = parseInt(d) + 1)
			} else if ("list_id" == a) for (var d in b)"l" == b[d] && (c = parseInt(d) + 1);
			else if ("press_name" == a) for (var d in b)"p" == b[d] && (c = parseInt(d) - 1);
			else if ("comment_id" == a) for (var d in b)"comment" == b[d] && (c = parseInt(d) + 1);
			var e = decodeURIComponent(b[c]);
			return e
		},
		getArgs2: function(a) {
			var c, b = new Object;
			c = 2 == arguments.length ? arguments[1] : location.search.substring(1);
			for (var d = c.split("&"), e = 0; d.length > e; e++) {
				var f = d[e].indexOf("=");
				if ( - 1 != f) {
					var g = d[e].substring(0, f),
					h = d[e].substring(f + 1);
					h = decodeURIComponent(h),
					b[g] = h
				}
			}
			return b[a]
		},
		getArgsByReg: function(a) {
			var b = window.location.href.replace(/https?\:\/\//, ""),
			c = b.match(a);
			return c.forEach(function(a, b) {
				c[b] = decodeURI(c[b])
			}),
			c.shift(),
			c
		},
		getSizeText: function(a) {
			return a > 0 ? a > 1e6 ? (a / 1e6).toFixed(2) + "&nbsp;MB": (a / 1e3).toFixed(2) + "&nbsp;KB": "未知"
		},
		commentLIstUlHeight: null,
		jQueryAjax: function(a, b) {
			d.ajax({
				url: a,
				success: function(a) {
					b(a)
				},
				complete: function() {
					l.commentLIstUlHeight = d(".comment-hot").hasClass("crt") ? d("#hottest").height() : d("#newest").height()
				},
				dataType: "json",
				type: "get"
			})
		},
		jump: function(a, b, c) {
			window.setTimeout(function() {
				a--,
				a > 0 ? (d(b).text(a), l.jump(a, b, c)) : document.location = c
			},
			1e3)
		},
		htmlEncode: function(a) {
			var b = "";
			return 0 == a.length ? "": (b = a.replace(/&/g, "&amp;"), b = b.replace(/</g, "&lt;"), b = b.replace(/>/g, "&gt;"), b = b.replace(/ /g, "&nbsp;"), b = b.replace(/\'/g, "&#39;"), b = b.replace(/\"/g, "&quot;"), b = b.replace(/\n/g, "<br>"))
		},
		removeNickEmail: function(a) {
			return a ? a.replace(/@[^\.]+\..+$/, "") : a
		},
		buildContent: function(a) {
			var b = "\n"; - 1 != a.search("\r\n") && (b = "\r\n");
			var c = a.split(b),
			d = "",
			e = new RegExp(b, "g");
			return c.forEach(function(a, b) {
				if (c[b].length > 0) {
					var f = l.htmlEncode(c[b].trim());
					d += "<p>" + f.replace(e, "</p><p>") + "</p>"
				}
			}),
			d
		},
		nbs: /^\s*(\s*?)\s+$/,
		removepace: function(a, b) {
			d(a).each(function(c) {
				var e = d(a).eq(c).text();
				if (1 == l.nbs.test(e) || "" == e) {
					var f = d(this).next("p").text(); (0 == l.nbs.test(f) || "" != e) && d(this).next("p").addClass(b),
					d(a).eq(c).empty().remove()
				}
			})
		},
		loadingIcon: function(a) {
			var b = new Spinner({
				lines: 12,
				length: 0,
				width: 6,
				radius: 14,
				corners: 1,
				color: "#333",
				speed: 1,
				trail: 60,
				shadow: !1,
				hwaccel: !1,
				className: "spinner",
				top: 0,
				left: 0
			}),
			c = d(a + " .spin")[0];
			return b.spin(c),
			b
		},
		smallLoadingIcon: function(a) {
			var b = new Spinner({
				lines: 12,
				length: 5,
				width: 2,
				radius: 4,
				color: "#333",
				speed: 1,
				trail: 50,
				shadow: !1,
				hwaccel: !1,
				className: "spinner",
				top: 0,
				left: 0
			}),
			c = d(a + " .spin")[0];
			return b.spin(c),
			b
		},
		showStarLi: function(a) {
			if (2 > a) {
				var b = "<ul class='five'><li></li><li></li><li></li><li></li><li></li></ul>";
				return b
			}
			if (2.5 > a) {
				var b = "<ul class='five'><li class=red></li><li></li><li></li><li></li><li></li></ul>";
				return b
			}
			if (3.5 > a) {
				var b = "<ul class='five'><li class=red></li><li class=halfive></li><li></li><li></li><li></li></ul>";
				return b
			}
			if (4.5 > a) {
				var b = "<ul class='five'><li class=red></li><li class=red></li><li></li><li></li><li></li></ul>";
				return b
			}
			if (5.5 > a) {
				var b = "<ul class='five'> <li class=red></li> <li class=red></li> <li class=halfive></li> <li></li> <li></li></ul>";
				return b
			}
			if (6.5 > a) {
				var b = "<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li></li> <li></li></ul>";
				return b
			}
			if (7.5 > a) {
				var b = "<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=halfive></li> <li></li></ul>";
				return b
			}
			if (8.5 > a) {
				var b = "<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> <li></li></ul>";
				return b
			}
			if (9.5 > a) {
				var b = "<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> <li class=halfive></li></ul>";
				return b
			}
			if (10 >= a) {
				var b = "<ul class='five'><li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> <li class=red></li> </ul>";
				return b
			}
			var b = "<ul class='five'><li></li><li></li><li></li><li></li><li></li></ul>";
			return b
		},
		addBasicCookie: function(a, b, c) {
			c > 0 ? d.cookie(a, b, {
				expires: c,
				path: "/",
				domain: k()
			}) : d.cookie(a, b, {
				path: "/",
				domain: k()
			})
		},
		delBasicCookie: function(a) {
			d.removeCookie(a, {
				path: "/",
				domain: k()
			})
		},
		generateDeviceID: function() {
			var a = l.getCookie("device_id");
			if (null == a || "" == a) {
				for (var b = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM",
				c = "",
				d = 0; 12 > d; d++) c += b.charAt(Math.ceil(1e8 * Math.random()) % b.length);
				a = "D900" + c
			}
			l.addBasicCookie("device_id", a, 360),
			l.addBasicCookie("app_id", "web", 360)
		},
		initialize: function() {
			l.generateDeviceID(),
			j && (d(".m-slider .wrap").bind({
				mouseenter: function() {
					d(this).addClass("hover")
				},
				mouseleave: function() {
					d(this).removeClass("hover")
				}
			}), d(".m-slider .wrap, .w-booklist, .w-booklist2, w-txtlist").delegate("li", {
				mouseenter: function() {
					d(this).addClass("hover")
				},
				mouseleave: function() {
					d(this).removeClass("hover")
				}
			}))
		},
		buildAuthor: function(a) {
			var a = a || "佚名",
			b = "\n";
			if ( - 1 != a.search("\r\n")) var b = "\r\n";
			for (var c = "",
			d = a.split(b), e = d.length > 2 ? 2 : d.length, f = 0; e > f; f++) c += "<span>" + d[f] + "</span>";
			return d.length > 2 && (c += "<span>" + d[2], c += d.length > 3 ? "&nbsp;等</span>": "</span>"),
			c
		},
		getSizeText: function(a) {
			return a > 0 ? a > 1e6 ? (a / 1e6).toFixed(2) + "&nbsp;MB": (a / 1e3).toFixed(2) + "&nbsp;KB": "未知"
		},
		showStar: function(a) {
			if (0 == a.result) {
				if (a.score_count > 0) var b = a.score_count + " 个评分";
				else var b = "评分个数不足";
				d(".num").append(b);
				var c = a.score;
				2 > c || (2.5 > c ? d(".book_five .five>li:eq(0)").addClass("red") : 3.5 > c ? (d(".book_five .five>li:eq(0)").addClass("red"), d(".book_five .five>li:eq(1)").addClass("halfive")) : 4.5 > c ? d(".book_five .five>li:lt(2)").addClass("red") : 5.5 > c ? (d(".book_five .five>li:lt(2)").addClass("red"), d(".book_five .five>li:eq(2)").addClass("halfive")) : 6.5 > c ? d(".book_five .five>li:lt(3)").addClass("red") : 7.5 > c ? (d(".book_five .five>li:lt(3)").addClass("red"), d(".book_five .five>li:eq(3)").addClass("halfive")) : 8.5 > c ? d(".book_five .five>li:lt(4)").addClass("red") : 9.5 > c ? (d(".book_five .five>li:lt(4)").addClass("red"), d(".book_five .five>li:eq(4)").addClass("halfive")) : 10 >= c && d(".book_five .five>li:lt(5)").addClass("red"))
			}
		},
		postioncenter: function(a) {
			var b = (d(window).height() - d(a).height()) / 2,
			c = (d(window).width() - d(a).width()) / 2;
			0 > b && (b = 0),
			d(a).css({
				top: b,
				left: c
			}),
			d(window).unbind("resize"),
			d(window).scroll(function() {
				var b = (d(window).height() - d(a).height()) / 2 + d(window).scrollTop(),
				c = (d(window).width() - d(a).width()) / 2;
				0 > b && (b = 0),
				d(a).css({
					top: b,
					left: c
				})
			}),
			d(window).resize(function() {
				var b = (d(window).height() - d(a).height()) / 2,
				c = (d(window).width() - d(a).width()) / 2;
				0 > b && (b = 0),
				d(a).css({
					top: b,
					left: c
				})
			})
		},
		bookpage: function(a, b) {
			var c = Math.ceil(a);
			d("#total-page").html(c),
			1 >= c && d(".w-page").hide();
			for (var e = 1; c >= e; e++) {
				if (e > 5 && c > e) var f = "<li style='display:none'><a>" + e + "</a></li>";
				else var f = "<li><a>" + e + "</a></li>";
				d("#bookpage-a").append(f)
			}
			c > 5 && d("#bookpage-a>li").eq(4).after("<li class='ellipsis'>...</li>"),
			d("#bookpage-a>li").eq(0).addClass("crt"),
			d(".pre").addClass("init"),
			d("#bookpage-a").delegate("li", "click",
			function() {
				if ("crt" == d(this).attr("class")) return ! 1;
				d(".ellipsis").empty().remove(),
				d("#bookpage-a>li").not(d("#bookpage-a>li:first")).not(d("#bookpage-a>li:last")).hide();
				var a = d(this).index(),
				e = a - 2,
				f = a + 3;
				0 > e && (e = 0),
				d("#bookpage-a>li").slice(e, f).show(),
				d("#bookpage-a>li:eq(1)").is(":hidden") && d("#bookpage-a>li:last").prev("li").is(":hidden") ? (d("#bookpage-a>li").eq(f).after("<li class='ellipsis'>...</li>"), d("#bookpage-a>li").eq(e).before("<li class='ellipsis'>...</li>")) : d("#bookpage-a>li:eq(1)").is(":visible") ? 5 == c && 1 == a || d("#bookpage-a>li").eq(f).after("<li class='ellipsis'>...</li>") : d("#bookpage-a>li:last").prev("li").is(":visible") && d("#bookpage-a>li").eq(e).before("<li class='ellipsis'>...</li>"),
				d("#bookpage-a>li").removeClass("crt"),
				d(this).addClass("crt");
				var g = d(this).text();
				d("#one-page").html(g),
				g == c && c > 1 ? d(".next").addClass("init") : d(".next").removeClass("init"),
				d(this).index() > 0 ? (comment.start = 10 * (g - 1) + 1, book.category.listStart = 10 * (g - 1), d(".pre").removeClass("init")) : 0 == d(this).index() && (comment.start = 1, category.listStart = 0, d(".pre").addClass("init"), d(".next").removeClass("init")),
				"replylist" == b ? comment.comment_LIst(10) : "commentlist" == b ? comment.commentLIst() : "category" == b && category.categoryLIst()
			}),
			d(".pre").click(function() {
				return d(this).hasClass("init") ? !1 : (d("#bookpage-a>li.crt").prev().click(), void 0)
			}),
			d(".next").click(function() {
				return d(this).hasClass("init") ? !1 : (d("#bookpage-a>li.crt").next().click(), void 0)
			})
		}
	};
	c.exports = l
}),
function(a, b, c) {
	function d(a, c) {
		var e, d = b.createElement(a || "div");
		for (e in c) d[e] = c[e];
		return d
	}
	function e(a) {
		for (var b = 1,
		c = arguments.length; c > b; b++) a.appendChild(arguments[b]);
		return a
	}
	function f(a, b, c, d) {
		var e = ["opacity", b, ~~ (100 * a), c, d].join("-"),
		f = .01 + 100 * (c / d),
		g = Math.max(1 - (1 - a) / b * (100 - f), a),
		h = m.substring(0, m.indexOf("Animation")).toLowerCase(),
		i = h && "-" + h + "-" || "";
		return l[e] || (n.insertRule("@" + i + "keyframes " + e + "{" + "0%{opacity:" + g + "}" + f + "%{opacity:" + a + "}" + (f + .01) + "%{opacity:1}" + (f + b) % 100 + "%{opacity:" + a + "}" + "100%{opacity:" + g + "}" + "}", 0), l[e] = 1),
		e
	}
	function g(a, b) {
		var e, f, d = a.style;
		if (d[b] !== c) return b;
		for (b = b.charAt(0).toUpperCase() + b.slice(1), f = 0; k.length > f; f++) if (e = k[f] + b, d[e] !== c) return e
	}
	function h(a, b) {
		for (var c in b) a.style[g(a, c) || c] = b[c];
		return a
	}
	function i(a) {
		for (var b = 1; arguments.length > b; b++) {
			var d = arguments[b];
			for (var e in d) a[e] === c && (a[e] = d[e])
		}
		return a
	}
	function j(a) {
		for (var b = {
			x: a.offsetLeft,
			y: a.offsetTop
		}; a = a.offsetParent;) b.x += a.offsetLeft,
		b.y += a.offsetTop;
		return b
	}
	var m, k = ["webkit", "Moz", "ms", "O"],
	l = {},
	n = function() {
		var a = d("style");
		return e(b.getElementsByTagName("head")[0], a),
		a.sheet || a.styleSheet
	} (),
	o = {
		lines: 12,
		length: 7,
		width: 5,
		radius: 10,
		color: "#000",
		speed: 1,
		trail: 100,
		opacity: .25,
		fps: 20,
		zIndex: 2e9,
		className: "spinner",
		top: "auto",
		left: "auto"
	},
	p = function q(a) {
		return this.spin ? (this.opts = i(a || {},
		q.defaults, o), void 0) : new q(a)
	};
	p.defaults = {},
	p.prototype = {
		spin: function(a) {
			this.stop();
			var g, i, b = this,
			c = b.opts,
			e = b.el = h(d(0, {
				className: c.className
			}), {
				position: "relative",
				zIndex: c.zIndex
			}),
			f = c.radius + c.length + c.width;
			if (a && (a.insertBefore(e, a.firstChild || null), i = j(a), g = j(e), h(e, {
				left: ("auto" == c.left ? i.x - g.x + (a.offsetWidth >> 1) : c.left + f) + "px",
				top: ("auto" == c.top ? i.y - g.y + (a.offsetHeight >> 1) : c.top + f) + "px"
			})), e.setAttribute("aria-role", "progressbar"), b.lines(e, b.opts), !m) {
				var k = 0,
				l = c.fps,
				n = l / c.speed,
				o = (1 - c.opacity) / (n * c.trail / 100),
				p = n / c.lines; !
				function q() {
					k++;
					for (var a = c.lines; a; a--) {
						var d = Math.max(1 - (k + a * p) % n * o, c.opacity);
						b.opacity(e, c.lines - a, d, c)
					}
					b.timeout = b.el && setTimeout(q, ~~ (1e3 / l))
				} ()
			}
			return b
		},
		stop: function() {
			var a = this.el;
			return a && (clearTimeout(this.timeout), a.parentNode && a.parentNode.removeChild(a), this.el = c),
			this
		},
		lines: function(a, b) {
			function c(a, c) {
				return h(d(), {
					position: "absolute",
					width: b.length + b.width + "px",
					height: b.width + "px",
					background: a,
					boxShadow: c,
					transformOrigin: "left",
					transform: "rotate(" + ~~ (360 / b.lines * g) + "deg) translate(" + b.radius + "px" + ",0)",
					borderRadius: (b.width >> 1) + "px"
				})
			}
			for (var i, g = 0; b.lines > g; g++) i = h(d(), {
				position: "absolute",
				top: 1 + ~ (b.width / 2) + "px",
				transform: b.hwaccel ? "translate3d(0,0,0)": "",
				opacity: b.opacity,
				animation: m && f(b.opacity, b.trail, g, b.lines) + " " + 1 / b.speed + "s linear infinite"
			}),
			b.shadow && e(i, h(c("#000", "0 0 4px #000"), {
				top: "2px"
			})),
			e(a, e(i, c(b.color, "0 0 1px rgba(0,0,0,.1)")));
			return a
		},
		opacity: function(a, b, c) {
			a.childNodes.length > b && (a.childNodes[b].style.opacity = c)
		}
	},
	!
	function() {
		var b, a = h(d("group"), {
			behavior: "url(#default#VML)"
		});
		if (!g(a, "transform") && a.adj) {
			for (b = 4; b--;) n.addRule(["group", "roundrect", "fill", "stroke"][b], "behavior:url(#default#VML)");
			p.prototype.lines = function(a, b) {
				function c() {
					return h(d("group", {
						coordsize: i + " " + i,
						coordorigin: -g + " " + -g
					}), {
						width: i,
						height: i
					})
				}
				function f(a, f, i) {
					e(k, e(h(c(), {
						rotation: 360 / b.lines * a + "deg",
						left: ~~f
					}), e(h(d("roundrect", {
						arcsize: 1
					}), {
						width: g,
						height: b.width,
						left: b.radius,
						top: -b.width >> 1,
						filter: i
					}), d("fill", {
						color: b.color,
						opacity: b.opacity
					}), d("stroke", {
						opacity: 0
					}))))
				}
				var l, g = b.length + b.width,
				i = 2 * g,
				j = 2 * -(b.width + b.length) + "px",
				k = h(c(), {
					position: "absolute",
					top: j,
					left: j
				});
				if (b.shadow) for (l = 1; b.lines >= l; l++) f(l, -2, "progid:DXImageTransform.Microsoft.Blur(pixelradius=2,makeshadow=1,shadowopacity=.3)");
				for (l = 1; b.lines >= l; l++) f(l);
				return e(a, k)
			},
			p.prototype.opacity = function(a, b, c, d) {
				var e = a.firstChild;
				d = d.shadow && d.lines || 0,
				e && e.childNodes.length > b + d && (e = e.childNodes[b + d], e = e && e.firstChild, e = e && e.firstChild, e && (e.opacity = c))
			}
		} else m = g(a, "animation")
	} (),
	a.Spinner = p
} (window, document),
define("duokan/store/1/page/../common/fbox/fbox", [],
function(a) {
	var d = a("gallery/jquery/1.8.3/jquery"),
	e = a("../../core"),
	f = a("../../widget/top/top"),
	g = a("../../widget/event"),
	h = e.create(),
	i = h.extend(g);
	i.init = function(a) {
		this.supInit(a),
		this.__body = d(a.body),
		this.__module = this.__body.find(a.module || ".js-sns"),
		this.__list = this.__module.find("li"),
		this.__dft = this.__list.eq(a.dft || 2),
		this.__clazz = a.clazz || "crt",
		this.__toTop = this.__body.find(a.toTop || ".gotop"),
		this.__width = a.width || 1280,
		this.__isWide(),
		this.__list.toArray().forEach(this.__onHover, this),
		this.__onResize(),
		this.__doPosition(),
		d(window).bind("resize", this.__onResize.bind(this)),
		d(window).bind("resize", this.__doPosition.bind(this)),
		this.__goTop(),
		this.__body.show()
	},
	i.__isWide = function() {
		this.__tag = d(document).width() > this.__width
	},
	i.__doPosition = function() {
		d(window).width() > 1070 ? this.__body.css("right", (d(window).width() - 1010) / 2 - 34 + "px") : this.__body.css("right", "0px")
	},
	i.__goTop = function() {
		new f({
			body: this.__toTop
		})
	},
	i.__onHover = function(a) {
		var b = this.__clazz;
		a = d(a),
		a.hover(function() {
			this.__list.removeClass(b),
			a.addClass(b)
		}.bind(this),
		function() {
			this.__tag || a.removeClass(b)
		}.bind(this))
	},
	i.__onResize = function() {
		this.__isWide(),
		this.__tag ? this.__dft.addClass(this.__clazz) : this.__list.removeClass(this.__clazz),
		this.__tag ? this.__module.removeClass("m-sns-2").addClass("m-sns-1") : this.__module.removeClass("m-sns-1").addClass("m-sns-2")
	};
	var j = a("../../config");
	return j.browser.trident2 && (i.__doPosition = function() {
		return ! 1
	}),
	h
}),
define("duokan/store/1/page/../core", [],
function(a, b) {
	var d = a("gallery/jquery/1.8.3/jquery");
	b.pushCSSText = function(a) {
		var b = d("<style>" + a + "</style>");
		d("head").append(b)
	},
	b.create = function() {
		var a = function() {
			return this.init.apply(this, arguments)
		};
		return a.extend = function(a) {
			this._$super = a,
			this._$supro = a.prototype;
			var b = function() {};
			b.prototype = a.prototype,
			this.prototype = new b;
			var c = this.prototype;
			c.constructor = this,
			c.init = function() {
				this.supInit.apply(this, arguments)
			};
			var d = a;
			return c.supInit = function() {
				var b = d.prototype.init;
				d = d._$super || a,
				b && b.apply(this, arguments),
				d = a
			},
			c
		},
		a
	},
	Function.prototype.aop = function(a, b) {
		var c = function() {
			return ! 1
		},
		b = b || c,
		a = a || c,
		d = this;
		return function() {
			var c = {
				args: NEJ.R.slice.call(arguments, 0)
			};
			return a(c),
			c.stopped || (c.value = d.apply(this, c.args), b(c)),
			c.value
		}
	}
}),
define("duokan/store/1/page/../widget/top/top", [],
function(a) {
	var d = a("gallery/jquery/1.8.3/jquery"),
	e = a("../../core"),
	f = a("../event"),
	g = e.create(),
	h = g.extend(f);
	return h.init = function(a) {
		this.supInit(a),
		this.__body = d(a.body),
		this.__limit = a.limit || 20,
		d(window).bind("scroll", this.__onscroll.bind(this)),
		this.__body.bind("click", this.__backTop.bind(this)),
		this.__toggle()
	},
	h.__toggle = function(a) {
		a = a || d(window).scrollTop(),
		this.__limit > a ? this.__body.hide() : this.__body.show()
	},
	h.__onscroll = function() {
		var a = d(window).scrollTop();
		this.__toggle(a)
	},
	h.__backTop = function() {
		d(window).scrollTop(0)
	},
	g
}),
define("duokan/store/1/page/../widget/event", [],
function(a, b, c) {
	var d = a("../core.js"),
	e = a("gallery/jquery/1.8.3/jquery"),
	f = d.create();
	f.prototype = {
		constructor: f,
		init: function() {
			this.initEvent(arguments[0])
		},
		initEvent: function(a) {
			if (this.__events = {},
			a && e.isPlainObject(a)) for (var b in a) this.addEvent(b, a[b])
		},
		addEvent: function() {
			this.setEvent.apply(this, arguments)
		},
		setEvent: function(a, b) {
			return a && e.isFunction(b) && (this.__events[a.toLowerCase()] = b),
			this
		},
		fireEvent: function(a) {
			var b = this.__events[(a || "").toLowerCase()];
			if (!b) return this;
			var c = Array.prototype.slice.call(arguments, 1);
			return e.isArray(b) ? (b.forEach(function(a) {
				try {
					a.apply(this, c)
				} catch(b) {}
			},
			this), this) : b.apply(this, c)
		},
		removeEvent: function(a) {
			var a = (a || "").toLowerCase();
			if (a) delete this.__events[a];
			else for (var b in this.__events) this.removeEvent(b);
			return this
		},
		hasEvent: function(a) {
			var b = this.__events[a.toLowerCase()];
			return !! b
		}
	},
	c.exports = f
}),
define("duokan/store/1/page/../config", [],
function(a, b) {
	var d = window.navigator.platform,
	e = window.navigator.userAgent,
	f = {
		mac: d,
		win: d,
		linux: d,
		ipad: e,
		ipod: e,
		iphone: d,
		android: e
	};
	for (var g in f) f[g] = new RegExp(g, "i").test(f[g]);
	f.ios = f.ipad || f.iphone || f.ipod,
	f.tablet = f.ipad,
	f.desktop = f.mac || f.win || f.linux && !f.android;
	var h = a("gallery/jquery/1.8.3/jquery");
	b.browser = {
		gecko: h.browser.gecko,
		webkit: h.browser.webkit,
		presto: h.browser.presto,
		trident: h.browser.msie && 10 == +h.browser.version,
		trident1: h.browser.msie && +h.browser.version > 6 && 10 > h.browser.version,
		trident2: h.browser.msie && 6 >= +h.browser.version
	},
	b.platform = f
}),
define("duokan/store/1/page/../common/statistic/statistic", [],
function() {
	var d = function() {
		if ( - 1 == location.href.indexOf("ctest")) {
			window._hmt = window._hmt || [];
			var a = document.createElement("script");
			a.src = "//hm.baidu.com/hm.js?2d8f9b400e4b1e50b94358d8bb45b3de";
			var b = document.getElementsByTagName("script")[0];
			b.parentNode.insertBefore(a, b)
		}
	};
	return d
}),
define("duokan/store/1/page/book", ["../utils", "../common", "../common/fbox/fbox", "../core", "../widget/top/top", "../widget/event", "../config", "../common/statistic/statistic", "gallery/jquery/1.8.3/jquery"],
function(a) {
	var d = a("gallery/jquery/1.8.3/jquery");
	a("../utils");
	var f = a("../common"),
	g = a("../common/fbox/fbox"),
	h = a("../common/statistic/statistic");
	f.initialize(), 
	new g({
		body: ".w-fbox"
	}),
	function(a, b) {
		var c = [["^/$"], ["/r/" + encodeURI("免费榜"), "/r/" + encodeURI("月度榜"), "/r/" + encodeURI("畅销榜"), "/r/" + encodeURI("好评榜"), "/r/" + encodeURI("最新")], ["/r/" + encodeURI("热门推荐"), "/r/" + encodeURI("限免特价"), "/r/" + encodeURI("免费") + "$"], ["/c/"]];
		c.forEach(function(c, d) {
			c.forEach(function(c) {
				var c = new RegExp(c);
				c.test(location.pathname) && a.eq(d).addClass(b)
			})
		})
	} (d(".m-nav li"), "crt"),
	h()
});