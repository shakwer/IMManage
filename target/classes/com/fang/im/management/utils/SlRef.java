/**
 * File：SlRef.java
 * Package：com.fang.im.management.utils
 * Author：Administrator
 * Date：2017年4月25日 下午2:49:04
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.utils;

/**
 * SlRef
 *
 * @param <T>
 *        T
 * @author ldb
 *
 */
public class SlRef<T> {

  /**
   * value
   */
  private T value;

  /**
   * SlRef
   *
   * @param value
   *        value
   */
  public SlRef(T value) {
    this.value = value;
  }

  /**
   * get
   *
   * @return
   *         return
   */
  public T get() {
    return value;
  }

  /**
   * set
   *
   * @param anotherValue
   *        anotherValue
   */
  public void set(T anotherValue) {
    value = anotherValue;
  }

  /**
   * toString
   *
   * @return
   *         return
   */
  public String toString() {
    return value.toString();
  }

  /**
   * equals
   *
   * @param obj
   *        obj
   * @return
   *         return
   */
  public boolean equals(Object obj) {
    return value.equals(obj);
  }

  /**
   * hashCode
   *
   * @return
   *         return
   */
  public int hashCode() {
    return value.hashCode();
  }
}
