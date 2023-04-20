package com.example.book_system.ifs;




import java.util.List;

import com.example.book_system.entity.Book;
import com.example.book_system.vo.BookRequest;
import com.example.book_system.vo.BookResponse;
import com.example.book_system.vo.BookUpdateRequest;
import com.example.book_system.vo.UserOrderListRequest;
import com.example.book_system.vo.UserOrderListResponse;




public interface BookService {

	// у秖э穝糤膟
	public BookResponse addBook(BookRequest request);

	// 穓碝膟:綪扳坝
	public BookResponse searchBook(BookRequest request);
	
	// 穓碝膟:禣
	public BookResponse searchBookByUser(BookRequest request);
	
	// у秖э畐秖秈砯
	public BookResponse updateStockQuantity(BookRequest request);
	
	// у秖ээ基
	public BookResponse updatePrice(BookRequest request);
	
	// 穝綪扳籔畐  琩高ㄏノ潦禦膟基计秖
	public UserOrderListResponse userBuyBook(UserOrderListRequest request);
	
	// 琩高綪秖top5 膟
	public BookResponse searchTop5BookBySQ();
	

}
