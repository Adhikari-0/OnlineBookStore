package com.onlineBookStore.businessLogic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.onlineBookStore.businessLogic.dto.BookDTO;
import com.onlineBookStore.businessLogic.entity.Book;
import com.onlineBookStore.businessLogic.entity.BookImage;
import com.onlineBookStore.businessLogic.entity.BookPdf;
import com.onlineBookStore.businessLogic.repository.BookRepository;



@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Book saveBook(String name, String author, String price, String category, MultipartFile imageFile,
			MultipartFile pdfFile) throws IOException {

		if (!pdfFile.getOriginalFilename().toLowerCase().endsWith(".pdf")
				|| !"application/pdf".equalsIgnoreCase(pdfFile.getContentType())) {
			throw new IllegalArgumentException("Only PDF files are allowed for upload.");
		}

		Book book = new Book();
		book.setName(name);
		book.setAuthor(author);
		book.setPrice(price);
		book.setCategory(category);

		BookImage bookImage = new BookImage();
		bookImage.setFileName(imageFile.getOriginalFilename());
		bookImage.setFileType(imageFile.getContentType());
		bookImage.setImageData(imageFile.getBytes());
		bookImage.setBook(book);

		BookPdf bookPdf = new BookPdf();
		bookPdf.setFileName(pdfFile.getOriginalFilename());
		bookPdf.setFileType(pdfFile.getContentType());
		bookPdf.setPdfData(pdfFile.getBytes());
		bookPdf.setBook(book);

		book.setImage(bookImage);
		book.setPdf(bookPdf);

		return bookRepository.save(book);
	}

	public List<BookDTO> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		List<BookDTO> responseList = new ArrayList<>();

		for (Book book : books) {
			BookDTO dto = new BookDTO();
			dto.setId(book.getId());
			dto.setName(book.getName());
			dto.setAuthor(book.getAuthor());
			dto.setPrice(book.getPrice());
			dto.setCategory(book.getCategory());

			if (book.getImage() != null && book.getImage().getImageData() != null) {
				dto.setImageData(Base64.getEncoder().encodeToString(book.getImage().getImageData()));
				dto.setImageFileName(book.getImage().getFileName());
			}

			if (book.getPdf() != null && book.getPdf().getPdfData() != null) {
				dto.setPdfData(Base64.getEncoder().encodeToString(book.getPdf().getPdfData()));
				dto.setPdfFileName(book.getPdf().getFileName());
			}

			responseList.add(dto);
		}

		return responseList;
	}

	@Transactional
	public void deleteBookById(int id) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			bookRepository.delete(book);
		}
	}

	public BookDTO getBookDtoById(int id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

		BookDTO dto = new BookDTO();
		dto.setId(book.getId());
		dto.setName(book.getName());
		dto.setAuthor(book.getAuthor());
		dto.setPrice(book.getPrice());
		dto.setCategory(book.getCategory());

		if (book.getImage() != null) {
			dto.setImageFileName(book.getImage().getFileName());
			dto.setImageData(Base64.getEncoder().encodeToString(book.getImage().getImageData()));
		}

		if (book.getPdf() != null) {
			dto.setPdfData(Base64.getEncoder().encodeToString(book.getPdf().getPdfData()));
			dto.setPdfFileName(book.getPdf().getFileName());
		}

		return dto;
	}

	@Transactional
	public void updateBook(int id, String name, String author, String price, String category, MultipartFile imageFile,
			MultipartFile pdfFile) throws IOException {

		// Book book = getBookById(id);
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

		book.setName(name);
		book.setAuthor(author);
		book.setPrice(price);
		book.setCategory(category);

		if (imageFile != null && !imageFile.isEmpty()) {
			book.getImage().setFileName(imageFile.getOriginalFilename());
			book.getImage().setFileType(imageFile.getContentType());
			book.getImage().setImageData(imageFile.getBytes());
		}

		if (pdfFile != null && !pdfFile.isEmpty()) {
			book.getPdf().setFileName(pdfFile.getOriginalFilename());
			book.getPdf().setFileType(pdfFile.getContentType());
			book.getPdf().setPdfData(pdfFile.getBytes());
		}

		bookRepository.save(book);
	}

	public List<BookDTO> getBookDTOsByCategory(String category) {
		List<Book> books = bookRepository.findByCategory(category);

		return books.stream().map(book -> {
			BookDTO dto = new BookDTO();
			dto.setId(book.getId());
			dto.setName(book.getName());
			dto.setAuthor(book.getAuthor());
			dto.setCategory(book.getCategory());
			dto.setPrice(book.getPrice());

			if (book.getImage() != null && book.getImage().getImageData() != null) {
				dto.setImageData(Base64.getEncoder().encodeToString(book.getImage().getImageData()));
			}

			if (book.getPdf() != null && book.getPdf().getPdfData() != null) {
				dto.setPdfData(Base64.getEncoder().encodeToString(book.getPdf().getPdfData()));
				dto.setPdfFileName(book.getPdf().getFileName());
			}

			return dto;
		}).collect(Collectors.toList());
	}

	public List<String> suggestBookNames(String keyword) {
		return bookRepository.findTop5ByNameContainingIgnoreCase(keyword).stream().map(Book::getName)
				.collect(Collectors.toList());
	}

	public List<BookDTO> searchBooksByName(String name) {
		List<Book> books = bookRepository.findByNameContainingIgnoreCase(name);

		return books.stream().map(book -> {
			BookDTO dto = new BookDTO();
			dto.setId(book.getId());
			dto.setName(book.getName());
			dto.setAuthor(book.getAuthor());
			dto.setPrice(book.getPrice());
			dto.setCategory(book.getCategory());

			// Handle image
			BookImage bookImage = book.getImage();
			if (bookImage != null) {
				dto.setImageData(Base64.getEncoder().encodeToString(bookImage.getImageData())); // Assuming byte[]
				dto.setImageFileName(bookImage.getFileName());
			}

			// Handle PDF
			BookPdf bookPdf = book.getPdf();
			if (bookPdf != null) {
				dto.setPdfData(Base64.getEncoder().encodeToString(bookPdf.getPdfData())); // Assuming byte[]
				dto.setPdfFileName(bookPdf.getFileName());
			}

			return dto;
		}).collect(Collectors.toList());
	}

}
