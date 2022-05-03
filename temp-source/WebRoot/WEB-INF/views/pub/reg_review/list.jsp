<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function() {
  loadProperties();
  $('.stringDepositName').html($.i18n.prop('stringDepositName'));
  $('.stringAccountAuditMailbox').html($.i18n.prop('stringAccountAuditMailbox'));
  $('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));

  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '${MENU.channel}/query',
    height: '100%',
    sortName: 'createtime',
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
      title: $.i18n.prop("stringDepositName")
    },
    {
      field: 'account',
      title: $.i18n.prop("stringAccountAuditMailbox")
    },
    {
      field: 'phone',
      title: $.i18n.prop("stringPhone")
    },
    {
      field: 'ctime',
      title: $.i18n.prop("stringAccountAuditRegistrationTime")
    },
    {
      field: 'parentName',
      title: $.i18n.prop("stringAccountAuditSuperiorName")
    },
    {
      field: 'reviewState',
      title: $.i18n.prop("stringMenuStatus"),
      formatter: stateFormatter
    },
    {
      field: 'reviewReason',
      title: $.i18n.prop("stringMailReason")
    }]
  });
})

function stateFormatter(value, row, index) {
  if (value == 0) {
    return $.i18n.prop("stringDepositInAudit");
  } else if (value == 1) {
    return $.i18n.prop("stringAccountAuditOpenedAnAccount");
  } else if (value == 2) {
    return $.i18n.prop("stringDepositRejected");
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
    order: order,
    name: $("#reviewUserNameFilter").val(),
    account: $("#reviewUserAccountFilter").val()
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

// 审核
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
  if (rows[0].reviewState > 0) {
    crm.toastrsAlert({
      code: 'warning',
      msg: $.i18n.prop("stringAccountAuditUserHaveBeenAudited")
    });
    return;
  }
  crm.showWindow({
    title: $.i18n.prop("stringAccountAuditReviewUsers"),
    href: rootPath + '/op_review_${OP.menu}?id=' + rows[0].id,
    width: '800px',
    height: '90%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}
</script>
<div class="wrapper wrapper-content">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <div class="panel panel-default">
        <div class="panel-body">
          <form id="formSearch" class="form-horizontal">
            <div class="form-group form-group-sm" style="margin-top: 15px">
              <label class="control-label col-sm-2" for="">
                <span class="stringDepositName"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control " id="reviewUserNameFilter">
              </div>
              <label class="control-label col-sm-2" for="">
                <span class="stringAccountAuditMailbox"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control " id="reviewUserAccountFilter">
              </div>
              <div class="col-sm-2">
                <button type="button" onclick="crm.admin.menu${OP.menu}.serach();" class="btn btn-sm btn-primary">
                  <span class="stringOperationCheck"></span>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true"></table>
      </div>
    </div>
  </div>
</div>