$(function() {
	findBind();
	firstPageBind();
	lastPageBind();
	pageFrontBind();
	priviousPageBind();
	nextPageBind();
	middlePageBind();
	cutBind();
	addBind();
});
// tab�л�
$('.bottom-border').each(function(index) {
	var $self = $(this);
	var selfWidth = $self.parent().find('span:eq(0)').outerWidth();
	$(this).width(selfWidth);
});
// 头部群转换标记
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

// 关闭
$('.close').click(function() {
	isupdate = false;
	isallrec = false;
	isalldelete = false;
	// 清除所有
	$(this).parents('.alert-box').hide();
});
// 移交列表
$('.c-list-t').click(function() {
	var $self = $(this);
	if ($self.parent().hasClass('cur')) {
		$self.parent().removeClass('cur');
	} else {
		$self.parent().addClass('cur');
	}

});

// 数据展示相关

// 时间排序
$('#order').click(
		function() {
			if (order == "desc") {
				order = "asc";
			} else if (order == "asc") {
				order = "desc";
			} else {
				order = "asc";
			}
			if (currentIndex != null && currentIndex != "") {
				var url = contextPath + '/community.html?pageindex='
						+ currentIndex + '&orderstr=' + order;
			} else {
				var url = contextPath + '/community.html' + '?orderstr='
						+ order;
			}

			window.location.href = url;
		});
// 删除列表时间排序
$('#delorder').click(
		function() {
			if (order == "desc") {
				order = "asc";
			} else if (order == "asc") {
				order = "desc";
			} else {
				order = "asc";
			}
			if (currentIndex != null && currentIndex != "") {
				var url = contextPath + '/community.html?isdelete=1&pageindex='
						+ currentIndex + '&orderstr=' + order;
			} else {
				var url = contextPath + '/community.html?isdelete=1&orderstr='
						+ order;
			}
			window.location.href = url;
		});

// 修改群信息相关

// 搜索相关

// 添加
$('#addusersearch').click(function() {
	var name = "";
	name = $('#addinput').val();
	if (name.trim().length > 0) {
		$.post("/searchpassuser.do", {
			"name" : name
		}, function(data) {
			if (data != "") {
				// $("#unadduser").empty();
				var name = data;
				var div = $("<div></div>");
				div.attr("class", "item clearfix");
				var img = $("<img\>");
				img.attr("src", contextPath + "/resources/images/ph.jpg");
				var span = $("<span></span>");
				span.text(name);
				var ii = $("<i></i>");
				div.append(img);
				div.append(span);
				div.append(ii);
				bindingRecenetMove(div);
				if ($('#unadduser>div:eq(0)').length > 0) {
					div.insertBefore($('#unadduser>div:eq(0)'));
				} else {
					$("#unadduser").append(div);
				}

			}
		});
	} else {
		alert("请输入关键字");
	}

});

// 删除

// 拉人
$('#singleaddusersearch').click(function() {
	debugger
	var name = "";
	debugger
	name = $('#singleaddinput').val();
	if (name.trim().length > 0) {
		$.post(contextPath + "/searchpassuser.do", {
			"name" : name
		}, function(data) {
			debugger
			if (data != "") {
//				 $("#singleunadduser").empty();
				var name = data;
				var div = $("<div></div>");
				div.attr("class", "item clearfix");
				var img = $("<img\>");
				img.attr("src", contextPath + "/resources/images/ph.jpg");
				var span = $("<span></span>");
				span.text(name);
				var ii = $("<i></i>");
				div.append(img);
				div.append(span);
				div.append(ii);
				bindingSingleAddMove(div);
				if ($('#singleunadduser>div:eq(0)').length > 0) {
					div.insertBefore($('#singleunadduser>div:eq(0)'));
				} else {
					$("#singleunadduser").html(div);
				}

			}
		});
	} else {
		alert("请输入关键字");
	}
});

// 转移
$('#transferusersearch').click(function() {
	var name = "";
	name = $('#transferinput').val();
	if (name.trim().length > 0) {
		$.post(contextPath + "/searchuser.do", {
			"name" : name,
			"cityid" : transcityid
		}, function(data) {
			if (data != "") {
				$("#transferuser").empty();
				var i = 0, length = data.length;
				if (length < 1) {
					alert('未查到用户');
				}
				for (; i < data.length; i++) {
					var name = data;
					var div = $("<div></div>");
					div.attr("class", "item clearfix");
					var img = $("<img\>");
					img.attr("src", contextPath + "/resources/images/ph.jpg");
					var span = $("<span></span>");
					span.text(name);
					var ii = $("<i onclick='showZhuanyi();'></i>");
					div.append(img);
					div.append(span);
					div.append(ii);
					bindingTransfer(div);
					$("#transferuser").append(div);
				}

			}
		});
	} else {
		alert("请输入关键字");
	}
});

// 查询按钮组
// 转让人检查

function bindisexist($self, text) {
	var islimit = false;
	$self.each(function() {
		var thistext = $(this).text();
		if (thistext == text) {
			islimit = true;
		}
	});

	return islimit;
};

// 创建群
// 添加人的切换
function bindingSingleAddMove(elem) {
	elem.click(function() {
		var parentName = elem.parent().attr("id");
		if (parentName == "singleadduser") {
			var count = $('.singlecount>i:eq(0)').text();
			count = +count - 1;
			$('.singlecount>i:eq(0)').text(count);
			elem.find('i:eq(0)').removeClass("sc");
			$("#singleunadduser").append(elem);
		} else if (parentName == "singleunadduser") {

			if (!bindisexist($($('#singleadduser span')), $(this).find(
					"span:eq(0)").text())) {
				var count = $('.singlecount>i:eq(0)').text();
				count = +count + 1;
				$('.singlecount>i:eq(0)').text(count);
				elem.find('i:eq(0)').addClass("sc");
				$("#singleadduser").append(elem);
			}
		} else {
			$('#transferuser .item').removeClass('cur');
			$(this).addClass('cur');
		}

	});
}

