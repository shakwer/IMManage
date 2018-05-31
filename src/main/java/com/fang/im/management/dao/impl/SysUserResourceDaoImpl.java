/**
 * File：SysUserResourceDaoimpl.java
 * Package：com.fang.im.management.dao.impl
 * Author：jin
 * Date：2017年4月11日 下午2:42:33
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fang.common.utils.OutParameterUtils;
import com.fang.im.management.dao.SysUserResourceDao;
import com.fang.im.management.po.SysCity;
import com.fang.im.management.po.SysRole;
import com.fang.im.management.po.SysUserResource;

/**
 * <p>
 * Description: SysUserResourceDaoimpl
 * </p>
 *
 * @author jinshilei
 *         2017年4月11日
 * @version 1.0
 *
 */
@Repository
public class SysUserResourceDaoImpl implements SysUserResourceDao {

  /**
   * 读模板
   */
  @Autowired
  @Qualifier("hibernateTemplate_r")
  private HibernateTemplate hibernateTemplateR;

  /**
   * 写模板
   */
  @Autowired
  @Qualifier("hibernateTemplate_w")
  HibernateTemplate hibernateTemplateW;

  @Override
  public SysUserResource getUserByEmail(String email) throws Exception {
    DetachedCriteria criteria = DetachedCriteria.forClass(SysUserResource.class);
    criteria.add(Restrictions.and(Restrictions.eq("email", email),
        Restrictions.eq("isDelete", false)));
    @SuppressWarnings("unchecked")
    List<SysUserResource> list = (List<SysUserResource>) hibernateTemplateR
        .findByCriteria(criteria);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 模糊查询邮箱和真实姓名
   */
  @Override
  public List<SysUserResource> getUserLikeEmailOrName(String value) throws Exception {
    DetachedCriteria criteria = DetachedCriteria.forClass(SysUserResource.class, "sysUserResource");
    criteria.add(Restrictions.and(
        Restrictions.or(Restrictions.like("email", "%" + value + "%"),
            Restrictions.like("sysUserRealName", "%" + value + "%")),
        Restrictions.eq("isDelete", false)));

    ProjectionList proList = Projections.projectionList();
    proList.add(Projections.property("sysUserResource.email"), "email");
    proList.add(Projections.property("sysUserResource.sysUserRealName"), "sysUserRealName");

    criteria.setProjection(proList);
    criteria.setResultTransformer(Transformers.aliasToBean(SysUserResource.class));

    @SuppressWarnings("unchecked")
    List<SysUserResource> list = (List<SysUserResource>) hibernateTemplateR
        .findByCriteria(criteria);
    if (list != null && list.size() > 0) {
      return list;
    }
    return null;
  }

  @Override
  public List<SysUserResource> getPageUserByRoleAndCities(Integer roieId, List<Integer> cityIds,
                                                          Integer pageIndex, Integer pageSize,
                                                          OutParameterUtils<Integer> outTotal)
      throws Exception {
    List<Integer> roleIds = null;
    if (roieId != null) {
      roleIds = new ArrayList<Integer>();
      roleIds.add(roieId);
    }
    return getPageUserByRolesAndCitiesAndEmail(roleIds, cityIds, null, pageIndex, pageSize,
        outTotal);
  }

  @Override
  public List<SysUserResource> getPageUserByRoleAndCity(Integer roieId, Integer cityId,
                                                        Integer pageIndex, Integer pageSize,
                                                        OutParameterUtils<Integer> outTotal)
      throws Exception {
    List<Integer> roleIds = null;
    if (roieId != null) {
      roleIds = new ArrayList<Integer>();
      roleIds.add(roieId);
    }
    List<Integer> cityIds = null;
    if (cityId != null) {
      cityIds = new ArrayList<Integer>();
      cityIds.add(cityId);
    }
    return getPageUserByRolesAndCitiesAndEmail(roleIds, cityIds, null, pageIndex, pageSize,
        outTotal);
  }

  @Override
  public List<SysUserResource> getAllUserByRoleAndCities(Integer roieId, List<Integer> cityIds)
      throws Exception {
    // 暂时使用分页，查询第一页，每页100万条
    return getPageUserByRoleAndCities(roieId, cityIds, 1, 1000000, null);
  }

  @Override
  public List<SysUserResource> getPageUserByRolesAndCitiesAndEmail(List<Integer> roleIds,
                                                                   List<Integer> cityIds,
                                                                   String email,
                                                                   Integer pageIndex,
                                                                   Integer pageSize,
                                                                   OutParameterUtils<Integer> outTotal)
      throws Exception {
    String roleCondition = concatInString(roleIds);
    String cityCondition = concatInString(cityIds);
    System.err.println("---------"+roleCondition+"--------"+cityCondition);
    HibernateCallbackForSysUserResource callback = new HibernateCallbackForSysUserResource(
        roleCondition, cityCondition, email, pageIndex, pageSize, outTotal);
    List<SysUserResource> res = hibernateTemplateR.execute(callback);
    return res;
  }

  @Override
  public Integer addUserReource(SysUserResource user) throws Exception {
    Serializable serializable = hibernateTemplateW.save(user);
    if (serializable != null) {
      return Integer.parseInt(serializable.toString());
    }
    return null;
  }

  @Override
  public void deleteUserResource(Integer userId) throws Exception {
    SysUserResource user = hibernateTemplateW.get(SysUserResource.class, userId);
    if (user != null && !user.getIsDelete()) {
      user.setIsDelete(true);
      user.setSysRole(null);
      user.setSysCities(null);
    }
  }

  @Override
  public boolean updateUserInfo(Integer userId, SysRole role, String realName, Date lastLoginTime)
      throws Exception {
    SysUserResource user = hibernateTemplateW.get(SysUserResource.class, userId);
    if (user != null && !user.getIsDelete()) {
      if (role != null && role.getSysRoleId() > 0) {
        user.setSysRole(role);
      }
      if (!StringUtils.isEmpty(realName)) {
        user.setSysUserRealName(realName);
      }
      if (lastLoginTime != null) {
        user.setLastLoginTime(lastLoginTime);
      }
    }
    return true;
  }

  /**
   *
   * 拼接in条件
   *
   * @param list
   *        集合
   * @return 返回值
   */
  private String concatInString(List<Integer> list) {
    StringBuffer buffer = null;
    if (list != null && list.size() > 0) {
      buffer = new StringBuffer();
      buffer.append("(");
      for (Integer item : list) {
        buffer.append(item + ",");
      }
      // 去掉最后一个逗号
      buffer.delete(buffer.length() - 1, buffer.length());
      buffer.append(")");
    }
    if (buffer != null) {
      return buffer.toString();
    }
    return null;
  }

  @Override
  public SysUserResource getUserByUserId(Integer userId) throws Exception {
    DetachedCriteria criteria = DetachedCriteria.forClass(SysUserResource.class);
    criteria.add(Restrictions.and(Restrictions.eq("sysUserId", userId),
        Restrictions.eq("isDelete", false)));
    @SuppressWarnings("unchecked")
    List<SysUserResource> list = (List<SysUserResource>) hibernateTemplateR
        .findByCriteria(criteria);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public void deleteCityPermissionToUser(Integer userId, Set<Integer> cityIds) throws Exception {
    if (cityIds != null && cityIds.size() > 0) {
      SysUserResource user = hibernateTemplateW.get(SysUserResource.class, userId);
      if (user != null && !user.getIsDelete()) {
        Set<SysCity> cities = user.getSysCities();
        Iterator<SysCity> iterator = cities.iterator();
        for (int i = 0; i < cities.size(); i++) {
          SysCity city = iterator.next();
          if (cityIds.contains(city.getSysCityId())) {
            iterator.remove();
            i--;
          }
        }
      }
    }
  }

  @Override
  public void addCityPermissionToUser(Integer userId, Set<Integer> cityIds) throws Exception {
    if (cityIds != null && cityIds.size() > 0) {
      SysUserResource user = hibernateTemplateW.get(SysUserResource.class, userId);
      if (user != null && !user.getIsDelete()) {
        @SuppressWarnings("unchecked")
        List<SysCity> cities = (List<SysCity>) hibernateTemplateW.findByCriteria(DetachedCriteria
            .forClass(SysCity.class).add(Restrictions.in("sysCityId", cityIds)));
        if (cities != null && cities.size() > 0) {
          for (SysCity sysCity : cities) {
            user.getSysCities().add(sysCity);
          }
        }
      }
    }
  }
}

/**
 *
 * <p>
 * Description: HibernateCallbackForSysUserResource
 * </p>
 *
 * @author jinshilei
 *         2017年4月13日
 * @version 1.0
 *
 */
class HibernateCallbackForSysUserResource implements HibernateCallback<List<SysUserResource>> {

  /**
   * 角色条件
   */
  private String roleCondition;

  /**
   * 城市条件
   */
  private String cityCondition;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 页码数
   */
  private Integer pageIndex;

  /**
   * 总记录数
   */
  private OutParameterUtils<Integer> outTotal;

  /**
   * 每页记录数
   */
  private Integer pageSize;

  /**
   *
   * getRoleCondition
   *
   * @return 返回值
   */
  public String getRoleCondition() {
    return roleCondition;
  }

  /**
   *
   * setRoleCondition
   *
   * @param roleCondition
   *        角色条件
   */
  public void setRoleCondition(String roleCondition) {
    this.roleCondition = roleCondition;
  }

  /**
   *
   * getCityCondition
   *
   * @return 返回值
   */
  public String getCityCondition() {
    return cityCondition;
  }

  /**
   *
   * setCityCondition
   *
   * @param cityCondition
   *        城市条件
   */
  public void setCityCondition(String cityCondition) {
    this.cityCondition = cityCondition;
  }

  /**
   *
   * getEmail
   *
   * @return 返回值
   */
  public String getEmail() {
    return email;
  }

  /**
   *
   * setEmail
   *
   * @param email
   *        邮箱
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   *
   * getPageIndex
   *
   * @return 返回值
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   *
   * setPageIndex
   *
   * @param pageIndex
   *        页码数
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   *
   * getPageSize
   *
   * @return 返回值
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   *
   * setPageSize
   *
   * @param pageSize
   *        每页记录数
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   *
   * getOutTotal
   *
   * @return 返回值
   */
  public OutParameterUtils<Integer> getOutTotal() {
    return outTotal;
  }

  /**
   *
   * setOutTotal
   *
   * @param outTotal
   *        总记录数
   */
  public void setOutTotal(OutParameterUtils<Integer> outTotal) {
    this.outTotal = outTotal;
  }

  /**
   *
   * 构造函数
   *
   * @param roleCondition
   *        角色条件
   * @param cityCondition
   *        城市条件
   * @param email
   *        邮箱
   * @param pageIndex
   *        页码数
   * @param pageSize
   *        每页记录数
   * @param outTotal
   *        总记录数
   */
  HibernateCallbackForSysUserResource(String roleCondition, String cityCondition, String email,
      Integer pageIndex, Integer pageSize, OutParameterUtils<Integer> outTotal) {
    this.roleCondition = roleCondition;
    this.cityCondition = cityCondition;
    this.email = email;
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
    this.outTotal = outTotal;
  }

  @Override
  public List<SysUserResource> doInHibernate(Session session) throws HibernateException {
    StringBuffer bufferCondition = new StringBuffer();
    bufferCondition.append("where user.isDelete=0 and ");
    if (!StringUtils.isEmpty(this.email)) {
      bufferCondition.append("user.email='" + this.email + "' and ");
    }
    if (!StringUtils.isEmpty(this.roleCondition)) {
      bufferCondition.append("role.sysRoleId in " + this.roleCondition + " and ");
    }
    if (!StringUtils.isEmpty(this.cityCondition)) {
      bufferCondition.append("city.sysCityId in " + this.cityCondition + " ");
    } else {
      bufferCondition.delete(bufferCondition.length() - 4, bufferCondition.length());
    }
//    String hql = "select distinct user from SysUserResource user inner join fetch user.sysCities city"
//        + " inner join fetch user.sysRole role "
//        + bufferCondition.toString()
//        + "order by user.createTime desc";
//    String hqlCount = "select count(distinct user) from SysUserResource user inner join user.sysCities city"
//        + " inner join user.sysRole role " + bufferCondition.toString();
    String hql = "select distinct user from SysUserResource user "
            + "order by user.createTime desc";
    String hqlCount = "select count(distinct user) from SysUserResource user";
    System.err.println("sqlcount-------"+hqlCount);
    System.err.println("sql1-------"+hql);
    Query queryCount = session.createQuery(hqlCount);
    Integer total = ((Long) queryCount.uniqueResult()).intValue();
    if (this.outTotal != null) {
      this.outTotal.setValue(total);
    }
    if (total > 0) {
      Query query = session.createQuery(hql);
      query.setFirstResult((this.pageIndex - 1) * this.pageSize);
      query.setMaxResults(this.pageSize);
      @SuppressWarnings("unchecked")
      List<SysUserResource> result = query.list();
      return result;
    }
    return null;
  }

}
