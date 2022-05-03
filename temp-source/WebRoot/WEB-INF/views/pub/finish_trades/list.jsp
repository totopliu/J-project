<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

$('.date').datepicker({
  language: '${lg}'
});

loadProperties();
$('.stringDepositMy').html($.i18n.prop('stringDepositMy'));
$('.stringHoldingOrdersUserMailbox').html($.i18n.prop('stringHoldingOrdersUserMailbox'));
$('.stringHoldingOrdersDataArea').html($.i18n.prop('stringHoldingOrdersDataArea'));
$('.stringProfileMTAccount').html($.i18n.prop('stringProfileMTAccount'));
$('.stringHoldingOrdersDealNumber').html($.i18n.prop('stringHoldingOrdersDealNumber'));
$('.stringHoldingOrdersOrderProfitAndLoss').html($.i18n.prop('stringHoldingOrdersOrderProfitAndLoss'));
$('.stringHoldingOrdersTransactionType').html($.i18n.prop('stringHoldingOrdersTransactionType'));
$('.stringHoldingOrdersTradingInstruction').html($.i18n.prop('stringHoldingOrdersTradingInstruction'));
$('.stringHoldingOrdersGroup').html($.i18n.prop('stringHoldingOrdersGroup'));
$('.stringClosingOrdersTo').html($.i18n.prop('stringClosingOrdersTo'));
$('.stringHoldingOrdersTimeFrame').html($.i18n.prop('stringHoldingOrdersTimeFrame'));
$('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));
$('.stringClosingOrdersTransactionSummary').html($.i18n.prop('stringClosingOrdersTransactionSummary'));
$('.stringClosingOrdersNumber').html($.i18n.prop('stringClosingOrdersNumber'));
$('.stringHoldingOrdersCommission').html($.i18n.prop('stringHoldingOrdersCommission'));
$('.stringHoldingOrdersInterests').html($.i18n.prop('stringHoldingOrdersInterests'));
$('.stringClosingOrdersProfitAndLossOfTransactionOrders').html($.i18n.prop('stringClosingOrdersProfitAndLossOfTransactionOrders'));
$('.stringClosingOrdersTotalProfitAndLoss').html($.i18n.prop('stringClosingOrdersTotalProfitAndLoss'));
$('.stringClosingOrdersTotalWithdrawalAndDeposit').html($.i18n.prop('stringClosingOrdersTotalWithdrawalAndDeposit'));
$('.stringClosingOrdersOutInGoldNumber').html($.i18n.prop('stringClosingOrdersOutInGoldNumber'));
$('.stringClosingOrdersDepositAmount').html($.i18n.prop('stringClosingOrdersDepositAmount'));
$('.stringClosingOrdersWithdrawalAmount').html($.i18n.prop('stringClosingOrdersWithdrawalAmount'));
$('.stringClosingOrdersBalanceOfWithdrawalAndDeposit').html($.i18n.prop('stringClosingOrdersBalanceOfWithdrawalAndDeposit'));

$('#finishProfitFltStart').attr('placeholder',$.i18n.prop('stringHoldingOrdersTheLowestPrice'));
$('#finishProfitFltEnd').attr('placeholder',$.i18n.prop('stringHoldingOrdersTheHighestPrice'));
$('#finishCreateTimeStart').attr('placeholder',$.i18n.prop('stringHoldingOrdersBegin'));
$('#finishCreateTimeEnd').attr('placeholder',$.i18n.prop('stringHoldingOrdersEnd'));

$("#finishPriceGroupFlt option:first-child").text($.i18n.prop('stringCustomerListAll'));
$("#finishActionFlt option:first-child").text($.i18n.prop('stringCustomerListAll'));
$("#finishGroupFlt option:first-child").text($.i18n.prop('stringCustomerListAll'));
$("#finishLimitFlt option:first-child").text($.i18n.prop('stringHoldingOrdersOneself'));
$("#finishLimitFlt option:nth-child(2)").text($.i18n.prop('stringHoldingOrdersDirectSubordinates'));
$("#finishLimitFlt option:nth-child(3)").text($.i18n.prop('stringHoldingOrdersAllSubordinates'));
$("#finishLimitFlt option:nth-child(4)").text($.i18n.prop('stringHoldingOrdersSubordinatesPlusOneself'));

$('#finishToggleBtn').html($.i18n.prop('stringOpenLeftTree'));
$('.stringHoldingOrdersTransactionLots').html($.i18n.prop('stringHoldingOrdersTransactionLots'));

var login_finish = null;
var login_finsih_nodeId = null;
var managerId_finish = null;
$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '${MENU.channel}/query',
    height: '100%',
    sortName: 'PositionID',
    sortOrder: 'desc',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 25, 50, 100, 200],
    search: false,
    sidePagination: 'server',
    idField: 'Deal',
    uniqueId: 'Deal',
    responseHandler: responseHandler,
    queryParams: queryParams,
    minimumCountColumns: 2,
    clickToSelect: false,
    searchAlign: 'left',
    locale: '${lg}',
    onRefresh: countFinishTotal,
    columns: [{
      field: 'PositionID',
      title: $.i18n.prop("stringHoldingOrdersDealAndAccountNo"),
      sortable: true,
      formatter: finishPositionIDFmt
    },
    {
      field: 'ename',
      title: $.i18n.prop("stringClosingOrdersEnglishNameAndMTName"),
      sortable: true,
      formatter: finishEnameFmt
    },
    {
      field: 'Entry',
      title: $.i18n.prop("stringClosingOrdersEntry"),
      sortable: true,
      formatter: function(value, row, index) {
        if (value == 0) {
          return 'IN';
        } else if (value == 1) {
          return 'OUT';
        }
      }
    },
    {
      field: 'Action',
      title: $.i18n.prop("stringClosingOrdersTransactionInstructionAndVarieties"),
      sortable: true,
      formatter: formatAction
    },
    {
      field: 'Volume',
      title: $.i18n.prop("stringHoldingOrdersTransactionLots"),
      sortable: true,
      formatter: finishVolumeFmt
    },
    {
      field: 'Price',
      title: $.i18n.prop("stringHoldingOrdersPrice"),
      sortable: true
    },
    {
      field: 'Storage',
      title: $.i18n.prop("stringHoldingOrdersInterestAndCommission"),
      sortable: true,
      formatter: finishStorageFmt
    },
    {
      field: 'TimeFmt',
      title: $.i18n.prop("stringDepositTime"),
      sortable: true
    },
    {
      field: 'Profit',
      title: $.i18n.prop("stringHoldingOrdersProfit"),
      sortable: true,
      formatter: function(value, row, index) {
        return value.toFixed(2);
      }
    }]
  });
});
  
