<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/common.jspf"%>
</head>

<body class="fixed-sidebar full-height-layout gray-bg pace-done skin-1">
  <div class="crm-head">
    <div class="crm-title">
      <img alt="" src="${ctx}/back/img/title.png" width="60">
    </div>
    <div class="title-content">
      <div class="title-content-name">
        <span class="hello"></span>
        <strong class="font-bold">${USER_SESSION_KEY.name}</strong>
      </div>
      <select class="lg-select" id="lg" onchange="selectLg()">
        <option value="zh">中文</option>
        <option value="en">English</option>
        <option value="vi">Tiếng Việt</option>
      </select>
    </div>
    <div class="title-pic J_menuItem" href="${ctx}/profile" style="cursor: pointer;">
      <span style="display: none;" class="stringModifyHead"></span>
      <img alt="image" class="img-circle" src="<c:choose><c:when test='${empty USER_SESSION_KEY.photo}'>${ctx}/common/coder.jpeg</c:when><c:otherwise>${USER_SESSION_KEY.photo}</c:otherwise></c:choose>" width="46px" height="46px" />
    </div>
    <div class="title-menu-line"></div>
    <div class="title-menu" onclick="javascript: window.location.href='${ctx}/logout'">
      <div class="title-menu-icon">
        <i class="fa fa-power-off"></i>
      </div>
      <div class="title-menu-name">
        <span class="stringExit"></span>
      </div>
    </div>
    <div class="title-menu J_menuItem" href="${ctx}/profileInfo">
      <div class="title-menu-icon">
        <i class="fa fa-user"></i>
      </div>
      <div class="title-menu-name">
        <span class="stringPersonalData"></span>
      </div>
    </div>
    <div class="title-menu J_menuItem" href="${ctx}/profileSetting">
      <div class="title-menu-icon">
        <i class="fa fa-gear"></i>
      </div>
      <div class="title-menu-name">
        <span class="stringAccountSetting"></span>
      </div>
    </div>
  </div>
  <div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation" style="height: 100%; position: fixed !important;">
      <div class="nav-close">
        <i class="fa fa-times-circle"></i>
      </div>
      <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
          <c:if test="${list != null}">
            <c:forEach items="${list}" var="l">
              <li>
                <a href="javascript:;" style="height: 45px; line-height: 20px; border-bottom: 1px solid #1f1f1f; box-shadow: 0px 2px #3a4d61; font-size: 10px;">
                  <i class="${l.attributes.img}"></i>
                  <c:choose>
                    <c:when test="${param.lg eq 'en' }">
                      <span class="nav-label">${l.etext}</span>
                    </c:when>
                    <c:when test="${param.lg eq 'vi' }">
                      <span class="nav-label">${l.vtext}</span>
                    </c:when>
                    <c:otherwise>
                      <span class="nav-label">${l.text}</span>
                    </c:otherwise>
                  </c:choose>
                  <span class="fa arrow" style="margin-top: 4px;"></span>
                </a>
                <ul class="nav nav-second-level">
                  <c:forEach items="${l.children}" var="c">
                    <li>
                      <a title="${ch.text}" class="J_menuItem" href="${ctx}/op_list_${c.id}" style="height: 35px; line-height: 20px; font-size: 10px;">
                        <i class="${c.attributes.img}"></i>
                        <c:choose>
                          <c:when test="${param.lg eq 'en' }">
                            <span class="nav-label">${c.etext}</span>
                          </c:when>
                          <c:when test="${param.lg eq 'vi' }">
                            <span class="nav-label">${c.vtext}</span>
                          </c:when>
                          <c:otherwise>
                            <span class="nav-label">${c.text}</span>
                          </c:otherwise>
                        </c:choose>
                      </a>
                    </li>
                    <div style="border-bottom: 1px dashed #495a6b; margin: 0 20px 0 20px;"></div>
                  </c:forEach>
                </ul>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1" style="min-height: 643px;">
      <div class="row content-tabs">
        <button class="roll-nav roll-left J_tabLeft">
          <i class="fa fa-backward"></i>
        </button>
        <nav class="page-tabs J_menuTabs">
          <div class="page-tabs-content">
            <a href="javascript:void(0);" class="J_menuTab active main" id="main" data-id="main">
              <span class="stringIndex"></span>
            </a>
          </div>
        </nav>
        <button class="roll-nav roll-right J_tabRight">
          <i class="fa fa-forward"></i>
        </button>
        <div class="btn-group roll-nav roll-right">
          <button class="dropdown J_tabClose" data-toggle="dropdown" onclick="changeNavBarMiniText()">
            <span class="stringClosing"></span>
            <span class="caret"></span>
          </button>
          <ul role="menu" class="dropdown-menu dropdown-menu-right">
            <li class="J_tabShowActive" style="background-color: #fff;">
              <a class="navbar-minimalize" id="navMiniBar" href="#">
                <span class="stringCloseLeft"></span>
              </a>
            </li>
            <li class="divider"></li>
            <li class="J_tabCloseAll" style="background-color: #fff;">
              <a>
                <span class="stringCloseAllTabs"></span>
              </a>
            </li>
            <li class="J_tabCloseOther" style="background-color: #fff;">
              <a>
                <span class="stringCloseOtherTabs"></span>
              </a>
            </li>
          </ul>
        </div>
      </div>
      <div class="row J_mainContent bbg" id="content-main">
        <div class="J_box smartmenu index" width="100%" name="iframe0" data-id="main" height="100%" src="${ctx}/main"></div>
      </div>
      <div class="footer">
        <div class="my-pull-right">
          &copy; 2014-2017
          <a href="javascript:void(0);">Bleak.Tang</a>
        </div>
      </div>
    </div>
    <!--右侧部分结束-->
  </div>
