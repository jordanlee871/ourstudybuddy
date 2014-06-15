package com.billjc.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherUtil {
	/**
	 * 根据常量替换内容
	 * @param content
	 * @param stuName
	 * @param stuID
	 * @param teaName
	 * @param clsDT
	 * @param clsLength
	 * @return
	 */
	public static String replaceContent(String content,String stuName,String stuID,String teaName,
			String clsDT,String clsLength){
		content = content.replace("<stuName>", stuName);
		content = content.replace("<stuID>", stuID);
		content = content.replace("<teaName>", teaName);
		content = content.replace("<clsDT>", clsDT);
		content = content.replace("<clsLength>", clsLength);
		return content;
	}
}
