/**
 * File：ImMd5ForMember.java
 * Package：com.fang.im.management.utils
 * Author：Administrator
 * Date：2017年5月5日 下午2:54:56
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ImMd5ForMember
 *
 * @author ldb
 *
 */
public abstract class ImMd5ForMember {

  /**
   * md5
   *
   * @param key
   *        key
   * @return
   *         return
   */
  public static String md5(String key) {
    String ret = null;
    try {
      if (key != null) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] a = md.digest(key.getBytes("UTF-8"));
        StringBuffer sb = new StringBuffer();
        for (byte b : a) {
          String h = Integer.toHexString(b & 0xff);
          if (h.length() < 2) {
            sb.append("0").append(h);
          } else {
            sb.append(h);
          }
        }
        ret = sb.toString();
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return ret;
  }

  /**
   * MD5
   *
   * @param key
   *        key
   * @return
   *         return
   */
  public static byte[] mD5(String key) {
    byte[] bs = null;
    try {
      if (key != null) {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        bs = md5.digest(key.getBytes("UTF-8"));
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return bs;
  }
}
