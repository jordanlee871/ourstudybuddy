package com.billjc.framework.util;

import java.security.MessageDigest;

/**
 * Title:MD5Util.java
 * 
 * desc:MD5加密类
 * 使用方法MD5Util.encode32("需加密的字符串")，返回加密后的内容（32位密文）
 * 使用方法MD5Util.encode16("需加密的字符串")，返回加密后的内容（16位密文）
 * @author Guihua Liu
 * @date 2011-10-24
 * @time 10:23:07
 * @version 1.0
 */
public class MD5Util {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 小写32位加密
	 * @param origin
	 * @return
	 */
	public static String encode32L(String origin) {
		String resultString = null;
		byte[] originBytes = origin.getBytes();
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(originBytes));
		} catch (Exception ex) {
		}
		return resultString;
	}

	/**
	 * 小写16位加密
	 * @param origin
	 * @return
	 */
	public static String encode16L(String origin) {
		String md5_32 = encode32L(origin);
		return md5_32.substring(8, 24);
	}
	
	/**
	 * 大写16位加密
	 * @param origin
	 * @return
	 */
	public static String encode16U(String origin){
		return encode16L(origin).toUpperCase();
	}
	
	/**
	 * 大写32位加密
	 * @param origin
	 * @return
	 */
	public static String encode32U(String origin){
		return encode32L(origin).toUpperCase();
	}

	public static void main(String[] args) {
		String md5_16l = encode16L("111111");
		String md5_32l = encode32L("111111");
		String md5_16u = encode16U("111111");
		String md5_32u = encode32U("111111");
		System.out.println(md5_16l+"\n"+md5_32l+"\n"+md5_16u+"\n"+md5_32u);
	}
}
