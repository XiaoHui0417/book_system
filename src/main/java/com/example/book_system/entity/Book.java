package com.example.book_system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 創建一個實體 容器
@Table(name = "book")
public class Book {
	@Id // 主鍵
	@Column(name = "isbn") // 欄位
	private String isbn;

	@Column(name = "book_title")
	private String bookTitle;

	@Column(name = "author")
	private String author;

	@Column(name = "price")
	private int price;

	@Column(name = "stock_quantity")
	private int stockQuantity;

	@Column(name = "sales_quantity")
	private int salesQuantity;

	@Column(name = "categories")
	private String categories;

	public Book() {

	}
	
	public Book(String bookTitle, String ISBN, String author, int price) {
		this.bookTitle = bookTitle;
		this.isbn = ISBN;
		this.author = author;
		this.price = price;
	}


	public Book(String bookTitle, String ISBN, String author, int price, int stockQuantity, int salesQuantity,
			String categories) {
		this.bookTitle = bookTitle;
		this.isbn = ISBN;
		this.author = author;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.salesQuantity = salesQuantity;
		this.categories = categories;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(String ISBN) {
		this.isbn = ISBN;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

}
