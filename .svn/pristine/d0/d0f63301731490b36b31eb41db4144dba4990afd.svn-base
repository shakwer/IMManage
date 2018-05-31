/**
 * File：SysExceptionResolver.java
 * Package：com.fang.im.management.systemexception
 * Author：jin
 * Date：2017年4月18日 下午1:33:09
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.systemexception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

/**
 * <p>
 * Description: SysExceptionResolver
 * </p>
 *
 * @author jinshilei
 *         2017年4月18日
 * @version 1.0
 *
 */
public class SysExceptionResolver implements HandlerExceptionResolver {

  /**
   * 日志对象
   */
  private static final Logger LOGGER = LogManager.getLogger("error");

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                       Object handler, Exception exception) {

    exception.printStackTrace();

    String requestUrl = request.getServletPath();
    ModelAndView view = new ModelAndView();
    Map<String, String> jsonError = new HashMap<String, String>();
    FastJsonJsonView jsonView = new FastJsonJsonView();
    if (exception instanceof UnauthorizedException) {
      if (requestUrl.endsWith(".html")) {
        view.setViewName("views/unauthorized");
        return view;
      } else if (requestUrl.endsWith(".do")) {
        jsonError.put("result", "error");
        jsonError.put("message", exception.getMessage());
        jsonView.setAttributesMap(jsonError);
        view.setView(jsonView);
        return view;
      }
    } else if (exception instanceof CustomeBusinessJsonExciption) {
      jsonError.put("result", "error");
      jsonError.put("message", exception.getMessage());
      jsonView.setAttributesMap(jsonError);
      view.setView(jsonView);
      return view;
    } else {
      LOGGER.error(exception.getMessage() + "--" + exception.getStackTrace());
      jsonError.put("result", "error");
      jsonError.put("message", "系统错误");
      jsonView.setAttributesMap(jsonError);
      view.setView(jsonView);
      return view;
    }
    return null;
  }
}
