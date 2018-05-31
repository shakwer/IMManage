/**
 * File：SysCityOperateServiceImpl.java
 * Package：com.fang.im.management.service.impl
 * Author：jin
 * Date：2017年4月14日 上午8:58:49
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.im.management.dao.SysCityDao;
import com.fang.im.management.po.SysCity;
import com.fang.im.management.service.SysCityOperateService;

/**
 * <p>
 * Description: SysCityOperateServiceImpl
 * </p>
 *
 * @author jinshilei
 *         2017年4月14日
 * @version 1.0
 *
 */
@Service
public class SysCityOperateServiceImpl implements SysCityOperateService {

  /**
   * 城市数据操作
   */
  @Autowired
  private SysCityDao sysCityDao;

  @Override
  public SysCity getByCityId(Integer cityId) throws Exception {
    return sysCityDao.getByCityId(cityId);
  }

  @Override
  public SysCity getByCityName(String cityName) throws Exception {
    return sysCityDao.getByCityName(cityName);
  }

  @Override
  public Map<Integer, SysCity> getAllCities() throws Exception {
    return sysCityDao.getAllCities();
  }

}
