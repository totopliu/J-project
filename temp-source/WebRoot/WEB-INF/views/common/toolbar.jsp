<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- CRUD按钮 -->
<div class="doc-buttons">
  <c:forEach var="operate" items="${operates}">
    <c:if test="${operate.isshow == 1  && operate.op != 'list'}">
      <button onclick="crm.admin.menu${OP.menu}.${operate.op}();" class="btn btn-sm btn-primary" type="button">
        <i class="fa fa-${operate.icon}"></i>
        <c:choose>
          <c:when test="${lg eq 'en' }">
            <span class="nav-label">${operate.englishName}</span>
          </c:when>
          <c:when test="${lg eq 'vi' }">
            <span class="nav-label">${operate.vietnamese}</span>
          </c:when>
          <c:otherwise>
            <span class="nav-label">${operate.name}</span>
          </c:otherwise>
        </c:choose>
      </button>
    </c:if>
  </c:forEach>
</div>

