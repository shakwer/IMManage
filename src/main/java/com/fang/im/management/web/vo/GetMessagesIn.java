package com.fang.im.management.web.vo;

/**
 * 消息输入类
 *
 * @author zhaozele
 */
public class GetMessagesIn {

  /**
   * 群id
   */
  private String communityId;

  /**
   * 最后发言时间
   */
  private String lastSpeakingTime;

  /**
   * 发言人
   */
  private String spokesMan;

  /**
   * 消息关键字
   */
  private String keyWords;

  /**
   * 页码
   */
  private Integer pageIndex;

  /**
   * 每页大小
   */
  private Integer pageSize;

  /**
   * 获取群id
   *
   * @return 群id
   */
  public String getCommunityId() {
    return communityId;
  }

  /**
   * 设置群id
   *
   * @param communityId
   *        群id
   */
  public void setCommunityId(String communityId) {
    this.communityId = communityId;
  }

  /**
   * 获取最后发言时间
   *
   * @return 最后发言时间
   */
  public String getLastSpeakingTime() {
    return lastSpeakingTime;
  }

  /**
   * 设置最后发言时间
   *
   * @param lastSpeakingTime
   *        最后发言时间
   */
  public void setLastSpeakingTime(String lastSpeakingTime) {
    this.lastSpeakingTime = lastSpeakingTime;
  }

  /**
   * 获取发言人
   *
   * @return 发言人
   */
  public String getSpokesMan() {
    return spokesMan;
  }

  /**
   * 设置发言人
   *
   * @param spokesMan
   *        发言人
   */
  public void setSpokesMan(String spokesMan) {
    this.spokesMan = spokesMan;
  }

  /**
   * 获取消息关键字
   *
   * @return 消息关键字
   */
  public String getKeyWords() {
    return keyWords;
  }

  /**
   * 设置消息关键字
   *
   * @param keyWords
   *        消息关键字
   */
  public void setKeyWords(String keyWords) {
    this.keyWords = keyWords;
  }

  /**
   * 获取页码
   *
   * @return 页码
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * 设置页码
   *
   * @param pageIndex
   *        页码
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * 获取每页大小
   *
   * @return 每页大小
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * 设置每页大小
   *
   * @param pageSize
   *        每页大小
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
