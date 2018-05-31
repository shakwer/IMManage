$(function() {
	getAllCity();
	getAllRole();
	findBind();
	firstPageBind();
	lastPageBind();
	pageFrontBind();
	priviousPageBind();
	nextPageBind();
	middlePageBind();
	getSelectedRoleAndCityAndEmail();
});
// 获取有权限操作的城市
function getAllCity() {
	$('#city_select_all').find('li').each(function() {
		var cityId = $(this).attr('id').split('_')[3];
		if (cityId != "0") {
			allCityIds.push(cityId);
		}
	});
}
// 获取所有有权限操作的角色
function getAllRole() {
	$('#role_select_all').find('li').each(function() {
		var roleId = $(this).attr('id').split('_')[3];
		if (roleId != "0") {
			allRoleIds.push(roleId);
		}
	});
}
// 获取当前查询的角色、城市、邮箱条件(服务端传过来，获取后操作dom元素展示条件)
function getSelectedRoleAndCityAndEmail() {
	if (roleId) {
		var liRole = $('#role_select_li_' + roleId);
		var spanRole = $($('#role_select').children('span').get(0));
		spanRole.attr('id', 'role_select_' + roleId).text(liRole.text());
	}
	if (cityId) {
		var liCity = $('#city_select_li_' + cityId);
		var spanCity = $($('#city_select').children('span').get(0));
		spanCity.attr('id', 'city_select' + cityId).text(liCity.text());
	}
	if (email) {
		$('#email').val(email + '@fang.com');
	}
}

$('#email').on('blur',function(){
     email = $('#email').val();
});
// 绑定条件查询点击事件
function findBind() {
	$('#find').on('click', function() {
		getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
	});
}
// 首页点击事件
function firstPageBind() {
	$('#firstPage').on('click', function() {
		if (currentIndex != 1) {
			currentIndex = 1;
			getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
		}
	});
}
function lastPageBind() {
	$('#lastPage').on('click', function() {
		if (currentIndex != totalPage) {
			currentIndex = totalPage;
			getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
		}
	});
}
function pageFrontBind() {
	$('#pageNum').find('div').each(function(){
		var id=$(this).attr('id');
		if(id){
		if(id.indexOf('front_')!=-1){
			$(this).on('click', function() {
				currentIndex = $(this).text();
				getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
			});
		}
		}
	});
	/*for (var i = currentIndex; i <= currentIndex+2; i++) {
		$('#front_' + i).on('click', function() {
			currentIndex = $(this).text();
			getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
		});
	}*/
}

function priviousPageBind() {
	$('#priviousPage').on('click', function() {
		if (currentIndex > 1) {
			currentIndex--;
			getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
		}
	});
}

function nextPageBind() {
	$('#nextPage').on('click', function() {
		if (currentIndex < totalPage) {
			currentIndex++;
			getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
		}
	});
}

function middlePageBind() {
	$('#middlePage').on('click', function() {
		currentIndex += 3;
		getPageUserInfo(currentIndex, pageSize, roleId, cityId, email);
	});
}
// 跳转刷新页面方式获取分页数据
function getPageUserInfo(pageIndex, pageSize, roleId, cityId) {
	var url = contextPath + 'permission.html?pageIndex=' + pageIndex
			+ '&pageSize=' + pageSize;
	if (roleId) {
		url += '&roleId=' + roleId;
	}
	if (cityId) {
		url += '&cityId=' + cityId;
	}
	if (email) {
		url += '&email=' + email;
	}
	window.location.href = url;
}

