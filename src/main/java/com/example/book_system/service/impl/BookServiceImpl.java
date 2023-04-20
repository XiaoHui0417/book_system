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
	 * �s�W���y
	 */
	@Override
	public BookResponse addBook(BookRequest request) {

		// �qrequest�����X�h�� Book
		List<Book> errorBookList = new ArrayList<>();
		// errorBookList = bookDao.findAll(); // �qbook���Ҧ���� SQL = select * from book

		List<Book> boookList = request.getBookList();
		// �ˬd�h�� Book : �ϥ� foreach �v���ˬd

		for (Book item : boookList) {
			// �ˬd�浧 Book(item)
			if (!StringUtils.hasText(item.getBookTitle()) || !StringUtils.hasText(item.getISBN())
					|| !StringUtils.hasText(item.getAuthor()) || !StringUtils.hasText(item.getCategories())
					|| item.getPrice() == 0 || item.getSalesQuantity() == 0 || item.getStockQuantity() == 0) {
				return new BookResponse("�s�W����!!");
			} // �ˬd�O�_����

			if (bookDao.existsById(item.getISBN())) {
				// ��id�����ƪ�Book �[�� errorInfoList
				errorBookList.add(item);
			}

		}
		// �ˬd errorboookList �O�_����
		if (!errorBookList.isEmpty()) {
			return new BookResponse(errorBookList, "�b���w�s�b");
		}
		// �s�W�h�� Book
		bookDao.saveAll(boookList);
		return new BookResponse(boookList, "�s�W���\");
	}

	/**
	 * �u��s�w�s�q
	 * 
	 */
	@Override
	public BookResponse updateStockQuantity(BookRequest request) {

		List<Book> boookList = request.getBookList(); // �ˬd�h�� Book : �ϥ� foreach �v���ˬd
		
		List<Book> updateList = new ArrayList<>(); // �ݭn��s��list

		for (Book item : boookList) {
			Book temp = new Book();
			Book old_book = bookDao.findById(item.getISBN()).get();

			// �ˬdISBN������
			if (!StringUtils.hasText(item.getBookTitle()) && !StringUtils.hasText(item.getISBN())
					|| !StringUtils.hasText(item.getAuthor()) || !StringUtils.hasText(item.getCategories())
					|| item.getPrice() == 0 || item.getSalesQuantity() == 0 || item.getStockQuantity() == 0 && old_book.equals(null)) {
				return new BookResponse("��s����!!");
			}
			
			temp.setAuthor(old_book.getAuthor());
			temp.setBookTitle(old_book.getBookTitle());
			temp.setISBN(old_book.getISBN());
			temp.setPrice(old_book.getPrice());
			temp.setSalesQuantity(item.getSalesQuantity()); // �u��s�w�s
			temp.setStockQuantity(old_book.getStockQuantity());
			
			updateList.add(item);
		}

		bookDao.saveAll(updateList); // �ק�h�� Book ���
		return new BookResponse(boookList, "�ק令�\");
	} 

	/**
	 * �u��s����
	 * 
	 */
	@Override
	public BookResponse updatePrice(BookRequest request) {

		// �n�`�Napi��J���榡�C
		List<Book> boookList = request.getBookList(); // �ˬd�h�� Book : �ϥ� foreach �v���ˬd
		
		List<Book> updateList = new ArrayList<>(); // �ݭn��s��list

		for (Book item : boookList) {
			Book temp = new Book();
			Book old_book = bookDao.findById(item.getISBN()).get();
			
			// �ˬdISBN �����šA�B��������0
			if (!StringUtils.hasText(old_book.getBookTitle()) && !StringUtils.hasText(old_book.getISBN()) && item.getPrice() != 0 && old_book.equals(null)) {
				return new BookResponse("��s����!!");
			}
			
			temp.setAuthor(old_book.getAuthor());
			temp.setBookTitle(old_book.getBookTitle());
			System.out.println("step1 . " + old_book.getAuthor() + "\n");
			System.out.println("step1 . " + old_book.getBookTitle() + "\n");
			System.out.println("step1 . " + old_book.getISBN() + "\n");
			temp.setISBN(old_book.getISBN());
			temp.setPrice(item.getPrice()); // �u��s����
			temp.setSalesQuantity(old_book.getSalesQuantity());
			temp.setStockQuantity(old_book.getStockQuantity());
			updateList.add(item);
			System.out.println("step1 ." + "\n");
		}
		System.out.println("step2 ." + "\n");
		bookDao.saveAll(updateList); // �ק�h�� Book ���
		System.out.println("step3 ." + "\n");
		return new BookResponse(boookList, "�ק令�\");
	} 

	/**
	 * userBuyBook ���O���ʶR(�i�R�h���A���ܦh3���A���e3): 
	 * �u��ܮѦW�BISBN�B�@�̡B����B�ʶR�ƶq�B�ʶR�`����B
	 * �P��q+1�B�w�s�q-1
	 * 
	 * request �ϥΪ̵o�e���ШD
	 */
	@Override
	public UserOrderListResponse userBuyBook(UserOrderListRequest request) {
		// ���o�ϥΪ̮��O��request
		List<UserOrderList> userShop = request.getUserShop();
		// ����ʪ����G�M��
		List<UserOrderList> resultOrderList = new ArrayList<>();
		Integer totalCount = 0;
		//�ˬd�h�� Book : �ϥ� foreach �v���ˬd
		for (UserOrderList item : userShop) {
			UserOrderList temp = new UserOrderList();
			// ���oDB���Ѿl�w�s���y���
			Book old_book = bookDao.findById(item.getISBN()).get();
			// �ˬd�浧 Book(item) �ˬdisbn�O�_���šB�i�R�h���A���ܦh3���A�w�s�q�j���ʶR�q
			if (!item.getISBN().equals(null) && 
				0 < item.getStockQuantity()  && 
				item.getStockQuantity() <= 3 && 
				!old_book.equals(null)       &&
				old_book.getStockQuantity() >= item.getStockQuantity() ) {
				return new UserOrderListResponse("�ʶR����!!");
			} 
			
			old_book.setSalesQuantity(old_book.getSalesQuantity()+1); //�P��q�[�@
			old_book.setStockQuantity(old_book.getStockQuantity()-1); //�w�s�q��@

			totalCount += old_book.getPrice() * item.getHowManyBuy(); // ����ʶR�`�� = DB���y��� * �ϥΪ��ʶR�ƶq
			
			//�u��ܮѦW�BISBN�B�@�̡B����B�ʶR�ƶq�A�ʶR�`����
			temp.setISBN(old_book.getISBN());
			temp.setBookTitle(old_book.getBookTitle());
			temp.setAuthor(old_book.getAuthor());
			temp.setPrice(old_book.getPrice());
			temp.setHowManyBuy(item.getHowManyBuy());
			temp.setTotalPrices(totalCount); 			
			resultOrderList.add(temp); // �]�w�i����ݭn��ܵ�API��Response
			
            bookDao.save(old_book);
		}
		
		return new UserOrderListResponse(resultOrderList,"�s�W���\");
	}
	
	/**
	 * searchBookByUser()
	 * �Ѯ��y�Ӭd�ߨϥ�
	 * (�z�L�ѦW�� ISBN�Χ@��)
	 * ��ܮѦW�BISBN�B�@�̡B����B�P��q�B�w�s�q
	 * 
	 */
	@Override
	public BookResponse searchBook(BookRequest request) {
		List<Book> boookList = request.getBookList();
		List<Book> search_boookList = new ArrayList<>();
		
		List<Book> final_result = new ArrayList<>();

		// �z�L�ѦW�� ISBN�Χ@��
		for (Book item : boookList) {
			if (!item.getBookTitle().equals(null)) {
				// �̨�ѦW�j�M���y���
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
				// �̨�ISBN�j�M���y���
				Book temp = bookDao.findById(item.getISBN()).get();
				String title = temp.getBookTitle();
				String isbn = temp.getISBN();
				String author = temp.getAuthor();
				Integer price = temp.getPrice();
				final_result.add(new Book(title, isbn, author, price));
			} else if (!item.getAuthor().equals(null)) {
				// �̨�@�̷j�M���y���
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
				return new BookResponse(final_result, "�d�ߥ���");
			}

		}

		return new BookResponse(final_result, "�d�ߦ��\");
	}

	/**
	 * searchBookByUser()
	 * �ѨϥΪ̬d�ߨϥ�
	 * �u��ܮѦW�BISBN�B�@�̡B����
	 * 
	 */
	@Override
	public BookResponse searchBookByUser(BookRequest request) {
		List<Book> boookList = request.getBookList();
		List<Book> search_boookList = new ArrayList<>();
		
		List<Book> final_result = new ArrayList<>();

		// �z�L�ѦW�� ISBN�Χ@��
		for (Book item : boookList) {
			if (!item.getBookTitle().equals(null)) {
				// �̨�ѦW�j�M���y���
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
				// �̨�ISBN�j�M���y���
				Book temp = bookDao.findById(item.getISBN()).get();
				String title = temp.getBookTitle();
				String isbn = temp.getISBN();
				String author = temp.getAuthor();
				Integer price = temp.getPrice();
				final_result.add(new Book(title, isbn, author, price));
			} else if (!item.getAuthor().equals(null)) {
				// �̨�@�̷j�M���y���
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
				return new BookResponse(final_result, "�d�ߥ���");
			}

		}

		return new BookResponse(final_result, "�d�ߦ��\");
	}

	
	/**
	 * �j�Mtop5 �����P��q�����y
	 */
	@Override
	public BookResponse searchTop5BookBySQ() {
		List<Book> final_result = bookDao.findTop5BySalesQuantity();
		if(final_result.equals(null)) { 
			return new BookResponse(final_result, "�d�ߥ���");
		} else {
			return new BookResponse(final_result, "�d�ߦ��\");
		}
	}

	

}
