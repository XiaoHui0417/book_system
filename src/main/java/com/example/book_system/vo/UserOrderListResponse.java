package com.example.book_system.vo;

import java.util.List;

import com.example.book_system.entity.Book;
import com.example.book_system.entity.UserOrderList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrderListResponse {

	@JsonProperty("usershop")
	private UserOrderList userShopOrder;

	@JsonProperty("usershop_list")
	private List<UserOrderList> userShopOrderlist;

	private String message;
	
	public UserOrderListResponse() {
		
	}
	
	public UserOrderListResponse(String message) {
		super();
		this.message = message;
	}
	
	public UserOrderListResponse(List<UserOrderList> userShopOrderlist, String message) {
		super();
		this.userShopOrderlist = userShopOrderlist;
		this.message = message;
	}

	public UserOrderListResponse(UserOrderList userShopOrder, List<UserOrderList> userShopOrderlist,
			String message) {
		super();
		this.userShopOrder = userShopOrder;
		this.userShopOrderlist = userShopOrderlist;
		this.message = message;
	}

	public UserOrderList getUserShopOrder() {
		return userShopOrder;
	}

	public void setUserShopOrder(UserOrderList userShopOrder) {
		this.userShopOrder = userShopOrder;
	}

	public List<UserOrderList> getUserShopOrderlist() {
		return userShopOrderlist;
	}

	public void setUserShopOrderlist(List<UserOrderList> userShopOrderlist) {
		this.userShopOrderlist = userShopOrderlist;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
