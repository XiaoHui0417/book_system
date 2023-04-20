package com.example.book_system.vo;

import java.util.List;

import com.example.book_system.entity.UserOrderList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserOrderListRequest {
	@JsonProperty("user_shop_order_list")
	private List<UserOrderList> UserShop;

	public List<UserOrderList> getUserShop() {
		return UserShop;
	}

	public void setUserShop(List<UserOrderList> userShop) {
		UserShop = userShop;
	}

}
