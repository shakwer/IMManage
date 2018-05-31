package com.fang.im.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.im.management.dao.SysCommunityCategoryDao;
import com.fang.im.management.po.SysCommunityCategory;
import com.fang.im.management.service.SysCommunityCategoryService;

/**
 * SysCommunityCategoryServiceImpl
 *
 * @author YZT_SouFun
 */
@Service
public class SysCommunityCategoryServiceImpl implements SysCommunityCategoryService {

  /**
   * sysCommunityCategoryDao
   */
  @Autowired
  private SysCommunityCategoryDao sysCommunityCategoryDao;

  /**
   * 获取全部分类
   */
  @Override
  public List<SysCommunityCategory> getAllCategoryList() throws Exception {
    return sysCommunityCategoryDao.getAllCategoryList();
  }

  /**
   * 获取id对应分类
   */
  @Override
  public SysCommunityCategory getCategoryById(Integer catgid) throws Exception {
    return sysCommunityCategoryDao.getCategoryById(catgid);
  }

}
