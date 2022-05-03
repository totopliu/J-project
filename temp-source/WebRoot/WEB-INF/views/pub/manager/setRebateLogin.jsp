<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringMailMT5RebateAccount').html($.i18n.prop('stringMailMT5RebateAccount'));
$('.stringMailMT5RebateAccountPassword').html($.i18n.prop('stringMailMT5RebateAccountPassword'));

$(function() {
  crm.admin.menu${OP.menu}.updateRebateLogin = function(obj) {
    if ($('input[name="rebateLogin"]').val() == '') {
      parent.layer.msg($.i18n.prop('stringAccountManagementPleaseFillinAccount'), {
        shade: 0.3
      });
      return;
    }
    if ($('input[name="rebateLoginPwd"]').val() == '') {
      parent.layer.msg($.i18n.prop('stringAccountManagementPleaseFillinPassword'), {
        shade: 0.3
      });
      return;
    }
    if ($("#menu${OP.menu}Form").valid()) {
      crm.ajaxJson({
        url: rootPath + "/op_updateRebateLogin_${OP.menu}",
        data: $('#menu${OP.menu}Form').serializeArray()
      },
      function() {
        crm.closeWindow();
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
      });
    }
  }
});
</script>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" method="post">
      <input type="hidden" name="managerId" value="${managerId}" />
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMailMT5RebateAccount"></span>
        </label>
        <div class="col-sm-8">
          <input name="rebateLogin" class="form-control" type="text" value="${dto.rebateLogin }">
        </div>
      </div>
      <div class="form-group form-group-sm">
        <label class="col-sm-3 control-label">
          <span class="stringMailMT5RebateAccountPassword"></span>
        </label>
        <div class="col-sm-8">
          <input name="rebateLoginPwd" class="form-control" type="text" value="${dto.rebateLoginPwd }">
        </div>
      </div>
    </form>
  </div>
</div>
