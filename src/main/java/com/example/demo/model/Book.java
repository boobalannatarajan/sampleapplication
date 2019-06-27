package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class Book {
	@Id
	private int bcode;
	private String author;
	private String title;
	private double price;
	
	public Book() {}
	public Book(int bcode, String author, String title, double price) {
		super();
		this.bcode = bcode;
		this.author = author;
		this.title = title;
		this.price = price;
	}
	/**
	 * @return the bcode
	 */
	public int getBcode() {
		return bcode;
	}
	/**
	 * @param bcode the bcode to set
	 */
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	

}
