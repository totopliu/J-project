<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>CRM</title>
  <link href="${pageContext.request.contextPath}/back/css/main.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/back/css/reset.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/back/css/reset.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/back/css/bootstrap.min.css" rel="stylesheet">
  <script>
    if(window.top!==window.self){window.top.location=window.location};
  </script>
</head>
<style>
  .login-error {
    color: red;
    font-size: 12px;
  }
  .lg-select {
    position: fixed;
    top: 10%;
    right: 10%;
  }
  
  .lg-select .btn {
    border-radius: 0px;
    width: 110px;
    background-color: #1ABC9C;
    color: #fff;
  }
  
  .lg-item {
    border-color: #1ABC9C;
    border-style: dashed;
  }
  
  .dropdown-toggle.btn-default:focus,
  .dropdown-toggle.btn-default:hover {
    background-color: #1ABC9C;
    color: #fff;
  }
  
  .open>.dropdown-toggle.btn-default:focus,
  .open>.dropdown-toggle.btn-default:hover {
    background-color: #1ABC9C;
    color: #fff;
  }
  
  .open>.dropdown-menu>li>a,
  .open>.dropdown-menu>li>a {
    color: #1ABC9C;
  }
  
  .open>.dropdown-menu>li>a:focus,
  .open>.dropdown-menu>li>a:hover {
    background-color: #1ABC9C;
    color: #fff;
  }
</style>
<body>
  <div class="dropdown lg-select">
    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
      <span id="lg" style="border-right: 1px solid #ccc; padding-right: 5px;">简体中文</span>
      <span class="glyphicon glyphicon-align-justify"></span>
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style="background-color: transparent; min-width: 110px;">
      <li style="border: 1px dashed #1ABC9C;">
        <a href="javascript:void(0);" onclick="selectLg('zh')" class="lg-item">简体中文</a>
      </li>
      <li style="border: 1px dashed #1ABC9C;">
        <a href="javascript:void(0);" onclick="selectLg('en')" class="lg-item">ENGLISH</a>
      </li>
      <li style="border: 1px dashed #1ABC9C;">
        <a href="javascript:void(0);" onclick="selectLg('vi')" class="lg-item">Tiếng Việt</a>
      </li>
    </ul>
  </div>
  <div class="main-wrapper">
    <div class="login-wrapper">
      <div style="margin: 0 auto; width: 100px;">
        <img src="${pageContext.request.contextPath}/common/favicon.png" width="100" height="100">
      </div>
      <form class="form-area" action="login" id="form" method="post">
        <input class="input-box login-user" type="text" id="account" name="account" />
        <input class="input-box login-password" type="password" name="password" id="password" />
        <c:if test="${!empty lg }">
          <input type="hidden" name="lg" <c:if test="${empty param.lg }">value="${lg }"</c:if> <c:if test="${!empty param.lg }">value="${param.lg }"</c:if>>
        </c:if>
        <c:if test="${empty lg }">
          <input type="hidden" name="lg" <c:if test="${empty param.lg }">value="zh"</c:if> <c:if test="${!empty param.lg }">value="${param.lg }"</c:if>>
        </c:if>
        <p class="login-error">&nbsp;${LOGIN_ERROR_MESSAGE}</p>
      </form>
    </div>
    <input type="button" class="submit-btn" id="loginButton" value="登录" onclick="submitForm()" />
    <c:if test="${!empty lg }">
      <div class="go-login">
        <span class="stringLoginNoLoginAccount"></span>
        <a class="now-login" href="registered/belong_1?lg=<c:if test="${empty param.lg }">${lg }</c:if><c:if test="${!empty param.lg }">${param.lg }</c:if>">
          <span class="stringLoginRegisterNow"></span>
        </a>
      </div>
      <div class="go-login">
        <span class="stringLoginForgetPassword"></span>
        <a class="now-login" href="forgetPass?lg=<c:if test="${empty param.lg }">${lg }</c:if><c:if test="${!empty param.lg }">${param.lg }</c:if>">
          <span class="stringLoginRetrievePassword"></span>
        </a>
      </div>
    </c:if>
    <c:if test="${empty lg }">
      <div class="go-login">
        <span class="stringLoginNoLoginAccount"></span>
        <a class="now-login" href="registered/belong_1?lg=<c:if test="${empty param.lg }">zh</c:if><c:if test="${!empty param.lg }">${param.lg }</c:if>">
          <span class="stringLoginRegisterNow"></span>
        </a>
      </div>
      <div class="go-login">
        <span class="stringLoginForgetPassword"></span>
        <a class="now-login" href="forgetPass?lg=<c:if test="${empty param.lg }">zh</c:if><c:if test="${!empty param.lg }">${param.lg }</c:if>">
          <span class="stringLoginRetrievePassword"></span>
        </a>
      </div>
    </c:if>
  </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/bootstrap.min.js"></script>
<script type="text/javascript">
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

$(function() {
  if ('${param.lg}' != '') {
    if ('${param.lg}' == 'zh') {
      $('#lg').html('简体中文');
    } else if ('${param.lg}' == 'en') {
      $('#lg').html('ENGLISH');
    } else if ('${param.lg}' == 'vi') {
      $('#lg').html('Tiếng Việt');
      $('body').css('font-family', 'Times New Roman');
      $('.main-wrapper').css('font-family', 'Times New Roman');
    }
  } else if ('${lg}' != '') {
    var url = window.location.href;
    if (url.indexOf('?lg') != -1) {
      url = url.substring(0, url.indexOf('?lg'));
    }
    window.location.href = url + '?lg=${lg}';
  } else {
    $('#lg').html('简体中文');
    var url = window.location.href;
    if (url.indexOf('?lg') != -1) {
      url = url.substring(0, url.indexOf('?lg'));
    }
    window.location.href = url + '?lg=zh';
  }

  loadProperties();
  $('.stringLoginNoLoginAccount').html($.i18n.prop('stringLoginNoLoginAccount'));
  $('.stringLoginRegisterNow').html($.i18n.prop('stringLoginRegisterNow'));
  $('#loginButton').attr('value',$.i18n.prop('stringLogin'));
  $('#account').attr('placeholder',$.i18n.prop('stringPleaseEnterMailbox'));
  $('#password').attr('placeholder',$.i18n.prop('stringPleaseFillinPassword'));
  $('.stringLoginForgetPassword').html($.i18n.prop('stringLoginForgetPassword'));
  $('.stringLoginRetrievePassword').html($.i18n.prop('stringLoginRetrievePassword'));

  document.onkeydown = function(e){
    var ev = document.all ? window.event : e;
    if (ev.keyCode == 13) {
      $('#form').submit();
    }
  }
});

var lg = $.getUrlParam('lg');
if (lg == null) {
  lg = '${lg}';
}

function loadProperties(){
  jQuery.i18n.properties({//加载资浏览器语言对应的资源文件
    name:'strings', //资源文件名称
    path:'back/js/i18n/', //资源文件路径
    language:lg,//en显示英文，zh显示中文
    mode:'map', //用Map的方式使用资源文件中的值
    callback: function() {//加载成功后设置显示内容
      $('.hello').html($.i18n.prop('hello'));
    }
  });
}

function submitForm() {
  $('#form').submit();
}

function selectLg(lg) {
  var url = window.location.href;
  if (url.indexOf('?lg') != -1) {
    url = url.substring(0, url.indexOf('?lg'));
  }
  window.location.href = url + '?lg=' + lg;
}
</script>
</html>