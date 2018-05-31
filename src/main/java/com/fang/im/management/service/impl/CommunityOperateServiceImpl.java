/**
 * File：CommunityOperateServiceImpl.java
 * Package：com.fang.im.management.service.impl
 * Author：jin
 * Date：2017年4月13日 上午11:37:15
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fang.common.network.HttpClientUtils;
import com.fang.im.management.dao.SysCommunityResourceDao;
import com.fang.im.management.dao.SysUserResourceDao;
import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.po.SysUserResource;
import com.fang.im.management.service.CommunityOperateService;
import com.fang.im.management.utils.PropertiesUtils;
import com.fang.im.management.utils.SlRef;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * Description: CommunityOperateServiceImpl
 * </p>
 *
 * @author jinshilei 2017年4月13日
 * @version 1.0
 *
 */
@Service
public class CommunityOperateServiceImpl implements CommunityOperateService {

	/**
	 * 群操作
	 */
	@Autowired
	private SysCommunityResourceDao sysCommunityResourceDao;
	@Autowired
	private SysUserResourceDao sysUserResourceDao;

	/**
	 * 获取城市对应的列表
	 */
	@Override
	public List<SysCommunityResource> getCommunityByCities(List<Integer> cityIds) throws Exception {
		return sysCommunityResourceDao.getCommunityByCities(cityIds);
	}

	/**
	 * 获取用户创建的列表
	 */
	@Override
	public List<SysCommunityResource> getCommunityByUser(String email) throws Exception {
		return sysCommunityResourceDao.getCommunityByUser(email);
	}

	/**
	 * 获取用户创建的列表
	 */
	@Override
	public List<SysCommunityResource> getCommunityByCitiesOrderByCreatetime(Collection<Integer> cityIds)
			throws Exception {
		return sysCommunityResourceDao.getCommunityByCitiesOrderByCreatetime(cityIds);
	}

	/**
	 * 获取城市对应的列表
	 */
	@Override
	public List<SysCommunityResource> getCommunityByUserOrderByCreatetime(String email) throws Exception {
		return sysCommunityResourceDao.getCommunityByUserOrderByCreatetime(email);
	}

	/**
	 * 获取id可用的群信息
	 */
	@Override
	public SysCommunityResource getCommunityById(String id) throws Exception {
		return sysCommunityResourceDao.getCommunityById(id);
	}

	/**
	 * 获取id对应的群信息
	 */
	@Override
	public SysCommunityResource getCommunityByIdAndIsDelete(String communityid, boolean isdelete) throws Exception {
		return sysCommunityResourceDao.getCommunityByIdAndIsDelete(communityid, isdelete);
	}

	/**
	 * 添加群信息
	 */
	@Override
	public Boolean addCommunity(String communityid, String communityHolder, String communityName, int city,
			int category, Date createTime, String projectid) throws Exception {
		return sysCommunityResourceDao.addCommunity(communityid, communityHolder, communityName, city, category,
				createTime, projectid);
	}

	/**
	 * 获取群信息列表
	 */
	@Override
	public List<SysCommunityResource> getCommunityByIsDelete(String email, List<Integer> cityIds, boolean isdelete,
			int pageSize, int pageIndex, String orderStr) throws Exception {
		return sysCommunityResourceDao.getCommunityByIsDelete(email, cityIds, isdelete, pageSize, pageIndex, orderStr);
	}

	/**
	 * 获取群信息列表总数
	 */
	@Override
	public Integer getCountByIsDelete(String email, List<Integer> cityIds, boolean isdelete) throws Exception {
		return sysCommunityResourceDao.getCountByIsDelete(email, cityIds, isdelete);
	}

	/**
	 * 修改群信息
	 */
	@Override
	public Boolean updateSomeParam(String communityid, String communityName, Integer category) throws Exception {
		return sysCommunityResourceDao.updateSomeParam(communityid, communityName, category);
	}

