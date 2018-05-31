<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
  String contextPath = (String) request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>im群聊管理系统</title>
<script type="text/javascript"
	src="<%=contextPath%>/resources/js/jquery.min.js"></script>
<script type="text/javascript">
var contextPath="<%=contextPath%>";
	$(function() {
		$("#quit").on('click', function() {
			$.post(contextPath + "/loginout.do", {}, function(result) {
				if (result == "success") {
					window.location.href = "http://work.fang.com/v2/login/loginAct.do?method=login";
				}
			});
			return false;
		})
	});
</script>
</head>
<body>
	<h3>首页</h3>
	<shiro:hasRole name="超级管理员">
	<h1>我是超级管理员</h1>
	<shiro:hasPermission name="community:select:0000001">
	               <h3>可以查询0000001</h3>
	</shiro:hasPermission>
	<shiro:hasPermission name="community:update:0000001">
                   <h3>可以更新0000001</h3>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:update:1">
                   <h3>可以更新用户：1(靳石磊)</h3>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:update:2">
                   <h3>可以更新用户：2(赵泽乐)</h3>
    </shiro:hasPermission>
	</shiro:hasRole>

	<shiro:hasRole name="管理员">
	   <h1>我是管理员</h1>
	<shiro:hasPermission name="user:update:3">
                   <h3>可以更新用户：3(李丁彪)</h3>
    </shiro:hasPermission>
    <shiro:hasPermission name="community:update:0000001">
                   <h3>可以更新0000001</h3>
    </shiro:hasPermission>
	</shiro:hasRole>
	<shiro:hasRole name="普通群主">
       <h1>我是普通群主</h1>
    <shiro:hasPermission name="user:update:3">
                   <h3>可以更新用户：3(李丁彪)</h3>
    </shiro:hasPermission>
     <shiro:hasPermission name="community:update:0000001">
                   <h3>可以更新：0000001</h3>
    </shiro:hasPermission>
    </shiro:hasRole>

	<a id="quit" href="#">退出</a>
</body>
</html>