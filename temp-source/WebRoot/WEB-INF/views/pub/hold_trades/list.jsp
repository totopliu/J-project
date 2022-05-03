<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

$('.date').datepicker({
  language: '${lg}'
});

var login_hold = null;
var login_hold_nodeId = null;
var managerId_hold = null;
$(function(){
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
  $('.stringHoldingOrdersTo').html($.i18n.prop('stringHoldingOrdersTo'));
  $('.stringHoldingOrdersTimeFrame').html($.i18n.prop('stringHoldingOrdersTimeFrame'));
  $('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));
  
  $('#holdProfitFltStart').attr('placeholder',$.i18n.prop('stringHoldingOrdersTheLowestPrice'));
  $('#holdProfitFltEnd').attr('placeholder',$.i18n.prop('stringHoldingOrdersTheHighestPrice'));
  $('#holdCreateTimeStart').attr('placeholder',$.i18n.prop('stringHoldingOrdersBegin'));
  $('#holdCreateTimeEnd').attr('placeholder',$.i18n.prop('stringHoldingOrdersEnd'));
  
  $("#holdPriceGroupFlt option:first-child").text($.i18n.prop('stringCustomerListAll'));
  $("#holdActionFlt option:first-child").text($.i18n.prop('stringCustomerListAll'));
  $("#holdGroupFlt option:first-child").text($.i18n.prop('stringCustomerListAll'));
  $("#holdLimitFlt option:first-child").text($.i18n.prop('stringHoldingOrdersOneself'));
  $("#holdLimitFlt option:nth-child(2)").text($.i18n.prop('stringHoldingOrdersDirectSubordinates'));
  $("#holdLimitFlt option:nth-child(3)").text($.i18n.prop('stringHoldingOrdersAllSubordinates'));
  $("#holdLimitFlt option:nth-child(4)").text($.i18n.prop('stringHoldingOrdersSubordinatesPlusOneself'));
  
  $('#holdToggleBtn').html($.i18n.prop('stringOpenLeftTree'));

  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({ 
    url: rootPath + '${MENU.channel}/query', 
    height: '100%',
    sortName: 'Position',
    sortOrder: 'desc',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 25, 50, 100, 200],
    search: false,
    sidePagination:'server',
    idField: 'Position',
    uniqueId: 'Position',
    responseHandler: responseHandler,
    queryParams: queryParams,
    minimumCountColumns: 2,
    clickToSelect: false,
    searchAlign: 'left',
    locale : '${lg}',
    columns: [{
      field : 'Position',
      title : $.i18n.prop("stringHoldingOrdersDealAndAccountNo"),
      align : 'left',
      valign : 'middle',
      sortable : true,
      formatter : holdTradePositionFmt
    },{
      field : 'ename',
      title : $.i18n.prop("stringMailEnglishName"),
      align : 'left',
      valign : 'middle',
      sortable : true
    }, {
      field : 'Action',
      title : $.i18n.prop("stringHoldingOrdersTransactionMode"),
      align : 'left',
      valign : 'middle',
      sortable : true,
      formatter : formatAction
    }, {
      field : 'Volume',
      title : $.i18n.prop("stringHoldingOrdersTransactionLots"),
      align : 'left',
      valign : 'middle',
      sortable : true,
      formatter : formatVolume
    }, {
      field : 'PriceOpen',
      title : $.i18n.prop("stringHoldingOrdersPrice"),
      align : 'left',
      valign : 'middle',
      sortable : true,
      formatter : holdTradePriceFmt
    }, {
      field : 'PriceSL',
      title : $.i18n.prop("stringHoldingOrdersStopLossAndTakeProfit"),
      align : 'left',
      valign : 'middle',
      sortable : true,
      formatter : holdTradePriceSLFmt
    }, {
      field : 'Storage',
      title : $.i18n.prop("stringHoldingOrdersInterestAndCommission"),
      align : 'left',
      valign : 'middle',
      sortable : true,
      formatter : holdTradeStorageFmt
    }, {
      field : 'TimeCreateFmt',
      title : $.i18n.prop("stringHoldingOrdersTransactionTime"),
      align : 'left',
      valign : 'middle',
      sortable : true
    }, {
      field : 'Profit',
      title : $.i18n.prop("stringHoldingOrdersProfit"),
      align : 'left',
      valign : 'middle',
      sortable : true
    }, {
      field : 'Comment',
      title : $.i18n.prop("stringHoldingOrdersRemarks"),
      align : 'left',
      valign : 'middle'
    }]
  });
});
    
   
function holdTradePositionFmt(value, row, index) {
  return $.i18n.prop("stringHoldingOrdersDealNumber") + '：' + value + '<br/>' + $.i18n.prop("stringAccount") + '：' + row.Login;
}
  
function formatAction(value, row, index) {
  if (value == 0) {
    return 'BUY' + '<br/>' + row.Symbol;
  } else if (value == 1) {
    return 'SELL' + '<br/>' + row.Symbol;
  }
}

function formatVolume(value, row, index) {
  return value/10000 + $.i18n.prop("stringHoldingOrdersLots");
}

function holdTradePriceFmt(value, row, index) {
  return $.i18n.prop("stringHoldingOrdersOpenTime") + '：' + value + '<br/>' + $.i18n.prop("stringHoldingOrdersCurrent") + '：' +row.PriceCurrent;
}

