/**
 * File：SysCity.java
 * Package：com.fang.im.management.po
 * Author：jin
 * Date：2017年4月10日 下午4:01:38
 * Copyright (C) 2017-2017 房天下-版权所有
 */
package com.fang.im.management.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * Description: SysCity
 * </p>
 *
 * @author jinshilei 2017年4月10日
 * @version 1.0
 *
 */
public class SysCity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3844180654669860793L;

	/**
	 * 城市id
	 */
	private Integer sysCityId;

	/**
	 * 城市名
	 */
	private String cityName;

	/**
	 * 大区ID
	 */
	private Integer sysregionid;

	/**
	 * 拥有该城市权限的用户
	 */
	//
	// @JsonIgnore
	// private Set<SysUserResource> users;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 是否有效
	 */
	private Boolean isDelete;

	/**
	 *
	 * getSysCityId
	 *
	 * @return 返回值
	 */
	public Integer getSysCityId() {
		return sysCityId;
	}

	/**
	 *
	 * setSysCityId
	 *
	 * @param sysCityId
	 *            城市id
	 */
	public void setSysCityId(Integer sysCityId) {
		this.sysCityId = sysCityId;
	}

	/**
	 *
	 * getCityName
	 *
	 * @return 返回值
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 *
	 * setCityName
	 *
	 * @param cityName
	 *            城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	// /**
	// *
	// * getUsers
	// *
	// * @return 返回值
	// */
	// public Set<SysUserResource> getUsers() {
	// return users;
	// }

	/**
	 *
	 * setUsers
	 *
	 * @param users
	 *            用户
	 */
	// public void setUsers(Set<SysUserResource> users) {
	// this.users = users;
	// }

	/**
	 *
	 * getCreateTime
	 *
	 * @return 返回值
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 *
	 * setCreateTime
	 *
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 *
	 * getIsDelete
	 *
	 * @return 返回值
	 */
	public Boolean getIsDelete() {
		return isDelete;
	}

	/**
	 *
	 * setIsDelete
	 *
	 * @param isDelete
	 *            是否有效
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getSysregionid() {
		return sysregionid;
	}

	public void setSysregionid(Integer sysregionid) {
		this.sysregionid = sysregionid;
	}

	@Override
	public int hashCode() {
		return this.sysCityId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SysCity)) {
			return false;
		}
		SysCity city = (SysCity) obj;
		return city.getSysCityId().equals(this.getSysCityId());
	}
}
