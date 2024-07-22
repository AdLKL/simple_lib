package com.ad.simpleLib.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.ad.simpleLib.domain.entities.BookEntity;
import com.ad.simpleLib.repositories.BookRepository;
import com.ad.simpleLib.services.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	
	
	public BookServiceImpl(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}


	@Override
	public BookEntity createUpdateBook(String isbn, BookEntity book) {
		book.setIsbn(isbn);
		return bookRepository.save(book);
	}


	@Override
	public List<BookEntity> findAll() {
		return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}


	@Override
	public Optional<BookEntity> findOne(String isbn) {
		return bookRepository.findById(isbn);
	}


	@Override
	public boolean isExists(String isbn) {
		return bookRepository.existsById(isbn);
	}

}