function addBind() {
	var canadd = true;
	// 添加按钮
	$('.addQunBtn').click(
			function() {
				$('#addnotice').attr("value", "");
				$('#addnotice').attr("value", "");
				$('#addlimit').find('.tli-item').removeClass('cur');
				$('#addlimit').find('.tli-item:eq(0)').addClass('cur');
				$('#addlimit').find('.tli-item').removeClass('cur');
				$('#addlimit').find('.tli-item:eq(0)').addClass('cur');

				// 初始化大区城市类别筛选
				initGropuCate($("select[type='groupCate']"), "option");
				initRegionCity($("select[type='region']"),
						$("select[type='city']"), "option", "no")
				$('.addBox').show();
			});
	// 添加下一步
	$('.addNext').click(
			function() {
				canadd = true;
				addcategory = $('#addcategory').find('.tli-item.cur>span')
						.text();
				if ($('#uploadgroupimg')[0].src.length > 50) {
					addpic = $('#uploadgroupimg')[0].src;
				}
				addlimit = $('#addlimit').find('.tli-item.cur>span').text();
				addnotice = $('#addnotice').val();
				var spanCity = $($('#city_add').children('span').get(0));
				var selectCityId = spanCity.attr('id').split('_')[2];
				addcity = selectCityId;
				addcommunityname = $('#addname').val();
				if (addcommunityname == null || addcommunityname == "") {
					alert("群名称不能为空");
					canadd = false;
				} else if (addcommunityname.length < 2
						|| addcommunityname.length > 10) {
					alert("群名称为2到10位字符");
					canadd = false;
				}
				if (addcity == null || addcity == "" || addcity == 0) {
					alert("城市不能为空");
					canadd = false;
				}
				if (addcategory == "新房") {
					addcategory = 1;
				}
				if (addcategory == "二手房") {
					addcategory = 2;
				}
				if (addnotice.length > 0) {
					if (addnotice.length < 15 || addnotice.length > 300) {
						alert("群公告为15到300位字符");
						canadd = false;
					}
				}

				if (canadd) {
					getRecentMembers();
					var $self = $(this);
					var $parentAlert = $self.parents('.alert');
					$parentAlert.find('.tab-c-item:eq(0)').hide();
					$parentAlert.find('.tab-c-item:eq(1)').show();
					$parentAlert.find('.tab-t>div:eq(1)').addClass('cur')
							.siblings().removeClass('cur');
					$parentAlert.find('.btn1-o:eq(1)').show();
					$parentAlert.find('.btn1-o:eq(0)').hide();
				}

			});
	// 添加上一步
	$('.addPre').click(
			function() {
				var $self = $(this);
				var $parentAlert = $self.parents('.alert');
				$parentAlert.find('.tab-c-item:eq(0)').show();
				$parentAlert.find('.tab-c-item:eq(1)').hide();
				$parentAlert.find('.tab-t>div:eq(0)').addClass('cur')
						.siblings().removeClass('cur');
				$parentAlert.find('.btn1-o:eq(1)').hide();
				$parentAlert.find('.btn1-o:eq(0)').show();
			});

	// 添加完成
	$('.comCreate').click(
			function() {
				var grouplist = "";
				$('#adduser span').each(function() {
					grouplist += $(this).text() + ",";
				});
				if (grouplist != "") {
					grouplist = grouplist.substring(0, grouplist.length - 1);
					canadd = true;
				} else {
					alert("缺少群成员");
					canadd = false;
				}
				if (canadd) {
					var url = contextPath + '/addcommunity.do?communityname='
							+ addcommunityname + '&city=' + addcity
							+ '&category=' + addcategory + '&grouplist='
							+ grouplist + '&notice=' + addnotice + '&limit='
							+ addlimit + '&pic=' + addpic;
					$.ajax({
						type : "get",
						url : url,
						dataType : "json",
						success : function(data) {
							if (data.result == "200") {
								// alert("添加成功");
								$('.alert-suc.addcomname>span:eq(1)').text(
										data.data.communityName);
								$('.alert-suc.addcomid>span:eq(1)').text(
										data.data.communityId);
								var qrcode = data.qrcode;
								$('div.addcomqr').find('img:eq(0)').attr("src",
										qrcode.split(',')[0]);
								$('#copylink').attr("data-clipboard-text",
										qrcode.split(',')[1]);
								$('.jiathis_button_loadimage').attr("href",
										qrcode.split(',')[0]);
								$('.addSuc').show();
								$('.addBox').hide();
								$(this).parents('.alert-box').hide();
							} else {
								alert(data.message);
							}
						}
					});
				}

			});

	$('.Create').click(
			function() {
				var selRegion = $("select[type='region']").find(
						"option:selected");
				var selCity = $("select[type='city']").find("option:selected");
				var selGroupCate = $("select[type='groupCate']").find(
						"option:selected");
				var groupName = $("#addname").val().trim();
				var projectId = $("#addId").val().trim();
				debugger
				if (selRegion.val() == null || selRegion.val() == undefined
						|| selRegion.val() == '' || isNaN(selRegion.val())
						|| selRegion.text() == ''
						|| selRegion.text() == '请选择大区') {
					alert("大区不能为空");
					return;
				}
				if (selCity.val() == null || selCity.val() == undefined
						|| selCity.val() == '' || isNaN(selCity.val())
						|| selCity.text() == '' || selCity.text() == '请选择城市') {
					alert("城市不能为空");
					return;
				}
				if (selGroupCate.val() == null
						|| selGroupCate.val() == undefined
						|| selGroupCate.val() == ''
						|| isNaN(selGroupCate.val())
						|| selGroupCate.text() == ''
						|| selGroupCate.text() == '请选择群分类') {
					alert("群分类不能为空");
					return;
				}
				if ($("#addId").parent().parent().is(":visible")
						&& (projectId == null || projectId == undefined
								|| projectId == '' || isNaN(projectId))) {
					alert("楼盘id不能为空")
					return;
				}
				if (groupName == null || groupName == undefined
						|| groupName == '') {
					alert("群名不能为空")
					return;
				}
				var addlimit = 0;
				var limtObj = $("#addlimit").find(".cur");
				if (limtObj != null && limtObj != undefined
						&& !isNaN(limtObj.text())) {
					addlimit = limtObj.text().trim();
				}
				var addnotice = $("#addnotice").val().trim();

				var addpic=$("#uploadgroupimg").attr("src").trim();
				var url = contextPath + '/addcommunity.do?communityname='+groupName+'&city=' + selCity.val()+ '&category='+ selGroupCate.val() + '&projectid=' + projectId+ '&notice=' +addnotice  + '&limit=' + addlimit+ '&pic=' + addpic;
				var data="{communityname:\""+escape(groupName) +"\",city:\""+selCity.text()+"\",category:"+selGroupCate.val()+",projectid:"+projectId+",notice:\""+addnotice+"\",limit:"+addlimit+",pic:\""+addpic+"\"}"

				$.ajax({
					type : "get",
					url : url,
					dataType:"json",
					contentType:"application/json;charset=UTF-8",
					success : function(data) {
						if (data.result == "200") {
							// alert("添加成功");
							$('.alert-suc.addcomname>span:eq(1)').text(
									data.data.communityName);
							$('.alert-suc.addcomid>span:eq(1)').text(
									data.data.communityId);
							var qrcode = data.qrcode;
							$('div.addcomqr').find('img:eq(0)').attr("src",
									qrcode.split(',')[0]);
							$('#copylink').attr("data-clipboard-text",
									qrcode.split(',')[1]);
							$('.jiathis_button_loadimage').attr("href",
									qrcode.split(',')[0]);
							$('.addSuc').show();
							$('.addBox').hide();
							$(this).parents('.alert-box').hide();
						} else {
							alert(data.message);
						}
					}
				});
			})
	// 大区筛选城市
	$(".tab-c-item").on("change", "select[type='region']", function() {
		debugger
		changeInitCity($(this), $("select[type='city']"), "option");
	})
}

