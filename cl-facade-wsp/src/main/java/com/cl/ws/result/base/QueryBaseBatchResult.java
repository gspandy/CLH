package com.cl.ws.result.base;

import com.cl.ws.base.ClBaseResult;
import com.cl.ws.base.Money;
import com.cl.ws.base.PageComponent;
import com.google.common.collect.Lists;


import java.util.List;

public class QueryBaseBatchResult<T> extends ClBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 420002915574977408L;
	long totalCount = 0;
	Money totalMoney = new Money(0);
	Money totalLoanAmount = new Money(0);
	Money totalLoanedAmount = new Money(0);
	long pageSize = 10;
	long pageNumber = 1;
	long pageCount = 1;
	List<T> pageList = Lists.newArrayList();
	
	public void initPageParam(PageComponent component) {
		this.setTotalCount(component.getRowCount());
		this.setPageSize(component.getPageSize());
		this.setPageNumber(component.getCurPage());
		this.pageCount = component.getPageCount();
	}
	
	public List<T> getPageList() {
		return pageList;
	}
	
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Money getTotalMoney() {
		return this.totalMoney;
	}
	
	public void setTotalMoney(Money totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public Money getTotalLoanAmount() {
		return totalLoanAmount;
	}
	
	public void setTotalLoanAmount(Money totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}
	
	public Money getTotalLoanedAmount() {
		return totalLoanedAmount;
	}
	
	public void setTotalLoanedAmount(Money totalLoanedAmount) {
		this.totalLoanedAmount = totalLoanedAmount;
	}
	
	public long getPageCount() {
		return this.pageCount;
	}
	
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryBaseBatchResult [totalCount=");
		builder.append(totalCount);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", pageList=");
		builder.append(pageList);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