function holdTradePriceSLFmt(value, row, index) {
  return $.i18n.prop("stringHoldingOrdersStopLoss") + '：' + value + '<br/>' + $.i18n.prop("stringHoldingOrdersTakeProfit") + '：' + row.PriceTP;
}

function holdTradeStorageFmt(value, row, index) {
  return $.i18n.prop("stringHoldingOrdersInterests") + '：' + value + '<br/>' + $.i18n.prop("stringHoldingOrdersCommission") + '：' + row.Commission
}

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
    order : order,
    login : login_hold,
    account : $('#holdAccountFlt').val(),
    getLimit : $('#holdLimitFlt').val(),
    loginFlt : $('#holdLoginFlt').val(),
    positon : $('#holdPositionFlt').val(),
    profitStart : $('#holdProfitFltStart').val(),
    profitEnd : $('#holdProfitFltEnd').val(),
    priceGroup : $('#holdPriceGroupFlt').val(),
    action : $('#holdActionFlt').val(),
    group : $('#holdGroupFlt').val(),
    createTimeStart : $('#holdCreateTimeStart').val(),
    createTimeEnd : $('#holdCreateTimeEnd').val(),
    treeManagerId: managerId_hold
  }
}

if (underlingUser.length > 0) {
  $('#trewview-hold-trades').treeview({
    levels : 1,
    data : underlingUser,
    selectedBackColor : "#1ABC9C",
    onNodeSelected : function(event, node) {
      login_hold_nodeId = node.nodeId;
      managerId_hold = node.val;
      login_hold = node.login;
      $('#holdLoginFlt').val('');
      $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {url : rootPath + '${MENU.channel}/query'});
    }
  });
} else {
  $('#holdTree').hide();
  $('#holdTreeBtnBox').hide();
  $('#holdTable').attr('class', 'col-sm-12');
}

function searchHold() {
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {url : rootPath + '${MENU.channel}/query'});
}

function selectMineHold() {
  login_hold = null;
  managerId_hold = null;
  $('#trewview-hold-trades').treeview('unselectNode', [login_hold_nodeId,{silent : true}]);
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {url : rootPath + '${MENU.channel}/query'});
}

function toggleHoldTree() {
  $('#holdTree').toggle();
  if ($('#holdTree').is(':hidden')) {
    $('#holdTable').attr('class', 'col-sm-12');
    $('#holdToggleBtn').html($.i18n.prop('stringOpenLeftTree'));
  } else {
    $('#holdTable').attr('class', 'col-sm-9');
    $('#holdToggleBtn').html($.i18n.prop('stringCloseLeftTree'));
  }
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {url : rootPath + '${MENU.channel}/query'});
}
</script>
<div class="wrapper wrapper-content">
  <div class="row" id="holdTreeBtnBox">
    <div class="col-sm-3">
      <a class="btn btn-sm btn-primary" id="holdToggleBtn" onclick="toggleHoldTree()"></a>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-3" id="holdTree" style="height: 800px; overflow: scroll; display: none;">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <a class="btn btn-sm btn-primary" style="width: 100%; text-align: left;" onclick="selectMineHold()">
            <span class="stringDepositMy"></span>
          </a>
          <div id="trewview-hold-trades"></div>
        </div>
      </div>
    </div>
    <div class="col-sm-12" id="holdTable">
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
                    <input type="text" class="form-control" id="holdAccountFlt">
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersDataArea"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="holdLimitFlt">
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
                    <input type="text" class="form-control" id="holdLoginFlt">
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersDealNumber"></span>
                  </label>
                  <div class="col-sm-3">
                    <input type="text" class="form-control" id="holdPositionFlt">
                  </div>
                </div>
                <div class="form-group form-group-sm">
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersOrderProfitAndLoss"></span>
                  </label>
                  <div class="col-sm-3">
                    <div class="input-group">
                      <input type="text" class="form-control" id="holdProfitFltStart" placeholder="最低价格">
                      <div class="input-group-addon">
                        <span>
                          <span class="stringHoldingOrdersTo"></span>
                        </span>
                      </div>
                      <input type="text" class="form-control" id="holdProfitFltEnd" placeholder="最高价格">
                    </div>
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersTransactionType"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="holdPriceGroupFlt">
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
                    <select class="form-control" id="holdActionFlt">
                      <option value="">全部</option>
                      <option value="0">BUY</option>
                      <option value="1">SELL</option>
                    </select>
                  </div>
                  <label class="control-label col-sm-2">
                    <span class="stringHoldingOrdersGroup"></span>
                  </label>
                  <div class="col-sm-3">
                    <select class="form-control" id="holdGroupFlt">
                      <option value="">全部</option>
                      <crm:list var="n" namespace="com.crm.platform.mapper.pub.GroupTypeMapper.listGroup">
                        <option value="${n.Group}" <c:if test="${dto.belongGroup eq  n.Group}">selected</c:if>>${n.Group}</option>
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
                      <input type="text" class="form-control " id="holdCreateTimeStart" placeholder="开始">
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
                      <input type="text" class="form-control " id="holdCreateTimeEnd" placeholder="结束">
                      <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-2">
                    <button type="button" class="btn btn-sm btn-primary" onclick="searchHold()">
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