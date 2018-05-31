<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = (String) request.getContextPath();
%>
<%@page import="com.fang.im.management.po.SysUserResource"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript">
var contextPath="<%=contextPath%>";
    $(function() {
        $("#quit").on('click',function() {
          $.post(
              contextPath + "/loginout.do",
              {},
               function(result) {
                  if (result == "success") {
                    window.location.href = "http://work.fang.com/v2/login/loginAct.do?method=login";
                        }
                        });
                            return false;
                        });
    });
    function isSelected(pageName){
    	if(pageName==1){
            $('#community').css('background-color','#CB0201');
        }
    	if(pageName==2){
            $('#member').css('background-color','#CB0201');
        }
    	if(pageName==3){
            $('#message').css('background-color','#CB0201');
        }
    	if(pageName==4){
    	    $('#permission').css('background-color','#CB0201');
    	}
    }
</script>