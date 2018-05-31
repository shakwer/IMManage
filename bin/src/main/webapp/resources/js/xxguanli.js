$(function() {
	isSelected(3);
	refreshTable();
	pageClickMsg();
	pageClickQun();
});

// 下拉框
$('.select-t').click(function() {
	var $self = $(this);
	var parent = $self.parent();
	if (parent.find('.select-c').is(':hidden')) {
		$('.select-c').hide();
		parent.find('.select-c').show();
	} else {
		parent.find('.select-c').hide();
	}

});
$('body').click(
		function(ev) {
			var strClass = $(ev.target).attr('class'), strClass1 = $(ev.target)
					.parent().attr('class');
			if (strClass && strClass.indexOf('select-t') != -1 || strClass1
					&& strClass1.indexOf('select-t') != -1) {

				return;
			}
			$('.select-c').hide();
		});
$('.select-c li').on('click', function() {

	var $self = $(this);
	var text = $self.text();
	var parentSelect = $self.parents('.select');
	if (parentSelect.hasClass('special')) {
		return;
	}
	parentSelect.find('.select-t span').text(text);
	$self.parents('.select-c').hide();
});
$('.select-c').on('click', function(event) {
	event.stopPropagation();
})
// tab切换
$('.bottom-border').each(function(index) {
	var $self = $(this);
	var selfWidth = $self.parent().find('span:eq(0)').outerWidth();
	$(this).width(selfWidth);
});
$('.dt-left>span')
		.on(
				'click',
				function() {
					var $self = $(this);
					var border = $self.parent().find('.bottom-border');
					var left = $self.offset().left
							- $self.parent().offset().left, width = $self
							.outerWidth();
					var index = $self.index();
					$self.parents('.section').find('.section-c').hide();
					$self.parents('.section').find(
							'.section-c:eq(' + index + ')').show();

					$self.addClass('cur').siblings().removeClass('cur');
					border.stop().animate({
						left : left + 'px',
						width : width + 'px'
					}, 300);
				});

// 弹框关闭
$('#qunclose').click(function() {
	$(this).parents('.alert-box').hide();
});


// 切换群
$('.switchQunBtn').click(function() {
	$('.switchQunBox').show();
});

// 更多筛选
$('.moreChoice').click(function(event) {
	event.stopPropagation();
	var $sxBox = $('.sx-box');
	if ($sxBox.is(':hidden')) {
		$sxBox.show();
	} else {
		$sxBox.hide();
	}
});
$('body').click(function() {
	$('.sx-box').hide();
});
$('.sx-box').click(function(event) {
	$("#calendar").hide();
	event.stopPropagation();
});
$('.sxConfirm').click(
		function() {
			$('.sx-box').hide();
			var param = getMessageParams();
			var url = getMessagesUrl(param.pageIndex, param.pageSize,
					param.keyWords, param.spokesman, param.lastSpeakingTime,
					param.communityId);
			getMessages(url);

		});

// 二维码弹出框
$("#messagestable").on("click", ".ewmBtn", function(event) {
	event.stopPropagation();
	var $alertBox = $(this).parent().find('.erweima-box1');
	if ($alertBox.is(':hidden')) {
		$('.erweima-box1').hide();
		$alertBox.show();
	} else {
		$alertBox.hide();
	}
});
$("#messagestable").on("click", ".erweima-box1", function(event) {
	event.stopPropagation();
});
$('body').click(function() {
	$('.erweima-box1').hide();
});

// body 最小高度
$('body').css('min-height', $(window).height() + 'px');
$(window).resize(function() {
	$('body').css('min-height', $(window).height() + 'px');
});

// 新增
// 筛选框的相关事件
// 发言人
$("#spokesman").on("click", "#closeman", function() {
	$("#spokesman").css("display", "none").text('');
	$("input[class='input-sty5']").val('');
	return false;
});
$("input[class='input-sty5']").blur(
		function() {
			var $spokesman = $("#spokesman");
			var oldspokesman = $spokesman.text();
			var newspokesman = $.trim($(this).val());
			if (newspokesman != '' && newspokesman != oldspokesman) {
				$spokesman.text('');
				$spokesman.prepend("发言人：" + newspokesman);
				$spokesman.append($("<img id='closeman' src='" + contextPath
						+ "/resources/images/close.png'>"));
				$spokesman.css("display", "block");
			} else if (newspokesman == '') {
				$spokesman.css("display", "none");
			}
			return false;
		});
