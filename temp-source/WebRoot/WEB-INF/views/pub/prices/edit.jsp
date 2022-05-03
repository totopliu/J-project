<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringCurrencySynCurrencyGroup').html($.i18n.prop('stringCurrencySynCurrencyGroup'));
$("#currency_id option:first-child").text($.i18n.prop('stringCurrencySynPleaseChooseCurrencygroup'));

$(document).ready(function() {
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green",
  });
});

$(function() {
  crm.admin.menu${OP.menu}.save = function(obj) {
    if ($("#menu${OP.menu}Form").valid()) {
      crm.ajaxJson({
        url: rootPath + "/op_save_${OP.menu}",
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
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" action="op_save_${OP.menu}" method="post">
      <input type="hidden" name="symbols" value="${symbols}" />
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringCurrencySynCurrencyGroup"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control m-b" id="currency_id" name="currencyId">
            <option value="">请选择货币组</option>
            <crm:list var="m" namespace="com.crm.platform.mapper.pub.CurrencyMapper.getCurrencyListForPrice">
              <option value="${m.id}">${m.name}</option>
            </crm:list>
          </select>
        </div>
      </div>
    </form>
  </div>
</div>
