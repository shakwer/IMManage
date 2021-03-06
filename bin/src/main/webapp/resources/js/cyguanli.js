//下拉框
$('.select-t').click(function(){
	var $self = $(this);
	var parent = $self.parent();
	if(parent.find('.select-c').is(':hidden')){
		$('.select-c').hide();
		parent.find('.select-c').show();
	}else{
		parent.find('.select-c').hide();
	}
	
});
$('body').click(function(ev){
	var strClass = $(ev.target).attr('class'),
		strClass1 = $(ev.target).parent().attr('class');
	if(strClass && strClass.indexOf('select-t') != -1 ||strClass1 && strClass1.indexOf('select-t') != -1){
		
		return;
	}
	$('.select-c').hide();
});
$('.select-c li').on('click',function(){
	
	var $self = $(this);
	var text = $self.text();
	var parentSelect = $self.parents('.select');
	if(parentSelect.hasClass('special')){
		return;
	}
	parentSelect.find('.select-t span').text(text);
	$self.parents('.select-c').hide();
});
$('.select-c').on('click',function(event){
	event.stopPropagation();
})
//tab切换
$('.bottom-border').each(function(index){
	var $self = $(this);
	var selfWidth = $self.parent().find('span:eq(0)').outerWidth();
	$(this).width(selfWidth);
});
$('.dt-left>span').on('click',function(){
	var $self = $(this);
	var border = $self.parent().find('.bottom-border');
	var left = $self.offset().left - $self.parent().offset().left,
		width = $self.outerWidth();
	var index = $self.index();
	$self.parents('.section').find('.section-c').hide();
	$self.parents('.section').find('.section-c:eq('+ index +')').show();
	
	$self.addClass('cur').siblings().removeClass('cur');
	border.stop().animate({
		left:left+'px',
		width:width+'px'
	},300);
});

// 弹框关闭
$('.close').click(function(){
	$(this).parents('.alert-box').hide();
});

//添加成员
$('.addPersonBtn').click(function(){
	$('.addPersonBox').show();
});
$('.addPersonConfirm').click(function(){
	$(this).parents('.alert-box').hide();
});
//展开闭合群成员
$('.c-list-t').click(function(){
	var $self = $(this);
	if($self.parent().hasClass('cur')){
		$self.parent().removeClass('cur');
	}else{
		$self.parent().addClass('cur');
	}
	
});
//编辑
$('.edit').click(function(event){
	event.stopPropagation();
	$(this).parents('tr').find('.changed-input').removeAttr('disabled').addClass('canEdit');
});
$('.changed-input').click(function(event){
	event.stopPropagation();
});
$('body').click(function(){
	$('.changed-input').removeClass('canEdit').attr('disabled','disabled');
});

//禁言btn
function jyBtn(){
	$(this).removeClass('jyBtn').addClass('jcjyBtn').attr('jymove','');
	$('.jyBox').show();
}
$('.jyBtn').on('click',jyBtn);
$('.jyConfirm').click(function(){
	$(this).parents('.alert-box').hide();
	$('.jcjyBtn[jymove]').attr('src','images/jinyan_h.png').attr('title','解除禁言').removeAttr('jymove').off('click',jyBtn).on('click',jcjyBtn);
});
$('.jyCancel').click(function(){
	$(this).parents('.alert-box').hide();
	$('.jcjyBtn[jymove]').removeAttr('jymove').removeClass('jcjyBtn').addClass('jyBtn');
});
$('.jyBox .close').click(function(){
	$(this).parents('.alert-box').hide();
	$('.jcjyBtn[jymove]').removeAttr('jymove').removeClass('jcjyBtn').addClass('jyBtn');
});
//解除禁言
function jcjyBtn(){
	$(this).removeClass('jcjyBtn').addClass('jyBtn').attr('jymove','');
	$('.jcjyBox').show();
}
$('.jcjyBtn').on('click',jcjyBtn);

$('.jcjyConfirm').click(function(){
	$(this).parents('.alert-box').hide();
	$('.jyBtn[jymove]').attr('src','images/jinyan.png').attr('title','禁言').removeAttr('jymove').off('click',jcjyBtn).on('click',jyBtn);
});
$('.jcjyBox .close').click(function(){
	$(this).parents('.alert-box').hide();
	$('.jyBtn[jymove]').removeAttr('jymove').removeClass('jyBtn').addClass('jcjyBtn');
});

//删除按钮
$('.delBtn').on('click',function(){
	$('.delPersonBox').show();
})
$('.delPersonConfirm').click(function(){
	$(this).parents('.alert-box').hide();
});

//切换群
$('.switchQunBtn').click(function(){
	$('.switchQunBox').show();
});

//更多筛选
$('.moreChoice').click(function(event){
	event.stopPropagation();
	var $sxBox = $('.sx-box');
	if($sxBox.is(':hidden')){
		$sxBox.show();
	}else{
		$sxBox.hide();
	}
	
});
$('body').click(function(){
	$('.sx-box').hide();
});
$('.sx-box').click(function(event){
	event.stopPropagation();
});
$('.sxConfirm').click(function(){
	$('.sx-box').hide();
});

//body 最小高度
$('body').css('min-height',$(window).height()+'px');
$(window).resize(function(){
	$('body').css('min-height',$(window).height()+'px');
});