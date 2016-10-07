package com.zhangfat.common.orm.mybatis;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = -8168234783245411542L;

	protected List<T> result;
	protected int totalNumber;
	
	public PageResult() {
	}

	public List<T> getResult() {
		return result;
	}

	public int getTotalNumber() {
		return totalNumber;
	}
	
	protected void setResult(List<T> result) {
		this.result = result;
	}

	protected void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

}
