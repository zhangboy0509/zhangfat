package com.zhangfat.common.orm.pojo;

import java.io.Serializable;
import java.util.List;

public class PageResultPojo<T> implements Serializable {

	private static final long serialVersionUID = -4688179460082527074L;
	
	//record number per page
	private Integer size;
	//total record number
	private int totalNumber;
	//result list
	private List<T> result;
	//total page number
	private Integer totalPages;
	//current page number
	private Integer number;
	
	public PageResultPojo(){}
	public PageResultPojo(List<T> content,int totalElements){
		this.result=content;
		this.totalNumber=totalElements;
	}
	public PageResultPojo(List<T> content,int totalElements,int number,int size){
		this.result=content;
		this.totalNumber=totalElements;
		this.number=number;
		this.size=size;
		if(size!=0)
		this.totalPages=(totalElements+size-1)/size;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
