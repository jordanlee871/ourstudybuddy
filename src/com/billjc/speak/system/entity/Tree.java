package com.billjc.speak.system.entity;

public class Tree {
	private String id;
	private String name;
	private String pid;
	private String isParent = "false";
	private String open = "false";
	private String click = "";
	private String url = "";
	private String target = "";
	
	public Tree() {
	}

	public Tree(String id, String name, String pid, String isParent, String open, String click, String url, String target) {
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.isParent = isParent;
		this.open = open;
		this.click = click;
		this.url = url;
		this.target = target;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(!(obj instanceof Tree)){
			return false;
		}else{
			Tree tree = (Tree)obj;
			return id.equals(tree.id);
		}
	}
	
}
