<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>

<script type="text/javascript">
loadProperties();
$('.stringMailPurpose').html($.i18n.prop('stringMailPurpose'));
$('.stringMailTitle').html($.i18n.prop('stringMailTitle'));
$('.stringEnglishMailTitle').html($.i18n.prop('stringEngLishMailTitle'));
$('.stringMailCreationTime').html($.i18n.prop('stringMailCreationTime'));
$('.stringMailTemplateKeyword').html($.i18n.prop('stringMailTemplateKeyword'));
$('.stringMailTemplate').html($.i18n.prop('stringMailTemplate'));
$('.stringMailSave').html($.i18n.prop('stringMailSave'));
$('.stringMailBack').html($.i18n.prop('stringMailBack'));

$('.stringMailUserName').html($.i18n.prop('stringMailUserName'));
$('.stringMailMT5TransactionAccount').html($.i18n.prop('stringMailMT5TransactionAccount'));
$('.stringMailMT5AccountPassword').html($.i18n.prop('stringMailMT5AccountPassword'));
$('.stringMailMT5AccountViewPassword').html($.i18n.prop('stringMailMT5AccountViewPassword'));
$('.stringMailMT5TransactionAccountPassword').html($.i18n.prop('stringMailMT5TransactionAccountPassword'));
$('.stringMailMT5TransactionAccountViewPassword').html($.i18n.prop('stringMailMT5TransactionAccountViewPassword'));
$('.stringMailMT5RebateAccount').html($.i18n.prop('stringMailMT5RebateAccount'));
$('.stringMailMT5RebateAccountPassword').html($.i18n.prop('stringMailMT5RebateAccountPassword'));
$('.stringProfileMTAccount').html($.i18n.prop('stringProfileMTAccount'));
$('.stringMailApplicationTime').html($.i18n.prop('stringMailApplicationTime'));
$('.stringMailAmount').html($.i18n.prop('stringMailAmount'));
$('.stringMailReason').html($.i18n.prop('stringMailReason'));
$('.stringMailUSD').html($.i18n.prop('stringMailUSD'));
$('.stringMailRMB').html($.i18n.prop('stringMailRMB'));
$('.stringMailEnglishName').html($.i18n.prop('stringMailEnglishName'));

$('.stringMailBank').html($.i18n.prop('stringMailBank'));
$('.stringMailBankcard').html($.i18n.prop('stringMailBankcard'));
  
  $(function(){
    crm.admin.menu${OP.menu}.save = function(obj) {
      for (instance in CKEDITOR.instances)
        CKEDITOR.instances[instance].updateElement();
      if ($("#menu${OP.menu}Form").valid()) {
        crm.ajaxJson(
          {url:rootPath + "/op_save_${OP.menu}", data:$('#menu${OP.menu}Form').serializeArray()},
          function(){
            refreshEmailTemp();
          }
        );
      }
    }
  });

  function refreshEmailTemp() {
    var o = layer.load();
    $(".J_mainContent div.J_box:visible").html('<div class="loading">' + $.i18n.prop("stringLoad") + '...<div>');
    $(".J_mainContent div.J_box:visible").loadUrl(rootPath + '/op_list_${OP.menu}','get',{},function(data) {
      layer.close(o);
      $(".J_mainContent div.J_box:visible").find("div.loading").remove();
    });
  }
  CKEDITOR.config.height = 400;
  CKEDITOR.config.width = 'auto';
  CKEDITOR.replace('editor');
  CKEDITOR.replace('eeditor');
