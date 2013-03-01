package com.scrbl.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware {

	/**
	 * @author Vishvesh Deshmukh / Amit Mhatre
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
		System.out.println(pageX);
		return SUCCESS;
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