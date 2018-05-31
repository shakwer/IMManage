/**
 * File：PhoneNOUtil.java
 * Package：com.fang.im.management.utils
 * Author：Administrator
 * Date：2017年4月27日 下午3:20:07
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PhoneNOUtil
 *
 * @author ldb
 *
 */
public abstract class PhoneNOUtil {

  /**
   * isMobileNO
   *
   * @param mobiles
   *        mobiles
   * @return
   *         return
   */
  public static boolean isMobileNO(String mobiles) {
    Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }

}
