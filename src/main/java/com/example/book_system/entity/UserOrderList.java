package com.example.book_system.entity;


public class UserOrderList extends Book {
	private Integer sellCounts;
	private Integer totalPrices; // 購買總價格
	private Integer howManyBuy; // 購買數量

	public UserOrderList(Integer sellCounts, Integer totalPrices, Integer howManyBuy) {
		super();
		this.sellCounts = sellCounts;
		this.totalPrices = totalPrices;
		this.howManyBuy = howManyBuy;
	}

	public UserOrderList(){
		
	}
	
	public Integer getSellCounts() {
		return sellCounts;
	}
	public void setSellCounts(Integer sellCounts) {
		this.sellCounts = sellCounts;
	}
	public Integer getTotalPrices() {
		return totalPrices;
	}
	public void setTotalPrices(Integer totalPrices) {
		this.totalPrices = totalPrices;
	}

	public Integer getHowManyBuy() {
		return howManyBuy;
	}

	public void setHowManyBuy(Integer howManyBuy) {
		this.howManyBuy = howManyBuy;
	}
	
	
}
