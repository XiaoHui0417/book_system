package com.example.book_system.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.book_system.entity.Book;


public interface BookDao extends JpaRepository<Book, String>{
	
	@Query(value = "SELECT * FROM book WHERE isbn = ?1", nativeQuery = true)
	List<Book> findByISBN(String isbn);
	
	@Query(value = "SELECT * FROM book WHERE book_title = ?1", nativeQuery = true)
	List<Book> findByBookTitle(String book_title);
	
	@Query(value = "SELECT * FROM book WHERE author = ?1", nativeQuery = true)
	List<Book> findByAuthor(String author);
	
	@Query(value = "SELECT * FROM book ORDER BY sales_quantity DESC LIMIT 5 ", nativeQuery = true)
	List<Book> findTop5BySalesQuantity();

}
