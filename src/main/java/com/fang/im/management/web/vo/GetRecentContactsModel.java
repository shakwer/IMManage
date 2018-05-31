/**
 * File：GetRecentContactsModel.java
 * Package：com.fang.im.management.web.vo
 * Author：Administrator
 * Date：2017年4月25日 下午8:02:39
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.web.vo;

/**
 * GetRecentContactsModel
 *
 * @author ldb
 *
 */
public class GetRecentContactsModel {

  /**
   * imusername
   */
  private String imusername;

  /**
   * nickname
   */
  private String nickname;

  /**
   * picurl
   */
  private String picurl;

  /**
   * 获取 picurl
   *
   * @return picurl
   */
  public String getPicurl() {
    return picurl;
  }

  /**
   * 设置picurl
   *
   * @param picurl
   *        picurl
   */
  public void setPicurl(String picurl) {
    this.picurl = picurl;
  }

  /**
   * 获取 imusername
   *
   * @return imusername
   */
  public String getImusername() {
    return imusername;
  }

  /**
   * 设置imusername
   *
   * @param imusername
   *        imusername
   */
  public void setImusername(String imusername) {
    this.imusername = imusername;
  }

  /**
   * 获取 nickname
   *
   * @return nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * 设置nickname
   *
   * @param nickname
   *        nickname
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

}
