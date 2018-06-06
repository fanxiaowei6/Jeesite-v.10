<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>年产值报表管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body class="gray-bg">
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
	<div class="wrapper wrapper-content">
		<div id="production" class="main000"></div>
		<echarts:production id="production" title="年产值报表列表" subtitle="报表统计"
			orientData="${orientData}" />
		<div class="ibox">
			
			<div class="ibox-content">
				<sys:message content="${message}" />

				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="production"
							action="${ctx}/yaer/production/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<span style="padding-left: 20px;">搜索引擎：</span>
								<form:input path="name" htmlEscape="false" maxlength="64"
									class=" form-control input-sm" />
							</div>
						</form:form>
						<br />
					</div>
				</div>

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left" style="padding-left:15px;">
						
								<table:addRow url="${ctx}/yaer/production/form" title="年产值报表"></table:addRow>
								<!-- 增加按钮 -->
						
								<table:editRow url="${ctx}/yaer/production/form" title="年产值报表"
									id="contentTable"></table:editRow>
								<!-- 编辑按钮 -->
							
								<table:delRow url="${ctx}/yaer/production/deleteAll"
									id="contentTable"></table:delRow>
								<!-- 删除按钮 -->
							
								<table:importExcel url="${ctx}/yaer/production/import"></table:importExcel>
								<!-- 导入按钮 -->
						
								<table:exportExcel url="${ctx}/yaer/production/export"></table:exportExcel>
								<!-- 导出按钮 -->
							
							<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="sortOrRefresh()" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>

						</div>
						<div class="pull-right">
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="reset()">
								<i class="fa fa-refresh"></i> 重置
							</button>
						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="i-checks"></th>
							<th class="sort-column updateDate">搜索引擎</th>
							<th class="sort-column name">访问量</th>
							<th class="sort-column remarks">备注信息</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="production">
							<tr>
								<td><input type="checkbox" id="${production.id}"
									class="i-checks"></td>
								<td><a href="#"
									onclick="openDialogView('查看年产值报表', '${ctx}/yaer/production/form?id=${production.id}','800px', '500px')">
										<%-- <fmt:formatDate value="${production.name}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
										${production.name }
								</a></td>
								<td>${production.value}</td>
								<td>${production.remarks}</td>
								<td width="200px">
										<a href="#"
											onclick="openDialogView('查看年产值报表', '${ctx}/yaer/production/form?id=${production.id}','800px', '500px')"><i
											class="fa fa-search-plus"></i> 查看</a>
								
										<a href="#"
											onclick="openDialog('修改年产值报表', '${ctx}/yaer/production/form?id=${production.id}','800px', '500px')"><i
											class="fa fa-edit"></i> 修改</a>
								
										<a href="${ctx}/yaer/production/delete?id=${production.id}"
											onclick="return confirmx('确认要删除该年产值报表吗？', this.href)"
											><i class="fa fa-trash"></i>
											删除</a>
							
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
			
				<div class="pagination">${page}</div>
				<br /> <br />
			</div>
		</div>
	</div>
</body>
</html>