/**
 * File：SysCityDaoImpl.java
 * Package：com.fang.im.management.dao.impl
 * Author：jin
 * Date：2017年4月11日 下午4:29:16
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

import com.fang.im.management.dao.SysCityDao;
import com.fang.im.management.po.SysCity;

/**
 * <p>
 * Description: SysCityDaoImpl
 * </p>
 *
 * @author jinshilei
 *         2017年4月11日
 * @version 1.0
 *
 */
@Repository
public class SysCityDaoImpl implements SysCityDao {

  /**
   * 对象保存的城市信息
   */
  private static Map<Integer, SysCity> cities = new HashMap<Integer, SysCity>();

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
    DetachedCriteria criteria = DetachedCriteria.forClass(SysCity.class);
    criteria.add(Restrictions.eq("isDelete", false));
    @SuppressWarnings("unchecked")
    List<SysCity> allCities = (List<SysCity>) hibernateTemplateR.findByCriteria(criteria);
    if (allCities != null && allCities.size() > 0) {
      for (SysCity sysCity : allCities) {
        cities.put(sysCity.getSysCityId(), sysCity);
      }
    } else {
      throw new Exception("加载城市异常");
    }
  }

  @Override
  public SysCity getByCityId(Integer cityId) throws Exception {
    if (cities.size() > 0) {
      return cities.get(cityId);
    }
    return null;
  }

  @Override
  public SysCity getByCityName(String cityName) throws Exception {
    if (cities.size() > 0) {
      for (SysCity sysCity : cities.values()) {
        if (sysCity.getCityName().equals(cityName)) {
          return sysCity;
        }
      }
    }
    return null;
  }

  @Override
  public Map<Integer, SysCity> getAllCities() throws Exception {
    if (cities.size() > 0) {
      return cities;
    }
    return null;
  }
}
