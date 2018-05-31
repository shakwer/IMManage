package com.fang.im.management.web.vo.in;

/**
 * 添加群
 *
 * @author YZT_SouFun
 */
public class AddCommunityInVo {

	/**
	 * 群类型
	 */
	private Integer category;

	/**
	 * 群名称
	 */
	private String communityname;

	/**
	 * 群人数上限
	 */
	private Integer limit;

	/**
	 * 群所在城市
	 */
	private Integer city;

	/**
	 * 群公告
	 */
	private String notice;

	/**
	 * 群头像
	 */
	private String pic;

	/**
	 * 成员列表
	 */
	private String grouplist;

	private String projectid;

	/**
	 * 获取群类型
	 *
	 * @return category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * 设置群类型
	 *
	 * @param category
	 *            群类型
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * 获取群名称
	 *
	 * @return communityname
	 */
	public String getCommunityname() {
		return communityname;
	}

	/**
	 * 设置群名称
	 *
	 * @param communityname
	 *            群名称
	 */
	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

	/**
	 * 获取群人数上限
	 *
	 * @return limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * 设置群人数上限
	 *
	 * @param limit
	 *            群人数上限
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * 获取群所在城市
	 *
	 * @return city
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * 设置群所在城市
	 *
	 * @param city
	 *            群所在城市
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	/**
	 * 获取群公告
	 *
	 * @return notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * 设置群公告
	 *
	 * @param notice
	 *            群公告
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * 获取群头像
	 *
	 * @return pic
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * 设置群头像
	 *
	 * @param pic
	 *            群头像
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * 获取群成员列表
	 *
	 * @return grouplist
	 */
	public String getGrouplist() {
		return grouplist;
	}

	/**
	 * 设置群成员列表
	 *
	 * @param grouplist
	 *            群成员列表
	 */
	public void setGrouplist(String grouplist) {
		this.grouplist = grouplist;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

}