function finishPositionIDFmt(value, row, index) {
  return $.i18n.prop("stringHoldingOrdersDealNumber") + '：' + value + '<br/>' + $.i18n.prop("stringAccount") + '：' + row.Login
}

function finishEnameFmt(value, row, index) {
  return $.i18n.prop("stringMailEnglishName") + '：' + value + '<br/>' + $.i18n.prop("stringClosingOrdersMTName") + '：' + value;
}

function finishVolumeFmt(value, row, index) {
  return value / 10000 + $.i18n.prop("stringHoldingOrdersLots");
}

function formatAction(value, row, index) {
  if (value == 0) {
    return 'BUY' + '<br/>' + row.Symbol;
  } else if (value == 1) {
    return 'SELL' + '<br/>' + row.Symbol;
  }
}

function finishStorageFmt(value, row, index) {
  return $.i18n.prop("stringHoldingOrdersInterests") + '：' + value.toFixed(2) + '<br/>' + $.i18n.prop("stringHoldingOrdersCommission") + '：' + row.Commission.toFixed(2);
}
//传递的参数
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
    login: login_finish,
    account: $('#finishAccountFlt').val(),
    getLimit: $('#finishLimitFlt').val(),
    loginFlt: $('#finishLoginFlt').val(),
    positon: $('#finishPositionFlt').val(),
    profitStart: $('#finishProfitFltStart').val(),
    profitEnd: $('#finishProfitFltEnd').val(),
    priceGroup: $('#finishPriceGroupFlt').val(),
    action: $('#finishActionFlt').val(),
    group: $('#finishGroupFlt').val(),
    createTimeStart: $('#finishCreateTimeStart').val(),
    createTimeEnd: $('#finishCreateTimeEnd').val(),
    treeManagerId: managerId_finish
  }
}
if (underlingUser.length > 0) {
  $('#trewview-finish-trades').treeview({
    levels: 1,
    data: underlingUser,
    selectedBackColor: "#1ABC9C",
    onNodeSelected: function(event, node) {
      login_finish = node.login;
      managerId_finish = node.val;
      login_finsih_nodeId = node.nodeId;
      $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
        url: rootPath + '${MENU.channel}/query'
      });
    }
  });
} else {
  $('#finishTree').hide();
  $('#finishTreeBtnBox').hide();
  $('#finishTable').attr('class', 'col-sm-12');
}

