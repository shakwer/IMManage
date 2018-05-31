/**
 * File：SysRole.java
 * Package：com.fang.im.management.po
 * Author：jin
 * Date：2017年4月10日 下午3:57:09
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * Description: SysRole
 * </p>
 *
 * @author jinshilei
 *         2017年4月10日
 * @version 1.0
 *
 */
public class SysRole implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4419710130543811600L;

  /**
   * 角色id
   */
  private Integer sysRoleId;

  /**
   * 角色名称
   */
  private String roleName;

  /**
   * 拥有该角色的用户
   */
  @JsonIgnore
  private Set<SysUserResource> users;

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
   * getSysRoleId
   *
   * @return 返回值
   */
  public Integer getSysRoleId() {
    return sysRoleId;
  }

  /**
   *
   * setSysRoleId
   *
   * @param sysRoleId
   *        角色id
   */
  public void setSysRoleId(Integer sysRoleId) {
    this.sysRoleId = sysRoleId;
  }

  /**
   *
   * getRoleName
   *
   * @return 返回值
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   *
   * setRoleName
   *
   * @param roleName
   *        角色名称
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   *
   * getUsers
   *
   * @return 返回值
   */
  public Set<SysUserResource> getUsers() {
    return users;
  }

  /**
   *
   * setUsers
   *
   * @param users
   *        用户
   */
  public void setUsers(Set<SysUserResource> users) {
    this.users = users;
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
