/**
 * File：CommunityOperateService.java
 * Package：com.fang.im.management.service
 * Author：jin
 * Date：2017年4月13日 上午11:36:57
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.service;

import com.fang.im.management.po.SysCommunityResource;
import com.fang.im.management.utils.SlRef;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Description: CommunityOperateService
 * </p>
 *
 * @author jinshilei 2017年4月13日
 * @version 1.0
 *
 */
public interface CommunityOperateService {

	Long searchCommunityCount(String projectid, String categoryid, String cityid, String regionid, String communityid,
			String communityname, boolean isdelete) throws Exception;

	/**
	 * 搜索群聊
	 **/
	List<SysCommunityResource> searchCommunity(String projectid, String categoryid, String cityid, String regionid,
			String communityid, String communityname, Integer pageIndex, Integer pageSize, boolean isdelete)
			throws Exception;

	/**
	 *
	 * 获取城市下的所有群
	 *
	 * @param cityIds
	 *            城市的id(传null，获取所有城市的群)
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getCommunityByCities(List<Integer> cityIds) throws Exception;

	/**
	 *
	 * 获取城市下的所有群，通过创建时间降序
	 *
	 * @param cityIds
	 *            城市的id(传null，获取所有城市的群)
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getCommunityByCitiesOrderByCreatetime(Collection<Integer> cityIds) throws Exception;

	/**
	 * getCommunityByCitiesOrderByCreatetimePaging
	 *
	 * @param cityIds
	 *            cityIds
	 * @param pageindex
	 *            pageindex
	 * @param pagesize
	 *            pagesize
	 * @param count
	 *            count
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	List<SysCommunityResource> getCommunityByCitiesOrderByCreatetimePaging(Collection<Integer> cityIds, int pageindex,
			int pagesize, SlRef<Integer> count) throws Exception;

	/**
	 * getNearestCommunityByCitiesOrderByCreatetime
	 *
	 * @param cityIds
	 *            cityIds
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	SysCommunityResource getNearestCommunityByCitiesOrderByCreatetime(Collection<Integer> cityIds) throws Exception;

	/**
	 *
	 * 查询群主管理的所有群
	 *
	 * @param email
	 *            用户邮箱(不带@fang.com)
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getCommunityByUser(String email) throws Exception;

	/**
	 *
	 * 查询群主管理的所有群，通过创建时间降序
	 *
	 * @param email
	 *            用户邮箱(不带@fang.com)
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getCommunityByUserOrderByCreatetime(String email) throws Exception;

	/**
	 * getCommunityByUserOrderByCreatetimePaging
	 *
	 * @param email
	 *            email
	 * @param pageindex
	 *            pageindex
	 * @param pagesize
	 *            pagesize
	 * @param count
	 *            count
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	List<SysCommunityResource> getCommunityByUserOrderByCreatetimePaging(String email, int pageindex, int pagesize,
			SlRef<Integer> count) throws Exception;

	/**
	 * getNearestCommunityByUserOrderByCreatetime
	 *
	 * @param email
	 *            email
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	SysCommunityResource getNearestCommunityByUserOrderByCreatetime(String email) throws Exception;

	/**
	 * 通过群ID查询
	 *
	 * @param id
	 *            id
	 * @return community
	 * @throws Exception
	 *             Exception
	 */
	SysCommunityResource getCommunityById(String id) throws Exception;

	/**
	 * 通过群ID和isdelete查询
	 *
	 * @param communityid
	 *            群id
	 * @param isdelete
	 *            删除标记
	 * @return SysCommunityResource
	 * @throws Exception
	 *             异常
	 */
	SysCommunityResource getCommunityByIdAndIsDelete(String communityid, boolean isdelete) throws Exception;

	/**
	 * 通过各种条件筛选对应的列表
	 *
	 * 邮箱查出此邮箱为群主的列表,可空 城市查出对应城市的列表，可空
	 *
	 * @param email
	 *            邮箱(不带@fang.com)
	 * @param cityIds
	 *            城市idlist
	 * @param isdelete
	 *            是否已删除
	 * @param pageSize
	 *            页大小
	 * @param pageIndex
	 *            页码
	 * @param orderStr
	 *            排序规则
	 * @return List<SysCommunityResource> 结果列表
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getCommunityByIsDelete(final String email, final List<Integer> cityIds,
			final boolean isdelete, final int pageSize, final int pageIndex, final String orderStr) throws Exception;

	/**
	 * 添加群信息
	 *
	 * @param communityid
	 *            群id
	 * @param communityHolder
	 *            群主邮箱
	 * @param communityName
	 *            群名称
	 * @param city
	 *            城市
	 * @param category
	 *            分类，
	 * @param createTime
	 *            创建时间
	 * @return boolean 添加成功标记
	 * @throws Exception
	 *             异常
	 */
	Boolean addCommunity(String communityid, String communityHolder, String communityName, int city, int category,
			Date createTime,String projectid) throws Exception;