// 最后发言时间
$("#div_lastspeak span:not(:last)").click(
		function() {
			$("#lastspeaktime_end").text('').css("display", "none");
			$("#div_lastspeak span:not(:last)").removeClass("cur");
			$(this).addClass("cur");
			var $lastspeaktime = $("#lastspeaktime");
			var oldtime = $lastspeaktime.text();
			var newtime = $(this).text();
			if (newtime != oldtime && newtime != '不限') {
				$lastspeaktime.text('');
				$lastspeaktime.prepend("最后发言：" + newtime);
				$lastspeaktime.append($("<img id='closelast' src='"
						+ contextPath + "/resources/images/close.png'>"));
				$lastspeaktime.css("display", "block");
			} else if (newtime == '不限') {
				$lastspeaktime.css("display", "none").text('');
			}
			$("#datestart").val('');
			$("#dateend").val('');
			return false;
		});
$("#lastspeaktime").on("click", "#closelast", function() {
	$("#datestart").val('');
	$("#lastspeaktime").css("display", "none").text('');
	$("#div_lastspeak span:not(:last,:first)").removeClass("cur");
	$("#div_lastspeak span:first").addClass("cur");
	return false;
});
$("#lastspeaktime_end").on("click", "#closelast", function() {
	$("#dateend").val('');
	$("#lastspeaktime_end").css("display", "none").text('');
	$("#div_lastspeak span:not(:last,:first)").removeClass("cur");
	$("#div_lastspeak span:first").addClass("cur");
	return false;
});

// 日历
// 日历
$("#datestart").calendar(
		{
			format : 'yyyy-MM-dd',
			btnBar : false,
			onSetDate : function() {
				var start = this.getDate('date');
				start = formatDate(new Date(start));
				var $lastspeaktime = $("#lastspeaktime");
				if (start != '') {
					$("#div_lastspeak span:not(:last)").removeClass("cur");
					$lastspeaktime.text('');
					$lastspeaktime.prepend("最后发言：开始时间" + start);
					$lastspeaktime.append($("<img id='closelast' src='"
							+ contextPath + "/resources/images/close.png'>"));
					$lastspeaktime.css("display", "block");
				} else {
					$("#div_lastspeak span:first").addClass("cur");
				}
			}
		});
$("#dateend").calendar(
		{
			format : 'yyyy-MM-dd',
			btnBar : false,
			onSetDate : function() {
				var end = this.getDate('date');
				end = formatDate(new Date(end));
				var $lastspeaktime_end = $("#lastspeaktime_end");
				if (end != '') {
					$("#div_lastspeak span:not(:last)").removeClass("cur");
					$lastspeaktime_end.text('');
					$lastspeaktime_end.prepend("最后发言：结束时间" + end);
					$lastspeaktime_end.append($("<img id='closelast' src='"
							+ contextPath + "/resources/images/close.png'>"));
					$lastspeaktime_end.css("display", "block");
				} else {
					$("#div_lastspeak span:first").addClass("cur");
				}
			}
		});
// 清除按钮
$(".btn5").click(function() {
	// 发言人
	$("#spokesman").css("display", "none").text('');
	$("input[class='input-sty5']").val('');
	// 最后发言
	$("#lastspeaktime").css("display", "none").text('');
	$("#lastspeaktime_end").css("display", "none").text('');
	$("#div_lastspeak span:not(:last,:first)").removeClass("cur");
	$("#div_lastspeak span:first").addClass("cur");
	$("#datestart").val('');
	$("#dateend").val('');

	return false;
});

// 消息搜索按钮
$("#findMessages").click(
		function() {
			// 根据筛选框和搜索输入框搜索消息列表
			var param = getMessageParams();
			var url = getMessagesUrl(param.pageIndex, param.pageSize,
					param.keyWords, param.spokesman, param.lastSpeakingTime,
					param.communityId);
			getMessages(url);
		});
// 切换群按钮,群搜索按钮
$("#switchQun,#findQun").click(function() {
	// 查询当前登录用户管理的群
	var param = getQunParams();
	var url = getCommunitiesUrl(0, 0, param.q);
	getQuns(url);
});
// 给展示的群名称绑定点击事件
$("#groups").on("click", "span", function() {
	var groupid = $(this).attr("id");
	$(".close").click();
	refreshTable(groupid);
	return false;
});

