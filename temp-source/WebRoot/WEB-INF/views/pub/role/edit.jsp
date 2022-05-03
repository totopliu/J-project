<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crm" uri="/WEB-INF/list.tld"%>
<script type="text/javascript">
	loadProperties();
	$('.stringRoleName').html($.i18n.prop('stringRoleName'));
	$('.stringRoleCode').html($.i18n.prop('stringRoleCode'));
	$('.stringRoleAuthority').html($.i18n.prop('stringRoleAuthority'));
	$('.stringSelectAllNone').html($.i18n.prop('stringSelectAllNone'));
	$('.stringRoleDefaultPermissions').html($.i18n.prop('stringRoleDefaultPermissions'));
	$('.stringRoleYes').html($.i18n.prop('stringRoleYes'));
	$('.stringRoleNo').html($.i18n.prop('stringRoleNo'));
	$('.stringMenuRemark').html($.i18n.prop('stringMenuRemark'));
	
	$(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
 		var operates = [
		                <c:forEach var="m" varStatus="n" items="${ops}">
		                <c:if test="${n.index != 0}">,</c:if> 
		                  '${m.op_id}'
		               </c:forEach>
		];
 		
 		
		$(function() {
			console.info(operates);
			IDMark_A = "_a";
			/* 初始化功能树 */
			//ztree树  view回调方法 ：能够自定义树 追加内容
			$.fn.zTree.init($('#menuTree'), {
				data : {
					simpleData : {
						enable : true
					}
				},
				view: {
					addDiyDom: function(treeId, treeNode) {
						var aObj = $("#" + treeNode.tId + IDMark_A);
						var html = [];
						for(var i=0;i<treeNode.operates.length;i++) {
							var op = treeNode.operates[i];
							var span = "<font style='margin-top:3px;'>"+op.name+"</font>";
							html.push(" <label><input class=\"operates\" type=\"checkbox\" name=\"operates\" value=\""+op.id+"\""+($.inArray(op.id,operates)!=-1?" checked":"")+"/>"+span+"</label>");
						}
						aObj.after(html.join(""));
					}
				},
				callback : {
					onClick : function(event, treeId, treeNode, clickFlag) {
						//
					}
				}
			}, [ 
			<c:forEach varStatus="in" var="m" items="${menus}">
			  <c:if test="${in.index != 0}">,</c:if>{
				    "id" : '${m.id}',
					"pId" : '${m.pId}',
					"name" : "${m.name}",
					"open" : true,
					"operates" : [
					    <c:forEach varStatus="oi" var="o" items="${m.operates}">
					    <c:if test="${oi.index != 0}">,</c:if>
					    {"id" : '${o.id}', "name" : '${o.name}'}
						</c:forEach>   
					]
				}
			 </c:forEach>    
			]);
			crm.admin.menu${OP.menu}.save = function(obj) {
		  		if($("#menu${OP.menu}Form").valid()){
		  			crm.ajaxJson({url:rootPath + "/op_save_${OP.menu}",data:$('#menu${OP.menu}Form').serializeArray()},function(){
		  				crm.closeWindow();
						$('#admin_menu${OP.menu}_datagrid').bootstrapTable('refresh');
		    		});
		  		}
			}
	});
</script>
<div class="ibox float-e-margins">
	<div class="ibox-content">
		<form class="form-horizontal m-t required-validate"
			id="menu${OP.menu}Form" action="op_save_${OP.menu}.action"
			method="post">
			<input type="hidden" name="id" value="${dto.id}" />
			<div class="form-group">
				<label class="col-sm-3 control-label"><span class="stringRoleName"></span>：</label>
				<div class="col-sm-8">
					<input name="name" class="form-control" value="${dto.name}" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><span class="stringRoleCode"></span>：</label>
				<div class="col-sm-8">
					<c:choose>
						<c:when test="${empty dto.id}">
							<input name="code" class="form-control" value="${dto.code}" type="text">
						</c:when>
						<c:otherwise>
							<span style="display: block; margin-top: 6px;">${dto.code}</span>
							<input type="hidden" name="code" class="formText"
								value="${dto.code}" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="form-group">
                 <label class="col-sm-3 control-label"><span class="stringRoleAuthority"></span>：</label>
                 <div class="col-sm-8" >
                     <div  style="width: 850px; height:auto ; overflow: auto; border-width: 1px; border-color: #ccc; border-style: solid; padding: 1px;">
						<div id="menuTree" class="ztree"></div>
					</div>
					<label><input id="checkAll"  onclick="javascript:$('.operates').prop('checked',($(this).prop('checked') ? true : false ));"   type="checkbox" /><span class="stringSelectAllNone"></span> </label>
				</div>
            </div>
            <div class="form-group">
            	<label class="col-sm-3 control-label"><span class="stringRoleDefaultPermissions"></span>：</label>
            	<div class="col-sm-8">
					<div class="radio i-checks radio-inline">
						<label>
							<input type="radio" <c:if test="${dto.defaultState == 1}"> checked</c:if> value="1" name="defaultState">
							<i></i> <span class="stringRoleYes"></span>
						</label>
					</div>
					<div class="radio i-checks radio-inline">
						<label>
							<input type="radio" <c:if test="${dto.defaultState == 0}"> checked</c:if> value="0" name="defaultState"> 
							<i></i> <span class="stringRoleNo"></span>
						</label>
					</div>
				</div>
            </div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><span class="stringMenuRemark"></span>：</label>
				<div class="col-sm-8">
					<textarea id="ccomment" name="remark" cols="55" rows="3" class="form-control">${dto.remark}</textarea>
				</div>
			</div>
		</form>
	</div>
</div>

