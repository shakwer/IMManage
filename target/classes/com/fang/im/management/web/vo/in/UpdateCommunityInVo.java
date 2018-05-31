package com.fang.im.management.web.vo.in;

/**
 * 修改群invo
 *
 * @author YZT_SouFun
 */
public class UpdateCommunityInVo {

	/**
	 * communityid
	 */
	private String communityid;

	/**
	 * category
	 */
	private Integer category;

	/**
	 * communityname
	 */
	private String communityname;

	/**
	 * communityholder
	 */
	private String communityholder;

	private String communitynotice;
	private String communitypic;

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
	 * communityid
	 *
	 * @return communityid
	 */
	public String getCommunityid() {
		return communityid;
	}

	/**
	 * communityid
	 *
	 * @param communityid
	 *            communityid
	 */
	public void setCommunityid(String communityid) {
		this.communityid = communityid;
	}

	/**
	 * category
	 *
	 * @return category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * category
	 *
	 * @param category
	 *            category
	 */
	public void setCategory(Integer category) {
		this.category = category;
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

	public String getCommunitynotice() {
		return communitynotice;
	}

	public void setCommunitynotice(String communitynotice) {
		this.communitynotice = communitynotice;
	}

	public String getCommunitypic() {
		return communitypic;
	}

	public void setCommunitypic(String communitypic) {
		this.communitypic = communitypic;
	}

}
