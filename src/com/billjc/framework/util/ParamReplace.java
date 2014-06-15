package com.billjc.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:ParamReplace.java
 *
 * desc:
 * 
 * @author Guihua Liu
 * @date 2011-11-17
 * @time 下午02:40:34
 * @version 1.0
 */
public class ParamReplace {

	/**
	 * 验证字符串是否纯数字(正数，包含小数)
	 * @param digit
	 * @return true 纯数字
	 *         false 非纯数字
	 */
	public static boolean isDigit(String param){
		String pattern = "[0-9]+(.[0-9]+)?";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(param);
		return m.matches();
	}

	/**
	 * 验证字符串中是否存在SQL脚本和JS脚本,如果参数为NULL,则返回true
	 * 
	 * @param param
	 * @return true存在 , false不存在
	 */
	public static boolean sqlInjectFilter(String param){
		if(null == param)return true;
		String injects = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,|<|>|script|document|eval";   
		String inject[] = injects.split("\\|");   
		for (int i=0 ; i < inject.length ; i++ ){
			if (param.toLowerCase().indexOf(inject[i])>=0) 
				return true;
		}   
		return false;   
	}
	
	/**
	 * 过滤SQL关键字符
	 * @param content
	 * @return
	 */
	public static String filterSqlInject(String content){
	   String flt ="'|and|exec|insert|select|delete|update|count|having|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,|<|>|script";
	   String filter[] = flt.split("|");
	   for(int i=0;i<filter.length ; i++){
		   content = content.replace(filter[i], "");
	   }
	   return content;
	}
	
	public static void main(String[] args) {
		System.out.println(ParamReplace.isDigit("-1"));
		
		System.out.println(ParamReplace.sqlInjectFilter("1=1"));
	}

}