</body>
<script type="text/javascript">
<c:set value="${param.lg}" var="lg" scope="session"/>

if ('${param.lg}' != '') {
  $('#lg').val('${param.lg}');
  if ('${param.lg}' == 'vi') {
    $('body').css('font-family', 'Times New Roman');
  }
} else {
  $('#lg').val('zh');
  var url = window.location.href;
  if (url.indexOf('?lg') != -1) {
    url = url.substring(0, url.indexOf('?lg'));
  }
  window.location.href = url + '?lg=' + $('#lg').val();
}

var underlingUser;

$.ajax({
  url: '${pageContext.request.contextPath}/pub/manager/findUnderlingTree',
  data : {},
  dataType: 'json',
  async: true,
  type: 'get'
}).done(function(response){
  if (response.success) {
    underlingUser = response.data;
  } else {
    crm.toastrsAlert({
      code:'warning',
      msg:$.i18n.prop('stringException')
    });
  }
});

//格式化金额  
//优化负数格式化问题  
function fmoney(s, n) {
  n = n > 0 && n <= 20 ? n : 2;
  f = s < 0 ? "-" : ""; //判断是否为负数  
  s = parseFloat((Math.abs(s) + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";//取绝对值处理, 更改这里n数也可确定要保留的小数位  
  var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
  t = "";
  for (i = 0; i < l.length; i++) {
    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
  }
  return f + t.split("").reverse().join("") + "." + r.substring(0, 2);//保留2位小数  如果要改动 把substring 最后一位数改动就可  
}

function changeNavBarMiniText() {
  if ($('body').hasClass('mini-navbar')) {
    $('.stringCloseLeft').text($.i18n.prop('stringOpenLeft'));
  } else {
    $('.stringCloseLeft').text($.i18n.prop('stringCloseLeft'));
  }
}

function selectLg() {
  var url = window.location.href;
  if (url.indexOf('?lg') != -1) {
    url = url.substring(0, url.indexOf('?lg'));
  }
  var lg = $('#lg').val();
  window.location.href = url + '?lg=' + lg;
}
</script>
</html>