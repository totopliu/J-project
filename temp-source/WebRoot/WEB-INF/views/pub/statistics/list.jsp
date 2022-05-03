<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
loadProperties();
$('.stringProfileMTAccount').html($.i18n.prop('stringProfileMTAccount'));
$('.stringHoldingOrdersGroup').html($.i18n.prop('stringHoldingOrdersGroup'));
$('.stringHoldingOrdersTimeFrame').html($.i18n.prop('stringHoldingOrdersTimeFrame'));
$('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));
$('.stringCustomerListSummaryStatistics').html($.i18n.prop('stringCustomerListSummaryStatistics'));
$('.stringHoldingOrdersTransactionLots').html($.i18n.prop('stringHoldingOrdersTransactionLots'));
$('.stringClosingOrdersNumber').html($.i18n.prop('stringClosingOrdersNumber'));
$('.stringCustomerReportInGoldNumber').html($.i18n.prop('stringCustomerReportInGoldNumber'));
$('.stringCustomerReportInGoldAmount').html($.i18n.prop('stringCustomerReportInGoldAmount'));
$('.stringCustomerReportOutGoldNumber').html($.i18n.prop('stringCustomerReportOutGoldNumber'));
$('.stringCustomerReportOutGoldAmount').html($.i18n.prop('stringCustomerReportOutGoldAmount'));
$('.stringStatisticsReportDifferenceOfWithdrawalAndDeposit').html($.i18n.prop('stringStatisticsReportDifferenceOfWithdrawalAndDeposit'));
$('.stringStatisticsReportTotalProfitAndLoss').html($.i18n.prop('stringStatisticsReportTotalProfitAndLoss'));
$('.stringStatisticsReportCommssion').html($.i18n.prop('stringStatisticsReportCommssion'));
summary();
$(function(){
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({ 
    url: rootPath + '${MENU.channel}/query', 
    height: '100%',
    sortName: '',
    sortOrder: 'DESC',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 25, 50, 100, 200],
    search: false,
    sidePagination:'server',
    idField: 'time',
    uniqueId: 'time',
    responseHandler: responseHandler,
    queryParams: queryParams,
    minimumCountColumns: 2,
    clickToSelect: true,
    searchAlign: 'left',
    locale : '${lg}',
    columns: [{
        field: 'dTime',
        title: $.i18n.prop("stringDepositDate"),
        sortable: true
    }, {
        field: 'userCount',
        title: $.i18n.prop("stringStatisticsReportCustomerNumber"),
        sortable: true
    }, {
        field: 'dealVolume',
        title: $.i18n.prop("stringStatisticsReportTransactionLotsAndAmount"),
        sortable: true,
        formatter: statisticsVolumeFmt
    }, {
        field: 'inSum',
        title: $.i18n.prop("stringCustomerReportTotalDepositAndAmount"),
        sortable: true,
        formatter: statisticsInSumFmt
    }, {
        field: 'outSum',
        title: $.i18n.prop("stringCustomerReportTotalWithdrawalAndAmount"),
        sortable: true,
        formatter: statisticsOutSumFmt
    }, {
        field: 'inOutMargin',
        title: $.i18n.prop("stringStatisticsReportDifferenceOfWithdrawalAndDeposit"),
        sortable: true,
        formatter: statisticsDollar
    }, {
        field: 'profit',
        title: $.i18n.prop("stringStatisticsReportTotalProfitAndLoss"),
        sortable: true,
        formatter: function(value, row, index){
          return '&#36;' + value.toFixed(2);
        }
    }, {
        field: 'rebateSum',
        title: $.i18n.prop("stringStatisticsReportCommssion"),
        sortable: true,
        formatter: function(value, row, index){
          if (typeof(value) == 'undefined') {
            return '&#36;0.00';
          }
          return '&#36;' + value.toFixed(2);
        }
    }]
  });
});

function statisticsVolumeFmt(value, row, index) {
  var value = value/10000;
  return value + $.i18n.prop("stringHoldingOrdersLots") + '/' + row.dealAmount + $.i18n.prop("stringStatisticsReportAmount");
}

