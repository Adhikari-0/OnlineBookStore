package com.onlineBookStore.businessLogic.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.services.AuthenticationService;
import com.onlineBookStore.businessLogic.dto.BookDTO;
import com.onlineBookStore.businessLogic.entity.Book;
import com.onlineBookStore.businessLogic.repository.BookRepository;
import com.onlineBookStore.businessLogic.service.WishlistService;

@PreAuthorize("hasAnyRole('USER')")
@Controller
@RequestMapping("/wishlist")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthenticationService authenticationService;

	// Add to wishlist
	@GetMapping("/add/{bookId}")
	@ResponseBody
	public Map<String, String> addToWishlist(@PathVariable int bookId) {
		User user = authenticationService.getCurrentUser();
		Book book = bookRepository.findById(bookId).orElseThrow();

		wishlistService.addToWishlist(user, book);

		return Map.of("message", "Added successfully");
	}

	// Remove from wishlist
	@PostMapping("/remove")
	public String removeFromWishlist(@RequestParam int id) {

		User user = authenticationService.getCurrentUser();
		Book book = bookRepository.findById(id).orElseThrow();

		wishlistService.removeFromWishlist(user, book);

		return "redirect:/wishlist";
	}

	// View wishlist
	@GetMapping
	public ModelAndView viewWishlist() {
		List<BookDTO> responseList = wishlistService.getUserWishlist();
		return new ModelAndView("wiseList", "wishlist", responseList);
	}
}