function searchFinish() {
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {url : rootPath + '${MENU.channel}/query'});
}

countFinishTotal();
function countFinishTotal() {
  $('#tradeVolumSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#tradeCount').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#commissionSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#storageSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#tradeProfitSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#tradeAllProfitSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#inCount').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#inGoldSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#outGoldSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#inOutGold').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#balanceSum').html('<i class="fa fa-spinner fa-spin"></i>');
  $('#equitySum').html('<i class="fa fa-spinner fa-spin"></i>');
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/finishTrades/countFinishProfitTotal',
    data: {
      login: login_finish,
      account: $('#finishAccountFlt').val(),
      getLimit: $('#finishLimitFlt').val(),
      loginFlt: $('#finishLoginFlt').val(),
      positon: $('#finishPositionFlt').val(),
      profitStart: $('#finishProfitFltStart').val(),
      profitEnd: $('#finishProfitFltEnd').val(),
      priceGroup: $('#finishPriceGroupFlt').val(),
      action: $('#finishActionFlt').val(),
      group: $('#finishGroupFlt').val(),
      createTimeStart: $('#finishCreateTimeStart').val(),
      createTimeEnd: $('#finishCreateTimeEnd').val(),
      treeManagerId: managerId_finish
    },
    dataType: 'json',
    async: true,
    type: 'get'
  }).done(function(response) {
    if (response.success) {
      var data = response.data;
      $('#tradeVolumSum').html(data.tradeVolumSum / 10000);
      $('#tradeCount').html(data.tradeCount);
      $('#commissionSum').html(data.commissionSum > 0 ? '<span style="color:green;">&#36;' + data.commissionSum + '</span>': '<span style="color:red;">&#36;' + data.commissionSum + '</span>');
      $('#storageSum').html(data.storageSum > 0 ? '<span style="color:green;">&#36;' + data.storageSum + '</span>': '<span style="color:red;">&#36;' + data.storageSum + '</span>');
      $('#tradeProfitSum').html(data.tradeProfitSum > 0 ? '<span style="color:green;">&#36;' + data.tradeProfitSum + '</span>': '<span style="color:red;">&#36;' + data.tradeProfitSum + '</span>');
      $('#tradeAllProfitSum').html(data.tradeAllProfitSum > 0 ? '<span style="color:green;">&#36;' + data.tradeProfitSum + '</span>': '<span style="color:red;">&#36;' + data.tradeProfitSum + '</span>');
      $('#inCount').html(data.inCount);
      $('#inGoldSum').html('<span style="color:green;">&#36;' + data.inGoldSum + '</span>');
      $('#outGoldSum').html('<span style="color:red;">&#36;' + data.outGoldSum + '</span>');
      $('#inOutGold').html(data.inOutGold > 0 ? '<span style="color:green;">&#36;' + data.inOutGold + '</span>': '<span style="color:red;">&#36;' + data.inOutGold + '</span>');
      $('#balanceSum').html(data.balanceSum);
      $('#equitySum').html(data.equitySum);
    }
  });
}
function selectMineFinish() {
  login_finish = null;
  managerId_finish = null;
  $('#trewview-finish-trades').treeview('unselectNode', [login_finsih_nodeId, {
    silent: true
  }]);
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
    url: rootPath + '${MENU.channel}/query'
  });
}

