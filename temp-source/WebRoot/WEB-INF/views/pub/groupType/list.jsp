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
      field: 'transactionType',
      title: $.i18n.prop("stringGroupingSynTransactionType"),
      align: 'right',
      valign: 'middle',
      formatter: transactionTypeFmt
    },
    {
      field: 'Group',
      title: $.i18n.prop("stringGroupingSynMTGroup"),
      align: 'left',
      valign: 'middle'
    }]
  });
})

function transactionTypeFmt(value, row, index) {
  if (value == 1) {
    return 'ABOOK';
  } else if (value == 2) {
    return 'BBOOK';
  } else {
    return '-';
  }
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

crm.admin.menu${OP.menu}.edit = function() {
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop("stringGroupingSynPleaseSelectGroupForSet")
    });
    return;
  }
  var ps = [];
  $.each(rows,
  function(i, n) {
    ps.push(n.Group);
  });
  crm.showWindow({
    title: $.i18n.prop("stringGroupingSynSetTransactionType"),
    href: rootPath + '/op_edit_${OP.menu}?groupNames=' + encodeURI(ps.join(",")),
    width: '500px',
    height: '280px',
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
      <div class="row">
        <div class="col-sm-6">
          <div class="table-responsive">
            <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true">
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>