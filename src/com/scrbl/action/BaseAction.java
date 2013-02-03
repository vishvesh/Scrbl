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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String firstBlood()
	{
		name = "Welcome To Scrbl!";
		return SUCCESS;
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
}