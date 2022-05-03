<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<script type="text/javascript">
loadProperties();
$('.stringMenuAddMenuName').html($.i18n.prop('stringMenuAddMenuName'));
$('.stringOperationCheck').html($.i18n.prop('stringOperationCheck'));

var tt = 0;
$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable({
    url: rootPath + '${MENU.channel}/query',
    height: '100%',
    sortName: 'name',
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
    locale: '${lg}',
    columns: [{
      checkbox: true
    },
    {
      field: 'menuname',
      title: $.i18n.prop("stringMenuAddMenuName"),
      align: 'center',
      valign: 'middle',
      sortable: true
    },
    {
      field: 'name',
      title: $.i18n.prop("stringOperationName"),
      align: 'center',
      valign: 'middle',
      sortable: true
    },
    {
      field: 'op',
      title: $.i18n.prop("stringOperationCode"),
      align: 'center',
      valign: 'middle',
      sortable: true
    },
    {
      field: 'icon',
      title: $.i18n.prop("stringOperationIcon"),
      align: 'left',
      valign: 'top',
      formatter: function(val, row) {
        return '<i class="fa fa-' + val + ' text-navy"></i>';
      },
      sortable: true
    },
    {
      field: 'ordno',
      title: $.i18n.prop("stringMenuSort"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'isshow',
      title: $.i18n.prop("stringOperationToolbar"),
      align: 'center',
      valign: 'middle',
      sortable: true,
      formatter: function(val, row) {
        return val == 1 ? '<i class="fa fa-check text-navy"></i> ': '<i class="fa fa-close text-danger"></i>';
      }
    }]
  });
});

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
    menuId: $("#menu").val()
  }
}

$.ajax({
  url: rootPath + '/pub/menu/findAll',
  dataType: 'json',
  async: true,
  type: 'get'
}).done(function(response) {
  if (response.success) {
    var data = response.data;
    var source = '<option value="0">' + $.i18n.prop("stringOperationPleaseChooseMenu") + '</option>';
    for (var i = 0; i < data.length; i++) {
      source += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
    }
    $('#menu').html(source);
  }
});

//新增
crm.admin.menu${OP.menu}.add = function() {
  crm.showWindow({
    title: $.i18n.prop('stringOperationAddOperation'),
    href: rootPath + '/op_edit_${OP.menu}',
    width: '50%',
    height: '60%',
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
  switch (rows.length) {
  case 0:
    crm.msg(msg.choose);
    break;
  case 1:
    crm.showWindow({
      title:
      $.i18n.prop('stringOperationEditOperation'),
      href: rootPath + '/op_edit_${OP.menu}?menu=' + rows[0].menu + '&op=' + rows[0].op,
      width: '50%',
      height: '60%',
      okhandler: function() {
        crm.admin.menu${OP.menu}.save();
      },
      cancelhandler: function() {
        crm.closeWindow();
      }
    });
    break;
  default:
    crm.msg(msg.single);
    break;
  }
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
    var ps = [];
    $.each(rows,
    function(i, n) {
      ps.push(n.menu + "-" + n.op);
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

// 查询
crm.admin.menu${OP.menu}.serach = function() {
  $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
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
              <label class="control-label col-sm-1">
                <span class="stringMenuAddMenuName"></span>
              </label>
              <div class="col-sm-3">
                <select id="menu" class="form-control"></select>
              </div>
              <div class="col-sm-4" style="text-align: left;">
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
