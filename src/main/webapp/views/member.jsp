<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@include file="public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成员管理</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="content">
		<div class="content-c">
			<div class="section">
				<div style="color: #df3031;font-weight: 600;font-size: 14px;"><a href=#” onClick="javascript :history.back(-1);" style="color:#df3031"><img src="<%=contextPath%>/resources/images/right_arr.png"/> 成员列表</a></div>
				<div id="groupinfo" class="dt clearfix" style="top:-27px;margin: 0 0 0 85px;width: 1050px;">
					<div id="changeMemberBtn" class="btn4 switchQunBtn">切换群</div>
					<div class="dt-left clearfix" style="width: 840px;">
						<span id="groupinfo_id" style="text-align: center;display: block;width: 100%;cursor: auto" class="cur"></span>
					</div>
					<div class="abs-r">
						群成员人数：<span id="communityCount"></span>/<span id="communityLimit"></span>
					</div>
				</div>
				<div class="section-c">
					<div class="outline clearfix">
						<span id="inviteMember" class="btn addPersonBtn"><i></i>邀请成员</span> <span id="removeMemberBtn"
							class="btn-sty1 delBtn">删除成员</span>
						<div class="outline-right">
							<input id="searchKey" class="input-sty1" type="text" name="" placeholder="搜索关键词"> <span
								id="searchMemberOut" class="btn" style="margin-right: 0; padding: 0 20px;">搜索</span> <span
								class="btn-sty1 moreChoice" style="margin-left: 15px; margin-right: 0; padding: 0 5px;">更多筛选<i></i></span>
							<div class="sx-box">
								<div class="lit-cover"></div>
								<div class="sx-box-item clearfix">
									<div class="left">信用：</div>
									<div id="creditOption" class="right clearfix">
										<span val="0" class="cur">不限</span> <span val="1">不良记录成员</span>
									</div>
								</div>
								<div class="sx-box-item clearfix">
									<div class="left">入群时间：</div>
									<div id="addtimeOption" class="right clearfix">
										<span val="0" class="cur">不限</span> <span val="1">1个月以内</span> <span val="2">1-3个月以内</span> <span val="3">3-6个月以内</span>
										<span val="4">6-12个月以内</span> <span val="5">12个月以上</span> <span val="6">根据自选时间</span> <input
											id="addstarttimebtn" type="text" style="width: 85px;" placeholder="开始时间"> <span
											style="margin-right: 2px;">-</span> <input id="addendtimebtn" type="text" style="width: 85px;"
											placeholder="结束时间">
									</div>
								</div>
								<div class="sx-box-item clearfix" style="margin-top: 9px;">
									<div class="left">最后发言：</div>
									<div id="lastmessageOption" class="right clearfix">
										<span val="0" class="cur">不限</span> <span val="1">1个月以内</span> <span val="2">1-3个月以内</span> <span val="3">3-6个月以内</span>
										<span val="4">6-12个月以内</span> <span val="5">12个月以上</span><span val="6">根据自选时间</span><input
											id="messagestarttimebtn" type="text" style="width: 85px;" placeholder="开始时间"> <span
											style="margin-right: 2px;">-</span> <input id="messageendtimebtn" type="text" style="width: 85px;"
											placeholder="结束时间">
									</div>
								</div>
								<div class="sx-box-item special clearfix">
									<div class="left" style="position: relative; top: 3px;">筛选条件：</div>
									<div class="right clearfix">
										<span id="searchOptions" style="border: 0"></span>
										<div id="clearOptions" class="btn5">清除</div>
										<div id="submitOptions" class="btn6 sxConfirm">确定</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<table id="memberTable" class="tables1">
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th class="special">成员(昵称)</th>
							<th>群名片</th>
							<th>IM账号</th>
							<th>性别</th>
							<th>入群时间<img src="<%=contextPath%>/resources/images/xx.jpg"></th>
							<th>最后发言<img src="<%=contextPath%>/resources/images/xx.jpg"></th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</table>
					<div class="pageo clearfix">
						<div class="page">
							<span>共<i id="pageTotal"></i>条记录 <i id="pageIndex"></i>/<span id="pageSize">10</span>页 (20项/页)
							</span>
							<div id="pageBar" class="page-num clearfix"></div>
						</div>
					</div>
				</div>
				<!-- section-c end -->
			</div>
		</div>
		<!-- content-c end -->
	</div>
	<!-- content end -->
	<div class="alert-box sty4 switchQunBox">
		<div class="alert" style="padding-bottom: 30px;">
			<div class="top">
				<span class="top-h">选择群</span> <span id="swhclose" class="close"></span>
			</div>
			<div class="alert-con">
				<div class="tab-line clearfix">
					<input id="searchCommunityText" class="input-sty3" type="text" name="" placeholder="选择群名或者群ID">
					<div id="searchCommunity" class="btn3">搜索</div>
				</div>
				<div class="xzqun">
					<div class="xzqun-t">
						我管理的群(<span id="pageTotalChange2"></span>)
					</div>
					<div id="communityList" class="xzqun-c clearfix"></div>
					<div class="page">
						<span>共<i id="pageTotalChange"></i>条记录 <i id="pageIndexChange">1</i>/<span id="pageSizeChange">1</span>页
							(6项/页)
						</span>
						<div id="changePageBar" class="page-num clearfix"></div>
					</div>
				</div>

			</div>
			<!-- alert-con end -->
		</div>
	</div>
	<!-- alert-box end -->

	<!-- <div class="alert-box sty2 delPersonBox">
		<div class="alert">
			<div class="top">
				<span class="top-h">提示</span> <span class="close"></span>
			</div>
			<div class="alert-con" style="padding: 20px 50px 5px;">
				<p style="padding: 30px 0 15px; line-height: 22px; font-size: 14px; color: #333;">确定要将朱竹梅、小云、大兴等3位成员从本群中删除吗？</p>
				<i class="check"></i> <span class="check-text">不再接收她们加群申请</span>
			</div>
			alert-con end
			<div class="btn1-o">
				<a class="btn1 delPersonConfirm">确定</a>
			</div>
		</div>
	</div> -->
	<!-- alert-box end -->
	<div id="jyBtn" class="alert-box sty2 jyBox">
		<div class="alert">
			<div class="top">
				<span class="top-h">禁言</span> <span class="close"></span>
			</div>
			<div class="alert-con">
				<div class="jy-item clearfix">
					<div class="jy-item-left">选择禁言时间：</div>
					<div class="jy-item-right">
						<div class="select">
							<div class="select-t">
								<span id="jytime">10分钟</span><i></i>
							</div>
							<div class="select-c">
								<ul class="clearfix">
									<li>10分钟</li>
									<li>20分钟</li>
									<li>30分钟</li>
									<li>40分钟</li>
									<li>根据自定义时间</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="jy-item clearfix">
					<div class="jy-item-left">自定义禁言时间：</div>
					<div class="jy-item-right">
						<span class="jy-time sty1"><input id="timeDay" type="text" style="width: 15px; height: 30px;" /></span> <span
							class="jy-time">天</span> <span class="jy-time sty1"><input id="timeHour" type="text"
							style="width: 15px; height: 30px;" /></span> <span class="jy-time">小时</span> <span class="jy-time sty1"><input
							id="timeMinute" type="text" style="width: 15px; height: 30px;" /></span> <span class="jy-time">分钟</span>
					</div>
				</div>
				<!--<div class="jy-item clearfix" style="margin-bottom: 0;">
					<div class="jy-item-left"></div>
					<div class="jy-item-right">
						<i class="check"></i> <span class="check-text">永久禁言</span>
					</div>
				</div> -->
			</div>
			<!-- alert-con end -->
			<div class="btn1-o">
				<a id="jySubmit" class="btn1 jyConfirm">确定</a> <a class="btn1-s jyCancel"
					style="margin-left: 19px; padding: 8px 25px;">取消</a>
			</div>
		</div>
	</div>
	<!-- alert-box end -->
	<div id="jcjyBtn" class="alert-box sty2 jcjyBox">
		<div class="alert">
			<div class="top">
				<span class="top-h">解除禁言</span> <span class="close"></span>
			</div>
			<div class="alert-con" style="padding: 20px 50px 5px;">
				<p style="padding: 40px 0 30px; text-align: center; font-size: 16px; color: #333;">确定要解除对该人的禁言吗？</p>
			</div>
			<!-- alert-con end -->
			<div class="btn1-o">
				<a id="jcjySubmit" class="btn1 jcjyConfirm">确定</a>
			</div>
		</div>
	</div>
	<!-- alert-box end -->
	<div class="alert-box addPersonBox">
		<div class="alert">
			<div class="top">
				<span class="top-h">邀请群成员</span> <span class="close"></span>
			</div>
			<div class="alert-con">
				<div class="alert-tab">
					<div class="tab-c">
						<div class="tab-c-item">
							<div class="tab-line clearfix" style="padding-top: 0">
								<input id="nameorphone" class="input-sty2" type="text" name="" placeholder="用户名或手机号">
								<div id="searchMemberBtn" class="btn3">搜索</div>
							</div>
							<div class="tab-line clearfix">
								<div class="tl-left">
									<div class="t">最近联系人</div>
									<div class="c">
										<div class="c-list cur">
											<div id="recentMembers" class="c-list-c"></div>
										</div>
									</div>
								</div>
								<div class="tl-left de">
									<div class="t">
										已选成员
										<div class="p-num">
											<i id="selectMemberCount">0</i> <span>/</span> 
											<i id="recentMemberLimit">0</i>
										</div>
									</div>
									<div class="c">
										<div class="c-list cur">
											<div id="selectMembers" class="c-list-c"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- alert-con end -->
			<div class="btn1-o">
				<a id="addMemberBtn" class="btn1 addPersonConfirm">确定</a>
			</div>
		</div>
	</div>
	<script src="<%=contextPath%>/resources/js/jquery-1.7.2.min.js"></script>
	<script src="<%=contextPath%>/resources/js/cyguanli.js"></script>
	<script src="<%=contextPath%>/resources/js/calendar/lhgcalendar.js"></script>
	<link rel="stylesheet" href="<%=contextPath%>/resources/css/calendar/lhgcalendar.css" />
	<script type="text/javascript">
		var grouplist = [];
		var grouplistremove = [];
		var searchOptions = [];
		var searchO = {};
		function changeGroup(groupid) {
			var currentid = $("#groupinfo_id").attr("groudid");
			if (currentid == groupid) {
				$("#swhclose").click();
				return;
			}
			$.ajax({
				type : "post",
				url : contextPath+"/changeGroup.do",
				data : {
					groupid : groupid
				},
				async : false,
				success : function(data) {
					if (data == "") {
						alert("群切换失败");
					}
					$("#groupinfo_id").text(data.groupname);
					$("#groupinfo_id").attr("groudid", data.groupid);
					$("#communityCount").text(data.count);
					$("#communityLimit").text(data.limit);
				}
			});
			$("#swhclose").click();
			var abo = {};
			abo.groupid = $("#groupinfo_id").attr("groudid");
			abo.username = "";
			abo.lastmessagestarttime = "";
			abo.lastmessageendtime = "";
			abo.addstarttime = "";
			abo.addendtime = "";
			abo.forbidden = "";
			abo.ordermethod = "desc";
			abo.ordercolumn = "orderAddtime";
			abo.pageindex = 1;
			abo.pagesize = 20;
			changeSearchOption(abo);
			getPageList();
		};
		//群列表的分页功能
		function getPageBarChange(pageindex, pagesize, total) {
			$("#pageTotalChange").text(total);
			$("#pageTotalChange2").text(total);
			var pagenum = Math.ceil(total / pagesize);
			$("#pageIndexChange").text(pageindex);
			$("#pageSizeChange").text(pagenum);
			var i = 1;
			var pagebar = $("#changePageBar");
			pagebar.empty();
			var pagefirst = $("<div></div>");
			pagefirst.attr("class", "sty1");
			pagefirst.text("首页");
			pagefirst.click(function() {
				getCommunities(1, 6);
			});
			pagebar.append(pagefirst);
			if (pagenum <= 5) {
				for (; i <= pagenum; i++) {
					var pagediv = $("<div>" + i + "</div>");
					if (i == pageindex) {
						pagediv.attr("class", "cur");
					}
					pagediv.click(function() {
						var cur = $(this).text();
						getCommunities(cur, 6);
					});
					pagebar.append(pagediv);
				}
			} else {
				var pagepre = $("<div>" + (+pageindex - 1) + "</div>");
				pagepre.click(function() {
					getCommunities(+pageindex - 1, pagesize);
				});
				var pagecur = $("<div>" + pageindex + "</div>");
				pagecur.attr("class", "cur");
				pagecur.click(function() {
					var cur = $(this).text();
					getCommunities(cur, 6);
				});
				var pageafter = $("<div>" + (+pageindex + 1) + "</div>");
				pageafter.click(function() {
					var cur = $(this).text();
					getCommunities(cur, 6);
				});
				var pageother = $("<div>" + "..." + "</div>");
				pageother.click(function() {
					var num = +pageindex + 3;
					if (num > pagenum) {
						num = pagenum;
					}
					getCommunities(num, 6);
				});
				if (pageindex >= 2) {
					pagebar.append(pagepre);
				}
				pagebar.append(pagecur);
				if (pageindex < pagenum) {
					pagebar.append(pageafter);
					pagebar.append(pageother);
				}
			}
			var pagenext = $("<div></div>");
			pagenext.attr("class", "sty1");
			pagenext.text("下一页");
			pagenext.click(function() {
				if (+pageindex < pagenum) {
					getCommunities(+pageindex + 1, 6);
				} else {
					getCommunities(+pageindex, 6);
				}
			});
			pagebar.append(pagenext);
			var pagelast = $("<div></div>");
			pagelast.attr("class", "sty1");
			pagelast.text("尾页");
			pagelast.click(function() {
				getCommunities(pagenum, 6);
			});
			pagebar.append(pagelast);
		}
		//分页逻辑
		function getPageBar(pageindex, pagesize, total) {
			$("#pageTotal").text(total);
			var pagenum = Math.ceil(total / pagesize);
			$("#pageSize").text(pagenum);
			$("#pageIndex").text(pageindex);
			var i = 1;
			var pagebar = $("#pageBar");
			pagebar.empty();
			var pagefirst = $("<div></div>");
			pagefirst.attr("class", "sty1");
			pagefirst.text("首页");
			pagefirst.click(function() {
				var abo = {};
				abo.pageindex = 1;
				abo.pagesize = 20;
				changeSearchOption(abo);
				getPageList();
			});

			var pagebehind = $("<div></div>");
			pagebehind.attr("class", "sty1");
			pagebehind.text("上一页");
			pagebehind.click(function() {
				if (+pageindex > 1) {
					var abo = {};
					abo.pageindex = +pageindex - 1;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				} else {
					var abo = {};
					abo.pageindex = +pageindex;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				}
			});
			pagebar.append(pagefirst);
			pagebar.append(pagebehind);
			if (pagenum <= 5) {
				for (; i <= pagenum; i++) {
					var pagediv = $("<div>" + i + "</div>");
					if (i == pageindex) {
						pagediv.attr("class", "cur");
					}
					pagediv.click(function() {
						var cur = $(this).text();
						var abo = {};
						abo.pageindex = cur;
						abo.pagesize = 20;
						changeSearchOption(abo);
						getPageList();
					});
					pagebar.append(pagediv);
				}
			} else {
				var pagepre = $("<div>" + (+pageindex - 1) + "</div>");
				pagepre.click(function() {
					var abo = {};
					abo.pageindex = +pageindex - 1;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				});
				var pagecur = $("<div>" + pageindex + "</div>");
				pagecur.attr("class", "cur");
				pagecur.click(function() {
					var abo = {};
					abo.pageindex = pageindex;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				});
				var pageafter = $("<div>" + (+pageindex + 1) + "</div>");
				pageafter.click(function() {
					var abo = {};
					abo.pageindex = +pageindex + 1;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				});
				var pageother = $("<div>" + "..." + "</div>");
				pageother.click(function() {
					var num = +pageindex + 3;
					if (num > pagenum) {
						num = pagenum;
					}
					var abo = {};
					abo.pageindex = num;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				});
				if (pageindex == 1) {
					var page2 = $("<div>" + (+pageindex + 1) + "</div>");
					page2.click(function() {
						var abo = {};
						abo.pageindex = +pageindex + 1;
						abo.pagesize = 20;
						changeSearchOption(abo);
						getPageList();
					});
					var page3 = $("<div>" + (+pageindex + 2) + "</div>");
					page3.click(function() {
						var abo = {};
						abo.pageindex = +pageindex + 2;
						abo.pagesize = 20;
						changeSearchOption(abo);
						getPageList();
					});
					pagebar.append(pagecur);
					pagebar.append(page2);
					pagebar.append(page3);
					pagebar.append(pageother);
				} else if (pageindex == pagenum) {
					var page2 = $("<div>" + (+pageindex - 1) + "</div>");
					page2.click(function() {
						var abo = {};
						abo.pageindex = +pageindex - 1;
						abo.pagesize = 20;
						changeSearchOption(abo);
						getPageList();
					});
					var page3 = $("<div>" + (+pageindex - 2) + "</div>");
					page3.click(function() {
						var abo = {};
						abo.pageindex = +pageindex - 2;
						abo.pagesize = 20;
						changeSearchOption(abo);
						getPageList();
					});
					pagebar.append(page3);
					pagebar.append(page2);
					pagebar.append(pagecur);
				} else {
					if (pageindex >= 2) {
						pagebar.append(pagepre);
					}
					pagebar.append(pagecur);
					if (pageindex < pagenum) {
						pagebar.append(pageafter);
						pagebar.append(pageother);
					}
				}
			}

			var pagenext = $("<div></div>");
			pagenext.attr("class", "sty1");
			pagenext.text("下一页");
			pagenext.click(function() {
				if (+pageindex < pagenum) {
					var abo = {};
					abo.pageindex = +pageindex + 1;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				} else {
					var abo = {};
					abo.pageindex = +pageindex;
					abo.pagesize = 20;
					changeSearchOption(abo);
					getPageList();
				}
			});
			pagebar.append(pagenext);
			var pagelast = $("<div></div>");
			pagelast.attr("class", "sty1");
			pagelast.text("尾页");
			pagelast.click(function() {
				var abo = {};
				abo.pageindex = pagenum;
				abo.pagesize = 20;
				changeSearchOption(abo);
				getPageList();
			});
			pagebar.append(pagelast);
		};
		//最近联系人的来回切换
		function bindingMove(elem) {
			elem.click(function() {
				debugger
				var parentName = elem.parent().attr("id");
				if (parentName == "recentMembers") {
					$("#selectMembers").append(elem);
					var num =$("#selectMemberCount").text(); //$("#recentMemberCount").text();
					num = +num + 1;
					$("#selectMemberCount").text(num);
					//$("#recentMemberCount").text(num);
					var memberid = elem.attr("memberid");
					grouplist[memberid] = 1;
				} else {
					$("#recentMembers").append(elem);
					var num = $("#recentMemberCount").text();
					num = +num - 1;
					$("#recentMemberCount").text(num);
					var memberid = elem.attr("memberid");
					grouplist[memberid] = 0;
				}
			});
		}
		//通过im用户名查询最近联系人
		function getRecentMembers(imname) {
			$.post(contextPath+"/getRecentMember.do", {
				"imname" : imname
			}, function(res) {
				var data = res.data;
				if(data==null||data==undefined){
					return;
				}
				$("#recentMembers").empty();
				$("#selectMembers").empty();
				var i = 0, length = data.length;
				$("#recentMemberLimit").text(length);
				for (; i < data.length; i++) {
					var name = data[i].nickname;
					var div = $("<div></div>");
					div.attr("class", "item clearfix");
					var img = $("<img\>");
					img.attr("src", contextPath+"/resources/images/ph.jpg");
					img.css("heigth", "20px");
					img.css("width", "20px");
					if (data[i].picurl != null && data[i].picurl != "") {
						img.attr("src", data[i].picurl);
					}
					var span = $("<span></span>");
					span.text(name);
					var ii = $("<i></i>");
					div.append(img);
					div.append(span);
					div.append(ii);
					div.attr("memberid", data[i].imusername);
					bindingMove(div);
					$("#recentMembers").append(div);
				}
			});
		};
		function searchMember(key) {
			$.post(contextPath+"/searchMemberPassport.do", {
				"key" : key
			}, function(data) {
				if (data == "") {
					alert("用户不存在");
					return;
				}
				var name = "l:" + data.name;

				if (!($("div[id='"+name+"']").attr("memberid"))) {
					var llimit = $("#recentMemberCount").text();
					llimit = +llimit + 1;
					$("#recentMemberCount").text(llimit);
					var ccount = $("#recentMemberLimit").text();
					ccount = +ccount + 1;
					$("#recentMemberLimit").text(ccount);
					var div = $("<div></div>");
					div.attr("id", name);
					div.attr("class", "item clearfix");
					div.attr("memberid", name);
					var img = $("<img\>");
					img.attr("src", contextPath+"/resources/images/ph.jpg");
					if (data.pic !== null) {
						img.attr("src", data.pic);
					}
					var span = $("<span></span>");
					span.text(name);
					var ii = $("<i></i>");
					div.append(img);
					div.append(span);
					div.append(ii);
					bindingMove(div);
					$("div[class='tl-left']").find("div[id='recentMembers']").append(div);
					grouplist[name] = 1;
				}

			});
		};
		function changeSearchOption(o) {
			if (!(typeof (o.groupid) == "undefined")) {
				searchO.groupid = o.groupid;
			}
			;
			if (!(typeof (o.username) == "undefined")) {
				searchO.username = o.username;
			}
			;
			if (!(typeof (o.lastmessagestarttime) == "undefined")) {
				searchO.lastmessagestarttime = o.lastmessagestarttime;
			}
			;
			if (!(typeof (o.lastmessageendtime) == "undefined")) {
				searchO.lastmessageendtime = o.lastmessageendtime;
			}
			;
			if (!(typeof (o.addstarttime) == "undefined")) {
				searchO.addstarttime = o.addstarttime;
			}
			;
			if (!(typeof (o.addendtime) == "undefined")) {
				searchO.addendtime = o.addendtime;
			}
			;
			if (!(typeof (o.forbidden) == "undefined")) {
				searchO.forbidden = o.forbidden;
			}
			;
			if (!(typeof (o.ordermethod) == "undefined")) {
				searchO.ordermethod = o.ordermethod;
			}
			;
			if (!(typeof (o.ordercolumn) == "undefined")) {
				searchO.ordercolumn = o.ordercolumn;
			}
			;
			if (!(typeof (o.pageindex) == "undefined")) {
				searchO.pageindex = o.pageindex;
			}
			;
			if (!(typeof (o.pagesize) == "undefined")) {
				searchO.pagesize = o.pagesize;
			}
			;
			if (!(typeof (o.black) == "undefined")) {
				searchO.black = o.black;
			}
			;
		};
		function getPageList() {
			$.post(contextPath+"/searchMember.do", searchO, function(data) {
				refresh(data);
				getPageBar(searchO.pageindex, searchO.pagesize, data.msg);
			});
		};
		function addtimechange() {
			if (searchO.ordercolumn == "addorder") {
				if (searchO.ordermethod == "desc") {
					var abo = {};
					abo.ordermethod = "asc";
					changeSearchOption(abo);
				} else {
					var abo = {};
					abo.ordermethod = "desc";
					changeSearchOption(abo);
				}
			} else {
				var abo = {};
				abo.ordercolumn = "addorder";
				abo.ordermethod = "desc";
				changeSearchOption(abo);
			}
			getPageList();
		};
		function messagetimechange() {
			if (searchO.ordercolumn == "messageorder") {
				if (searchO.ordermethod == "desc") {
					var abo = {};
					abo.ordermethod = "asc";
					changeSearchOption(abo);
				} else {
					var abo = {};
					abo.ordermethod = "desc";
					changeSearchOption(abo);
				}
			} else {
				var abo = {};
				abo.ordercolumn = "messageorder";
				abo.ordermethod = "desc";
				changeSearchOption(abo);
			}
			getPageList();
		}
		//刷新成员列表
		function refresh(res) {
			var data = res.data;
			$("#communityCount").text(res.msg);
			var memberTable = $("#memberTable");
			memberTable.empty();
			var head = $("<tr><th></th><th></th><th></th><th class=\"special\">成员(昵称)</th><th>群名片</th><th>IM账号</th><th>入群时间<img onclick=\"addtimechange();\" src=\""+contextPath+"/resources/images/xx.jpg\"></th><th>最后发言<img onclick=\"messagetimechange();\" src=\""+contextPath+"/resources/images/xx.jpg\"></th><th>状态</th><th>操作</th></tr>");
			memberTable.append(head);
			var i = 0, length = data.length;
			for (; i < length; i++) {
				var tr = $("<tr></tr>");
				var nnn = i;
				tr.attr("id", "memberrow" + nnn);
				var tds = [];
				for (var j = 0; j < 10; j++) {
					tds[j] = $("<td></td>");
				}
				var ii = $("<i></i>");
				ii.attr("memberid", data[i].username);
				ii.attr("membername", data[i].nickname);
				ii.click(function() {
					var elem = $(this);
					var id = elem.attr("memberid");
					if (elem.text() == "√") {
						elem.text("");
						grouplistremove[id] = 0;
					} else {
						elem.text("√");
						grouplistremove[id] = 1;
					}
				});
				tds[0].append(ii);
				tds[1].text(i + 1);
				var img = $("<img/>");
				var src = "";
				switch (data[i].usertag) {
				case "owner":
					src = contextPath+"/resources/images/qunzhu.png";
					break;
				case "common":
					src = "";
					break;
				default:
					src = "";
				}
				img.attr("src", src);
				img.attr("title", data[i].uesrtag);
				tds[2].append(img);
				tds[3].attr("class", "special");
				var img2 = $("<img/>");
				img2.attr("class", "tx");
				img2.attr("src", contextPath+"/resources/images/q.jpg");
				img2.css("width", "20px");
				img2.css("height", "20px");
				if (data[i].picurl != null && data[i].picurl != "") {
					img2.attr("src", data[i].picurl);
				}
				var input = $("<input/>");
				input.attr("class", "changed-input");
				input.css("width", "100px");
				input.attr("type", "text");
				input.attr("value", data[i].nickname);
				input.attr("disabled", "disabled");
				tds[3].append(img2);
				tds[3].append(input);
				var iiinput = $("<input/>");
				iiinput.val(data[i].cardname);
				iiinput.attr("disabled", "disabled");
				iiinput.css("text-align", "center");
				iiinput.attr("type", "text");
				iiinput.attr("id", "specialinput" + i);
				iiinput.attr("memberid", data[i].username);
				iiinput.change(function() {
					$.post(contextPath+"/updateCardname.do", {
						"groupid" : $("#groupinfo_id").attr("groudid"),
						"username" : $(this).attr("memberid"),
						"cardname" : $(this).val()
					}, function(data) {
						if (data == "") {
							alert("修改失败");
						} else {
							alert("修改成功");
						}
					});
					$(this).blur();
				});
				tds[4].append(iiinput);
				tds[5].text(data[i].username);
				var adt = data[i].addtime;
				if (adt === null) {
					tds[6].text("");
				} else {
					tds[6].text(adt);
				}
				var lsmt = data[i].lastmessagetime;
				if (lsmt === null) {
					tds[7].text("");
				} else {
					tds[7].text(lsmt);
				}
				var state = "";
				var img4 = $("<img/>");
				img4.attr("class", "ibtn jyBtn");
				img4.attr("memberid", data[i].username);

				if (data[i].forbidden == "0") {
					state = "正常";
					img4.attr("src", contextPath+"/resources/images/jinyan.png");
					img4.attr("title", "禁言");
					img4.click(function() {
						$("#jySubmit").attr("memberid",
								$(this).attr("memberid"));
						var iid = $(this).parent().parent().attr("id");
						$("#jySubmit").attr("rowid", iid);
						$("#timeDay").val("");
						$("#timeHour").val("");
						$("#timeMinute").val("");
						$("#jyBtn").show();
					});
				} else if (data[i].forbidden == "1") {
					state = "禁言";
					img4.attr("src", contextPath+"/resources/images/jinyan_h.png");
					img4.attr("title", "解除禁言");
					img4.click(function() {
						$("#jcjySubmit").attr("memberid",
								$(this).attr("memberid"));
						var iid = $(this).parent().parent().attr("id");
						$("#jcjySubmit").attr("rowid", iid);
						$("#jcjyBtn").show();
					});
				}
				tds[8].text(state);
				var img3 = $("<img/>");
				img3.attr("class", "ibtn edit");
				img3.attr("src", contextPath+"/resources/images/bianji.png");
				img3.attr("title", "编辑");
				img3.attr("inputid", "specialinput" + i);
				img3.click(function() {
					var inputid = $(this).attr("inputid");
					var input = $("#" + inputid);
					input.removeAttr("disabled");
					input.focus();
				});

				var img5 = $("<img/>");
				img5.attr("class", "ibtn delete delBtn");
				img5.attr("src", contextPath+"/resources/images/delete.png");
				img5.attr("title", "删除");
				img5.attr("memberid", data[i].username);
				img5.attr("membername", data[i].nickname);
				img5.click(function() {
					var member = $(this).attr("memberid");
					var name = $(this).attr("membername");
					var res = confirm("确定要删除成员(" + member + ")吗");
					if (res) {
						removeMembers(member);
					}
				});

				var img6 = $("<img/>");
				img6.attr("memberid", data[i].username);
				img6.attr("class", "ibtn");

				//已经拉黑，只有一个解除拉黑操作
				if (data[i].black == "1") {
					state = "拉黑";
					tds[8].text(state);
					img6.attr("src", contextPath+"/resources/images/black.png");
					img6.attr("title", "解除拉黑");
					img6.click(function() {
						var res = confirm("解除对成员(" + $(this).attr("memberid")
								+ ")的拉黑?");
						if (res) {
							$.post(contextPath+"/unblackMember.do", {
								"groupid" : $("#groupinfo_id").attr("groudid"),
								"username" : $(this).attr("memberid")
							}, function(data) {
								if (data == "success") {
									alert("操作成功");
								} else {
									alert("操作失败");
								}
								getPageList();
							});
						}
					});
					tds[9].append(img6);
				} else {
					state = "正常";
					img6.attr("src", contextPath+"/resources/images/noblack.png");
					img6.attr("title", "拉黑");
					img6.click(function() {
						var res = confirm("要拉黑成员(" + $(this).attr("memberid")
								+ ")吗?");
						if (res) {
							$.post(contextPath+"/blackMember.do", {
								"groupid" : $("#groupinfo_id").attr("groudid"),
								"username" : $(this).attr("memberid")
							}, function(data) {
								if (data == "success") {
									alert("操作成功");
								} else {
									alert("操作失败");
								}
								getPageList();
							});
						}
					});
					tds[9].append(img3);
					tds[9].append(img4);
					tds[9].append(img5);
					tds[9].append(img6);
				}

				for (var j = 0; j < 10; j++) {
					tr.append(tds[j]);
				}
				memberTable.append(tr);
			}
		};
		//刷新单行成员
		function refreshSingleRow(row) {
			var memberid = $($("#" + row).children("td")[0]).children("i")
					.attr("memberid");
			$.post(contextPath+"/getMemberById.do", {
				"groupid" : $("#groupinfo_id").attr("groudid"),
				"memberid" : memberid
			}, function(data) {
				if (data.forbidden == "0") {
					$($("#" + row).children("td")[8]).text("正常");
					var img = $($($("#" + row).children("td")[9]).children(
							"img")[1]);
					img.attr("title", "禁言");
					img.attr("src", contextPath+"/resources/images/jinyan.png");
					img.unbind();
					img.click(function() {
						$("#jySubmit").attr("memberid", data.username);
						$("#jySubmit").attr("rowid", row);
						$("#timeDay").val("");
						$("#timeHour").val("");
						$("#timeMinute").val("");
						$("#jyBtn").show();
					});
				} else {
					$($("#" + row).children("td")[8]).text("禁言");
					var img = $($($("#" + row).children("td")[9]).children(
							"img")[1]);
					img.attr("title", "解除禁言");
					img.attr("src", contextPath+"/resources/images/jinyan_h.png");
					img.unbind();
					img.click(function() {
						$("#jcjySubmit").attr("memberid", data.username);
						$("#jcjySubmit").attr("rowid", row);
						$("#jcjyBtn").show();
					});
				}

			});
		}
		//获取群
		function getCommunities(pageindex, pagesize) {
			$.ajax({
				type : "post",
				url : contextPath+"/getCommunities.do",
				data : {
					"pageindex" : pageindex,
					"pagesize" : pagesize
				},
				async : false,
				success : function(res) {
					$("#communityList").empty();
					fillCommunity(res.data);
					getPageBarChange(res.pageindex, res.pagesize, res.total);
				}
			});
		};
		//搜索群
		function searchCommunity(key) {
			$.post(contextPath+"/searchCommunity.do", {
				"key" : key
			}, function(data) {
				$("#communityList").empty();
				fillCommunity(data);
			});
		};
		//填充群列表
		function fillCommunity(data) {
			var parentDiv = $("#communityList");
			var i = 0, length = data.length;
			for (; i < length; i++) {
				var div = $("<div></div>");
				div.attr("class", "xzqun-c-list clearfix");
				var img = $("<img/>");
				img.attr("src", contextPath+"/resources/images/q.jpg");
				if (data[i].pic != "") {
					img.attr("src", data[i].pic);
				}
				var span = $("<span></span>");
				span.text(data[i].communityName);
				span.attr("gid", data[i].communityId);
				if (i == 0) {
					span.attr("id", "markcommunity");
				}
				span.click(function() {
					changeGroup($(this).attr("gid"));
				});
				div.append(img);
				div.append(span);
				parentDiv.append(div);
			}
		};
		//增加成员
		function addMembers(member) {
			var groupid = $("#groupinfo_id").attr("groudid");
			$.post(contextPath+"/addMembers.do", {
				"form" : "im:system",
				"groupid" : groupid,
				"members" : member,
				"action" : "add"
			}, function(data) {
				if (data == "ok") {
					getPageList();
				} else {
					alert("增加失败");
				}
			});
		};
		//删除成员
		function removeMembers(member) {
			var groupid = $("#groupinfo_id").attr("groudid");
			$.post(contextPath+"/removeMembers.do", {
				"form" : "im:system",
				"groupid" : groupid,
				"members" : member,
				"action" : "exit"
			}, function(data) {
				if (data == "ok") {
					var curpage = $("#pageBar").children(".cur").text();
					getPageList();
				} else {
					alert("删除失败");
				}
				grouplistremove = [];
			});
		};
		function searchMemberInGroup(name) {
			debugger
			var abo = {};
			abo.groupid = $("#groupinfo_id").attr("groudid");
			abo.username = name;
			abo.pageindex = 1;
			abo.pagesize = 20;
			changeSearchOption(abo);
			$.post(contextPath+"/searchMember.do", searchO, function(data) {
				refresh(data);
				getPageBar(searchO.pageindex, searchO.pagesize, data.msg);
			});
		};
		function searchMemberMoreOption(option) {
			var abo = {};
			abo.groupid = $("#groupinfo_id").attr("groudid");
			abo.lastmessagestarttime = option.lastmessagestarttime;
			abo.lastmessageendtime = option.lastmessageendtime;
			abo.addstarttime = option.addstarttime;
			abo.addendtime = option.addendtime;
			abo.ordermethod = option.ordermethod;
			abo.ordercolumn = option.ordercolumn;
			abo.pageindex = option.pageindex;
			abo.pagesize = option.pagesize;
			abo.forbidden = option.forbidden;
			changeSearchOption(abo);
			$.post(contextPath+"/searchMember.do", searchO, function(data) {
				refresh(data);
				getPageBar(searchO.pageindex, searchO.pagesize, data.msg);
			});
		};
		function dateToString(d) {
			return "" + d.getFullYear() + "-"
					+ LessThanTenOpr(d.getMonth() + 1) + "-" + LessThanTenOpr(d.getDate());
		};
		function dateToStringLong(d) {
			return "" + d.getFullYear() + "-"
					+ LessThanTenOpr(d.getMonth() + 1) + "-" + LessThanTenOpr(d.getDate())
					+ " " + LessThanTenOpr(d.getHours()) + ":"
					+ LessThanTenOpr(d.getMinutes()) + ":"
					+ LessThanTenOpr(d.getSeconds());
		}
		function LessThanTenOpr(t) {
			return +t < 10 ? ("0" + t) : t;
		}
		function forbiddenMember(name, time) {
			$.ajax({
				type : "post",
				url : contextPath+"/forbiddenMember.do",
				data : {
					"groupid" : $("#groupinfo_id").attr("groudid"),
					"username" : name,
					"endtime" : time
				},
				async : false,
				success : function(res) {
					alert("操作成功");
				}
			});
		};
		function unforbiddenMember(name) {
			$.ajax({
				type : "post",
				url : contextPath+"/unforbiddenMember.do",
				data : {
					"groupid" : $("#groupinfo_id").attr("groudid"),
					"username" : name
				},
				async : false,
				success : function(res) {
					if (res == "success") {
						alert("操作成功");
					} else {
						alert("操作失败");
					}
				}
			});
		};
		function GetQueryString(name)
		{
			var a = window.location.search.replace("?", "").split("&");
			for (var i=0;i<a.length;i++) {
				var e = a[i];
				var p = e.split("=");
				if(name == p[0]) {
					return decodeURI(p[1]);
				}
			}
		}
		function initCommunity() {
			var groupname = GetQueryString("groupname");
			var groupid = GetQueryString("groupid");
			var groupcount = GetQueryString("groupcount");
			var grouplimit = GetQueryString("grouplimit");
			if(groupname && groupid && groupcount && groupcount && grouplimit) {
				$("#groupinfo_id").text(groupname);
				$("#groupinfo_id").attr("groudid", groupid);
				$("#communityCount").text(groupcount);
				$("#communityLimit").text(grouplimit);
			} else {
				$.ajax({
					type : "post",
					url : contextPath+"/getNearestCommunity.do",
					data : {},
					async : false,
					success : function(res) {
						$("#groupinfo_id").text(res.data[0].groupname);
						$("#groupinfo_id").attr("groudid", res.data[0].groupid);
						$("#communityCount").text(res.data[0].count);
						$("#communityLimit").text(res.data[0].limit);
					}
				});
			}
		};
		function isRightDate(d) {
			var reg = new RegExp(
					"(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
			return reg.test(d);
		}

		$(function() {
			isSelected(2);
			initCommunity();
			$("#inviteMember").click(function() {
				$("#nameorphone").val("");
				$("#recentMemberCount").text(0);
				$("#recentMemberLimit").text(0);
				getRecentMembers("aa");
			});
			$("#searchMemberBtn").click(function() {
				debugger
				searchMember($("#nameorphone").val());
			});
			$("#changeMemberBtn").click(function() {
				$("#searchCommunityText").val("");
				$("#communityList").empty();
				getCommunities(1, 6);
			});
			$("#searchCommunity").click(function() {
				var key = $("#searchCommunityText").val();
				if (key == "") {
					$("#communityList").empty();
					getCommunities(1, 6);
				} else {
					searchCommunity(key);
				}
			});
			var abo = {};
			abo.groupid = $("#groupinfo_id").attr("groudid");
			abo.username = "";
			abo.lastmessagestarttime = "";
			abo.lastmessageendtime = "";
			abo.addstarttime = "";
			abo.addendtime = "";
			abo.forbidden = "";
			abo.ordermethod = "desc";
			abo.ordercolumn = "addorder";
			abo.pageindex = 1;
			abo.pagesize = 20;
			changeSearchOption(abo);
			getPageList();
			$("#addMemberBtn").click(function() {
				var member = "";
				for (elem in grouplist) {
					if (grouplist[elem] == 1) {
						member += elem + ",";
					}
				}
				if (member.length > 1) {
					member = member.substr(0, member.length - 1);
				}
				addMembers(member);
			});
			$("#removeMemberBtn").click(function() {
				var member = "";
				var name = "";
				for (elem in grouplistremove) {
					if (grouplistremove[elem] != 0) {
						member += elem + ",";
						name += elem + ",";
					}
				}
				if (member.length > 1) {
					member = member.substr(0, member.length - 1);
				}
				if (name.length > 1) {
					name = name.substr(0, name.length - 1);
				}
				if (member.length < 1) {
					alert("请选择要删除的成员");
				} else {
					var res = confirm("确定要删除成员(" + name + ")吗");
					if (res) {
						removeMembers(member);
					}
				}
			});
			$("#searchMemberOut").click(function() {
				debugger
				searchMemberInGroup($("#searchKey").val());
			});

			$("#creditOption").children("span").click(
					function() {
						$(this).attr("class", "cur");
						$(this).siblings("span").removeAttr("class");
						searchOptions["credit"] = $(this).attr("val");
						$("#creditO").remove();
						if ($(this).attr("val") != 0) {
							var span = $("<span></span>");
							span.attr("id", "creditO");
							span.text("信用：不良记录者");
							var img = $("<img/>");
							img.attr("src", contextPath+"/resources/images/close.png");
							span.append(img);
							img.click(function() {
								$(this).parent().remove();
								$("#creditOption").children().removeAttr(
										"class");
								$("#creditOption").children("span[val=0]")
										.attr("class", "cur");
								searchOptions["credit"] = 0;
							});
							$("#searchOptions").append(span);
						}
					});
			$("#addtimeOption").children("span").click(
					function() {
						$(this).attr("class", "cur");
						$(this).siblings("span").removeAttr("class");
						searchOptions["addtime"] = $(this).attr("val");
						$("#addtimeO").remove();
						if ($(this).attr("val") != 0) {
							var span = $("<span></span>");
							span.attr("id", "addtimeO");
							var str = "入群时间：";
							str += $(this).text();
							span.text(str);
							var img = $("<img/>");
							img.attr("src", "/resources/images/close.png");
							img.click(function() {
								$(this).parent().remove();
								$("#addtimeOption").children().removeAttr(
										"class");
								$("#addtimeOption").children("span[val=0]")
										.attr("class", "cur");
								searchOptions["addtime"] = 0;
							});
							span.append(img);
							$("#searchOptions").append(span);
						}
					});
			$("#lastmessageOption").children("span").click(
					function() {
						$(this).attr("class", "cur");
						$(this).siblings("span").removeAttr("class");
						searchOptions["lastmessage"] = $(this).attr("val");
						$("#lastmessageO").remove();
						if ($(this).attr("val") != 0) {
							var span = $("<span></span>");
							span.attr("id", "lastmessageO");
							var str = "最后发言：";
							str += $(this).text();
							span.text(str);
							var img = $("<img/>");
							img.attr("src", contextPath+"/resources/images/close.png");
							img.click(function() {
								$(this).parent().remove();
								$("#lastmessageOption").children().removeAttr(
										"class");
								$("#lastmessageOption").children("span[val=0]")
										.attr("class", "cur");
								searchOptions["lastmessage"] = 0;
							});
							span.append(img);
							$("#searchOptions").append(span);
						}
					});
			$("#clearOptions").click(function() {
				$("#searchOptions").children("span").children("img").click();
			});
			$("#submitOptions").click(function() {
				var option = {};
				var d = new Date();
				var d2 = new Date();
				if (searchOptions["addtime"] == 0) {
					option.addstarttime = "";
					option.addendtime = "";
				} else {
					switch (+searchOptions["addtime"]) {
					case 1:
						d.setDate(d.getDate() + 1);
						option.addendtime = dateToString(d);
						d.setMonth(d.getMonth() - 1);
						option.addstarttime = dateToString(d);
						break;
					case 2:
						d.setMonth(d.getMonth() - 1);
						option.addendtime = dateToString(d);
						d.setMonth(d.getMonth() - 2);
						option.addstarttime = dateToString(d);
						break;
					case 3:
						d.setMonth(d.getMonth() - 3);
						option.addendtime = dateToString(d);
						d.setMonth(d.getMonth() - 3);
						option.addstarttime = dateToString(d);
						break;
					case 4:
						d.setMonth(d.getMonth() - 6);
						option.addendtime = dateToString(d);
						d.setMonth(d.getMonth() - 6);
						option.addstarttime = dateToString(d);
						break;
					case 5:
						d.setMonth(d.getMonth() - 12);
						option.addendtime = dateToString(d);
						option.addstarttime = "";
						break;
					case 6:
						var bb = $("#addstarttimebtn").val();
						var cc = $("#addendtimebtn").val();
						if (new Date(bb) > new Date(cc)) {
							alert("入群时间有误!");
							return;
						}
						option.addstarttime = bb;
						var ccbb = new Date(cc);
						ccbb.setDate(ccbb.getDate() + 1);
						option.addendtime = dateToString(ccbb);
						break;
					default:
						option.addendtime = "";
						option.addstarttime = "";
						break;
					}
				}
				if (searchOptions["lastmessage"] == 0) {
					option.lastmessagestarttime = "";
					option.lastmessageendtime = "";
				} else {
					switch (+searchOptions["lastmessage"]) {
					case 1:
						option.lastmessageendtime = dateToString(d2);
						d2.setMonth(d2.getMonth() - 1);
						option.lastmessagestarttime = dateToString(d2);
						break;
					case 2:
						d2.setMonth(d2.getMonth() - 1);
						option.lastmessageendtime = dateToString(d2);
						d2.setMonth(d2.getMonth() - 2);
						option.lastmessagestarttime = dateToString(d2);
						break;
					case 3:
						d2.setMonth(d2.getMonth() - 3);
						option.lastmessageendtime = dateToString(d2);
						d2.setMonth(d2.getMonth() - 3);
						option.lastmessagestarttime = dateToString(d2);
						break;
					case 4:
						d2.setMonth(d2.getMonth() - 6);
						option.lastmessageendtime = dateToString(d2);
						d2.setMonth(d2.getMonth() - 6);
						option.lastmessagestarttime = dateToString(d2);
						break;
					case 5:
						d2.setMonth(d2.getMonth() - 12);
						option.lastmessageendtime = dateToString(d2);
						option.lastmessagestarttime = "";
						break;
					case 6:
						var dd = $("#messagestarttimebtn").val();
						var ee = $("#messageendtimebtn").val();
						if (new Date(dd) > new Date(ee)) {
							alert("入群时间有误!");
							return;
						}
						option.lastmessagestarttime = dd;
						var eedd = new Date(ee);
						eedd.setDate(eedd.getDate() + 1);
						option.lastmessageendtime = dateToString(eedd);
						break;
					default:
						option.lastmessageendtime = "";
						option.lastmessagestarttime = "";
						break;
					}
				}
				//禁言成员
				if (searchOptions["credit"] == 1) {
					option.forbidden = 1;
				} else {
					option.forbidden = "";
				}
				option.ordercolumn = "addorder";
				option.ordermethod = "desc";
				option.pageindex = 1;
				option.pagesize = 20;
				searchMemberMoreOption(option);
			});
			$("#jySubmit").click(
					function() {
						var jytime = $("#jytime");
						var d = new Date();
						switch (jytime.text()) {
						case "10分钟":
							d.setMinutes(d.getMinutes() + 9);
							break;
						case "20分钟":
							d.setMinutes(d.getMinutes() + 19);
							break;
						case "30分钟":
							d.setMinutes(d.getMinutes() + 29);
							break;
						case "40分钟":
							d.setMinutes(d.getMinutes() + 39);
							break;
						case "根据自定义时间":
							var day = $("#timeDay").val();
							var hour = $("#timeHour").val();
							var minute = $("#timeMinute").val();
							if (day == "" && hour == "" && minute == "") {
								alert("选时间啊,是不是傻");
								return;
							}
							if (isNaN(day) || isNaN(hour) || isNaN(minute)) {
								alert("输入数字,是真傻啊");
								return;
							}
							if (+day < 0 || +hour < 0 || +minute<0||+hour>23
									|| +minute > 59) {
								alert("时间有误");
								return;
							}
							if (+day == 0 && +hour == 0 && +minute == 0) {
								alert("在干吗呢?");
								return;
							}
							d.setDate(d.getDate() + +day);
							d.setHours(d.getHours() + +hour);
							d.setMinutes(d.getMinutes() + (+minute - 1));
							break;
						default:
							break;
						}
						var endtime = dateToStringLong(d);
						forbiddenMember($(this).attr("memberid"), endtime);
						refreshSingleRow($(this).attr("rowid"));
					});
			$("#jcjySubmit").click(function() {
				var memberid = $(this).attr("memberid");
				unforbiddenMember(memberid);
				refreshSingleRow($(this).attr("rowid"));
			});
			$("#addstarttimebtn").calendar({
				format : 'yyyy-MM-dd',
				btnBar : false,
				onSetDate : function() {
				}
			});
			$("#addendtimebtn").calendar({
				format : 'yyyy-MM-dd',
				btnBar : false,
				onSetDate : function() {
				}
			});
			$("#messagestarttimebtn").calendar({
				format : 'yyyy-MM-dd',
				btnBar : false,
				onSetDate : function() {
				}
			});
			$("#messageendtimebtn").calendar({
				format : 'yyyy-MM-dd',
				btnBar : false,
				onSetDate : function() {
				}
			});

		});
	</script>
</body>
</html>