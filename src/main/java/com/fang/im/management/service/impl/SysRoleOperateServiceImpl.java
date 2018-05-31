/**
 * File：SysRoleOperateServiceImpl.java
 * Package：com.fang.im.management.service.impl
 * Author：jin
 * Date：2017年4月13日 上午10:45:31
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.im.management.dao.SysRoleDao;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.service.SysRoleOperateService;

/**
 * <p>
 * Description: SysRoleOperateServiceImpl
 * </p>
 *
 * @author jinshilei
 *         2017年4月13日
 * @version 1.0
 *
 */
@Service
public class SysRoleOperateServiceImpl implements SysRoleOperateService {

  /**
   * 角色数据操作
   */
  @Autowired
  private SysRoleDao sysRoleDao;

  @Override
  public SysRole getByRoleId(Integer roleId) throws Exception {
    return sysRoleDao.getByRoleId(roleId);
  }

  @Override
  public SysRole getByRoleName(String roleName) throws Exception {
    return sysRoleDao.getByRoleName(roleName);
  }

  @Override
  public Map<Integer, SysRole> getAllRoles() throws Exception {
    return sysRoleDao.getAllRoles();
  }

}
