package com.ad.simpleLib.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ad.simpleLib.domain.dto.BookDto;
import com.ad.simpleLib.domain.entities.BookEntity;
import com.ad.simpleLib.mappers.Mapper;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

	private ModelMapper modelMapper;
	
	
	public BookMapper(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	@Override
	public BookDto mapTo(BookEntity book) {
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	public BookEntity mapFrom(BookDto bookDto) {
		return modelMapper.map(bookDto, BookEntity.class);
	}

}
