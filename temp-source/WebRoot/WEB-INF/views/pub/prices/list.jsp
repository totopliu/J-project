<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
loadProperties();
$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '${MENU.channel}/query',
    height: '100%',
    sortName: 'Symbol',
    sortOrder: 'desc',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 25, 50, 100, 200],
    search: false,
    sidePagination: 'server',
    idField: 'Symbol',
    uniqueId: 'Symbol',
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
      field: 'Symbol',
      title: $.i18n.prop("stringCurrencySynCurrency"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'currency',
      title: $.i18n.prop("stringCurrencySynCurrencyGroup"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'fixed',
      title: $.i18n.prop("stringCurrencySynFixedPoint"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'point_relation',
      title: $.i18n.prop("stringCurrencySynPoint"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'gmt_create_fmt',
      title: $.i18n.prop("stringMenuModificationTime"),
      align: 'center',
      valign: 'middle',
      sortable: true
    }]
  });
})

// 传递的参数
function queryParams(params) {
  var pageSize = params.limit;
  var sort = params.sort;
  var offset = params.offset;
  var order = params.order;
  var pageNum = offset / pageSize + 1;
  return {
    pageSize: pageSize,
    pageNum: pageNum,
    sort: sort,
    order: order,
    name: $("#name").val()
  }
}

//查询
var serach = 0;
crm.admin.menu${OP.menu}.serach = function() {
  if ($("#name").val() != '') {
    $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
    serach = 0;
  } else {
    if (serach == 0) {
      $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
      serach++;
    }
  }
}

//分组
crm.admin.menu${OP.menu}.edit = function() {
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop("stringCurrencySynPleaseChooseCurrencyWantToGroup")
    });
    return;
  }
  var ps = [];
  $.each(rows,
  function(i, n) {
    ps.push(n.Symbol);
  });
  crm.showWindow({
    title: $.i18n.prop("stringCurrencySynAllocationGroup"),
    href: rootPath + '/op_edit_${OP.menu}?symbols=' + ps.join(","),
    width: '30%',
    height: '30%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
};
</script>
<!-- 去掉 fadeInRight兼容火狐浏览器（关闭操作功能） -->
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true">
        </table>
      </div>
    </div>
  </div>
</div>