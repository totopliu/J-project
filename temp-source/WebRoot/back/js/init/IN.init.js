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
        	
        	//自定义滚动条
        	$(".bbg,.bgg",$p).each(function(){
        		var $this = $(this);
        		  $this.slimScroll({
                 	 width: 'auto', //可滚动区域宽度
                      height: '100%', //可滚动区域高度
                      size: '5px', //组件宽度
                      color: '#000', //滚动条颜色
                      position: 'right', //组件位置：left/right
                      distance: '0px', //组件与侧边之间的距离
                      start: 'top', //默认滚动位置：top/bottom
                      opacity: .4, //滚动条透明度
                      alwaysVisible: true, //是否 始终显示组件
                      disableFadeOut: false, //是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
                      railVisible: true, //是否 显示轨道
                      railColor: '#333', //轨道颜色
                      railOpacity: .2, //轨道透明度
                      railDraggable: true, //是否 滚动条可拖动
                     // railClass: 'slimScrollRail', //轨道div类名 
                    //  barClass: 'slimScrollBar', //滚动条div类名
                    //  wrapperClass: 'slimScrollDiv', //外包div类名
                      allowPageScroll: true, //是否 使用滚轮到达顶端/底端时，滚动窗口
                      wheelStep: 20, //滚轮滚动量
                      touchScrollStep: 200, //滚动量当用户使用手势
                      borderRadius: '7px', //滚动条圆角
                      railBorderRadius: '7px' //轨道圆角
                 }); 
        	});

        	// 这里放其他第三方jQuery插件...
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
