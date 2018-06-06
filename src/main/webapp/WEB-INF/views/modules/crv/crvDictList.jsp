<%@ page contentType="text/html;charset=UTF-8"   pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/crv/crvDict/">数据字典列表</a></li>
		<shiro:hasPermission name="crv:crvDict:edit"><li><a href="${ctx}/crv/crvDict/form">数据字典添加</a></li></shiro:hasPermission>
	</ul>
	
	<form:form id="searchForm" modelAttribute="crvDict" action="${ctx}/crv/crvDict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<label>类型：
			</label>
				<form:select id="type" path="type" class="input-medium"><form:option value="" label=""/><form:options items="${typeList}" htmlEscape="false"/>
			</form:select>
		&nbsp;&nbsp;<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>键值</th><th>标签</th><th>类型</th><th>描述</th><th>排序</th><shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="crvDict">
		<tr>
				<td>${crvDict.value}</td>
				<td><a href="${ctx}/crv/crvDict/form?id=${crvDict.id}">${crvDict.label}</a></td>
				<td><a href="javascript:" onclick="$('#type').val('${crvDict.type}');$('#searchForm').submit();return false;">${crvDict.type}</a></td>
				<td>${crvDict.description}</td>
				<td>${crvDict.sort}</td>
				<shiro:hasPermission name="crv:crvDict:edit"><td>
    				<a href="${ctx}/crv/crvDict/form?id=${crvDict.id}">修改</a>
					<a href="${ctx}/crv/crvDict/delete?id=${crvDict.id}&type=${crvDict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
    				<a href="<c:url value='${fns:getAdminPath()}/crv/crvDict/form?type=${crvDict.type}&sort=${crvDict.sort+10}'><c:param name='description' value='${crvDict.description}'/></c:url>">添加键值</a>
				</td></shiro:hasPermission>
			</tr>
			<%-- <tr>
				<td><a href="${ctx}/crv/crvDict/form?id=${crvDict.id}">
					<fmt:formatDate value="${crvDict.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${crvDict.remarks}
				</td>
				<shiro:hasPermission name="crv:crvDict:edit"><td>
    				<a href="${ctx}/crv/crvDict/form?id=${crvDict.id}">修改</a>
					<a href="${ctx}/crv/crvDict/delete?id=${crvDict.id}" onclick="return confirmx('确认要删除该数据字典吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr> --%>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>