function initUI(_box){
        	//var $p = $(_box || document);
        	var $p = $(document);
        	loadProperties();
        	var context =[
				[ {
				    text: $.i18n.prop("stringRightClickPluginsClosecurrent"),
				    func: function() {
				    	s();
				    }
				},{
				    text: $.i18n.prop("stringRightClickPluginsCloseother"),
				    func: function() {
				    	$(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(function() {
							$('.J_box[data-id="' + $(this).data("id") + '"]').remove(),
							$(this).remove()
						}),
						$(".page-tabs-content").css("margin-left", "0")
				    }
				},{
				    text: $.i18n.prop("stringRightClickPluginsCloseall"),
				    func: function() {
				    	$(".page-tabs-content").children("[data-id]").not(":first").each(function() {
							$('.J_box[data-id="' + $(this).data("id") + '"]').remove(), $(this).remove()
						}), $(".page-tabs-content").children("[data-id]:first").each(function() {
							$('.J_box[data-id="' + $(this).data("id") + '"]').show(), $(this).addClass("active")
						}), $(".page-tabs-content").css("margin-left", "0")
				    }
				}],
				[{
				    text: $.i18n.prop("stringRightClickPluginsRefresh"),
				    func: function() {
				    	$(this).each(function(){
				    		 var o = layer.load();
				    		 if (!$(this).hasClass("active")) {
				    			    var t = $(this).data("id");
				    				$(".J_mainContent .J_box").each(function() {
				    					return $(this).data("id") == t ? ($(this).show().siblings(".J_box").hide(), !1) : void 0
				    				}), $(this).addClass("active").siblings(".J_menuTab").removeClass("active"), e(this)
				    		 }
			    			 $('.J_box[data-id="' + $(this).data("id") + '"]').html('<div class="loading">'+$.i18n.prop("stringLoad")+'<div>');
				    		 $('.J_box[data-id="' + $(this).data("id") + '"]').loadUrl($(this).data("id"),'get',{},function(data) {
								layer.close(o);
								$('.J_box[data-id="' + $(this).data("id") + '"]').find("div.loading").remove();
							}); 
				    	});
				    }
				}]
        	] 
        	
        	$(".main").smartMenu(context);
        	$(".smartmenu" ,$p).each(function(){
        		$(this).smartMenu(context);
        	});     
        	  	
        	//validate form
        	$("form.required-validate", $p).each(function(){
        		var $form = $(this);
        		$form.attr("autocomplete", "off");
        		$.metadata.setType("attr", "validate");
        		$form.validate({
        			errorClass: "help-block m-b-none",
         	    	validClass: "help-block m-b-none",
         	    	 highlight: function(e) {
         	    		$(e).closest(".form-group").removeClass("has-success").addClass("has-error");
         	    	}, 
         	    	success: function(e) {
         	    		e.closest(".form-group").removeClass("has-error").addClass("has-success");
         	    	},
         	    	errorElement: "span",
         	    	errorPlacement: function(e, r) {
         	    		e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent());
         	    	},
           	        submitHandler: function(form){  
           	        	console.info(form);
           		    }  
        		});
        		
        		$form.find('input[customvalid]').each(function(){
        			var $input = $(this);
        			$input.rules("add", {
        				customvalid: $input.attr("customvalid")
        			})
        		});
        	});
        	
        	//??????????????????
        	$(".bbg,.bgg",$p).each(function(){
        		var $this = $(this);
        		  $this.slimScroll({
                 	 width: 'auto', //?????????????????????
                      height: '100%', //?????????????????????
                      size: '5px', //????????????
                      color: '#000', //???????????????
                      position: 'right', //???????????????left/right
                      distance: '0px', //??????????????????????????????
                      start: 'top', //?????????????????????top/bottom
                      opacity: .4, //??????????????????
                      alwaysVisible: true, //?????? ??????????????????
                      disableFadeOut: false, //?????? ??????????????????????????????????????????????????????????????????
                      railVisible: true, //?????? ????????????
                      railColor: '#333', //????????????
                      railOpacity: .2, //???????????????
                      railDraggable: true, //?????? ??????????????????
                     // railClass: 'slimScrollRail', //??????div?????? 
                    //  barClass: 'slimScrollBar', //?????????div??????
                    //  wrapperClass: 'slimScrollDiv', //??????div??????
                      allowPageScroll: true, //?????? ????????????????????????/????????????????????????
                      wheelStep: 20, //???????????????
                      touchScrollStep: 200, //??????????????????????????????
                      borderRadius: '7px', //???????????????
                      railBorderRadius: '7px' //????????????
                 }); 
        	});

        	// ????????????????????????jQuery??????...
        	if(typeof initOtherPlugin == "function"){initOtherPlugin();}
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
            function t(t) {
        		var e = 0;
        		return $(t).each(function() {
        			e += $(this).outerWidth(!0)
        		}), e
        	}
        	function e(e) {
        		var a = t($(e).prevAll()),
        			i = t($(e).nextAll()),
        			n = t($(".content-tabs").children().not(".J_menuTabs")),
        			s = $(".content-tabs").outerWidth(!0) - n,
        			r = 0;
        		if ($(".page-tabs-content").outerWidth() < s) r = 0;
        		else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
        			if (s - $(e).next().outerWidth(!0) > i) {
        				r = a;
        				for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content").outerWidth() - s;) r -= $(o).prev().outerWidth(), o = $(o).prev()
        			}
        		} else a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
        		$(".page-tabs-content").animate({
        			marginLeft: 0 - r + "px"
        		}, "fast")
        	}
        	
        	function s() {
        		var t = $(this).data("id"),
        			a = $(this).width();
        		if ($(this).hasClass("active")) {
        			if ($(this).next(".J_menuTab").size()) {
        				var i = $(this).next(".J_menuTab:eq(0)").data("id");
        				$(this).next(".J_menuTab:eq(0)").addClass("active"), $(".J_mainContent .J_box").each(function() {
        					return $(this).data("id") == i ? ($(this).show().siblings(".J_box").hide(), !1) : void 0
        				});
        				var n = parseInt($(".page-tabs-content").css("margin-left"));
        				0 > n && $(".page-tabs-content").animate({
        					marginLeft: n + a + "px"
        				}, "fast"), $(this).remove(), $(".J_mainContent .J_box").each(function() {
        					return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
        				})
        			}
        			if ($(this).prev(".J_menuTab").size()) {
        				var i = $(this).prev(".J_menuTab:last").data("id");
        				$(this).prev(".J_menuTab:last").addClass("active"), $(".J_mainContent .J_box").each(function() {
        					return $(this).data("id") == i ? ($(this).show().siblings(".J_box").hide(), !1) : void 0
        				}), $(this).remove(), $(".J_mainContent .J_box").each(function() {
        					return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
        				})
        			}
        		} else $(this).remove(), $(".J_mainContent .J_box").each(function() {
        			return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
        		}), e($(".J_menuTab.active"));
        		return !1
        	}
        	
        	
        }