	/**
	 * 获取群列表总数
	 *
	 * @param email
	 *            用户邮箱(不带@fang.com)
	 * @param cityIds
	 *            城市idlist
	 * @param isdelete
	 *            删除标记
	 * @return Integer
	 * @throws Exception
	 *             异常
	 */
	Integer getCountByIsDelete(final String email, final List<Integer> cityIds, final boolean isdelete)
			throws Exception;

	/**
	 * 修改群信息
	 *
	 * @param communityid
	 *            群id
	 * @param communityName
	 *            群名
	 * @param category
	 *            群分类
	 * @return Boolean
	 * @throws Exception
	 *             异常
	 */
	Boolean updateSomeParam(String communityid, String communityName, Integer category) throws Exception;

	void sendMessage(String[] ids, String message);

	/**
	 * 修改群删除标记
	 *
	 * @param communityid
	 *            群id
	 * @param isdelete
	 *            删除标记
	 * @return Boolean
	 * @throws Exception
	 *             异常
	 */
	Boolean updateIsdeleteCommunity(String communityid, boolean isdelete) throws Exception;

	/**
	 * 修改群主
	 *
	 * @param communityid
	 *            群id
	 * @param communityholder
	 *            群主
	 * @return Boolean
	 * @throws Exception
	 *             异常
	 */
	Boolean updateCommunityHolder(String communityid, String communityholder) throws Exception;

	/**
	 * getCommunitiesByName
	 *
	 * @param name
	 *            name
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	List<SysCommunityResource> getCommunitiesByName(String name) throws Exception;

	/**
	 * 根据创建时间按一定的顺序获取群集合
	 *
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页大小
	 * @param order
	 *            顺序（"desc"代表倒序,"asc"代表正序）
	 * @return 群集合
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getPageListByCreateTime(Integer pageIndex, Integer pageSize, String order)
			throws Exception;

	/**
	 * 根据群名称模糊查询所有符合的群信息
	 *
	 * @param communityName
	 *            群名称
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页大小
	 * @param order
	 *            顺序（"desc"代表倒序,"asc"代表正序,排序字段为creatTime）
	 * @return 群信息集合
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getByCommunityName(String communityName, Integer pageIndex, Integer pageSize,
			String order) throws Exception;

	/**
	 *
	 * 分页获取城市下的群
	 *
	 * @param cityIds
	 *            城市的id(传null，获取所有城市的群)
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页大小
	 * @param order
	 *            排序（"asc"代表正序，"desc"代表倒序）
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getPageListByCities(List<Integer> cityIds, Integer pageIndex, Integer pageSize,
			String order) throws Exception;

	/**
	 *
	 * 分页查询群主管理的所有群
	 *
	 * @param email
	 *            用户邮箱(不带@fang.com)
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页大小
	 * @param order
	 *            排序（"asc"代表正序，"desc"代表倒序）
	 * @return 返回值
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getPageListByUser(String email, Integer pageIndex, Integer pageSize, String order)
			throws Exception;

	/**
	 * 查询模糊群名获取分页信息
	 *
	 * @param communityName
	 *            模糊群名
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @param order
	 *            排序
	 * @param email
	 *            邮箱可以为null
	 * @param cities
	 *            城市可以为null
	 * @param isdelete
	 *            删除标记
	 * @return List<SysCommunityResource>
	 * @throws Exception
	 *             异常
	 */
	List<SysCommunityResource> getCommunityLikeNameByParam(String communityName, Integer pageIndex, Integer pageSize,
			String order, String email, Collection<Integer> cities, boolean isdelete) throws Exception;

	/**
	 * 查询模糊群名的总数
	 *
	 * @param communityName
	 *            模糊群名
	 * @param email
	 *            群主可以为null
	 * @param cities
	 *            城市可以为null
	 * @param isdelete
	 *            删除标记
	 * @return Long
	 * @throws Exception
	 *             异常
	 */
	Long getCountLikeNameByParam(String communityName, String email, Collection<Integer> cities, boolean isdelete)
			throws Exception;

	/**
	 * 获取群总数
	 *
	 * @return 群总数
	 * @throws Exception
	 *             异常
	 */
	Long getCommunityCount() throws Exception;

	/**
	 * 根据城市id获取相应的群总数
	 *
	 * @param cityIds
	 *            城市ids
	 * @return 群总数
	 * @throws Exception
	 *             异常
	 */
	Long getCommunityCountByCities(List<Integer> cityIds) throws Exception;

	/**
	 * 根据群主获取管理的群总数
	 *
	 * @param email
	 *            用户邮箱(不带@fang.com)
	 * @return 群总数
	 * @throws Exception
	 *             异常
	 */
	Long getCommunityCountByUser(String email) throws Exception;

	/**
	 * 根据群名称模糊查询群总数
	 *
	 * @param communityName
	 *            群名称
	 * @return 群总数
	 * @throws Exception
	 *             异常
	 */
	Long getCommunityCountByName(String communityName) throws Exception;
}
