<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="row-fluid">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">用户基本信息</div>
				</div>
				<div class="portlet-body">
					<div class="row-fluid">
						<div class="control-group">
							<div class="span6">
								<label class="control-label">归属公司:</label>
								<div class="controls">
					                <tags:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
										title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
								</div>
							</div>
							<div class="span6">
								<label class="control-label">登录名:</label>
								<div class="controls">
									<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
									<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="control-group">
							<div class="span6">
								<label class="control-label">工号:</label>
								<div class="controls">
									<form:input path="cardNo" htmlEscape="false" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="span6">
								<label class="control-label">姓名:</label>
								<div class="controls">
									<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="control-group">
							<div class="span6">
								<label class="control-label">密码:</label>
								<div class="controls">
									<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
									<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
								</div>
							</div>
							<div class="span6">
								<label class="control-label">确认密码:</label>
								<div class="controls">
									<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="control-group">
							<div class="span6">
								<label class="control-label">邮箱:</label>
								<div class="controls">
									<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
								</div>
							</div>
							<div class="span6">
								<label class="control-label">电话:</label>
								<div class="controls">
									<form:input path="phone" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="control-group">
							<div class="span6">
								<label class="control-label">手机:</label>
								<div class="controls">
									<form:input path="mobile" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="span6">
								<label class="control-label">用户类型:</label>
								<div class="controls">
									<form:select path="userType">
										<form:option value="" label="请选择"/>
										<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</div>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">用户角色:</label>
						<div class="controls" style="margin-top: 8px;">
							<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">备注:</label>
						<div class="controls">
							<form:textarea path="remarks" htmlEscape="false" rows="2" maxlength="200" cssStyle="width:600px;"/>
						</div>
					</div>
					<c:if test="${not empty user.id}">
					<div class="row-fluid">
						<div class="control-group">
							<div class="span6">
								<label class="control-label">创建时间:</label>
								<div class="controls">
									<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
								</div>
							</div>
							<div class="span6">
								<label class="control-label">最后登陆:</label>
								<div class="controls">
									<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
								</div>
							</div>
						</div>
					</div>
					</c:if>
					<div class="form-actions">
						<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginName").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
</body>
</html>