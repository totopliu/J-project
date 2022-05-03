<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function(){
  loadProperties();
  $('.stringCustomerReportCustomerLevel').html($.i18n.prop('stringCustomerReportCustomerLevel'));
  $('.stringCustomerReportCustomerSituation').html($.i18n.prop('stringCustomerReportCustomerSituation'));
  $('.stringClosingOrdersNumber').html($.i18n.prop('stringClosingOrdersNumber'));
  $('.stringCustomerReportCustomer').html($.i18n.prop('stringCustomerReportCustomer'));
  $('.stringHoldingOrdersTransactionLots').html($.i18n.prop('stringHoldingOrdersTransactionLots'));
  $('.stringCustomerReportInGoldAmount').html($.i18n.prop('stringCustomerReportInGoldAmount'));
  $('.stringCustomerReportInGoldNumber').html($.i18n.prop('stringCustomerReportInGoldNumber'));
  $('.stringCustomerReportOutGoldNumber').html($.i18n.prop('stringCustomerReportOutGoldNumber'));
  $('.stringCommission').html($.i18n.prop('stringCommission'));
  $('.stringCustomerReportProfitAndLoss').html($.i18n.prop('stringCustomerReportProfitAndLoss'));
  $('.stringCustomerReportOutGoldAmount').html($.i18n.prop('stringCustomerReportOutGoldAmount'));
});

getCustomersTable();
function getCustomersTable() {
  $.ajax({
    type: 'get',
    url: rootPath + '/pub/customersReport/table',
    data: {},
    dataType: 'json',
    success: function(response) {
      if (response.success) {
        var data = response.data;
        var source = '';
        for (var i = 0; i < data.length; i++) {
          source += '<tr><td>' + data[i].level + '</td><td>' + data[i].amount + '</td><td>' + data[i].dealCount + '</td><td>' + data[i].volume / 10000 + '</td><td>' + data[i].inCount + '</td><td><span style="color:green;">&#36;' + data[i].inSum.toFixed(2) + '</span></td><td>' + data[i].outCount + '</td><td><span style="color:red;">&#36;' + data[i].outSum.toFixed(2) + '</span><td>' + (data[i].profit >= 0 ? '<span style="color:green;">&#36;' + data[i].profit.toFixed(2) + '</span>' : '<span style="color:green;">&#36;' + data[i].profit.toFixed(2) + '</span>') + '</td><td>&#36;' + data[i].rebate.toFixed(2) + '</td></tr>';
        }
        $('#customersTable').html(source);
      }
    }
  });
}
</script>
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="panel panel-primary">
        <div class="panel-heading"><span class="stringCustomerReportCustomer"></span></div>
        <div class="table-responsive">
          <table class="table">
            <thead>
              <tr>
                <th>
                  <span class="stringCustomerReportCustomerLevel"></span>
                </th>
                <th>
                  <span class="stringCustomerReportCustomerSituation"></span>
                </th>
                <th>
                  <span class="stringClosingOrdersNumber"></span>
                </th>
                <th>
                  <span class="stringHoldingOrdersTransactionLots"></span>
                </th>
                <th>
                  <span class="stringCustomerReportInGoldNumber"></span>
                </th>
                <th>
                  <span class="stringCustomerReportInGoldAmount"></span>
                </th>
                <th>
                  <span class="stringCustomerReportOutGoldNumber"></span>
                </th>
                <th>
                  <span class="stringCustomerReportOutGoldAmount"></span>
                </th>
                <th>
                  <span class="stringCustomerReportProfitAndLoss"></span>
                </th>
                <th>
                  <span class="stringCommission"></span>
                </th>
              </tr>
            </thead>
            <tbody id="customersTable"></tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
