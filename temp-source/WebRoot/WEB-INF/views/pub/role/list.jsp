<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
loadProperties();
$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '/${MENU.channel}/query',
    height: '100%',
    sortName: 'id',
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
    locale: '${lg}',
    columns: [{
      checkbox: true
    },
    {
      field: 'name',
      title: $.i18n.prop("stringRoleName"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'code',
      title: $.i18n.prop("stringRoleCode"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'defaultState',
      title: $.i18n.prop("stringRoleRegistrationDefaultRole"),
      align: 'center',
      valign: 'middle',
      formatter: stateFormatter
    },
    {
      field: 'remark',
      title: $.i18n.prop("stringMenuRemark"),
      align: 'left',
      valign: 'top'
    }]
  });
})

function stateFormatter(value, row, index) {
  if (value == 1) {
    return '<i class="fa fa-check text-navy"></i>';
  } else {
    return '<i class="fa fa-close text-danger"></i>';
  }
  return value;
}

//新增
crm.admin.menu${OP.menu}.add = function() {
  crm.showWindow({
    title: $.i18n.prop("stringRoleAddRole"),
    href: 'op_edit_${OP.menu}',
    width: '70%',
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
crm.admin.menu${OP.menu}.edit = function() {
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop("stringMailPleaseSelectEditRecord")
    });
    return;
  }
  if (rows.length > 1) {
    crm.toastrsAlert({
      code: 'warning',
      msg: $.i18n.prop("stringMailSorryOnlyOneCouldbeEdit")
    });
    return;
  }
  crm.showWindow({
    title: $.i18n.prop("stringRoleEditRole"),
    href: rootPath + '/op_edit_${OP.menu}?id=' + rows[0].id,
    width: '70%',
    height: '90%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}

//删除
crm.admin.menu${OP.menu}.remove = function() {
  var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
  if (rows.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop("stringMenuChooseRecordToDelete")
    });
    return;
  }
  crm.confirm(function() {
    var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
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
</script>
<!-- 去掉 fadeInRight兼容火狐浏览器（关闭操作功能） -->
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true">
        </table>
      </div>
    </div>
  </div>
</div>