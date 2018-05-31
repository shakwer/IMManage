/**
 * File：SysRoleDaoImpl.java
 * Package：com.fang.im.management.dao.impl
 * Author：jin
 * Date：2017年4月11日 下午5:04:42
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fang.im.management.dao.SysRoleDao;
import com.fang.im.management.po.SysRole;

/**
 * <p>
 * Description: SysRoleDaoImpl
 * </p>
 *
 * @author jinshilei
 *         2017年4月11日
 * @version 1.0
 *
 */
@Repository
public class SysRoleDaoImpl implements SysRoleDao {

  /**
   * 所有角色
   */
  private static Map<Integer, SysRole> roles = new HashMap<Integer, SysRole>();

  /**
   * 读模板
   */
  @Autowired
  @Qualifier("hibernateTemplate_r")
  private HibernateTemplate hibernateTemplateR;

  /**
   *
   * init
   *
   * @throws Exception
   *         异常
   */
  @PostConstruct
  public void init() throws Exception {
    DetachedCriteria criteria = DetachedCriteria.forClass(SysRole.class);
    criteria.add(Restrictions.eq("isDelete", false));
    @SuppressWarnings("unchecked")
    List<SysRole> allRoles = (List<SysRole>) hibernateTemplateR.findByCriteria(criteria);
    if (allRoles.size() > 0) {
      for (SysRole sysRole : allRoles) {
        roles.put(sysRole.getSysRoleId(), sysRole);
      }
    } else {
      throw new Exception("加载角色异常");
    }
  }

  @Override
  public SysRole getByRoleId(Integer roleId) throws Exception {
    if (roles.size() > 0) {
      return roles.get(roleId);
    }
    return null;
  }

  @Override
  public SysRole getByRoleName(String roleName) throws Exception {
    if (roles.size() > 0) {
      for (SysRole sysRole : roles.values()) {
        if (sysRole.getRoleName().equals(roleName)) {
          return sysRole;
        }
      }
    }
    return null;
  }

  @Override
  public Map<Integer, SysRole> getAllRoles() throws Exception {
    if (roles.size() > 0) {
      return roles;
    }
    return null;
  }

}
