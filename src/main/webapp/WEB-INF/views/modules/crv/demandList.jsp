<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>需求分析管理</title>
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
		<li class="active"><a href="${ctx}/crv/demand/">需求分析列表</a></li>
		<shiro:hasPermission name="crv:demand:edit"><li><a href="${ctx}/crv/demand/form">需求分析添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="demand" action="${ctx}/crv/demand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		
			<label>产品名称：</label><form:input path="deName" htmlEscape="false" maxlength="50" class="input-medium"/>
		
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		
		
	<!-- 	<ul class="ul-form">
			<li>
				产品名称：<input  type="text" id="de_name" name="de_name">
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	 -->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>ID</th>
				<th>名称</th>
				<th>类型</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注</th>
				
				<shiro:hasPermission name="crv:demand:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="demand">
			<tr>
				<td>${demand.id }</td>
				<td>${demand.deName }</td>
				<td>${demand.deType }</td>
				<td>
					<fmt:formatDate value="${demand.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td><a href="${ctx}/crv/demand/form?id=${demand.id}">
					<fmt:formatDate value="${demand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td width="40px" height="15px">${demand.remarks }</td>
				<shiro:hasPermission name="crv:demand:edit">
					<td>
    				<a href="${ctx}/crv/demand/form?id=${demand.id}">修改</a>
					<a href="${ctx}/crv/demand/delete?id=${demand.id}" onclick="return confirmx('确认要删除该需求分析吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>