/**
 * File：OaLoginController.java
 * Package：com.fang.im.management.web.controller
 * Author：jin
 * Date：2017年4月1日 下午4:28:48
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fang.common.service.FangOaOperateService;
import com.fang.common.service.vo.FangOaAuthenResult;
import com.fang.common.utils.OutParameterUtils;
import com.fang.im.management.utils.PropertiesUtils;

/**
 * <p>
 * Description: OaLoginController
 * </p>
 *
 * @author jinshilei
 *         2017年4月1日
 * @version 1.0
 *
 */
@Controller
public class OaLoginController {

  /**
   *
   * 通过OA自动登录到后台系统
   *
   * @param request
   *        web请求
   *
   * @param response
   *        web响应
   * @return 返回值
   */
  @RequestMapping(value = "/login")
  public String login(HttpServletRequest request, HttpServletResponse response) {
	System.out.println("login");
    String emailSuffix = "";
    OutParameterUtils<FangOaAuthenResult> outValue = new OutParameterUtils<FangOaAuthenResult>();
    boolean authenResult = FangOaOperateService.authenticate(request,
        PropertiesUtils.getPropertyValue("oa_service_id"), outValue);
	//System.out.println("login1---"+authenResult+"---"+outValue.getValue().getLoginid());
    if (authenResult) {
      // 有后台系统权限
      UsernamePasswordToken token = new UsernamePasswordToken();
      token.setRememberMe(false);
      token.setUsername(outValue.getValue().getLoginid() + emailSuffix);
      token.setPassword(null);
      try {
        SecurityUtils.getSubject().login(token);
      } catch (Exception e) {
    	  e.printStackTrace();
        return "redirect:login.jsp";
      }
      return "redirect:community.html";
    }
    // 没有后台系统权限，跳转到无权限提示页面
    System.out.println("权限失败");
    return "redirect:login.jsp";
  }
}
