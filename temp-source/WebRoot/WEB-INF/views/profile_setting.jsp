<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<div class="wrapper wrapper-content">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <form class="form-horizontal" style="margin-top:10px;">
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label"><span class="stringProfileSettingRebate"></span></label>
          <div class="col-sm-6">
            <div class="radio i-checks radio-inline">
              <label> 
                <input type="radio" <c:if test="${dto.autoRebate == 1}"> checked</c:if> value="1" name="autoRebate"> <i></i> 
                <span class="stringProfileSettingAuto"></span>
              </label>
            </div>
            <div class="radio i-checks radio-inline">
              <label> 
                <input type="radio" <c:if test="${dto.autoRebate == 0}"> checked</c:if> value="0" name="autoRebate"> <i></i> 
                <span class="stringProfileSettingManual"></span>
              </label>
            </div>
          </div>
          <div class="col-sm-2">
            <button type="button" class="btn btn-sm btn-primary" onclick="saveProfileSetting()">
              <span class="stringProfileSettingSaveRebate"></span>
            </button>
          </div>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label">
            <span class="stringProfileSettingUpdateCRMpass"></span>
          </label>
          <div class="col-sm-6">
            <input class="form-control" type="text" id="password" name="password">
          </div>
          <div class="col-sm-2">
            <button type="button" class="btn btn-sm btn-primary" onclick="savePass()">
              <span class="stringProfileSettingUpdateCRMpass"></span>
            </button>
          </div>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label">
            <span class="stringProfileSettingUpdateMTpass"></span>
          </label>
          <div class="col-sm-6">
            <input class="form-control" type="text" id="MTpassword" name="MTpassword">
            <span class="help-block m-b-none" style="color:red;">
              <i class="fa fa-info-circle"></i>
              <span class="stringProfileSettingMTpassCheckAlert"></span>
            </span>
          </div>
          <div class="col-sm-2">
            <button type="button" class="btn btn-sm btn-primary" onclick="saveMTpass()">
              <span class="stringProfileSettingUpdateMTpass"></span>
            </button>
          </div>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label">
            <span class="stringProfileSettingUpdateMTgcpass"></span>
          </label>
          <div class="col-sm-6">
            <input class="form-control" type="text" id="MTgcpassword" name="MTgcpassword">
            <span class="help-block m-b-none" style="color:red;">
              <i class="fa fa-info-circle"></i><span class="stringProfileSettingMTpassCheckAlert"></span>
            </span>
          </div>
          <div class="col-sm-2">
            <button type="button" class="btn btn-sm btn-primary" onclick="saveMTgcpass()">
              <span class="stringProfileSettingUpdateMTgcpass"></span>
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script>
  $(document).ready(function() {
    $(".i-checks").iCheck({
      checkboxClass : "icheckbox_square-green",
      radioClass : "iradio_square-green",
    })
  });

  function saveProfileSetting() {
    $.ajax({
      url : '${pageContext.request.contextPath}/saveProfileSetting',
      data : {
        autoRebate : $('input[name="autoRebate"]:checked').val()
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

  function savePass() {
    var password = $('#password').val();
    if (!checkPass(password)) {
      parent.layer.msg($.i18n.prop('stringRegErrorPassword'), {shade: 0.3});
      return;
    }
    $.ajax({
      url : '${pageContext.request.contextPath}/savePass',
      data : {
        password : password
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
  
  function saveMTpass() {
    var MTpass = $('#MTpassword').val();
    if (!checkMTpass(MTpass)) {
      parent.layer.msg($.i18n.prop('stringProfileSettingMTpassCheckAlert'), {shade: 0.3});
      return;
    }
    var oo = layer.load();
    $.ajax({
      url : '${pageContext.request.contextPath}/saveMTPass',
      data : {
          password : MTpass
      },
      dataType : 'json',
      async : true,
      type : 'post'
    }).done(function(response) {
      layer.close(oo);
      crm.toastrsAlert({
        code : response.success ? 'success' : 'error',
        msg : response.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
      });
    });
  }
  
  function saveMTgcpass() {
    var MTgcpass = $('#MTgcpassword').val();
    if (!checkMTpass(MTgcpass)) {
      parent.layer.msg($.i18n.prop('stringProfileSettingMTpassCheckAlert'), {shade: 0.3});
      return;
    }
    var oo = layer.load();
    $.ajax({
      url : '${pageContext.request.contextPath}/saveMTGcPass',
      data : {
          password : $('#MTgcpassword').val()
      },
      dataType : 'json',
      async : true,
      type : 'post'
    }).done(function(response) {
      layer.close(oo);
      crm.toastrsAlert({
        code : response.success ? 'success' : 'error',
        msg : response.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
      });
    });
  }
  
  function checkPass(str) {
      var re = /^\w{5,15}$/;
      return re.test(str);
  }
  
  function checkMTpass(pass) {
    var re = /^\w{10}$/;
        return re.test(pass);
  }
  
  loadProperties();
  $('.stringProfileSettingRebate').html($.i18n.prop('stringProfileSettingRebate'));
  $('.stringProfileSettingAuto').html($.i18n.prop('stringProfileSettingAuto'));
  $('.stringProfileSettingManual').html($.i18n.prop('stringProfileSettingManual'));
  $('.stringProfileSettingSaveRebate').html($.i18n.prop('stringProfileSettingSaveRebate'));
  $('.stringProfileSettingUpdateCRMpass').html($.i18n.prop('stringProfileSettingUpdateCRMpass'));
  $('.stringProfileSettingUpdateMTpass').html($.i18n.prop('stringProfileSettingUpdateMTpass'));
  $('.stringProfileSettingUpdateMTgcpass').html($.i18n.prop('stringProfileSettingUpdateMTgcpass'));
  $('.stringProfileSettingMTpassCheckAlert').html($.i18n.prop('stringProfileSettingMTpassCheckAlert'));
</script>