function toggleFinishTree() {
  $('#finishTree').toggle();
  if ($('#finishTree').is(':hidden')) {
    $('#finishTable').attr('class', 'col-sm-12');
    $('#finishToggleBtn').html($.i18n.prop('stringOpenLeftTree'));
  } else {
    $('#finishTable').attr('class', 'col-sm-9');
    $('#finishToggleBtn').html($.i18n.prop('stringCloseLeftTree'));
  }
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {
    url: rootPath + '${MENU.channel}/query'
  });
}
</script>
<div class="wrapper wrapper-content animated">
  <div class="row" id="finishTreeBtnBox">
    <div class="col-sm-3">
      <a class="btn btn-sm btn-primary" id="finishToggleBtn" onclick="toggleFinishTree()"></a>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-3" id="finishTree" style="height: 800px; overflow: scroll; display: none;">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <a class="btn btn-sm btn-primary" style="width: 100%; text-align: left;" onclick="selectMineFinish()">
            <span class="stringDepositMy"></span>
          </a>
          <div id="trewview-finish-trades"></div>
        </div>
      </div>
    </div>
    <div class="col-sm-12" id="finishTable">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <div class="panel panel-default">
            <div class="panel-body">
              <form id="formSearch" class="form-horizontal">
                <div class="form-group form-group-sm" style="margin-top: 15px">
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersUserMailbox"></span>
                  </label>
                  <div class="col-sm-3">
                    <input type="text" class="form-control" id="finishAccountFlt">
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersDataArea"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="finishLimitFlt">
                      <option value="0">本人</option>
                      <option value="1">直接下级</option>
                      <option value="2">所有下级</option>
                      <option value="3">下级+本人</option>
                    </select>
                  </div>
                </div>
                <div class="form-group form-group-sm">
                  <label class="control-label col-sm-2">
                    <span class="stringProfileMTAccount"></span>
                  </label>
                  <div class="col-sm-3">
                    <input type="text" class="form-control" id="finishLoginFlt">
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersDealNumber"></span>
                  </label>
                  <div class="col-sm-3">
                    <input type="text" class="form-control" id="finishPositionFlt">
                  </div>
                </div>
                <div class="form-group form-group-sm">
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersOrderProfitAndLoss"></span>
                  </label>
                  <div class="col-sm-3">
                    <div class="input-group">
                      <input type="text" class="form-control" id="finishProfitFltStart" placeholder="最低价格">
                      <div class="input-group-addon">
                        <span>
                          <span class="stringClosingOrdersTo"></span>
                        </span>
                      </div>
                      <input type="text" class="form-control" id="finishProfitFltEnd" placeholder="最高价格">
                    </div>
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersTransactionType"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="finishPriceGroupFlt">
                      <option value="">全部</option>
                      <crm:list var="m" namespace="com.crm.platform.mapper.pub.CurrencyMapper.getCurrencyListForPrice">
                        <option value="${m.id}">${m.name}</option>
                      </crm:list>
                    </select>
                  </div>
                </div>
                <div class="form-group form-group-sm">
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersTradingInstruction"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="finishActionFlt">
                      <option value="">全部</option>
                      <option value="0">BUY</option>
                      <option value="1">SELL</option>
                    </select>
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersGroup"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="finishGroupFlt">
                      <option value="">全部</option>
                      <crm:list var="n" namespace="com.crm.platform.mapper.pub.GroupTypeMapper.listGroup">
                        <option value="${n.Group}">${n.Group}</option>
                      </crm:list>
                    </select>
                  </div>
                </div>
                <div class="form-group form-group-sm">
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersTimeFrame"></span>
                  </label>
                  <div class="col-sm-3">
                    <div class="input-group date" data-provide="datepicker" style="margin-top: 0;">
                      <input type="text" class="form-control " id="finishCreateTimeStart" placeholder="开始">
                      <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                      </div>
                    </div>
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersTimeFrame"></span>
                  </label>
                  <div class="col-sm-3">
                    <div class="input-group date" data-provide="datepicker" style="margin-top: 0;">
                      <input type="text" class="form-control " id="finishCreateTimeEnd" placeholder="结束">
                      <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-2" style="text-align: left;">
                    <button type="button" class="btn btn-sm btn-primary" onclick="searchFinish()">
                      <span class="stringOperationCheck"></span>
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div class="well">
              <span class="stringClosingOrdersTransactionSummary"></span>
              &nbsp;&nbsp;
              <span class="stringHoldingOrdersTransactionLots"></span>
              <span id="tradeVolumSum"></span>
              <span class="stringClosingOrdersNumber"></span>
              <span id="tradeCount"></span>
              <span class="stringHoldingOrdersCommission"></span>
              <span id="commissionSum"></span>
              <span class="stringHoldingOrdersInterests"></span>
              <span id="storageSum"></span>
              <span class="stringClosingOrdersProfitAndLossOfTransactionOrders"></span>
              <span id="tradeProfitSum"></span>
              <span class="stringClosingOrdersTotalProfitAndLoss"></span>
              <span id="tradeAllProfitSum"></span>
              <br><br>
              <span class="stringClosingOrdersTotalWithdrawalAndDeposit"></span>
              &nbsp;&nbsp;
              <span class="stringClosingOrdersOutInGoldNumber"></span>
              <span id="inCount"></span>
              <span class="stringClosingOrdersDepositAmount"></span>
              <span id="inGoldSum"></span>
              <span class="stringClosingOrdersWithdrawalAmount"></span>
              <span id="outGoldSum"></span>
              <span class="stringClosingOrdersBalanceOfWithdrawalAndDeposit"></span>
              <span id="inOutGold"></span>
              <span>余额</span>
              <span id="balanceSum"></span>
              <span>净值</span>
              <span id="equitySum"></span>
          </div>
          <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
          <div class="table-responsive">
            <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true"></table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>