// 获取消息信息的方法
function getMessagesUrl(pageIndex, pageSize, keyWords, spokesMan,
		lastSpeakingTime, communityId) {
	var url = contextPath + "/getPageListMessages.do?pageIndex=" + pageIndex
			+ "&pageSize=" + pageSize;
	if (keyWords) {
		url = url + "&keyWords=" + keyWords;
	}
	if (spokesMan) {
		url = url + "&spokesMan=" + spokesMan;
	}
	if (lastSpeakingTime) {
		url = url + "&lastSpeakingTime=" + lastSpeakingTime;
	}
	if (communityId) {
		url = url + "&communityId=" + communityId;
	}
	return url;
}
// 获取群的方法
function getCommunitiesUrl(pageIndex, pageSize, q) {
	var url = contextPath + "/searchcommunitybyid.do?pageIndex=" + pageIndex
			+ "&pageSize=" + pageSize;
	if (q) {
		url = url + "&q=" + q;
	}
	return url;
}
// 获取消息参数
function getMessageParams() {
	var starttime = subString($("#lastspeaktime").text());
	var endtime = subString($("#lastspeaktime_end").text());
	var lastSpeakingTime = "";
	if (starttime && endtime) {
		lastSpeakingTime = starttime + "-" + endtime;
	} else if (starttime) {
		lastSpeakingTime = starttime + "-";
	} else if (endtime) {
		lastSpeakingTime = "-" + endtime;
	}
	var spokesman = subString($("#spokesman").text());

	var total
	var json = {
		communityId : $("#communityId").val(),
		keyWords : $("#keyword").val(),
		spokesman : spokesman,
		lastSpeakingTime : lastSpeakingTime,
		totalPage : $("#totalpage").text(),
		pageIndex : $("#currentpage").text(),
		pageSize : 20

	};
	return json;
}
// 获取群参数
function getQunParams() {
	// 切换群 参数
	var json = {
		q : $("#QId").val(),
		communityTotalPage : $("#communityTotalPage").val(),
		communityPageIndex : $("#communityPageIndex").val(),
		communityPageSize : 6
	};
	return json;
}

function pageQunBoxStyle() {
	var length = $("#page-num div").length;
	if (length == 5) {
		$("#total").css("margin-left", "43%");
	} else if (length == 6) {
		$("#total").css("margin-left", "37%");
	} else if (length == 7) {
		$("#total").css("margin-left", "31%");
	} else if (length == 8) {
		$("#total").css("margin-left", "25%");
	}
}
function getQuns(url) {
	$
			.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				success : function(jsonStr) {
					var obj = eval("(" + jsonStr + ")");
					var data = obj.data;
					var totalPage = obj.totalPage;
					var pageIndex = obj.pageIndex;
					var total = obj.total;
					// 动态添加管理群的总数
					$("#totalQun").text(total);
					// 动态添加群展示
					$("#groups").html('');
					$
							.each(
									data,
									function(i, n) {
										var divgroup = "";
										divgroup += "<div class='xzqun-c-list clearfix'><img src='"
												+ (n.pic == "" ? (contextPath + "/resources/images/q.jpg")
														: n.pic)
												+ "'><span id='"
												+ n.communityId
												+ "'>"
												+ n.communityName
												+ "</span></div>";
										$("#groups").append(divgroup);
									});
					// 动态添加分页导航栏
					var pageHtml = pageQun(totalPage, pageIndex);
					$("#pagination").html('');
					$("#pagination").append(pageHtml);
					pageQunBoxStyle();
				}
			});
}