function changeInitCity(regionObj, cityObj, type) {
	debugger
	var regionDataStr = $("#regionCityData").val();
	if ((regionDataStr == null || regionDataStr == undefined)
			&& $(this).find("option").length > 0) {
		alert("请刷新后重试")
		return;
	}
	var selectObj;
	var sysRegionId;
	if (type == 'option') {
		selectObj = $(regionObj).find("option:selected");
		sysRegionId = selectObj.val();
	} else if (type = "li") {
		selectObj = $(regionObj);
		sysRegionId = $(regionObj).attr("sysregionid");
	} else {
		return;
	}

	var jsonData = JSON.parse(regionDataStr);
	if (jsonData == null || jsonData == undefined || jsonData.length < 1) {
		return;
	}

	for (var i = 0; i < jsonData.length; i++) {
		if (jsonData[i].sysRegionId == sysRegionId
				&& jsonData[i].regionName == selectObj.text()) {
			// 更新城市炫酷
			var cities = jsonData[i].citys;
			if (cities != null && cities != undefined) {
				var cityHtml = "";
				if (type == 'option') {
					cityHtml='<option value="">请选择城市</option>'
					for (var j = 0; j < cities.length; j++) {
						cityHtml += "<option value=" + cities[j].sysCityId
								+ ">" + cities[j].cityName + "</option>"
					}
				} else {
					$(cityObj).parent().parent().find("div[class='select-t']").html('请选择城市<i></i>')
					for (var j = 0; j < cities.length; j++) {
						cityHtml += "<li sysCityId=" + cities[j].sysCityId
								+ ">" + cities[j].cityName + "</li>"
					}
				}

			}
			$(cityObj).html(cityHtml);
		}
	}
}
// 筛选群分类

$(".tab-c-item").on("change", "select[type='groupCate']", function() {
	debugger
	var selectObj = $(this).find("option:selected");
	if (selectObj.val() == "6" && selectObj.text() == "楼盘群") {
		$("#addId").parent().parent().show();
	} else {
		$("#addId").parent().parent().hide();
	}

})
// 添加后分享
$('.addSucConfirm').click(function() {
	$(this).parents('.alert-box').hide();
	if (currentIndex != 1) {
		currentIndex = 1;
	}
	getPageGroupInfo(currentIndex, 0);
});

// span切换
$('.tli-item i').click(function() {
	$(this).parent().parent().find('.cur').removeClass('cur');
	$(this).parent().addClass("cur");
});

// 城市选择
// 阻止事件冒泡
$('.add-c').on('click', function(event) {
	event.stopPropagation();
});
// 城市展示
$('.add-t').click(function() {
	var $self = $(this);
	var parent = $self.parent();
	if (parent.find('.add-c').is(':hidden')) {
		$('.add-c').hide();
		parent.find('.add-c').show();
	} else {
		parent.find('.add-c').hide();
	}

});
// 创建时城市选择
$('.add-c li').on('click', function() {

	var $self = $(this);
	var text = $self.text();
	var idValue = $self.attr('id').replace(/li_/g, "");
	var parentSelect = $self.parents('.addcity');
	if (parentSelect.hasClass('special')) {
		return;
	}
	parentSelect.find('.add-t span').text(text);
	parentSelect.find('.add-t span').attr('id', idValue);
	$self.parents('.add-c').hide();

});

// 分类选择
// 阻止事件冒泡
$('.up-catg').on('click', function(event) {
	event.stopPropagation();
});
// 分类选择
$('.up-catg li').on('click', function() {

	var $self = $(this);
	var text = $self.text();
	var idValue = $self.attr('id').replace(/li_/g, "");
	var parentSelect = $self.parents('.selectcatg');
	if (parentSelect.hasClass('special')) {
		return;
	}
	parentSelect.find('.up-t span').text(text);
	parentSelect.find('.up-t span').attr('id', idValue);
	// 选择按钮
	$('.up-catg li').each(function() {
		$(this).css('display', 'initial');
	});
	$self.hide();
	$self.parents('.up-catg ').hide();

});

// tab�л�
$('.bottom-border').each(function(index) {
	var $self = $(this);
	var selfWidth = $self.parent().find('span:eq(0)').outerWidth();
	$(this).width(selfWidth);
});
// 单个删除按钮
$('.delete.delBtn').click(function() {
	delcommunityid = $(this).parent().parent().children('td').get(2).innerHTML;
	$('.delBox').show();
});

// 批量删除按钮
$('.btn-sty1.delBtn').click(function() {
	if ($('#tbody1').find('i').hasClass("cur")) {
		isalldelete = true;
		$('.delBox').show();
	}
});
// 彻底删除按钮
$('.realDelBtn').click(function() {
	$('.realDelBox').show();
});
// 正常群多选框点击事件
$('#tbody1 i').click(function() {
	if ($(this).hasClass('cur')) {
		$(this).removeClass('cur');
	} else {
		$(this).addClass('cur');
	}

});
// 删除群多选框点击事件
$('#tbody2 i').click(function() {
	if ($(this).hasClass('cur')) {
		$(this).removeClass('cur');
	} else {
		$(this).addClass('cur');
	}

});
// 拉人入群按钮
$('.singleAdd')
		.click(
				function() {
					debugger
					$('#singleaddinput').attr("value", "");
					singleaddid = $(this).parent().parent().children('td').get(
							2).innerHTML;
					singleaddholder = $(this).parent().parent().children('td')
							.get(5).innerHTML;

					getSingleAddUsers();
					debugger;
					$('.singleAddBox').show();
					console.log("singleAdd");
				});
// 拉人确认按钮
$('.singleAddConfirm').click(
		function() {
			// 获取指定的链接
			$('#singleadduser span').each(function() {
				singleaddimuser += $(this).text() + ",";
			});
			if (singleaddimuser != "") {
				singleaddimuser = singleaddimuser.substring(0,
						singleaddimuser.length - 1);
			}
			if (singleaddimuser.trim().length > 0) {
				debugger
				var url = contextPath + '/singleaddser.do?communityid='
						+ singleaddid + '&communityholder=' + singleaddholder
						+ "&imuser=" + singleaddimuser;
				$.ajax({
					type : "get",
					url : url,
					dataType : "json",
					success : function(data) {
						if (data.result == "200") {
							alert("更新成功");
							getPageGroupInfo(currentIndex, 0);
						} else {
							alert(data.message);
						}

					},
					error : function(error) {
						alert("fuck")
					}
				});
			} else {
				alert("请添加成员");
			}

		});

// 最近联系人的切换
function bindingRecenetMove(elem) {
	elem.click(function() {
		var parentName = elem.parent().attr("id");
		if (parentName == "adduser") {

			var count = $('.addlistcount>i:eq(0)').text();
			count = +count - 1;
			$('.addlistcount>i:eq(0)').text(count);
			elem.find('i:eq(0)').removeClass("sc");
			$("#unadduser").append(elem);

		} else if (parentName == "unadduser") {
			if (!bindisexist($($("#adduser span")), $(this).find("span:eq(0)")
					.text())) {
				var count = $('.addlistcount>i:eq(0)').text();
				count = +count + 1;
				$('.addlistcount>i:eq(0)').text(count);
				elem.find('i:eq(0)').addClass("sc");
				$("#adduser").append(elem);
			}
		}

	});
}

