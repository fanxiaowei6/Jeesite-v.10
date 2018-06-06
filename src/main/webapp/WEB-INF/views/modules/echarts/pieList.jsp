<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
<title>统计</title>
<meta name="decorator" content="default" />
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
<body class="gray-bg">
	<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
	<div class="wrapper wrapper-content">
		<div id="pie" class="main000"></div>
		<echarts:pie id="pie" title="搜索引擎统计" subtitle="访问统计"
			orientData="${orientData}" />
		<div class="ibox">

			<div class="ibox-content">
				<sys:message content="${message}" />


				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="pie"
							action="${ctx}/echarts/pie/" method="post" class="form-inline">
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
					<!--  -->
					<div class="col-sm-12">
						<div class="pull-left" style="padding-left: 10px;">

							<!-- 增加按钮 -->
							<table:addRow url="${ctx}/echarts/pie/form" title="搜索引擎"></table:addRow>

							<!-- 删除按钮 -->
							<table:delRow url="${ctx}/echarts/pie/deleteAll"
								id="contentTable"></table:delRow>

							<!-- 编辑按钮 -->

							<table:editRow url="${ctx}/echarts/pie/form" title="搜索引擎"
								id="contentTable"></table:editRow>

							<!-- 导入按钮 -->
							<table:importExcel url="${ctx}/echarts/pie/import"></table:importExcel>


							<!-- 导出按钮 -->
							<table:exportExcel url="${ctx}/echarts/pie/export"></table:exportExcel>



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
							<th class="sort-column className">搜索引擎</th>
							<th class="sort-column num">访问量</th>
							<th class="sort-column remarks">备注信息</th>
							<th>操作</th>
						</tr>
					</thead>
				<tbody>
						<c:forEach items="${page.list}" var="pie">
							<tr>
								<td><input type="checkbox" id="${pie.id}"
									class="i-checks"></td>
								<td><a href="#"
									onclick="openDialogView('查看年产值报表', '${ctx}/echarts/pie/form?id=${pie.id}','800px', '500px')">
								
										${pie.name }
								</a></td>
								<td>${pie.value}</td>
								<td>${pie.remarks}</td>
								<td width="200px">
										<a href="#"
											onclick="openDialogView('查看年产值报表', '${ctx}/echarts/pie/form?id=${pie.id}','800px', '500px')"><i
											class="fa fa-search-plus"></i> 查看</a>
								
										<a href="#"
											onclick="openDialog('修改年产值报表', '${ctx}/echarts/pie/form?id=${pie.id}','800px', '500px')"><i
											class="fa fa-edit"></i> 修改</a>
								
										<a href="${ctx}/echarts/pie/delete?id=${pie.id}"
											onclick="return confirmx('确认要删除该年产值报表吗？', this.href)"
											><i class="fa fa-trash"></i>
											删除</a>
							
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<%-- <table:page page="${page}"></table:page> --%>
				<div class="pagination">${page}</div>

				<br /> <br />
			</div>
		</div>
	</div>

</body>
</html>