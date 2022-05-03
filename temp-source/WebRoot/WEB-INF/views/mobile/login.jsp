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
        <div class="form-group form-group-lg" id="accountInputGroup">
          <label class="control-label" for="account">账号</label>
          <input type="text" class="form-control" id="account" oninput="accountOnchange()">
          <span id="accountMsg" class="help-block"></span>
        </div>
        <div class="form-group form-group-lg" id="passwordInputGroup">
          <label class="control-label" for="password">密码</label>
          <input type="password" class="form-control" id="password" oninput="passwordOnchange()">
          <span id="passwordMsg" class="help-block"></span>
        </div>
        <button type="button" class="btn btn-lg btn-success btn-block" onclick="login()">登录</button>
      </form>
    </div>
  </div>
</div>

<script src="${ctx}/back/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx}/back/js/bootstrap.min.js?v=3.3.5"></script>
<script>
  function login(){
	  var account=$('#account').val();
	  var password=$('#password').val();
	  if(account==''){
		  $('#accountInputGroup').addClass('has-error');
		  $('#accountMsg').html('请填写账号');
		  return;
	  }
	  if(password==''){
		  $('#passwordInputGroup').addClass('has-error');
		  $('#passwordMsg').html('请填写密码');
		  return;
	  }
	  $.ajax({
		  url: '${ctx}/mobileLogin',
		  type: 'post',
		  data: {account:account,password:password},
		  dataType: 'json',
		  success: function(response){
			 if(response.success){
	             window.location.href="${ctx}/pub/inGold/mobileDepositPage"
	         }else{
	        	 $('#accountInputGroup').addClass('has-error');
	             $('#passwordInputGroup').addClass('has-error');
	             $('#passwordMsg').html(response.msg);
	         }
		  }
	  });
  }
  
  function accountOnchange(){
	  $('#accountInputGroup').removeClass('has-error');
	  $('#accountMsg').html('');
  }
  
  function passwordOnchange(){
	  $('#passwordInputGroup').removeClass('has-error');
	  $('#passwordMsg').html('');
  }
</script>
</body>
</html>