<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
  loadProperties();
  $('.stringMenuName').html($.i18n.prop('stringMenuName'));
  $('.stringAccountManagementLoginAccount').html($.i18n.prop('stringAccountManagementLoginAccount'));
  $('.stringPhone').html($.i18n.prop('stringPhone'));
  $('.stringAccountAuditUserType').html($.i18n.prop('stringAccountAuditUserType'));
  $('.stringAccountAuditAgentLevel').html($.i18n.prop('stringAccountAuditAgentLevel'));
  $('.stringAccountAuditNormalUsers').html($.i18n.prop('stringAccountAuditNormalUsers'));
  $('.stringAccountAuditAgent').html($.i18n.prop('stringAccountAuditAgent'));
  $('.stringAccountAuditIDNumber').html($.i18n.prop('stringAccountAuditIDNumber'));
  $('.stringFrontOfIDcard').html($.i18n.prop('stringFrontOfIDcard'));
  $('.stringAddressProof').html($.i18n.prop('stringAddressProof'));
  $('.stringAccountAuditRole').html($.i18n.prop('stringAccountAuditRole'));
  $('.stringAccountManagementPassword').html($.i18n.prop('stringAccountManagementPassword'));
  $('.stringAccountManagementConfirmPassword').html($.i18n.prop('stringAccountManagementConfirmPassword'));
  $('.stringMenuStatus').html($.i18n.prop('stringMenuStatus'));
  $('.stringMenuOpen').html($.i18n.prop('stringMenuOpen'));
  $('.stringMenuClose').html($.i18n.prop('stringMenuClose'));
  $('.stringAccountManagementFillinNonePasswordChangeNone').html($.i18n.prop('stringAccountManagementFillinNonePasswordChangeNone'));
  
  $("#role option:first-child").text($.i18n.prop('stringAccountAuditPleaseChooseRole'));
  
  $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
  $(function() {
    crm.admin.menu${OP.menu}.save = function(obj) {
      if ($("#menu${OP.menu}Form").valid()) {
        var type = $('input[name="type"]:checked').val();
        if (type == 1) {
          if ($('input[name="level"]').val() == '') {
            parent.layer.msg($.i18n.prop('stringAccountAuditPleaseFillinTheRebateLevel'), {
              shade: 0.3
            });
            return;
          }
        }
        crm.ajaxJson({
          url: rootPath + "/op_save_${OP.menu}",
          data: $('#menu${OP.menu}Form').serializeArray()
        },
        function(data) {
          if (data.success) {
            crm.closeWindow();
            $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
          } else {
            parent.layer.msg(data.msg, {
              shade: 0.3
            });
            return;
          }
        });
      }
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
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" action="op_save_${OP.menu}" method="post">
      <input type="hidden" name="managerid" value="${dto.managerid}" />
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMenuName"></span>
        </label>
        <div class="col-sm-8">
          <input name="name" class="form-control" type="text" value="${dto.name}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMenuName"></span>
        </label>
        <div class="col-sm-8">
          <input name="ename" class="form-control" type="text" value="${dto.ename}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountManagementLoginAccount"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" name="account" class="form-control" value="${dto.account}">
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
          <span class="stringAccountAuditUserType"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.type == 0}"> checked</c:if> value="0" name="type">
              <i></i>
              <span class="stringAccountAuditNormalUsers"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.type == 1}"> checked</c:if> value="1" name="type">
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
          <input name="level" class="form-control" type="text" value="${dto.level}">
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
          <input type="hidden" id="idcardurl" name="idcardurl" value="${dto.idcardurl}">
        </div>
        <div class="col-sm-4">
          <img alt="" src="${dto.idcardbkurl}" width="100%" onclick="headimgbk.click()" style="cursor: pointer;" id="idcardImgbk">
          <input type="file" id="headimgbk" name="uploadFile" style="display: none;" accept="" onchange="upload_file(2)">
          <input type="hidden" id="idcardbkurl" name="idcardbkurl" value="${dto.idcardbkurl}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAddressProof"></span>
        </label>
        <div class="col-sm-4">
          <img alt="" src="${dto.addrurl}" width="100%" onclick="addrimg.click()" style="cursor: pointer;" id="addrImg">
          <input type="file" id="addrimg" name="uploadFile" style="display: none;" accept="" onchange="upload_file(3)">
          <input type="hidden" id="addrurl" name="addrurl" value="${dto.addrurl}">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditRole"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control m-b" name="role" id="role">
            <option value="">请选择角色</option>
            <crm:list var="m" namespace="com.crm.platform.mapper.pub.RoleMapper.listRoleByscort">
              <option value="${m.id}" <c:if test="${m.id == dto.role}" >selected</c:if>>${m.name}</option>
            </crm:list>
          </select>
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountManagementPassword"></span>
        </label>
        <div class="col-sm-8">
          <input type="password" name="password" id="password" class="form-control">
          ${dto != null && dto.managerid != null ? '<span style="color: red;"><span class="stringAccountManagementFillinNonePasswordChangeNone"></span></span>' : ''}
        </div>
      </div>
      <c:if test="${dto == null || dto.managerid == null}">
        <div class="form-group form-group-sm">
          <label class="col-sm-3 control-label">
            <span class="stringAccountManagementConfirmPassword"></span>
          </label>
          <div class="col-sm-8">
            <input name="rePassword" class="form-control" type="password">
          </div>
        </div>
      </c:if>
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
    </form>
  </div>
</div>
