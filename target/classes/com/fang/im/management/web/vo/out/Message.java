package com.fang.im.management.web.vo.out;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息类
 *
 * @author zhaozele
 */
public class Message {

  /**
   * 消息id
   */
  private String messageID;

  /**
   * 发言人账号
   */
  @JSONField(name = "form")
  private String formName;

  /**
   * 群名称
   */
  @JSONField(name = "groupname")
  private String communityName;

  /**
   * 发言人群名片
   */
  private String cardName;

  /**
   * 消息内容
   */
  private String message;

  /**
   * 消息时间
   */
  private String messageTime;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 消息类型(group_video、group_img、group_voice)
   */
  private String command;

  /**
   * 获取消息id
   *
   * @return 消息id
   */
  public String getMessageID() {
    return messageID;
  }

  /**
   * 设置消息id
   *
   * @param messageID
   *        消息id
   */
  public void setMessageID(String messageID) {
    this.messageID = messageID;
  }

  /**
   * 获取发言人账号
   *
   * @return 发言人账号
   */
  public String getFormName() {
    return formName;
  }

  /**
   * 设置发言人账号
   *
   * @param formName
   *        发言人账号
   */
  public void setFormName(String formName) {
    this.formName = formName;
  }

  /**
   * 获取群名
   *
   * @return 群名
   */

  public String getCommunityName() {
    return communityName;
  }

  /**
   * 设置群名
   *
   * @param communityName
   *        群名
   */
  public void setCommunityName(String communityName) {
    this.communityName = communityName;
  }

  /**
   * 获取群名片
   *
   * @return 群名片
   */
  public String getCardName() {
    return cardName;
  }

  /**
   * 设置群名片
   *
   * @param cardName
   *        群名片
   */
  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  /**
   * 获取消息内容
   *
   * @return 消息内容
   */
  public String getMessage() {
    return message;
  }

  /**
   * 设置消息内容
   *
   * @param message
   *        消息内容
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * 获取消息时间
   *
   * @return 消息时间
   */
  public String getMessageTime() {
    return messageTime;
  }

  /**
   * 设置消息时间
   *
   * @param messageTime
   *        消息时间
   */
  public void setMessageTime(String messageTime) {
    this.messageTime = messageTime;
  }

  /**
   * 获取昵称
   *
   * @return 昵称
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * 设置昵称
   *
   * @param nickName
   *        昵称
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * 获取消息类型
   * @return 消息类型(group_video、group_img、group_voice)
   */
  public String getCommand() {
    return command;
  }

  /**
   * 设置消息类型
   * @param command
   *        消息类型(group_video、group_img、group_voice)
   */
  public void setCommand(String command) {
    this.command = command;
  }
}
