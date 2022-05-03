<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <a href="druid" target="_blank" class="btn btn-sm btn-w-m btn-primary"><span class="stringGoToMonitorPage"></span></a>
    </div>
  </div>
</div>
<script type="text/javascript">
$(function(){
  loadProperties();
  $('.stringGoToMonitorPage').html($.i18n.prop('stringGoToMonitorPage'));
});
</script>