<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>环境管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/crv/computer/">环境管理列表</a></li>
		<li class="active"><a href="${ctx}/crv/computer/form?id=${computer.id}">环境管理<shiro:hasPermission name="crv:computer:edit">${not empty computer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="crv:computer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="computer" action="${ctx}/crv/computer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">comp_id：</label>
			<div class="controls">
				<form:input path="compId" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<%-- <label class="control-label">姓名：</label>
			<sys:treeselect id="user" name="user.id" value="${hrAudit.user.id}" labelName="user.name" labelValue="${hrAudit.user.name}" 
							title="用户" url="/sys/office/treeData?type=3" cssClass="required recipient" cssStyle="width:150px" 
							allowClear="true" notAllowSelectParent="true" smallBtn="false"/> --%>
		<div class="control-group">
			<label class="control-label">comp_name：</label>
			<div class="controls">
				<form:input path="compName" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">comp_type：</label>
			<div class="controls">
				<form:input path="compType" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">comp_describe：</label>
			<div class="controls">
				<form:input path="compDescribe" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="crv:computer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>