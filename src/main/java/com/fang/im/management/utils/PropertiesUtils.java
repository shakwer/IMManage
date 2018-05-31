/**
 * File：PropertiesUtils.java
 * Package：com.fang.im.management.utils
 * Author：jin
 * Date：2017年4月1日 下午5:12:59
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

/**
 * <p>
 * Description: PropertiesUtils
 * </p>
 *
 * @author jinshilei
 *         2017年4月1日
 * @version 1.0
 *
 */
public class PropertiesUtils {

  /**
   * 运行环境
   */
  private static String runState;

  /**
   * 属性文件读取器
   */
  private static ResourceBundle bundle;

  /**
   * 静态代码块
   */
  static {
    runState = ResourceBundle.getBundle("config/runState").getString("runState");
    bundle = ResourceBundle.getBundle("config/constants_" + runState);
  }

  /**
   *
   * 读取属性文件配置的常量值
   *
   * @param key
   *        属性的key
   * @return 返回值
   */
  public static String getPropertyValue(String key) {
    return bundle.getString(key);
  }

  /**
   *
   * 获取当前的运行状态
   *
   * @return 返回值
   */
  public static String getRunState() {
    return runState;
  }

  private static String appendUrlParameter(String prefix, String name, String value) {
    boolean isEmpty = StringUtils.isEmpty(value);
    String newString;
    if (!isEmpty) {
      try {
        newString = prefix + "&" + name + "=" + URLEncoder.encode(value, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    } else {
      newString = prefix;
    }
    return newString;
  }

  public static String getBatchInviteCreateInstructionUrl(String grouptitle,String grouplist,String grouplord,String limit,String groupnotice,String categoryid,String projectid,String issystemcreate) {
    String url = PropertiesUtils.getPropertyValue("batchInviteCreateInstructionInterface");
    url = appendUrlParameter(url, "grouptitle", grouptitle);
    url = appendUrlParameter(url, "grouplist", grouplist);
    url = appendUrlParameter(url, "grouplord", grouplord);
    url = appendUrlParameter(url, "limit", limit);
    url = appendUrlParameter(url,"groupnotice", groupnotice);
    url = appendUrlParameter(url, "categoryid", categoryid);
    url = appendUrlParameter(url, "projectid", projectid);
    url = appendUrlParameter(url, "issystemcreate", issystemcreate);
    return url;
  }

}
