]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<tags:message content="${message}"/>
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">用户基本信息</div>
		</div>
		<div class="portlet-body">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<tr><th>角色名称</th><th>归属机构</th><th>数据范围</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
				<c:forEach items="${list}" var="role">
					<tr>
						<td><a href="form?id=${role.id}">${role.name}</a></td>
						<td>${role.office.name}</td>
						<td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
						<shiro:hasPermission name="sys:role:edit"><td>
							<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
							<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
							<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
						</td></shiro:hasPermission>	
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>