function getMessages(url) {
	$
			.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				success : function(jsonStr) {
					var obj = $.parseJSON(jsonStr);
					var data = obj.data;
					var totalPage = obj.totalPage;
					var pageIndex = obj.pageIndex;
					var totalMsg = obj.totalMsg;
					var groupQr = obj.groupQr;
					// var interfaceUrlOut = obj.groupName;
					// 填充群信息
					$("#communityname").text(obj.groupName);
					$("#communityId").val(obj.groupid);
					$("#totalNum").text(obj.count);
					$("#maxNum").text(obj.limit);
					// 更换群二维码
					$("#groupqr").attr("src", groupQr);
					// 动态添加数据
					$("#messagestable").html('');
					var head = "<tr><th>messageID</th><th>发言人账号</th><th>所在群</th><th>发言人群名片</th><th>发言内容</th><th>消息时间</th><th>操作</th></tr>";
					// 清楚视频语音图片消息弹出框
					$("#messagealert").html('');
                     addClose();

					$("#messagestable").append(head);
					$
							.each(
									data,
									function(i, n) {
										var msg = "<tr><td>"
												+ n.messageID
												+ "</td><td>"
												+ n.form
												+ "</td><td>"
												+ n.groupname
												+ "</td><td>"
												+ (typeof (n.cardName) == "undefined" ? ""
														: n.cardName)
												+ "</td><td style='width: 250px; max-width: 250px;'>"
												+ messageCommand(n)
												+ "</td><td>"
												+ n.messageTime
												+ "</td>"
												+ "<td class='ewm-td'><img class='ewmBtn' src='"
												+ contextPath
												+ "/resources/images/cz.png'><div class='erweima-box1'><img src='"
												+ (groupQr == "" ? (contextPath + '/resources/images/erweima1.jpg')
														: groupQr)
												+ "'><p>使用房天下app扫码</p></div></td></tr>";
										$("#messagestable").append(msg);
									});

					// 动态生成页面导航
					var pageHtml = pageMsg(totalMsg, totalPage, pageIndex);
					$("#paginationMsg").html('');
					$("#paginationMsg").append(pageHtml);
				}
			});
}
// 切换群功能的分页方法
function pageQun(totalpage, pageindex) {
	var totalPage = totalpage == 0 ? 1 : totalpage;
	var pageIndex = pageindex;
	var pageHtmlStart = "<input id='communityPageIndex' type='hidden' value='"
			+ pageIndex
			+ "'/><input id='communityTotalPage' type='hidden' value='"
			+ totalPage + "'><div class='pageo-QunBox'>"
			+ "<div class='page-QunBox'>" + "<span id='total'><i>" + pageIndex
			+ "</i>/" + totalPage + "页（6项/页） </span>"
			+ "<div id='page-num' class='page-num'>"
			+ "<div id='firstpage-QunBox' class='sty1'>首页</div>"
			+ "<div id='previouspage-QunBox' class='sty1 '>上一页</div>";

	var pageHtmlEnd = " <div id='nextpage-QunBox' class='sty1 '>下一页</div>"
			+ "<div id='lastpage-QunBox' class='sty1'>尾页</div>" + "</div>"
			+ "</div>" + "</div>";
	var pageHtml = "";
	if (totalPage > 0) {
		if (totalPage <= 3) {
			for (var i = 1; i <= totalPage; i++) {
				if (i == pageIndex) {
					pageHtml = pageHtml + "<div id = 'frontQunBox_" + i
							+ "' class = 'cur' onclick='everyQunBoxPage(" + i
							+ ")'>" + i + "</div >"
				} else {
					pageHtml = pageHtml + "<div id = 'frontQunBox_" + i
							+ "' onclick='everyQunBoxPage(" + i + ")'>" + i
							+ "</div >"
				}
			}
		} else {
			var remainingPage = totalPage - pageIndex;
			var begin = remainingPage > 2 ? pageIndex : totalPage - 2;
			var end = pageIndex + (remainingPage > 2 ? 2 : remainingPage);
			for (var i = begin; i <= end; i++) {
				if (i == pageIndex) {
					pageHtml = pageHtml + "<div id='frontQunBox_" + i
							+ "' class='cur' onclick='everyQunBoxPage(" + i
							+ ")'>" + i + "</div >";
				} else {
					pageHtml = pageHtml + "<div id = 'frontQunBox_" + i
							+ "' onclick='everyQunBoxPage(" + i + ")'>" + i
							+ "</div >"
				}

			}
			if (remainingPage > 2) {
				pageHtml = pageHtml + "<div id='middlePage-QunBox'>…</div>"
			}
		}
	} else {
		pageHtml = "<div class='cur'>1</div>";
	}

	return pageHtmlStart + pageHtml + pageHtmlEnd;
}
// 切换群功能的分页方法
function pageMsg(totalMsg, totalpage, pageindex) {
	var totalPage = totalpage == 0 ? 1 : totalpage;
	var pageIndex = pageindex;
	var totalMsg = totalMsg;
	var pageHtmlStart = "<div class='page'><span>共<i>" + totalMsg + "</i>条记录"
			+ "<i id='currentpage'>" + pageIndex + "</i>/<i id='totalpage'>"
			+ totalPage + "</i>页（20项/页） </span>"
			+ "<div class='page-num clearfix'>"
			+ "<div id='firstpage' class='sty1'>首页</div>"
			+ "<div id='previouspage' class='sty1 '>上一页</div>";

	var pageHtmlEnd = " <div id='nextpage' class='sty1 '>下一页</div>"
			+ "<div id='lastpage' class='sty1'>尾页</div>" + "</div>" + "</div>";
	var pageHtml = "";
	if (totalPage > 0) {
		if (totalPage <= 3) {
			for (var i = 1; i <= totalPage; i++) {
				if (i == pageIndex) {
					pageHtml = pageHtml + "<div id = 'front_" + i
							+ "' class = 'cur' onclick='everyMessagePage(" + i
							+ ")'>" + i + "</div >"
				} else {
					pageHtml = pageHtml + "<div id = 'front_" + i
							+ "' onclick='everyMessagePage(" + i + ")'>" + i
							+ "</div >";
				}
			}
		} else {
			var remainingPage = totalPage - pageIndex;
			var begin = remainingPage > 2 ? pageIndex : totalPage - 2;
			var end = pageIndex + (remainingPage > 2 ? 2 : remainingPage);
			for (var i = begin; i <= end; i++) {
				if (i == pageIndex) {
					pageHtml = pageHtml + "<div id='front_" + i
							+ "' class='cur' onclick='everyMessagePage(" + i
							+ ")'>" + i + "</div >";
				} else {
					pageHtml = pageHtml + "<div id = 'front_" + i
							+ "' onclick='everyMessagePage(" + i + ")'>" + i
							+ "</div >"
				}

			}
			if (remainingPage > 2) {
				pageHtml = pageHtml + "<div id='middlePage'>…</div>"
			}
		}
	} else {
		pageHtml = "<div class='cur'>1</div>";
	}

	return pageHtmlStart + pageHtml + pageHtmlEnd;
}
// 每页点击
function everyMessagePage(i) {
	var params = getMessageParams();
	var currentPageIndex = i;
	var url = getMessagesUrl(currentPageIndex, params.pageSize,
			params.keyWords, params.spokesman, params.lastSpeakingTime,
			params.communityId);
	getMessages(url);

}

