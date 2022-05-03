<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
  $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
  
  loadProperties();
  $('.stringDepositName').html($.i18n.prop('stringDepositName'));
  $('.stringAccountAuditMailbox').html($.i18n.prop('stringAccountAuditMailbox'));
  $('.stringPhone').html($.i18n.prop('stringPhone'));
  $('.stringAccountAuditSuperiorAgent').html($.i18n.prop('stringAccountAuditSuperiorAgent'));
  $('.stringAccountAuditSuperiorLevel').html($.i18n.prop('stringAccountAuditSuperiorLevel'));
  $('.stringAccountAuditResetSuperior').html($.i18n.prop('stringAccountAuditResetSuperior'));
  $('.stringAccountAuditUserType').html($.i18n.prop('stringAccountAuditUserType'));
  $('.stringAccountAuditNormalUsers').html($.i18n.prop('stringAccountAuditNormalUsers'));
  $('.stringAccountAuditAgent').html($.i18n.prop('stringAccountAuditAgent'));
  $('.stringAccountAuditAgentLevel').html($.i18n.prop('stringAccountAuditAgentLevel'));
  $('.stringGroupingSynMTGroup').html($.i18n.prop('stringGroupingSynMTGroup'));
  $('.stringAccountAuditIDNumber').html($.i18n.prop('stringAccountAuditIDNumber'));
  $('.stringFrontOfIDcard').html($.i18n.prop('stringFrontOfIDcard'));
  $('.stringAddressProof').html($.i18n.prop('stringAddressProof'));
  $('.stringAccountAuditRole').html($.i18n.prop('stringAccountAuditRole'));
  $('.stringAccountAuditResetRole').html($.i18n.prop('stringAccountAuditResetRole'));
  $('.stringMenuStatus').html($.i18n.prop('stringMenuStatus'));
  $('.stringMenuOpen').html($.i18n.prop('stringMenuOpen'));
  $('.stringMenuClose').html($.i18n.prop('stringMenuClose'));
  $('.stringAccountAuditAgree').html($.i18n.prop('stringAccountAuditAgree'));
  $('.stringAccountAuditRefuse').html($.i18n.prop('stringAccountAuditRefuse'));
  $('.stringAccountAuditRefuseReason').html($.i18n.prop('stringAccountAuditRefuseReason'));
  $('.stringAccountAuditNoChooseNoChangeSuperior').html($.i18n.prop('stringAccountAuditNoChooseNoChangeSuperior'));
  $('.stringAccountAuditOrdinaryUserFillinNone').html($.i18n.prop('stringAccountAuditOrdinaryUserFillinNone'));
  $('.stringMailEnglishName').html($.i18n.prop('stringMailEnglishName'));
  $('#level').attr('placeholder',$.i18n.prop('stringAccountAuditFillinNumberForExample'));
  
  $("#group option:first-child").text($.i18n.prop('stringAccountAuditPleaseChooseGroup'));
  $("#role option:first-child").text($.i18n.prop('stringAccountAuditPleaseChooseRole'));
  
$(function(){
  crm.admin.menu${OP.menu}.save = function(obj) {
    var regReviewState = $('input[name="reviewState"]:checked').val();
    if (typeof(regReviewState) == "undefined") {
      parent.layer.msg($.i18n.prop('stringAccountAuditPleaseChoosePassOrRefuse'), {shade: 0.3});
      return;
    }
    if (regReviewState == 2) {
    if ($('input[name="reviewReason"]').val() == '') {
      parent.layer.msg($.i18n.prop('stringAccountAuditPleaseFillinRefuseReason'), {shade: 0.3});
      return;
    }
    }
    var type = $('input[name="type"]:checked').val();
    if (typeof(type) == 'undefined') {
      parent.layer.msg($.i18n.prop('stringAccountAuditPleaseChooseUserType'), {shade: 0.3});
      return;
    }
    if (type == 1) {
      if ($('input[name="level"]').val() == '') {
        parent.layer.msg($.i18n.prop('stringAccountAuditPleaseFillinTheRebateLevel'), {shade: 0.3});
        return;
      }
    }
    if($("#menu${OP.menu}Form").valid()){
      var oo = layer.load();
      crm.ajaxJson({url:rootPath + "/op_save_${OP.menu}",data:$('#menu${OP.menu}Form').serializeArray()},function(){
        crm.closeWindow();
        layer.close(oo);
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
      });
    }
  }
});
  
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/manager/listReviewedManager',
    data : {},
    dataType: 'json',
    async: true,
    type: 'get'
  }).done(function(response){
    if (response.success) {
      var data = response.data;
      var source = '<option value="">'+$.i18n.prop('stringAccountAuditPleaseChooseNewSuperior')+'</option>';
      for (var i = 0; i < data.length; i++) {
        source += '<option value="' + data[i].managerid + '">'+ data[i].name +'</option>';
      }
      $('#belongSelect').html(source);
      $("#belongSelect").selectpicker({});
    }
  });
  