// 点击事件
function bindingTransfer(elem) {
	elem.click(function() {
		var parentName = elem.parent().attr("id");
		$('#' + parentName + ' .item').removeClass('cur');
		$(this).addClass('cur');

	});
}
// 通过im用户名查询最近联系人
function getRecentMembers() {
	$.get(contextPath+"/getimuser.do", function(data) {
		$("#adduser").empty();
		$("#unadduser").empty();
		var i = 0, length = data.length;
		for (; i < data.length; i++) {
			var name = data[i].split(',')[0];
			var div = $("<div></div>");
			div.attr("class", "item clearfix");
			var img = $("<img\>");
			img.attr("src", contextPath + "/resources/images/ph.jpg");
			var span = $("<span></span>");
			span.text(name);
			var ii = $("<i></i>");
			div.append(img);
			div.append(span);
			div.append(ii);
			bindingRecenetMove(div);
			$("#unadduser").append(div);
		}

	});

};
// 获取可转让群成员
function getTransferUsers() {

	$.post(contextPath + "/getlocaluser.do", {
		"pageindex" : 1
	}, function(data) {
		$("#transferuser").empty();
		var i = 0, length = data.length;
		if (length < 1) {
			alert('未查到用户');
		}
		for (; i < data.length; i++) {
			var name = data[i].split(',')[0];
			var div = $("<div></div>");
			div.attr("class", "item clearfix");
			var img = $("<img\>");
			img.attr("src", contextPath + "/resources/images/ph.jpg");
			var span = $("<span></span>");
			span.text(name);
			var ii = $("<i onclick='showZhuanyi();'></i>");
			div.append(img);
			div.append(span);
			div.append(ii);
			bindingTransfer(div);
			$("#transferuser").append(div);
		}
	});

};

// 获取单独添加群成员
function getSingleAddUsers() {
	debugger
	$.get(contextPath + "/getimuser.do", function(data) {
		debugger
		$("#singleunadduser").empty();
		$("#singleadduser").empty();
		if (data == null || data == undefined) {
			alert('未查到用户');
			return;
		}
		var i = 0, length = data.length;
		if (length < 1) {
			return;
			alert('未查到用户');
		}
		for (; i < data.length; i++) {
			var name = data[i].split(',')[0];
			var div = $("<div></div>");
			div.attr("class", "item clearfix");
			var img = $("<img\>");
			img.attr("src", contextPath + "/resources/images/ph.jpg");
			var span = $("<span></span>");
			span.text(name);
			var ii = $("<i></i>");
			div.append(img);
			div.append(span);
			div.append(ii);
			bindingSingleAddMove(div);
			$("#singleunadduser").append(div);
		}
	});

};

// 移交人切换
$('.zr .item').on('click', function() {
	$('.zr .item').removeClass('cur');
	$(this).addClass('cur');
});
// 移交人展示
$('.zrBtn').click(
		function() {
			transcommunityholder = "";
			transcommunityholder = $('#transferuser').find(
					".item.cur>span:eq(0)").text().split('(')[1];
			var isuuu = "liio(jinshilei)";
			var newisuu = isuuu.split('(')[1];
			transcommunityholder = transcommunityholder.substr(0,
					transcommunityholder.length - 1);
			$('.zrConfirmBox').show();
		});
function showZhuanyi() {
	transcommunityholder = "";
	transcommunityholder = $('#transferuser').find(".item.cur>span:eq(0)")
			.text().split('(')[1];
	//var isuuu = "liio(jinshilei)";
	//var newisuu = isuuu.split('(')[1];
//	alert('aa--' + transcommunityholder);
//	if(transcommunityholder == null){
//		transcommunityholder = "";
//	}
//	alert('bb--' + transcommunityholder);
	transcommunityholder = transcommunityholder.substr(0,
			transcommunityholder.length - 1);
	$('.zrConfirmBox').show();
}

// 移交完成按钮
$('.zrConfirmBtn').click(
		function() {
			if (transcommunityholder.trim().length > 0) {
				var url = contextPath + '/transfercommunity.do?communityid='
						+ transcommunityid + '&communityholder='
						+ transcommunityholder;
				$.ajax({
					type : "get",
					url : url,
					dataType : "json",
					success : function(data) {
						if (data.result == "200") {
							alert("转移成功");
							getPageGroupInfo(currentIndex, 0);
						} else {
							alert(data.message);
						}
					}
				});
			} else {
				alert("请选择移交人");
			}

		});
// 移交群按钮
$('.getout')
		.on(
				'click',
				function() {
					getTransferUsers();

					transcommunityid = $(this).parent().parent().children('td')
							.get(2).innerHTML;
					var $cityself = $($(this).parent().parent().children('td')
							.get(6));
					transcityid = $cityself.attr('id').split('_')[1];
					oldholder = $(this).parent().parent().children('td').get(5).innerHTML;
					$('.zr').show();
				});

// 修改方法
$('.edit').click(
		function(event) {
			debugger
			isupdate = true;
			// 只允许一个一个修改
			$('.changed-input.canEdit').removeClass('canEdit').attr('disabled',
					'disabled');
			$('.up-t.canUpdate').removeClass('canUpdate');
			event.stopPropagation();
			var $trself = $(this).parents('tr');
			debugger
			$trself.find('.changed-input').removeAttr('disabled').addClass(
					'canEdit');
			$trself.find('.up-t').addClass('canUpdate');
			updatecommunityid = "";
			updatecommunityname = "";
			updatecategory = "";
			imgStr="";
			
			// 修改格式

			updatecommunityid = $trself.children('td').get(2).innerHTML;
			updatecommunityname = $trself.children('td:eq(3)').find(
					'.changed-input').val();
			imgStr= $trself.children('td:eq(3)').find("img").attr("src");
			var $catgelem = $trself.find('div.up-t>span:eq(0)');
		
			updatecategory = $catgelem.attr('id').split('_')[2];
			var cateName=$catgelem.html();
			var city=$trself.children('td').get(6).innerHTML;
			var countPer=$trself.children('td').get(7).innerHTML;
			var reginoName=$trself.attr("regionName");
			var projId=$trself.attr("projId");
			var notice=$trself.attr("notice")
			
			var updateObj=$('.updateBox').find(".editBox-text");
			updateObj.find("img").attr("src",imgStr);
			updateObj.find("i[type='countPer']").html(countPer)
			updateObj.find("gg[type='regionInfo']").html("第一大区"+" "+city+" "+cateName);
			updateObj.find("p[type='groupInfo']").html("群ID "+updatecommunityid+"");
			updateObj.find("p[type='projectInfo']").html("楼盘ID "+projId)
			$("#upname").val(updatecommunityname);
			$("#upnotice").val(notice);
			
			$trself.find('.up-catg>li').each(function() {
				$(this).show();
			});
			$trself.find('#catg_up_li_' + updatecategory + '').hide();
			// 防止冒泡
			$('.up-t.canUpdate').on('click', function(event) {
				event.stopPropagation();
			});
			// 绑定点击事件
			$('.up-t.canUpdate').bind("click", function() {
				event.stopPropagation();
				var $self = $(this);
				var parent = $self.parent();
				if (parent.find('.up-catg').is(':hidden')) {
					$('.up-catg').hide();
					parent.find('.up-catg').show();
				} else {
					parent.find('.up-catg').hide();
				}
			});
			$('.updateBox').show();
		});


