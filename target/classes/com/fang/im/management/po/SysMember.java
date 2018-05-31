/**
 * File：SysMember.java
 * Package：com.fang.im.management.po
 * Author：Administrator
 * Date：2017年4月21日 上午9:36:08
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.fang.im.management.po;

import java.io.Serializable;

/**
 * SysMember
 *
 * @author ldb
 *
 */
public class SysMember implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -3432831566490493010L;

  /**
   * 群主、管理员、成员
   */
  private String usertag;

  /**
   * 群名片
   */
  private String cardname;

  /**
   * 最后发消息时间
   */
  private String lastmessagetime;

  /**
   * 添加时间
   */
  private String addtime;

  /**
   * 昵称
   */
  private String nickname;

  /**
   * 用户名
   */
  private String username;

  /**
   * 性别
   */
  private String sex;

  /**
   * 状态：正常、禁言、拉黑
   */
  private String state;

  /**
   * 获取 state
   *
   * @return state
   */
  public String getState() {
    return state;
  }

  /**
   * 设置state
   *
   * @param state
   *        state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * 获取 sex
   *
   * @return sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * 设置sex
   *
   * @param sex
   *        sex
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * 获取 usertag
   *
   * @return usertag
   */
  public String getUsertag() {
    return usertag;
  }

  /**
   * 设置usertag
   *
   * @param usertag
   *        usertag
   */
  public void setUsertag(String usertag) {
    this.usertag = usertag;
  }

  /**
   * 获取 cardname
   *
   * @return cardname
   */
  public String getCardname() {
    return cardname;
  }

  /**
   * 设置cardname
   *
   * @param cardname
   *        cardname
   */
  public void setCardname(String cardname) {
    this.cardname = cardname;
  }

  /**
   * 获取 lastmessagetime
   *
   * @return lastmessagetime
   */
  public String getLastmessagetime() {
    return lastmessagetime;
  }

  /**
   * 设置lastmessagetime
   *
   * @param lastmessagetime
   *        lastmessagetime
   */
  public void setLastmessagetime(String lastmessagetime) {
    this.lastmessagetime = lastmessagetime;
  }

  /**
   * 获取 addtime
   *
   * @return addtime
   */
  public String getAddtime() {
    return addtime;
  }

  /**
   * 设置addtime
   *
   * @param addtime
   *        addtime
   */
  public void setAddtime(String addtime) {
    this.addtime = addtime;
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

  /**
   * 获取 username
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * 设置username
   *
   * @param username
   *        username
   */
  public void setUsername(String username) {
    this.username = username;
  }

}
