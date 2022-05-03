<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$('.date').datepicker({
  language: '${lg}'
});
loadProperties();
$('.stringDepositName').html($.i18n.prop('stringDepositName'));
$('.stringAccountAuditMailbox').html($.i18n.prop('stringAccountAuditMailbox'));
$('.stringCustomerListStartDate').html($.i18n.prop('stringCustomerListStartDate'));
$('.stringCustomerListCustomerType').html($.i18n.prop('stringCustomerListCustomerType'));
$('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));
$('.stringPhone').html($.i18n.prop('stringPhone'));
$('.stringProfileMTAccount').html($.i18n.prop('stringProfileMTAccount'));
$('.stringCustomerListEndDate').html($.i18n.prop('stringCustomerListEndDate'));
$('.stringCustomerListSummaryStatistics').html($.i18n.prop('stringCustomerListSummaryStatistics'));
$('.stringCustomerListAgentsStatistics').html($.i18n.prop('stringCustomerListAgentsStatistics'));
$('.stringCustomerListNumberOfAgents').html($.i18n.prop('stringCustomerListNumberOfAgents'));
$('.stringCustomerListNumberOfTerminalCustomers').html($.i18n.prop('stringCustomerListNumberOfTerminalCustomers'));

$("#underLingTypeFilter option:first-child").text($.i18n.prop('stringCustomerListAll'));
$("#underLingTypeFilter option:nth-child(2)").text($.i18n.prop('stringCustomerListTerminalCustomer'));
$("#underLingTypeFilter option:nth-child(3)").text($.i18n.prop('stringAccountAuditAgent'));
$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '${MENU.channel}/query',
    height: '100%',
    sortName: 'managerid',
    sortOrder: 'asc',
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
    onRefresh: countUnderlingTotal,
    locale: '${lg}',
    columns: [{
      checkbox: true
    },
    {
      field: 'name',
      title: $.i18n.prop("stringCustomerListCustomerInformation"),
      sortable: true,
      formatter: underlingNameFmt
    },
    {
      field: 'phone',
      title: $.i18n.prop("stringCustomerListContactInformation"),
      sortable: true,
      formatter: underlingPhoneFmt
    },
    {
      field: 'reviewState',
      title: $.i18n.prop("stringMenuStatus"),
      sortable: true,
      formatter: underlingReviewStateFmt
    },
    {
      field: 'login',
      title: $.i18n.prop("stringMailMT5TransactionAccount"),
      sortable: true,
      formatter: underlingLoginFmt
    },
    {
      field: 'rebateLogin',
      title: $.i18n.prop("stringMailMT5RebateAccount"),
      sortable: true,
      formatter: underlingRebateLoginFmt
    },
    {
      field: 'balance',
      title: $.i18n.prop("stringProfileBalance"),
      sortable: true,
      formatter: underlingBalanceFmt
    },
    {
      field: 'belongName',
      title: $.i18n.prop("stringCustomerListSuperior"),
      sortable: true,
      formatter: underlingBelongNameFmt
    },
    {
      field: 'createTimeFmt',
      title: $.i18n.prop("stringAccountAuditRegistrationTime"),
      sortable: true
    }]
  });
});

function underlingNameFmt(value, row, index) {
  if (row.type == 0) {
    return value + '<br/>' + $.i18n.prop("stringCustomerListDirectCustomer");
  } else if (row.type == 1) {
    return value + '<br/>' + row.level + $.i18n.prop("stringCustomerListLevelAgent");
  }
}

function underlingPhoneFmt(value, row, index) {
  var hiddenValue = value.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
  return hiddenValue + '<br/>' + row.account;
}

function underlingReviewStateFmt(value, row, index) {
  if (value == 0) {
    return $.i18n.prop("stringDepositInAudit");
  } else if (value == 1) {
    return $.i18n.prop("stringCustomerListActivated");
  } else if (value == 2) {
    return $.i18n.prop("stringDepositRejected");
  }
}

function underlingLoginFmt(value, row, index) {
  if (row.reviewState == 1) {
    return 'MT：' + value + '<br/>Group：' + row.groupName;
  } else {
    return '-';
  }

}

function underlingRebateLoginFmt(value, row, index) {
  if (row.type == 0) {
    return '-';
  } else if (row.reviewState == 1) {
    return 'MT：' + value + '<br/>Group：' + row.rebateGroupName;
  } else {
    return '-';
  }
}

function underlingBalanceFmt(value, row, index) {
  if (row.type == 0) {
    return $.i18n.prop("stringCustomerListTransaction") + '：&#36;' + value;
  } else if (row.type == 1) {
    return $.i18n.prop("stringCustomerListTransaction") + '：&#36;' + value + '<br/>' + $.i18n.prop("stringCommission") + '：&#36;' + row.rebateBalance;
  }
}

function underlingBelongNameFmt(value, row, index) {
  return value + '<br/>' + row.belongEname;
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
    name: $("#underLingNameFilter").val(),
    phone: $("#underLingPhoneFilter").val(),
    account: $("#underLingAccountFilter").val(),
    login: $("#underLingLoginFilter").val(),
    createTimeStart: $("#underLingCreateTimeStart").val(),
    createTimeEnd: $("#underLingCreateTimeEnd").val(),
    type: $('#underLingTypeFilter').val()
  }
}

