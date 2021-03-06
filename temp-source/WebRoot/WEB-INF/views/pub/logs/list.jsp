<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
  loadProperties();

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
    locale: '${lg}',
    columns: [{
      checkbox: true
    },
    {
      field: 'account',
      title: $.i18n.prop("stringAccount"),
      align: 'center',
      valign: 'middle',
      sortable: true
    },
    {
      field: 'title',
      title: $.i18n.prop("stringLogActionItem"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'message',
      title: $.i18n.prop("stringLogInformation"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'optime',
      title: $.i18n.prop("stringLogOperationTime"),
      align: 'left',
      valign: 'top',
      sortable: true,
      formatter: function(value, row, index) {
        return new Date(value).pattern("yyyy-MM-dd HH:mm:ss")
      }
    },
    {
      field: 'ip',
      title: $.i18n.prop("stringLogIP"),
      align: 'center',
      valign: 'middle'
    },
    {
      field: 'url',
      title: $.i18n.prop("stringLogJumpAddress"),
      align: 'center',
      valign: 'middle',
      sortable: true
    }]
  });

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
      order: order
    }
  }

  //导出日志
  crm.admin.menu${OP.menu}.export = function() {
    crm.confirm(function() {
      location.href = rootPath + "/op_export_${OP.menu}";
    });
  }

});
</script>
<!-- 去掉 fadeInRight兼容火狐浏览器（关闭操作功能） -->
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive">
        <table id="admin_menu${OP.menu}_datagrid" data-toolbar="#toolbar" data-show-refresh="true" data-show-toggle="true" data-show-columns="true" data-show-footer="false" data-mobile-responsive="true">
        </table>
      </div>
    </div>
  </div>
</div>