$('.comUpdate')
.click(
		function() {
			debugger
			isupdate = true;
			// 刷新单条
			if (isupdate) {
				var endname = "";
				var upnotice = "";
				var endpic = "";
				endname = $('#upname').val();
				upnotice = $('#upnotice').val();
				endpic = $('#uploadImgEdit').attr("src");

//				if (endname.trim().length < 2
//						|| endname.trim().length > 10) {
//					alert("群名称为2到10位字符");
//					isupdate = false;
//				}
//				if (endname == updatecommunityname) {
//					endname = "";
//				}
//				if (upnotice == updatenotice) {
//					endcategory = "";
//				}
//				if (endpic == updatepic) {
//					endpic = "";
//				}
				// 去除项目本身的img
				if (endpic.indexOf('resources/images/') > 0) {
					endpic = "";
				}

			}

			if (isupdate) {
				$('.updateBox').hide();
				var url = contextPath+'/updatecommunity.do?communityid='
						+ updatecommunityid + '&communitynotice='
						+ upnotice + '&communityname=' + endname
						+ '&communitypic=' + endpic;
				$.ajax({
					type : "get",
					url : url,
					dataType : "json",
					timeout : 10 * 1000,
					success : function(data) {
						if (data.result == "200") {
							alert("更新成功");
						} else {
							alert(data.message);
						}
						getPageGroupInfo(currentIndex, 0);
					},
					error : function() {
						alert("服务器开小差了,请重试");
					}
				});

			}

		});
$('body').click(
		function(ev) {
			// 获取.changed-input以及canedit,调用接口保存，并修改其页面值
			if (isupdate) {
				var endname = "";
				var endcategory = "";
				// 解绑事件
				$('#btn').unbind("click");
				$('.changed-input.canEdit').each(
						function(index) {
							var $self = $(this);
							var selfWidth = $self.parent().find('span:eq(0)')
									.outerWidth();
							$(this).width(selfWidth);
						});
				endname = $('.groupname-input.canEdit').val();
				var $endcategory = $('.up-t.canUpdate').find('span:eq(0)');
				endcategory = $endcategory.attr('id').split('_')[2];

				if (endname == updatecommunityname
						&& endcategory == updatecategory) {
					isupdate = false;
				}
				if (endname.trim().length < 2 || endname.trim().length > 10) {
					alert("群名称为2到10位字符");
					// 恢复原来
					$('.groupname-input.canEdit').attr("value",
							updatecommunityname);
					isupdate = false;
				}
				if (endname == updatecommunityname) {
					endname = "";
				}
				if (endcategory == updatecategory) {
					endcategory = "";
				}

			}

			if (isupdate) {

				var url = contextPath+'/updatecommunity.do?communityid='
						+ updatecommunityid + '&category=' + endcategory
						+ '&communityname=' + endname;
				$.ajax({
					type : "get",
					url : url,
					dataType : "json",
					success : function(data) {
						if (data.result == "200") {
							alert("更新成功");
						} else {
							alert(data.message);
						}
						getPageGroupInfo(currentIndex, 0);
					}
				});

			}

			$('.changed-input').removeClass('canEdit').attr('disabled',
					'disabled');
			$('.up-t').removeClass('canUpdate');
			isupdate = false;

			var strClass = $(ev.target).attr('class'), strClass1 = $(ev.target)
					.parent().attr('class');
			if (strClass && strClass.indexOf('select-t') != -1 || strClass1
					&& strClass1.indexOf('select-t') != -1) {

				return;
			}
			$('.select-c').hide();
		});

// 分享
$('.share').on('click', function() {
	var tr = $(this).parent().parent();

	var divid = "";
	if (isdelete != null && isdelete == 1) {
		divid = 'divshare_' + tr.children('td').eq(2).html();
	} else {
		divid = 'divshare_' + tr.children('td').eq(2).html();
	}
	$('#' + divid + '').show();
});
//
$('.realDelConfirm').click(function() {
	$(this).parents('.alert-box').hide();
});
// 删除群
$('.delConfirm').click(
		function() {
			if (isalldelete) {
				var communityid = "";
				$('#tbody1').find('i.cur').each(
						function() {
							communityid = $(this).parent().parent().children(
									'td').get(2).innerHTML
									+ "," + communityid;
						});
				communityid = communityid.substr(0, communityid.length - 1);
				$.ajax({
					type : "get",
					url : contextPath + '/delcommunity.do?communityid='
							+ communityid + '&isdelete=1',
					success : function(data) {
						if (data.result == "200") {
							alert("删除成功");
							$(this).parents('.alert-box').hide();
						} else {
							alert(data.message);
						}
						getPageGroupInfo(currentIndex, 0);
					}
				});

				isalldelete = false;
			} else {

				$.ajax({
					type : "get",
					url : contextPath + '/delcommunity.do?communityid='
							+ delcommunityid + '&isdelete=1',
					success : function(data) {
						if (data.result == "200") {
							alert("删除成功");
							$(this).parents('.alert-box').hide();
						} else {
							alert(data.message);
						}
						getPageGroupInfo(currentIndex, 0);
					}
				});
			}

		});
// 单个恢复
$('.ibtn.huifuQun')
		.click(
				function() {
					huifucommunityid = $(this).parent().parent().children('td')
							.get(2).innerHTML;
					$('.huifuBox').show();
				});
// 批量恢复
$('.btn.huifuQun').click(function() {
	if ($('#tbody2').find('i').hasClass("cur")) {
		isallrec = true;
		$('.huifuBox').show();
	}

});
$('.huifuConfirm').click(
		function() {

			if (isallrec) {
				var communityid = "";
				$('#tbody2').find('i.cur').each(
						function() {
							communityid = $(this).parent().parent().children(
									'td').get(2).innerHTML
									+ "," + communityid;
						});
				communityid = communityid.substr(0, communityid.length - 1);
				// alert(communityid);
				var obj = "communityid=" + communityid + "&isdelete=0";
				var url = '/reccommunity.do?' + obj;
				$.ajax({
					type : "get",
					url : url,
					success : function(data) {
						if (data.result == "200") {
						} else {
							alert(data.message);
						}
						getPageGroupInfo(currentIndex, 1);
					}
				});

				isallrec = false;
			} else {
				// 恢复
				var obj = "communityid=" + huifucommunityid + "&isdelete=0";
				var url = '/reccommunity.do?' + obj;
				$.ajax({
					type : "get",
					url : url,
					success : function(data) {
						if (data.result == "200") {
							alert("恢复成功");
							getPageGroupInfo(currentIndex, 1);
						} else {
							alert(data.message);
						}
					}
				});
				$(this).parents('.alert-box').hide();

			}
		});

// body css
$('body').css('min-height', $(window).height() + 'px');
$(window).resize(function() {
	$('body').css('min-height', $(window).height() + 'px');
});