countUnderlingTotal();
function countUnderlingTotal() {
  $('#underlingAgentCount').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#underlingUserCount').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#underlingAgentTotal').html('<i class="fa fa-spinner fa-spin"></i>');
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/underling/getUnderlingTotal',
    data: {
      name: $("#underLingNameFilter").val(),
      phone: $("#underLingPhoneFilter").val(),
      account: $("#underLingAccountFilter").val(),
      login: $("#underLingLoginFilter").val(),
      createTimeStart: $("#underLingCreateTimeStart").val(),
      createTimeEnd: $("#underLingCreateTimeEnd").val(),
      type: $('#underLingTypeFilter').val()
    },
    dataType: 'json',
    async: true,
    type: 'get'
  }).done(function(response) {
    if (response.success) {
      var data = response.data;
      $('#underlingAgentCount').html(data.agentCount);
      $('#underlingUserCount').html(data.userCount);
    }
  });

  $.ajax({
    url: '${pageContext.request.contextPath}/pub/underling/getUnderlingAgentTotal',
    data: {
      name: $("#underLingNameFilter").val(),
      phone: $("#underLingPhoneFilter").val(),
      account: $("#underLingAccountFilter").val(),
      login: $("#underLingLoginFilter").val(),
      createTimeStart: $("#underLingCreateTimeStart").val(),
      createTimeEnd: $("#underLingCreateTimeEnd").val(),
      type: $('#underLingTypeFilter').val()
    },
    dataType: 'json',
    async: true,
    type: 'get'
  }).done(function(response) {
    if (response.success) {
      var data = response.data;
      var source = '';
      for (var i = 0; i < data.length; i++) {
        if (i == (data.length - 1)) {
          source += data[i].level + $.i18n.prop("stringCustomerListLevel") + '&nbsp;' + data[i].agentCount + $.i18n.prop("stringCustomerListPeople") + '&nbsp;'
        } else {
          source += data[i].level + $.i18n.prop("stringCustomerListLevel") + '&nbsp;' + data[i].agentCount + $.i18n.prop("stringCustomerListPeople") + '&nbsp;，'
        }
      }
      $('#underlingAgentTotal').html(source);
    }
  });
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

crm.admin.menu${OP.menu}.getUnderlingManager = function() {
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop("stringCustomerListPleaseSelectCheckRecord")
    });
    return;
  }
  if (rows.length > 1) {
    crm.toastrsAlert({
      code: 'warning',
      msg: $.i18n.prop("stringCustomerListSorryOnlyOneRecordCouldCheck")
    });
    return;
  }
  crm.showWindow({
    title: $.i18n.prop('stringCustomerListCheckDetails'),
    href: rootPath + '/op_getUnderlingManager_${OP.menu}?managerId=' + rows[0].id,
    width: '600px',
    height: '90%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.close();
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
      <div class="panel panel-default">
        <div class="panel-body">
          <form id="formSearch" class="form-horizontal">
            <div class="form-group form-group-sm" style="margin-top: 15px">
              <label class="control-label col-sm-2">
                <span class="stringDepositName"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="underLingNameFilter">
              </div>
              <label class="control-label col-sm-2">
                <span class="stringPhone"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="underLingPhoneFilter">
              </div>
            </div>
            <div class="form-group form-group-sm">
              <label class="control-label col-sm-2">
                <span class="stringAccountAuditMailbox"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="underLingAccountFilter">
              </div>
              <label class="control-label col-sm-2">
                <span class="stringProfileMTAccount"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="underLingLoginFilter">
              </div>
            </div>
            <div class="form-group form-group-sm">
              <label class="control-label col-sm-2">
                <span class="stringCustomerListStartDate"></span>
              </label>
              <div class="col-sm-3">
                <div class="input-group date" data-provide="datepicker" style="margin-top: 0;">
                  <input type="text" class="form-control" id="underLingCreateTimeStart">
                  <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                  </div>
                </div>
              </div>
              <label class="control-label col-sm-2">
                <span class="stringCustomerListEndDate"></span>
              </label>
              <div class="col-sm-3">
                <div class="input-group date" data-provide="datepicker" style="margin-top: 0;">
                  <input type="text" class="form-control" id="underLingCreateTimeEnd">
                  <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group form-group-sm">
              <label class="control-label col-sm-2">
                <span class="stringCustomerListCustomerType"></span>
              </label>
              <div class="col-sm-3">
                <select class="form-control" id="underLingTypeFilter">
                  <option value="">全部</option>
                  <option value="0">直客</option>
                  <option value="1">代理</option>
                </select>
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
      <div class="panel panel-default">
        <div class="panel-body">
          <div>
            <span class="stringCustomerListSummaryStatistics"></span>
            :&nbsp;&nbsp;
            <span class="stringCustomerListNumberOfAgents"></span>
            :&nbsp;
            <span id="underlingAgentCount"></span>
            &nbsp;&nbsp;
            <span class="stringCustomerListNumberOfTerminalCustomers"></span>
            :&nbsp;
            <span id="underlingUserCount"></span>
            <br />
            <span class="stringCustomerListAgentsStatistics"></span>
            :&nbsp;&nbsp;
            <span id="underlingAgentTotal"></span>
          </div>
        </div>
      </div>
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true">
        </table>
      </div>
    </div>
  </div>
</div>