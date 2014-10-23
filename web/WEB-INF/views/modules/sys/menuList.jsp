<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<style type="text/css">.table td i{margin:0 2px;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 2});
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<tags:message content="${message}"/>
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">菜单管理</div>
		</div>
		<div class="portlet-body">
			<form id="listForm" method="post">
				<table id="treeTable" class="table table-striped table-bordered table-condensed">
					<tr><th>名称</th><th>链接</th><th style="text-align:center;">排序</th><th>可见</th><th>权限标识</th><shiro:hasPermission name="sys:menu:edit"><th>操作</th></shiro:hasPermission></tr>
					<c:forEach items="${list}" var="menu">
						<tr id="${menu.id}" pId="${menu.parent.id ne '1' ? menu.parent.id : '0'}">
							<td><i class="icon-${not empty menu.icon ? menu.icon : 'hide'}"></i><a href="javascript:;" onclick="treeTableTrtoggle('${menu.id}')">${menu.name}</a></td>
							<td>${menu.href}</td>
							<td style="text-align:center;">
								<shiro:hasPermission name="sys:menu:edit">
									<input type="hidden" name="ids" value="${menu.id}"/>
									<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
								</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
									${menu.sort}
								</shiro:lacksPermission>
							</td>
							<td>${menu.isShow eq '1' ? '显示' : '隐藏'}</td>
							<td>${menu.permission}</td>
							<shiro:hasPermission name="sys:menu:edit"><td>
								<a href="${ctx}/sys/menu/form?id=${menu.id}">修改</a>
								<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
								<a href="${ctx}/sys/menu/form?parent.id=${menu.id}">添加下级菜单</a> 
							</td></shiro:hasPermission>
						</tr>
					</c:forEach>
				</table>
				<shiro:hasPermission name="sys:menu:edit"><div class="form-actions pagination-left">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
				</div></shiro:hasPermission>
			 </form>
		</div>
	</div>
</body>
</html>
