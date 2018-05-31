package Test;

import java.net.URLEncoder;
import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fang.common.network.HttpClientUtils;
import com.fang.im.management.utils.MD5Util;
import com.fang.im.management.utils.SlMd5;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] groupname = { "特价房大连", "特价房哈尔滨", "特价房本溪", "特价房吉林", "特价房鞍山", "特价房沈阳", "特价房唐山", "特价房长春", "特价房四平",
				"特价房呼和浩特", "特价房张家口", "特价房廊坊", "特价房包头", "特价房大同", "特价房保定", "特价房北京", "特价房徐州", "特价房秦皇岛", "特价房太原", "特价房天津",
				"特价房连云港", "特价房南宁", "特价房石家庄", "特价房衡水", "特价房邯郸", "特价房南阳", "特价房承德", "特价房青岛", "特价房泉州", "特价房济南", "特价房佛山",
				"特价房威海", "特价房汕头", "特价房烟台", "特价房临沂", "特价房珠海", "特价房广州", "特价房海南", "特价房三亚", "特价房漳州", "特价房福州", "特价房惠州",
				"特价房北海", "特价房东莞", "特价房桂林", "特价房深圳", "特价房柳州", "特价房南通", "特价房上海", "特价房昆山", "特价房杭州", "特价房嘉兴", "特价房绍兴",
				"特价房宁波", "特价房厦门", "特价房台州", "特价房温州", "特价房南京", "特价房镇江", "特价房苏州", "特价房湖州", "特价房常州", "特价房张家港", "特价房常熟",
				"特价房无锡", "特价房扬州", "特价房淮安", "特价房江门", "特价房南昌", "特价房合肥", "特价房舟山", "特价房长沙", "特价房武汉", "特价房郑州", "特价房兰州",
				"特价房西安", "特价房潍坊", "特价房银川", "特价房洛阳", "特价房乌鲁木齐", "特价房咸阳", "特价房汉中", "特价房渭南", "特价房重庆", "特价房绵阳", "特价房乐山",
				"特价房成都", "特价房南充", "特价房西宁", "特价房岳阳", "特价房大理", "特价房昆明", "特价房贵阳", "特价租房大连", "特价租房哈尔滨", "特价租房本溪", "特价租房吉林",
				"特价租房鞍山", "特价租房沈阳", "特价租房唐山", "特价租房长春", "特价租房四平", "特价租房呼和浩特", "特价租房张家口", "特价租房廊坊", "特价租房包头", "特价租房大同",
				"特价租房保定", "特价租房北京", "特价租房徐州", "特价租房秦皇岛", "特价租房太原", "特价租房天津", "特价租房连云港", "特价租房南宁", "特价租房石家庄", "特价租房衡水",
				"特价租房邯郸", "特价租房南阳", "特价租房承德", "特价租房青岛", "特价租房泉州", "特价租房济南", "特价租房佛山", "特价租房威海", "特价租房汕头", "特价租房烟台",
				"特价租房临沂", "特价租房珠海", "特价租房广州", "特价租房海南", "特价租房三亚", "特价租房漳州", "特价租房福州", "特价租房惠州", "特价租房北海", "特价租房东莞",
				"特价租房桂林", "特价租房深圳", "特价租房柳州", "特价租房南通", "特价租房上海", "特价租房昆山", "特价租房杭州", "特价租房嘉兴", "特价租房绍兴", "特价租房宁波",
				"特价租房厦门", "特价租房台州", "特价租房温州", "特价租房南京", "特价租房镇江", "特价租房苏州", "特价租房湖州", "特价租房常州", "特价租房张家港", "特价租房常熟",
				"特价租房无锡", "特价租房扬州", "特价租房淮安", "特价租房江门", "特价租房南昌", "特价租房合肥", "特价租房舟山", "特价租房长沙", "特价租房武汉", "特价租房郑州",
				"特价租房兰州", "特价租房西安", "特价租房潍坊", "特价租房银川", "特价租房洛阳", "特价租房乌鲁木齐", "特价租房咸阳", "特价租房汉中", "特价租房渭南", "特价租房重庆",
				"特价租房绵阳", "特价租房乐山", "特价租房成都", "特价租房南充", "特价租房西宁", "特价租房岳阳", "特价租房大理", "特价租房昆明", "特价租房贵阳"

		};
		String[] cityname = { "大连", "哈尔滨", "本溪", "吉林", "鞍山", "沈阳", "唐山", "长春", "四平", "呼和浩特", "张家口", "廊坊", "包头", "大同",
				"保定", "北京", "徐州", "秦皇岛", "太原", "天津", "连云港", "南宁", "石家庄", "衡水", "邯郸", "南阳", "承德", "青岛", "泉州", "济南", "佛山",
				"威海", "汕头", "烟台", "临沂", "珠海", "广州", "海南", "三亚", "漳州", "福州", "惠州", "北海", "东莞", "桂林", "深圳", "柳州", "南通",
				"上海", "昆山", "杭州", "嘉兴", "绍兴", "宁波", "厦门", "台州", "温州", "南京", "镇江", "苏州", "湖州", "常州", "张家港", "常熟", "无锡",
				"扬州", "淮安", "江门", "南昌", "合肥", "舟山", "长沙", "武汉", "郑州", "兰州", "西安", "潍坊", "银川", "洛阳", "乌鲁木齐", "咸阳", "汉中",
				"渭南", "重庆", "绵阳", "乐山", "成都", "南充", "西宁", "岳阳", "大理", "昆明", "贵阳", "大连", "哈尔滨", "本溪", "吉林", "鞍山", "沈阳",
				"唐山", "长春", "四平", "呼和浩特", "张家口", "廊坊", "包头", "大同", "保定", "北京", "徐州", "秦皇岛", "太原", "天津", "连云港", "南宁",
				"石家庄", "衡水", "邯郸", "南阳", "承德", "青岛", "泉州", "济南", "佛山", "威海", "汕头", "烟台", "临沂", "珠海", "广州", "海南", "三亚",
				"漳州", "福州", "惠州", "北海", "东莞", "桂林", "深圳", "柳州", "南通", "上海", "昆山", "杭州", "嘉兴", "绍兴", "宁波", "厦门", "台州",
				"温州", "南京", "镇江", "苏州", "湖州", "常州", "张家港", "常熟", "无锡", "扬州", "淮安", "江门", "南昌", "合肥", "舟山", "长沙", "武汉",
				"郑州", "兰州", "西安", "潍坊", "银川", "洛阳", "乌鲁木齐", "咸阳", "汉中", "渭南", "重庆", "绵阳", "乐山", "成都", "南充", "西宁", "岳阳",
				"大理", "昆明", "贵阳" };
		String[] groupids = {

		};

		String groupnotice = "欢迎加入房天下业主群";
		String username = "l:小宅姐";
		// 群类型 1 2
		System.out.println("a---" + groupname.length + "----" + cityname.length);

		for (int i = 0; i < groupname.length; i++) {
			if (i == 184 || i == 180 || i == 166 || i == 141 || i == 83 || i == 72 || i == 62) {
			//if (i == 62) {
				try {
					String id = group(username, groupname[i], username);
					System.out.println(i + "-----" + id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

//		 try {
//		 group("l:soufunso222495387", "大家好", "l:soufunso222495387");
//		 } catch (Exception e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
		
		
		//s_sign:0f1b3abd33577a0fefd4d5eb1ccad53d|
		//{grouplist=l:小宅姐, groupname=特价房张家港, command=autoCreateGroup, username=l:小宅姐} | 
		//command=autoCreateGroupgrouplist=l:小宅姐groupname=特价房张家港username=l:小宅姐key_2015-08-27 17:53:45
		//command=autoCreateGroupgrouplist=l:小宅姐groupname=特价房张家港username=l:小宅姐key_2015-08-27 17:53:45
		//F1B3ABD33577A0FEFD4D5EB1CCAD53D
		
		
//		System.out.println("a----"+SlMd5.hashPassword("command=autoCreateGroupgrouplist=l:小宅姐groupname=特价房张家港username=l:小宅姐key_2015-08-27 17:53:45"));
//		System.out.println("a----"+MD5Util.Md5("command=autoCreateGroupgrouplist=l:小宅姐groupname=特价房张家港username=l:小宅姐key_2015-08-27 17:53:45"));
	}

	public static String group(String username, String groupName, String goupList) throws Exception {
		String imSecretKey = "key_2015-08-27 17:53:45";
		String utf8groupname = URLEncoder.encode(groupName, "utf-8");
		String utf8username = URLEncoder.encode(username, "utf-8");
		String utf8goupList = URLEncoder.encode(goupList, "utf-8");
		String sign = MD5Util.Md5(String.format("command=autoCreateGroupgrouplist=%sgroupname=%susername=%s",
				goupList, groupName, username) + imSecretKey);
		String addCommunityImUrl = MessageFormat.format(
				"http://124.251.46.130:8089/ServerInterface?command=autoCreateGroup&username={0}&groupname={1}&grouplist={2}&sign={3}",
				utf8username, utf8groupname, utf8goupList, sign);
		String result = HttpClientUtils.requestUrl(addCommunityImUrl, 1000 * 3, "utf-8");
		 //System.out.println("a----"+result+"----"+addCommunityImUrl+"----"+sign);
		JSONObject obj = JSON.parseObject(result);
		//
		if (obj != null && obj.get("ret_code") != null) {
			if (obj.get("ret_code").toString().equals("0")) {
				int groupid = Integer.parseInt(obj.get("msg").toString());
				// System.out.println(groupid);
				return String.valueOf(groupid);
			} else {
				return "又出错了";
			}
		} else {
			return "出错了";
		}
	}

}
