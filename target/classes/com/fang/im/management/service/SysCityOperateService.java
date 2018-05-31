/**
 * File：SysCityOperateService.java
 * Package：com.fang.im.management.service
 * Author：jin
 * Date：2017年4月14日 上午8:58:10
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service;

import java.util.Map;

import com.fang.im.management.po.SysCity;

/**
 * <p>
 * Description: SysCityOperateService
 * </p>
 *
 * @author jinshilei
 *         2017年4月14日
 * @version 1.0
 *
 */
public interface SysCityOperateService {

  /**
   *
   * 通过城市id获取城市信息
   *
   * @param cityId
   *        城市id
   * @return 返回值
   * @throws Exception
   *         异常
   */
  SysCity getByCityId(Integer cityId) throws Exception;

  /**
   *
   * 通过城市名称获取城市信息
   *
   * @param cityName
   *        城市名称
   * @return 返回值
   * @throws Exception
   *         异常
   */
  SysCity getByCityName(String cityName) throws Exception;

  /**
   *
   * 获取所有城市信息
   *
   * @return 返回值
   * @throws Exception
   *         异常
   */
  Map<Integer, SysCity> getAllCities() throws Exception;
}
