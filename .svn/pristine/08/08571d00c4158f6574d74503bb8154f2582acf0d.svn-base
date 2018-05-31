/**
 * File：SysUserResourceDao.java
 * Package：com.fang.im.management.dao
 * Author：jin
 * Date：2017年4月11日 下午2:41:43
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fang.common.utils.OutParameterUtils;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.po.SysUserResource;

/**
 * <p>
 * Description: SysUserResourceDao
 * </p>
 *
 * @author jinshilei
 *         2017年4月11日
 * @version 1.0
 *
 */
public interface SysUserResourceDao {

  /**
   *
   * 通过邮箱查找用户信息
   *
   * @param email
   *        邮箱(不带@fang.com)
   * @return 返回值
   * @throws Exception
   *         异常
   */
  SysUserResource getUserByEmail(String email) throws Exception;

  /**
   *
   * 通过用户id查找用户信息
   *
   * @param userId
   *        用户id
   * @return 返回值
   * @throws Exception
   *         异常
   */
  SysUserResource getUserByUserId(Integer userId) throws Exception;

  /**
   * 模糊匹配邮箱和真实姓名
   *
   * @param value
   *        邮箱(不含@fang.com),真实姓名
   * @return List<SysUserResource>
   * @throws Exception
   *         异常
   */
  List<SysUserResource> getUserLikeEmailOrName(String value) throws Exception;

  /**
   *
   * 查看用户分页信息(通过角色和城市过滤)
   *
   * @param roieIds
   *        多个角色id(传null，查询所有角色)
   * @param cityIds
   *        多个城市id (传null，查询所有城市)
   * @param email
   *        邮箱
   * @param pageIndex
   *        第几页
   * @param pageSize
   *        每页的记录数
   * @param outTotal
   *        总记录数
   * @return 返回值
   * @throws Exception
   *         异常
   */
  List<SysUserResource> getPageUserByRolesAndCitiesAndEmail(List<Integer> roieIds,
                                                            List<Integer> cityIds, String email,
                                                            Integer pageIndex, Integer pageSize,
                                                            OutParameterUtils<Integer> outTotal)
      throws Exception;

  /**
   *
   * 查看用户分页信息(通过角色和城市过滤)
   *
   * @param roieId
   *        单个角色
   * @param cityIds
   *        多个城市
   * @param pageIndex
   *        第几页
   * @param pageSize
   *        每页记录数
   * @param outTotal
   *        总记录数
   * @return 返回值
   * @throws Exception
   *         异常
   */
  List<SysUserResource> getPageUserByRoleAndCities(Integer roieId, List<Integer> cityIds,
                                                   Integer pageIndex, Integer pageSize,
                                                   OutParameterUtils<Integer> outTotal)
      throws Exception;

  /**
   *
   * 查看所有用户信息(通过角色和城市过滤)
   *
   * @param roieId
   *        角色id
   * @param cityIds
   *        城市的id
   * @return 返回值
   * @throws Exception
   *         异常
   */
  List<SysUserResource> getAllUserByRoleAndCities(Integer roieId, List<Integer> cityIds)
      throws Exception;

  /**
   *
   * 查看用户分页信息(通过角色和城市过滤)
   *
   * @param roieId
   *        单个角色
   * @param cityId
   *        单个城市
   * @param pageIndex
   *        第几页
   * @param pageSize
   *        每页记录数
   * @param outTotal
   *        总记录数
   * @return 返回值
   * @throws Exception
   *         异常
   */
  List<SysUserResource> getPageUserByRoleAndCity(Integer roieId, Integer cityId, Integer pageIndex,
                                                 Integer pageSize,
                                                 OutParameterUtils<Integer> outTotal)
      throws Exception;

  /**
   *
   * 添加用户
   *
   * @param user
   *        用户
   * @return 返回值
   * @throws Exception
   *         异常
   */
  Integer addUserReource(SysUserResource user) throws Exception;

  /**
   *
   * 删除用户(软删除)
   *
   * @param userId
   *        用户id
   * @throws Exception
   *         异常
   */
  void deleteUserResource(Integer userId) throws Exception;

  /**
   *
   * 通过用户id删除用户的城市权限
   *
   * @param userId
   *        用户id
   * @param cityIds
   *        城市id
   * @throws Exception
   *         异常
   */
  void deleteCityPermissionToUser(Integer userId, Set<Integer> cityIds) throws Exception;

  /**
   *
   * 通过用户id增加用户的城市权限
   *
   * @param userId
   *        用户id
   * @param cityIds
   *        城市id
   * @throws Exception
   *         异常
   */
  void addCityPermissionToUser(Integer userId, Set<Integer> cityIds) throws Exception;

  /**
   *
   * 通过用户id更新用户信息(不更新传null)
   *
   * @param userId
   *        用户id
   * @param role
   *        角色
   * @param realName
   *        更换的真实姓名
   * @param lastLoginTime
   *        最后登录时间
   * @return 返回值
   * @throws Exception
   *         异常
   */
  boolean updateUserInfo(Integer userId, SysRole role, String realName, Date lastLoginTime)
      throws Exception;
}
