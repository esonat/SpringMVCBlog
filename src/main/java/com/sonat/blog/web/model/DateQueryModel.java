package com.sonat.blog.web.model;

import javax.validation.constraints.NotNull;

public class DateQueryModel {
	private String dateFrom;
	private String dateTo;
	private DateQueryEnum dateQueryEnum;
	
	public DateQueryModel(String dateFrom,String dateTo,DateQueryEnum dateQueryEnum){
		this.dateFrom=dateFrom;
		this.dateTo=dateTo;
		this.dateQueryEnum=dateQueryEnum;
	}
	
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public DateQueryEnum getDateQueryEnum() {
		return dateQueryEnum;
	}
	public void setDateQueryEnum(DateQueryEnum dateQueryEnum) {
		this.dateQueryEnum = dateQueryEnum;
	}
	
	
}
