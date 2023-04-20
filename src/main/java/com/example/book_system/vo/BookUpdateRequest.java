package com.example.book_system.vo;

import java.util.List;

import com.example.book_system.entity.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookUpdateRequest {
	@JsonProperty("old_booktitle")
	private String oldBookTitle;
	@JsonProperty("new_booktitle")
	private String newBookTitle;
	@JsonProperty("old_isbn")
	private String oldisbn;
	@JsonProperty("new_isbn")
	private String newisbn;
	@JsonProperty("old_author")
	private String oldAuthor;
	@JsonProperty("new_author")
	private String newAuthor;
	@JsonProperty("old_price")
	private int oldPrice;
	@JsonProperty("new_price")
	private int newPrice;
	@JsonProperty("old_stockQuantity")
	private int oldStockQuantity;
	@JsonProperty("new_stockQuantity")
	private int newStockQuantity;
	@JsonProperty("old_salesQuantity")
	private int oldSalesQuantity;
	@JsonProperty("new_salesQuantity")
	private int newSalesQuantity;
	@JsonProperty("book_list")
	private List<Book> bookList;

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public BookUpdateRequest() {
		super();
	}

	public String getOldBookTitle() {
		return oldBookTitle;
	}

	public void setOldBookTitle(String oldBookTitle) {
		this.oldBookTitle = oldBookTitle;
	}

	public String getNewBookTitle() {
		return newBookTitle;
	}

	public void setNewBookTitle(String newBookTitle) {
		this.newBookTitle = newBookTitle;
	}

	public String getOldisbn() {
		return oldisbn;
	}

	public void setOldisbn(String oldisbn) {
		this.oldisbn = oldisbn;
	}

	public String getNewisbn() {
		return newisbn;
	}

	public void setNewisbn(String newisbn) {
		this.newisbn = newisbn;
	}

	public String getOldAuthor() {
		return oldAuthor;
	}

	public void setOldAuthor(String oldAuthor) {
		this.oldAuthor = oldAuthor;
	}

	public String getNewAuthor() {
		return newAuthor;
	}

	public void setNewAuthor(String newAuthor) {
		this.newAuthor = newAuthor;
	}

	public int getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(int oldPrice) {
		this.oldPrice = oldPrice;
	}

	public int getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(int newPrice) {
		this.newPrice = newPrice;
	}

	public int getOldStockQuantity() {
		return oldStockQuantity;
	}

	public void setOldStockQuantity(int oldStockQuantity) {
		this.oldStockQuantity = oldStockQuantity;
	}

	public int getNewStockQuantity() {
		return newStockQuantity;
	}

	public void setNewStockQuantity(int newStockQuantity) {
		this.newStockQuantity = newStockQuantity;
	}

	public int getOldSalesQuantity() {
		return oldSalesQuantity;
	}

	public void setOldSalesQuantity(int oldSalesQuantity) {
		this.oldSalesQuantity = oldSalesQuantity;
	}

	public int getNewSalesQuantity() {
		return newSalesQuantity;
	}

	public void setNewSalesQuantity(int newSalesQuantity) {
		this.newSalesQuantity = newSalesQuantity;
	}

}
