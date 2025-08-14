package com.onlineBookStore.businessLogic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.onlineBookStore.businessLogic.dto.BookDTO;
import com.onlineBookStore.businessLogic.service.BookService;


@RequestMapping("/book")
@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public ModelAndView getAllBooks() {
		List<BookDTO> responseList = bookService.getAllBooks();
		return new ModelAndView("home", "book", responseList);
	}
	@GetMapping("/wiseList")
	public ModelAndView getWiseListBooks() {
		List<BookDTO> responseList = bookService.getAllBooks();
		return new ModelAndView("wiseList", "book", responseList);
	}

	@GetMapping("/categorysearch")
	public ModelAndView searchBooksByCategory(@RequestParam String category) {
		List<BookDTO> responseList = bookService.getBookDTOsByCategory(category);

		ModelAndView mv = new ModelAndView("home");
		mv.addObject("book", responseList);
		mv.addObject("categoryName", category);
		return mv;
	}
	@GetMapping("/suggest")
	public ResponseEntity<List<String>> suggestBooks(@RequestParam("q") String query) {
		List<String> suggestions = bookService.suggestBookNames(query);
		return ResponseEntity.ok(suggestions);
	}

	@GetMapping("/search")
	public ModelAndView searchBooks(@RequestParam String name) {
		List<BookDTO> result = bookService.searchBooksByName(name);
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("book", result);
		mv.addObject("searchKeyword", name);
		mv.addObject("noBookFound", result.isEmpty());
		return mv;
	}
}
