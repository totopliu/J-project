<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringGroupingSynSetTransactionType').html($.i18n.prop('stringGroupingSynSetTransactionType'));

$(document).ready(function() {
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green",
  })
});
$(function() {
  crm.admin.menu${OP.menu}.updateTransaction = function(obj) {
    var transactionType = $('input[name="transactionType"]:checked').val();
    if (typeof(transactionType) == 'undefined') {
      parent.layer.msg($.i18n.prop('stringGroupingSynPleaseSelectTransactionType'), {
        shade: 0.3
      });
      return;
    }
    if ($("#menu${OP.menu}Form").valid()) {
      crm.ajaxJson({
        url: rootPath + "/op_updateTransaction_${OP.menu}",
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
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringGroupingSynSetTransactionType"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="1" name="transactionType">
              <i></i>
              ABOOK
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="2" name="transactionType">
              <i></i>
              BBOOK
            </label>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>
