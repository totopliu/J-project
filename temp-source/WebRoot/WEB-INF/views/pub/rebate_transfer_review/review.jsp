<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringMailUserName').html($.i18n.prop('stringMailUserName'));
$('.stringMailMT5TransactionAccount').html($.i18n.prop('stringMailMT5TransactionAccount'));
$('.stringPendingApprovalForWithdrawalMT5TransactionAccountBalance').html($.i18n.prop('stringPendingApprovalForWithdrawalMT5TransactionAccountBalance'));
$('.stringMailMT5RebateAccount').html($.i18n.prop('stringMailMT5RebateAccount'));
$('.stringPendingApprovalForWithdrawalMT5CommissionAccountBalance').html($.i18n.prop('stringPendingApprovalForWithdrawalMT5CommissionAccountBalance'));
$('.stringPendingApprovalForCommissionTransferAmountUSD').html($.i18n.prop('stringPendingApprovalForCommissionTransferAmountUSD'));
$('.stringPendingApprovalForDepositAudit').html($.i18n.prop('stringPendingApprovalForDepositAudit'));
$('.stringAccountAuditAgree').html($.i18n.prop('stringAccountAuditAgree'));
$('.stringAccountAuditRefuse').html($.i18n.prop('stringAccountAuditRefuse'));
$('.stringAccountAuditRefuseReason').html($.i18n.prop('stringAccountAuditRefuseReason'));
$('.stringPendingApprovalForCommissionTransferAmountFromRebateToTransactionAccount').html($.i18n.prop('stringPendingApprovalForCommissionTransferAmountFromRebateToTransactionAccount'));

$(document).ready(function() {
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green",
  });
});
$(function() {
  crm.admin.menu${OP.menu}.save = function(obj) {
    var inDefuseState = $('input[name="state"]:checked').val();
    if (typeof(inDefuseState) == 'undefined') {
      parent.layer.msg($.i18n.prop("stringAccountAuditPleaseChooseAgreeOrRefuse"), {
        shade: 0.3
      });
      return;
    }
    if (inDefuseState == 2) {
      if ($('input[name="reason"]').val() == '') {
        parent.layer.msg($.i18n.prop("stringAccountAuditPleaseFillinRefuseReason"), {
          shade: 0.3
        });
        return;
      }
    }
    if ($("#menu${OP.menu}Form").valid()) {
      var oo = layer.load();
      crm.ajaxJson({
        url: rootPath + "/op_save_${OP.menu}",
        data: $('#menu${OP.menu}Form').serializeArray()
      },
      function() {
        crm.closeWindow();
        layer.close(oo);
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
      });
    }
  }
});
</script>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" action="op_save_${OP.menu}" method="post">
      <input type="hidden" name="id" value="${dto.id}">
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMailUserName"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.name }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMailMT5TransactionAccount"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.login }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringPendingApprovalForWithdrawalMT5TransactionAccountBalance"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.balance }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMailMT5RebateAccount"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.rebateLogin }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringPendingApprovalForWithdrawalMT5CommissionAccountBalance"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.rebateBalance }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringPendingApprovalForCommissionTransferAmountUSD"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.dollar }</p>
          <span class="help-block m-b-none" style="color: red;">
            <i class="fa fa-info-circle"></i>
            <span class="stringPendingApprovalForCommissionTransferAmountFromRebateToTransactionAccount"></span>
          </span>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringPendingApprovalForDepositAudit"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="1" name="state">
              <i></i>
              <span class="stringAccountAuditAgree"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="2" name="state">
              <i></i>
              <span class="stringAccountAuditRefuse"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringAccountAuditRefuseReason"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" class="form-control" name="reason">
        </div>
      </div>
    </form>
  </div>
</div>
