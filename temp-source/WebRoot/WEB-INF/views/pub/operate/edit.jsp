<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<div class="ibox float-e-margins">
  <div class="ibox-content">
    <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" method="post">
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringOperationMenu"></span>
        </label>
        <div class="col-sm-8">
          <select class="form-control m-b" name="menu" id="menu">
            <option value="">请选择菜单</option>
            <crm:list var="m" namespace="com.crm.platform.mapper.pub.MenuMapper.listMenuByscort">
              <option value="${m.id}" <c:if test="${m.id == dto.menu}" >selected</c:if>>
                <c:forEach var="a" begin="0" end="${m.nlevel}"> －  </c:forEach> ＋ ${m.name}
              </option>
            </crm:list>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringOperationName"></span>
        </label>
        <div class="col-sm-8">
          <input name="name" value="${dto.name}" class="form-control" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringOperationCode"></span>
        </label>
        <div class="col-sm-8">
          <input type="text" name="op" value="${dto.op}" class="form-control">
          <div class="help-block m-b-none">
            <i class="fa fa-info-circle"></i>
            <span class="stringOperationSuchAs"></span>
            list add edit remove save
            <span class="stringOperationEtc"></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringOperationIcon"></span>
        </label>
        <div class="col-sm-8">
          <input name="icon" value="${dto.icon}" class="form-control" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuSort"></span>
        </label>
        <div class="col-sm-8">
          <input name="ordno" value="${dto.ordno}" class="form-control" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringMenuRemark"></span>
        </label>
        <div class="col-sm-8">
          <textarea name="remark" cols="55" rows="3" style="resize: vertical;" class="form-control">${dto.remark}</textarea>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-3 control-label">
          <span class="stringOperationToolbar"></span>
        </label>
        <div class="col-sm-8">
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" value="1" <c:if test="${dto.isshow == 1}" > checked</c:if> name="isshow">
              <i></i>
              <span class="stringOperationVisual"></span>
            </label>
          </div>
          <div class="radio i-checks radio-inline">
            <label>
              <input type="radio" <c:if test="${dto.isshow == 0}" > checked</c:if> value="0" name="isshow">
              <i></i>
              <span class="stringOperationInvisible"></span>
            </label>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>

<script type="text/javascript">
  $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
  
  loadProperties();
  $('.stringOperationMenu').html($.i18n.prop('stringOperationMenu'));
  $(".stringOperationName").text($.i18n.prop('stringOperationName'));
  $(".stringOperationCode").text($.i18n.prop('stringOperationCode'));
  $(".stringOperationSuchAs").text($.i18n.prop('stringOperationSuchAs'));
  $(".stringOperationEtc").text($.i18n.prop('stringOperationEtc'));
  $(".stringOperationIcon").text($.i18n.prop('stringOperationIcon'));
  $(".stringMenuSort").text($.i18n.prop('stringMenuSort'));
  $(".stringMenuRemark").text($.i18n.prop('stringMenuRemark'));
  $(".stringOperationToolbar").text($.i18n.prop('stringOperationToolbar'));
  $(".stringOperationVisual").text($.i18n.prop('stringOperationVisual'));
  $(".stringOperationInvisible").text($.i18n.prop('stringOperationInvisible'));
  
  $("#menu option:first-child").text($.i18n.prop('stringOperationPleaseChooseMenu'));
  
$(function(){
  crm.admin.menu${OP.menu}.save = function() {
    if($("#menu${OP.menu}Form").valid()){
      crm.ajaxJson({url:rootPath + "/op_save_${OP.menu}",data:$('#menu${OP.menu}Form').serializeArray()},function(){
        crm.closeWindow();
        $('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
      });
    }
  }
});
</script>