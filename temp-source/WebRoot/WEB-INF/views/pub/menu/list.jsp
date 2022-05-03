<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
  background-color: #FFF;
}
</style>
<!-- 去掉 fadeInRight兼容火狐浏览器（关闭操作功能） -->
<div class="wrapper wrapper-content animated" style="height: 100%">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <%@ include file="/WEB-INF/views/common/toolbar.jsp"%>
      <div class="table-responsive" style="margin-top: 20px;">
        <form role="form" class="form-inline" action="${ctx}/menu/list">
          <table id="admin_menu${OP.menu}_datagrid" class="table table table-striped table-bordered table-hover">
            <thead>
              <tr>
                <th>
                  <input type="checkbox" onclick="$('.ii-checks').prop('checked',($(this).prop('checked') ? true : false ))" name="input[]">
                </th>
                <th>
                  <span class="stringMenuName"></span>
                </th>
                <th>
                  <span class="stringMenuCatalog"></span>
                </th>
                <th>
                  <span class="stringMenuIcon"></span>
                </th>
                <th>
                  <span class="stringMenuDeleteOrNot"></span>
                </th>
                <th>
                  <span class="stringMenuAddTime"></span>
                </th>
                <th>
                  <span class="stringMenuModificationTime"></span>
                </th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${page.list}" var="tree">
                <tr class="treegrid-${tree.id} <c:if test="${tree.pid != 0}">treegrid-parent-${tree.pid}</c:if>">
                  <td>
                    <input type="checkbox" class="ii-checks" name="check" value="${tree.id}">
                  </td>
                  <td>${tree.name}</td>
                  <td>${tree.channel}</td>
                  <td>
                    <i class="${tree.img} text-navy"></i>
                  </td>
                  <td>${tree.state ? '<i class="fa fa-check text-navy"></i>' : '<i class="fa fa-close text-danger"></i> '}</td>
                  <td>
                    <fmt:formatDate value="${tree.addtime}" pattern="yyyy-MM-dd HH:mm:ss" />
                  </td>
                  <td>
                    <fmt:formatDate value="${tree.updatetime}" pattern="yyyy-MM-dd HH:mm:ss" />
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <div class="fixed-table-pagination" style="display: block; margin-top: -50px;">
            <div class="pull-left pagination-detail">
              <span class="pagination-info">
                <span class="stringMenuShowing"></span>
                ${page.pageNum}
                <span class="stringMenuTo"></span>
                ${page.pageNum * page.pageSize }
                <span class="stringMenuRecords"></span>
                ，
                <span class="stringMenuTotalRecords"></span>
                &nbsp;${page.total}&nbsp;
                <span class="stringMenuRecords"></span>
              </span>
              <span class="page-list">
                <span class="stringMenuEachPageShow"></span>
                <span class="btn-group dropup">
                  <button type="button" class="btn btn-default  dropdown-toggle" data-toggle="dropdown">
                    <span class="page-size">${page.pageSize}</span>
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu" id="resources_meny">
                    <li>
                      <a href="javascript:void(0)">10</a>
                    </li>
                    <li>
                      <a href="javascript:void(0)">30</a>
                    </li>
                    <li>
                      <a href="javascript:void(0)">50</a>
                    </li>
                  </ul>
                </span>
                <span class="stringMenuRecords"></span>
                &nbsp;
                <span class="stringMenuRecordsAreDisplayedPerPage"></span>
              </span>
            </div>
            <div class="pull-right pagination ${page.pageSize >= page.total ? 'hidden' : null}">
              <ul class="pagination">
                <li class="page-first ${page.pageNum == page.firstPage ? 'disabled' : null}" onclick="loadResources(1)">
                  <a href="javascript:void(0)">«</a>
                </li>
                <li class="page-pre ${page.pageNum == page.firstPage ? 'disabled' : null}" onclick="loadResources(${page.pageNum-1})">
                  <a href="javascript:void(0)">‹</a>
                </li>
                <c:forEach begin="1" end="${page.pages}" varStatus="k">
                  <li class="page-number ${page.pageNum == k.index ? 'active' : null}" onclick="loadResources(${k.index})">
                    <a href="javascript:void(0)">${k.index}</a>
                  </li>
                </c:forEach>
                <li class="page-next ${page.pageNum == page.lastPage ? 'disabled' : null}" onclick="loadResources(${page.pageNum+1})">
                  <a href="javascript:void(0)">›</a>
                </li>
                <li class="page-last ${page.pageNum == page.lastPage ? 'disabled' : null}" onclick="loadResources(${page.lastPage})">
                  <a href="javascript:void(0)">»</a>
                </li>
              </ul>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
