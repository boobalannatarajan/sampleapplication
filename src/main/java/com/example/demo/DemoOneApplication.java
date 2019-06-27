package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.BookRepository;
import com.example.demo.model.Book;

@SpringBootApplication
public class DemoOneApplication implements CommandLineRunner{
	
	@Autowired
	private BookRepository bookRepo;
	public static void main(String[] args) {
		SpringApplication.run(DemoOneApplication.class, args);
	}
	public void saveBook() {
		Book book =new Book(102,"shana","SQL",250.00);
		bookRepo.save(book);
	}
	@Override
	public void run(String... arg0) throws Exception {
		saveBook();
		
	}

}
