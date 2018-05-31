/**
 * File：SlRequest.java
 * Package：com.fang.im.management.utils
 * Author：Administrator
 * Date：2017年4月25日 下午2:24:53
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * SlRequest
 *
 * @author ldb
 *
 */
public class SlRequest {

  /**
   * getHtml
   *
   * @param url
   *        url
   * @param encoding
   *        encoding
   * @param timeout
   *        timeout
   * @param httpActionType
   *        httpActionType
   * @param postParameter
   *        postParameter
   * @return
   *         return
   */
  public static String getHtml(String url, String encoding, int timeout,
                               SlHttpActionType httpActionType, String postParameter) {
    try {
      URL realUrl = new URL(url);
      HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl
          .openConnection();
      httpURLConnection.setConnectTimeout(timeout);
      httpURLConnection.setRequestProperty("Accept-Encoding",
          "gzip,deflate");
      PrintWriter printWriter = null;
      if (httpActionType == SlHttpActionType.Get) {
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
      } else {
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.addRequestProperty("Content-type",
            "application/x-www-form-urlencoded");

        if (postParameter != null) {
          printWriter = new PrintWriter(
              httpURLConnection.getOutputStream());
          printWriter.print(postParameter);
          printWriter.flush();
        }
      }
      BufferedReader bufferedReader = null;
      String type = httpURLConnection.getContentEncoding();
      if (type != null) {
        type = type.toLowerCase();
      }
      if ("gzip".equals(type)) {
        bufferedReader = new BufferedReader(
            new InputStreamReader(new GZIPInputStream(
                httpURLConnection.getInputStream()), encoding));
      } else if ("deflate".equals(type)) {
        bufferedReader = new BufferedReader(new InputStreamReader(
            new DeflaterInputStream(
                httpURLConnection.getInputStream()), encoding));
      } else {
        InputStream inputStream = httpURLConnection.getInputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(
            inputStream, encoding));
      }

      String line = null;
      StringBuilder stringBuilder = new StringBuilder();
      try {
        while ((line = bufferedReader.readLine()) != null) {
          stringBuilder.append(line);
        }
      } finally {
        bufferedReader.close();
        httpURLConnection.disconnect();
        if (printWriter != null) {
          printWriter.close();
        }
      }
      return stringBuilder.toString();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * getHtml
   *
   * @param url
   *        url
   * @param encoding
   *        encoding
   * @param timeout
   *        timeout
   * @param httpActionType
   *        httpActionType
   * @return
   *         return
   */
  public static String getHtml(String url, String encoding, int timeout,
                               SlHttpActionType httpActionType) {
    return getHtml(url, encoding, timeout, httpActionType, null);
  }

  /**
   * getHtml
   *
   * @param url
   *        url
   * @param encoding
   *        encoding
   * @param timeout
   *        timeout
   * @return
   *         return
   */
  public static String getHtml(String url, String encoding, int timeout) {
    return getHtml(url, encoding, timeout, SlHttpActionType.Get, null);
  }

  /**
   * getHtml
   *
   * @param url
   *        url
   * @param encoding
   *        encoding
   * @return
   *         return
   */
  public static String getHtml(String url, String encoding) {
    return getHtml(url, encoding, 1000, SlHttpActionType.Get, null);
  }

  /**
   * getHtml
   *
   * @param url
   *        url
   * @return
   *         return
   */
  public static String getHtml(String url) {
    return getHtml(url, "utf8", 1000, SlHttpActionType.Get, null);
  }
}
