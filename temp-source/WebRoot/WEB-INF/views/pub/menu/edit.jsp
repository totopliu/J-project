<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
$(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});

loadProperties();
$('.stringMenuAddMenuName').html($.i18n.prop('stringMenuAddMenuName'));
$('.stringMenuPreviousMenu').html($.i18n.prop('stringMenuPreviousMenu'));
$('.stringMenuIcon').html($.i18n.prop('stringMenuIcon'));
$('.stringMenuCatalog').html($.i18n.prop('stringMenuCatalog'));
$('.stringMenuParameter').html($.i18n.prop('stringMenuParameter'));
$('.stringMenuSort').html($.i18n.prop('stringMenuSort'));
$('.stringMenuStatus').html($.i18n.prop('stringMenuStatus'));
$('.stringMenuOpen').html($.i18n.prop('stringMenuOpen'));
$('.stringMenuClose').html($.i18n.prop('stringMenuClose'));
$('.stringMenuRemark').html($.i18n.prop('stringMenuRemark'));
$('.stringMenuUsageRules').html($.i18n.prop('stringMenuUsageRules'));

$("#pid option:first-child").text($.i18n.prop('stringMenuPleaseChoosePreviousMenu'));

$(function() {
  crm.admin.menu${OP.menu}.save = function(obj) {
    if ($("#menu${OP.menu}Form").valid()) {
      crm.ajaxJson({
        url: rootPath + "/op_save_${OP.menu}",
        data: $('#menu${OP.menu}Form').serializeArray()
      },
      function() {
        crm.closeWindow();
        crm.reloadDiv(rootPath + '/op_list_${OP.menu}');
      });
    }
  }

  crm.admin.menu${OP.menu}.imgs = function() {
    crm.showWindow({
      title: $.i18n.prop('stringMenuUsageRules'),
      type: 2,
      href: 'http://www.zi-han.net/theme/hplus/fontawesome.html?v=4.0#contao',
      width: '45%',
      height: '65%',
      okhandler: function() {
        var str = $(".layui-layer").attr("times");
        var index = parseInt(str) + 1;
        crm.closeNestWindow(index);
      },
      cancelhandler: function() {
        crm.closeWindow();
      }
    });
  }
});
</script>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form">
      <input type="hidden" name="id" value="${dto.id}" />
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuAddMenuName"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" name="name" class="form-control" value="${dto.name}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuPreviousMenu"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control m-b" name="pid" id="pid">
            <option value="">请选择上级菜单</option>
            <crm:list var="d" namespace="com.crm.platform.mapper.pub.MenuMapper.listMenuByscort">
              <option value="${d.id}" <c:if test="${d.id == dto.pid }" > selected</c:if>>
                <c:forEach var="a" begin="0" end="${d.nlevel}"> － </c:forEach> ＋ ${d.name}
              </option>
            </crm:list>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuIcon"></span>
        </label>
        <div class="col-sm-8">
          <input name="img" class="form-control" type="text" value="${dto.img}">
          <span class="help-block m-b-none">
            <i class="fa fa-info-circle"></i>
            <a href="javascript:;" onClick="crm.admin.menu${OP.menu}.imgs();">
              <span class="stringMenuUsageRules"></span>
            </a>
          </span>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuCatalog"></span>
        </label>
        <div class="col-sm-8">
          <input name="channel" class="form-control" type="text" value="${dto.channel}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuParameter"></span>
        </label>
        <div class="col-sm-8">
          <input name="param" class="form-control" type="text" value="${dto.param}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuSort"></span>
        </label>
        <div class="col-sm-8">
          <input name="ordno" class="form-control" type="text" value="${dto.ordno}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuStatus"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="true" <c:if test="${dto.state == true}" > checked</c:if> name="state">
              <i></i>
              <span class="stringMenuOpen"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.state == false}" > checked</c:if> value="false" name="state">
              <i></i>
              <span class="stringMenuClose"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuRemark"></span>
        </label>
        <div class="col-sm-8">
          <textarea name="remark" cols="55" rows="3" class="form-control">${dto.remark}</textarea>
        </div>
      </div>
    </form>
  </div>
</div>


