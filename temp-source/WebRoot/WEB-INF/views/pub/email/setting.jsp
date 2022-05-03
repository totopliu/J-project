<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>

<script type="text/javascript">
loadProperties();
$('.stringMailSMTPServer').html($.i18n.prop('stringMailSMTPServer'));
$('.stringMailAccount').html($.i18n.prop('stringMailAccount'));
$('.stringMailPassword').html($.i18n.prop('stringMailPassword'));
$('.stringMailSave').html($.i18n.prop('stringMailSave'));
$('.stringMailBack').html($.i18n.prop('stringMailBack'));
  
  $(function(){
    crm.admin.menu${OP.menu}.saveSetting = function(obj) {
      if ($("#menu${OP.menu}Form").valid()) {
        crm.ajaxJson({url:rootPath + "/op_saveSetting_${OP.menu}",data:$('#menu${OP.menu}Form').serializeArray()},function(){
          refreshEmailTemp();
        });
      }
    }
  });
  
  function refreshEmailTemp() {
    var o = layer.load();
    $(".J_mainContent div.J_box:visible").html('<div class="loading">' + $.i18n.prop('stringLoad') + '...<div>');
    $(".J_mainContent div.J_box:visible").loadUrl(rootPath + '/op_list_${OP.menu}','get',{},function(data) {
      layer.close(o);
      $(".J_mainContent div.J_box:visible").find("div.loading").remove();  
    });
  }

</script>
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <form id="menu${OP.menu}Form">
        <table class="table table-bordered">
          <tr>
            <td align="right">
              <span class="stringMailSMTPServer"></span>
            </td>
            <td>
              <input type="text" class="form-control input-sm" validate="required: true" value="${dto.smtpServer }" name="smtpServer">
            </td>
          </tr>
          <tr>
            <td align="right">
              <span class="stringMailAccount"></span>
            </td>
            <td>
              <input type="text" class="form-control input-sm" validate="required: true" value="${dto.mailAccount }" name="mailAccount">
            </td>
          </tr>
          <tr>
            <td align="right">
              <span class="stringMailPassword"></span>
            </td>
            <td>
              <input type="text" class="form-control input-sm" validate="required: true" value="${dto.mailPassword }" name="mailPassword">
            </td>
          </tr>
          <tr>
            <td align="center" colspan="2">
              <button type="button" class="btn btn-sm btn-w-m btn-primary" onclick="crm.admin.menu${OP.menu}.saveSetting()">
                <span class="stringMailSave"></span>
              </button>
              <button type="button" class="btn btn-sm btn-w-m btn-default" onclick="refreshEmailTemp()">
                <span class="stringMailBack"></span>
              </button>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
</div>