// ������
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
	var idValue = $self.attr('id').replace(/li_/g, "");
	var parentSelect = $self.parents('.select');
	if (parentSelect.hasClass('special')) {
		return;
	}
	parentSelect.find('.select-t span').text(text);
	parentSelect.find('.select-t span').attr('id', idValue);
	$self.parents('.select-c').hide();

	// 获取条件查询，当前选中的城市和角色(每次单选框变化，都改变全局变量)
	var spanCity = $($('#city_select').children('span').get(0));
	var selectCityId = spanCity.attr('id').split('_')[2];
	cityId = selectCityId;

	var spanRole = $($('#role_select').children('span').get(0));
	var selectRoleId = spanRole.attr('id').split('_')[2];
	roleId = selectRoleId;

	//添加用户时，判断角色是否选中了"超级管理员"，如果选中了，则默认所有城市被选中。
	var spanRoleAdd=$($('#role_add').children('span').get(0));
	var roleAddName=spanRoleAdd.text();
	if(roleAddName=='超级管理员'){
		$('input[name="city_add"]').each(function(){
			$(this).attr("checked", true);
			$(this).attr("disabled", true);
		});
	}else{
		$('input[name="city_add"]').each(function(){
			$(this).attr("checked", false);
			$(this).attr("disabled", false);
		});
	}

	//修改用户角色时，如果修改为超级管理员，则默认添加所有剩余没有添加的城市
	var spanRoleUpdate=$($('#role_update').children('span').get(0));
	var roleUpdateName=spanRoleUpdate.text();
	if(roleUpdateName=='超级管理员'){
		$('input[name="city_updateToAdd"]').each(function(){
			$(this).attr("checked", true);
			$(this).attr("disabled", true);
		});
	}else{
		$('input[name="city_updateToAdd"]').each(function(){
			$(this).attr("checked", false);
			$(this).attr("disabled", false);
		});
	}

	/*
	 * var spanCityUpdate = $($('#city_update').children('span').get(0)); var
	 * selectCityIdUpdate = spanCityUpdate.attr('id').split('_')[2]; if
	 * (selectCityId != 0) { cityId = selectCityId; } var spanCityUpdate =
	 * $($('#role_update').children('span').get(0)); var selectRoleIdUpdate =
	 * spanCityUpdate.attr('id').split('_')[2]; if (selectRoleId != 0) { roleId =
	 * selectRoleId; }
	 */

});
$('.select-c').on('click', function(event) {
	event.stopPropagation();
})
// tab�л�
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

// ����ر�
$('.close').click(function() {
	$(this).parents('.alert-box').hide();
});

// �༭
/*
 * $('.edit').click(function(event){ event.stopPropagation();
 * $(this).parents('tr').find('.changed-input').removeAttr('disabled').addClass('canEdit');
 * });
 */
$('.changed-input').click(function(event) {
	event.stopPropagation();
});
$('body').click(function() {
	$('.changed-input').removeClass('canEdit').attr('disabled', 'disabled');
});

// ɾ��ť
$('.delBtn').on('click', function() {
	var userId = $(this).parent().parent().children('td').get(0).innerHTML;
	$('#user_delete').val(userId);
	$('.delPersonBox').show();
})
//删除用户
$('.delPersonConfirm').click(function() {
	$(this).parents('.alert-box').hide();
	var userId=$('#user_delete').val();
	$.post(contextPath + 'deleteUser.do',{"userId":userId},function(result){
		if(result.result=="success"){
			getPageUserInfo(currentIndex,pageSize);
			alert("删除成功");
		}else{
			alert(result.message);
		}
	});
});

// 点击修改按钮
$('.edit').on('click',function() {
					$('#city_haveOpen').children().remove();
					$('#city_canAdd').children().remove();
					$('#city_canAdd').html('');
					$('#role_update').empty();
					var edit = $(this);
					var userId = $(this).parent().parent().children('td').get(0).innerHTML;
					$.post(
									contextPath + 'getUserByUserId.do',
									{
										"userId" : userId
									},
									function(result) {
										if (result.result == "success") {
											$('#userId').val(result.user.sysUserId);
											$('#email_update').val(result.user.email);
											$('#realName_update').val(result.user.sysUserRealName);
											$('#role_update').append('<span id="role_update_'+result.user.sysRole.sysRoleId+'">'+result.user.sysRole.roleName+'</span><i></i>');
											// 显示已经开通城市
											if (result.user.sysCities) {
												var haveOpenCities = result.user.sysCities;
												for (var i = 0; i < haveOpenCities.length; i++) {
													var span = '<span><a class="city_canDelete" style="color:red;" value="'
															+ haveOpenCities[i].sysCityId
															+ '" href="#" id="city_candeldte_'
															+ haveOpenCities[i].sysCityId
															+ '">'
															+ haveOpenCities[i].cityName;
													if (i == (haveOpenCities.length - 1)) {
														$('#city_haveOpen').append(span+'</a></span>');
													} else {
														$('#city_haveOpen').append(span+',</a></span>');
													}
													if ((i + 1) % 8 == 0) {
														$('#city_haveOpen').append('</br>');
													}
												}
												if(result.user.sysRole.roleName!='超级管理员'){
												$('.city_canDelete')
														.on('click',function() {
																	var currentA = $(this);
																	var cityId_delete = currentA
																			.attr('value');
																	if (confirm('确定删除用户对城市"'
																			+ $(this).html().replace(/,/,"")+'"的权限吗?')) {
																		$.post(
																						contextPath
																								+ 'deleteCity.do',
																						{
																							"userId" : userId,
																							"cityId" : cityId_delete
																						},
																						function(
																								result) {
																							if (result.result == "success") {
																								currentA.remove();
																							} else {
																								alert(result.message);
																							}
																						});
																	}
																});
												}
											}
											// 显示未开通城市
											if (result.canAddCities) {
												var canAddCities = result.canAddCities;
												for (var i = 0; i < canAddCities.length; i++) {
													var checkBox = '<input name="city_updateToAdd" type="checkbox" value="'
															+ canAddCities[i].sysCityId
															+ '" />'
															+ canAddCities[i].cityName;
													$('#city_canAdd').append(checkBox);
													if ((i + 1) % 7 == 0) {
														$('#city_canAdd').append('</br>');
													}
												}
											}
											$('#alert-edit').show();
										} else {
											alert(result.message);
										}
									});
				});
