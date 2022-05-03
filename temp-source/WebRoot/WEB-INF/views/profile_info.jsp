<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="wrapper wrapper-content">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <table class="table table-bordered">
        <tr>
          <td align="right">
            <span class="stringAccount"></span>
          </td>
          <td>${dto.account }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringProfileMTAccount"></span>
          </td>
          <td>${dto.login }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringProfileBalance"></span>
          </td>
          <td>${balance }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringRealName"></span>
          </td>
          <td>${dto.name }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringMailEnglishName"></span>
          </td>
          <td>${dto.ename }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringPhone"></span>
          </td>
          <td>${dto.phone }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringIDNumber"></span>
          </td>
          <td>${dto.idcard }</td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringAddressProof"></span>
          </td>
          <td>
            <img id="addrpic" src="${dto.addrurl }" style="width: 200px;">
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringFrontOfIDcard"></span>
          </td>
          <td>
            <img id="idcardpic" src="${dto.idcardurl }" style="width: 200px;">
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringOppositeOfIDcard"></span>
          </td>
          <td>
            <img id="idcardbkpic" src="${dto.idcardbkurl }" style="width: 200px;">
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringProfileBank"></span>
          </td>
          <td>
            <input class="form-control input-sm" type="text" id="profileBank" value="${dto.bank }">
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringCustomerListBankCardNumber"></span>
          </td>
          <td>
            <input class="form-control input-sm" type="text" id="profileBankCard" value="${dto.bankCard }">
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringProfileBankPic"></span>
          </td>
          <td>
            <a style="padding: 4px 10px;" class="btn btn-sm btn-w-m btn-white" onclick="javascript:bankimg.click()">
              <span class="stringProfileUpload"></span>
            </a>
            <input type="file" id="bankimg" name="uploadFile" style="display: none;" accept="" onchange="upload_file(0)">
            <input type="hidden" id="profileBankCardUrl" value="${dto.bankCardUrl }">
            <img id="bankpic" src="${dto.bankCardUrl }" style="width: 200px;">
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2">
            <input type="hidden" value="${dto.managerid }" id="managerid">
            <button type="button" class="btn btn-sm btn-w-m btn-primary" onclick="saveProfileInfo()">
              <span class="stringMailSave"></span>
            </button>
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
<script>
//ajax上传文件
function upload_file(tag) {
  var fileElementId, fileInputId, fileImgId;
  switch (tag) {
  case 0:
    fileElementId = 'bankimg';
    fileInputId = 'profileBankCardUrl';
    fileImgId = 'bankpic';
    break;
  case 1:
    fileElementId = 'idCardFront';
    fileInputId = 'idcardurl';
    fileImgId = 'idcardpic';
    break;
  case 2:
    fileElementId = 'idCardBack';
    fileInputId = 'idcardbkurl';
    fileImgId = 'idcardbkpic';
    break;
  case 3:
    fileElementId = 'addrInput';
    fileInputId = 'addrurl';
    fileImgId = 'addrpic';
    break;
  default:
    break;
  }
  $.ajaxFileUpload({
    url : '${pageContext.request.contextPath}/uploadCard',
    secureuri : false,
    fileElementId : fileElementId,
    dataType : "json",
    type : "POST",
    success : function(response) {
      if (response.success) {
        $('#' + fileInputId).val(response.data);
        $('#' + fileImgId).prop('src', response.data);
      } else {
        alert($.i18n.prop('stringProfileUplaodFail'));
      }
    },
    error : function(d) {
    	console.log(d);
    }
  });
}

function saveProfileInfo() {
  $.ajax({
    url : '${pageContext.request.contextPath}/updProfileInfo',
    data : {
      managerid : $('#managerid').val(),
      bank : $('#profileBank').val(),
      bankCard : $('#profileBankCard').val(),
      bankCardUrl : $('#profileBankCardUrl').val()
    },
    dataType : 'json',
    async : true,
    type : 'post'
  }).done(function(response) {
    crm.toastrsAlert({
      code : response.success ? 'success' : 'error',
      msg : response.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
    });
  });
}

loadProperties();
$('.stringAccount').html($.i18n.prop('stringAccount'));
$('.stringProfileMTAccount').html($.i18n.prop('stringProfileMTAccount'));
$('.stringProfileBalance').html($.i18n.prop('stringProfileBalance'));
$('.stringRealName').html($.i18n.prop('stringRealName'));
$('.stringMailEnglishName').html($.i18n.prop('stringMailEnglishName'));
$('.stringPhone').html($.i18n.prop('stringPhone'));
$('.stringAddressProof').html($.i18n.prop('stringAddressProof'));
$('.stringIDNumber').html($.i18n.prop('stringIDNumber'));
$('.stringFrontOfIDcard').html($.i18n.prop('stringFrontOfIDcard'));
$('.stringOppositeOfIDcard').html($.i18n.prop('stringOppositeOfIDcard'));
$('.stringProfileBank').html($.i18n.prop('stringProfileBank'));
$('.stringCustomerListBankCardNumber').html($.i18n.prop('stringCustomerListBankCardNumber'));
$('.stringProfileBankPic').html($.i18n.prop('stringProfileBankPic'));
$('.stringMailSave').html($.i18n.prop('stringMailSave'));
$('.stringProfileUpload').html($.i18n.prop('stringProfileUpload'));

</script>