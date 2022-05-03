<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function() {
  loadProperties();
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '${MENU.channel}/query',
    height: '100%',
    sortName: 'id',
    sortOrder: 'desc',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 25, 50, 100, 200],
    search: false,
    sidePagination: 'server',
    idField: 'id',
    uniqueId: 'id',
    responseHandler: responseHandler,
    queryParams: queryParams,
    minimumCountColumns: 2,
    clickToSelect: true,
    searchAlign: 'left',
    locale: '${lg}',
    columns: [{
      checkbox: true
    },
    {
      field: 'effect',
      title: $.i18n.prop("stringMailPurpose"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'title',
      title: $.i18n.prop("stringMailTitle"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'modifyTimeCol',
      title: $.i18n.prop("stringMenuModificationTime"),
      align: 'center',
      valign: 'middle',
      sortable: true
    },
    {
      field: 'createTimeCol',
      title: $.i18n.prop("stringMailCreationTime"),
      align: 'center',
      valign: 'middle',
      sortable: true
    }]
  });
});

  // 传递的参数
  function queryParams(params) {
    var pageSize = params.limit;
    var sort = params.sort;
    var offset = params.offset;
    var order = params.order;
    var pageNum = offset / pageSize + 1;
    return {
      pageSize : pageSize,
      pageNum : pageNum,
      sort : sort,
      order : order
    }
  }

  //编辑
  crm.admin.menu${OP.menu}.edit = function(){
    var rows =$('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
    if (rows.length == 0) {
      crm.toastrsAlert({
        code:'info',
        msg:$.i18n.prop("stringMailPleaseSelectEditRecord")
      });
      return;
    }
    if (rows.length > 1) {
      crm.toastrsAlert({
        code:'warning',
        msg:$.i18n.prop("stringMailSorryOnlyOneCouldbeEdit")
      });
      return;
    }
    var o = layer.load();
    $(".J_mainContent div.J_box:visible").html('<div class="loading">' + $.i18n.prop("stringLoad") + '...<div>');
    $(".J_mainContent div.J_box:visible").loadUrl(rootPath + '/op_edit_${OP.menu}?id='+rows[0].id,'get',{},function(data) {
      layer.close(o);
      $(".J_mainContent div.J_box:visible").find("div.loading").remove();
    });
  }

  crm.admin.menu${OP.menu}.setting = function(){
    var o = layer.load();
    $(".J_mainContent div.J_box:visible").html('<div class="loading">' + $.i18n.prop("stringLoad") + '...<div>');
    $(".J_mainContent div.J_box:visible").loadUrl(rootPath + '/op_setting_${OP.menu}','get',{},function(data) {
      layer.close(o);
      $(".J_mainContent div.J_box:visible").find("div.loading").remove();  
    });
  }
  
</script>
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false"
          data-mobile-responsive="true">
        </table>
      </div>
    </div>
  </div>
</div>