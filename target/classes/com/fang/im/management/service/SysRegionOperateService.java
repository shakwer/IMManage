package com.fang.im.management.service;

import java.util.List;

import com.fang.im.management.po.SysRegion;

public interface SysRegionOperateService {

	List<SysRegion> getAllSysRegionList() throws Exception;

	SysRegion getSysRegionById(Integer regionid) throws Exception;
}
