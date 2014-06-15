package com.billjc.speak.report.entity;

import java.util.List;

import com.billjc.framework.util.ExcelEntity;

public class TeaDailyClsInfo extends ExcelEntity{

	private Integer teaId;
	private String link;
	private String name;
	private Integer trailTimes;
	private Double trailClass;
	private Integer noTrailTimes;
	private Double noTrailClass;
	private Integer freeClass;
	private Integer stuEmCxlTimes;
	private Integer teaEmCxlTimes;
	
	/**
	 * 试听次数
	 * @return
	 */
	public Integer getTrailTimes() {
		return trailTimes;
	}
	public void setTrailTimes(Integer trailTimes) {
		this.trailTimes = trailTimes;
	}
	
	/**
	 * 试听课时数
	 * @return
	 */
	public Double getTrailClass() {
		return trailClass;
	}
	public void setTrailClass(Double trailClass) {
		this.trailClass = trailClass;
	}
	
	/**
	 * 正常课程次数
	 * @return
	 */
	public Integer getNoTrailTimes() {
		return noTrailTimes;
	}
	public void setNoTrailTimes(Integer noTrailTimes) {
		this.noTrailTimes = noTrailTimes;
	}
	
	/**
	 * 正常课程课时数
	 * @return
	 */
	public Double getNoTrailClass() {
		return noTrailClass;
	}
	public void setNoTrailClass(Double noTrailClass) {
		this.noTrailClass = noTrailClass;
	}
	
	/**
	 * 空余时间总数
	 * @return
	 */
	public Integer getFreeClass() {
		return freeClass;
	}
	public void setFreeClass(Integer freeClass) {
		this.freeClass = freeClass;
	}
	
	/**
	 * 学生紧急取消次数
	 * @return
	 */
	public Integer getStuEmCxlTimes() {
		return stuEmCxlTimes;
	}
	public void setStuEmCxlTimes(Integer stuEmCxlTimes) {
		this.stuEmCxlTimes = stuEmCxlTimes;
	}
	
	/**
	 * 老师紧急取消次数
	 * @return
	 */
	public Integer getTeaEmCxlTimes() {
		return teaEmCxlTimes;
	}
	public void setTeaEmCxlTimes(Integer teaEmCxlTimes) {
		this.teaEmCxlTimes = teaEmCxlTimes;
	}
	
	/**
	 * 老师的id
	 * @return
	 */
	public Integer getTeaId() {
		return teaId;
	}
	public void setTeaId(Integer teaId) {
		this.teaId = teaId;
	}
	
	/**
	 * 链接
	 * @return
	 */
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * 老师名字
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
