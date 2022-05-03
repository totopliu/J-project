<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
loadProperties();

$(function(){
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
    },{
       field: 'name',
       title: $.i18n.prop("stringCurrencyGroupName"),
       align: 'center',
       valign: 'middle'
    },{
       field: 'gmt_modified_fmt',
       title: $.i18n.prop("stringMenuModificationTime"),
       align: 'center',
       valign: 'middle',
       sortable: true
    },{
       field: 'gmt_create_fmt',
       title: $.i18n.prop("stringMailCreationTime"),
       align: 'center',
       valign: 'middle',
       sortable: true
    }]
  });
});

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
    order : order
  }
}

  //新增
  crm.admin.menu${OP.menu}.add = function(){
    crm.showWindow({
      title:$.i18n.prop("stringCurrencyAddGroup"),
      href:rootPath + '/op_edit_${OP.menu}',
      width:'30%',
      height:'30%',
      okhandler:function(){
        crm.admin.menu${OP.menu}.save();
      },
      cancelhandler:function(){
        crm.closeWindow();
      }
    });
  }

  //编辑
  crm.admin.menu${OP.menu}.edit = function(){
    var rows = $('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
    if (rows.length == 0) {
      crm.toastrsAlert({
        code:'info',
        msg:$.i18n.prop("stringMailPleaseSelectEditRecord")
      });
      return;
    }
    if (rows.length > 1) {
      crm.toastrsAlert({
        code:'warning',
        msg:$.i18n.prop("stringMailSorryOnlyOneCouldbeEdit")
      });
      return;
    }
    crm.showWindow({
      title:$.i18n.prop("stringCurrencyEditGroup"),
      href:rootPath + '/op_edit_${OP.menu}?id='+rows[0].id,
      width:'30%',
      height:'30%',
      okhandler:function(){
        crm.admin.menu${OP.menu}.save();
      },
      cancelhandler:function(){
        crm.closeWindow();
      }
    });
  }

  //删除
  crm.admin.menu${OP.menu}.remove = function(){
    var rows =$('#admin_menu${OP.menu}_datagrid').bootstrapTable('getSelections');
    if (rows.length == 0){
      crm.toastrsAlert({
        code:'info',
        msg:$.i18n.prop("stringMenuChooseRecordToDelete")
      });
      return;
    }
    crm.confirm(function(){
      var ps = [];
      $.each(rows,function(i,n){
        ps.push(n.id);
      });
      $.ajax({
        type: 'post',
        url: rootPath + '/op_remove_${OP.menu}',
        data: {"ids": ps.join(",")},
        dataType: 'json',
        success: function (data) {
          $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
          crm.toastrsAlert({
            code: data.success ? 'success' :'error',
            msg: data.success ? $.i18n.prop("stringMenuSuccess") :$.i18n.prop("stringMenuFailure")
          });
        }
      });
    });
  };
</script>
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="row">
        <div class="col-sm-6">
          <div class="table-responsive">
            <table id="admin_menu${OP.menu}_datagrid"
              data-toolbar="#toolbar" data-show-footer="false" data-mobile-responsive="true">
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
