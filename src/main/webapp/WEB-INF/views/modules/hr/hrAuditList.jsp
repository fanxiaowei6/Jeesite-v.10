<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hr/audit/">审批列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="hrAudit" action="${ctx}/hr/hrAudit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>姓名：</label><sys:treeselect id="user" name="user.id" value="${hrAudit.user.id}" labelName="user.name" labelValue="${hrAudit.user.name}" 
			title="用户" url="/sys/office/treeData?type=3" cssStyle="width:150px" allowClear="true" notAllowSelectParent="true"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>部门</th><th>岗位职级</th><th>调整原因</th><th>申请时间</th>
		<shiro:hasPermission name="hr:audit:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="hrAudit">
			<tr>
				<td><a href="${ctx}/hr/audit/form?id=${hrAudit.id}">${hrAudit.user.name}</a></td>
				<td>${hrAudit.office.name}</td>
				<td>${hrAudit.post}</td>
				<td>${hrAudit.content}</td>
				<td><fmt:formatDate value="${hrAudit.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="hr:hrAudit:edit"><td>
    				<a href="${ctx}/hr/audit/form?id=${hrAudit.id}">详情</a>
					<a href="${ctx}/hr/audit/delete?id=${hrAudit.id}" onclick="return confirmx('确认要删除该审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
