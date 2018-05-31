/**
 * File：GetCommunitiesInVo.java
 * Package：com.fang.im.management.web.vo
 * Author：Administrator
 * Date：2017年4月26日 下午7:59:24
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.web.vo;

/**
 * GetCommunitiesInVo
 *
 * @author ldb
 *
 */
public class GetCommunitiesInVo {

  /**
   * pageindex
   */
  private Integer pageindex;

  /**
   * pagesize
   */
  private Integer pagesize;

  /**
   * 获取 pageindex
   *
   * @return pageindex
   */
  public Integer getPageindex() {
    return pageindex;
  }

  /**
   * 设置pageindex
   *
   * @param pageindex
   *        pageindex
   */
  public void setPageindex(Integer pageindex) {
    this.pageindex = pageindex;
  }

  /**
   * 获取 pagesize
   *
   * @return pagesize
   */
  public Integer getPagesize() {
    return pagesize;
  }

  /**
   * 设置pagesize
   *
   * @param pagesize
   *        pagesize
   */
  public void setPagesize(Integer pagesize) {
    this.pagesize = pagesize;
  }

}
