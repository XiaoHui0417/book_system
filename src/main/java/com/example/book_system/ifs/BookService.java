package com.example.book_system.ifs;




import java.util.List;

import com.example.book_system.entity.Book;
import com.example.book_system.vo.BookRequest;
import com.example.book_system.vo.BookResponse;
import com.example.book_system.vo.BookUpdateRequest;
import com.example.book_system.vo.UserOrderListRequest;
import com.example.book_system.vo.UserOrderListResponse;




public interface BookService {

	// ��q�ק�A�s�W���y
	public BookResponse addBook(BookRequest request);

	// �j�M���y:�P���
	public BookResponse searchBook(BookRequest request);
	
	// �j�M���y:���O��
	public BookResponse searchBookByUser(BookRequest request);
	
	// ��q�ק�A�w�s�q�i�f
	public BookResponse updateStockQuantity(BookRequest request);
	
	// ��q�ק�A�ק����
	public BookResponse updatePrice(BookRequest request);
	
	// ��s�P��P�w�s �� �d�ߨϥΪ��ʶR���y����B�ƶq
	public UserOrderListResponse userBuyBook(UserOrderListRequest request);
	
	// �d�߾P�qtop5 ���y
	public BookResponse searchTop5BookBySQ();
	

}
