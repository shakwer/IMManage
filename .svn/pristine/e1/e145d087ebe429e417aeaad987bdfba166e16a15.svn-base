/**
 * File：MySessionIdGenerator.java
 * Package：com.fang.im.management.realm
 * Author：jin
 * Date：2017年4月1日 下午5:49:33
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import com.fang.common.utils.GuidUtils;
import com.fang.im.management.utils.PropertiesUtils;

/**
 * <p>
 * Description: MySessionIdGenerator
 * </p>
 *
 * @author jinshilei
 *         2017年4月1日
 * @version 1.0
 *
 */
public class MySessionIdGenerator implements SessionIdGenerator {

  /**
   * 自定义sessionid生成器
   *
   * @param session
   *        session
   * @return 返回值
   */
  @Override
  public Serializable generateId(Session session) {
    return GuidUtils.getGuid() + "_" + PropertiesUtils.getRunState();
  }

}
