<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CRM</title>
<link href="${ctx}/back/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-sm-4 col-sm-offset-4">
      <div style="text-align: center;">
        <img src="${ctx}/common/favicon.png">
      </div>
      <form>
        <input type="hidden" id="rate" value="${rate }">
        <div class="form-group form-group-lg">
          <label class="control-label">当前汇率</label>
          <p class="form-control-static">${rate }</p>
        </div>
        <div class="form-group form-group-lg" id="moneyInputGroup">
          <label class="control-label" for="money">人民币</label>
          <input type="text" class="form-control" id="money" oninput="toDollar()">
        </div>
        <div class="form-group form-group-lg" id="dollarInputGroup">
          <label class="control-label" for="dollar">美金</label>
          <input type="text" class="form-control" id="dollar" oninput="toRMB()">
          <span id="dollarMsg" class="help-block"></span>
        </div>
        <div class="form-group form-group-lg" id="dollarInputGroup">
          <label class="control-label" for="dollar">银行</label>
          <select class="form-control" id="channelid">
            <option value="ccb">中国建设银行</option>
            <option value="icbc">工商银行</option>
            <option value="abc">农业银行</option>
            <option value="boc">中国银行</option>
            <option value="comm">交通银行</option>
            <option value="cncb">中信银行</option>
            <option value="ceb">中国光大银行</option>
            <option value="cmbc">中国民生银行</option>
            <option value="gdb">广发银行</option>
            <option value="psbc">邮储银行</option>
            <option value="pingan">平安银行</option>
            <option value="cmb">招商银行</option>
            <option value="cib">兴业银行</option>
            <option value="spdb">浦发银行</option>
            <option value="hxb">华夏银行</option>
            <option value="bob">北京银行</option>
            <option value="bosh">上海银行</option>
          </select>
        </div>
        <button type="button" class="btn btn-lg btn-success btn-block" onclick="deposit()">入金</button>
      </form>
    </div>
  </div>
</div>

<script src="${ctx}/back/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx}/back/js/bootstrap.min.js?v=3.3.5"></script>
<script>
  var rate = '${rate}';

  function deposit(){
	  var rate=$('#rate').val();
	  var money=$('#money').val();
	  var dollar=$('#dollar').val();
	  var channelid=$('#channelid').val();
	  $.ajax({
		  url: '${ctx}/pub/inGold/mobileDeposit',
		  type: 'post',
		  data: {
			  rate:rate,
			  money:money,
			  dollar:dollar,
			  channelid:channelid
		  },
		  dataType: 'json',
		  success: function(response){
			 if(response.success){
				 window.location = "${ctx}/pub/inGold/payHxt_" + response.data.id;
	         }else{
	        	 $('#moneyInputGroup').addClass('has-error');
	             $('#dollarInputGroup').addClass('has-error');
	             $('#dollarMsg').html(response.msg);
	         }
		  }
	  });
  }
  
  function toRMB() {
    var dollar = $('#dollar').val();
    var rmb = dollar*rate;
    $('#money').val(Math.ceil(rmb*100)/100);
  }
  
  function toDollar() {
    var rmb = $('#money').val();
    var dollar = rmb/rate;
    $('#dollar').val(Math.floor(dollar*100)/100);
  }
  
</script>
</body>
</html>