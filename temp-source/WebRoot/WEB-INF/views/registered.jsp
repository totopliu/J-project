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
  <link href="${pageContext.request.contextPath}/back/css/bootstrap.min.css" rel="stylesheet">
  <script>
    if (window.top !== window.self) {
      window.top.location = window.location;
    }
  </script>
<style type="text/css">
  .login-error {
      font-size: 16px;
      color: red;
  }
  
  .reg-success {
      font-size: 16px;
      color: green;
  }
  
  .form-control {
      color: #000;
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
</head>

<body>
  <div class="dropdown lg-select">
    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
      <span id="lg" style="border-right: 1px solid #ccc; padding-right: 5px;">????????????</span>
      <span class="glyphicon glyphicon-align-justify"></span>
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style="background-color: transparent; min-width: 110px;">
      <li style="border: 1px dashed #1ABC9C;">
        <a href="javascript:void(0);" onclick="selectLg('zh')" class="lg-item">????????????</a>
      </li>
      <li style="border: 1px dashed #1ABC9C;">
        <a href="javascript:void(0);" onclick="selectLg('en')" class="lg-item">ENGLISH</a>
      </li>
      <li style="border: 1px dashed #1ABC9C;">
        <a href="javascript:void(0);" onclick="selectLg('vi')" class="lg-item">Ti???ng Vi???t</a>
      </li>
    </ul>
  </div>
  <div class="main-wrapper">
    <div class="register-wrapper">
      <!-- <h1 class="title"><span class="bold-text">CRM</span>????????????</h1> -->
      <div style="margin: 0 auto; width: 100px;">
        <img src="${pageContext.request.contextPath}/common/favicon.png" width="100" height="100">
      </div>
      <form class="form-area">
        <input type="hidden" name="belongid" value="${belongid }" />
        <input class="input-box" type="text" id="account" name="account" placeholder="???????????????" />
        <div class="input-group">
          <input class="input-box" type="password" id="password" name="password" placeholder="???????????????" />
          <input class="input-box" type="password" id="repassword" name="repassword" placeholder="?????????????????????" />
        </div>
        <div class="input-group">
          <input class="input-box" type="text" id="phoneNumber" name="phone" placeholder="??????" />
          <input class="input-box" type="text" id="ename" name="ename" placeholder="?????????" />
        </div>
        <select id="country" class="input-box">
          <option value="">???????????????</option>
          <c:forEach var="c" items="${country }">
            <option value="${c.id }">${c.country }</option>
          </c:forEach>
        </select>
        <input class="input-box" type="email" id="realname" name="name" placeholder="????????????" />
        <input class="input-box" type="text" id="idcard" name="idcard" placeholder="????????????" />
        <div class="updata">
          <div class="updata-item">
            <span class="updata-text">
              <span class="stringFrontOfIDcard"></span>
            </span>
            <div class="updata-area" onclick="headimg.click()" style="cursor: pointer;" id="idcardImg">
              <input type="file" id="headimg" name="uploadFile" style="display: none;" accept="image/jpg,image/jpeg,image/png" onchange="upload_file(1)">
              <input type="hidden" id="idcardurl" name="idcardurl">
            </div>
          </div>
          <div class="updata-item">
            <span class="updata-text">
              <span class="stringOppositeOfIDcard"></span>
            </span>
            <div class="updata-area" onclick="headimgbk.click()" style="cursor: pointer;" id="idcardImgbk">
              <input type="file" id="headimgbk" name="uploadFile" style="display: none;" accept="image/jpg,image/jpeg,image/png" onchange="upload_file(2)">
              <input type="hidden" id="idcardbkurl" name="idcardbkurl">
            </div>
          </div>
        </div>
        <div class="updata">
          <div class="updata-item">
            <span class="updata-text">
              <span class="stringAddressProof"></span>
            </span>
            <div class="updata-area" onclick="addrimg.click()" style="cursor: pointer;" id="addrImg">
              <input type="file" id="addrimg" name="uploadFile" style="display: none;" accept="image/jpg,image/jpeg,image/png" onchange="upload_file(3)">
              <input type="hidden" id="addrurl" name="addrurl">
            </div>
          </div>
          <div class="updata-item"></div>
        </div>
        <input type="hidden" name="lg" value="${param.lg}">
        <div class="input-group">
          <p class="login-error" style="width: 100%; text-align: center;"></p>
          <p class="reg-success" style="width: 100%; text-align: center;"></p>
        </div>
      </form>
    </div>
    <input type="button" class="submit-btn" id="register" value="??????" onclick="submit_form()" />
    <div class="go-login">
      <span class="stringAlreadyHaveAccount"></span>
      ???
      <a class="now-login" href="${pageContext.request.contextPath}/login?lg=${param.lg}">
        <span class="stringLogin"></span>
      </a>
    </div>
  </div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/plugins/ajaxFileUpload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/back/js/bootstrap.min.js"></script>
<script type="text/javascript">

(function($) {
  $.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
  }
})(jQuery);

$(function() {
  if ('${param.lg}' != '') {
    if ('${param.lg}' == 'zh') {
      $('#lg').html('????????????');
    } else if ('${param.lg}' == 'en') {
      $('#lg').html('ENGLISH');
    } else if ('${param.lg}' == 'vi') {
      $('#lg').html('Ti???ng Vi???t');
      $('body').css('font-family', 'Times New Roman');
      $('.main-wrapper').css('font-family', 'Times New Roman');
    }
  } else {
    $('#lg').html('????????????');
    var url = window.location.href;
    if (url.indexOf('?lg') != -1) {
      url = url.substring(0, url.indexOf('?lg'));
    }
    window.location.href = url + '?lg=zh';
  }
  loadProperties();
});