//ajax上传文件
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
          $('#idcardImg').prop('src', response.data);
        } else if (tag == 2) {
          $('#idcardbkurl').val(response.data);
          $('#idcardImgbk').prop('src', response.data);
        } else if (tag == 3) {
          $('#addrurl').val(response.data);
          $('#addrImg').prop('src', response.data);
        }
      } else {
        alert($.i18n.prop('stringMenuFailure') + "！");
      }
    },
    error: function(d) {
      console.log(d);
    }
  });
}
</script>
<style>
    .pull-left {margin-top: 0px !important;}
</style>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" action="op_save_${OP.menu}" method="post">
      <input type="hidden" name="managerid" value="${dto.managerid}" />
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringDepositName"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" name="name" value="${dto.name}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMailEnglishName"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" name="ename" value="${dto.ename}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditMailbox"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" name="account" value="${dto.account}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringPhone"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" name="phone" value="${dto.phone}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditSuperiorAgent"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.belongName }</p>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditSuperiorLevel"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.belongLevel }</p>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditResetSuperior"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control selectpicker" id="belongSelect" name="belongid" data-live-search="true"></select>
          <span class="help-block m-b-none" style="color: red;">
            <i class="fa fa-info-circle"></i>
            <span class="stringAccountAuditNoChooseNoChangeSuperior"></span>
          </span>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditUserType"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="0" name="type">
              <i></i>
              <span class="stringAccountAuditNormalUsers"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="1" name="type">
              <i></i>
              <span class="stringAccountAuditAgent"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditAgentLevel"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" id="level" name="level" placeholder="填写数字，例如：1">
          <span class="help-block m-b-none" style="color: red;">
            <i class="fa fa-info-circle"></i>
            <span class="stringAccountAuditOrdinaryUserFillinNone"></span>
          </span>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringGroupingSynMTGroup"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control m-b" name="group" id="group">
            <option value="">请选择组</option>
            <crm:list var="n" namespace="com.crm.platform.mapper.pub.GroupTypeMapper.listGroup">
              <option value="${n.Group}" <c:if test="${dto.belongGroup eq  n.Group}">selected</c:if>>${n.Group}</option>
            </crm:list>
          </select>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditIDNumber"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" name="idcard" value="${dto.idcard}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringFrontOfIDcard"></span>
        </label>
        <div class="col-sm-4">
          <img alt="" src="${dto.idcardurl}" width="100%" onclick="headimg.click()" style="cursor: pointer;" id="idcardImg">
          <input type="file" id="headimg" name="uploadFile" style="display: none;" accept="" onchange="upload_file(1)">
          <input type="hidden" id="idcardurl" name="idcardurl" value="${dto.idcardurl }">
        </div>
        <div class="col-sm-4">
          <img alt="" src="${dto.idcardbkurl}" width="100%" onclick="headimgbk.click()" style="cursor: pointer;" id="idcardImgbk">
          <input type="file" id="headimgbk" name="uploadFile" style="display: none;" accept="" onchange="upload_file(2)">
          <input type="hidden" id="idcardbkurl" name="idcardbkurl" value="${dto.idcardbkurl }">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAddressProof"></span>
        </label>
        <div class="col-sm-4">
          <img alt="" src="${dto.addrurl}" width="100%" onclick="addrimg.click()" style="cursor: pointer;" id="addrImg">
          <input type="file" id="addrimg" name="uploadFile" style="display: none;" accept="" onchange="upload_file(3)">
          <input type="hidden" id="addrurl" name="addrurl" value="${dto.addrurl }">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditRole"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.rolename}</p>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditResetRole"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control m-b" name="role" id="role">
            <option value="">请选择角色</option>
            <crm:list var="m" namespace="com.crm.platform.mapper.pub.RoleMapper.listRoleByscort">
              <option value="${m.id}" <c:if test="${m.id eq dto.role}" >selected</c:if>>${m.name}</option>
            </crm:list>
          </select>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMenuStatus"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.locked == 1}"> checked</c:if> value="1" name="locked">
              <i></i>
              <span class="stringMenuOpen"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.locked == 0}"> checked</c:if> value="0" name="locked">
              <i></i>
              <span class="stringMenuClose"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMenuStatus"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.reviewState == 1}"> checked</c:if> value="1" name="reviewState">
              <i></i>
              <span class="stringAccountAuditAgree"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.reviewState == 2}"> checked</c:if> value="2" name="reviewState">
              <i></i>
              <span class="stringAccountAuditRefuse"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditRefuseReason"></span>
        </label>
        <div class="col-sm-8">
          <input class="form-control" type="text" name="reviewReason" value="${dto.reviewReason}">
        </div>
      </div>
    </form>
  </div>
</div>
