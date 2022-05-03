<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
      field: 'name',
      title: $.i18n.prop("stringMailUserName"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'dollar',
      title: $.i18n.prop("stringCommissionTransferAmountUSD"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'login',
      title: $.i18n.prop("stringMailMT5TransactionAccount"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'balance',
      title: $.i18n.prop("stringPendingApprovalForWithdrawalMT5TransactionAccountBalance"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'rebateLogin',
      title: $.i18n.prop("stringMailMT5RebateAccount"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'rebateBalance',
      title: $.i18n.prop("stringPendingApprovalForWithdrawalMT5CommissionAccountBalance"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'createTimeFmt',
      title: $.i18n.prop("stringMailApplicationTime"),
      align: 'center',
      valign: 'middle'
    }]
  });
});

function stateFormatter(value, row, index) {
  if (value == 0) {
    return '-';
  } else if (value == 1) {
    return '<i class="fa fa-check text-navy"></i>';
  } else if (value == 2) {
    return '<i class="fa fa-close text-danger"></i>';
  }
  return value;
}

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
    order: order
  }
}

crm.admin.menu${OP.menu}.review = function() {
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop("stringAccountAuditPleaseSelectUserToAudit")
    });
    return;
  }
  if (rows.length > 1) {
    crm.toastrsAlert({
      code: 'warning',
      msg: $.i18n.prop("stringAccountAuditOnlyOneCanbeSelectToAudit")
    });
    return;
  }
  crm.showWindow({
    title: $.i18n.prop("stringPendingApprovalForCommissionTransferAuditTransfer"),
    href: rootPath + '/op_review_${OP.menu}?id=' + rows[0].id,
    width: '40%',
    height: '70%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}
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