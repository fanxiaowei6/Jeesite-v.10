<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
	$(document).ready(function() {
		var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data = ${fns:toJson(list)}, rootId = "0";
		addRow("#treeTableList", tpl, data, rootId, true);
		$("#treeTable").treeTable({expandLevel : 5});
	});
	function addRow(list, tpl, data, pid, root){
		for (var i=0; i<data.length; i++){
			var row = data[i];
			if ((${fns:jsGetVal('row.parentId')}) == pid){
				$(list).append(Mustache.render(tpl, {
					crvDict: {
						//type: getCrvDictLabel(${fns:toJson(fns:getCrvDictList('sys_area_type'))}, row.type)
						type: getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))}, row.type)
					}, pid: (root?0:pid), row: row
				}));
				addRow(list, tpl, data, row.id);
			}
		}
	}
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/crv/crvArea/list">区域管理列表</a></li>
		<shiro:hasPermission name="crv:crvArea:edit"><li><a href="${ctx}/crv/crvArea/form">区域管理添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>区域名称</th><th>区域编码</th><th>区域类型</th><th>备注信息</th><shiro:hasPermission name="crv:crvArea:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
		
		<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/crv/crvArea/form?id={{row.id}}">{{row.name}}</a></td>
			<td>{{row.code}}</td>
			<td>{{crvDict.type}}</td>
			<td>{{row.remarks}}</td>
			<shiro:hasPermission name="crv:crvArea:edit"><td>
				<a href="${ctx}/crv/crvArea/form?id={{row.id}}">修改</a>
				<a href="${ctx}/crv/crvArea/delete?id={{row.id}}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)">删除</a>
				<a href="${ctx}/crv/crvArea/form?parent.id={{row.id}}">添加下级区域</a> 
			</td></shiro:hasPermission>
		</tr>
		</script>
</body>
</html>