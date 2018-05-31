<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/resources/css/calendar/lhgcalendar.css" />
<link rel="stylesheet" href="<%=contextPath%>/resources/css/message/page-QunBox.css" />
<link rel="stylesheet" href="<%=contextPath%>/resources/css/video/video-js.css" />
<title>消息管理</title>
</head>
<body>
  <!-- header文件 -->
  <jsp:include page="header.jsp"></jsp:include>
  <!-- content start -->
  <div class="content">
    <div class="content-c">
      
      <div class="section">
      <div class="dt clearfix">
        
          <div class="dt-left clearfix">
            <span id="communityname" class="cur"></span> <input id="communityId" type="hidden" value="${communityId}" />
            <div class="bottom-border"></div>
          </div>
          <div id="switchQun" class="btn4 switchQunBtn">切换群</div>
          <div class="abs-r">
            群成员人数：<span id="totalNum"></span>/<span id="maxNum"></span>
          <a  onClick="javascript :history.back(-1);">返回房聊群管理&gt;</a> 
          </div>
        </div>
        <div class="section-c">
          <div class="outline clearfix">
            <div class="ewm-btn">
              <span>扫二维码进群</span>
              <div class="erweima-box">
                <!-- 根据群id获取群二维码 -->
                <img id="groupqr">
                <p>使用房天下app扫码</p>
              </div>
            </div>
            <div class="outline-right">
              <input id="keyword" class="input-sty1" type="text" name="" placeholder="搜索关键词"> <span
                id="findMessages" class="btn" style="margin-right: 0; padding: 0 20px;">搜索</span> <span
                class="btn-sty1 moreChoice" style="margin-left: 15px; margin-right: 0; padding: 0 5px;">更多筛选<i></i></span>
              <!-- 筛选框 start -->
              <div class="sx-box">
                <div class="lit-cover"></div>
                <div class="sx-box-item clearfix">
                  <div class="left">最后发言：</div>
                  <div id="div_lastspeak" class="right clearfix">
                    <span class="cur">不限</span> <span>1个月以内</span> <span>1-3个月以内</span> <span>3-6个月以内</span> <span>6-12个月以内</span>
                    <span>12个月以上</span> <input id="datestart" type="text" style="width: 85px;" placeholder="开始时间" /> <span
                      style="margin-right: 2px;">-</span> <input id="dateend" type="text" style="width: 85px;"
                      placeholder="结束时间" />
                  </div>
                </div>
                <div class="sx-box-item clearfix" style="margin-top: 9px;">
                  <div class="input-item1 clearfix">
                    <div class="left">发言人：</div>
                    <div class="right">
                      <input class="input-sty5" type="text" value="">
                    </div>
                  </div>
                </div>
                <div class="sx-box-item special clearfix">
                  <div class="left" style="position: relative; top: 3px;">筛选条件：</div>
                  <div class="right clearfix">
                    <span id="spokesman" style="display: none;"><img id="closeman"
                      src="<%=contextPath%>/resources/images/close.png"></span> <span id="lastspeaktime"
                      style="display: none;"><img id="closelast"
                      src="<%=contextPath%>/resources/images/close.png"></span> <span id="lastspeaktime_end"
                      style="display: none;"><img id="closelast"
                      src="<%=contextPath%>/resources/images/close.png"></span>
                    <div class="btn5">清除</div>
                    <div class="btn6 sxConfirm">确定</div>
                  </div>
                </div>
              </div>
              <!-- 筛选框 end -->
            </div>
          </div>
          <!-- 消息展示 start -->
          <table id="messagestable" class="tables1">
            <tr>
              <th>messageID</th>
              <th>发言人账号</th>
              <th>所在群</th>
              <th>发言人群名片</th>
              <th>发言内容</th>
              <th>消息时间<%-- <img src="<%=contextPath%>/resources/images/xx.jpg"> --%></th>
              <th>操作</th>
            </tr>
          </table>
          <!-- 消息展示 end -->
          <!-- 分页导航 start -->
          <div id="paginationMsg" class="pageo clearfix"></div>
        </div>
      </div>
      <!-- 分页导航 end -->
    </div>
    <!-- section-c end -->
  </div>
  <!-- content-c end -->
  <!-- content end -->
  <!-- 切换群 start -->
  <div id="switchQunDiv" class="alert-box sty4 switchQunBox">
    <div class="alert" style="padding-bottom: 10px;">
      <div class="top">
        <span class="top-h">选择群</span> <span id="qunclose" class="close"></span>
      </div>
      <div class="alert-con">
        <div class="tab-line clearfix">
          <input id="QId" class="input-sty3" type="text" name="" placeholder="选择群名或者群ID">
          <div id="findQun" class="btn3">搜索</div>
        </div>
        <div class="xzqun">
          <div class="xzqun-t">
            我管理的群(<span id="totalQun"></span>)
          </div>
          <div id="groups" class="xzqun-c clearfix">
            <!-- 群展示 -->
          </div>
        </div>
        <!-- 分页导航 start -->
        <div id="pagination"></div>
        <!-- 分页导航 end -->
      </div>
      <!-- alert-con end -->
    </div>
  </div>
  <!-- 切换群 end -->
  <!-- 视频，音频，图片消息弹出框 start-->
  <div id="msgalert" class="alert-box sty4">
    <div id="messagealert" class="alert">
      <div id="msgtop" class="top">
        <span class="top-h"></span><span id="msgclose" class="close" ></span>
      </div>
    </div>
  </div>
  <!-- 视频，音频，图片消息弹出框 end -->
  <script src="<%=contextPath%>/resources/js/calendar/lhgcalendar.js"></script>
  <script src="<%=contextPath%>/resources/js/video/videojs-ie8.min.js"></script>
  <script src="<%=contextPath%>/resources/js/video/video.min.js"></script>
  <script src="<%=contextPath%>/resources/js/xxguanli.js"></script>
</body>
</html>