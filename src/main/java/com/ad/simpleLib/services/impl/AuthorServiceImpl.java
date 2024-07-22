package com.ad.simpleLib.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.ad.simpleLib.domain.entities.AuthorEntity;
import com.ad.simpleLib.repositories.AuthorRepository;
import com.ad.simpleLib.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

	@Override
	public List<AuthorEntity> findAll() {
		return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Optional<AuthorEntity> findOne(Long id) {
		return authorRepository.findById(id);
	}

	@Override
	public boolean isExists(Long id) {
		return authorRepository.existsById(id);
	}
}