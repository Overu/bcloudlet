define(function(require, exports, module) {
  var $ = require('jquery')
  var slides = require('./client_intro')
  
  $.fn.responsiveSlides = function (options) {
    // Default settings
    var settings = $.extend({
      "auto": true,             // Boolean: Animate automatically, true or false
      "speed": 1000,            // Integer: Speed of the transition, in milliseconds
      "timeout": 4000,          // Integer: Time between slide transitions, in milliseconds
      "pager": false,           // Boolean: Show pager, true or false
      "nav": false,             // Boolean: Show navigation, true or false
      "random": false,          // Boolean: Randomize the order of the slides, true or false
      "pause": false,           // Boolean: Pause on hover, true or false
      "pauseControls": false,   // Boolean: Pause when hovering controls, true or false
      "prevText": "Previous",   // String: Text for the "previous" button
      "nextText": "Next",       // String: Text for the "next" button
      "maxwidth": "",           // Integer: Max-width of the slideshow, in pixels
      "controls": "",           // Selector: Where controls should be appended to, default is after the <ul>
      "namespace": "slides",   // String: change the default namespace used
      before: function () {},   // Function: Before callback
      after: function () {}     // Function: After callback
    }, options);
      // Index for namespacing
      i = 1;
      var $this = $(this),
        // Local variables
        selectTab,
        startCycle,
        restartCycle,
        rotate,
        $tabs,
        started = false,
        // Helpers
        index = 0,
        $slide = $this.children(),
        length = $slide.size(),
        fadeTime = parseFloat(settings.speed),
        waitTime = parseFloat(settings.timeout),
        maxw = parseFloat(settings.maxwidth),

        // Namespacing
        namespace = settings.namespace,
        namespaceIdx = namespace + i,

        // Classes
        navClass = namespace + "_nav " + namespaceIdx + "_nav",
        activeClass = namespace + "_here",
        visibleClass = namespaceIdx + "_on",
        slideClassPrefix = namespaceIdx + "_s",

        // Pager
        $pager = $("<ul class='" + namespace + "_tabs " + namespaceIdx + "_tabs' />"),

        // Styles for visible and hidden slides
        visible = {"float": "left", "position": "relative"},
        hidden = {"float": "none", "position": "absolute"},

        // Fading animation
        slideTo = function (idx) {
          settings.before();
          $slide
            .stop()
            .fadeOut(fadeTime, function () {
              $(this)
                .removeClass(visibleClass)
                .css(hidden);
            })
            .eq(idx)
            .fadeIn(fadeTime, function () {
              $(this)
                .addClass(visibleClass)
                .css(visible);
              settings.after();
              index = idx;
            });
        };

      // Random order
      if (settings.random) {
        $slide.sort(function () {
          return (Math.round(Math.random()) - 0.5);
        });
        $this
          .empty()
          .append($slide);
      }

      // Add ID's to each slide
      $slide.each(function (i) {
        this.id = slideClassPrefix + i;
      });

      // Add max-width and classes
      $this.addClass(namespace + " " + namespaceIdx);
      if (options && options.maxwidth) {
        $this.css("max-width", maxw);
      }

      // Hide all slides, then show first one
      $slide
        .hide()
        .eq(0)
        .addClass(visibleClass)
        .css(visible)
        .show();

      // Only run if there's more than one slide
      if ($slide.size() > 1) {

        // Make sure the timeout is at least 100ms longer than the fade
        if (waitTime < fadeTime + 100) {
          return;
        }

        // Pager
        if (settings.pager) {
          var tabMarkup = [];
          $slide.each(function (i) {
            var n = i + 1;
            tabMarkup +=
              "<li>" +
              "<a href='#' hidefocus='hidefocus' class='" + slideClassPrefix + n + "'>" + n + "</a>" +
              "</li>";
          });
          $pager.append(tabMarkup);

          $tabs = $pager.find("a");

          // Inject pager
          if (options.controls) {
            $(settings.controls).append($pager);
          } else {
            $this.after($pager);
          }

          // Select pager item
          selectTab = function (idx) {
            $tabs
              .closest("li")
              .removeClass(activeClass)
              .eq(idx)
              .addClass(activeClass);
          };
        }
        startCycle = function () {
          rotate = setInterval(function () {

            // Clear the event queue
            $slide.stop(true, true);
            var idx = index + 1 < length ? index + 1 : 0;
            // Remove active state and set new if pager is set
            if (settings.pager) {
              selectTab(idx);
            }
            slideTo(idx);
          }, waitTime);
          started = true;
        };
          
        // Auto cycle
        if (settings.auto) {
          // Init cycle
          startCycle();
        }
        // Restarting cycle
        restartCycle = function () {
          if (started) {
            // Stop
            clearInterval(rotate);
            // Restart
            startCycle();
          }
        };
        // Pause on hover
        if (settings.pause) {
          $this.hover(function () {
            clearInterval(rotate);
          }, function () {
            restartCycle();
          });
        }
        // Pager click event handler
        if (settings.pager) {
          $tabs.bind("click", function (e) {
            e.preventDefault();

            if (!settings.pauseControls) {
              restartCycle();
            }
            // Get index of clicked tab
            var idx = $tabs.index(this);
            // Break if element is already active or currently animated
            if (index === idx || $("." + visibleClass + ":animated").length) {
              return;
            }
            // Remove active state from old tab and set new one
            selectTab(idx);

            // Do the animation
            slideTo(idx);
          })
            .eq(0)
            .closest("li")
            .addClass(activeClass);

          // Pause when hovering pager
          if (settings.pauseControls) {
            $tabs.hover(function () {
              clearInterval(rotate);
            }, function () {
              restartCycle();
            });
          }
        }
        // Navigation
        if (settings.nav) {
          var navMarkup =
            "<a href='#' hidefocus='hidefocus' class='" + navClass + " prev'>" + settings.prevText + "</a>" +
            "<a href='#' hidefocus='hidefocus' class='" + navClass + " next'>" + settings.nextText + "</a>";

          // Inject navigation
          if (options.controls) {
            $(settings.controls).append(navMarkup);
          } else {
            $this.after(navMarkup);
          }
          var $trigger = $("." + namespaceIdx + "_nav"),
            $prev = $("." + namespaceIdx + "_nav.prev");
          // Click event handler
          $trigger.bind("click", function (e) {
            e.preventDefault();
            // Prevent clicking if currently animated
            if ($("." + visibleClass + ":animated").length) {
              return;
            }
            //  Adds active class during slide animation
            //  $(this)
            //    .addClass(namespace + "_active")
            //    .delay(fadeTime)
            //    .queue(function (next) {
            //      $(this).removeClass(namespace + "_active");
            //      next();
            //  });
            // Determine where to slide
            var idx = $slide.index($("." + visibleClass)),
              prevIdx = idx - 1,
              nextIdx = idx + 1 < length ? index + 1 : 0;
            // Go to slide
            slideTo($(this)[0] === $prev[0] ? prevIdx : nextIdx);
            if (settings.pager) {
              selectTab($(this)[0] === $prev[0] ? prevIdx : nextIdx);
            }
           if (!settings.pauseControls) {
              restartCycle();
            }
          });
          // Pause when hovering navigation
          if (settings.pauseControls) {
            $trigger.hover(function () {
              clearInterval(rotate);
            }, function () {
              restartCycle();
            });
          }
        }
      }
     // Max-width fallback
      if (typeof document.body.style.maxWidth === "undefined" && options.maxwidth) {
        var widthSupport = function () {
          $this.css("width", "100%");
          if ($this.width() > maxw) {
            $this.css("width", maxw);
          }
        };
        // Init fallback
        widthSupport();
        $(window).bind("resize", function () {
          widthSupport();
        });
      }
    return startCycle;
  };

    $("#slides").responsiveSlides({         //首页广告
    auto: true, 
    pager: true,
    nav: true,
    pause: true,
    pauseControls: true,
    speed: 400,
    maxwidth: 877,
    namespace: "slides"
  });
});
 
 
 
 
 
 
 


