/**
 * File：GetCommunitiesOutVo.java
 * Package：com.fang.im.management.web.vo.out
 * Author：Administrator
 * Date：2017年4月26日 下午8:03:26
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.web.vo.out;

import java.util.List;

import com.fang.im.management.web.vo.SysCommunityResourceOutVo;

/**
 * GetCommunitiesOutVo
 *
 * @author ldb
 *
 */
public class GetCommunitiesOutVo {

  /**
   * total
   */
  private Integer total;

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

  /**
   * data
   */
  private List<SysCommunityResourceOutVo> data;

  /**
   * 获取 total
   *
   * @return total
   */
  public Integer getTotal() {
    return total;
  }

  /**
   * 设置total
   *
   * @param total
   *        total
   */
  public void setTotal(Integer total) {
    this.total = total;
  }

  /**
   * 获取 data
   *
   * @return data
   */
  public List<SysCommunityResourceOutVo> getData() {
    return data;
  }

  /**
   * 设置data
   *
   * @param data
   *        data
   */
  public void setData(List<SysCommunityResourceOutVo> data) {
    this.data = data;
  }

}
