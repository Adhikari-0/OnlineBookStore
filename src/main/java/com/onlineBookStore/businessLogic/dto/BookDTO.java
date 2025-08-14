package com.onlineBookStore.businessLogic.dto;

public class BookDTO {

	private int id;
	private String name;
	private String author;
	private String price;
	private String category;

	private String imageData;
	private String imageFileName;

	private String pdfData;
	private String pdfFileName;

	public BookDTO() {
		super();
	}

	public BookDTO(int id, String name, String author, String price, String category, String imageData,
			String imageFileName, String pdfData, String pdfFileName) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
		this.category = category;
		this.imageData = imageData;
		this.imageFileName = imageFileName;
		this.pdfData = pdfData;
		this.pdfFileName = pdfFileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getPdfData() {
		return pdfData;
	}

	public void setPdfData(String pdfData) {
		this.pdfData = pdfData;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

}
