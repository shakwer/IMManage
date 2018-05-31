/**
 * File：SysUserOperateServiceImpl.java
 * Package：com.fang.im.management.service.impl
 * Author：jin
 * Date：2017年4月11日 下午2:09:54
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fang.common.utils.OutParameterUtils;
import com.fang.im.management.dao.SysCityDao;
import com.fang.im.management.dao.SysCommunityResourceDao;
import com.fang.im.management.dao.SysRoleDao;
import com.fang.im.management.dao.SysUserResourceDao;
import com.fang.im.management.po.SysCity;
import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.po.SysUserResource;
import com.fang.im.management.service.SysUserOperateService;
import com.fang.im.management.systemexception.CustomeBusinessJsonExciption;

/**
 * <p>
 * Description: SysUserOperateServiceImpl
 * </p>
 *
 * @author jinshilei
 *         2017年4月11日
 * @version 1.0
 *
 */
@Service
public class SysUserOperateServiceImpl implements SysUserOperateService {

  /**
   * 用户信息操作
   */
  @Autowired
  private SysUserResourceDao sysUserResourceDao;

  /**
   * 城市操作
   */
  @Autowired
  private SysCityDao sysCityDao;

  /**
   * 角色操作
   */
  @Autowired
  private SysRoleDao sysRoleDao;

  /**
   * 群操作
   */
  @Autowired
  private SysCommunityResourceDao sysCommunityResourceDao;

  @Override
  public SysUserResource getUserByEmail(String email) throws Exception {
    return sysUserResourceDao.getUserByEmail(email);
  }

  @Override
  public List<SysUserResource> getUserLikeEmailOrName(String value) throws Exception {
    return sysUserResourceDao.getUserLikeEmailOrName(value);
  }

  @Override
  public List<SysUserResource> getAllUserByRoleAndCities(Integer roieId, List<Integer> cityIds)
      throws Exception {
    return sysUserResourceDao.getAllUserByRoleAndCities(roieId, cityIds);
  }

  @Override
  public List<SysUserResource> getPageUserByRolesAndCitiesAndEmail(List<Integer> roieIds,
                                                                   List<Integer> cityIds,
                                                                   String email,
                                                                   Integer pageIndex,
                                                                   Integer pageSize,
                                                                   OutParameterUtils<Integer> outTotal)
      throws Exception {
    return sysUserResourceDao.getPageUserByRolesAndCitiesAndEmail(roieIds, cityIds, email,
        pageIndex, pageSize, outTotal);
  }

  @Override
  public List<SysUserResource> getPageUserByRoleAndCities(Integer roieId, List<Integer> cityIds,
                                                          Integer pageIndex, Integer pageSize,
                                                          OutParameterUtils<Integer> outTotal)
      throws Exception {
    return sysUserResourceDao.getPageUserByRoleAndCities(roieId, cityIds, pageIndex, pageSize,
        outTotal);
  }

  @Override
  public List<SysUserResource> getPageUserByRoleAndCity(Integer roieId, Integer cityId,
                                                        Integer pageIndex, Integer pageSize,
                                                        OutParameterUtils<Integer> outTotal)
      throws Exception {
    return sysUserResourceDao.getPageUserByRoleAndCity(roieId, cityId, pageIndex, pageSize,
        outTotal);
  }

  @Override
  public boolean addNewUser(String email, String imUserName, String realName, Set<SysCity> cities,
                            SysRole role) throws Exception {
    // 添加用户信息
    if (!StringUtils.isEmpty(email)) {
      email = email.replaceAll("@fang.com", "");
      email = email.replaceAll("@soufun.com", "");
    }
    if (!StringUtils.isEmpty(imUserName)) {
      // im的app端的用户名前缀为：l:
      imUserName = "l:" + imUserName;
    }
    SysUserResource user = new SysUserResource();
    user.setEmail(email);
    user.setImUserName(imUserName);
    user.setSysUserRealName(realName);
    user.setCreateTime(new Date());
    user.setIsDelete(false);
    // 添加城市信息
    if (cities != null && cities.size() > 0) {
      for (SysCity sysCity : cities) {
        if (sysCity.getSysCityId() == null || sysCity.getSysCityId() <= 0) {
          throw new CustomeBusinessJsonExciption("错误的城市");
        }
      }
      user.setSysCities(cities);
    } else {
      throw new CustomeBusinessJsonExciption("必须指定用户可管理的城市");
    }
    // 添加角色信息
    if (role != null && role.getSysRoleId() != null && role.getSysRoleId() > 0) {
      user.setSysRole(role);
    } else {
      throw new CustomeBusinessJsonExciption("必须指定用户的角色");
    }
    sysUserResourceDao.addUserReource(user);
    return true;
  }

  @Override
  public void deleteUser(Integer userId) throws Exception {
    // 对于角色为普通群主的用户，删除前应该判断是否还有直接管理的群(即该用户还是某些群的群主)，有的话，不允许删除，必须将群转移后删除
    SysUserResource user = getUserByUserId(userId);
    if (user != null) {
      List<SysCommunityResource> communities = sysCommunityResourceDao.getCommunityByUser(user
          .getEmail());
      if (communities != null && communities.size() > 0) {
        throw new CustomeBusinessJsonExciption("该用户还有直接管理的群没有转移,不能删除");
      }
    }
    sysUserResourceDao.deleteUserResource(userId);
  }

  @Override
  public boolean updateUserInfo(Integer userId, SysRole role, String realName, Date lastLoginTime)
      throws Exception {
    return sysUserResourceDao.updateUserInfo(userId, role, realName, lastLoginTime);
  }

  @Override
  public SysUserResource getUserByUserId(Integer userId) throws Exception {
    return sysUserResourceDao.getUserByUserId(userId);
  }

  @Override
  public void deleteCityPermissionToUser(Integer userId, Set<Integer> cityIds) throws Exception {
    SysUserResource user = getUserByUserId(userId);
    if (user != null) {
      Set<SysCity> cities = user.getSysCities();
      if (cities != null) {
        if (cities.size() <= 1) {
          // 只剩余一个城市权限，则不允许删除
          throw new CustomeBusinessJsonExciption("只有一个可管理的城市，不能删除");
        }
        if (cities.size() - cityIds.size() < 1) {
          throw new CustomeBusinessJsonExciption("不能删除所有城市");
        }
        sysUserResourceDao.deleteCityPermissionToUser(userId, cityIds);
      }
    }
  }

  @Override
  public void addCityPermissionToUser(Integer userId, Set<Integer> cityIds) throws Exception {
    sysUserResourceDao.addCityPermissionToUser(userId, cityIds);
  }
}
