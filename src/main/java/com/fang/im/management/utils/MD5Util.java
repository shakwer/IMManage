package com.fang.im.management.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String Md5(String key){
    	String ret = null;
    	try {
    		if(key!=null){
    			MessageDigest md = MessageDigest.getInstance("MD5");
    			byte [] a= md.digest(key.getBytes("UTF-8"));
    			StringBuffer sb = new StringBuffer();
    			for (byte b : a) {
    				String h = Integer.toHexString(b&0xff);
    				if(h.length()<2){
    					sb.append("0").append(h);
    				} else{
    					sb.append(h);
    				}
    			}
    			ret = sb.toString();
    		}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
    }
	/**
	 * 生成MD5后的16位字节数组（不转换成十六进制字符串）
	 * @param key
	 * @return
	 */
	public static byte[] MD5(String key){
		byte[] bs = null;
		try {
			if(key != null){
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				bs = md5.digest(key.getBytes("UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bs;
	}
}
