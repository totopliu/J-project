<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function() {
  loadProperties();
  $('.stringTaskName').html($.i18n.prop('stringTaskName'));
  $('.stringTaskDescription').html($.i18n.prop('stringTaskDescription'));
  $('.stringTaskOperation').html($.i18n.prop('stringTaskOperation'));
  $('.stringTaskPause').html($.i18n.prop('stringTaskPause'));
  $('.stringTaskRecovery').html($.i18n.prop('stringTaskRecovery'));
});

function pauseJob(jobName) {
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/job/pauseJob',
    data: {
      jobName: jobName
    },
    dataType: "json",
    type: "POST",
    success: function(response) {
      refreshPage();
    },
    error: function(d) {
      console.log(d);
    }
  });
}

function resumeJob(jobName) {
  $.ajax({
    url: '${pageContext.request.contextPath}/pub/job/resumeJob',
    data: {
      jobName: jobName
    },
    dataType: "json",
    type: "POST",
    success: function(response) {
      refreshPage();
    },
    error: function(d) {
      console.log(d);
    }
  });
}

function refreshPage() {
  var o = layer.load();
  $(".J_mainContent div.J_box:visible").html('<div class="loading">' + $.i18n.prop('stringLoad') + '...<div>');
  $(".J_mainContent div.J_box:visible").loadUrl(rootPath + '/op_list_${OP.menu}', 'get', {},
  function(data) {
    layer.close(o);
    $(".J_mainContent div.J_box:visible").find("div.loading").remove();
  });
}
</script>
<!-- 去掉 fadeInRight兼容火狐浏览器（关闭操作功能） -->
<div class="wrapper wrapper-content animated">
  <div class="ibox float-e-margins">
    <div class="ibox-content">
      <table class="table">
        <tr>
          <th>
            <span class="stringTaskName"></span>
          </th>
          <th>
            <span class="stringTaskDescription"></span>
          </th>
          <th>
            <span class="stringTaskOperation"></span>
          </th>
        </tr>
        <c:forEach items="${dtos }" var="v">
          <tr>
            <td>${v.jobName }</td>
            <td>${v.description }</td>
            <td>
              <button class="btn btn-xs btn-danger" onclick="pauseJob('${v.jobName }')">
                <span class="stringTaskPause"></span>
              </button>
              <button class="btn btn-xs btn-primary" onclick="resumeJob('${v.jobName }')">
                <span class="stringTaskRecovery"></span>
              </button>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>
</div>