// 消息分页事件
function pageClickMsg() {
	// 首页点击
	$("#paginationMsg").on(
			"click",
			"#firstpage",
			function() {
				var params = getMessageParams();
				var currentPageIndex = params.pageIndex;
				if (currentPageIndex != 1) {
					var url = getMessagesUrl(1, params.pageSize,
							params.keyWords, params.spokesman,
							params.lastSpeakingTime, params.communityId);
					getMessages(url);
				}
			});
	// 上一页点击
	$("#paginationMsg").on(
			"click",
			"#previouspage",
			function() {
				var params = getMessageParams();
				var currentPageIndex = params.pageIndex;
				if (currentPageIndex > 1) {
					currentPageIndex--;
					var url = getMessagesUrl(currentPageIndex, params.pageSize,
							params.keyWords, params.spokesman,
							params.lastSpeakingTime, params.communityId);
					getMessages(url);
				}
			});

	// 下一页点击
	$("#paginationMsg").on(
			"click",
			"#nextpage",
			function() {
				var params = getMessageParams();
				var currentPageIndex = params.pageIndex;
				if (currentPageIndex < params.totalPage) {
					currentPageIndex++;
					var url = getMessagesUrl(currentPageIndex, params.pageSize,
							params.keyWords, params.spokesman,
							params.lastSpeakingTime, params.communityId);
					getMessages(url);
				}
			});
	// 尾页点击
	$("#paginationMsg").on(
			"click",
			"#lastpage",
			function() {
				var params = getMessageParams();
				var currentPageIndex = params.pageIndex;
				if (currentPageIndex != params.totalPage) {
					var url = getMessagesUrl(params.totalPage, params.pageSize,
							params.keyWords, params.spokesman,
							params.lastSpeakingTime, params.communityId);
					getMessages(url);
				}
			});
	// ... 点击
	$("#paginationMsg").on(
			"click",
			"#middlePage",
			function() {
				var params = getMessageParams();
				var currentPageIndex = parseInt(params.pageIndex);
				currentPageIndex += 3;
				var url = getMessagesUrl(currentPageIndex, params.pageSize,
						params.keyWords, params.spokesman,
						params.lastSpeakingTime, params.communityId);
				getMessages(url);
			});
}
// 每一页点击
function everyQunBoxPage(i) {
	var params = getQunParams();
	var currentIndex = i;
	var url = getCommunitiesUrl(currentIndex, params.communityPageSize,
			params.q);
	getQuns(url);
}

