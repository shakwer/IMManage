<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String contextPath = (String) request.getContextPath();
%>
  
      			<p>搜索结果<label><input type="checkbox" class="all"> 全选</label></p>
      			<c:choose>
      			<c:when test="${not empty communitylists}">
				<ol>
				<c:forEach items="${communitylists}" var="communityitem" varStatus="status">
				<li communityId="${communityitem.getCommunityid()}" communityName="${communityitem.getCommunityname()}" city="${communityitem.getCityname()}"
				cateName="${communityitem.getCategoryname()}" regionName="${communityitem.getRegionName()}" projectId="${communityitem.getProjectid()==null?0:communityitem.getProjectid()}"
				><span><img class="tx"
				<c:choose>
  <c:when test="${communityitem.getImurlout().getPic() != null && communityitem.getImurlout().getPic() != '' &&communityitem.getImurlout().getPic() != 'null' }">
    src="${communityitem.getImurlout().getPic()}"
  </c:when>
  <c:otherwise>
    src="<%=contextPath%>/resources/images/q.jpg"
  </c:otherwise>
</c:choose>
				></span>
						<dt><font>${communityitem.getCityname()} ${communityitem.getCategoryname()} ${communityitem.getCommunityname()}(ID:${communityitem.getCommunityid()})</font>
							<em>${communityitem.getRegionName()}/${communityitem.getCityname()}/${communityitem.getProjectid()==null?0:communityitem.getProjectid()}</em>
						</dt>
						<dd><input type="checkbox" class="single" ></dd>
					</li>
				
				</c:forEach>
					<!-- 分页 -->
					<div class="pageo clearfix">
								<div class="page" id="tbody1_page">
									<span>共<i>${totalcount }</i>条记录 <i>${currentindex}</i>/${totalpage}页（20项/页）
									</span>
									<div id="pageNum" class="page-num clearfix">
										<div id="firstPage" class="sty1">首页</div>
										<div id="priviousPage" class="sty1">上一页</div>
										<c:choose>
											<c:when test="${totalpage>0 }">
												<c:choose>
													<c:when test="${totalpage<=3 }">
														<c:forEach begin="1" end="${totalpage }" var="stat">
															<div id="front_${stat }"
																<c:if test="${stat==pageindex}">class="cur"</c:if>>${stat }</div>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach
															begin="${totalpage-pageindex>2?pageindex:(totalpage-2) }"
															end="${pageindex+(totalpage-pageindex>2?2:(totalpage-pageindex)) }"
															var="stat">
															<div id="front_${stat }"
																<c:if test="${stat==pageindex}">class="cur"</c:if>>${stat }</div>
														</c:forEach>
														<c:if test="${totalpage-pageindex>2 }">
															<div id="middlePage">…</div>
														</c:if>
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
				</ol>
				</c:when>
				<c:otherwise>
				<li>没有搜索到相关关键词的结果</li>
				</c:otherwise>
				
				</c:choose>
					
<script>

totalPage = "${totalpage}";
pageSize = 20;
currentIndex = "${pageindex}";

$(document).ready(function(){
	var list=$("ol li");
	if(list==null||list==undefined||list.length==0){
		return;
	}
	var groupId=[];
	debugger
	for(var i=0;i<selectedGroups.length;i++){
		groupId.push(selectedGroups[i].communityId);
	}
	for(var i=0;i<list.length;i++){
		var item=list[i];
		if( $.inArray($(item).attr("communityId"),groupId)>=0){
			$(item).find("input[type='checkbox']").attr("checked",true);
		}
	}
})
</script> 