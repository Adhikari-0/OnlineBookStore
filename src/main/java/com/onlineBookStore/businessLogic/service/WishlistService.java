package com.onlineBookStore.businessLogic.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.services.AuthenticationService;
import com.onlineBookStore.businessLogic.dto.BookDTO;
import com.onlineBookStore.businessLogic.entity.Book;
import com.onlineBookStore.businessLogic.entity.Wishlist;
import com.onlineBookStore.businessLogic.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private AuthenticationService authenticationService;

	public void addToWishlist(User user, Book book) {

		if (wishlistRepository.existsByUserAndBook(user, book)) {
			return; // already exists
		}

		Wishlist wishlist = new Wishlist();
		wishlist.setUser(user);
		wishlist.setBook(book);

		wishlistRepository.save(wishlist);
	}

	public List<BookDTO> getUserWishlist() {
		User user = authenticationService.getCurrentUser();
		List<Wishlist> wishlists = wishlistRepository.findByUser(user);
		List<BookDTO> responseList = new ArrayList<>();
		for (Wishlist wishlist : wishlists) {
			BookDTO dto = new BookDTO();
			dto.setId(wishlist.getBook().getId());
			dto.setName(wishlist.getBook().getName());
			dto.setAuthor(wishlist.getBook().getAuthor());
			dto.setPrice(wishlist.getBook().getPrice());
			dto.setCategory(wishlist.getBook().getCategory());

			if (wishlist.getBook().getImage() != null && wishlist.getBook().getImage().getImageData() != null) {
				dto.setImageData(Base64.getEncoder().encodeToString(wishlist.getBook().getImage().getImageData()));
				dto.setImageFileName(wishlist.getBook().getImage().getFileName());
			}

			if (wishlist.getBook().getPdf() != null && wishlist.getBook().getPdf().getPdfData() != null) {
				dto.setPdfData(Base64.getEncoder().encodeToString(wishlist.getBook().getPdf().getPdfData()));
				dto.setPdfFileName(wishlist.getBook().getPdf().getFileName());
			}

			responseList.add(dto);
		}

		return responseList;
		//return wishlistRepository.findByUser(user);
	}

	@Transactional
	public void removeFromWishlist(User user, Book book) {
		wishlistRepository.deleteByUserAndBook(user, book);
	}

}
