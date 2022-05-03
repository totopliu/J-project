 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>跳转中...</title>
</head>
<body  onLoad="document.hxtForm.submit();">
    <form action="http://101.52.129.24:9090/Gateway/direct/Bank/subOrder.do" name="hxtForm" method="post">
        <input type="hidden" name="merchantId" value="${dto.merchantId }"><br>
        <input type="hidden" name="cardType" value="${dto.cardType }"><br>
        <input type="hidden" name="bankCode" value="${dto.bankCode }"><br>
        <input type="hidden" name="orderAmt" value="${dto.orderAmt }"><br>
        <input type="hidden" name="merOrderId" value="${dto.merOrderId }"><br>
        <input type="hidden" name="returnUrl" value="${dto.returnUrl }"><br>
        <input type="hidden" name="notifyUrl" value="${dto.notifyUrl }"><br>
        <input type="hidden" name="remark" value="${dto.remark }"><br>
        <input type="hidden" name="timeStamp" value="${dto.timeStamp }"><br>
        <input type="hidden" name="sign" value="${dto.sign }"><br>
    </form>
</body>
</html>