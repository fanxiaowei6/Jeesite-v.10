<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>环境管理管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/crv/computer/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
	</script>
</head>
<body>
<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/crv/computer/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/crv/computer/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/crv/computer/">环境管理列表</a></li>
		<shiro:hasPermission name="crv:computer:edit">
			<li><a href="${ctx}/crv/computer/form">环境管理添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="computer"
		action="${ctx}/crv/computer/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<label>电脑编号：</label>
		<form:input path="compId" />
		<label>电脑名称：</label>
		<form:input path="compName" />
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
		<input type="button" value="导入" id="btnImport" class="btn btn-primary">
		<input type="button" value="导出" id="btnExport" class="btn btn-primary">

	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>comp_id</th>
				<th>comp_name</th>
				<th>comp_type</th>
				<th>create_date</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="crv:computer:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="computer">
				<tr>
					<td>${computer.compId }</td>
					<td>${computer.compName }</td>
					<td>${computer.compType }</td>
					<td><fmt:formatDate value="${computer.createDate }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<!-- 
				<a href="${ctx}/crv/computer/form?id=${computer.id}">
				 --> <fmt:formatDate value="${computer.updateDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${computer.remarks}</td>
					<shiro:hasPermission name="crv:computer:edit">
						<td><a href="${ctx}/crv/computer/form?id=${computer.id}">修改</a>
							<a href="${ctx}/crv/computer/delete?id=${computer.id}"
							onclick="return confirmx('确认要删除该环境管理吗？', this.href)">删除</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>