/**
 * File：LoginOutController.java
 * Package：com.fang.im.management.web.controller
 * Author：jin
 * Date：2017年4月5日 下午3:57:08
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fang.common.utils.CookieUtils;
import com.fang.im.management.utils.PropertiesUtils;

/**
 * <p>
 * Description: LoginOutController
 * </p>
 *
 * @author jinshilei
 *         2017年4月5日
 * @version 1.0
 *
 */
@Controller
public class LoginOutController {

  /**
   *
   * loginOut
   *
   * @param response
   *        响应
   * @return 返回值
   */
  @RequestMapping(value = "/loginout", produces = {"application/json"})
  @ResponseBody
  public String loginOut(HttpServletResponse response) {
    SecurityUtils.getSubject().logout();
    String cookieName = "fang_imgroup_id_" + PropertiesUtils.getRunState();
    CookieUtils.deleteCookie(response, cookieName, ".fang.com");
    return "success";
  }
}
