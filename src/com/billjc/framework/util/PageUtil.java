package com.billjc.framework.util;

public class PageUtil{
	protected int curPage = 0; //当前页
	protected int pageSize = 0; //每页多少行
	protected int endSize; //用于not in(select top endSize id)不在多少行内
	protected int totalRow; //共多少行
	protected int totalPage; //共多少页

	public PageUtil(){
		this.curPage = 1;
		this.pageSize = 10;
	}

	public int getStartRow(){
		return (curPage - 1) * pageSize + 1;
	}

	public int getEndRow(){
		return (curPage) * pageSize;
	}

	public int getStart(){
		if(curPage > 1)
			return (curPage - 1) * pageSize;
		else
			return 0;
	}

	public int getEnd(){
		return pageSize;
	}

	public int getCurPage(){
		return curPage;
	}

	public void setCurPage(int curPage){
		int temp = pageSize * (curPage - 1);
		this.setEndSize(temp);
		this.curPage = curPage;
	}

	public int getEndSize(){
		return endSize;
	}

	public void setEndSize(int endSize){
		this.endSize = endSize;
	}

	public int getPageSize(){
		return pageSize;
	}

	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
	}

	public int getTotalRow(){
		return totalRow;
	}

	public void setTotalRow(int totalRow){
		totalPage = totalRow / pageSize;
		if(totalRow % pageSize > 0)
			totalPage = totalPage + 1;
		this.totalRow = totalRow;
	}

	public int getTotalPage(){
		return this.totalPage;
	}

	public String getToolsMenu(){
		StringBuffer str = new StringBuffer("");
		int next, prev;
		prev = curPage - 1;
		next = curPage + 1;
		
		str.append("<a href=\"javascript:goPage(1);\" onclick=''>Frist</a>");
		
		if(curPage > 1){
			str.append("&nbsp;&nbsp;<a href=\"javascript:goPage(" + prev + ");\" onclick=''>Previous</a>");
		}else{
			str.append("&nbsp;&nbsp;<a title='Now First Page'>Previous</a>");
		}
		if(curPage < totalPage){
			str.append("&nbsp;&nbsp;<a href=\"javascript:goPage(" + next + ");\" class=\"page-next\" onclick=''>Next</a>");
		}else{
			str.append("&nbsp;&nbsp;<a title='Now Last Page' class=\"page-next\">Next</a>");
		}
		
		str.append("&nbsp;&nbsp;<a href=\"javascript:goPage("+totalPage+");\" onclick=''>Last</a>");
		
		str.append("&nbsp;&nbsp;Total<font color='#EC4308'>" + totalRow + "</font>records");
		str.append("&nbsp;Total<font color='#EC4308'>" + totalPage + "</font>Page");
		str.append("&nbsp;&nbsp;Page&nbsp;");
		str.append("<select style='width: 50px;' size='1' name='Pagelist' onchange='goPage(this.value)'>");
		for(int i = 1;i < totalPage + 1;i++){
			if(i == curPage){
				str.append("<option value='" + i + "' selected>" + i + "</option>");
			}else{
				str.append("<option value='" + i + "'>" + i + "</option>");
			}
		}
//		str.append("</select>页");
		str.append("</select>");
		str.append("<input type='hidden'  value='" + curPage + "' name=\"pages\" > ");
		str.append("<input type='hidden'  value='" + pageSize + "' name=\"pageSize\"> ");
		return str.toString();
	}
	
	
	public String getToolsMenuLotteryStatistics(){
		StringBuffer str = new StringBuffer("");
		str.append("Total&nbsp;<font color='#EC4308'>" + totalRow + "</font>&nbsp;records,");
		str.append("&nbsp;Page &nbsp;<font color='#EC4308'>" + curPage + "</font>/<font color='#EC4308'>" + totalPage + "</font>&nbsp; ");
		return str.toString();
	}
	
	public String getToolsMenuLotteryOperat(String path){
		StringBuffer str = new StringBuffer("");
		int next, prev;
		prev = curPage - 1;
		next = curPage + 1;
		
		str.append("<table border='0' align='right' cellpadding='0' cellspacing='0' style=' font-size: medium;'>\n");
		str.append("    <tr>\n");
		str.append("      <td width='5'><input type='button' value='First' style='cursor: pointer;width:50px;' onclick='goPage(1);' /></td>\n");
		if(curPage > 1){
			str.append("      <td width='45'><input type='button' value='Back' style='cursor: pointer;width:50px;' onclick='goPage(" + prev + ");'  /></td>\n");
		}else{
			str.append("      <td width='45'><input type='button' value='Back' style='cursor: pointer;width:50px;' /></td>\n");
		}
		if(curPage < totalPage){
			str.append("      <td width='45'><input type='button' value='Next' style='cursor: pointer;width:50px;' onclick='goPage(" + next + ");'  /></td>\n");
		}else{
			str.append("      <td width='45'><input type='button' value='Next' style='cursor: pointer;width:50px;' /></td>\n");
		}
		str.append("      <td width='40'><input type='button' value='Last' style='cursor: pointer;width:50px;' onclick='goPage(" +totalPage+ ");'  /></td>\n");
		str.append("      <td width='100'>\n");
		str.append("      	<div align='center'>\n");
		str.append("      		<span class='STYLE1'>Page&nbsp;<input id='go_page_to' type='text' size='4' onkeyup=\"value=value.replace(/\\D/g,'')\" style='height:12px; width:20px; border:1px solid #999999;' />  </span>\n");
		str.append("        </div>\n");
		str.append("      </td>\n");
		str.append("     <td width='40'><input type='button' value='Go' style='cursor: pointer;width:50px;'onclick=\"goPage($('#go_page_to').val());\"  /></td>\n");
		str.append("    </tr>\n");
		str.append("</table>\n");
		return str.toString();
	}
	
	
	
}
