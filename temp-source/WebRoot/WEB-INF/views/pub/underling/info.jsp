<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringDepositName').html($.i18n.prop('stringDepositName'));
$('.stringAccount').html($.i18n.prop('stringAccount'));
$('.stringIDNumber').html($.i18n.prop('stringIDNumber'));
$('.stringFrontOfIDcard').html($.i18n.prop('stringFrontOfIDcard'));
$('.stringOppositeOfIDcard').html($.i18n.prop('stringOppositeOfIDcard'));
$('.stringCustomerListBank').html($.i18n.prop('stringCustomerListBank'));
$('.stringCustomerListBankCardNumber').html($.i18n.prop('stringCustomerListBankCardNumber'));
$('.stringCustomerListBankCard').html($.i18n.prop('stringCustomerListBankCard'));

$(document).ready(function() {
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green",
  });
});
$(function() {
  crm.admin.menu${OP.menu}.close = function(obj) {
    crm.closeWindow();
    $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
  }
});
</script>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate">
      <input type="hidden" name="managerid" value="${dto.managerid}" />
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringDepositName"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.name }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringAccount"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.account }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringIDNumber"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.idcard }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringFrontOfIDcard"></span>
        </label>
        <div class="col-sm-8">
          <img alt="" src="${dto.idcardurl }" width="200">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringOppositeOfIDcard"></span>
        </label>
        <div class="col-sm-8">
          <img alt="" src="${dto.idcardbkurl }" width="200">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringCustomerListBank"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.bank }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringCustomerListBankCardNumber"></span>
        </label>
        <div class="col-sm-8">
          <p class="form-control-static">${dto.bankCard }</p>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringCustomerListBankCard"></span>
        </label>
        <div class="col-sm-8">
          <img alt="" src="${dto.bankCardUrl }" width="200">
        </div>
      </div>
    </form>
  </div>
</div>
