package com.example.book_system.vo;



import java.util.List;

import javax.persistence.Column;

import com.example.book_system.entity.Book;
import com.example.book_system.entity.UserOrderList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class BookRequest {

	@JsonProperty("book")
	private Book book;

	@JsonProperty("book_list")
	private List<Book> bookList;
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Book> getBookList() {
		return bookList;
	}
	
	

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

}
