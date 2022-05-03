<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function(){
loadProperties();
$('.stringMenuName').html($.i18n.prop('stringMenuName'));
$('.stringProfileMTAccount').html($.i18n.prop('stringProfileMTAccount'));
$('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));

 crm.ns('crm.admin.menu${OP.menu}');
 $('#admin_menu${OP.menu}_datagrid').bootstrapTable({ 
    url: rootPath + '${MENU.channel}/query', 
    height: '100%',
    sortName: 'managerid',
    sortOrder: 'desc',
    striped: true,
    pagination: true,
    pageSize: 10,
    pageList: [10, 25, 50, 100, 200],
    search: false,
    sidePagination:'server',
    idField: 'id',
    uniqueId: 'id',
    responseHandler: responseHandler,
    queryParams: queryParams,
    minimumCountColumns: 2,
    clickToSelect: true,
    searchAlign: 'left',
    locale : '${lg}',
    columns: [{
        checkbox: true
    },
    {
        field: 'ename',
        title: $.i18n.prop("stringAccountManagementUserName"),
        sortable: true
    },
    {
        field: 'login',
        title: $.i18n.prop("stringProfileMTAccount"),
        sortable: true,
        formatter: managerLoginFmt
    },
    {
        field: 'loginGroup',
        title: $.i18n.prop("stringAccountManagementGrouping"),
        sortable: true
    },
    {
        field: 'Leverage',
        title: $.i18n.prop("stringAccountManagementLever"),
        sortable: true,
        formatter: managerLeverageFmt
    },
    {
        field: 'Balance',
        title: $.i18n.prop("stringAccountManagementBalanceAndNetWorth"),
        sortable: true,
        formatter: managerBalanceFmt
    },
    {
        field: 'rolename',
        title: $.i18n.prop("stringRoleName"),
        sortable: true
    },
    {
        field: 'type',
        title: $.i18n.prop("stringAccountAuditUserType"),
        sortable: true,
        formatter: userTypeFmt
    },
    {
        field: 'time',
        title: $.i18n.prop("stringAccountAuditRegistrationTime"),
        sortable: true
    },
    {
        field: 'operator',
        title: $.i18n.prop("stringTaskOperation"),
        formatter: sendEmailFmt
    }]
  });
});

function managerLoginFmt(value, row, index) {
  return $.i18n.prop("stringProfileMTAccount") + '：' + value + '<br/>' + $.i18n.prop("stringClosingOrdersMTName") + '：' + row.ename;
}

function managerLeverageFmt(value, row, index) {
  if (typeof(value) == 'undefined') {
    return '-';
  } else {
    return '1:' + value;
  }
}
   
function managerBalanceFmt(value, row, index) {
  if (typeof(value) == 'undefined') {
    return '-';
  } else {
    return $.i18n.prop("stringBalance") + '：&#36;' + value + '<br/>' + $.i18n.prop("stringAccountManagementNetWorth") + '：&#36;' + row.Equity;
  }
}

function userTypeFmt(value, row, index) {
  if (value == 0) {
    return $.i18n.prop("stringCustomerListTerminalCustomer");
  } else if (value == 1) {
    return $.i18n.prop("stringAccountAuditAgent");
  } else {
    return '-';
  }
}

function sendEmailFmt(value, row, index) {
  var reviewState = row.reviewState;
  if (reviewState > 0) {
    return '<a class="btn btn-xs btn-primary" href="javascript:;" onclick="sendEmail(' + row.id + ')">' + $.i18n.prop("stringAccountManagementResendEmail") + '</a>';
  } else {
    return '-';
  }
}

function sendEmail(id) {
  crm.confirm(function() {
    $.ajax({
      type: 'post',
      url: rootPath + '/op_sendEmail_${OP.menu}',
      data: {
        "id": id
      },
      dataType: 'json',
      success: function(data) {
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
        crm.toastrsAlert({
          code: data.success ? 'success': 'error',
          msg: data.success ? $.i18n.prop('stringMenuSuccess') : data.msg
        });
      }
    });
  });
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
    name: $("#managerNameFilter").val(),
    login: $('#managerLoginFilter').val()
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

//新增
crm.admin.menu${OP.menu}.add = function() {
  crm.showWindow({
    title: $.i18n.prop('stringAccountManagementAddAccount'),
    href: rootPath + '/op_edit_${OP.menu}',
    width: '660px',
    height: '90%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}

//编辑
crm.admin.menu${OP.menu}.edit = function(){
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop('stringMailPleaseSelectEditRecord')
    });
    return;
  }
  if (rows.length > 1) {
    crm.toastrsAlert({
      code: 'warning',
      msg: $.i18n.prop('stringMailSorryOnlyOneCouldbeEdit')
    });
    return;
  }
  crm.showWindow({
    title: $.i18n.prop('stringAccountManagementEditAccount'),
    href: rootPath + '/op_edit_${OP.menu}?id=' + rows[0].id,
    width: '660px',
    height: '90%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}

// 设置上级
crm.admin.menu${OP.menu}.setting = function(){
  var rows =$('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if(rows.length==0){
    crm.toastrsAlert({
         code:'info',
         msg:$.i18n.prop('stringAccountManagementPleaseSelectSetRecord')
    });
    return;
  }
  if(rows.length > 1){
    crm.toastrsAlert({
         code:'warning',
         msg:$.i18n.prop('stringAccountManagementSorryOnlyOneCouldbeSet')
    });
    return;
  }
  crm.showWindow({
    title:$.i18n.prop('stringAccountManagementSetSuperior'),
    href:rootPath + '/op_setting_${OP.menu}?managerId='+rows[0].id,
    width:'500px',
    height:'90%',
    okhandler:function(){
      crm.admin.menu${OP.menu}.saveSetting();
    },
    cancelhandler:function(){
      crm.closeWindow();
    }
  });
}

//删除
crm.admin.menu${OP.menu}.remove = function(){ 
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop('stringMenuChooseRecordToDelete')
    });
    return;
  }
  crm.confirm(function() {
    var ps = [];
    $.each(rows,
    function(i, n) {
      ps.push(n.id);
    });
    $.ajax({
      type: 'post',
      url: rootPath + '/op_remove_${OP.menu}',
      data: {
        "ids": ps.join(",")
      },
      dataType: 'json',
      success: function(data) {
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
        crm.toastrsAlert({
          code: data.success ? 'success': 'error',
          msg: data.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
        });
      }
    });
  });
}

crm.admin.menu${OP.menu}.setRebateLogin = function () {
  var rows =$('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if(rows.length==0){
    crm.toastrsAlert({
      code:'info',
      msg:$.i18n.prop('stringAccountManagementPleaseSelectSetRecord')
    });
    return;
  }
  if(rows.length > 1){
    crm.toastrsAlert({
      code:'warning',
      msg:$.i18n.prop('stringAccountManagementSorryOnlyOneCouldbeSet')
    });
    return;
  }
  crm.showWindow({
    title:$.i18n.prop('stringAccountManagementSetRebateAccount'),
    href:rootPath + '/op_setRebateLogin_${OP.menu}?managerId='+rows[0].id,
    width:'500px',
    height:'350px',
    okhandler:function(){
      crm.admin.menu${OP.menu}.updateRebateLogin();
    },
    cancelhandler:function(){
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
              <label class="control-label col-sm-2" for="">
                <span class="stringMenuName"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="managerNameFilter">
              </div>
              <label class="control-label col-sm-2" for="">
                <span class="stringProfileMTAccount"></span>
              </label>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="managerLoginFilter">
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
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true">
        </table>
      </div>
    </div>
  </div>
</div>