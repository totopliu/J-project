/**
 *  全局插件初始化 
 *  通过jquery 插件扩展
 * 
 */
var IN = {
	jsonEval : function(data) {
		try {
			if ($.type(data) == 'string')
				return eval('(' + data + ')');
			else
				return data;
		} catch (e) {
			return {};
		}
	},
	statusCode : {
		ok : 200,
		error : 300,
		timeout : 301
	},
	ajaxError : function(xhr, ajaxOptions, thrownError) {
		layer.alert("Http status: " + xhr.status + " " + xhr.statusText+"<br/>"
				+ xhr.responseText,{closeBtn : false}, function(index){		   
			  layer.close(index);
			  window.location.reload();
		}); 
	}
};

(function($) {
	$.fn.extend({
		/**
		 * @param {Object} op: {type:GET/POST, url:ajax请求地址, data:ajax请求参数列表, callback:回调函数 }
		 */
		ajaxUrl : function(op) {
			var $this = $(this);
			$.ajax({
				type : op.type || 'GET',
				url : op.url,
				data : op.data,
				cache : false,
				success : function(response) {
					var json = IN.jsonEval(response);
					if (json.statusCode == IN.statusCode.timeout) {

					}if(json.sessionTimeout){
						  /*  layer.confirm('登录已超时，请重新登录', {icon: 3, title:'提示'}, function(index){
							 location.href="login.action";
							});
						    location.href="login.action";
						    return;*/
					}else if(json.noRight){
						/*sy.msg("没有权限！！");
						return false;*/
					}
					else if (json.statusCode == IN.statusCode.error) {
						
					} else {
						$this.html(response).initUI();
						if ($.isFunction(op.callback))
							op.callback(response);
					}

				},
				error : IN.ajaxError,
				statusCode : {
					503 : function(xhr, ajaxOptions, thrownError) {
						layer.alert(thrownError);
					}
				}
			});
		},
		loadUrl : function(url, type, data, callback) {
			$(this).ajaxUrl({
				url : url,
				type : type,
				data : data,
				callback : callback
			});
		},
		initUI : function() {
			return this.each(function() {
				if ($.isFunction(initUI))
					initUI(this);
			});
		}

	});
})(jQuery);