// 修改用户信息和权限
$('#updateUserAction').on('click', function() {
	var realName = $('#realName_update').val();
	var roleSpan = $($('#role_update').children('span').get(0));
	var roleId = roleSpan.attr('id').split('_')[2];
	var cityIdsAdd=[];
	$('input[name="city_updateToAdd"]:checked').each(function(){
		cityIdsAdd.push($(this).val());
	});
	var obj={
			"userId":$('#userId').val(),
			"roleId":roleId,
			"realName":realName,
			"citiesAdd":cityIdsAdd
			};
	$.ajax({
		type : "post",
		url : contextPath + 'updateUserInfo.do',
		contentType : "application/json",
		data : JSON.stringify(obj),
		dataType : "json",
		success : function(result) {
			if (result.result == "success") {
				$('#alert-edit').hide();
				alert("更新成功");
				getPageUserInfo(currentIndex, pageSize);
			} else {
				alert(result.message);
			}
		}
	});
});
$('.editCancel').click(function() {
	$(this).parents('.alert-box').hide();
});

// body ��С�߶�
$('body').css('min-height', $(window).height() + 'px');
$(window).resize(function() {
	$('body').css('min-height', $(window).height() + 'px');
});

$('#addUser').on('click', function() {
	$('#email_add').val('');
	$('#realName_add').val('');
	var spanRoleAdd=$($('#role_add').children('span').get(0));
	spanRoleAdd.attr('id','');
	spanRoleAdd.text('');
	$('input[name="city_add"]').each(function(){
		$(this).attr("checked", false);
		$(this).attr("disabled", false);
	});
	$('#alert-add').show();
});

$('#addUserAction').on('click', function() {
	var email;
	var realName;
	var cityIds = [];
	var roleId;
	email = $('#email_add').val();
	realName = $('#realName_add').val();
	if (!email) {
		alert('邮箱不能为空');
		return;
	}
	if (!realName) {
		alert('真实姓名不能为空');
		return;
	}

	var spanRoleAdd = $($('#role_add').children('span').get(0));
	var spanRoleId = spanRoleAdd.attr('id');
	if (!spanRoleId) {
		alert('请选择角色');
		return;
	}
	roleId = spanRoleId.split('_')[2];

	var isSelect=false;
	$('input[name="city_add"]:checked').each(function(){
		cityIds.push($(this).val());
		isSelect=true;
	});
	if(!isSelect){
		alert('请选择城市');
		return;
	}

//	var spanCityAdd = $($('#city_add').children('span').get(0));
//	var spanCityId = spanCityAdd.attr('id');
//	if (!spanCityId) {
//		alert('请选择城市');
//		return;
//	}
//	var cityId = spanCityId.split('_')[2];
//	if (cityId == 0) {
//		cityIds = allCityIds;
//	} else {
//		cityIds = [ cityId ];
//	}
	var obj = {
		"email" : email,
		"realName" : realName,
		"cityIds" : cityIds,
		"roleId" : roleId
	};
	$.ajax({
		type : "post",
		url : contextPath + 'addUser.do',
		contentType : "application/json",
		data : JSON.stringify(obj),
		dataType : "json",
		success : function(result) {
			if (result.result == "success") {
				$('#alert-add').hide();
				alert("添加成功");
				getPageUserInfo(1, pageSize);
			} else {
				alert(result.message);
			}
		}
	});

});
