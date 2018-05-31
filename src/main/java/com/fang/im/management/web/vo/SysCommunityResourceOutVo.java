/**
 * File：SysCommunityResourceOutVo.java
 * Package：com.fang.im.management.web.vo
 * Author：Administrator
 * Date：2017年5月8日 上午11:18:17
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.web.vo;

import com.fang.im.management.po.SysCommunityResource;



/**
 * SysCommunityResourceOutVo
 *
 * @author ldb
 *
 */
public class SysCommunityResourceOutVo extends SysCommunityResource {

  /**
   * pic
   */
  private String pic;

  /**
   * 获取 pic
   *
   * @return pic
   */
  public String getPic() {
    return pic;
  }

  /**
   * 设置pic
   *
   * @param pic
   *        pic
   */
  public void setPic(String pic) {
    this.pic = pic;
  }

}
