package com.fang.im.management.web.vo.out;

/**
 * 群管理输出类
 *
 * @author YZT_SouFun
 */
public class CommunityManagerOutVo {

	/**
	 * ImUserServiceInterfaceUrlOut
	 */
	private ImUserServiceInterfaceUrlOut imurlout;

	/**
	 * username
	 */
	private String communityid;

	/**
	 * communityid
	 *
	 * @return communityid
	 */
	public String getCommunityid() {
		return communityid;
	}

	/**
	 * 群id
	 *
	 * @param communityid
	 *            群id
	 */
	public void setCommunityid(String communityid) {
		this.communityid = communityid;
	}

	/**
	 * communityholder
	 */
	private String communityholder;

	/**
	 * communityname
	 */
	private String communityname;

	/**
	 * createtime
	 */
	private String createtime;

	/**
	 * categoryid
	 */
	private Integer categoryid;

	/**
	 * categoryname
	 */
	private String categoryname;

	/**
	 * cityid
	 */
	private Integer cityid;

	/**
	 * qrcode
	 */
	private String qrcode;

	/**
	 * qrcode
	 */
	private String qrurl;
	/*
	 * 群通知
	 **/
	private String communityNotice;
	/**
	 * 楼盘ID
	 **/
	private String projectid;
	/**
	 * 群头像
	 **/
	private String communityPic;
	/**
	 * 群上限人数
	 **/
	private Integer communityLimit;
	/**
	 * 大区名称
	 **/
	private String regionName;
	/**
	 * qrurlencode
	 */
	private String qrurlencode;

	/**
	 * getQrurlencode
	 *
	 * @return String
	 */
	public String getQrurlencode() {
		return qrurlencode;
	}

	/**
	 * setQrurlencode
	 *
	 * @param qrurlencode
	 *            qrurlencode
	 */
	public void setQrurlencode(String qrurlencode) {
		this.qrurlencode = qrurlencode;
	}

	/**
	 * getQrurl
	 *
	 * @return qrurl
	 */
	public String getQrurl() {
		return qrurl;
	}

	/**
	 * qrurl
	 *
	 * @param qrurl
	 *            qrurl
	 */
	public void setQrurl(String qrurl) {
		this.qrurl = qrurl;
	}

	/**
	 * username
	 */
	private String cityname;

	/**
	 * imurlout
	 *
	 * @return imurlout
	 */
	public ImUserServiceInterfaceUrlOut getImurlout() {
		return imurlout;
	}

	/**
	 * imurlout
	 *
	 * @param imurlout
	 *            imurlout
	 */
	public void setImurlout(ImUserServiceInterfaceUrlOut imurlout) {
		this.imurlout = imurlout;
	}

	/**
	 * communityholder
	 *
	 * @return communityholder
	 */
	public String getCommunityholder() {
		return communityholder;
	}

	/**
	 * communityholder
	 *
	 * @param communityholder
	 *            communityholder
	 */
	public void setCommunityholder(String communityholder) {
		this.communityholder = communityholder;
	}

	/**
	 * createtime
	 *
	 * @return createtime
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * createtime
	 *
	 * @param createtime
	 *            createtime
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * categoryid
	 *
	 * @return categoryid
	 */
	public Integer getCategoryid() {
		return categoryid;
	}

	/**
	 * categoryid
	 *
	 * @param categoryid
	 *            categoryid
	 */
	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	/**
	 * categoryname
	 *
	 * @return categoryname
	 */
	public String getCategoryname() {
		return categoryname;
	}

	/**
	 * categoryname
	 *
	 * @param categoryname
	 *            categoryname
	 */
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	/**
	 * cityid
	 *
	 * @return cityid
	 */
	public Integer getCityid() {
		return cityid;
	}

	/**
	 * cityid
	 *
	 * @param cityid
	 *            cityid
	 */
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	/**
	 * cityname
	 *
	 * @return cityname
	 */
	public String getCityname() {
		return cityname;
	}

	/**
	 * cityname
	 *
	 * @param cityname
	 *            cityname
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	/**
	 * communityname
	 *
	 * @return communityname
	 */
	public String getCommunityname() {
		return communityname;
	}

	/**
	 * communityname
	 *
	 * @param communityname
	 *            communityname
	 */
	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

	/**
	 * qrcode
	 *
	 * @return qrcode
	 */
	public String getQrcode() {
		return qrcode;
	}

	/**
	 * qrcode
	 *
	 * @param qrcode
	 *            qrcode
	 */
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getCommunityNotice() {
		return communityNotice;
	}

	public void setCommunityNotice(String communityNotice) {
		this.communityNotice = communityNotice;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getCommunityPic() {
		return communityPic;
	}

	public void setCommunityPic(String communityPic) {
		this.communityPic = communityPic;
	}

	public Integer getCommunityLimit() {
		return communityLimit;
	}

	public void setCommunityLimit(Integer communityLimit) {
		this.communityLimit = communityLimit;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
