package com.onlineBookStore.businessLogic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.businessLogic.entity.Book;
import com.onlineBookStore.businessLogic.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	List<Wishlist> findByUser(User user);

	boolean existsByUserAndBook(User user, Book book);

	void deleteByUserAndBook(User user, Book book);

}
