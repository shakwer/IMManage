/**
 * File：SysCommunityCategory.java
 * Package：com.fang.im.management.po
 * Author：jin
 * Date：2017年4月10日 下午6:04:53
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.po;

import java.util.Date;

/**
 * <p>
 * Description: SysCommunityCategory
 * </p>
 *
 * @author jinshilei
 *         2017年4月10日
 * @version 1.0
 *
 */
public class SysCommunityCategory {

  /**
   * 分类id
   */
  private Integer categoryId;

  /**
   * 分类名称
   */
  private String categoryName;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 是否有效
   */
  private Boolean isDelete;

  /**
   *
   * getCategoryId
   *
   * @return 返回值
   */
  public Integer getCategoryId() {
    return categoryId;
  }

  /**
   *
   * setCategoryId
   *
   * @param categoryId
   *        分类id
   */
  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  /**
   *
   * getCategoryName
   *
   * @return 返回值
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   *
   * setCategoryName
   *
   * @param categoryName
   *        分类名称
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   *
   * getCreateTime
   *
   * @return 返回值
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   *
   * setCreateTime
   *
   * @param createTime
   *        创建时间
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   *
   * getIsDelete
   *
   * @return 返回值
   */
  public Boolean getIsDelete() {
    return isDelete;
  }

  /**
   *
   * setIsDelete
   *
   * @param isDelete
   *        是否有效
   */
  public void setIsDelete(Boolean isDelete) {
    this.isDelete = isDelete;
  }

}
