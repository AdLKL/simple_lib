package com.ad.simpleLib.services;

import java.util.List;
import java.util.Optional;

import com.ad.simpleLib.domain.entities.BookEntity;

public interface BookService {

	BookEntity createUpdateBook(String isbn, BookEntity book);

	List<BookEntity> findAll();

	Optional<BookEntity> findOne(String isbn);

	boolean isExists(String isbn);

	BookEntity partialUpdate(String isbn, BookEntity bookEntity);

	void delete(String isbn);
}
