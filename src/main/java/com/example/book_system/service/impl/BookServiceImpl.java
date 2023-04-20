package com.example.book_system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.book_system.entity.Book;
import com.example.book_system.entity.UserOrderList;
import com.example.book_system.ifs.BookService;
import com.example.book_system.repository.BookDao;
import com.example.book_system.vo.BookRequest;
import com.example.book_system.vo.BookResponse;
import com.example.book_system.vo.UserOrderListRequest;
import com.example.book_system.vo.UserOrderListResponse;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;

	/**
	 * 新增書籍
	 */
	@Override
	public BookResponse addBook(BookRequest request) {

		// 從request中取出多筆 Book
		List<Book> errorBookList = new ArrayList<>();
		// errorBookList = bookDao.findAll(); // 從book撈所有資料 SQL = select * from book

		List<Book> boookList = request.getBookList();
		// 檢查多筆 Book : 使用 foreach 逐筆檢查

		for (Book item : boookList) {
			// 檢查單筆 Book(item)
			if (!StringUtils.hasText(item.getBookTitle()) || !StringUtils.hasText(item.getISBN())
					|| !StringUtils.hasText(item.getAuthor()) || !StringUtils.hasText(item.getCategories())
					|| item.getPrice() == 0 || item.getSalesQuantity() == 0 || item.getStockQuantity() == 0) {
				return new BookResponse("新增失敗!!");
			} // 檢查是否為空

			if (bookDao.existsById(item.getISBN())) {
				// 把id有重複的Book 加到 errorInfoList
				errorBookList.add(item);
			}

		}
		// 檢查 errorboookList 是否為空
		if (!errorBookList.isEmpty()) {
			return new BookResponse(errorBookList, "帳號已存在");
		}
		// 新增多筆 Book
		bookDao.saveAll(boookList);
		return new BookResponse(boookList, "新增成功");
	}

	/**
	 * 只更新庫存量
	 * 
	 */
	@Override
	public BookResponse updateStockQuantity(BookRequest request) {

		List<Book> boookList = request.getBookList(); // 檢查多筆 Book : 使用 foreach 逐筆檢查
		
		List<Book> updateList = new ArrayList<>(); // 需要更新的list

		for (Book item : boookList) {
			Book temp = new Book();
			Book old_book = bookDao.findById(item.getISBN()).get();

			// 檢查ISBN不為空
			if (!StringUtils.hasText(item.getBookTitle()) && !StringUtils.hasText(item.getISBN())
					|| !StringUtils.hasText(item.getAuthor()) || !StringUtils.hasText(item.getCategories())
					|| item.getPrice() == 0 || item.getSalesQuantity() == 0 || item.getStockQuantity() == 0 && old_book.equals(null)) {
				return new BookResponse("更新失敗!!");
			}
			
			temp.setAuthor(old_book.getAuthor());
			temp.setBookTitle(old_book.getBookTitle());
			temp.setISBN(old_book.getISBN());
			temp.setPrice(old_book.getPrice());
			temp.setSalesQuantity(item.getSalesQuantity()); // 只更新庫存
			temp.setStockQuantity(old_book.getStockQuantity());
			
			updateList.add(item);
		}

		bookDao.saveAll(updateList); // 修改多筆 Book 資料
		return new BookResponse(boookList, "修改成功");
	} 

	/**
	 * 只更新價格
	 * 
	 */
	@Override
	public BookResponse updatePrice(BookRequest request) {

		// 要注意api輸入的格式。
		List<Book> boookList = request.getBookList(); // 檢查多筆 Book : 使用 foreach 逐筆檢查
		
		List<Book> updateList = new ArrayList<>(); // 需要更新的list

		for (Book item : boookList) {
			Book temp = new Book();
			Book old_book = bookDao.findById(item.getISBN()).get();
			
			// 檢查ISBN 不為空，且價錢不為0
			if (!StringUtils.hasText(old_book.getBookTitle()) && !StringUtils.hasText(old_book.getISBN()) && item.getPrice() != 0 && old_book.equals(null)) {
				return new BookResponse("更新失敗!!");
			}
			
			temp.setAuthor(old_book.getAuthor());
			temp.setBookTitle(old_book.getBookTitle());
			System.out.println("step1 . " + old_book.getAuthor() + "\n");
			System.out.println("step1 . " + old_book.getBookTitle() + "\n");
			System.out.println("step1 . " + old_book.getISBN() + "\n");
			temp.setISBN(old_book.getISBN());
			temp.setPrice(item.getPrice()); // 只更新價格
			temp.setSalesQuantity(old_book.getSalesQuantity());
			temp.setStockQuantity(old_book.getStockQuantity());
			updateList.add(item);
			System.out.println("step1 ." + "\n");
		}
		System.out.println("step2 ." + "\n");
		bookDao.saveAll(updateList); // 修改多筆 Book 資料
		System.out.println("step3 ." + "\n");
		return new BookResponse(boookList, "修改成功");
	} 

	/**
	 * userBuyBook 消費者購買(可買多本，但至多3本，取前3): 
	 * 只顯示書名、ISBN、作者、價格、購買數量、購買總價格、
	 * 銷售量+1、庫存量-1
	 * 
	 * request 使用者發送的請求
	 */
	@Override
	public UserOrderListResponse userBuyBook(UserOrderListRequest request) {
		// 取得使用者消費的request
		List<UserOrderList> userShop = request.getUserShop();
		// 顯示購物結果清單
		List<UserOrderList> resultOrderList = new ArrayList<>();
		Integer totalCount = 0;
		//檢查多筆 Book : 使用 foreach 逐筆檢查
		for (UserOrderList item : userShop) {
			UserOrderList temp = new UserOrderList();
			// 取得DB中剩餘庫存書籍資料
			Book old_book = bookDao.findById(item.getISBN()).get();
			// 檢查單筆 Book(item) 檢查isbn是否為空、可買多本，但至多3本，庫存量大於購買量
			if (!item.getISBN().equals(null) && 
				0 < item.getStockQuantity()  && 
				item.getStockQuantity() <= 3 && 
				!old_book.equals(null)       &&
				old_book.getStockQuantity() >= item.getStockQuantity() ) {
				return new UserOrderListResponse("購買失敗!!");
			} 
			
			old_book.setSalesQuantity(old_book.getSalesQuantity()+1); //銷售量加一
			old_book.setStockQuantity(old_book.getStockQuantity()-1); //庫存量減一

			totalCount += old_book.getPrice() * item.getHowManyBuy(); // 顯示購買總價 = DB書籍資料 * 使用者購買數量
			
			//只顯示書名、ISBN、作者、價格、購買數量，購買總價格
			temp.setISBN(old_book.getISBN());
			temp.setBookTitle(old_book.getBookTitle());
			temp.setAuthor(old_book.getAuthor());
			temp.setPrice(old_book.getPrice());
			temp.setHowManyBuy(item.getHowManyBuy());
			temp.setTotalPrices(totalCount); 			
			resultOrderList.add(temp); // 設定進之後需要顯示給API的Response
			
            bookDao.save(old_book);
		}
		
		return new UserOrderListResponse(resultOrderList,"新增成功");
	}
	
	/**
	 * searchBookByUser()
	 * 供書籍商查詢使用
	 * (透過書名或 ISBN或作者)
	 * 顯示書名、ISBN、作者、價格、銷售量、庫存量
	 * 
	 */
	@Override
	public BookResponse searchBook(BookRequest request) {
		List<Book> boookList = request.getBookList();
		List<Book> search_boookList = new ArrayList<>();
		
		List<Book> final_result = new ArrayList<>();

		// 透過書名或 ISBN或作者
		for (Book item : boookList) {
			if (!item.getBookTitle().equals(null)) {
				// 依具書名搜尋書籍資料
				search_boookList = bookDao.findByBookTitle(item.getBookTitle());
				for(Book book : search_boookList) {
					System.out.println("Step 3 : " + book +  " \n");
					if (!book.equals(null)) {
						
						String title = book.getBookTitle();
						String isbn = book.getISBN();
						String author = book.getAuthor();
						Integer price = book.getPrice();
						Integer stockQuantity = book.getStockQuantity();
						Integer salesQuantity = book.getSalesQuantity();
						final_result.add(new Book(title, isbn, author, price, stockQuantity, salesQuantity, null));
					}
				}
				search_boookList.forEach( (element) -> {
					
				});
			} else if (!item.getISBN().equals(null)) {
				// 依具ISBN搜尋書籍資料
				Book temp = bookDao.findById(item.getISBN()).get();
				String title = temp.getBookTitle();
				String isbn = temp.getISBN();
				String author = temp.getAuthor();
				Integer price = temp.getPrice();
				final_result.add(new Book(title, isbn, author, price));
			} else if (!item.getAuthor().equals(null)) {
				// 依具作者搜尋書籍資料
				search_boookList = bookDao.findByAuthor(item.getAuthor());
				search_boookList.forEach( (element) -> {
					if (!element.equals(null)) {
						String title = element.getBookTitle();
						String isbn = element.getISBN();
						String author = element.getAuthor();
						Integer price = element.getPrice();
						Integer stockQuantity = element.getStockQuantity();
						Integer salesQuantity = element.getSalesQuantity();
						final_result.add(new Book(title, isbn, author, price, stockQuantity, salesQuantity, null));
					}
				});
			} else {
				return new BookResponse(final_result, "查詢失敗");
			}

		}

		return new BookResponse(final_result, "查詢成功");
	}

	/**
	 * searchBookByUser()
	 * 供使用者查詢使用
	 * 只顯示書名、ISBN、作者、價格
	 * 
	 */
	@Override
	public BookResponse searchBookByUser(BookRequest request) {
		List<Book> boookList = request.getBookList();
		List<Book> search_boookList = new ArrayList<>();
		
		List<Book> final_result = new ArrayList<>();

		// 透過書名或 ISBN或作者
		for (Book item : boookList) {
			if (!item.getBookTitle().equals(null)) {
				// 依具書名搜尋書籍資料
				search_boookList = bookDao.findByBookTitle(item.getBookTitle());
				for(Book book : search_boookList) {
					if (!book.equals(null)) {
						
						String title = book.getBookTitle();
						String isbn = book.getISBN();
						String author = book.getAuthor();
						Integer price = book.getPrice();
						System.out.println("Step 4 : " + title +" : "+ isbn +  " \n");
						final_result.add(new Book(title, isbn, author, price));
					}
				}
				search_boookList.forEach( (element) -> {
					
				});
			} else if (!item.getISBN().equals(null)) {
				// 依具ISBN搜尋書籍資料
				Book temp = bookDao.findById(item.getISBN()).get();
				String title = temp.getBookTitle();
				String isbn = temp.getISBN();
				String author = temp.getAuthor();
				Integer price = temp.getPrice();
				final_result.add(new Book(title, isbn, author, price));
			} else if (!item.getAuthor().equals(null)) {
				// 依具作者搜尋書籍資料
				search_boookList = bookDao.findByAuthor(item.getAuthor());
				search_boookList.forEach( (element) -> {
					if (!element.equals(null)) {
						String title = element.getBookTitle();
						String isbn = element.getISBN();
						String author = element.getAuthor();
						Integer price = element.getPrice();
						final_result.add(new Book(title, isbn, author, price));
					}
				});
			} else {
				return new BookResponse(final_result, "查詢失敗");
			}

		}

		return new BookResponse(final_result, "查詢成功");
	}

	
	/**
	 * 搜尋top5 熱門銷售量的書籍
	 */
	@Override
	public BookResponse searchTop5BookBySQ() {
		List<Book> final_result = bookDao.findTop5BySalesQuantity();
		if(final_result.equals(null)) { 
			return new BookResponse(final_result, "查詢失敗");
		} else {
			return new BookResponse(final_result, "查詢成功");
		}
	}

	

}
