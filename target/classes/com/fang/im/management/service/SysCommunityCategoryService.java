package com.fang.im.management.service;

import java.util.List;

import com.fang.im.management.po.SysCommunityCategory;

/**
 * SysCommunityCategoryService
 *
 * @author YZT_SouFun
 */
public interface SysCommunityCategoryService {

  /**
   * 获取全部分类
   *
   * @return List<SysCommunityCategory>
   * @throws Exception
   *         异常
   */
  List<SysCommunityCategory> getAllCategoryList() throws Exception;

  /**
   * 获取id对应的分类
   *
   * @param catgid
   *        分类id
   * @return SysCommunityCategory
   * @throws Exception
   *         异常
   */
  SysCommunityCategory getCategoryById(Integer catgid) throws Exception;
}
