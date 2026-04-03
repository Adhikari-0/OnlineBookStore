package com.onlineBookStore.businessLogic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineBookStore.businessLogic.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	List<Book> findByCategory(String category);
	List<Book> findTop5ByNameContainingIgnoreCase(String name);
	List<Book> findByNameContainingIgnoreCase(String name);
	Optional<Book> findById(Integer id);

}
