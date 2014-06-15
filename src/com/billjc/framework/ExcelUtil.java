package com.billjc.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;

import com.billjc.framework.util.ExcelEntity;

public class ExcelUtil {
	
	/**
	 * 设置一张sheet的标头
	 * @param wb
	 * @param sheet
	 * @param nameArr 标头的名字
	 * @return
	 */
	public static Sheet setHeader(HSSFWorkbook wb ,Sheet sheet, String[] nameArr){
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		Font cnFont = wb.createFont();   
	    cnFont.setFontHeightInPoints((short) 12); 
	    cnFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    cnFont.setFontName("黑体");  
	    
	    CellStyle cnStyle = wb.createCellStyle(); 
	    cnStyle.setAlignment(CellStyle.ALIGN_LEFT);
	    cnStyle.setFont(cnFont); 
	    
	    //设列宽
		/*sheet.setColumnWidth(0,(int)(288*15.38));
		sheet.setColumnWidth(1,(int)(288*8.38));
		sheet.setColumnWidth(2,(int)(288*10.88));
		sheet.setColumnWidth(3,(int)(288*13.13));
		sheet.setColumnWidth(4,(int)(288*15.38));
		sheet.setColumnWidth(5,(int)(288*15.38));
		sheet.setColumnWidth(6,(int)(288*15.38));
		sheet.setColumnWidth(7,(int)(288*15.38));*/
		
	    row=(HSSFRow) sheet.createRow(0);
	    row.setHeight((short)(14.25*20));
	    
	    for(int i=0; i<nameArr.length; i++){
	        cell=row.createCell(i);
			cell.setCellStyle(cnStyle);   
			cell.setCellValue(nameArr[i]);  
	    }
			
		return sheet;
	}
	
	/**
	 * 设置一张sheet表的表内容
	 * @param wb
	 * @param sheet
	 * @param data 数据
	 * @param clzz 类
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Sheet setContent(HSSFWorkbook wb ,Sheet sheet, List<?> data, Class clzz) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		Font cnFont = wb.createFont();   
	    cnFont.setFontHeightInPoints((short) 12); 
	    cnFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
	    cnFont.setFontName("宋体");  
	    
	    CellStyle cnStyle = wb.createCellStyle(); 
	    cnStyle.setAlignment(CellStyle.ALIGN_LEFT);
	    cnStyle.setFont(cnFont); 
		
	    for(int line=1; line<=data.size(); line++){
	    	ExcelEntity ee = (ExcelEntity)data.get(line-1);
	    	Object obj = data.get(line-1);
		    row=(HSSFRow) sheet.createRow(line);
		    row.setHeight((short)(14.25*20));
		    
			for(int i=0; i<ee.getContentSort().length; i++){
				String getMethodName = ee.getContentSort()[i];
				Method method = clzz.getDeclaredMethod(getMethodName, null);
				Object value = method.invoke(obj, null);
				String cellValue = (value == null ? "" : value).toString();
				cell=row.createCell(i);
				cell.setCellStyle(cnStyle);   
				cell.setCellValue(cellValue);  
			}
	    }
		
		return sheet;
	}
}
