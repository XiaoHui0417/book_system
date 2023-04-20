package com.example.book_system.vo;

import java.util.List;

import com.example.book_system.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {

	@JsonProperty("book")
	private Book book;

	@JsonProperty("book_list")
	private List<Book> bookList;

	public List<Book> getBookList() {
		return bookList;
	}

	private String message;

	public BookResponse() {
		super();
	}

	public BookResponse(List<Book> bookList, String message) {
		super();
		this.bookList = bookList;
		this.message = message;
	}

	public BookResponse(String message) {
		super();
		this.message = message;
	}

	

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

}
