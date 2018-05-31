/**
 * File：ChangeGroupOutVo.java
 * Package：com.fang.im.management.web.vo.out
 * Author：Administrator
 * Date：2017年4月25日 下午2:33:11
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.web.vo.out;

/**
 * ChangeGroupOutVo
 *
 * @author ldb
 *
 */
public class ChangeGroupOutVo {

  /**
   * 群ID
   */
  private String groupid;

  /**
   * 群名称
   */
  private String groupname;

  /**
   * 群人数
   */
  private String count;

  /**
   * 群人数上限
   */
  private String limit;

  /**
   * 群头像
   */
  private String pic;

  /**
   * 获取 groupid
   *
   * @return groupid
   */
  public String getGroupid() {
    return groupid;
  }

  /**
   * 设置groupid
   *
   * @param groupid
   *        groupid
   */
  public void setGroupid(String groupid) {
    this.groupid = groupid;
  }

  /**
   * 获取 groupname
   *
   * @return groupname
   */
  public String getGroupname() {
    return groupname;
  }

  /**
   * 设置groupname
   *
   * @param groupname
   *        groupname
   */
  public void setGroupname(String groupname) {
    this.groupname = groupname;
  }

  /**
   * 获取 count
   *
   * @return count
   */
  public String getCount() {
    return count;
  }

  /**
   * 设置count
   *
   * @param count
   *        count
   */
  public void setCount(String count) {
    this.count = count;
  }

  /**
   * 获取 limit
   *
   * @return limit
   */
  public String getLimit() {
    return limit;
  }

  /**
   * 设置limit
   *
   * @param limit
   *        limit
   */
  public void setLimit(String limit) {
    this.limit = limit;
  }

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