// 页面头切换
function cutBind() {
	$('#grouplist').on(
			'click',
			function() {
				if (currentIndex != null && currentIndex != "") {
					var url = contextPath + '/community.html?pageindex='
							+ currentIndex;
				} else {
					var url = contextPath + '/community.html';
				}
				window.location.href = url;
			});
	$('#delgrouplist').on(
			'click',
			function() {
				if (currentIndex != null && currentIndex != "") {
					var url = contextPath
							+ '/community.html?isdelete=1&pageindex='
							+ currentIndex;
				} else {
					var url = contextPath + '/community.html?isdelete=1';
				}
				// window.location.href = url;
				window.location.href = url;
			});
}

// 绑定条件查询点击事件
function findBind() {
	$('#find').on(
			'click',
			function() {
				/*
				 * var
				 * region=$("div[type='mainregion]").find(".select-t").text();
				 * var city=$("div[type='maincity]").find(".select-t").text();
				 * var
				 * groupname=$("div[type='mainregion]").find(".select-t").text();
				 * debugger var communityname = $('#selectgroup').val();
				 */
				// searchPage(1, communityname, 0);
				debugger
				var communityid = $("#selectgroup").val().trim();
				var communityname = $("#communityname").val().trim();
				var projectid = $("#projectid").val().trim();
				var regionId = $("#mainregion").val().trim();
				var categoryId = $("#maingroupCate").val().trim();
				var cityId = $("#maincity").val().trim();
				debugger
				var param = "communityid=" + communityid + "&communityname="
						+ communityname + "&projectid=" + projectid
						+ "&regionId=" + regionId + "&category=" + categoryId
						+ "&cityId=" + cityId;
				searchPageNew(1,param)
			});
	$('#delfind').on('click', function() {
		var communityname = $('#selectdelgroup').val();
		searchPage(1, communityname, 1);
	});
}

function searchPageNew(pageindex,param, isdelete) {

//	var url = contextPath + '/searchcommunity.do?' + param + '&pageindex='+ pageindex;
	if(param==''){
		var url = contextPath + '/searchcommunity.do?pageindex='+ pageindex;
	}else{
		var url = contextPath + '/searchcommunity.do?pageindex='+ pageindex+ param ;
	}
	
	if (isdelete != null && isdelete != "") {
		if (isdelete == 1) {
			url = url + '&isdelete=1';
		}
	}
	window.location.href = url;
}
function pageManage(pageIndex,isdelete){
	debugger
	searchPageNew(pageIndex,searchText(), 0);
}
function searchText(){
	var communityid=getUrlParam("communityid");
	var communityname=getUrlParam("communityname");
	var projectid=getUrlParam("projectid");
	var regionId=getUrlParam("regionId");
	var category=getUrlParam("category");
	var cityId=getUrlParam("cityId");

	var param ='';
	if(communityid!=''&&communityid!=null){
		param="communityid=" + communityid ;
	}
	if(communityname!=''&&communityname!=null){
		param+="&communityname="+ communityname;
	}
	if(projectid!=''&&projectid!=null){
		param+="&projectid=" + projectid;
	}
	if(regionId!=''&&projectid!=null){
		param+="&regionId=" + regionId ;
	}
	if(category!=''&&category!=null){
		param+="&category=" + category;
	}
	if(cityId!=''&&cityId!=null){
		param+="&cityId=" + cityId;
	}
	return param;
}
function getUrlParam(name) {  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象  
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数  
    if (r != null) return unescape(r[2]); return null; //返回参数值  
}  
// 群搜索方法
function searchPage(pageindex, communityname, isdelete) {
	debugger
	var url = contextPath + '/searchcommunity.do?communityname='
			+ communityname + '&pageindex=' + pageindex;
	if (isdelete != null && isdelete != "") {
		if (isdelete == 1) {
			url = contextPath + '/searchcommunity.do?communityname='
					+ communityname + '&pageindex=' + pageindex + '&isdelete=1';
		}
	}
	window.location.href = url;
	// window.open(url);
}

