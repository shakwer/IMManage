package com.fang.im.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.im.management.dao.SysRegionDao;
import com.fang.im.management.po.SysRegion;
import com.fang.im.management.service.SysRegionOperateService;

@Service
public class SysRegionOperateServiceImpl implements SysRegionOperateService {

	@Autowired
	private SysRegionDao sysRegionDao;

	@Override
	public List<SysRegion> getAllSysRegionList() throws Exception {
		// TODO Auto-generated method stub
		return sysRegionDao.getAllSysRegionList();
	}

	@Override
	public SysRegion getSysRegionById(Integer regionid) throws Exception {
		// TODO Auto-generated method stub
		return sysRegionDao.getSysRegionById(regionid);
	}

}