	private String getNowString() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date now = new Date();
		String nowString = DateFormatUtils.format(now, pattern);
		return nowString;
	}

	private String getUUID() {
		UUID uuid = UUID.randomUUID();
		String upperUUID = uuid.toString().toUpperCase();
		return upperUUID;
	}

	private JSONObject getGroupRequest(String groupId, String form, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("chatinstruction", "chat");
		jsonObject.put("chattype", "groupchat");
		jsonObject.put("clienttype", "phone");
		jsonObject.put("form", form);
		jsonObject.put("from", form);
		jsonObject.put("groupid", groupId);
		jsonObject.put("message", message);
		jsonObject.put("messagekey", getUUID());
		jsonObject.put("messagetime", getNowString());
		jsonObject.put("type", "agent");
		return jsonObject;
	}

	private String getImUserName(String communityId) {
		String imUserName = null;
		try {
			SysCommunityResource sysCommunityResource = sysCommunityResourceDao.getCommunityById(communityId);
			if (sysCommunityResource != null) {
				String communityHolder = sysCommunityResource.getCommunityHolder();
				SysUserResource sysUserResource = sysUserResourceDao.getUserByEmail(communityHolder);
				if (sysUserResource != null) {
					imUserName = sysUserResource.getImUserName();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return imUserName;
	}

	@Override
	public void sendMessage(String[] ids,String message) {
		String url = PropertiesUtils.getPropertyValue("sdkHttpSendMessage");
		for (String id : ids) {
			String imUserName = getImUserName(id);
			if (imUserName != null) {
				JSONObject jsonObject = getGroupRequest(id, imUserName, message);
				try {
					HttpClientUtils.postDataUrl(url, "utf-8", jsonObject);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 修改群删除标记
	 */
	@Override
	public Boolean updateIsdeleteCommunity(String communityid, boolean isdelete) throws Exception {
		return sysCommunityResourceDao.updateIsdeleteCommunity(communityid, isdelete);
	}

	/**
	 * 修改群主
	 */
	@Override
	public Boolean updateCommunityHolder(String communityid, String communityholder) throws Exception {
		return sysCommunityResourceDao.updateCommunityHolder(communityid, communityholder);
	}

	@Override
	public List<SysCommunityResource> getCommunitiesByName(String name) throws Exception {
		return sysCommunityResourceDao.getCommunitiesByName(name);
	}

	@Override
	public List<SysCommunityResource> getPageListByCreateTime(Integer pageIndex, Integer pageSize, String order)
			throws Exception {

		return sysCommunityResourceDao.getPageListByCreateTime(pageIndex, pageSize, order);
	}

	@Override
	public List<SysCommunityResource> getByCommunityName(String communityName, Integer pageIndex, Integer pageSize,
			String order) throws Exception {
		return sysCommunityResourceDao.getByCommunityName(communityName, pageIndex, pageSize, order);
	}

	@Override
	public List<SysCommunityResource> getCommunityLikeNameByParam(String communityName, Integer pageIndex,
			Integer pageSize, String order, String email, Collection<Integer> cities, boolean isdelete)
			throws Exception {
		return sysCommunityResourceDao.getCommunityLikeNameByParam(communityName, pageIndex, pageSize, order, email,
				cities, isdelete);
	}

	@Override
	public Long getCountLikeNameByParam(String communityName, String email, Collection<Integer> cities,
			boolean isdelete) throws Exception {
		return sysCommunityResourceDao.getCountLikeNameByParam(communityName, email, cities, isdelete);
	}

	@Override
	public List<SysCommunityResource> getPageListByCities(List<Integer> cityIds, Integer pageIndex, Integer pageSize,
			String order) throws Exception {
		return sysCommunityResourceDao.getPageListByCities(cityIds, pageIndex, pageSize, order);
	}

	@Override
	public List<SysCommunityResource> getPageListByUser(String email, Integer pageIndex, Integer pageSize, String order)
			throws Exception {
		return sysCommunityResourceDao.getPageListByUser(email, pageIndex, pageSize, order);
	}

	@Override
	public Long getCommunityCount() throws Exception {
		return sysCommunityResourceDao.getCommunityCount();
	}

	@Override
	public Long getCommunityCountByCities(List<Integer> cityIds) throws Exception {
		return sysCommunityResourceDao.getCommunityCountByCities(cityIds);
	}

	@Override
	public Long getCommunityCountByUser(String email) throws Exception {
		return sysCommunityResourceDao.getCommunityCountByUser(email);
	}

	@Override
	public SysCommunityResource getNearestCommunityByCitiesOrderByCreatetime(Collection<Integer> cityIds)
			throws Exception {
		return sysCommunityResourceDao.getNearestCommunityByCitiesOrderByCreatetime(cityIds);
	}

	@Override
	public SysCommunityResource getNearestCommunityByUserOrderByCreatetime(String email) throws Exception {
		return sysCommunityResourceDao.getNearestCommunityByUserOrderByCreatetime(email);
	}

	@Override
	public List<SysCommunityResource> getCommunityByCitiesOrderByCreatetimePaging(Collection<Integer> cityIds,
			int pageindex, int pagesize, SlRef<Integer> count) throws Exception {
		List<Integer> ids = new ArrayList<Integer>();
		ids.addAll(cityIds);
		Integer countByIsDelete = sysCommunityResourceDao.getCountByIsDelete(null, ids, false);
		count.set(countByIsDelete);
		return sysCommunityResourceDao.getCommunityByCitiesOrderByCreatetimePaging(cityIds, pageindex, pagesize);
	}

	@Override
	public List<SysCommunityResource> getCommunityByUserOrderByCreatetimePaging(String email, int pageindex,
			int pagesize, SlRef<Integer> count) throws Exception {
		Integer communityCountByUser = sysCommunityResourceDao.getCountByIsDelete(email, null, false);
		count.set(communityCountByUser);
		return sysCommunityResourceDao.getCommunityByUserOrderByCreatetimePaging(email, pageindex, pagesize);
	}

	@Override
	public Long getCommunityCountByName(String communityName) throws Exception {
		return sysCommunityResourceDao.getCommunityCountByName(communityName);
	}

	@Override
	public List<SysCommunityResource> searchCommunity(String projectid, String categoryid, String cityid,
			String regionid, String communityid, String communityname, Integer pageIndex, Integer pageSize,
			boolean isdelete) throws Exception {
		// TODO Auto-generated method stub
		return sysCommunityResourceDao.searchCommunity(projectid, categoryid, cityid, regionid, communityid,
				communityname, pageIndex, pageSize, isdelete);
	}

	@Override
	public Long searchCommunityCount(String projectid, String categoryid, String cityid, String regionid,
			String communityid, String communityname, boolean isdelete) throws Exception {
		// TODO Auto-generated method stub
		return sysCommunityResourceDao.searchCommunityCount(projectid, categoryid, cityid, regionid, communityid,
				communityname, isdelete);
	}

}
