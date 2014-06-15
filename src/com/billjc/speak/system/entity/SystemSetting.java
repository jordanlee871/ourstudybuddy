package com.billjc.speak.system.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wayne
 *
 */
public class SystemSetting implements Serializable {
	private static final long serialVersionUID = 7197830830905019572L;
	private SystemSettingBean stuEmgCxl;
	private SystemSettingBean teaEmgCxl;
	private SystemSettingBean urlTaobao;
	private SystemSettingBean appKeyTaobao;
	private SystemSettingBean appSecretTaobao;
	private SystemSettingBean sessionKeyTaobao;
	private SystemSettingBean speakingIdTaobao;
	private SystemSettingBean essayIdTaobao;
	private SystemSettingBean essayPart1SkuTaobao;
	private SystemSettingBean essayPart2SkuTaobao;
	private SystemSettingBean essay7D5ESkuTaobao;

	public static String STU_EMG_CXL_DB_NAME = "STU_EMG_CXL";
	public static String TEA_EMG_CXL_DB_NAME = "TEA_EMG_CXL";
	public static String URL_DB_NAME = "URL";
	public static String APP_KEY_DB_NAME = "APP_KEY";
	public static String APP_SECRET_DB_NAME = "APP_SECRET";
	public static String SESSION_KEY_DB_NAME = "SESSION_KEY";
	public static String SPEAKING_ID_DB_NAME = "SPEAKING_ID";
	public static String ESSAY_ID_DB_NAME = "ESSAY_ID";
	public static String ESSAY_PART1_SKU_DB_NAME = "ESSAY_PART1_SKU";
	public static String ESSAY_PART2_SKU_DB_NAME = "ESSAY_PART2_SKU";
	public static String ESSAY_7D5E_SKU_DB_NAME = "ESSAY_7D5E_SKU";

	public SystemSetting() {
		super();

	}

	public SystemSetting(List<SystemSettingBean> ssbs) {
		super();
		for (SystemSettingBean ssb : ssbs) {
			if (STU_EMG_CXL_DB_NAME.equals(ssb.getSettingName())) {
				stuEmgCxl = ssb;
			} else if (TEA_EMG_CXL_DB_NAME.equals(ssb.getSettingName())) {
				teaEmgCxl = ssb;
			} else if (URL_DB_NAME.equals(ssb.getSettingName())) {
				urlTaobao = ssb;
			} else if (APP_KEY_DB_NAME.equals(ssb.getSettingName())) {
				appKeyTaobao = ssb;
			} else if (APP_SECRET_DB_NAME.equals(ssb.getSettingName())) {
				appSecretTaobao = ssb;
			} else if (SESSION_KEY_DB_NAME.equals(ssb.getSettingName())) {
				sessionKeyTaobao = ssb;
			} else if (SPEAKING_ID_DB_NAME.equals(ssb.getSettingName())) {
				speakingIdTaobao = ssb;
			} else if (ESSAY_ID_DB_NAME.equals(ssb.getSettingName())) {
				essayIdTaobao = ssb;
			} else if (ESSAY_PART1_SKU_DB_NAME.equals(ssb.getSettingName())) {
				essayPart1SkuTaobao = ssb;
			} else if (ESSAY_PART2_SKU_DB_NAME.equals(ssb.getSettingName())) {
				essayPart2SkuTaobao = ssb;
			} else if (ESSAY_7D5E_SKU_DB_NAME.equals(ssb.getSettingName())) {
					essay7D5ESkuTaobao = ssb;
			} else {

			}
		}
	}

	public SystemSettingBean getStuEmgCxl() {
		return stuEmgCxl;
	}

	public void setStuEmgCxl(SystemSettingBean stuEmgCxl) {
		this.stuEmgCxl = stuEmgCxl;
	}

	public SystemSettingBean getTeaEmgCxl() {
		return teaEmgCxl;
	}

	public void setTeaEmgCxl(SystemSettingBean teaEmgCxl) {
		this.teaEmgCxl = teaEmgCxl;
	}

	public SystemSettingBean getAppKeyTaobao() {
		return appKeyTaobao;
	}

	public void setAppKeyTaobao(SystemSettingBean appKeyTaobao) {
		this.appKeyTaobao = appKeyTaobao;
	}

	public SystemSettingBean getAppSecretTaobao() {
		return appSecretTaobao;
	}

	public void setAppSecretTaobao(SystemSettingBean appSecretTaobao) {
		this.appSecretTaobao = appSecretTaobao;
	}

	public SystemSettingBean getSessionKeyTaobao() {
		return sessionKeyTaobao;
	}

	public void setSessionKeyTaobao(SystemSettingBean sessionKeyTaobao) {
		this.sessionKeyTaobao = sessionKeyTaobao;
	}

	public SystemSettingBean getSpeakingIdTaobao() {
		return speakingIdTaobao;
	}

	public void setSpeakingIdTaobao(SystemSettingBean speakingIdTaobao) {
		this.speakingIdTaobao = speakingIdTaobao;
	}

	public SystemSettingBean getEssayIdTaobao() {
		return essayIdTaobao;
	}

	public void setEssayIdTaobao(SystemSettingBean essayIdTaobao) {
		this.essayIdTaobao = essayIdTaobao;
	}

	public SystemSettingBean getEssayPart1SkuTaobao() {
		return essayPart1SkuTaobao;
	}

	public void setEssayPart1SkuTaobao(SystemSettingBean essayPart1SkuTaobao) {
		this.essayPart1SkuTaobao = essayPart1SkuTaobao;
	}

	public SystemSettingBean getEssayPart2SkuTaobao() {
		return essayPart2SkuTaobao;
	}

	public void setEssayPart2SkuTaobao(SystemSettingBean essayPart2SkuTaobao) {
		this.essayPart2SkuTaobao = essayPart2SkuTaobao;
	}

	public SystemSettingBean getUrlTaobao() {
		return urlTaobao;
	}

	public void setUrlTaobao(SystemSettingBean urlTaobao) {
		this.urlTaobao = urlTaobao;
	}

	public SystemSettingBean getEssay7D5ESkuTaobao() {
		return essay7D5ESkuTaobao;
	}

	public void setEssay7D5ESkuTaobao(SystemSettingBean essay7d5eSkuTaobao) {
		essay7D5ESkuTaobao = essay7d5eSkuTaobao;
	}
	
	
}
