<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
loadProperties();
$('.stringMailUSD').html($.i18n.prop('stringMailUSD'));
$('.stringCommissionTransferFillinTheApplicationAboutAmountFromRebateToTransaction').html($.i18n.prop('stringCommissionTransferFillinTheApplicationAboutAmountFromRebateToTransaction'));

$(function() {
  crm.admin.menu${OP.menu}.save = function(obj) {
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
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMailUSD"></span>
        </label>
        <div class="col-sm-8">
          <input name="dollar" class="form-control input-sm" type="text">
          <span class="help-block m-b-none" style="color: red;">
            <i class="fa fa-info-circle"></i>
            <span class="stringCommissionTransferFillinTheApplicationAboutAmountFromRebateToTransaction"></span>
          </span>
        </div>
      </div>
    </form>
  </div>
</div>