function loadProperties() {
  jQuery.i18n.properties({ //?????????????????????????????????????????????
    name: 'strings',
    //??????????????????
    path: '${pageContext.request.contextPath}/back/js/i18n/',
    //??????????????????
    language: $.getUrlParam('lg'),
    //en???????????????zh????????????
    mode: 'map',
    //???Map????????????????????????????????????
    callback: function() { //?????????????????????????????????
      $('.hello').html($.i18n.prop('hello'));
      $('#account').attr('placeholder', $.i18n.prop('stringPleaseEnterMailbox'));
      $('#password').attr('placeholder', $.i18n.prop('stringPleaseFillinPassword'));
      $('#repassword').attr('placeholder', $.i18n.prop('stringPleaseReenterPassword'));
      $('#phoneNumber').attr('placeholder', $.i18n.prop('stringPhoneNumber'));
      $('#ename').attr('placeholder', $.i18n.prop('stringRegisterEnglishName'));
      $('#realname').attr('placeholder', $.i18n.prop('stringRegisterRealname'));
      $('#idcard').attr('placeholder', $.i18n.prop('stringIDNumber'));
      $('#register').attr('value', $.i18n.prop('stringRegister'));

      $("#country option:first-child").text($.i18n.prop('stringRegisterPleaseSelectCountry'));
      $('.stringFrontOfIDcard').html($.i18n.prop('stringFrontOfIDcard'));
      $('.stringOppositeOfIDcard').html($.i18n.prop('stringOppositeOfIDcard'));
      $('.stringAddressProof').html($.i18n.prop('stringAddressProof'));
      $('.stringAlreadyHaveAccount').html($.i18n.prop('stringAlreadyHaveAccount'));
      $('.stringLogin').html($.i18n.prop('stringLogin'));
    }
  });
}

// ajax????????????
function upload_file(tag) {
  var inputId;
  if (tag == 1) {
    inputId = 'headimg';
  } else if (tag == 2) {
    inputId = 'headimgbk';
  } else if (tag == 3) {
    inputId = 'addrimg';
  }
  $.ajaxFileUpload({
    url: '${pageContext.request.contextPath}/pub/manager/upload',
    secureuri: false,
    fileElementId: inputId,
    dataType: "json",
    type: "POST",
    success: function(response) {
      if (response.success) {
        if (tag == 1) {
          $('#idcardurl').val(response.data);
          $('#idcardImg').css('background-image', "url(../" + response.data + ")");
        } else if (tag == 2) {
          $('#idcardbkurl').val(response.data);
          $('#idcardImgbk').css('background-image', "url(../" + response.data + ")");
        } else if (tag == 3) {
          $('#addrurl').val(response.data);
          $('#addrImg').css('background-image', "url(../" + response.data + ")");
        }
      } else {
        alert($.i18n.prop('stringMenuFailure'));
      }
    },
    error: function(d) {
      console.log(d);
    }
  });
}

// ??????????????????
function submit_form() {
  // ??????????????????
  $('.reg-success').html('');
  $('.login-error').html('');
  // ???????????????
  var managerid = $('input[name="managerid"]').val();
  var account = $('input[name="account"]').val();
  var password = $('input[name="password"]').val();
  var repassword = $('input[name="repassword"]').val();
  var name = $('input[name="name"]').val();
  var phone = $('input[name="phone"]').val();
  var idcard = $('input[name="idcard"]').val();
  var idcardurl = $('input[name="idcardurl"]').val();
  var idcardbkurl = $('input[name="idcardbkurl"]').val();
  var belongid = $('input[name="belongid"]').val();
  var ename = $('input[name="ename"]').val();
  var addrurl = $('input[name="addrurl"]').val();
  var country = $('#country').val();
  // ????????????
  if (account == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyEmail'));
    return;
  } else if (password == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyPassword'));
    return;
  } else if (!checkPass(password)) {
    $('.login-error').html($.i18n.prop('stringRegErrorPassword'));
    return;
  } else if (password != repassword) {
    $('.login-error').html($.i18n.prop('stringRegErrorCheckPassword'));
    return;
  } else if (phone == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyPhone'));
    return;
  } else if (ename == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyEname'));
    return;
  } else if (name == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyName'));
    return;
  } else if (!checkEmail(account)) {
    $('.login-error').html($.i18n.prop('stringRegErrorEmail'));
    return;
  } else if (belongid == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyBelong'));
    return;
  } else if (idcard == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyIdcard'));
    return;
  } else if (idcardurl == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyIdcardurl'));
    return;
  } else if (idcardbkurl == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyIdcardbkurl'));
    return;
  } else if (addrurl == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyAddrurl'));
    return;
  } else if (country == '') {
    $('.login-error').html($.i18n.prop('stringRegEmptyCountry'));
    return;
  } else {
    $.ajax({
      url: '${pageContext.request.contextPath}/registered',
      data: {
        managerid: managerid,
        account: account,
        password: password,
        name: name,
        phone: phone,
        idcard: idcard,
        idcardurl: idcardurl,
        idcardbkurl: idcardbkurl,
        belongid: belongid,
        addrurl: addrurl,
        ename: ename,
        country: country,
        lg: $('input[name="lg"]').val()
      },
      dataType: 'json',
      async: true,
      type: 'post'
    }).done(function(response) {
      if (response.success) {
        alert(response.msg);
      } else {
        alert(response.msg);
      }
    });
  }
}

//???????????????6-16???
function checkPass(str) {
  var re = /^\w{5,15}$/;
  return re.test(str);
}

// ????????????
function checkEmail(szMail) {
  var szReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
  return szReg.test(szMail);
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