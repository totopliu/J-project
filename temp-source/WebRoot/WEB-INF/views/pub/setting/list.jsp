<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script>
loadProperties();
$('.stringSystemDepositAudit').html($.i18n.prop('stringSystemDepositAudit'));
$('.stringSystemReminderMail').html($.i18n.prop('stringSystemReminderMail'));
$('.stringSystemAutomatic').html($.i18n.prop('stringSystemAutomatic'));
$('.stringSystemManual').html($.i18n.prop('stringSystemManual'));

crm.ns('crm.admin.menu${OP.menu}');
$(document).ready(function() {
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green",
  });
});

crm.admin.menu${OP.menu}.saveInGoldSetting = function() {
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/setting/saveSysSetting',
    data: {
      autoIn: $('input[name="autoIn"]:checked').val()
    },
    dataType: 'json',
    async: true,
    type: 'post'
  }).done(function(response) {
    crm.toastrsAlert({
      code: response.success ? 'success': 'error',
      msg: response.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
    });
  });
}

crm.admin.menu${OP.menu}.saveRemindEmail = function() {
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/setting/saveRemindEmail',
    data: {
      remindEmail: $('#remindEmail').val()
    },
    dataType: 'json',
    async: true,
    type: 'post'
  }).done(function(response) {
    crm.toastrsAlert({
      code: response.success ? 'success': 'error',
      msg: response.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
    });
  });
}

</script>
<div class="wrapper wrapper-content">
  <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <table class="table table-bordered">
        <tr>
          <td align="right">
            <span class="stringSystemDepositAudit"></span>
          </td>
          <td>
            <div class="radio i-checks radio-inline">
              <label>
                <input type="radio" <c:if test="${autoIn == 1}"> checked</c:if> value="1" name="autoIn">
                <i></i>
                <span class="stringSystemAutomatic"></span>
              </label>
            </div>
            <div class="radio i-checks radio-inline">
              <label>
                <input type="radio" <c:if test="${autoIn == 0}"> checked</c:if> value="0" name="autoIn">
                <i></i>
                <span class="stringSystemManual"></span>
              </label>
            </div>
          </td>
        </tr>
        <tr>
          <td align="right">
            <span class="stringSystemReminderMail"></span>
          </td>
          <td>
            <input type="text" class="form-control" id="remindEmail" value="${remindEmail }">
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
