<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="row border-bottom white-bg dashboard-header">
  <div class="col-sm-12">
    <div class="ibox float-e-margins">
      <div class="ibox-content">
        <table class="table table-bordered">
          <tr>
            <td align="right" width="20%">
              <span class="stringAccount"></span>
            </td>
            <td colspan="2">${dto.account }</td>
          </tr>
          <tr>
            <td align="right" width="20%">
              <span class="stringRealName"></span>
            </td>
            <td colspan="2">${dto.name }</td>
          </tr>
          <tr>
            <td align="right" width="20%">
              <span class="stringPhone"></span>
            </td>
            <td colspan="2">${dto.phone }</td>
          </tr>
          <tr>
            <td align="right" width="20%">
              MT&nbsp;
              <span class="stringAccount"></span>
            </td>
            <td colspan="2">${dto.login }</td>
          </tr>
          <tr>
            <td align="right" width="20%">
              MT&nbsp;
              <span class="stringAccount"></span>
              <span class="stringBalance"></span>
            </td>
            <td colspan="2">${balance }</td>
          </tr>
          <c:if test="${dto.type eq 1}">
            <tr>
              <td align="right" width="20%">
                MT&nbsp;
                <span class="stringCommission"></span>
                <span class="stringAccount"></span>
              </td>
              <td colspan="2">${dto.rebateLogin }</td>
            </tr>
            <tr>
              <td align="right" width="20%">
                MT&nbsp;
                <span class="stringCommission"></span>
                <span class="stringAccount"></span>
                <span class="stringBalance"></span>
              </td>
              <td colspan="2">${rebateBalance }</td>
            </tr>
            <tr>
              <td align="right" width="20%">
                CRM&nbsp;
                <span class="stringCommission"></span>
                <span class="stringBalance"></span>
              </td>
              <td>
                <span id="rebateBalance">${rebate }</span>
              </td>
              <td>
                <button type="button" class="btn btn-sm btn-w-m btn-primary" onclick="rebateSumToMT4()" id="rebateBtn">
                  <span class="stringCommissionDeposit"></span>
                </button>
              </td>
            </tr>
            <tr>
              <td align="right" width="20%">
                <span class="stringReferralLink"></span>
              </td>
              <td><%=basePath%>registered/belong_${USER_SESSION_KEY.managerid }
              </td>
              <td>
                <button type="button" class="btn btn-sm btn-w-m btn-primary" id="copyBtn" data-clipboard-text="<%=basePath %>registered/belong_${USER_SESSION_KEY.managerid }">
                  <span class="stringCopyLink"></span>
                </button>
              </td>
            </tr>
            <tr>
              <td align="right" width="20%">
                <span class="stringExtendedTwoDimensionalCode"></span>
              </td>
              <td>
                <img alt="" width="100" height="100" style="display: block;" src="<%=basePath %>pub/underling/qrCode?urlLink=<%=basePath %>registered/belong_${USER_SESSION_KEY.managerid }">
              </td>
              <td>
                <button type="button" class="btn btn-sm btn-w-m btn-primary" onclick="download('<%=basePath %>pub/underling/qrCode?urlLink=<%=basePath %>registered/belong_${USER_SESSION_KEY.managerid }')">
                  <span class="stringDownload"></span>
                </button>
              </td>
            </tr>
          </c:if>
        </table>
      </div>
    </div>
  </div>
</div>
<script>

loadProperties();
$('.stringAccount').html($.i18n.prop('stringAccount'));
$('.stringRealName').html($.i18n.prop('stringRealName'));
$('.stringPhone').html($.i18n.prop('stringPhone'));
$('.stringBalance').html($.i18n.prop('stringBalance'));
$('.stringCommission').html($.i18n.prop('stringCommission'));
$('.stringReferralLink').html($.i18n.prop('stringReferralLink'));
$('.stringExtendedTwoDimensionalCode').html($.i18n.prop('stringExtendedTwoDimensionalCode'));
$('.stringCommissionDeposit').html($.i18n.prop('stringCommissionDeposit'));
$('.stringCopyLink').html($.i18n.prop('stringCopyLink'));
$('.stringDownload').html($.i18n.prop('stringDownload'));

var rebating = false;
function rebateSumToMT4() {
  if (rebating) {
    return;
  }
  crm.confirm(function() {
    rebating = true;
    $('#rebateBtn').html('正在入金&nbsp;<i class="fa fa-spinner fa-spin"></i>');
    var login = '${dto.login}';
    $.ajax({
      url : '${pageContext.request.contextPath}/pub/managerRebate/rebateSumToMT4',
      data : {
        login : login
      },
      dataType : 'json',
      async : true,
      type : 'post'
    }).done(function(response) {
      $('#rebateBtn').html('返佣入金');
      rebating = false;
      crm.toastrsAlert({
        code : response.success ? 'success' : 'error',
        msg : response.success ? '入金成功' : response.msg
      });
      if (response.success) {
        $('#rebateBalance').html(response.data);
        window.location.reload();
      }
    });
  });
}

var clipboard = new Clipboard('#copyBtn');
  clipboard.on('success', function(e) {
  crm.toastrsAlert({
    code : 'success',
    msg : '复制成功'
  });
});
  
clipboard.on('error', function(e) {
  console.log(e);
});

function download(src) {
  var $a = $("<a></a>").attr("href", src).attr("download", "推广二维码.png");
  $a[0].click();
}
</script>