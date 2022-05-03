<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringAccountManagementSetSuperior').html($.i18n.prop('stringAccountManagementSetSuperior'));

$(function() {
  crm.admin.menu${OP.menu}.saveSetting = function(obj) {
    if ($("#menu${OP.menu}Form").valid()) {
      crm.ajaxJson({
        url: rootPath + "/op_settingBelong_${OP.menu}",
        data: $('#menu${OP.menu}Form').serializeArray()
      },
      function() {
        crm.closeWindow();
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
      });
    }
  }
});
$.ajax({
  url: '${pageContext.request.contextPath}/pub/manager/listReviewedManager',
  data: {},
  dataType: 'json',
  async: true,
  type: 'get'
}).done(function(response) {
  if (response.success) {
    var data = response.data;
    var source = '<option value="">' + $.i18n.prop('stringAccountAuditPleaseChooseNewSuperior') + '</option>';
    for (var i = 0; i < data.length; i++) {
      source += '<option value="' + data[i].managerid + '">' + data[i].name + '</option>';
    }
    $('#belongUser').html(source);
    $("#belongUser").selectpicker({});
  }
});
</script>
<style>
    .pull-left {margin-top: 0px !important;}
</style>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" action="op_save_${OP.menu}" method="post">
      <input type="hidden" name="managerId" value="${managerId}" />
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringAccountManagementSetSuperior"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control selectpicker" id="belongUser" name="belongId" data-live-search="true"></select>
        </div>
      </div>
    </form>
  </div>
</div>
