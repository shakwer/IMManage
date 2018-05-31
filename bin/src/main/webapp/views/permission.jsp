<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@include file="public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="content">
		<div class="content-c">
			<div class="section">
				<div class="dt clearfix">
					<div class="dt-left clearfix">
						<span class="cur">用户列表</span>
						<div class="bottom-border"></div>
					</div>
				</div>
				<div class="section-c">
					<div class="outline clearfix">
						<!-- <div class="outline-item">
							<div class="left">城市：</div>
							<input class="input-sty1" style="width: 90px;" type="text"
								name="">
						</div> -->
						<div class="outline-item">
							<div class="left">城市：</div>
							<div class="select sty1">
								<div id="city_select" class="select-t">
									<span id="city_select_0">全部</span><i></i>
								</div>
								<div class="select-c" style="display: none;">
									<ul id="city_select_all" class="clearfix">
										<c:forEach items="${cities }" var="city">
											<li id="city_select_li_${city.getSysCityId() }">${city.getCityName()}</li>
										</c:forEach>
										<li id="city_select_li_0">全部</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="outline-item">
							<div class="left">角色：</div>
							<div class="select sty1">
								<div id="role_select" class="select-t">
									<span id="role_select_0">全部</span><i></i>
								</div>
								<div class="select-c" style="display: none;">
									<ul id="role_select_all" class="clearfix">
										<c:forEach items="${roles }" var="role">
											<li id="role_select_li_${role.getSysRoleId() }">${role.getRoleName()}</li>
										</c:forEach>
										<li id="role_select_li_0">全部</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="outline-item">
							<div class="left">邮箱：</div>
							<input id="email" class="input-sty1" style="margin-right: 0;"
								type="text" value="" placeholder="例如：lisi@fang.com">
						</div>
						<span id="find" class="btn"
							style="width: 50px; text-align: center;">查找</span> <span
							id="addUser" class="btn">添加用户</span>
					</div>
					<table class="tables1">
						<tr>
							<th>编号</th>
							<th>邮箱</th>
							<th>姓名</th>
							<th>角色</th>
							<th>城市</th>
							<th>添加时间</th>
							<th>最后登录时间</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${users}" var="user">
							<tr>
								<td>${user.getSysUserId() }</td>
								<td>${user.getEmail() }@fang.com</td>
								<td><a href="#">${user.getSysUserRealName() }</a></td>
								<td>${user.getSysRole().getRoleName() }</td>
								<td><c:forEach items="${user.getSysCities() }" var="city"
										varStatus="stat">
										${city.getCityName()}<c:if test="${stat.last==false }">,</c:if>
									</c:forEach></td>
								<td>${user.getCreateTime() }</td>
								<td>${user.getLastLoginTime() }</td>
								<td><img class="ibtn edit"
									src="<%=contextPath%>/resources/images/bianji.png" title="修改">
									<img class="ibtn delete delBtn"
									src="<%=contextPath%>/resources/images/delete.png" title="删除"></td>
							</tr>
						</c:forEach>
					</table>
					<div class="pageo clearfix">
						<div class="page">
							<span>共<i>${total }</i>条记录 <i>1</i>/${totalPage==0?1:totalPage }页（${pageSize }项/页）
							</span>
							<div id="pageNum" class="page-num clearfix">
								<div id="firstPage" class="sty1">首页</div>
								<div id="priviousPage" class="sty1">上一页</div>
								<c:choose>
									<c:when test="${totalPage>0 }">
										<c:choose>
											<c:when test="${totalPage-pageIndex+1<=3 }">
												<c:forEach begin="1"
													end="${(totalPage%3==0?3:totalPage%3)+1 }" var="stat">
													<c:if
														test="${totalPage-(totalPage%3==0?3:totalPage%3)+stat-1!=0 }">
														<div
															id="front_${totalPage-(totalPage%3==0?3:totalPage%3)+stat-1 }"
															<c:if test="${totalPage-(totalPage%3==0?3:totalPage%3)+stat-1==pageIndex}">class="cur"</c:if>>${totalPage-(totalPage%3==0?3:totalPage%3)+stat-1 }</div>
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach begin="${pageIndex }" end="${pageIndex+2 }"
													var="stat">
													<div id="front_${stat }"
														<c:if test="${stat==pageIndex}">class="cur"</c:if>>${stat }</div>
												</c:forEach>
												<div id="middlePage">…</div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<div class="cur">1</div>
									</c:otherwise>
								</c:choose>
								<div id="nextPage" class="sty1">下一页</div>
								<div id="lastPage" class="sty1">尾页</div>
							</div>
						</div>
					</div>
				</div>
				<!-- section-c end -->
			</div>
		</div>
		<!-- content-c end -->
	</div>
	<!-- content end -->

	<div id="alert-add" class="alert-box sty2 editBox">
		<div class="alert">
			<div class="top">
				<span class="top-h">添加用户详情</span> <span class="close"></span>
			</div>
			<div class="alert-con">
				<div class="input-item clearfix">
					<div class="left">电子邮箱：</div>
					<div class="right">
						<input id="email_add" class="input-sty4" type="text"
							placeholder="例如：zhangsan" value="">
					</div>
				</div>
				<div class="input-item clearfix">
					<div class="left">真实姓名：</div>
					<div class="right">
						<input id="realName_add" class="input-sty4" type="text"
							placeholder="例如：张三" value="">
					</div>
				</div>
				<div class="input-item clearfix">
					<div class="left">角色选择：</div>
					<div class="select sty2">
						<div id="role_add" class="select-t">
							<span id=""></span><i></i>
						</div>
						<div class="select-c" style="display: none;">
							<ul class="clearfix">
								<c:forEach items="${roles }" var="role">
									<li id="role_add_li_${role.getSysRoleId() }">${role.getRoleName()}</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="input-item clearfix" style="margin-bottom: 0;">
					<div>选择开通城市：</div>
					<div id="" style="margin-top: 15px;">
						<c:forEach items="${cities }" var="city" varStatus="status">
							<input name="city_add" type="checkbox" disabled="disabled"
								value="${city.getSysCityId() }">${city.getCityName()}
							<c:if test="${(status.index+1)%6==0 }">
								<br>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<%-- <div class="left">城市配置：</div>
				<div class="select sty2">
					<div id="city_add" class="select-t">
						<span id=""></span><i></i>
					</div>
					<div class="select-c" style="display: none;">
						<ul class="clearfix">
							<c:forEach items="${cities }" var="city">
								<li id="city_add_li_${city.getSysCityId() }">${city.getCityName()}</li>
							</c:forEach>
							<li id="city_add_li_0">全部</li>
						</ul>
					</div>
				</div> --%>
			</div>
			<!-- alert-con end -->
			<div class="btn1-o">
				<a id="addUserAction" class="btn1">添加用户</a> <a
					class="btn1-s editCancel" style="margin-left: 19px;">取消</a>
			</div>
		</div>
	</div>
	<!-- alert-box-add end -->
	<div id="alert-edit" class="alert-box sty2 editBox">
		<input id="userId" type="hidden">
		<div class="alert">
			<div class="top">
				<span class="top-h">修改用户详情</span> <span class="close"></span>
			</div>
			<div class="alert-con">
				<div class="input-item clearfix">
					<div class="left">电子邮箱：</div>
					<div class="right">
						<input id="email_update" disabled="disabled" class="input-sty4"
							type="text" placeholder="例如：zhangsan" value="">
					</div>
				</div>
				<div class="input-item clearfix">
					<div class="left">真实姓名：</div>
					<div class="right">
						<input id="realName_update" class="input-sty4" type="text"
							placeholder="例如：张三" value="">
					</div>
				</div>
				<div class="input-item clearfix">
					<div class="left">角色选择：</div>
					<div class="select sty2">
						<div id="role_update" class="select-t">
							<span id="role_update_selected"></span><i></i>
						</div>
						<div class="select-c" style="display: none;">
							<ul class="clearfix">
								<c:forEach items="${roles }" var="role">
									<li id="role_update_li_${role.getSysRoleId() }">${role.getRoleName()}</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="input-item clearfix">
					<div>已开通城市(单击可删除,超级管理员除外)：</div>
					<div id="city_haveOpen" style="margin-top: 15px;"></div>
				</div>
				<div class="input-item clearfix">
					<div>可开通城市：</div>
					<div id="city_canAdd" style="margin-top: 15px;"></div>
				</div>
			</div>
			<!-- alert-con end -->
			<div class="btn1-o">
				<a id="updateUserAction" class="btn1">保存修改</a> <a
					class="btn1-s editCancel" style="margin-left: 19px;">取消</a>
			</div>
		</div>
	</div>
	<!-- alert-box-edit end -->
	<div class="alert-box sty2 delPersonBox">
		<input type="hidden" id="user_delete">
		<div class="alert">
			<div class="top">
				<span class="top-h">提示</span> <span class="close"></span>
			</div>
			<div class="alert-con" style="padding: 20px 50px 5px;">
				<p
					style="padding: 30px 0; text-align: center; line-height: 22px; font-size: 16px; color: #333;">确认要删除该用户吗？</p>
			</div>
			<!-- alert-con end -->
			<div class="btn1-o">
				<a class="btn1 delPersonConfirm">确定</a>
			</div>
		</div>
	</div>
	<!-- alert-box end -->
	<script type="text/javascript">
		var totalPage = "${totalPage}";
		var pageSize = "${pageSize}";
		var currentIndex = "${pageIndex}";
		var email = "${email}";
		var roleId = "${roleId}";
		var cityId = "${cityId}";
		var allRoleIds = [];
		var allCityIds = [];
		isSelected(4);
	</script>
	<script type="text/javascript"
		src="<%=contextPath%>/resources/js/qxguanli.js"></script>
</body>
</html>