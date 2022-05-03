<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <div class="row">
        <div class="col-sm-3">
          <select class="form-control m-b" id="rebateGroup" onchange="selectGroup()">
            <option id="defaultOption" value=""><span class="stringCommissionSettingPleaseChooseGroup"></span></option>
            <crm:list var="n" namespace="com.crm.platform.mapper.pub.GroupTypeMapper.listGroup">
              <option value="${n.Group}">${n.Group}</option>
            </crm:list>
          </select>
        </div>
      </div>
    </div>
  </div>
  <div class="ibox float-e-margins" id="setRebateBox" style="display: none;">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <a class="btn btn-sm btn-danger" href="javascript:;" onclick="cancelRebateConfig()">
        <span class="stringCancel"></span>
      </a>
      <a class="btn btn-sm btn-primary" href="javascript:;" onclick="plusNextLevel()">
        <span class="stringCommissionSettingAddLevel"></span>
      </a>
      <div id="rebateBox" class="row"></div>
    </div>
  </div>
</div>
<script type="text/javascript">
loadProperties();
$('#defaultOption').text($.i18n.prop('stringCommissionSettingPleaseChooseGroup'));
$('.stringCancel').text($.i18n.prop('stringCancel'));
$('.stringCommissionSettingAddLevel').text($.i18n.prop('stringCommissionSettingAddLevel'));

$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
});

function selectGroup() {
  var groupName = $('#rebateGroup').val();
  if (groupName != '') {
    getGroupRebateConfig(groupName);
    $('#setRebateBox').show();
  } else {
    $('#setRebateBox').hide();
    return;
  }
}

var currencyData;
var source;
getCurrency();
function getCurrency() {
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/currency/listCurrency',
    data: {},
    dataType: 'json',
    async: true,
    type: 'get'
  }).done(function(response) {
    if (response.success) {
      var data = response.data;
      currencyData = data;
      source = '<div class="col-sm-3 currencyBox"><table class="table table-bordered currencyTable">' + '<tr>' + '<td align="center"><span class="configLevel"></span>' + $.i18n.prop('stringCustomerListLevel') + '</td>' + '<td colspan="2">' + '<a class="btn btn-sm btn-danger" href="javascript:;" onclick="removeSelfTable(this)" >' + $.i18n.prop('stringCommissionSettingDelete') + '</a>' + '</td>' + '</tr>';
      for (var i = 0; i < data.length; i++) {
        source += '<tr class="currencyGroup">' + '<td align="center">' + data[i].name + '<input type="hidden" class="currencyId" value="' + data[i].id + '"></td>' + '<td><input type="text" class="form-control input-sm fixedRebate" placeholder="' + $.i18n.prop('stringCommissionSettingFixedValue') + '" oninput="fixedChange(this)"></td>' + '<td><input type="text" class="form-control input-sm pointRebate" placeholder="' + $.i18n.prop('stringCommissionSettingPoint') + '" oninput="pointChange(this)"></td>' + '</tr>';
      }
      source += '</table><div>';
    }
  });
}
function getGroupRebateConfig(groupName) {
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/groupRebate/getConfig',
    data: {
      groupName: groupName,
    },
    dataType: 'json',
    async: true,
    type: 'get'
  }).done(function(response) {
    if (response.success) {
      var data = response.data;
      var configSource = '';
      for (var i = 0; i < data.length; i++) {
        configSource += '<div class="col-sm-3 currencyBox"><table class="table table-bordered currencyTable">' + '<tr>' + '<td align="center"><span class="configLevel">' + data[i].level + '</span>' + $.i18n.prop('stringCustomerListLevel') + '</td>' + '<td colspan="2">' + '<a class="btn btn-sm btn-danger" href="javascript:;" onclick="removeSelfTable(this)" >' + $.i18n.prop('stringCommissionSettingDelete') + '</a>' + '</td>' + '</tr>';
        for (var j = 0; j < currencyData.length; j++) {
          var fixedVal = '',
          pointVal = '';
          for (var k = 0; k < data[i].groupRebateCurrencyDTO.length; k++) {
            if (data[i].groupRebateCurrencyDTO[k].currencyId == currencyData[j].id) {
              fixedVal = data[i].groupRebateCurrencyDTO[k].fixed == 0 ? "": data[i].groupRebateCurrencyDTO[k].fixed;
              pointVal = data[i].groupRebateCurrencyDTO[k].point == 0 ? "": data[i].groupRebateCurrencyDTO[k].point;
            }
          }
          fixedVal = fixedVal == null ? '': fixedVal;
          pointVal = pointVal == null ? '': pointVal;
          configSource += '<tr class="currencyGroup">' + '<td align="center">' + currencyData[j].name + '<input type="hidden" class="currencyId" value="' + currencyData[j].id + '"></td>' + '<td><input type="text" class="form-control input-sm fixedRebate" value="' + fixedVal + '" placeholder="' + $.i18n.prop('stringCommissionSettingFixedValue') + '" oninput="fixedChange(this)"></td>' + '<td><input type="text" class="form-control input-sm pointRebate" value="' + pointVal + '" placeholder="' + $.i18n.prop('stringCommissionSettingPoint') + '" oninput="pointChange(this)"></td>' + '</tr>';
        }
        configSource += '</table></div>';
      }
      $('#rebateBox').html(configSource);
    }
  });
}

crm.admin.menu${OP.menu}.save = function() {
  var groupName = $('#rebateGroup').val();
  var levelArray = new Array();
  $('.currencyTable').each(function(index, item) {
    var levelObj = new Object();
    levelObj.level = $(item).find('.configLevel').text();
    var currencyArray = new Array();
    $(item).find('.currencyGroup').each(function(groupIndex, groupItem) {
      var currencyObj = new Object();
      currencyObj.currencyId = $(groupItem).find('.currencyId').val();
      currencyObj.fixedRebate = $(groupItem).find('.fixedRebate').val() == '' ? '': $(groupItem).find('.fixedRebate').val();
      currencyObj.pointRebate = $(groupItem).find('.pointRebate').val() == '' ? '': $(groupItem).find('.pointRebate').val();
      currencyArray.push(currencyObj);
    });
    levelObj.currencies = currencyArray;
    levelArray.push(levelObj);
  });
  var levelStr = JSON.stringify(levelArray);
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/groupRebate/save',
    data: {
      groupName: groupName,
      levelJson: levelStr
    },
    dataType: 'json',
    async: true,
    type: 'post'
  }).done(function(data) {
    crm.toastrsAlert({
      code: data.success ? 'success': 'error',
      msg: data.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
    });
  });
}

function plusNextLevel() {
  $('#rebateBox').append(source);
  reSortLevel();
}

function removeSelfTable(self) {
  $(self).parents(".currencyBox").remove();
  reSortLevel();
}

function reSortLevel() {
  $('.configLevel').each(function(index, item) {
    $(item).text(++index);
  });
}

function fixedChange(self) {
  var selfVal = $(self).val();
  if (selfVal != '') {
    $(self).parent().parent().find('.pointRebate').val('');
  }
}

function pointChange(self) {
  var selfVal = $(self).val();
  if (selfVal != '') {
    $(self).parent().parent().find('.fixedRebate').val('');
  }
}

function cancelRebateConfig() {
  var groupName = $('#rebateGroup').val();
  getGroupRebateConfig(groupName);
}
</script>