// 分页
function firstPageBind() {
	$("div").on('click', '#firstPage',function() {
		debugger
		if (currentIndex != 1) {
			currentIndex = 1;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined||objPageSplit.val()!=1){
				if (searchText() != null && searchText() != "") {
//					searchPage(currentIndex, searchtext, isdelete);
					pageManage(currentIndex,isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
				
		
		}
	});
	$('#delfirstPage').on('click', function() {
		if (currentIndex != 1) {
			currentIndex = 1;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined||objPageSplit.val()!=1){
				if (searchtext != null && searchtext != "") {
					searchPage(currentIndex, searchtext, isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
			

		}
	});
}
function lastPageBind() {
	$('div').on('click','#lastPage', function() {
		debugger
		if (currentIndex != totalPage) {
			currentIndex = totalPage;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				
				if (searchText() != null && searchText() != "") {
//					searchPage(currentIndex, searchtext, isdelete);
					pageManage(currentIndex,isdelete);
				} else {
					debugger
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
		}
	});
	$("div").on('click','#dellastPage', function() {
		if (currentIndex != totalPage) {
			currentIndex = totalPage;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				if (searchText() != null && searchText() != "") {
					searchPage(currentIndex, searchtext, isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
		}
	});
}
function pageFrontBind() {
	debugger
	for (var i = 1; i <= totalPage; i++) {
		$("div").on('click', '#front_' + i,function() {
			debugger
			console.log('#front_' + i);
			currentIndex = $(this).text();
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				if (searchText() != null && searchText() != "") {
//					searchPage(currentIndex, searchtext, isdelete);
					pageManage(currentIndex,isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
				
			}else{
				searchSplitPage(currentIndex);
			}
			
		});
		$("div").on('click','#delfront_' + i, function() {
			debugger
			currentIndex = $(this).text();
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				if (searchtext != null && searchtext != "") {
					searchPage(currentIndex, searchtext, isdelete);
					
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
		});
	}
}

function priviousPageBind() {
	$('div').on('click','#priviousPage', function() {
		if (currentIndex > 1) {
			currentIndex--;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				if (searchText() != null && searchText() != "") {
//					searchPage(currentIndex, searchtext, isdelete);
					pageManage(currentIndex,isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
			
		}
	});
	$("div").on('click','#delpriviousPage', function() {
		if (currentIndex > 1) {
			currentIndex--;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				if (searchtext != null && searchtext != "") {
					searchPage(currentIndex, searchtext, isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
		
		}
	});
}

function nextPageBind() {
	$("div").on('click','#nextPage', function() {
		if (currentIndex < totalPage) {
			currentIndex++;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				debugger
				if (searchText() != null && searchText() != "") {
					
					pageManage(currentIndex,isdelete);
//					searchPage(currentIndex, searchtext, isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
			
		}
	});
	$("div").on('click','#delnextPage', function() {
		if (currentIndex < totalPage) {
			currentIndex++;
			var objPageSplit=$("#splitPage");
			if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
				if (searchtext != null && searchtext != "") {
					searchPage(currentIndex, searchtext, isdelete);
				} else {
					getPageGroupInfo(currentIndex, isdelete);
				}
			}else{
				searchSplitPage(currentIndex);
			}
			
		}
	});
}

function middlePageBind() {
	$('#middlePage').on('click', function() {
		currentIndex += 3;
		var objPageSplit=$("#splitPage");
		if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
			if (searchText() != null && searchText() != "") {
//				searchPage(currentIndex, searchtext, isdelete);
				pageManage(currentIndex,isdelete);
			} else {
				getPageGroupInfo(currentIndex, isdelete);
			}
		}else{
			searchSplitPage(currentIndex);
		}
		
	});
	$('#delmiddlePage').on('click', function() {
		currentIndex += 3;
		var objPageSplit=$("#splitPage");
		if(objPageSplit==null||objPageSplit==undefined ||objPageSplit.val()!=1){
			if (searchtext != null && searchtext != "") {
				searchPage(currentIndex, searchtext, isdelete);
			} else {
				getPageGroupInfo(currentIndex, isdelete);
			}
		}else{
			searchSplitPage(currentIndex);
		}
		
	});
}
// 跳转刷新页面方式获取分页数据
function getPageGroupInfo(pageindex, isdelete) {
	debugger
	var url = contextPath + '/community.html?pageindex=' + pageindex;

	if (isdelete != null && isdelete == 1) {
		url += '&isdelete=1';
	}

	window.location.href = url;
}

/**
 * 群发消息跳转
 * 
 * @returns
 */
$(document).on("click", ".messageBtn", function() {
	window.location.href = contextPath + "/groupmessage.html";
})
/**
 * 消息列表
 * 
 * @returns
 */
$("td").on("click", ".message", function() {
	debugger
	var obj = $(this).parents("tr");
	if (obj == null || obj == undefined) {
		return;
	}
	var id = $(obj).attr("qunid");
	var url = contextPath + "/message.html";
	var _form = $("<form></form>", {
		'id' : 'goMessageList',
		'method' : 'POST',
		'action' : url,
		'target' : "_self",
		'style' : 'display:none'
	}).appendTo($("body"));

	_form.append($("<input>", {
		'type' : 'hidden',
		'name' : 'communityId',
		'value' : id
	}));
	// 触发提交事件
	_form.trigger("submit");
	// 表单删除
	_form.remove();
})

$('.changed-input').click(function(event) {
	event.stopPropagation();
});

// body css样式
$('body').css('min-height', $(window).height() + 'px');
$(window).resize(function() {
	$('body').css('min-height', $(window).height() + 'px');
});

// 初始化群分类
function initGropuCate(obj, tag) {
	debugger
	if (obj == null || obj == undefined) {
		return;
	}
	$.ajax({
		url : contextPath + "/getAllSysCommunityCategory.html",
		success : function(data) {
			if (data != null && data.result == "1" && data.data != null) {
				var html = "";
				var communties = data.data;
				for (var i = 0; i < communties.length; i++) {
					if (communties.isDelete != "false") {
						switch (tag) {
						case "li": {
							html += "<li categoryId="
									+ communties[i].categoryId + ">"
									+ communties[i].categoryName + "</li>"
							break;
						}
						case "option": {
							html += "<option value=" + communties[i].categoryId
									+ ">" + communties[i].categoryName
									+ "</option>"
							break;
						}
						}
					}

				}
				$(obj).append(html);
			}
		}

	});

}

/**
 * 初始化大区城市
 * 
 * @param obj
 * @param tag
 * @param extraHtml
 * @returns
 */
function initRegionCity(regionObj, cityObj, tag, needInitCity) {
	if (regionObj == null || regionObj == undefined || cityObj == null
			|| cityObj == undefined) {
		return;
	}
	debugger
	// 大区城市初始化
	$.ajax({
		url : contextPath + "/getAllSysRegion.html",
		success : function(data) {
			debugger
			if (data != null && data.result == "1" && data.data != null) {
				var regionHtml = "";
				var cityHtml = "";

				var regions = data.data;
				$("#regionCityData").val(JSON.stringify(regions));
				for (var i = 0; i < regions.length; i++) {
					if (regions[i].isDelete == false) {
						switch (tag) {
						case "li": {
							regionHtml += '<li sysRegionId='
									+ regions[i].sysRegionId + '>'
									+ regions[i].regionName + '</li>';
							break;
						}
						case "option": {
							regionHtml += '<option value='
									+ regions[i].sysRegionId + '>'
									+ regions[i].regionName + '</option>';
							break;
						}
						}
						// 是否需要初始化城市
						if (needInitCity != null && needInitCity != undefined
								&& needInitCity.toLowerCase() == "initcity") {
							debugger
							if (i == 0) {
								// 赋值城市列表
								var cities = regions[i].citys;
								if (cities != null && cities != undefined) {
									for (var j = 0; j < cities.length; j++) {
										if (cities[j].isDelete == false) {
											switch (tag) {
											case "li": {
												cityHtml += '<li sysCityId='
														+ cities[j].sysCityId
														+ '>'
														+ cities[j].cityName
														+ '</li>';
												break;
											}
											case "option": {
												cityHtml += '<option value='
														+ cities[j].sysCityId
														+ '>'
														+ cities[j].cityName
														+ '</option>';
												break;
											}
											}
										}
									}
								}
								$(cityObj).append(cityHtml)
							}
						}

					}

				}
				$(regionObj).append(regionHtml);
			}
		}
	})
}
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
/**
 * 选择下拉框 
 * @returns
 */
$(".select-c").on('click', ' li', function() {
	debugger
	var $self = $(this);
	var text = $self.text();
	var parentSelect = $self.parents('.select');
	if (parentSelect.hasClass('special')) {
		return;
	}
	initIdData($self);

	parentSelect.find('.select-t').html(text + '<i></i>');
	$self.parents('.select-c').hide();

})
/**
 * 初始化数据
 * @param obj
 * @returns
 */
function initIdData(obj) {
	debugger
	if (obj == null || obj == undefined) {
		return;
	}
	var text = $(obj).text();
	var parentObj = $(obj).parent().parent().parent();
	var type = $(parentObj).attr("type");
	if (parentObj == null || parentObj == undefined || type == null
			|| type == undefined) {
		return;
	}
	switch (type) {
	case "mainregion": {
		var sourceText = $(parentObj).find("div[class='select-t']").text();
		if (sourceText != text) {
			changeInitCity($(obj), $("div[type='maincity']").find("ul"), "li");
		}
		$("#mainregion").val($(obj).attr("sysregionid"));// 选择大区id
		break;
	}
	case "maincity": {
		$("#maincity").val($(obj).attr("syscityid"));// 选择城市id
		break;
	}
	case "maingroupCate": {
		$("#maingroupCate").val($(obj).attr("categoryid"));// 选择群分类id
		break;
	}
	default:
		break;
	}
}

$('.select-c').on('click', function() {
	event.stopPropagation();
})




/**
 * 局部加载分页数据
 * @param dataParam
 * @returns
 */
function pageGroupList(dataParam){
	var data;
	if(dataParam==null||dataParam==undefined){
		data=''
	}else{
		data=dataParam;
	}

	   $.ajax({
		url : contextPath + "/pagegroup.html?" + dataParam,
		type : 'get',
		success : function(data) {
			debugger
			$("#groupList").html(data)
			// 绑定页面页码点击事件
			for (var i = 1; i <= totalPage; i++) {
				$('#front_' + i).on('click', function() {
					debugger
					console.log('#front_' + i);
					currentIndex = $(this).text();
					searchSplitPage(currentIndex);
				});
			}
		}
	})
}

/**
 * 搜索局部加载分页
 * @returns
 */
$("#search").on("click",function(){
	$("#groupList").html("<p>结果搜索中...</p>	")
	searchSplitPage(1);
	})
	
/**
 * ajax 局部分页
 * @param pageIndex
 * @param isdelete
 * @returns
 */
function searchSplitPage(pageIndex,isdelete){
	$("#groupList").html("<p>结果搜索中...</p>	")
	var communityid=$("#selectgroup").val().trim();
	var communityname=$("#groupName").val().trim();
	var projectid=$("#projId").val().trim();
	
	var cityId=$("#maincity").val().trim();
	var category=$("#maingroupCate").val().trim();
	var regionId=$("#mainregion").val().trim();
	if(isNaN(communityid)||isNaN(projectid)||isNaN(cityId)||isNaN(regionId)){
		alert("查询条件错误");
		return ;
	}
	
   var dataParam="communityid="+communityid+"&communityname="+communityname+"&projectid="+projectid+"&cityId="+cityId+"&category="+category+"&regionId="+regionId+"&pageindex=" + pageIndex;
   if (isdelete != null && isdelete != ""&&isdelete == 1) {
	   dataParam+="&isdelete=1";
		}
	pageGroupList(dataParam);
}
/**
 * 单个选中checkbox选中群
 * @returns
 */
$(document).on("click","input[type='checkbox']",function(){
	debugger
	var classes=$(this).attr("class");
	if(classes.indexOf("all")>=0){
		//全选
		allCheckBoxOpt($(this))
	}else if(classes.indexOf("single")>=0){
		//单选
		var groups=singleCheckBoxOpt($(this));
		showSelectGroups(groups);
	}
});

/**
 * 全选checkbox push或pull元素
 * @returns
 */
function allCheckBoxOpt(obj){
	var liObj=$("#groupList").find("li");
	
	for(var i=0;i<liObj.length;i++){
		var checkObj=$(liObj[i]).find("input[type='checkbox']");
		if($(obj).attr("checked")){
			$(checkObj).attr("checked",true);
		}else{
			$(checkObj).attr("checked",false);
		}
		
		showSelectGroups(singleCheckBoxOpt(checkObj));
	}
};
/**
 * 单击checkbo push 或pull 元素
 * @param obj
 * @returns
 */
function singleCheckBoxOpt(obj){
	var liObj=$(obj).parent().parent();
	var aimObj=new Object();
	
	aimObj.communityId=$(liObj).attr("communityId");
	aimObj.communityName=$(liObj).attr("communityName");
	aimObj.cateName=$(liObj).attr("catename");
	aimObj.city=$(liObj).attr("city");
	aimObj.cateName=$(liObj).attr("cateName");
	aimObj.regionName=$(liObj).attr("regionName");
	aimObj.projectId=$(liObj).attr("projectId");
	aimObj.imgSrc=$(liObj).find("img").attr("src");
	var groupId=[];
	debugger
	for(var i=0;i<selectedGroups.length;i++){
		groupId.push(selectedGroups[i].communityId);
	}
	
	debugger
	if($(obj).attr("checked")){	
		//选中 且已经选择
		if($.inArray(aimObj.communityId,groupId)<0){
			selectedGroups.push(aimObj)
		}
	}else{
		//非选中
		var _arry=[];
		for(var i=0;i<selectedGroups.length;i++){
			if(selectedGroups[i].communityId!=aimObj.communityId){
				_arry.push(selectedGroups[i]);
			}
		}
		selectedGroups=  _arry;
	}
	return selectedGroups;
};

/**
 * 选中列表展示选中的群组
 * @param selectedGroups
 * @returns
 */
function showSelectGroups(selectedGroups){
	var html="";
	for(var i=0;i<selectedGroups.length;i++){
		var item=selectedGroups[i];
		html+='<li id='+item.communityId+'><span><img src="'+item.imgSrc+'"></span><dt><font>'+
		item.city+' '+ item.cateName+' '+item.communityName+'(ID:'+item.communityId+')</font><em>'+
		item.regionName+'/'+item.city+'/'+item.projectId+'</em></dt><dd><i></i></dd>';
	}
	$("#selectedGroupList").find("ol").html(html);
	$("#selectedGroupList").find("p").html('已选群（'+selectedGroups.length+'个）<span>全部移除</span>');
};
/**
 * 删除全部已选元素
 * @returns
 */
$("#selectedGroupList").on("click","span",function(){
	$("#selectedGroupList").find("ol").html("");
	$("#selectedGroupList").find("p").html('已选群（0个）<span>全部移除</span>');
	selectedGroups=[];
	//取消左侧的选择checkbox
	$("#groupList").find("input[type='checkbox']").each(function(){
		$(this).attr("checked",false);
	})
});
/**
 * 单个删除
 * @returns
 */
$("#selectedGroupList").on("click","li i",function(){
	var liObj=$(this).parent().parent();
	var _arry=[];
	for(var i=0;i<selectedGroups.length;i++){
		if(selectedGroups[i].communityId!=$(liObj).attr("id")){
			_arry.push(selectedGroups[i]);
		}
	}
	selectedGroups=  _arry;
	
	$(liObj).remove();
	$("#selectedGroupList").find("p").html('已选群（'+selectedGroups.length+'个）<span>全部移除</span>');
	
	//删除左侧选择
	$("#groupList li").find("input[type='checkbox']").each(function(){
		debugger
		if($(this).parent().parent().attr("communityid")==$(liObj).attr("id")){
			$(this).attr("checked",false);
		}
	})
	//假如选择的群组为0 取消全选
	if(selectedGroups.length==0){
		$("#groupList p").find("input[type='checkbox']").attr("checked",false);
	}
});

/**
 * 发送消息
 * @returns
 */
$(".messageSend").on("click","button",function(){
	if(selectedGroups==null||selectedGroups==undefined||selectedGroups.length==0){
		alert("请选择发送消息群");
		return;
	}
	var messageContent=$("#mesContent").val().trim();
	if(messageContent==""){
		alert("请填写发送消息体");
		return;
	}
	var groupId='';
	debugger
	for(var i=0;i<selectedGroups.length;i++){
		if(i==selectedGroups.length-1){
			groupId+=selectedGroups[i].communityId
		}else{
			groupId+=selectedGroups[i].communityId+','
		}
		
	}
	var param="communityids="+groupId+"&message="+messageContent;
	debugger
	$.ajax({
		url : contextPath + "/sendMessage.do?"+param,
		type : 'get',
		success : function(data) {
		   alert(data.message);
		   $("#mesContent").val("");
		},
		error:function(){
			 alert("操作失败");
		}
	})
});
