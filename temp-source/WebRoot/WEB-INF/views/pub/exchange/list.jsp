<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld" %>
<script type="text/javascript">
    $(function () {
        loadProperties();
        $('.stringExchangeDepositExchangeRate').html($.i18n.prop('stringExchangeDepositExchangeRate'));
        $('.stringExchangeWithdrawalExchangeRate').html($.i18n.prop('stringExchangeWithdrawalExchangeRate'));
    });

    crm.ns('crm.admin.menu${OP.menu}');
    crm.admin.menu${OP.menu}.save = function () {
        $.ajax({
            url: '${pageContext.request.contextPath}/pub/exchange/save',
            data: {
                rate: $('input[name="rate"]').val(),
                outRate: $('input[name="outRate"]').val()
            },
            dataType: 'json',
            async: true,
            type: 'post'
        }).done(function (response) {
            crm.toastrsAlert({
                code: response.success ? 'success' : 'error',
                msg: response.success ? $.i18n.prop('stringMenuSuccess') : $.i18n.prop('stringExchangeSaveFailed')
            });
        });
    }

</script>
<div class="wrapper wrapper-content">
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <%@ include file="/WEB-INF/views/common/toolbar.jsp" %>
            <table class="table table-bordered">
                <tr>
                    <td align="right">
                        <span class="stringExchangeDepositExchangeRate"></span>
                    </td>
                    <td>
                        <input type="text" class="form-control input-sm" name="rate" value="${dto.rate }">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="stringExchangeWithdrawalExchangeRate"></span>
                    </td>
                    <td>
                        <input type="text" class="form-control input-sm" name="outRate" value="${dto.outRate }">
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
