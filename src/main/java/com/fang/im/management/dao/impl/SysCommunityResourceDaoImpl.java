/**
 * File：SysCommunityResourceDaoImpl.java
 * Package：com.fang.im.management.dao.impl
 * Author：jin
 * Date：2017年4月13日 上午11:29:45
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fang.im.management.dao.SysCommunityResourceDao;
import com.fang.im.management.po.SysCity;
import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.po.SysRegion;

/**
 * <p>
 * Description: SysCommunityResourceDaoImpl
 * </p>
 *
 * @author jinshilei 2017年4月13日
 * @version 1.0
 *
 */
@Repository
public class SysCommunityResourceDaoImpl implements SysCommunityResourceDao {

	/**
	 * 读模板
	 */
	@Autowired
	@Qualifier("hibernateTemplate_r")
	private HibernateTemplate hibernateTemplateR;

	/**
	 * 读模板
	 */
	@Autowired
	@Qualifier("hibernateTemplate_w")
	private HibernateTemplate hibernateTemplateW;

	@Override
	public Long searchCommunityCount(String projectid, String categoryid, String cityid, String regionid,
			String communityid, String communityname, boolean isdelete) throws Exception {
		// TODO Auto-generated method stub
		List<Integer> cityids = new ArrayList<>();
		if (com.fang.common.utils.StringUtils.isEmpty(cityid)) {
			if (!com.fang.common.utils.StringUtils.isEmpty(regionid)) {
				DetachedCriteria criteria = DetachedCriteria.forClass(SysRegion.class);
				criteria.add(Restrictions.eq("isDelete", false));
				criteria.add(Restrictions.eq("sysRegionId", Integer.valueOf(regionid)));
				@SuppressWarnings("unchecked")
				List<SysRegion> list = (List<SysRegion>) hibernateTemplateR.findByCriteria(criteria);
				for (SysCity city : list.get(0).getCitys()) {
					cityids.add(city.getSysCityId());
				}
			}
		} else {
			cityids.add(Integer.valueOf(cityid));
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		if (cityids.size() > 0) {
			System.err.println("cityids---" + cityids.size());
			criteria.add(Restrictions.in("city", cityids));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(projectid)) {
			System.err.println("projectid---" + projectid);
			criteria.add(Restrictions.eq("productid", projectid));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(categoryid)) {
			System.err.println("categoryid---" + categoryid);
			criteria.add(Restrictions.eq("category", Integer.valueOf(categoryid)));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(communityid)) {
			System.err.println("communityid---" + communityid);
			criteria.add(Restrictions.eq("communityId", communityid));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(communityname)) {
			System.err.println("communityname---" + communityname);
			criteria.add(Restrictions.like("communityName", "%" + communityname + "%"));
		}
		criteria.add(Restrictions.eq("isDelete", false));
		// 投影
		criteria.setProjection(Projections.count("communityId"));

		// List<SysCommunityResource> list = (List<SysCommunityResource>)
		// hibernateTemplateR.findByCriteria(criteria);
		// System.out.println("aaa-------" + list.size());

		// 查询
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplateR.findByCriteria(criteria);
		System.err.println("count-------" + list.get(0));
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return 0L;
	}

	@Override
	public List<SysCommunityResource> searchCommunity(String projectid, String categoryid, String cityid,
			String regionid, String communityid, String communityname, Integer pageIndex, Integer pageSize,
			boolean isdelete) throws Exception {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		if (pageSize > 20 || pageSize < 1) {
			pageSize = 20;
		}
		List<Integer> cityids = new ArrayList<>();
		if (com.fang.common.utils.StringUtils.isEmpty(cityid)) {
			if (!com.fang.common.utils.StringUtils.isEmpty(regionid)) {
				DetachedCriteria criteria = DetachedCriteria.forClass(SysRegion.class);
				criteria.add(Restrictions.eq("isDelete", false));
				criteria.add(Restrictions.eq("sysRegionId", Integer.valueOf(regionid)));
				@SuppressWarnings("unchecked")
				List<SysRegion> list = (List<SysRegion>) hibernateTemplateR.findByCriteria(criteria);
				for (SysCity city : list.get(0).getCitys()) {
					cityids.add(city.getSysCityId());
				}
			}
		} else {
			cityids.add(Integer.valueOf(cityid));
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		if (cityids.size() > 0) {
			criteria.add(Restrictions.in("city", cityids));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(projectid)) {
			criteria.add(Restrictions.eq("productid", projectid));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(categoryid)) {
			criteria.add(Restrictions.eq("category", Integer.valueOf(categoryid)));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(communityid)) {
			criteria.add(Restrictions.eq("communityId", communityid));
		}
		if (!com.fang.common.utils.StringUtils.isEmpty(communityname)) {
			criteria.add(Restrictions.like("communityName", "%" + communityname + "%"));
		}
		criteria.add(Restrictions.eq("isDelete", false));

		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria,
				(pageIndex - 1) * pageSize, pageSize);
		System.out.println("aaa-------" + list.size());
		return list;
	}

	/**
	 * 通过city查询指定群信息
	 */
	@Override
	public List<SysCommunityResource> getCommunityByCities(List<Integer> cityIds) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		if (cityIds != null && cityIds.size() > 0) {
			criteria.add(Restrictions.in("city", cityIds));
		}
		criteria.add(Restrictions.eq("isDelete", false));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria);
		return list;
	}

	/**
	 * 获取用户创建的群
	 */
	@Override
	public List<SysCommunityResource> getCommunityByCitiesOrderByCreatetime(Collection<Integer> cityIds)
			throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		if (cityIds != null && cityIds.size() > 0) {
			criteria.add(Restrictions.in("city", cityIds));
		}
		criteria.add(Restrictions.eq("isDelete", false));
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria);
		return list;
	}

	@Override
	public List<SysCommunityResource> getCommunityByCitiesOrderByCreatetimePaging(Collection<Integer> cityIds,
			int pageindex, int pagesize) throws Exception {
		if (pageindex < 1) {
			pageindex = 1;
		}
		if (pagesize > 20 || pagesize < 1) {
			pagesize = 20;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		if (cityIds != null && cityIds.size() > 0) {
			criteria.add(Restrictions.in("city", cityIds));
		}
		criteria.add(Restrictions.eq("isDelete", false));
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria,
				(pageindex - 1) * pagesize, pagesize);
		return list;
	}

	@Override
	public SysCommunityResource getNearestCommunityByCitiesOrderByCreatetime(Collection<Integer> cityIds)
			throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		if (cityIds != null && cityIds.size() > 0) {
			criteria.add(Restrictions.in("city", cityIds));
		}
		criteria.add(Restrictions.eq("isDelete", false));
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria, 0,
				1);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取用户创建的群
	 */
	@Override
	public List<SysCommunityResource> getCommunityByUser(String email) throws Exception {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.and(Restrictions.eq("communityHolder", email), Restrictions.eq("isDelete", false)));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria);
		return list;
	}

	/**
	 * 添加群信息
	 */
	@Override
	public Boolean addCommunity(String communityid, String communityHolder, String communityName, int city,
			int category, Date createTime, String projectid) throws Exception {
		SysCommunityResource sysCommunityResource = new SysCommunityResource();
		sysCommunityResource.setCommunityId(communityid);
		sysCommunityResource.setCommunityHolder(communityHolder);
		sysCommunityResource.setCommunityName(communityName);
		sysCommunityResource.setCity(city);
		sysCommunityResource.setCategory(category);
		sysCommunityResource.setCreateTime(createTime);
		sysCommunityResource.setIsDelete(false);
		sysCommunityResource.setProjectid(projectid);
		Serializable serializable = hibernateTemplateW.save(sysCommunityResource);
		if (serializable != null) {
			return Integer.parseInt(serializable.toString()) > 0;
		}
		return false;
	}

	/**
	 * 通过群id设置群删除标识
	 */
	@Override
	public Boolean updateIsdeleteCommunity(final String communityid, final boolean isdelete) throws Exception {
		SysCommunityResource community = hibernateTemplateW.get(SysCommunityResource.class, communityid);
		if (community != null) {
			community.setIsDelete(isdelete);
		} else {
			return false;
		}
		return true;

	}

	/**
	 * 修改群信息
	 *
	 */
	@Override
	public Boolean updateSomeParam(String communityid, String communityName, Integer category) throws Exception {
		SysCommunityResource community = hibernateTemplateW.get(SysCommunityResource.class, communityid);
		if (community != null) {
			if (!StringUtils.isEmpty(communityName)) {
				community.setCommunityName(communityName);
			}
			if (category != null) {
				if (category > 0) {
					community.setCategory(category);
				}
			}
		} else {
			return false;
		}
		return true;

	}

	/**
	 * 修改群主
	 */
	@Override
	public Boolean updateCommunityHolder(String communityid, String communityholder) throws Exception {
		SysCommunityResource community = hibernateTemplateW.get(SysCommunityResource.class, communityid);
		if (community != null) {
			if (!StringUtils.isEmpty(communityholder)) {
				community.setCommunityHolder(communityholder);
			}
		} else {
			return false;
		}
		return true;

	}

	/**
	 * 通过user和city查询群信息列表
	 *
	 * @param email
	 *            邮箱
	 * @param cityIds
	 *            城市
	 * @param isdelete
	 *            是否已删除
	 * @param pageSize
	 *            页大小
	 * @param pageIndex
	 *            页码
	 * @param orderStr
	 *            排序
	 * @return List<SysCommunityResource>
	 * @throws Exception
	 *             异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysCommunityResource> getCommunityByIsDelete(final String email, final List<Integer> cityIds,
			final boolean isdelete, final int pageSize, final int pageIndex, final String orderStr) throws Exception {
		// 需要拆分成三部分对应的dao
		return hibernateTemplateR.execute(new HibernateCallback<List<SysCommunityResource>>() {

			@Override
			public List<SysCommunityResource> doInHibernate(Session session) throws HibernateException {

				Criteria criteria = session.createCriteria(SysCommunityResource.class);
				// 群主需要
				if (!StringUtils.isEmpty(email)) {
					criteria.add(Restrictions.eq("communityHolder", email));
				}
				// 管理员需要
				if (cityIds != null && cityIds.size() > 0) {
					criteria.add(Restrictions.in("city", cityIds));
				}
				criteria.add(Restrictions.eq("isDelete", isdelete));

				// 排序
				Order order = Order.desc("createTime");
				if (!StringUtils.isEmpty(orderStr)) {
					if (orderStr.toLowerCase().equals("asc")) {
						order = Order.asc("createTime");
					}
				}

				criteria.addOrder(order);
				// 分页
				criteria.setFirstResult((pageIndex - 1) * pageSize);
				criteria.setMaxResults(pageSize);

				return criteria.list();
			}
		});
	}

	/**
	 * 通过user，city等信息查询群信息总数
	 */
	@Override
	public Integer getCountByIsDelete(final String email, final List<Integer> cityIds, final boolean isdelete)
			throws Exception {
		final StringBuilder hql = new StringBuilder();
		hql.append("select count(communityid) as count from management_syscommunityresource where isdelete = ");
		if (isdelete) {
			hql.append("1 ");
		} else {
			hql.append("0 ");
		}
		if (!StringUtils.isEmpty(email)) {
			hql.append(" and communityholder = '" + email + "'");
		}
		if (cityIds != null && cityIds.size() > 0) {
			hql.append(" and city in " + concatInString(cityIds));
		}

		Integer count = hibernateTemplateR.execute(new HibernateCallback<Integer>() {

			@SuppressWarnings("unchecked")
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {

				// SQL参数化查询,提高接口性能
				Query query = session.createSQLQuery(hql.toString()).addScalar("count", StandardBasicTypes.INTEGER);
				List<Integer> usersCount = query.list();
				if (usersCount != null && usersCount.size() > 0) {
					return usersCount.get(0);
				}
				return 0;
			}
		});

		return count;

	}

	/**
	 * 拼接in语句
	 *
	 * @param list
	 *            list
	 * @return String
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

	/**
	 * 获取用户创建的群
	 */
	@Override
	public List<SysCommunityResource> getCommunityByUserOrderByCreatetime(String email) throws Exception {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.and(Restrictions.eq("communityHolder", email), Restrictions.eq("isDelete", false)));
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria);
		return list;
	}

	/**
	 * 获取id对应的群信息
	 */
	@Override
	public SysCommunityResource getCommunityById(String id) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.and(Restrictions.idEq(id), Restrictions.eq("isDelete", false)));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> communities = ((List<SysCommunityResource>) hibernateTemplateR
				.findByCriteria(criteria));
		if (communities == null || communities.size() < 1) {
			return null;
		}
		return communities.get(0);
	}

	/**
	 * 获取id对应的群信息
	 */
	@Override
	public SysCommunityResource getCommunityByIdAndIsDelete(String communityid, boolean isdelete) throws Exception {
		if (StringUtils.isEmpty(communityid)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.and(Restrictions.idEq(communityid), Restrictions.eq("isDelete", isdelete)));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = ((List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria));
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<SysCommunityResource> getCommunitiesByName(String name) throws Exception {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.like("communityName", "%" + name + "%"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = ((List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria));
		return list;
	}

	@Override
	public List<SysCommunityResource> getPageListByCreateTime(final Integer pageIndex, final Integer pageSize,
			final String order) throws Exception {
		List<SysCommunityResource> list = hibernateTemplateR
				.execute(new HibernateCallback<List<SysCommunityResource>>() {

					@SuppressWarnings("unchecked")
					@Override
					public List<SysCommunityResource> doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(SysCommunityResource.class);
						criteria.add(Restrictions.eq("isDelete", false));
						// 排序
						if (!StringUtils.isEmpty(order)) {
							if (order.equals("asc")) {
								criteria.addOrder(Order.asc("createTime"));
							} else if (order.equals("desc")) {
								criteria.addOrder(Order.desc("createTime"));
							}
						}
						// 分页
						criteria.setFirstResult((pageIndex - 1) * pageSize);
						criteria.setMaxResults(pageSize);
						// 查询
						return criteria.list();
					}
				});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysCommunityResource> getByCommunityName(final String communityName, final Integer pageIndex,
			final Integer pageSize, final String order) throws Exception {

		List<SysCommunityResource> list = hibernateTemplateR
				.execute(new HibernateCallback<List<SysCommunityResource>>() {

					@Override
					public List<SysCommunityResource> doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(SysCommunityResource.class);
						// 查询条件
						criteria.add(Restrictions.like("communityName", "%" + communityName + "%"))
								.add(Restrictions.eq("isDelete", false));
						// 排序
						if (!StringUtils.isEmpty(order)) {
							if (order.equals("asc")) {
								criteria.addOrder(Order.asc("createTime"));
							} else if (order.equals("desc")) {
								criteria.addOrder(Order.desc("createTime"));
							}
						}
						// 分页
						criteria.setFirstResult((pageIndex - 1) * pageSize);
						criteria.setMaxResults(pageSize);
						// 查询
						return criteria.list();
					}
				});
		return list;
	}

	@Override
	public List<SysCommunityResource> getPageListByCities(final List<Integer> cityIds, final Integer pageIndex,
			final Integer pageSize, final String order) throws Exception {
		List<SysCommunityResource> list = hibernateTemplateR
				.execute(new HibernateCallback<List<SysCommunityResource>>() {

					@SuppressWarnings("unchecked")
					@Override
					public List<SysCommunityResource> doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(SysCommunityResource.class);
						// 查询条件
						if (cityIds != null && cityIds.size() > 0) {
							criteria.add(Restrictions.in("city", cityIds));
						}
						criteria.add(Restrictions.eq("isDelete", false));
						// 排序
						if (!StringUtils.isEmpty(order)) {
							if (order.equals("asc")) {
								criteria.addOrder(Order.asc("createTime"));
							} else if (order.equals("desc")) {
								criteria.addOrder(Order.desc("createTime"));
							}
						}
						// 分页
						criteria.setFirstResult((pageIndex - 1) * pageSize);
						criteria.setMaxResults(pageSize);
						// 查询
						return criteria.list();
					}
				});
		return list;
	}

	@Override
	public Long getCountLikeNameByParam(String communityName, String email, Collection<Integer> cities,
			boolean isdelete) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysCommunityResource.class,
				"sysCommunityResource");
		// 查询条件
		detachedCriteria.add(Restrictions.eq("isDelete", isdelete))
				.add(Restrictions.like("communityName", "%" + communityName + "%"));
		if (cities != null && cities.size() > 0) {
			detachedCriteria.add(Restrictions.in("city", cities));
		}
		if (!StringUtils.isEmpty(email)) {
			detachedCriteria.add(Restrictions.eq("communityHolder", email));
		}
		// 投影
		detachedCriteria.setProjection(Projections.count("sysCommunityResource.communityId"));
		// 查询
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplateR.findByCriteria(detachedCriteria);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysCommunityResource> getCommunityLikeNameByParam(final String communityName, final Integer pageIndex,
			final Integer pageSize, final String order, final String email, final Collection<Integer> cities,
			final boolean isdelete) throws Exception {

		List<SysCommunityResource> list = hibernateTemplateR
				.execute(new HibernateCallback<List<SysCommunityResource>>() {

					@Override
					public List<SysCommunityResource> doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(SysCommunityResource.class);
						// 查询条件
						criteria.add(Restrictions.like("communityName", "%" + communityName + "%"))
								.add(Restrictions.eq("isDelete", isdelete));
						if (cities != null && cities.size() > 0) {
							criteria.add(Restrictions.in("city", cities));
						}
						if (!StringUtils.isEmpty(email)) {
							criteria.add(Restrictions.eq("communityHolder", email));
						}
						// 排序
						if (!StringUtils.isEmpty(order)) {
							if (order.equals("asc")) {
								criteria.addOrder(Order.asc("createTime"));
							} else if (order.equals("desc")) {
								criteria.addOrder(Order.desc("createTime"));
							}
						}
						// 分页
						criteria.setFirstResult((pageIndex - 1) * pageSize);
						criteria.setMaxResults(pageSize);
						// 查询
						return criteria.list();
					}
				});
		return list;
	}

	@Override
	public List<SysCommunityResource> getPageListByUser(final String email, final Integer pageIndex,
			final Integer pageSize, final String order) throws Exception {
		List<SysCommunityResource> list = hibernateTemplateR
				.execute(new HibernateCallback<List<SysCommunityResource>>() {

					@SuppressWarnings("unchecked")
					@Override
					public List<SysCommunityResource> doInHibernate(Session session) throws HibernateException {
						if (StringUtils.isEmpty(email)) {
							return null;
						}
						Criteria criteria = session.createCriteria(SysCommunityResource.class);
						criteria.add(Restrictions.and(Restrictions.eq("communityHolder", email),
								Restrictions.eq("isDelete", false)));
						// 排序
						if (!StringUtils.isEmpty(order)) {
							if (order.equals("asc")) {
								criteria.addOrder(Order.asc("createTime"));
							} else if (order.equals("desc")) {
								criteria.addOrder(Order.desc("createTime"));
							}
						}
						// 分页
						criteria.setFirstResult((pageIndex - 1) * pageSize);
						criteria.setMaxResults(pageSize);
						// 查询
						return criteria.list();
					}
				});
		return list;
	}

	@Override
	public Long getCommunityCount() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysCommunityResource.class,
				"sysCommunityResource");
		// 查询条件
		detachedCriteria.add(Restrictions.eq("isDelete", false));
		// 投影
		detachedCriteria.setProjection(Projections.count("sysCommunityResource.communityId"));
		// 查询
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplateR.findByCriteria(detachedCriteria);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Long getCommunityCountByCities(List<Integer> cityIds) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysCommunityResource.class,
				"sysCommunityResource");
		// 查询条件
		detachedCriteria.add(Restrictions.eq("isDelete", false)).add(Restrictions.in("city", cityIds));
		// 投影
		detachedCriteria.setProjection(Projections.count("sysCommunityResource.communityId"));
		// 查询
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplateR.findByCriteria(detachedCriteria);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Long getCommunityCountByUser(String email) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysCommunityResource.class,
				"sysCommunityResource");
		// 查询条件
		detachedCriteria.add(Restrictions.eq("isDelete", false)).add(Restrictions.eq("communityHolder", email));
		// 投影
		detachedCriteria.setProjection(Projections.count("sysCommunityResource.communityId"));
		// 查询
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplateR.findByCriteria(detachedCriteria);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Long getCommunityCountByName(String communityName) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysCommunityResource.class,
				"sysCommunityResource");
		// 查询条件
		detachedCriteria.add(Restrictions.eq("isDelete", false))
				.add(Restrictions.like("communityName", "%" + communityName + "%"));
		// 投影
		detachedCriteria.setProjection(Projections.count("sysCommunityResource.communityId"));
		// 查询
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplateR.findByCriteria(detachedCriteria);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public SysCommunityResource getNearestCommunityByUserOrderByCreatetime(String email) throws Exception {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.and(Restrictions.eq("communityHolder", email), Restrictions.eq("isDelete", false)));
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria, 0,
				1);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<SysCommunityResource> getCommunityByUserOrderByCreatetimePaging(String email, int pageindex,
			int pagesize) throws Exception {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		if (pageindex < 1) {
			pageindex = 1;
		}
		if (pagesize < 1 || pagesize > 6) {
			pagesize = 6;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCommunityResource.class);
		criteria.add(Restrictions.and(Restrictions.eq("communityHolder", email), Restrictions.eq("isDelete", false)));
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<SysCommunityResource> list = (List<SysCommunityResource>) hibernateTemplateR.findByCriteria(criteria,
				(pageindex - 1) * pagesize, pagesize);
		return list;
	}

}
