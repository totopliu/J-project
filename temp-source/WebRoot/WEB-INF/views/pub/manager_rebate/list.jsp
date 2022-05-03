<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$('.date').datepicker({
  language: '${lg}'
});
var login_rebate = null;
var login_rebate_nodeId = null;
$(function() {
  loadProperties();
  $('.stringDepositMy').html($.i18n.prop('stringDepositMy'));
  $('.stringHoldingOrdersDealNumber').html($.i18n.prop('stringHoldingOrdersDealNumber'));
  $('.stringDepositDate').html($.i18n.prop('stringDepositDate'));
  $('.stringMenuStatus').html($.i18n.prop('stringMenuStatus'));
  $('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));

  $("#over option:first-child").text($.i18n.prop('stringCommissionRecordPleaseChoose'));

  $('#rebateToggleBtn').html($.i18n.prop('stringOpenLeftTree'));

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
    //onRefresh: countRebateTotal,
    columns: [{
      field: 'PositionID',
      title: $.i18n.prop("stringHoldingOrdersDealNumber"),
      sortable: true
    },
    {
      field: 'login',
      title: $.i18n.prop("stringProfileMTAccount"),
      sortable: true
    },
    {
      field: 'ename',
      title: $.i18n.prop("stringCommissionRecordUsersOfAcceptingCommission"),
      sortable: true,
      formatter: rebateNameFmt
    },
    {
      field: 'level',
      title: $.i18n.prop("stringCommissionRecordCommissionLevel"),
      sortable: true,
      formatter: rebateLevelFmt
    },
    {
      field: 'Volume',
      title: $.i18n.prop("stringCommissionRecordTransactionSituation"),
      sortable: true,
      formatter: rebateVolumeFmt
    },
    {
      field: 'rebate',
      title: $.i18n.prop("stringCommissionRecordCommissionAmount"),
      sortable: true
    },
    {
      field: 'createTimeCol',
      title: $.i18n.prop("stringCommissionRecordGenerationTime"),
      sortable: true
    },
    {
      field: 'rebateTimeCol',
      title: $.i18n.prop("stringCommissionRecordDepositTime"),
      sortable: true
    },
    {
      field: 'over',
      title: $.i18n.prop("stringCommissionRecordDepositOrNot"),
      sortable: true,
      formatter: overFormatter
    }]
  });
})

function rebateNameFmt(value, row, index) {
  return $.i18n.prop("stringMailEnglishName") + '：' + value + '<br/>' + $.i18n.prop("stringAccountAuditMailbox") + '：' + row.account;
}

function rebateLevelFmt(value, row, index) {
  return value + $.i18n.prop("stringCustomerListLevel");
}

function rebateVolumeFmt(value, row, index) {
  return value / 10000 + $.i18n.prop("stringHoldingOrdersLots") + row.Symbol;
}

function overFormatter(value, row, index) {
  if (value == 1) {
    return '<i class="fa fa-check text-navy"></i>';
  } else if (value == 0) {
    return '<i class="fa fa-close text-danger"></i>';
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
    order: order,
    login: login_rebate,
    rebateDate: $('#rebateDate').val(),
    ticket: $('#ticket-rebate').val(),
    over: $('#over').val()
  }
}

if (underlingUser.length > 0) {
  $('#trewview-rebate').treeview({
    levels: 1,
    data: underlingUser,
    selectedBackColor: "#1ABC9C",
    onNodeSelected: function(event, node) {
      login_rebate_nodeId = node.nodeId;
      login_rebate = node.login;
      $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
        url: rootPath + '${MENU.channel}/query'
      });
    }
  });
} else {
  $('#rebateTree').hide();
  $('#rebateTreeBtnBox').hide();
  $('#rebateTable').attr('class', 'col-sm-12');
}

function searchManagerRebate() {
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
    url: rootPath + '${MENU.channel}/query'
  });
}

function selectMineRebate() {
  $('#trewview-rebate').treeview('unselectNode', [login_rebate_nodeId, {
    silent: true
  }]);
  login_rebate = null;
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
    url: rootPath + '${MENU.channel}/query'
  });
}

function toggleRebateTree() {
  $('#rebateTree').toggle();
  if ($('#rebateTree').is(':hidden')) {
    $('#rebateTable').attr('class', 'col-sm-12');
    $('#rebateToggleBtn').html($.i18n.prop('stringOpenLeftTree'));
  } else {
    $('#rebateTable').attr('class', 'col-sm-9');
    $('#rebateToggleBtn').html($.i18n.prop('stringCloseLeftTree'));
  }
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
    url: rootPath + '${MENU.channel}/query'
  });
}
</script>
<div class="wrapper wrapper-content animated">
  <div class="row" id="rebateTreeBtnBox">
    <div class="col-sm-3">
      <a class="btn btn-sm btn-primary" id="rebateToggleBtn" onclick="toggleRebateTree()"></a>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-3" id="rebateTree" style="height: 800px; overflow: scroll; display: none;">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <a class="btn btn-sm btn-primary" style="width: 100%; text-align: left;" onclick="selectMineRebate()">
            <span class="stringDepositMy"></span>
          </a>
          <div id="trewview-rebate"></div>
        </div>
      </div>
    </div>
    <div class="col-sm-12" id="rebateTable">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <div class="panel panel-default">
            <div class="panel-body">
              <form id="formSearch" class="form-horizontal">
                <div class="form-group form-group-sm" style="margin-top: 15px">
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersDealNumber"></span>
                  </label>
                  <div class="col-sm-3">
                    <input type="text" class="form-control" id="ticket-rebate">
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringDepositDate"></span>
                  </label>
                  <div class="col-sm-3">
                    <div class="input-group date" data-provide="datepicker" style="margin-top: 0;">
                      <input type="text" class="form-control" id="rebateDate">
                      <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="form-group form-group-sm" style="margin-top: 15px">
                  <label class="control-label col-sm-2">
                    <span class="stringMenuStatus"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="over">
                      <option value="">请选择</option>
                      <option value="0">未入金</option>
                      <option value="1">已入金</option>
                    </select>
                  </div>
                  <div class="col-sm-2">
                    <button type="button" class="btn btn-sm btn-primary" onclick="searchManagerRebate()">
                      <span class="stringOperationCheck"></span>
                    </button>
                  </div>
                </div>
              </form>
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
  </div>
</div>