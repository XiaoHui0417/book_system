package com.example.book_system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_system.ifs.BookService;
import com.example.book_system.vo.BookRequest;
import com.example.book_system.vo.BookResponse;
import com.example.book_system.vo.BookUpdateRequest;
import com.example.book_system.vo.UserOrderListRequest;
import com.example.book_system.vo.UserOrderListResponse;



@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;

	@PostMapping("add_book")
	public BookResponse addBook(@RequestBody BookRequest request) {
		return bookService.addBook(request);
		}
	
	@PostMapping("search_book")
	public BookResponse searchBook(@RequestBody BookRequest request) {
		return bookService.searchBook(request);
	}
	
	@PostMapping("search_book_by_sales")
	public BookResponse searchBookBySales(@RequestBody BookRequest request) {
		return bookService.searchBook(request);
	}
	
	@PostMapping("search_book_by_users")
	public BookResponse searchBookByUser(@RequestBody BookRequest request) {
		return bookService.searchBookByUser(request);
	}
	
	@GetMapping("search_top5_book_by_SQ")
	public BookResponse searchTop5BookBySQ(@RequestBody BookRequest request) {
		return bookService.searchTop5BookBySQ();
	}
		
	
	@PostMapping("/update_stock_quantity")
	public BookResponse updateStockQuantity(@RequestBody BookRequest bookRequest) {

		return bookService.updateStockQuantity(bookRequest);
	}
	
	@PostMapping("/update_price")
	public BookResponse updatePrice(@RequestBody BookRequest bookRequest) {

		return bookService.updatePrice(bookRequest);
	}
	

	@PostMapping("user_buy_book")
	public UserOrderListResponse userBuyBook(@RequestBody UserOrderListRequest request) {
		return bookService.userBuyBook(request);
		}
}
