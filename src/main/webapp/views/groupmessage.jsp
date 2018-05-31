<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<%=contextPath%>/resources/css/community.css" />
<title>群管理</title>
<style>
select {
	font-size: 14px;
	color: #666;
}
</style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<input type='hidden' id='splitPage' value='1'>
<div class="content">
  <div class="content-c">
    <div class="section">
      <div class="dt clearfix">
        <div class="dt-left clearfix"> <span class="cur" >群发消息</span> </div>
        <div class="abs-r"><a href="#">返回房聊群管理></a></div>
      </div>
      <div class="section-c" >
        <div class="outline clearfix">
          <div class="select" type='mainregion'>
            <div class="select-t">请选择大区<i></i></div>
            <div class="select-c" style="display: none;">
              <ul class="clearfix">
                
              </ul>
            </div>
            <input type="hidden" id="mainregion">
          </div>
          <div class="select" type="maincity">
            <div class="select-t">请选择城市<i></i></div>
            <div class="select-c" style="display: none;">
              <ul class="clearfix">
                <li>城市1</li>
                <li>城市2</li>
                <li>城市3</li>
              </ul>
            </div>
            <input type="hidden" id="maincity">
          </div>
          <div class="select" type="maingroupCate">
            <div class="select-t">请选择群分类<i></i></div>
            <div class="select-c" style="display: none;">
              <ul class="clearfix">
             
              </ul>
            </div>
            <input type="hidden" id="maingroupCate">
          </div>
          <input class="input-sty1" type="text" id="projId" placeholder="楼盘ID">
          <input class="input-sty1" type="text" id="groupName" placeholder="群名称">
          <input class="input-sty1" type="text" id="selectgroup" placeholder="群ID">
          <span class="btn searchBtn" id="search"><i></i>搜索</span>
        </div>
      </div>
      <!-- section-c end --> 
      <div class="message">
      	<div class="groupList">
      		<ul>
      		<div id="groupList">
			<p>结果搜索中...</p>						
      		</ul>
      		
			<dl><i></i></dl>
     		<ul id="selectedGroupList">
				<p>已选群（0个）<span>全部移除</span></p>
				<ol >
					
     			</ol>
      		</ul> 
      	</div>
      	<div class="messageSend">
      		<ul><textarea id="mesContent"></textarea></ul>
      		<div class="btn1-o"> <button class="btn1 comSend">发送</button> </div>
      	</div>
      </div>
    </div>
  </div>
  <!-- content-c end --> 
</div>
<input type="hidden" id="regionCityData">
<!-- alert-box end --> 

</body>
</html>

<input type="file" name="uploadImg" id="realuploadImg" style="display: none;" class="upload-input">
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery.ui.widget.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery.xdr-transport.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery.fileupload.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/resources/js/clipboard.min.js"></script> 
 <script type="text/javascript" src="<%=contextPath%>/resources/js/index.js?20170513"></script>
 
 <script type="text/javascript">
 var totalPage = "${totalpage}";
 var pageSize = 20;
 var currentIndex = "${pageindex}"; 
 var isupdate = false;
 var searchtext = "${searchtext}";
 var isdelete = "${isdelete}";
 var selectedGroups=[];//选中的群列表
   $(document).ready(function(){
	   debugger
	   //初始化群分类选项
	   initGropuCate($("div[type='maingroupCate']").find("ul"),"li");
	  	   
	   //大区城市初始化
	   $.ajax({
		   url:"<%=contextPath%>/getAllSysRegion.html",
		   success:function(data){
			   debugger
			   if(data!=null&&data.result=="1"&&data.data!=null){
				   var regionHtml="";
				   var cityHtml="";
				   var regions=data.data;
					$("#regionCityData").val(JSON.stringify(regions));
				   for(var i=0;i<regions.length;i++){
					   if(regions[i].isDelete==false){
						   regionHtml+='<li sysRegionId='+regions[i].sysRegionId+'>'+regions[i].regionName+'</li>';
						   if(i==0){
							   //赋值城市列表 
						   }
					   }
				
				   }
				   $("div[type='mainregion']").find("ul").html(regionHtml);
				   $("div[type='maincity']").find("ul").html(cityHtml);
			   }
		   }
	   });
	   
	   pageGroupList();
	  
   })
   
  

/* 	$('.select-t').click(function(){
		
		var $self = $(this);
		var parent = $self.parent();
		if(parent.find('.select-c').is(':hidden')){
			$('.select-c').hide();
			parent.find('.select-c').show();
		}else{
			parent.find('.select-c').hide();
		}

	});
   $(".select-c").on('click',' li',function(){
		debugger
		var $self = $(this);
		var text = $self.text();
		var parentSelect = $self.parents('.select');
		if(parentSelect.hasClass('special')){
			return;
		}
		parentSelect.find('.select-t').html(text+'<i></i>');
		$self.parents('.select-c').hide();
		
	})  
	
	 $('.select-c').on('click',function(){
		event.stopPropagation();
	})  */
	$('body').click(function(ev){
		/* var strClass = $(ev.target).attr('class'),
			strClass1 = $(ev.target).parent().attr('class');
		if(strClass && strClass.indexOf('select-t') != -1 ||strClass1 && strClass1.indexOf('select-t') != -1){

			return;
		}
		$('.select-c').hide(); */
	}); 

	
  </script> 
 