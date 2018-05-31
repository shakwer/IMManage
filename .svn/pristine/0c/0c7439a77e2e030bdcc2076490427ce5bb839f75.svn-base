/**
 * File：SysRoleOperateService.java
 * Package：com.fang.im.management.service
 * Author：jin
 * Date：2017年4月13日 上午10:44:44
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service;

import java.util.Map;

import com.fang.im.management.po.SysRole;

/**
 * <p>
 * Description: SysRoleOperateService
 * </p>
 *
 * @author jinshilei
 *         2017年4月13日
 * @version 1.0
 *
 */
public interface SysRoleOperateService {

  /**
   *
   * 通过角色id获取角色信息
   *
   * @param roleId
   *        角色id
   * @return 返回值
   * @throws Exception
   *         异常
   */
  SysRole getByRoleId(Integer roleId) throws Exception;

  /**
   *
   * 通过角色名称获取角色信息
   *
   * @param roleName
   *        角色名称
   * @return 返回值
   * @throws Exception
   *         异常
   */
  SysRole getByRoleName(String roleName) throws Exception;

  /**
   *
   * 获取所有角色
   *
   * @return 返回值
   * @throws Exception
   *         异常
   */
  Map<Integer, SysRole> getAllRoles() throws Exception;
}
