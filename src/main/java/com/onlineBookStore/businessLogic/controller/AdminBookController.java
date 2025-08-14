package com.onlineBookStore.businessLogic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlineBookStore.businessLogic.dto.BookDTO;
import com.onlineBookStore.businessLogic.entity.Book;
import com.onlineBookStore.businessLogic.service.BookService;


@RequestMapping("/admin")
@Controller
public class AdminBookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		System.out.println("aasd");
		return ResponseEntity.ok("Good Health");
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAllBooks() {
		List<BookDTO> responseList = bookService.getAllBooks();
		return new ModelAndView("admin-home", "book", responseList);
	}

	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}

	@PostMapping("/save")
	public String saveBook(@RequestParam String name, @RequestParam String author, @RequestParam String price,
			@RequestParam String category, @RequestParam MultipartFile imageFile, @RequestParam MultipartFile pdfFile,
			Model model) {

		try {
			Book savedBook = bookService.saveBook(name, author, price, category, imageFile, pdfFile);
			model.addAttribute("message", "✅ Book saved successfully with ID: " + savedBook.getId());
		} catch (Exception e) {
			model.addAttribute("message", "❌ Error saving book: ");
		}
		return "bookRegister";
	}

	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id, RedirectAttributes redirectAttributes) {
		bookService.deleteBookById(id);
		return "redirect:/admin";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditForm(@PathVariable int id) {
		BookDTO dto = bookService.getBookDtoById(id);
		ModelAndView mv = new ModelAndView("bookEdit");
		mv.addObject("book", dto);
		return mv;
	}

	@PostMapping("/update/{id}")
	@Transactional
	public String updateBook(@PathVariable int id, @RequestParam String name, @RequestParam String author,
			@RequestParam String price, @RequestParam String category,
			@RequestParam(required = false) MultipartFile imageFile,
			@RequestParam(required = false) MultipartFile pdfFile, Model model) {
		System.out.println("ID: " + id);
		try {
			bookService.updateBook(id, name, author, price, category, imageFile, pdfFile);
			model.addAttribute("message", "Book updated successfully.");
		} catch (Exception e) {
			model.addAttribute("message", "Error updating book: " + e.getMessage());
		}
		return "redirect:/admin";
	}

	@GetMapping("/categorysearch")
	public ModelAndView searchBooksByCategory(@RequestParam String category) {
		List<BookDTO> responseList = bookService.getBookDTOsByCategory(category);

		ModelAndView mv = new ModelAndView("admin-home");
		mv.addObject("book", responseList);
		mv.addObject("categoryName", category);
		return mv;
	}

	@GetMapping("/suggest")
	public ResponseEntity<List<String>> suggestBooks(@RequestParam("q") String query) {
		List<String> suggestions = bookService.suggestBookNames(query);
		//System.out.println(query);
		return ResponseEntity.ok(suggestions);
	}

	@GetMapping("/search")
	public ModelAndView searchBooks(@RequestParam String name) {
		List<BookDTO> result = bookService.searchBooksByName(name);
		ModelAndView mv = new ModelAndView("admin-home");
		mv.addObject("book", result);
		mv.addObject("searchKeyword", name);
		mv.addObject("noBookFound", result.isEmpty());
		return mv;
	}

}
