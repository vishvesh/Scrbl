package com.scrbl.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware {

	/**
	 * @author Vishvesh Deshmukh
	 * Created : 2nd February, 2013
	 * Project : Scrbl
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String pageX;
	private String pageY;
	private String timeArray;

	public String firstBlood()
	{
		name = "Welcome To Scrbl!";
		return SUCCESS;
	}
	
	public String writeValuesToExcel()
	{
		System.out.println("Page X : "+pageX);
		System.out.println("Page Y : "+pageY);
		writeToExcel(pageX,pageY);
		return SUCCESS;
	}
	
	private void writeToExcel(String pageX, String pageY)
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		 
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("0", new String[] {"X-Coordinate", "Y-Coordinate", "Time"});
		String[] pagex = pageX.split(",");
		String[] pagey = pageY.split(",");
		int counter = 1;
		
		while(counter <= pagex.length)
		{
			System.out.print("Counter : "+counter + " ");
			data.put(Integer.toString(counter), new String[] {pagex[counter - 1], pagey[counter - 1], Integer.toString(counter)});
			counter++;
		}
		/*Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry pairs = (Map.Entry)it.next();
		 System.out.println(pairs.getKey()+",");
		}*/
		//SortedSet<String> keySet = new TreeSet<String>(data.keySet());
		List<String> keySet=new ArrayList<String>(data.keySet());
		Collections.sort(keySet);
		//keySet =  (TreeSet<String>) data.keySet();
		int rownum = 0;
		for (String key : keySet) {
			//System.out.println("KEYSET KEY : "+key);
		    Row row = sheet.createRow(rownum++);
		    String [] objArr = (String[]) data.get(key);
		    int cellnum = 0;
		    for (String obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        //System.out.println("String is : "+obj + " : cell column index : "+cell.getColumnIndex() +" : cell row index : "+cell.getRowIndex());
		        if(obj instanceof String)
		            cell.setCellValue((String)obj);
		    }
		}
		 
		try {
		    FileOutputStream out = new FileOutputStream(new File("C:\\new.xls"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		
	}
	
	public String getPageX() {
		return pageX;
	}

	public void setPageX(String pageX) {
		this.pageX = pageX;
	}

	public String getPageY() {
		return pageY;
	}

	public void setPageY(String pageY) {
		this.pageY = pageY;
	}

	public String getTimeArray() {
		return timeArray;
	}

	public void setTimeArray(String timeArray) {
		this.timeArray = timeArray;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}