package com.fang.im.management.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.fang.im.management.dao.SysRegionDao;
import com.fang.im.management.po.SysRegion;

@Service
public class SysRegionDaoImpl implements SysRegionDao {

	@Autowired
	@Qualifier("hibernateTemplate_r")
	private HibernateTemplate hibernateTemplateR;

	@Override
	public List<SysRegion> getAllSysRegionList() throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRegion.class);
		criteria.add(Restrictions.and(Restrictions.eq("isDelete", false)));
		criteria.addOrder(Order.desc("sysRegionId"));
		@SuppressWarnings("unchecked")
		List<SysRegion> list = (List<SysRegion>) hibernateTemplateR.findByCriteria(criteria);
		return list;
	}

	@Override
	public SysRegion getSysRegionById(Integer regionid) throws Exception {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRegion.class);
		criteria.add(Restrictions.eq("sysRegionId", regionid));
		@SuppressWarnings("unchecked")
		List<SysRegion> list = (List<SysRegion>) hibernateTemplateR.findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
