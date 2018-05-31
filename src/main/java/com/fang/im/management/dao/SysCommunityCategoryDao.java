package com.fang.im.management.dao;

import java.util.List;

import com.fang.im.management.po.SysCommunityCategory;

/**
 * 获取分类资源
 *
 * @author YZT_SouFun
 */
public interface SysCommunityCategoryDao {

  /**
   * 获取全部分类
   *
   * @return List<SysCommunityCategory>
   * @throws Exception
   *         异常
   */
  List<SysCommunityCategory> getAllCategoryList() throws Exception;

  /**
   * 通过id获取分类
   *
   * @param catgid
   *        catgid
   * @return SysCommunityCategory
   *
   * @throws Exception
   *         异常
   */
  SysCommunityCategory getCategoryById(Integer catgid) throws Exception;
}
