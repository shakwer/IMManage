/**
 * File：SlJson.java
 * Package：com.fang.im.management.utils
 * Author：Administrator
 * Date：2017年4月25日 下午2:48:15
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.utils;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * SlJson
 *
 * @author ldb
 *
 */
public class SlJson {

  /**
   * toJson
   *
   * @param source
   *        source
   * @return
   *         return
   */
  public static String toJson(Object source) {
    return JSONObject.fromObject(source).toString();
  }

  /**
   * fromJson
   *
   * @param json
   *        json
   * @param beanClass
   *        beanClass
   * @return
   *         return
   */
  public static Object fromJson(String json, Class<?> beanClass) {
    JSONObject jsonObject = JSONObject.fromObject(json);
    return JSONObject.toBean(jsonObject, beanClass);
  }

  /**
   * jsonEquals
   *
   * @param a
   *        a
   * @param b
   *        b
   * @return
   *         return
   */
  public static boolean jsonEquals(Object a, Object b) {
    return toJson(a).equals(toJson(b));
  }

  /**
   * getSafeJson
   *
   * @param input
   *        input
   * @return
   *         return
   */
  @SuppressWarnings("unchecked")
  public static String getSafeJson(String input) {
    if (input == null || input.isEmpty()) {
      return "";
    }

    SlJsonWarp<String> jsonWarp = new SlJsonWarp<String>();
    jsonWarp.setValue(input);

    return ((SlJsonWarp<String>) fromJson(toJson(jsonWarp),
        jsonWarp.getClass())).getValue();
  }

  /**
   * toBean
   *
   * @param json
   *        json
   * @param clazz
   *        clazz
   * @param map
   *        map
   * @param <T>
   *        T
   * @return
   *         return
   */
  @SuppressWarnings("unchecked")
  public static <T> T toBean(String json, Class<T> clazz, Map<String, Class<?>> map) {
    JSONObject jsonObject = JSONObject.fromObject(json);
    return (T) JSONObject.toBean(jsonObject, clazz, map);
  }

  /**
   * SlJsonWarp
   *
   * @param <T>
   *        T
   * @author ldb
   *
   */
  public static class SlJsonWarp<T> {

    /**
     * value
     */
    private T value;

    /**
     * getValue
     *
     * @return
     *         return
     */
    public T getValue() {
      return value;
    }

    /**
     * setValue
     *
     * @param value
     *        value
     */
    public void setValue(T value) {
      this.value = value;
    }
  }
}