// 群分页事件
function pageClickQun() {
	// 首页点击
	$("#switchQunDiv").on("click", "#firstpage-QunBox", function() {
		var params = getQunParams();
		var currentPageIndex = params.communityPageIndex;
		if (currentPageIndex != 1) {
			var url = getCommunitiesUrl(1, params.communityPageSize, params.q);
			getQuns(url);
		}
	});
	// 上一页点击
	$("#switchQunDiv").on(
			"click",
			"#previouspage-QunBox",
			function() {
				var params = getQunParams();
				var currentPageIndex = params.communityPageIndex;
				if (currentPageIndex > 1) {
					currentPageIndex--;
					var url = getCommunitiesUrl(currentPageIndex,
							params.communityPageSize, params.q);
					getQuns(url);
				}
			});
	// 下一页点击
	$("#switchQunDiv").on(
			"click",
			"#nextpage-QunBox",
			function() {
				var params = getQunParams();
				var currentPageIndex = params.communityPageIndex;
				if (currentPageIndex < params.communityTotalPage) {
					currentPageIndex++;
					var url = getCommunitiesUrl(currentPageIndex,
							params.communityPageSize, params.q);
					getQuns(url);
				}
			});
	// 尾页点击
	$("#switchQunDiv").on(
			"click",
			"#lastpage-QunBox",
			function() {
				var params = getQunParams();
				var currentPageIndex = params.communityPageIndex;
				if (currentPageIndex != params.communityTotalPage) {
					var url = getCommunitiesUrl(params.communityTotalPage,
							params.communityPageSize, params.q);
					getQuns(url);
				}
			});
	// ...点击
	$("#switchQunDiv").on(
			"click",
			"#middlePage-QunBox",
			function() {
				var params = getQunParams();
				var currentPageIndex = parseInt(params.communityPageIndex);
				currentPageIndex += 3;
				var url = getCommunitiesUrl(currentPageIndex,
						params.communityPageSize, params.q);
				getQuns(url);
			});
}
// 截取字符串
function subString(str) {
	if (str.indexOf("开始时间") >= 0 || str.indexOf("结束时间") >= 0) {
		return str.substring(9, str.length);
	} else if (str.indexOf("最后发言") >= 0) {
		return str.substring(5, str.length);
	} else if (str.indexOf("发言人") >= 0) {
		return str.substring(4, str.length);
	}
}

function formatDate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	return y + '-' + m + '-' + d;
}
// 根据消息类型增加相应的节点
function messageCommand(n) {
	var $msgalert = $("#messagealert");
	if (n.command == "group_video") {
		var $video = $("<video></video>");
		$video.attr("id", n.messageID);
		$video.attr("class", "video-js");
		$video.attr("preload", "auto");
		$video.attr("width", "580px");
		$video.attr("height", "530px");
		$video.attr("controls", "controls");
		$video.attr("src", n.message);
		$video.css("display", "none");

		$msgalert.append($video);

		return "<input type='button' value='点击播放视频' onclick='videoButton("+n.messageID+")'/>";

	}
	if (n.command == "group_img") {
		var $img = $("<img/>");
		$img.attr("id", n.messageID);
		$img.attr("src", n.message);
		$img.css("width", "580px");
		$img.css("height", "530px");
		$img.css("display","none");
		$msgalert.append($img);
		return "<input type='button' value='点击查看图片' onclick='imgButton("+n.messageID+")'/>";
	}
	if (n.command == "group_voice") {
		return "<p>目前PC端不支持收取语音消息，请尝试在房APP内打开</p>";
	}
	return n.message;
}

function refreshTable(groupid) {
	var params = getMessageParams();
	var url = getMessagesUrl(1, params.pageSize, "", "", "", groupid);
	getMessages(url);
}
// 视频、语音播放
function videoButton(messageID){
  $("#"+messageID).css("display","block");
  $("#msgalert").show();
  videojs(document.getElementById(messageID),{},function(){});
}
// 图片展示
function imgButton(messageID){
	  $("#"+messageID).css("display","block");
	  $("#msgalert").show();
}

function addClose(){
	var $close = $("<div></div>");
	$close.attr("id","msgtop").attr("class","top");

	var $span1 = $("<span></span>");
	$span1.attr("class","top-h");
    $close.append($span1);

	var $span2 = $("<span></span>");
	$span2.attr("id","msgclose").attr("class","close");
	$close.append($span2);

	$("#messagealert").append($close);
}
// 消息弹框关闭
$("#messagealert").on("click","#msgclose",function(){
	$("#msgclose").parents('.alert-box').hide();
	$("#messagealert div:has(video)").css("display","none");
	$("#messagealert > img").css("display","none");
});