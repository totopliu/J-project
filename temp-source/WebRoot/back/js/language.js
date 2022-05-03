/**
 * 
 */
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

$(function() {
	loadProperties();
});

function loadProperties(){
	jQuery.i18n.properties({//加载资浏览器语言对应的资源文件
		name:'strings', //资源文件名称
		path:'back/js/i18n/', //资源文件路径
		language:$.getUrlParam('lg'),//en显示英文，zh显示中文
		mode:'map', //用Map的方式使用资源文件中的值
		callback: function() {//加载成功后设置显示内容
			$('.hello').html($.i18n.prop('hello'));
			$('.welcome').html($.i18n.prop('welcome'));
			$('.stringModifyHead').html($.i18n.prop('stringModifyHead'));
			$('.stringExit').html($.i18n.prop('stringExit'));
			$('.stringPersonalData').html($.i18n.prop('stringPersonalData'));
			$('.stringAccountSetting').html($.i18n.prop('stringAccountSetting'));
			$('.stringIndex').html($.i18n.prop('stringIndex'));
			$('.stringClosing').html($.i18n.prop('stringClosing'));
			$('.stringCloseLeft').html($.i18n.prop('stringCloseLeft'));
			$('.stringCloseAllTabs').html($.i18n.prop('stringCloseAllTabs'));
			$('.stringCloseOtherTabs').html($.i18n.prop('stringCloseOtherTabs'));
		}
	});
}