loadProperties();
$('.stringMenuName').html($.i18n.prop('stringMenuName'));
$('.stringMenuCatalog').html($.i18n.prop('stringMenuCatalog'));
$('.stringMenuIcon').html($.i18n.prop('stringMenuIcon'));
$('.stringMenuDeleteOrNot').html($.i18n.prop('stringMenuDeleteOrNot'));
$('.stringMenuAddTime').html($.i18n.prop('stringMenuAddTime'));
$('.stringMenuModificationTime').html($.i18n.prop('stringMenuModificationTime'));
$('.stringMenuShowing').html($.i18n.prop('stringMenuShowing'));
$('.stringMenuTo').html($.i18n.prop('stringMenuTo'));
$('.stringMenuRecords').html($.i18n.prop('stringMenuRecords'));
$('.stringMenuTotalRecords').html($.i18n.prop('stringMenuTotalRecords'));
$('.stringMenuEachPageShow').html($.i18n.prop('stringMenuEachPageShow'));
$('.stringMenuRecordsAreDisplayedPerPage').html($.i18n.prop('stringMenuRecordsAreDisplayedPerPage'));

$(function() {
  crm.ns('crm.admin.menu${OP.menu}');
  $('#admin_menu${OP.menu}_datagrid').treegrid({
    expanderExpandedClass: 'glyphicon glyphicon-minus',
    expanderCollapsedClass: 'glyphicon glyphicon-plus'
  });
});

$("#resources_meny>li").click(function() {
  crm.reloadDiv("op_list_${OP.menu}?pageSize=" + $(this).text());
});

function loadResources(pageNum) {
  crm.reloadDiv("op_list_${OP.menu}?pageNum=" + pageNum);
}
//删除
crm.admin.menu${OP.menu}.remove = function() {
  var cbox = [];
  $("input[name='check']").each(function() {
    if (true == $(this).is(':checked')) cbox.push($(this).val());
  });
  if (cbox == "" || cbox.length == 0) {
    crm.toastrsAlert({
      code: 'info',
      msg: $.i18n.prop('stringMenuChooseRecordToDelete')
    });
    return;
  }
  layer.confirm($.i18n.prop('stringMenuDeleteOrNot') + '？',
  function(index) {
    $.ajax({
      type: 'post',
      url: rootPath + '/op_remove_${OP.menu}',
      data: {
        "ids": cbox.join(",")
      },
      dataType: 'json',
      success: function(data) {
        crm.reloadDiv(rootPath + '/op_list_${OP.menu}');
        crm.closeWindow();
        crm.toastrsAlert({
          code: data.success ? 'success': 'error',
          msg: data.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringMenuFailure')
        });
      }
    });
  })
};

crm.admin.menu${OP.menu}.add = function() {
  crm.showWindow({
    title: $.i18n.prop("stringMenuAddMenu"),
    href: 'op_edit_${OP.menu}',
    width: '50%',
    height: '65%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}

crm.admin.menu${OP.menu}.edit = function() {
  var cbox = [];
  $("input[name='check']").each(function() {
    if (true == $(this).is(':checked')) cbox.push($(this).val());
  });
  if (cbox == "") {
    layer.msg($.i18n.prop("stringMenuSelectEditItem"));
    return;
  }
  if (cbox.length > 1) {
    layer.msg($.i18n.prop("stringMenuOnlyOneSelected"));
    return;
  }
  crm.showWindow({
    title: $.i18n.prop('stringMenuEditMenu'),
    href: 'op_edit_${OP.menu}?id=' + cbox,
    width: '50%',
    height: '65%',
    okhandler: function() {
      crm.admin.menu${OP.menu}.save();
    },
    cancelhandler: function() {
      crm.closeWindow();
    }
  });
}
</script>