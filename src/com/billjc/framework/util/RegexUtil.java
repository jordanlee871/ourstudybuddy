package com.billjc.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	/**
	 * 匹配手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){  
		  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");  
		  
		Matcher m = p.matcher(mobiles);  
		  
		System.out.println(m.matches()+"---");  
		  
		return m.matches();  
		  
	}  
	
	/**
	 * 匹配邮箱
	 * @param mobiles
	 * @return
	 */
	public static boolean isEmail(String email){  
		  
		Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");  
		  
		Matcher m = p.matcher(email);  
		  
		System.out.println(m.matches()+"---");  
		  
		return m.matches();  
		  
	}
}
