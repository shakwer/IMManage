package com.fang.im.management.web.vo.out;

import com.fang.im.management.po.SysCommunityResource;

/**
 * 群扩展类
 *
 * @author zhaozele
 */
public class CommunityCustomOut extends SysCommunityResource {

  /**
   * 群头像
   */
  private String pic;

  /**
   * 获取群头像
   *
   * @return 群头像
   */
  public String getPic() {
    return pic;
  }

  /**
   * 设置群头像
   *
   * @param pic
   *        群头像
   */
  public void setPic(String pic) {
    this.pic = pic;
  }
}
