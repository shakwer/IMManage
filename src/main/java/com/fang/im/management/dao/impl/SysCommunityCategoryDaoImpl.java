package com.fang.im.management.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fang.im.management.dao.SysCommunityCategoryDao;
import com.fang.im.management.po.SysCommunityCategory;

/**
 * 获取分类
 *
 * @author YZT_SouFun
 */
@Repository
public class SysCommunityCategoryDaoImpl implements SysCommunityCategoryDao {

  /**
   * 读模板
   */
  @Autowired
  @Qualifier("hibernateTemplate_r")
  private HibernateTemplate hibernateTemplateR;

  /**
   * 获取可用分类
   */
  @Override
  public List<SysCommunityCategory> getAllCategoryList() throws Exception {
    DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityCategory.class);
    criteria.add(Restrictions.and(Restrictions.eq("isDelete", false)));
    criteria.addOrder(Order.desc("createTime"));
    @SuppressWarnings("unchecked")
    List<SysCommunityCategory> list = (List<SysCommunityCategory>) hibernateTemplateR
        .findByCriteria(criteria);
    return list;
  }

  /**
   * 获取id对应的分类
   */
  @Override
  public SysCommunityCategory getCategoryById(Integer catgid) throws Exception {

    DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityCategory.class);
    criteria.add(Restrictions.and(Restrictions.eq("isDelete", false),
        Restrictions.eq("categoryId", catgid)));
    criteria.addOrder(Order.desc("createTime"));
    @SuppressWarnings("unchecked")
    List<SysCommunityCategory> list = (List<SysCommunityCategory>) hibernateTemplateR
        .findByCriteria(criteria);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

}