</script>
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content row">
      <form class="form-horizontal m-t required-validate" id="menu${OP.menu}Form" action="op_save_${OP.menu}" method="post">
        <input type="hidden" name="id" value="${dto.id}" />
        <div class="form-group">
          <label class="col-sm-1 control-label">
            <span class="stringMailPurpose"></span>
          </label>
          <div class="col-sm-8">
            <p class="form-control-static">${dto.effect}</p>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label">
            <span class="stringMailTitle"></span>
          </label>
          <div class="col-sm-8">
            <input type="text" class="form-control" value="${dto.title}" name="title">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label">
            <span class="stringEnglishMailTitle"></span>
          </label>
          <div class="col-sm-8">
            <input type="text" class="form-control" value="${dto.etitle}" name="etitle">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label">
            <span class="stringMailTemplateKeyword"></span>
          </label>
          <div class="col-sm-8">
            <c:if test="${dto.code eq 'reg_review_success' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{mt5password}:
                <span class="stringMailMT5TransactionAccountPassword"></span>
                ;@{mt5passgc}:
                <span class="stringMailMT5TransactionAccountViewPassword"></span>
                ;@{mt5rebateAccount}:
                <span class="stringMailMT5RebateAccount"></span>
                ;@{mt5rebatePassword}:
                <span class="stringMailMT5RebateAccountPassword"></span>
              </p>
            </c:if>
            <c:if test="${dto.code eq 'out_review_success' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{createTime}:
                <span class="stringMailApplicationTime"></span>
                ;@{money}:
                <span class="stringMailAmount"></span>
              </p>
            </c:if>
            <c:if test="${dto.code eq 'in_review_success' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{createTime}:
                <span class="stringMailApplicationTime"></span>
                ;@{money}:
                <span class="stringMailAmount"></span>
              </p>
            </c:if>
            <c:if test="${dto.code eq 'reg_review_fail' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{reason}:
                <span class="stringMailReason"></span>
              </p>
            </c:if>
            <c:if test="${dto.code eq 'out_review_fail' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{createTime}:
                <span class="stringMailApplicationTime"></span>
                ;@{money}:
                <span class="stringMailAmount"></span>
                ;@{reason}:
                <span class="stringMailReason"></span>
              </p>
            </c:if>
            <c:if test="${dto.code eq 'in_review_fail' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{createTime}:
                <span class="stringMailApplicationTime"></span>
                ;@{money}:
                <span class="stringMailAmount"></span>
                ;@{reason}:
                <span class="stringMailReason"></span>
              </p>
            </c:if>
            <c:if test="${dto.code eq 'reg_review_common_success' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{mt5password}:
                <span class="stringMailMT5AccountPassword"></span>
                ;@{mt5passgc}:
                <span class="stringMailMT5AccountViewPassword"></span>
                ;
              </p>
            </c:if>
            <c:if test="${dto.code eq 'reg_remind' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{userEname}:
                <span class="stringMailEnglishName"></span>
                ;@{createTime}:
                <span class="stringMailApplicationTime"></span>
                ;
              </p>
            </c:if>
            <c:if test="${dto.code eq 'in_remind' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{dollar}
                <span class="stringMailUSD"></span>
                ;@{money}
                <span class="stringMailRMB"></span>
                ;
              </p>
            </c:if>
            <c:if test="${dto.code eq 'out_remind' }">
              <p class="form-control-static">
                @{username}:
                <span class="stringMailUserName"></span>
                ;@{mt5account}:
                <span class="stringMailMT5TransactionAccount"></span>
                ;@{dollar}
                <span class="stringMailUSD"></span>
                ;@{money}
                <span class="stringMailRMB"></span>
                ;@{bank}
                <span class="stringMailBank"></span>
                ;@{bankcard}
                <span class="stringMailBankcard"></span>
              </p>
            </c:if>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label">
            <span class="stringMailTemplate"></span>
          </label>
          <div class="col-sm-8">
            <textarea id="editor" name="content">${dto.content }</textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label">
            <span class="stringMailTemplate"></span>
          </label>
          <div class="col-sm-8">
            <textarea id="eeditor" name="econtent">${dto.econtent }</textarea>
          </div>
        </div>
        <div class="col-sm-offset-2 col-sm-4">
          <button type="button" class="btn btn-sm btn-w-m btn-primary" onclick="crm.admin.menu${OP.menu}.save()">
            <span class="stringMailSave"></span>
          </button>
          <button type="button" class="btn btn-sm btn-w-m btn-default" onclick="refreshEmailTemp()">
            <span class="stringMailBack"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</div>