function statisticsInSumFmt(value, row, index) {
  return value.toFixed(2) + '/' + row.inCount + $.i18n.prop("stringStatisticsReportAmount");
}

function statisticsOutSumFmt(value, row, index) {
  return value + '/' + row.outCount + $.i18n.prop("stringStatisticsReportAmount");
}

function statisticsDollar(value, row, index) {
  return (row.inSum + row.outSum).toFixed(2);
}

// 传递的参数
function queryParams(params) {
  var pageSize = params.limit;
  var sort = params.sort;
  var offset = params.offset;
  var order = params.order;
  var pageNum = offset / pageSize + 1;
  return {
    pageSize:pageSize,
    pageNum:pageNum,
    sort:sort,
    order:order,
    login:$('#statisticsLoginFlt').val(),
    group:$('#statisticsGroup').val(),
    startTime:$('#statisticsStartTime').val(),
    endTime:$('#statisticsEndTime').val()
  };
}

function searchStatistics() {
  summary();
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh', {url:rootPath + '${MENU.channel}/query'});
}

function summary(){
  var login = $('#statisticsLoginFlt').val();
  $.getJSON('${ctx}/pub/statistics/summary',{
    login:$('#statisticsLoginFlt').val(),
    group:$('#statisticsGroup').val(),
    startTime:$('#statisticsStartTime').val(),
    endTime:$('#statisticsEndTime').val()
  },function(response){
    if(response.success){
      var data = response.data;
      $('#summaryDealAmount').html(data.dealAmount);
      $('#summaryDealVolume').html(data.dealVolume/10000);
      $('#summaryInCount').html(data.inCount);
      $('#summaryInSum').html(data.inSum.toFixed(2));
      $('#summaryOutCount').html(data.outCount);
      $('#summaryOutSum').html(data.outSum.toFixed(2));
      $('#summaryInoutSum').html((data.inSum+data.outSum).toFixed(2));
      $('#summaryProfit').html(data.profit.toFixed(2));
      $('#summaryRebateSum').html(data.rebateSum.toFixed(2));
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
              <label class="control-label col-sm-2">
                <span class="stringProfileMTAccount"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="statisticsLoginFlt">
              </div>
              <label class="control-label col-sm-2">
                <span class="stringHoldingOrdersGroup"></span>
              </label>
              <div class="col-sm-3">
                <select class="form-control" id="statisticsGroup">
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
                  <input type="text" class="form-control " id="statisticsStartTime" placeholder="开始">
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
                  <input type="text" class="form-control " id="statisticsEndTime" placeholder="结束">
                  <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                  </div>
                </div>
              </div>
              <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-primary" onclick="searchStatistics()">
                  <span class="stringOperationCheck"></span>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div class="panel panel-primary">
        <div class="panel-heading"><span class="stringCustomerListSummaryStatistics"></span></div>
        <div class="table-responsive">
          <table class="table table-bordered">
            <tr>
              <td><span class="stringHoldingOrdersTransactionLots"></span></td>
              <td><span class="stringClosingOrdersNumber"></span></td>
              <td><span class="stringCustomerReportInGoldNumber"></span></td>
              <td><span class="stringCustomerReportInGoldAmount"></span></td>
              <td><span class="stringCustomerReportOutGoldNumber"></span></td>
              <td><span class="stringCustomerReportOutGoldAmount"></span></td>
              <td><span class="stringStatisticsReportDifferenceOfWithdrawalAndDeposit"></span></td>
              <td><span class="stringStatisticsReportTotalProfitAndLoss"></span></td>
              <td><span class="stringStatisticsReportCommssion"></span></td>
            </tr>
            <tr>
              <td><span id="summaryDealAmount"></span></td>
              <td><span id="summaryDealVolume"></span></td>
              <td><span id="summaryInCount"></span></td>
              <td><span id="summaryInSum"></span></td>
              <td><span id="summaryOutCount"></span></td>
              <td><span id="summaryOutSum"></span></td>
              <td><span id="summaryInoutSum"></span></td>
              <td><span id="summaryProfit"></span></td>
              <td><span id="summaryRebateSum"></span></td>
            </tr>
          </table>
        </div>
      </div>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true"></table>
      </div>
    </div>
  </div>
</div>